package com.sx.common.helper;


import com.sx.common.FileTypeHex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class FileHelper {

    /**
     *  将内容写入到某个文件中，可自动创建
     * @param filePath 文件内容
     * @param isAppend 是否追加  true 追加在文件后面 false 清空文件原始数据然后写入
     * @param content 文件内容
     * @throws IOException 文件不存在或其他特定错误会抛出io异常
     */
    public static void writeByteFile(String filePath,boolean isAppend,byte[] content) throws IOException {
        Path path= Paths.get(filePath);
        if(isAppend){
            Files.write(path, content,
                    StandardOpenOption.WRITE,  StandardOpenOption.CREATE,StandardOpenOption.APPEND );//可写。不存在就创建，清空数据
        }else{
            Files.write(path, content,
                    StandardOpenOption.WRITE,  StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING );//可写。不存在就创建，清空数据
        }
    }

    /**
     *  将内容写入到某个文件中，可自动创建
     * @param filePath 文件内容
     * @param isAppend 是否追加  true 追加在文件后面 false 清空文件原始数据然后写入
     * @param content 文件内容
     * @throws IOException 文件不存在或其他特定错误会抛出io异常
     */
    public static void writeFile(String filePath,boolean isAppend,String content) throws IOException {
        writeByteFile(filePath, isAppend,content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 读取图片等二进制文件的文件内容
     * @param filePath  文件路径
     * @return 返回文件所有字节
     * @throws IOException 文件不存在或其他特定错误会抛出io异常
     */
    public static byte[] readByteFile(String filePath) throws IOException {
        Path path= Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    /**
     * 读取文件内容
     * @param filePath  文件路径
     * @param cs 字符集，可从 StandardCharsets类中取
     * @return 返回文件所有内容
     * @throws IOException 文件不存在或其他特定错误会抛出io异常
     */
    public static String readFile(String filePath, Charset cs) throws IOException {
        Path path= Paths.get(filePath);
        List<String> strings=Files.readAllLines(path,cs);
        StringBuilder stringBuilder=new StringBuilder();
        for (String string : strings) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    /**
     *  读取文件内容 以UTF-8的字符集读取文件
     * @param filePath  文件路径
     * @return 返回文件所有内容
     * @throws IOException 文件不存在或其他特定错误会抛出io异常
     */
    public static String readFile(String filePath) throws IOException {
        return readFile(filePath, StandardCharsets.UTF_8);
    }


    /**
     * 校验文件后缀名是否是指定文件格式
     * @param file spring包装的文件类型
     * @param fileTypeHex 文件类型枚举
     * @return boolean  true 表示符合文件格式，false 表示不符合文件格式
     */
    public static boolean checkFileType(MultipartFile file, FileTypeHex... fileTypeHex) throws IOException {
        return checkFileExtName(file.getOriginalFilename(),fileTypeHex)&&checkFileType(file.getBytes(),fileTypeHex);
    }

    /**
     * 校验文件后缀名是否是指定文件格式
     * @param fileName 原始文件名称
     * @param fileTypeHex 文件类型枚举
     * @return boolean  true 表示符合文件格式，false 表示不符合文件格式
     */
    public static boolean checkFileExtName(String fileName, FileTypeHex... fileTypeHex){
        if(fileName.lastIndexOf(".")==-1){
            return false;
        }
        String fileExtName=fileName.substring(fileName.lastIndexOf(".")+1);
        for (FileTypeHex typeHex : fileTypeHex) {
            String[] extName=typeHex.getExtName().split(",");
            for (String s : extName) {
                if(fileExtName.toLowerCase().equals(s)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 校验文件是否是指定文件格式
     * @param filePath 需要校验的文件路径
     * @param fileTypeHex 文件类型枚举
     * @return true 表示符合文件格式，false 表示不符合文件格式
     * @throws IOException 文件不存在或其他
     */
    public static boolean checkFileType(String filePath, FileTypeHex... fileTypeHex) throws IOException {
        try(FileInputStream fin = new FileInputStream(new File(filePath)); FileChannel channel = fin.getChannel()){
            ByteBuffer bf = ByteBuffer.allocate(FileTypeHex.MAX_HEAD_HEX_LENGTH);
            channel.read(bf);
            return checkFileType(bf.array(),fileTypeHex);
        }
    }

    public static boolean checkFileType(byte[] fileData, FileTypeHex... fileTypeHex)  {
        byte[] headByte=Arrays.copyOf(fileData, FileTypeHex.MAX_HEAD_HEX_LENGTH);
        String string = bytesToHexString(headByte);
        for (FileTypeHex typeHex : fileTypeHex) {
            assert string != null;
            if(string.startsWith(typeHex.getHeadHex())){
                return true;
            }
        }
        return false;
    }

    /**
     * 将要读取文件头信息的文件的byte数组转换成string类型表示
     * @param src 要读取文件头信息的文件的byte数组
     * @return 文件头信息
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (byte b : src) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(b & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    /**
     * 判断文件大小
     *
     * @param len
     *            文件长度
     * @param size
     *            限制大小
     * @param unit FileSizeEnum 枚举，计算的单位
     *
     * @return 超过限制大小返回false 不超过限制大小返回true
     */
    public static boolean checkFileSize(long len, int size, FileSizeEnum unit) {
        if(len<0){
            throw new IllegalArgumentException("文件长度不能小于0");
        }
        if(size<0){
            throw new IllegalArgumentException("文件限制大小不能小于0");
        }
        long checkSize=size*unit.getFileSize();
        return len<=checkSize;
    }

    /**
     * 将输入流的输入写入到输出流中
     * @param input 输入流
     * @param output 输出流
     * @throws IOException
     */
    public static void streamWrite(InputStream input, OutputStream output) throws IOException {
        try (
                ReadableByteChannel inputChannel = Channels.newChannel(input);
                WritableByteChannel outputChannel = Channels.newChannel(output);
        ) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(8192);
            while (inputChannel.read(buffer) != -1) {
                buffer.flip();
                outputChannel.write(buffer);
                buffer.clear();
            }
        }
    }



    public enum FileSizeEnum{

        G(1073741824),MB(1048576),KB(1024),B(1);

        FileSizeEnum(long fileSize) {
            this.fileSize = fileSize;
        }
        private final long fileSize;

        public long getFileSize() {
            return fileSize;
        }
    }

}
