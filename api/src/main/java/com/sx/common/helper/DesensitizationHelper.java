package com.sx.common.helper;


/**
 * 信息脱敏处理工具类
 * 2024.09.11
 */
public class DesensitizationHelper {

    /**
     * 证件号码脱敏处理：
     *  身份证号码：显示前四位和后四位，中间用*替代
     *      * 非身份证号码：
     *      *  （1）长度小于11，显示前2位和3两位，中间用*替代
     *      *  （2）长度大于或等于11，同身份证号码显示规则一样
     * @param idNumber
     * @return
     */
    public static String maskIdNumber(String idNumber){
        if (StringHelper.isEmpty(idNumber)) {
            return "";
        }
        int preCount = 0;  //*前保留位数
        int sufCount = 0;  //*后保留位数
        if(idNumber.length()>=11){
            //身份证号码(18)、证件号码(大于11)
            preCount = 4;
            sufCount = 4;
        }else{
            //证件号码(小于11)
            preCount = 2;
            sufCount = 3;
        }
        return montageStr(preCount, sufCount, idNumber);
    }

    /**
     * 手机号码脱敏处理：
     * 手机号：隐藏手机号中间的四位数，只展示前三位和后四位
     * @param mobile
     * @return
     */
    public static String maskMobile(String mobile){
        if(StringHelper.isEmpty(mobile)) {
            return "";
        }
        return montageStr(3, 4, mobile);
    }

    /**
     * 对字符串进行*拼接
     * preCount  *前保留位数
     * sufCount  *后保留位数
     * @param preCount
     * @param sufCount
     * @param idNum
     * @return
     */
    private static String montageStr(int preCount, int sufCount, String idNum){
        StringBuilder sbStr = new StringBuilder();
        sbStr.append(idNum.substring(0, preCount));
        for(int i=0; i<idNum.length()-preCount-sufCount;i++){
            sbStr.append("*");
        }
        sbStr.append(idNum.substring(idNum.length()-sufCount));
        return sbStr.toString();
    }

}
