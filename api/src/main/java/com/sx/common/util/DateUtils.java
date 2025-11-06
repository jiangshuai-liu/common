package com.sx.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * @Classname DateUtils
 * @Description 日期工具类，以操作Date 类为主
 * @Date 2021/9/13 17:16
 * @Created by ly
 */
public class DateUtils {
    /**
     * 年月日时分秒无分隔符的基础格式 yyyyMMddHHmmss
     */
    public static final String BASE_PATTERN_DATE_TIME="yyyyMMddHHmmss";

    /**
     * 年月日时分秒 的显示格式 yyyy-MM-dd HH:mm:ss
     */
    public static final String PATTERN_DATE_TIME="yyyy-MM-dd HH:mm:ss";

    /**
     *  年月日无分隔符的基础格式 yyyyMMdd
     */
    public static final String BASE_PATTERN_DATE="yyyyMMdd";

    /**
     * 年月日的显示格式 yyyy-MM-dd
     */
    public static final String PATTERN_DATE="yyyy-MM-dd";
    /**
     * 年月日的显示格式 yyyy-MM
     */
    public static final String PATTERN_MONTH="yyyy-MM";
    /**
     * 得到当前时间
     * @return 返回当前时间date
     */
    public static Date now(){
        return transferLocalDateTimeToDate(LocalDateTime.now());
    }

    /**
     * 得到当前时间的字符串形式  yyyyMMddHHmmss 格式的
     * @return 返回当前时间字符串
     */
    public static String nowStr(){
        return transferLocalDateTimeToStr(LocalDateTime.now(),BASE_PATTERN_DATE_TIME);
    }
    /**
     * 得到当前时间的字符串形式
     * @param pattern 日期格式
     * @return 返回当前时间字符串
     */
    public static String nowStr(String pattern){
        return transferLocalDateTimeToStr(LocalDateTime.now(),pattern);
    }
    /**
     * 得到当前时间的字符串形式
     * @param pattern 日期格式
     * @return 返回当前时间字符串
     */
    /**
     * 将带有时间格式的
     * @param dateStr 日期字符串
     * @param source 转化前格式，源格式
     * @param target 转化后格式，目标格式
     */
    public static String transferDateTimePattern(String dateStr,String source,String target){
        LocalDateTime parse = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(source));
        return transferLocalDateTimeToStr(parse,target);
    }

    /**
     * 得到两个时间的时间差，以天为单位 精确到毫秒
     * @param start 开始时间
     * @param end 结束时间
     * @return 返回两个时间的的时间差
     */
    public static long getDateDiffByDay(Date start , Date end){
        return getDateDiff(start, end, ChronoUnit.DAYS);
    }

    /**
     * 得到两个时间的时间差，以月为单位 精确到毫秒
     * @param start 开始时间
     * @param end 结束时间
     * @return 返回两个时间的的时间差
     */
    public static long getDateDiffByMonth(Date start , Date end){
        return getDateDiff(start, end, ChronoUnit.MONTHS);
    }

    /**
     * 得到两个时间的时间差，以年为单位 精确到月 可计算年龄
     * @param start 开始时间
     * @param end 结束时间
     * @return 返回两个时间的的时间差
     */
    public static long getDateDiffAccurateMonthByYear(Date start , Date end){
        LocalDateTime startDate=transferDateToLocalDateTime(start);
        LocalDateTime endDate=transferDateToLocalDateTime(end);
        return ChronoUnit.YEARS.between(startDate.withDayOfMonth(1),endDate.withDayOfMonth(1));
    }

    /**
     * 得到两个时间的时间差，以年为单位 精确到毫秒
     * @param start 开始时间
     * @param end 结束时间
     * @return 返回两个时间的的时间差
     */
    public static long getDateDiffByYear(Date start , Date end){
        return getDateDiff(start, end, ChronoUnit.YEARS);
    }


    /**
     * 得到两个时间的时间差，可自由决定计算单位 精确到毫秒
     * @param start 开始时间
     * @param end 结束时间
     * @param unit 日期计算单位 可以已传入 ChronoUnit ,从年到纳秒的时间维度都有
     * @return 返回两个时间的的时间差
     */
    public static long getDateDiff(Date start , Date end, TemporalUnit unit){
        LocalDateTime startDate=transferDateToLocalDateTime(start);
        LocalDateTime endDate=transferDateToLocalDateTime(end);
        return unit.between(startDate, endDate);
    }

    /**
     * 将date对象转化为LocalDateTime对象
     * @param date 需要转化的date对象
     * @return 返回转化后的LocalDateTime对象
     */
    public static LocalDateTime transferDateToLocalDateTime(Date date){
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 将LocalDateTime对象转化为Date对象
     * @param localDateTime 需要转化的localDateTime对象
     * @return 返回转化后的Date对象
     */
    public static Date transferLocalDateTimeToDate(LocalDateTime localDateTime){
        return Date.from( localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将LocalDateTime对象转化为Date对象
     * @param localDateTime 需要转化的localDateTime对象
     * @return 返回转化后的Date对象
     */
    public static String  transferLocalDateTimeToStr(LocalDateTime localDateTime,String pattern){
        DateTimeFormatter df=DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(df);
    }

    /**
     * 将LocalDate对象转化为Date对象
     * @param localDate 需要转化的localDate对象
     * @return 返回转化后的Date对象
     */
    public static Date transferLocalDateToDate(LocalDate localDate){
        return Date.from( localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }



    /**
     * 将date对象转化为str对象
     * @param date 需要转化的date对象
     * @param pattern 需要转化的格式
     * @return 返回转化后的Date对象
     */
    public static String transferDateToStr(Date date,String pattern){
        LocalDateTime localDateTime=transferDateToLocalDateTime(date);
        return transferLocalDateTimeToStr(localDateTime, pattern);
    }

    /**
     * 将LocalDate对象转化为str对象
     * @param localDate 需要转化的localDate对象
     * @param pattern 需要转化的格式
     * @return 返回转化后的Date对象
     */
    public static String transferLocalDateToStr(LocalDate localDate,String pattern){
        DateTimeFormatter df=DateTimeFormatter.ofPattern(pattern);
        return localDate.format(df);
    }



    /**
     * 将Date对象转化为LocalDate对象
     * @param date 需要转化的Date对象
     * @return 返回转化后的Date对象
     */
    public static LocalDate transferDateToLocalDate(Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    /**
     * 将LocalDate对象转化为str对象
     * @param dateStr  日期字符串
     * @param pattern 日期字符串的格式
     * @return 返回转化后的Date对象
     */
    public static LocalDateTime transferStrToDateTime(String dateStr,String pattern){
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将LocalDate对象转化为str对象
     * @param dateStr  日期字符串
     * @param pattern 日期字符串的格式
     * @return 返回转化后的Date对象
     */
    public static LocalDate transferStrToDate(String dateStr,String pattern){
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将LocalDate对象转化为str对象
     * @param dateStr  日期字符串
     * @param pattern 日期字符串的格式
     * @return 返回转化后的Date对象
     */
    public static Long transferStrToTimeStamp(String dateStr,String pattern){
        return transferLocalDateTimeToTimeStamp(LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern)));
    }

    /**
     * 将LocalDate对象转化为str对象
     * @param localDateTime  日期
     * @return 返回转化后的Date对象
     */
    public static Long transferLocalDateTimeToTimeStamp(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 得到当前传入时间的 最后一秒
     * @param date 需要计算的日期
     * @return 返回传入日期对象当天最后一秒的日期
     */
    public static Date getEndTime(Date date){
        LocalDateTime localDateTime=transferDateToLocalDateTime(date);
        LocalDateTime zeroTime= LocalDateTime.of( localDateTime.toLocalDate(), LocalTime.MIN);
        LocalDateTime endTime=zeroTime.plus(1,ChronoUnit.DAYS).minus(1,ChronoUnit.SECONDS);
        return transferLocalDateTimeToDate(endTime);
    }


    /**
     * 得到当前传入时间的 0 点
     * @param date 需要计算的日期对象
     * @return 返回传入日期对象当天的0点日期
     */
    public static Date getZeroTime(Date date){
        LocalDateTime localDateTime=transferDateToLocalDateTime(date);
        return transferLocalDateTimeToDate(LocalDateTime.of( localDateTime.toLocalDate(), LocalTime.MIN));
    }

    /**
     * 日期添加一定量的时间  单位为年
     * @param date 需要改变的日期
     * @param num 需要添加的年份数
     * @return 返回改变后的时间
     */
    public static Date dateAddByYear(Date date,int num){
        LocalDateTime localDateTime=transferDateToLocalDateTime(date);
        return transferLocalDateTimeToDate(localDateTime.plus(num,ChronoUnit.YEARS));
    }

    /**
     * 日期添加一定量的时间  单位为月
     * @param date 需要改变的日期
     * @param num 需要添加的年份数
     * @return 返回改变后的时间
     */
    public static Date dateAddByMonth(Date date,int num){
        LocalDateTime localDateTime=transferDateToLocalDateTime(date);
        return transferLocalDateTimeToDate(localDateTime.plus(num,ChronoUnit.MONTHS));
    }

    /**
     * 日期添加一定量的时间  单位为天
     * @param date 需要改变的日期
     * @param num 需要添加的天数
     * @return 返回改变后的时间
     */
    public static Date dateAddByDay(Date date,int num){
        LocalDateTime localDateTime=transferDateToLocalDateTime(date);
        return transferLocalDateTimeToDate(localDateTime.plus(num,ChronoUnit.DAYS));
    }


    /**
     * 日期添加一定量的时间 时分秒年月日都行
     * @param date 需要改变的日期
     * @param num 改变的数量
     * @param unit 改变的单位 可传入  ChronoUnit枚举
     * @return 返回改变后的时间
     */
    public static Date dateAdd(Date date,int num, TemporalUnit unit){
        LocalDateTime localDateTime=transferDateToLocalDateTime(date);
        return transferLocalDateTimeToDate(localDateTime.plus(num,unit));
    }


//
//
//    public static long getDaysAfterHourSetTime(long time, int timeAdd, String jrs, String gzrs, String workHourStart, String workHourEnd, String noonBreakStart, String noonBreakEnd) {
//
//        LocalDateTime startTime=Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
//        LocalDateTime current=Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
//
//        LocalTime workHourStartTime=LocalTime.parse(workHourStart,DateTimeFormatter.ofPattern("HH:mm:ss"));
//        LocalTime workHourEndTime=LocalTime.parse(workHourEnd,DateTimeFormatter.ofPattern("HH:mm:ss"));
//        LocalTime noonBreakStartTime=LocalTime.parse(noonBreakStart,DateTimeFormatter.ofPattern("HH:mm:ss"));
//        LocalTime noonBreakEndTime=LocalTime.parse(noonBreakEnd,DateTimeFormatter.ofPattern("HH:mm:ss"));
//
//        while (true){
//            // 如果当前时间是下午下班时间或者当前日期是节假日，则日期加1 但是时间设置为上班时间
//            if (current.toLocalTime().compareTo(workHourEndTime)>0||checkHoliday(current, jrs, gzrs)) {
//                current=current.plus(1,ChronoUnit.DAYS)
//                        .withHour(workHourStartTime.getHour())
//                        .withMinute(workHourStartTime.getMinute())
//                        .withSecond(workHourStartTime.getSecond())
//                        .withNano(0);
//                continue;
//            }
//
//            if(current.toLocalTime().compareTo(workHourStartTime)<0){ // 如果当前时间在上班时间之前，设置当前实际为上班时间
//                current=current.withHour(workHourStartTime.getHour())
//                        .withMinute(workHourStartTime.getMinute())
//                        .withSecond(workHourStartTime.getSecond())
//                        .withNano(0);
//            }
//
//            if(current.toLocalTime().compareTo(workHourStartTime)<0){ // 如果当前时间在午休时间之间，设置当前时间为下午上班时间
//                current=current.withHour(noonBreakStartTime.getHour())
//                        .withMinute(noonBreakStartTime.getMinute())
//                        .withSecond(noonBreakStartTime.getSecond())
//                        .withNano(0);
//            }
//
//
//
//
//        }
//            Date current = date;
//            //指定时间  1小时  单位小时 ,自己根据需求修正
//            int zdSec = hour * 60 * 60;
//            SimpleDateFormat hm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            SimpleDateFormat hm6 = new SimpleDateFormat("yyyy-MM-dd");
//
//            while (true) {
//                String startWork = hm6.format(current) + " " + workHoursStart;
//                String lunchBreak = hm6.format(current) + " " + noonBreakStart;
//                String workGoOn = hm6.format(current) + " " + noonBreakEnd;
//                String offDuty = hm6.format(current) + " " + workHoursEnd;
//                //今天是否为节假日
//                if (checkHoliday(current, jrs, gzrs)) {
//                    //是节假日
//                    String sw = hm6.format(current) + " " + workHoursStart;
//                    current = getTomorrow(hm.parse(sw));
//                } else {
//                    if (zdSec <= 0) {
//                        break;
//                    }
//                    //如果当前时间小于 早上上班时间  把时间初始化到九点半
//                    if (current.getTime() < hm.parse(startWork).getTime()) {
//                        current = hm.parse(startWork);
//                        continue;
//                    }
//                    //如果时间在上午上班内 就用当前时间 到上午下班时间减掉 是否大于有效时间
//                    //如果不大于有效时间就直接九点半加上有效时间 如果大于有限时间 就减掉有效时间
//                    //设置当前时间为下午上班时间
//                    if (hm.parse(startWork).getTime() <= current.getTime() && current.getTime()
//                            <= hm.parse(lunchBreak).getTime()) {
//                        TimeDifference datePoor = getDatePoor(current, hm.parse(lunchBreak));
//                        if (datePoor.getTotalSec() >= zdSec) {
//                            current = addHour(zdSec, current, 2);
//                            break;
//                        } else {
//                            zdSec = zdSec - datePoor.getTotalSec().intValue();
//                            current = hm.parse(workGoOn);
//                            continue;
//                        }
//                    }
//                    if (hm.parse(lunchBreak).getTime() < current.getTime() && current.getTime()
//                            < hm.parse(workGoOn).getTime()) {
//                        //如果时间在午休时候  就让时间变成下午起始时间
//                        current = hm.parse(workGoOn);
//                    }
//                    //如果时间在下午上班时间 就用当前时间 到下午下班时间减掉有效时间
//                    if (hm.parse(workGoOn).getTime() <= current.getTime() && current.getTime()
//                            <= hm.parse(offDuty).getTime()) {
//                        TimeDifference datePoor = getDatePoor(current, hm.parse(offDuty));
//                        if (datePoor.getTotalSec() >= zdSec) {
//                            current = addHour(zdSec, current, 2);
//                            break;
//                        } else {
//                            zdSec = zdSec - datePoor.getTotalSec().intValue();
//                            current = getTomorrow(hm.parse(startWork));
//                            continue;
//                        }
//                    }
//                    //如果时间在下午下班时候 就跳过再次进入循环
//                    if (current.getTime() > hm.parse(offDuty).getTime()) {
//                        current = getTomorrow(hm.parse(startWork));
//                    }
//                }
//            }
//            return current.getTime();
//
//    }


//
//    /**
//     * 入参为空 和 不是假期返回 false, 是假期返回 true
//     * @param today
//     * @param jrs
//     * @param gzrs
//     * @return
//     */
//    private static boolean checkHoliday(LocalDateTime today, String jrs, String gzrs) {
//        if(StringHelper.isEmpty(jrs) || StringHelper.isEmpty(gzrs)) {
//            throw  new NullPointerException("节假日或工作日为空");
//        }
//
//        String dateStr=transferLocalDateTimeToStr(today,"yyyyMMdd");
//        if(DayOfWeek.SUNDAY==today.getDayOfWeek()||DayOfWeek.SATURDAY==today.getDayOfWeek()){
//            return !gzrs.contains(dateStr);
//        }else{
//            return jrs.contains(dateStr);
//        }
//    }
//
//    /**
//     * 获取明天的日期
//     *
//     * @param date
//     * @return
//     */
//    public static Date getTomorrow(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DAY_OF_MONTH, +1);
//        date = calendar.getTime();
//        return date;
//    }


}
