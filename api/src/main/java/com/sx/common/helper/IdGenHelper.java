package com.sx.common.helper;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * @author: WangHe
 * @description:
 * @date: 2019\6\6 0006.
 */

public class IdGenHelper {
    private static SecureRandom random = new SecureRandom();


    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.  32位长度。一般用它
     *
     * @param flag 是否有中间的'-' true有 false无
     * @return
     */
    public static String uuid(boolean flag) {
        if (flag) {
            return UUID.randomUUID().toString();
        } else {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }


    private static String strPad(String str, int len) {
        int l = str.length();
        String s = str;
        for(int i = 0; i < len - l; ++i) {
            s = "0" + s;
        }
        return s;
    }
}
