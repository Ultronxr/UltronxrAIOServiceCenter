package cn.ultronxr.common.util;

import cn.hutool.core.date.CalendarUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Ultronxr
 * @date 2022/12/15 22:23
 * @description
 */
public class DateTimeUtils {

    public static final String STANDARD_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String UTC_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS Z";

    public static final SimpleDateFormat STANDARD_FORMAT = new SimpleDateFormat(STANDARD_FORMAT_PATTERN);

    public static final SimpleDateFormat UTC_FORMAT = new SimpleDateFormat(UTC_FORMAT_PATTERN);


    /**
     * 把Calendar日期时间根据指定格式进行格式化
     *
     * @param calendar      原 {@code Calendar} 对象，
     *                      如果为null则获取当前时间的Calendar实例
     * @param formatPattern 格式化格式
     *                      如果为null或为空则使用 {@link #STANDARD_FORMAT_PATTERN}
     * @return 格式化后的日期时间
     */
    public static String getFormattedCalendar(@Nullable Calendar calendar, @Nullable String formatPattern) {
        calendar = ifNullGetInstance(calendar);
        if(StringUtils.isEmpty(formatPattern) || formatPattern.equals(STANDARD_FORMAT_PATTERN)){
            return STANDARD_FORMAT.format(calendar.getTime());
        }
        return new SimpleDateFormat(formatPattern).format(calendar.getTime());
    }

    /**
     * 把指定格式的日期时间字符串转为Calendar
     *
     * @param dateTimeString 待转化为Calendar的日期时间字符串
     * @param formatPattern  格式化格式，如果为null或为空则使用 {@link #STANDARD_FORMAT_PATTERN}
     * @return 转化之后的Calendar
     */
    public static Calendar parseStringToCalendar(String dateTimeString, String formatPattern) {
        try {
            Date date = null;
            if(StringUtils.isEmpty(formatPattern) || formatPattern.equals(STANDARD_FORMAT_PATTERN)){
                date = STANDARD_FORMAT.parse(dateTimeString);
            } else {
                date = new SimpleDateFormat(formatPattern).parse(dateTimeString);
            }
            return CalendarUtil.calendar(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 计算Calendar对象加减 x毫秒 之后的时间
     *
     * @param calendar     原 {@code Calendar} 对象，
     *                     如果为null则以当前时间的Calendar实例为基础进行计算
     * @param milliseconds 加/减x毫秒数，正数加，负数减
     * @return 加/减x毫秒之后的时间
     */
    public static Calendar calculateMilliseconds(@Nullable Calendar calendar, long milliseconds) {
        calendar = ifNullGetInstance(calendar);
        return CalendarUtil.calendar(calendar.getTimeInMillis() + milliseconds);
    }

    /**
     * 计算Calendar对象加减 x天 之后的时间
     *
     * @param calendar 原 {@code Calendar} 对象，
     *                 如果为null则以当前时间的Calendar实例为基础进行计算
     * @param day      加/减x天数，正数加，负数减
     * @return 加/减x天之后的时间
     */
    public static Calendar calculateDay(@Nullable Calendar calendar, int day) {
        calendar = ifNullGetInstance(calendar);
        calendar.add(Calendar.DATE, day);
        return calendar;
    }

    /**
     * 计算Calendar对象加减 x星期（周） 之后的时间
     *
     * @param calendar 原 {@code Calendar} 对象，
     *                 如果为null则以当前时间的Calendar实例为基础进行计算
     * @param week     加/减x星期（周），正数加，负数减
     * @return 加/减x星期（周）之后的时间
     */
    public static Calendar calculateWeek(@Nullable Calendar calendar, int week) {
        calendar = ifNullGetInstance(calendar);
        calendar.add(Calendar.WEEK_OF_YEAR, week);
        return calendar;
    }

    /**
     * 计算Calendar对象加减 x周 之后的 星期几 的时间
     * 星期日是按照“一周内星期一为第一天，星期日为第七天”的规则计算的，也就是说这里计算的星期日是Calendar中的下一个星期的星期日
     *
     * @param calendar          原 {@code Calendar} 对象，
     *                          如果为null则以当前时间的Calendar实例为基础进行计算
     * @param week              加/减x星期（周），正数加，负数减
     * @param calendarDayOfWeek 星期几
     *                          填入 {@link Calendar#MONDAY} {@link Calendar#TUESDAY} {@link Calendar#WEDNESDAY} ...
     * @return 加/减x星期（周）之后的星期几的时间
     */
    public static Calendar calculateWeekAndDayOfWeek(@Nullable Calendar calendar, int week, int calendarDayOfWeek) {
        if(calendarDayOfWeek == Calendar.SUNDAY) {
            ++week;
        }
        calendar = calculateWeek(calendar, week);
        calendar.set(Calendar.DAY_OF_WEEK, calendarDayOfWeek);
        return calendar;
    }

    /**
     * 计算Calendar对象加减 x月 之后的时间
     *
     * @param calendar 原 {@code Calendar} 对象，
     *                 如果为null则以当前时间的Calendar实例为基础进行计算
     * @param month    加/减x月，正数加，负数减
     * @return 加/减x月之后的时间
     */
    public static Calendar calculateMonth(@Nullable Calendar calendar, int month) {
        calendar = ifNullGetInstance(calendar);
        calendar.add(Calendar.MONTH, month);
        return calendar;
    }

    /**
     * 计算Calendar对象加减 x月 之后的 第几日 的时间
     * 第几日可填1-31，返回第几日的结果总是在这个月内，即使超出这个月的范围也只会返回这个月的第一日或者最后一日
     *
     * @param calendar   原 {@code Calendar} 对象，
     *                   如果为null则以当前时间的Calendar实例为基础进行计算
     * @param month      加/减x月，正数加，负数减
     * @param dayOfMonth 月份中的第几日（可填1-31）
     * @return 加/减x月之后的第几日时间
     */
    public static Calendar calculateMonthAndDayOfMonth(@Nullable Calendar calendar, int month, int dayOfMonth) {
        calendar = calculateMonth(calendar, month);
        dayOfMonth = Math.min(dayOfMonth, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        dayOfMonth = Math.max(dayOfMonth, 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return calendar;
    }

    /**
     * 计算Calendar对象加减 x年 之后的时间
     *
     * @param calendar 原 {@code Calendar} 对象，
     *                 如果为null则以当前时间的Calendar实例为基础进行计算
     * @param year     加/减x年，正数加，负数减
     * @return 加/减x年之后的时间
     */
    public static Calendar calculateYear(@Nullable Calendar calendar, int year) {
        calendar = ifNullGetInstance(calendar);
        calendar.add(Calendar.YEAR, year);
        return calendar;
    }


    /**
     * 如果calendar为null，返回当前时间的Calendar实例
     */
    public static Calendar ifNullGetInstance(Calendar calendar) {
        return calendar == null ? Calendar.getInstance() : calendar;
    }


    /**
     * 修改Calendar对象中的当天时间
     *
     * @param calendar    修改calendar对象
     * @param hourOfDay   小时，24小时制
     * @param minute      分钟
     * @param second      秒
     * @param milliSecond 毫秒
     */
    public static void setCalendarTime(Calendar calendar, Integer hourOfDay, Integer minute, Integer second, Integer milliSecond){
        if(null == calendar){
            return;
        }
        if(null != hourOfDay && hourOfDay >= 0 && hourOfDay <= 24){
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        }
        if(null != minute && minute >= 0 && minute <= 59){
            calendar.set(Calendar.MINUTE, minute);
        }
        if(null != second && second >= 0 && second <= 59){
            calendar.set(Calendar.SECOND, second);
        }
        if(null != milliSecond && milliSecond >= 0 && milliSecond <= 1000){
            calendar.set(Calendar.MILLISECOND, milliSecond);
        }
    }

    /**
     * 检查 一个时间 处于其当天的哪个 整点时间段
     *
     * 时间段编号：
     *   00:00 - xx:00 - xx:00 - xx:00 - ... - 24:00
     *         0       1       2         ...
     *
     * @param calendar      待检查的时间
     * @param increaseHours 递增的整点小时节点（24小时制，取值区间 [1,23] ）
     *                      0时和24时已经隐含地自动检查了，无需在数组首尾填写！
     * @return 时间段编号
     */
    public static Integer checkTimeHourPeriod(Calendar calendar, int[] increaseHours){
        if(null == calendar || null == increaseHours || 0 == increaseHours.length){
            return -1;
        }
        Calendar calendarHour = CalendarUtil.calendar(calendar.getTime());
        for(int i = 0; i < increaseHours.length; i++){
            setCalendarTime(calendarHour, increaseHours[i], 0, 0, 0);
            if(calendar.before(calendarHour)){
                return i;
            }
        }
        return increaseHours.length;
    }

    /**
     * 检查指定日期是否是周末（包括周六和周日）
     *
     * @param calendar 待检查的日期
     * @return true-是周末，false-不是周末
     */
    public static Boolean isWeekend(Calendar calendar){
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    /**
     * 计算两个日期之间经过了多少天
     * 左开右闭
     *   例（假设同一年同一月）：
     *       1号-1号 经过了 零天
     *       1号-2号 经过了 2 一天
     *       1号-3号 经过了 2、3 两天
     *
     * @param calendarStart 起始日
     * @param calendarEnd   结束日
     * @return 经过的天数（一个自然数）
     */
    public static Integer daysBetweenCalendars(Calendar calendarStart, Calendar calendarEnd){
        if(calendarEnd.before(calendarStart)){
            Calendar temp = calendarStart;
            calendarStart = calendarEnd;
            calendarEnd = temp;
        }
        long daysGap = (calendarEnd.getTimeInMillis() - calendarStart.getTimeInMillis()) / (24*60*60*1000);
        return (int) daysGap;
    }

    /**
     * 计算两个日期之间经过了多少工作日（去掉周六和周日）
     * 左开右闭
     *   例（假设同一年同一月，且4、5是周六与周日）：
     *       1号-1号 经过了 零天
     *       1号-3号 经过了 2、3 两天
     *       1号-5号 经过了 2、3 两天
     *       1号-7号 经过了 2、3、6、7 四天
     *
     * @param calendarStart 起始日
     * @param calendarEnd   结束日
     * @return 经过的工作日数量（一个自然数）
     */
    public static Integer weekdaysBetweenCalendars(Calendar calendarStart, Calendar calendarEnd){
        if(calendarEnd.before(calendarStart)){
            Calendar temp = calendarStart;
            calendarStart = calendarEnd;
            calendarEnd = temp;
        }
        int daysGap = daysBetweenCalendars(calendarStart, calendarEnd);
        int dowStart = changeFirstDayOfWeek(calendarStart.get(Calendar.DAY_OF_WEEK)),
                dowEnd = changeFirstDayOfWeek(calendarEnd.get(Calendar.DAY_OF_WEEK));
        int weekdaysGap = (daysGap + dowStart + 7 - dowEnd) / 7 * 5 - Math.min(dowStart, 5) - (5- Math.min(dowEnd, 5));
        return weekdaysGap;
    }

    /**
     * 修改 Calendar.DAY_OF_WEEK 获取的一周中的第几天的计算方法
     * 把周一改成一周中的第一天，而不是周日是第一天
     *
     * @param dayOfWeek calendar.get(Calendar.DAY_OF_WEEK) 方法获取到的第几天数字
     * @return 修改后的一周中的第几天
     */
    public static Integer changeFirstDayOfWeek(Integer dayOfWeek){
        if (dayOfWeek == Calendar.SUNDAY) {
            return 7;
        } else {
            return dayOfWeek - 1;
        }
    }

    /**
     * UTC格式的时间字符串 转换为 北京时间（UTC+8）标准格式 字符串<br/>
     * 例：2022-01-01T16:00:00.000Z  ==>  2022-01-02 00:00:00
     *
     * @param utcDateTime UTC格式的时间字符串，包含 T、Z 标记
     * @return 北京时间（UTC+8）标准格式 字符串
     */
    public static String utcToUtc8WithStandardFormat(String utcDateTime) {
        utcDateTime = utcDateTime.replace("Z", " UTC");
        try {
            Date date = UTC_FORMAT.parse(utcDateTime);
            return STANDARD_FORMAT.format(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 检查一个时间点是否处于一段时间区间中（包含时间区间两端点）
     *
     * @param target      待检查的时间点
     * @param periodStart 时间区间开始
     * @param periodEnd   时间区间结束
     * @return true-处于；false-不处于
     */
    public static Boolean isTargetInCalendarPeriod(Calendar target, Calendar periodStart, Calendar periodEnd) {
        return CalendarUtil.compare(target, periodStart) >= 0 && CalendarUtil.compare(target, periodEnd) <= 0
                || CalendarUtil.compare(target, periodStart) <= 0 && CalendarUtil.compare(target, periodEnd) >= 0;
    }

    /**
     * 检查现在时间点是否处于一段时间区间中（包含时间区间两端点）
     *
     * @param periodStart 时间区间开始
     * @param periodEnd   时间区间结束
     * @return true-处于；false-不处于
     */
    public static Boolean isNowInCalendarPeriod(Calendar periodStart, Calendar periodEnd) {
        return isTargetInCalendarPeriod(Calendar.getInstance(), periodStart, periodEnd);
    }

}
