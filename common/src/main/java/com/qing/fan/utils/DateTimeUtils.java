package com.qing.fan.utils;

import com.qing.fan.contants.DateFormatConstant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author bluefish 2019-07-07
 * @version 1.0.0
 */
public class DateTimeUtils {

    /**
     * 时区：东八区
     */
    private static final String UTC_8_ZONE = "+8";

    /**
     * 格式化
     *
     * @param date   时间
     * @param format 格式化
     * @return 格式化的时间格式
     */
    public static String format(Date date, String format) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.of(UTC_8_ZONE)).format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 格式化当前时间
     *
     * @param format 格式化
     * @return 格式化的时间格式
     */
    public static String format(LocalDate date, String format) {
        return DateTimeFormatter.ofPattern(format).format(date);
    }

    /**
     * 格式化当前时间
     *
     * @param format 格式化
     * @return 格式化的时间格式
     */
    public static String formatNow(String format) {
        return DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
    }

    /**
     * 将字符串解析为时间
     *
     * @param dateStr 字符串格式
     * @param format  格式化格式
     * @return 字符串格式化后的时间
     */
    public static Date parseDateTime(String dateStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
        return Date.from(dateTime.toInstant(ZoneOffset.of(UTC_8_ZONE)));
    }

    /**
     * 将字符串解析为日期
     *
     * @param dateStr 字符串格式
     * @param format  格式化格式
     * @return 字符串格式化后的日期
     */
    public static Date parseDate(String dateStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate date = LocalDate.parse(dateStr, formatter);
        return Date.from(date.atStartOfDay(ZoneOffset.of(UTC_8_ZONE)).toInstant());
    }

    /**
     * 当前时间，单位秒
     *
     * @return int数据
     */
    public static Integer getCurrentTimeInt() {
        return Long.valueOf(System.currentTimeMillis() / 1000).intValue();
    }

    /**
     * 当前时间，单位秒
     *
     * @return long数据
     */
    public static Long getCurrentTimeLong() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取当前时间减去天数的日期
     *
     * @param subDay 减去的天数
     * @return
     */
    public static String getDateStrFromNowMinusDay(int subDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate now = LocalDate.now();
        if (subDay == 0) {
            return now.format(formatter);
        }
        LocalDate minusDays = now.minusDays(subDay);
        return minusDays.format(formatter);
    }

    public static void main(String[] args) {
        int threadNums = 100;

        ExecutorService pool = Executors.newFixedThreadPool(30);

        for (int i = 0; i < threadNums; i++) {
            pool.execute(() -> {
                System.out.println(formatNow(DateFormatConstant.YYYY_MM_DD_HH_MM_SS));
                System.out.println(format(parseDateTime("20190102222222", DateFormatConstant.YYYY_MM_DD_HH_MM_SS), DateFormatConstant.YYYY_MM_DD));
                System.out.println(format(parseDate("20190909", DateFormatConstant.YYYY_MM_DD), DateFormatConstant.YYYY_MM_DD_HH_MM_SS));
            });
        }

        pool.shutdown();
    }

}
