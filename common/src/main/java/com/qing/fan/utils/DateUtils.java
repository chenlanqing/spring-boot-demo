package com.qing.fan.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author QingFan 2022/6/30
 * @version 1.0.0
 */
public final class DateUtils {
    private DateUtils() {
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * 使用 yyyy-MM-dd HH:mm:ss 格式化
     *
     * @param date 日期
     * @return yyyy-MM-dd HH:mm:ss 格式化的日期
     */
    public static String formatDate(Date date) {
        return formatDate(date, DateFormatConstant.COMMON);
    }

    /**
     * 使用 yyyy-MM-dd HH:mm:ss 格式化
     *
     * @param localDateTime 日期时间
     * @return yyyy-MM-dd HH:mm:ss 格式化的日期时间
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return formatLocalDateTime(localDateTime, DateFormatConstant.COMMON);
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
    }

    /**
     * 使用 yyyy-MM-dd 格式化
     *
     * @param localDate 日期
     * @return yyyy-MM-dd 格式化的
     */
    public static String formatLocalDate(LocalDate localDate) {
        return formatLocalDate(localDate, DateFormatConstant.COMMON);
    }

    public static String formatLocalDate(LocalDate localDate, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(localDate);
    }

    public static LocalDate parseToLocalDate(String date, String pattern) {
        // 也可以使用如下写法
        DateTimeFormatter.ofPattern(pattern).parse(date);
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate parseToLocalDate(String date) {
        return parseToLocalDate(date, DateFormatConstant.YYYY_MM_DD);
    }

    public static LocalDateTime parseToLocalDateTime(String date) {
        return parseToLocalDateTime(date, DateFormatConstant.COMMON);
    }

    public static LocalDateTime parseToLocalDateTime(String date, String pattern) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将 RFC3339日期格式转换为本地 LocalDateTime，比如：2022-04-28T23:00:00Z，
     * 那么对应北京时间： 2022-04-29T07:00:00.000+08:00
     *
     * @param rfcDate 日期格式
     * @return 默认时区时间
     */
    public static LocalDateTime parseToLocalFromRFCDate(String rfcDate) {
        Instant instant = Instant.parse(rfcDate);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        return zonedDateTime.toLocalDateTime();
    }

    private static ZonedDateTime dateToZonedDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault());
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return dateToZonedDateTime(date).toLocalDateTime();
    }

    public static LocalDate dateToLocalDate(Date date) {
        return dateToZonedDateTime(date).toLocalDate();
    }

    public static LocalTime dateToLocalTime(Date date) {
        return dateToZonedDateTime(date).toLocalTime();
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前时间是星期几
     *
     * @return 星期几（数字）: 1-星期一...7-星期日
     * @see DayOfWeek
     */
    public static int dayOfWeek() {
        return dayOfWeek(LocalDate.now());
    }

    public static int dayOfWeek(String dateStr) {
        LocalDate localDate = parseToLocalDate(dateStr);
        return dayOfWeek(localDate);
    }

    public static int dayOfWeek(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek.get(ChronoField.DAY_OF_WEEK);
    }

    /**
     * 列出两个日期之间的所有日期列表，格式：yyyy-MM-dd
     *
     * @param startInclusive 开始时间，包含
     * @param endInclusive   结束时间，包含
     * @return 日期列表，ArrayList
     */
    public static List<String> listBetweenDates(String startInclusive, String endInclusive) {
        LocalDate startDate = parseToLocalDate(startInclusive);
        LocalDate endDate = parseToLocalDate(endInclusive);
        if (endDate.isBefore(startDate)) {
            return new ArrayList<>();
        }
        Period period = Period.between(startDate, endDate);
        List<String> list = new ArrayList<>(period.getDays() + 1);
        list.add(startInclusive);
        while (!startDate.isEqual(endDate)) {
            startDate = startDate.plusDays(1);
            list.add(formatLocalDate(startDate));
        }
        return list;
    }

    public static String utcDateToGMT8(Date date) {
        String dateStr = formatDate(date);
        LocalDateTime localDateTime = parseToLocalDateTime(dateStr);
        Instant instant = localDateTime.atZone(ZoneId.of("+0")).toInstant();
        LocalDateTime ofInstant = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return formatLocalDateTime(ofInstant);
    }

    public static String utcLocalDateTimeToGMT8(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.of("+0")).toInstant();
        LocalDateTime ofInstant = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return formatLocalDateTime(ofInstant);
    }

    public static int getYear(String dateTime) {
        return parseToLocalDate(dateTime).getYear();
    }

    public static LocalDate lastDayOfMonth(LocalDate localDate) {
        return localDate.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static boolean isWeekend(String date) {
        return isWeekend(parseToLocalDate(date));
    }

    public static boolean isWeekend(LocalDate localDate) {
        int dayOfWeek = dayOfWeek(localDate);
        return dayOfWeek == 6 || dayOfWeek == 7;
    }

    /**
     * 获取一个月的天数
     *
     * @param localDate 日期
     * @return 一个月天数
     */
    public static int daysOfMonth(LocalDate localDate) {
        return localDate.lengthOfMonth();
    }

    public static int daysOfMonth(String date) {
        return daysOfMonth(parseToLocalDate(date));
    }

    /**
     * @return the month-of-year, from 1 to 12
     */
    public static int getMonth(String dateTime) {
        return parseToLocalDate(dateTime).getMonthValue();
    }

    public static final class DateFormatConstant {
        private DateFormatConstant() {
        }

        public static final String COMMON = "yyyy-MM-dd HH:mm:ss";
        public static final String YYYY_MM_DD = "yyyy-MM-dd";
    }
}
