package com.sx.common.helper;


import com.sx.common.SexEnum;
import com.sx.common.util.DateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 身份证号码工具类
 * User: jzy
 * Date: 2021/7/27
 * Time: 11:41
 * Description: [  IDCardHelper工具类  ]
 */
public  class IDCardHelper {

	/**
	 * 判断是否是正确的身份证号码
	 * @param sfzhm
	 * @return 是否是正确的身份证号码
	 * @throws Exception
	 */
	public static boolean checkIdCard(String sfzhm)  {
		boolean b = false;
		if (sfzhm != null) {
			sfzhm = sfzhm.trim();
			sfzhm = sfzhm.toUpperCase();
			if (sfzhm.length() == 15) {
				String newId = toUpperCaseSfzhm(sfzhm);
				if (newId != null && !"".equals(newId)) {
					if (!checkSfzhmDate(newId)) {
						return false;
					}
					b = true;
				}
			} else if (sfzhm.length() == 18) {
				// String newId = this.sfzhmzh(sfzhm);
				String newId = check18Sfzhm(sfzhm);
				if (sfzhm.equals(newId)) {
					if (!checkSfzhmDate(newId)) {
						return false;
					}
					b = true;
				}
			}
		}
		return b;
	}

	/**
	 * 把15位的身份证号码转换为18位
	 * @param strobject1
	 * @return
	 */
	private static String toUpperCaseSfzhm(String strobject1) {
		int l_l_jym[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,
				1 };
		int l_l_total = 0;
		String last;
		String strobject = "";
		int lastnum = 0;
		int l_l_temp1 = 0;
		if (strobject1 == null) {
			strobject1 = "";
		}
		if (strobject1.length() == 15) {
			strobject = strobject1.substring(0, 6) + "19"
					+ strobject1.substring(6, 15);
			for (int i = 0; i < strobject.length(); i++) {
				try {
					l_l_temp1 = Integer.parseInt(strobject.substring(i, i + 1))
							* l_l_jym[i];
				} catch (Exception ex) {
					return "";
				}
				l_l_total += l_l_temp1;
			}
			l_l_total--;
			lastnum = l_l_total % 11;// 最后一位
			if (lastnum == 0) {
				last = "0";
			} else {
				if (lastnum == 1) {
					last = "X";
				} else {
					last = Integer.toString(11 - lastnum);
				}
			}
			strobject = strobject + last;
			return strobject;
		} else {
			return strobject1;
		}
	}

	/**
	 * 判断身份证号码中的日期是否正确
	 * @param sfzhm
	 * @return
	 */
	private static boolean checkSfzhmDate(String sfzhm) {
		try {
			int[] iaMonthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,
					31 };
			int year, month, day;
			if (sfzhm == null) {
				return false;
			}
			if (sfzhm.length() < 18) {
				return false;
			}
			year = Integer.parseInt(sfzhm.substring(6, 10));
			month = Integer.parseInt(sfzhm.substring(10, 12));
			day = Integer.parseInt(sfzhm.substring(12, 14));
			if (year < 1900 || year > 2100) {
				return false;
			}
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
				iaMonthDays[1] = 29;
			}
			if (month < 1 || month > 12) {
				return false;
			}
			if (day < 1 || day > iaMonthDays[month - 1]) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 检查18位身份证号码是否校验身份证是否正确
	 * @param strobject1
	 * @return
	 */
	private static String check18Sfzhm(String strobject1) {
		int l_l_jym[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2,
				1 };
		int l_l_total = 0;
		String last;
		String strobject = "";
		int lastnum = 0;
		int l_l_temp1 = 0;
		if (strobject1 == null) {
			strobject1 = "";
		}
		if (strobject1.length() == 18) {
			strobject = strobject1.substring(0, 17);
			for (int i = 0; i < strobject.length(); i++) {
				try {
					l_l_temp1 = Integer.parseInt(strobject.substring(i, i + 1))
							* l_l_jym[i];
				} catch (Exception ex) {
					return "";
				}
				l_l_total += l_l_temp1;
			}
			l_l_total--;
			lastnum = l_l_total % 11;// 最后一位
			if (lastnum == 0) {
				last = "0";
			} else {
				if (lastnum == 1) {
					last = "X";
				} else {
					last = Integer.toString(11 - lastnum);
				}
			}
			strobject = strobject + last;
			return strobject;
		} else {
			return "";
		}
	}
	
	/**
	 * 转换身份证号码-15为18位
	 * @param sfzhm
	 * @return 18位身份证号码
	 * @throws Exception
	 */
	public static String upperCaseSfzhm15to18(String sfzhm) throws Exception {
		String result = "";
		if(sfzhm==null){
			return result;
		}
		sfzhm  = sfzhm.trim();
		sfzhm = sfzhm.toUpperCase();
		if(sfzhm.length()!=18&&sfzhm.length()!=15){
			return result;
		}
		if(sfzhm.length()==18){
			if(checkIdCard(sfzhm)){
				result = sfzhm;
			}
			return result;
		}
		if(sfzhm.length()==15){
			if(checkIdCard(sfzhm)){
				sfzhm = toUpperCaseSfzhm(sfzhm);
				result = sfzhm;
			}
			return result;
		}
		if (!checkSfzhmDate(result)){
			return "";
		}
		return result;
	}

	/**
	 * 根据身份证号码得到性别
	 * @param sfzhm
	 * @return
	 */
	public static String getSexCode(String sfzhm) {
		if (sfzhm != null && sfzhm.length() == 18) {
			return Integer.parseInt(sfzhm.substring(16, 17)) % 2 == 0 ? SexEnum.woman.getSexCode() : SexEnum.man.getSexCode();
		} else {
			return "";
		}
	}

	/**
	 * 根据身份证号码得到性别名称
	 * @param sfzhm
	 * @return
	 */
	public static String getSexName(String sfzhm) {
		String sexCode = getSexCode(sfzhm);
		if (sexCode != null && !sexCode.trim().equals("")) {
			if (sexCode.trim().equals("1")) {
				return "男";
			}
			if (sexCode.trim().equals("2")) {
				return "女";
			}
		}
		return "";
	}

	/**
	 * 根据身份证号码得到出生年月
	 * @param sfzhm
	 * @return
	 */
	public static String getBirthday(String sfzhm) {
		return sfzhm != null && sfzhm.length() == 18 ? DateHelper.dateStrFormat(sfzhm.substring(6, 12), "yyyyMM", "yyyy-MM") : "";
	}

	/**
	 * 根据身份证号码得到出生日期
	 * @param sfzhm 需要计算的身份证号码
	 * @return 返回出生日期date对象
	 */
	public static Date getBirthdayDate(String sfzhm) {
		if(sfzhm == null || sfzhm.length() != 18){
			return null;
		}

		String birthDayStr=sfzhm.substring(6, 14);
		LocalDate localDate = LocalDate.parse(birthDayStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
		return DateUtils.transferLocalDateToDate(localDate);


	}

	/**
	 * 根据身份证号计算年龄，精确到月
	 * @param sfzhm 身份证号码
	 * @return 返回年龄
	 */
	public static int getAgeByMonth(String sfzhm){
		LocalDate now=LocalDate.now();
		int nowYear=now.getYear();
		int nowMonth=now.getMonthValue();
		int birthYear=Integer.parseInt(sfzhm.substring(6, 10));
		int birthMonth=Integer.parseInt(sfzhm.substring(10, 12));
		return ((nowYear-birthYear)*12+(nowMonth-birthMonth))/12;
	}

	public static int getAgeByDay(String sfzhm){
		LocalDate now = LocalDate.now();
		int nowYear = now.getYear();
		int nowMonth = now.getMonthValue();
		int birthYear = Integer.parseInt(sfzhm.substring(6, 10));
		int birthMonth = Integer.parseInt(sfzhm.substring(10, 12));
		int birthDay = Integer.parseInt(sfzhm.substring(12, 14));
		int dayM = 0;
		if(birthDay > now.getDayOfMonth()){
			dayM = -1;
		}
		return ((nowYear-birthYear)*12+(nowMonth-birthMonth)+dayM)/12;
	}

//	public static void main(String[] args) {
//		System.out.println(getAgeByDay("421023199411160413"));
//		System.out.println(getAgeByDay("421023199411170413"));
//		System.out.println(getAgeByDay("421023199411180413"));
//	}

}
