package com.sx.common.util;

/**
 * 正则校验工具类
 * @author jiangshuai
 */
public class PatternValidater {

    /**
     * 手机号码的正则
     */
    public static final String SJH = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
    /**
     * 统一社会信用代码的正则
     */
    public static final String TYSHXYDM = "^[0-9A-Z]{18}$";
    /**
     * 身份证号码的正则
     */
    public static final String SFZHM = "^[1-9]\\d{5}(19|20)\\d{2}[01]\\d[0-3]\\d\\d{3}[0-9X]$";
    /**
     * 人员姓名的正则
     */
    public static final String XM = "^[\\u4e00-\\u9fa5]+((·[\\u4e00-\\u9fa5]+)*)?$|^[a-zA-Z0-9]+((·[a-zA-Z0-9]+)*)?$";
    /**
     * 人员姓名的正则（中文姓名）
     */
    public static final String XM_CN = "^[\\u4e00-\\u9fa5]+((·[\\u4e00-\\u9fa5]+)*)?$";
    /**
     * 中文输入框的正则
     */
    public static final String CHINESE = "^[\\u4e00-\\u9fa5]*$";
    /**
     * 日期 年月的正则
     */
    public static final String DATE_YM = "^(19|20)\\d{2}-((0[1-9])|(1[0-2]))$";
    /**
     * 日期 年月日的正则//
     */
    public static final String DATE_YMD = "^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
    /**
     * 日期yyyyMMdd格式的正则
     */
    public static final String YYYYMMDD = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])";
    /**
     * 日期yyyyMMddHHmmss的正则
     */
    public static final String yyyyMMddHHmmss = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])([01][0-9]|2[0-3])([0-5][0-9]){2}";
	/**
	 * 严格非法字符控制
	 */
	public static final String full = "^[^\\[\\]:?\"{}`=^&!*|;$%@\'<>+\\r\\n,\\\\../ ]*$";
	/**
	 * 标准非法字符控制
	 */
	public static final String normal = "^[^|$%'<>`+\\r\\n,\\\\ *]*$";
    /**
     * 标准非法字符控制（不包含逗号）
     */
    public static final String normal_d = "^[^|$%'<>`+\\r\\n\\\\ *]*$";
	/**
	 * 行政区划代码
	 */
	public static final String xzqhdm = "^([1-8][0-7]\\d{10})?$";
    /**
     * 行政区划代码，前六位
     */
    public static final String xzqhdm_6 = "^([1-8][0-7]\\d{4})?$";
    /**
     * 流动全国跨地区平台，机构编号
     */
    public static final String ldqgkdqpt_jgbh = "^([1-8][1-7]\\d{6})?$";
	/**
	 * 通用唯一识别码
	 */
	public static final String UUID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
	/**
	 * 通用唯一识别码，无-
	 */
    public static final String UUID_32 = "^[0-9a-fA-F]{32}$";
    /**
     * 正则校验两位小数
     */
    public static final String lwxs= "^(([1-9]{1}\\d*)|(0{1}))(\\.\\d{1,2})?$";
    /**
    *小数点前后是数字即可，无小数点后数据也ok
     */
    public static final String double_all="^[-\\+]?[.\\d]*$";
    /**
     *  区别控制小数点后两位，无小数点后数据也ok
     */
    public static final String double_two="^-?([0-9]+|[0-9]{1,3}(,[0-9]{3})*)(.[0-9]{1,2})?$";
    /**
     * 小数或整数
     */
    public static final String double_Int= "[0-9]*\\.?[0-9]+";
    /**
     * 证件号码15位（身份证号码|社保卡号）
     */
    public static final String zjhm_15 = "^[1-9][0-9]{5}[0-9]{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)[0-9]{2}[0-9]$";
    /**
     * 证件号码18位（身份证号码|社保卡号）
     */
    public static final String zjhm_18 = "^[1-9][0-9]{5}(18|19|20)[0-9]{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)[0-9]{3}([0-9]|(X|x))$";

    //通行证号
    public static final String txzh="(^[HMhm]{1}\\d{8}$)|(^[0-9]{8}$)";
    //邮编
    public  static final String yb = "^([0-9]{6})?$";
    //邮箱
    public  static final String email = "^([-\\w]?\\w+([-+.]\\w+)*[-\\w]?@[-\\w]?\\w+([-.]\\w+)*[-\\w]?\\.[-\\w]?\\w+([-.]\\w+)*[-\\w]?)?$";
    //联系电话
    public  static final String lxdh = "^([0-9]{11})?";
    //网址
    public  static final String url = "^http:\\/\\/[A-Za-z0-9-]+\\.[A-Za-z0-9-]+[\\/=\\?%\\-&_~`@[\\-]\\':+!-]*([^<>\\-])*$";
    //固定电话
    public  static final String gddh = "^\\d?[0-9-]*$";
    //英文
    public  static final String english = "^[A-Za-z]*$";
    //中文
    public  static final String chinese = "^[Α-￥]*$";
    //QQ号码[10位以内]
    public  static final String qq = "^([1-9]\\d{4,9})?$";
    //正整数(包含0)
    public  static final String number = "^\\d*$";
    //正整数(不包含0)
    public  static final String num = "^[1-9]\\d*$";
    //整数(正和负)
    public  static final String integer = "^[-\\+]?\\d*$";
    //浮点数(正和负)
    public  static final String Double = "^[-\\+]?\\d+(\\.\\d+)?$";
    //正浮点数(包含0)
    public  static final String posdouble = "^\\d+(\\.\\d+)?$";
    //组织机构代码[含-]
    public  static final String zzjgdm = "^([a-zA-Z0-9]{8}-[a-zA-Z0-9])?$";
    //组织机构代码[不含-]
    public  static final String zzjgm = "^([a-zA-Z0-9]{9})*$";
    //QQ号码[5位以上]
    public  static final String longqq = "^([1-9]\\d{4,})?$";
    //IP地址
    public  static final String ip = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    //双字节字符(包括汉字)
    public  static final String szjzf = "[^\\x00-\\xff]";
    //护照
    public  static final String hzh = "^[a-zA-Z0-9]{1,18}$";
    //字母数字下划线
    public static final String zmszxhx = "^[a-zA-Z0-9_]*$";

    /**
     * 日期 年月的正则 不带杠
     */
    public static final String DATEYM = "^(19|20)\\d{2}((0[1-9])|(1[0-2]))$";

/*    public static void main(String[] args) {
        Pattern pattern = Pattern.compile(lwxs);
        Matcher matcher =pattern.matcher("1.5");
        System.out.println(matcher.matches());
    }*/
}