/**
 *DateTime.java 创建于 2009-12-8
 *Copyright (c) 2009-12-8 by 成都启迪信息技术有限公司
 *@作者	饶正勇
 *@版本	2.0
 */
package com.choncheng.maya.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

import com.choncheng.maya.MyApplication;

/**
 * 获取日期,时间类
 * 
 * @作者 饶正勇
 * @创建日期 2009-12-8
 * @版本 2.0
 */
public class DateTime {
	/**
	 * 获取当前系统时间(精确到秒)
	 * 
	 * @return nowDate 当前系统时间
	 * */
	public static String getDateTime() {
		// 时间转换器
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 当前时间
		Date date = new Date();
		// 当前时间转换
		String nowDate = format.format(date);
		return nowDate;
	}

	/**
	 * 获取当前系统时间(精确到秒)
	 * 
	 * @return nowDate 当前系统时间
	 * */
	public static String getE() {
		// 时间转换器
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		// 当前时间
		Date date = new Date();
		// 当前时间转换
		String nowDate = format.format(date);
		return nowDate;
	}

	/**
	 * 获取当前系统日期(精确到天)
	 * 
	 * @return nowDate 当前系统时间
	 * */
	public static String getDate() {
		// 时间转换器
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 当前时间
		Date date = new Date();
		// 当前时间转换
		String nowDate = format.format(date);
		return nowDate;
	}

	/**
	 * 获取当前系统日期时间(精确到分)
	 * 
	 * @return nowDate 当前系统时间
	 * */
	public static String getDateMinutes() {
		// 时间转换器
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 当前时间
		Date date = new Date();
		// 当前时间转换
		String nowDate = format.format(date);
		return nowDate;
	}
	/**
	 * 获取当前系统日期时间(精确到分)
	 * 
	 * @return nowDate 当前系统时间
	 * */
	public static String getDateMinutes(long time) {
		// 时间转换器
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 当前时间
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(time*1000);
		Date date = calendar.getTime();
		// 当前时间转换
		String nowDate = format.format(date);
		return nowDate;
	}
	
	/**
	 * 获取当前系统日期时间(只要时分)
	 * 
	 * @return nowDate 当前系统时间
	 * */
	public static String getDateOnlyMinutes(long time) {
		// 时间转换器
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		// 当前时间
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(time*1000);
		Date date = calendar.getTime();
		// 当前时间转换
		String nowDate = format.format(date);
		return nowDate;
	}
	
	/**
	 * 加一年后的时间 格式：yyyy-MM-dd
	 * 
	 * @return nextyear 当前系统时间加一年
	 * */
	public static String getDateAddOneYear() {
		String nextyear = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar next = Calendar.getInstance();
		next.add(Calendar.YEAR, 1);// 加一年
		nextyear = format.format(next.getTime());// 一年后的今天
		return nextyear;
	}

	/**
	 * 加一天的时间 格式：yyyy-MM-dd
	 * */
	public static String getDateAddOneDay() {
		String nextday = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, 1);// 加一天
		return nextday = format.format(day.getTime());
	}

	/**
	 * 减一天的时间 格式：yyyy-MM-dd
	 * */
	public static String getDateCutOneDay() {
		String nextday = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, -1);// 减一天
		return nextday = format.format(day.getTime());
	}

	/**
	 * 减2天的时间 格式：yyyy-MM-dd
	 * */
	public static String getDateCutTwoDay() {
		String nextday = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, -2);// 减2天
		return nextday = format.format(day.getTime());
	}

	/**
	 * 减7天的时间 格式：yyyy-MM-dd
	 * */
	public static String getDateCutSevenDay() {
		String nextday = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, -7);// 减7天
		return format.format(day.getTime());
	}

	/**
	 * 加一月后的时间 格式：yyyy-MM-dd
	 * 
	 * */
	public static String getDateAddOneMonth() {
		String last_time = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 1);// 加一月
		return last_time = format.format(c.getTime());
	}

	/**
	 * 加N秒后的时间
	 * 
	 * @param second
	 *            要加的秒数
	 * @return String 加N秒后的时间
	 */
	public static String getDateAddSecond(int second) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.SECOND, second);// 要加的秒数
		return format.format(c.getTime());
	}

	/**
	 * 减N分后的时间
	 * 
	 * @param minute
	 *            减N的分钟
	 * @return String 减N后的时间
	 */
	public static String getDateCutMinute(int minute) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -minute);// 要减的分钟
		return format.format(c.getTime());
	}

	/**
	 * 时间比较(精确到秒钟)
	 * 
	 * @param time1
	 *            时间1
	 * @param time2
	 *            时间2
	 * @param addMM
	 *            时间2要加的分钟数
	 * @return boolean 比较结果
	 */
	public static boolean isTimeComparisonSeconds(String time1, String time2,
			int addMM) {
		SimpleDateFormat lFormat;
		SimpleDateFormat lFormat1;
		Date date1 = new Date();
		Date date2 = new Date();
		boolean flag = false;
		lFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			date1 = lFormat.parse(time1);
			date2 = lFormat.parse(time2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date2 = new Date(date2.getTime() + addMM * 60 * 1000);
		String gRtnStr1 = lFormat1.format(date1);
		String gRtnStr2 = lFormat1.format(date2);
		if (Double.parseDouble(gRtnStr1) <= Double.parseDouble(gRtnStr2)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 时间比较(精确到分钟)
	 * 
	 * @param time1
	 *            时间1
	 * @param time2
	 *            时间2
	 * @param addMM
	 *            时间2要加的分钟数
	 * @return boolean 比较结果
	 */
	public static boolean isTimeComparisonMinutes(String time1, String time2,
			int addMM) {
		SimpleDateFormat lFormat;
		SimpleDateFormat lFormat1;
		Date date1 = new Date();
		Date date2 = new Date();
		boolean flag = false;
		lFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		lFormat1 = new SimpleDateFormat("yyyyMMddHHmm");
		try {
			date1 = lFormat.parse(time1);
			date2 = lFormat.parse(time2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date2 = new Date(date2.getTime() + addMM * 60 * 1000);
		String gRtnStr1 = lFormat1.format(date1);
		String gRtnStr2 = lFormat1.format(date2);
		if (Double.parseDouble(gRtnStr1) <= Double.parseDouble(gRtnStr2)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 时间比较(精确到秒钟)
	 * 
	 * @param time1
	 *            时间1
	 * @param time2
	 *            时间2
	 * @return boolean 比较结果
	 */
	public static boolean isTimeComparison(String time1, String time2) {
		SimpleDateFormat lFormat;
		SimpleDateFormat lFormat1;
		Date date1 = new Date();
		Date date2 = new Date();
		boolean flag = false;
		lFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		lFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			date1 = lFormat.parse(time1);
			date2 = lFormat.parse(time2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String gRtnStr1 = lFormat1.format(date1);
		String gRtnStr2 = lFormat1.format(date2);
		if (Double.parseDouble(gRtnStr1) <= Double.parseDouble(gRtnStr2)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 时间比较(精确到天)
	 * 
	 * @param time1
	 *            时间1
	 * @param time2
	 *            时间2
	 */
	public static boolean isTimeComparisonDay(String time1, String time2) {
		SimpleDateFormat lFormat;
		SimpleDateFormat lFormat1;
		Date date1 = new Date();
		Date date2 = new Date();
		boolean flag = false;
		lFormat = new SimpleDateFormat("yyyy-MM-dd");
		lFormat1 = new SimpleDateFormat("yyyyMMdd");
		try {
			date1 = lFormat.parse(time1);
			date2 = lFormat.parse(time2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date2 = new Date(date2.getTime());
		String gRtnStr1 = lFormat1.format(date1);
		String gRtnStr2 = lFormat1.format(date2);
		if (Double.parseDouble(gRtnStr1) < Double.parseDouble(gRtnStr2)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 时间加减(精确到分钟)
	 * 
	 * @param time
	 *            时间
	 * @param minutes
	 *            要添加或减少的分钟数
	 * @param tag
	 *            加减标识(0为添加时间,1为减少时间)
	 * @return String 添加或减少之后的时间
	 */
	public static String getAddSubtractTime(String time, int minutes, int tag) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (tag == 0) {
			date = new Date(date.getTime() + minutes * 60 * 1000);
		} else if (tag == 1) {
			date = new Date(date.getTime() - minutes * 60 * 1000);
		}
		String newDate = sdf.format(date);
		return newDate;
	}

	/**
	 * 时间加减(精确到秒钟)
	 * 
	 * @param time
	 *            时间
	 * @param minutes
	 *            要添加或减少的分钟数
	 * @param tag
	 *            加减标识(0为添加时间,1为减少时间)
	 * @return String 添加或减少之后的时间
	 */
	public static String getAddSubtractTimeSeconds(String time, int minutes,
			int tag) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (tag == 0) {
			date = new Date(date.getTime() + minutes * 60 * 1000);
		} else if (tag == 1) {
			date = new Date(date.getTime() - minutes * 60 * 1000);
		}
		String newDate = sdf1.format(date);
		return newDate;
	}

	/**
	 * 计算2个时间差
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long badTimeComparison(String starttime, String nowtime) {
		SimpleDateFormat lFormat;
		Date startdate = null;
		Date nowdate = null;
		long flag = 0L;
		lFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			startdate = lFormat.parse(starttime);
			nowdate = lFormat.parse(nowtime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		flag = nowdate.getTime() - startdate.getTime();
		return flag;
	}

	/**
	 * 获取当前系统时间(精确到秒)
	 * 
	 * @return nowDate 当前系统时间
	 * */
	public static String getSimpleDateTime() {
		// 时间转换器
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		// 当前时间
		Date date = new Date();
		// 当前时间转换
		String nowDate = format.format(date);
		return nowDate;
	}

	public static boolean comparisonDate(String date) throws ParseException {
		java.util.Date nowdate = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date d = sdf.parse(date);

		boolean flag = d.before(nowdate);
		if (flag)
			System.out.print("早于今天");
		else
			System.out.print("晚于今天");
		return flag;
	}

	public static int getHour() {
		return new Date().getHours();
	}

	/**
	 * 日期转发为时间搓
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static long getTimeStemp(String str) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		long timeStemp = 0;
		try {
			date = simpleDateFormat.parse(str);
			timeStemp = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeStemp / 1000;
	}
	/**
	 * 日期转发为时间搓
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static long getTimeStemp2(String str) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date;
		long timeStemp = 0;
		try {
			date = simpleDateFormat.parse(str);
			timeStemp = date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeStemp / 1000;
	}

	/**
	 * 时间搓转为日期
	 * 
	 * @param stemp
	 * @return
	 */
	public static String getDatebyStemp(String stemp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sd = format.format(new Date(Long.parseLong(stemp) * 1000));
		return sd;
	}

	/**
	 * 时间搓转为日期
	 * 
	 * @param stemp
	 * @return
	 */
	public static String getDateAndSecondbyStemp(String stemp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sd = format.format(new Date(Long.parseLong(stemp) * 1000));
		return sd;
	}

	/**
	 * 聊天时间转换
	 * 
	 * @param timesamp
	 * @return
	 */
	public static String getChatTime(long timesamp) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date today = new Date(System.currentTimeMillis());
		Date otherDay = new Date(timesamp);
		int temp = Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));

		switch (temp) {
		case 0:
			result = "今天 " + getHourAndMin(timesamp);
			break;
		case 1:
			result = "昨天 " + getHourAndMin(timesamp);
			break;
		case 2:
			result = "前天 " + getHourAndMin(timesamp);
			break;

		default:
			// result = temp + "天前 ";
			result = getTime(timesamp);
			break;
		}

		return result;
	}

	public static String getHourAndMin(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(new Date(time));
	}

	public static String getTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
		return format.format(new Date(time));
	}

	/**
	 * 获取营业时间
	 * 
	 * @return
	 */
	public static String getBusinessHour() {
		long beginTime = MyApplication.getInstance().getBeginTime();
		long endTime = MyApplication.getInstance().getEndTime();
		long begin_hour = beginTime / 3600;
		long begin_minute = (beginTime - begin_hour * 3600) / 60;

		long end_hour = endTime / 3600;
		long end_minute = (endTime - end_hour * 3600) / 60;

		if (begin_hour < 10) {

		}
		String beginTimeMinute = begin_hour + ":" + begin_minute;
		if (begin_hour < 10) {
			beginTimeMinute = "0" + begin_hour + ":" + begin_minute;
		}
		if (begin_minute < 10) {
			beginTimeMinute = begin_hour + ":" + "0" + begin_minute;
		}

		if (begin_hour < 10 && begin_minute < 10) {
			beginTimeMinute = "0" + begin_hour + ":" + "0" + begin_minute;
		}
		String endTimeNimute = end_hour + ":" + end_minute;
		if (end_hour < 10) {
			endTimeNimute = "0" + end_hour + ":" + end_minute;
		}
		if (end_minute < 10) {
			endTimeNimute = end_hour + ":" + "0" + end_minute;
		}
		if (end_hour < 10 && end_minute < 10) {
			endTimeNimute = "0" + end_hour + ":" + "0" + end_minute;
		}
		return beginTimeMinute + "-" + endTimeNimute;
	}

	/**
	 * 获取当前时间的秒数
	 * 
	 * @return
	 */
	public static long getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		return hour * 3600 + minute * 60;
	}
}
