package com.sx.common;

/**
 * @author
 * @Classname SexEnum
 * @Description  性别枚举
 * @Date 2021/9/22 15:09
 * @Created by ly
 */
public enum SexEnum {
    man("1","男"),woman("2","女");

    SexEnum(String sexCode, String sexName) {
        this.sexCode = sexCode;
        this.sexName = sexName;
    }
    private final String sexCode;
    private final String sexName;

    public String getSexCode() {
        return sexCode;
    }

    public String getSexName() {
        return sexName;
    }
}
