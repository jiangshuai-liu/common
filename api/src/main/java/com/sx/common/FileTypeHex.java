package com.sx.common;



/**
 * 常见文件类型的文件头类型
 * @Classname FileTypeHex
 * @Description 文件类型枚举
 * @Date 2021-9-8 10:57:11
 * @Created by ly
 */
public enum FileTypeHex {
    JPEG("FFD8FF","jpg,jpeg"),
    PNG("89504E47","png"),
    GIF("47494638","gif"),
    BMP("424D","bmp"),
    DOC("D0CF11E0","doc"),
    XLS("D0CF11E0","xls"),
    PDF("255044462D312E","pdf"),
    RAR("52617221","rar"),
    ZIP("504B0304","zip"),
    XLSX("504B0304","xlsx"),
    DOCX("504B0304","docx"),
    AVI("41564920","avi"),
    ;

    FileTypeHex(String headHex, String extName) {
        this.headHex = headHex;
        this.extName = extName;
    }

    /**
     * 文件头最长字节
     */
    public static final int MAX_HEAD_HEX_LENGTH = 14;

    private final String headHex;
    private final String extName;

    public String getHeadHex() {
        return this.headHex;
    }

    public String getExtName() {
        return extName;
    }
}
