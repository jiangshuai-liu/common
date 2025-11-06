package com.sx.common.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式判断字符串中是否仅包含英文字母、数字和汉字
 * @Title StrValidate
 * @Author jiangshuai
 * @Date 2025/4/8 15:34
 * @Version 1.0
 **/
public class StrValidate {
    /**
     * 纯数字
     */
    private static String DIGIT_REGEX = "[0-9]+";
    /**
     * 含有数字
     */
    private static String CONTAIN_DIGIT_REGEX = ".*[0-9].*";
    /**
     *  纯字母
     */
    private static String LETTER_REGEX = "[a-zA-Z]+";
    /**
     * 包含字母
     */
    private static String CONTAIN_LETTER_REGEX = ".*[a-zA-z].*";
    /**
     * 纯中文
     */
    private static String CHINESE_REGEX = "[\u4e00-\u9fa5]";
    /**
     * 仅仅包含字母和数字
     */
    private static String LETTER_DIGIT_REGEX = "^[a-z0-9A-Z]+$";
    private static String CHINESE_LETTER_REGEX = "([\u4e00-\u9fa5]+|[a-zA-Z]+)";
    private static String CHINESE_LETTER_DIGIT_REGEX = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";

    /**
     * 判断字符串是否仅含有数字和字母
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        return str.matches(LETTER_DIGIT_REGEX);
    }
    /**
     * 是否为汉字，不包括标点符号
     *
     * @param con
     * @return true 是汉字
     */
    public static boolean isChinese(String con) {
        Pattern pattern = Pattern.compile(CHINESE_REGEX);
        for (int i = 0; i < con.length(); i = i + 1) {
            if (!pattern.matcher(
                    String.valueOf(con.charAt(i))).find()) {
                return false;
            }
        }
        return true;
    }
    /**
     * 用正则表达式判断字符串中是否
     * 仅包含英文字母、数字和汉字
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigitOrChinese(String str) {
        return str.matches(CHINESE_LETTER_DIGIT_REGEX);
    }
    /**
     * 姓名中可包含汉字和字母，无其它字符
     *
     * @param passengerName
     * @return
     */
    public static boolean checkChineseLetter(String passengerName) {
        Pattern pattern = Pattern.compile(CHINESE_LETTER_REGEX);
        Matcher matcher = pattern.matcher(passengerName);
        if (matcher.matches()) {
            //不包含特殊字符
            return true;
        } else {
            //包含了特殊字符
            return false;
        }
    }
    /**
     * 判断一个字符串是否包含标点符号（中文或者英文标点符号），true 包含。<br/>
     * 原理：对原字符串做一次清洗，清洗掉所有标点符号。<br/>
     * 此时，如果入参 ret 包含标点符号，那么清洗前后字符串长度不同，返回true；否则，长度相等，返回false。<br/>
     *
     * @param ret
     * @return true 包含中英文标点符号
     */
    public static boolean checkPunctuation(String ret) {
        boolean b = false;
        String tmp = ret;
//        replaceAll里面的正则匹配可以清空字符串中的中英文标点符号，只保留数字、英文和中文。
        tmp = tmp.replaceAll("\\p{P}", "");
        if (ret.length() != tmp.length()) {
            b = true;
        }
        return b;
    }
    public static boolean isDigit(String ret) {
        return ret.matches(DIGIT_REGEX);
    }
    public static boolean isLetter(String ret) {
        return ret.matches(LETTER_REGEX);
    }
    public static boolean hasDigit(String ret) {
        return ret.matches(CONTAIN_DIGIT_REGEX);
    }
    public static boolean hasLetter(String ret) {
        return ret.matches(CONTAIN_LETTER_REGEX);
    }
}
