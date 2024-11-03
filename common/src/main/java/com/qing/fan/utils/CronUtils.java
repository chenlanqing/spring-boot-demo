package com.qing.fan.utils;

import com.google.common.collect.Lists;
import com.qing.fan.cron.CronExpression;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author QingFan
 * @version 1.0.0
 * @date 2022年09月25日 00:27
 */
public class CronUtils {

	/**
	 * 根据cron表达式，列出接下来执行的时间
	 *
	 * @param expression cron表达式
	 * @return 数据集合
	 */
	public static List<String> calculateCronExpression(String expression) {
		List<String> result = Lists.newArrayList();
		try {
			CronExpression cronExpression = new CronExpression(expression);
			Date time = new Date();
			for (int i = 0; i < 600; i++) {
				Date nextValidTime = cronExpression.getNextValidTimeAfter(time);
				if (nextValidTime == null) {
					break;
				}
				result.add(DateFormatUtils.format(nextValidTime.getTime(), "yyyy-MM-dd HH:mm:ss"));
				time = nextValidTime;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

}
