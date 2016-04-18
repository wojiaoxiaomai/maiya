/**
 *DateTime.java ������ 2009-12-8
 *Copyright (c) 2009-12-8 by �ɶ�������Ϣ�������޹�˾
 *@����	������
 *@�汾	2.0
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
 * ��ȡ����,ʱ����
 * 
 * @���� ������
 * @�������� 2009-12-8
 * @�汾 2.0
 */
public class DateTime {
	/**
	 * ��ȡ��ǰϵͳʱ��(��ȷ����)
	 * 
	 * @return nowDate ��ǰϵͳʱ��
	 * */
	public static String getDateTime() {
		// ʱ��ת����
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// ��ǰʱ��
		Date date = new Date();
		// ��ǰʱ��ת��
		String nowDate = format.format(date);
		return nowDate;
	}

	/**
	 * ��ȡ��ǰϵͳʱ��(��ȷ����)
	 * 
	 * @return nowDate ��ǰϵͳʱ��
	 * */
	public static String getE() {
		// ʱ��ת����
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		// ��ǰʱ��
		Date date = new Date();
		// ��ǰʱ��ת��
		String nowDate = format.format(date);
		return nowDate;
	}

	/**
	 * ��ȡ��ǰϵͳ����(��ȷ����)
	 * 
	 * @return nowDate ��ǰϵͳʱ��
	 * */
	public static String getDate() {
		// ʱ��ת����
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// ��ǰʱ��
		Date date = new Date();
		// ��ǰʱ��ת��
		String nowDate = format.format(date);
		return nowDate;
	}

	/**
	 * ��ȡ��ǰϵͳ����ʱ��(��ȷ����)
	 * 
	 * @return nowDate ��ǰϵͳʱ��
	 * */
	public static String getDateMinutes() {
		// ʱ��ת����
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// ��ǰʱ��
		Date date = new Date();
		// ��ǰʱ��ת��
		String nowDate = format.format(date);
		return nowDate;
	}
	/**
	 * ��ȡ��ǰϵͳ����ʱ��(��ȷ����)
	 * 
	 * @return nowDate ��ǰϵͳʱ��
	 * */
	public static String getDateMinutes(long time) {
		// ʱ��ת����
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// ��ǰʱ��
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(time*1000);
		Date date = calendar.getTime();
		// ��ǰʱ��ת��
		String nowDate = format.format(date);
		return nowDate;
	}
	
	/**
	 * ��ȡ��ǰϵͳ����ʱ��(ֻҪʱ��)
	 * 
	 * @return nowDate ��ǰϵͳʱ��
	 * */
	public static String getDateOnlyMinutes(long time) {
		// ʱ��ת����
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		// ��ǰʱ��
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(time*1000);
		Date date = calendar.getTime();
		// ��ǰʱ��ת��
		String nowDate = format.format(date);
		return nowDate;
	}
	
	/**
	 * ��һ����ʱ�� ��ʽ��yyyy-MM-dd
	 * 
	 * @return nextyear ��ǰϵͳʱ���һ��
	 * */
	public static String getDateAddOneYear() {
		String nextyear = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar next = Calendar.getInstance();
		next.add(Calendar.YEAR, 1);// ��һ��
		nextyear = format.format(next.getTime());// һ���Ľ���
		return nextyear;
	}

	/**
	 * ��һ���ʱ�� ��ʽ��yyyy-MM-dd
	 * */
	public static String getDateAddOneDay() {
		String nextday = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, 1);// ��һ��
		return nextday = format.format(day.getTime());
	}

	/**
	 * ��һ���ʱ�� ��ʽ��yyyy-MM-dd
	 * */
	public static String getDateCutOneDay() {
		String nextday = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, -1);// ��һ��
		return nextday = format.format(day.getTime());
	}

	/**
	 * ��2���ʱ�� ��ʽ��yyyy-MM-dd
	 * */
	public static String getDateCutTwoDay() {
		String nextday = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, -2);// ��2��
		return nextday = format.format(day.getTime());
	}

	/**
	 * ��7���ʱ�� ��ʽ��yyyy-MM-dd
	 * */
	public static String getDateCutSevenDay() {
		String nextday = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, -7);// ��7��
		return format.format(day.getTime());
	}

	/**
	 * ��һ�º��ʱ�� ��ʽ��yyyy-MM-dd
	 * 
	 * */
	public static String getDateAddOneMonth() {
		String last_time = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 1);// ��һ��
		return last_time = format.format(c.getTime());
	}

	/**
	 * ��N����ʱ��
	 * 
	 * @param second
	 *            Ҫ�ӵ�����
	 * @return String ��N����ʱ��
	 */
	public static String getDateAddSecond(int second) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.SECOND, second);// Ҫ�ӵ�����
		return format.format(c.getTime());
	}

	/**
	 * ��N�ֺ��ʱ��
	 * 
	 * @param minute
	 *            ��N�ķ���
	 * @return String ��N���ʱ��
	 */
	public static String getDateCutMinute(int minute) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -minute);// Ҫ���ķ���
		return format.format(c.getTime());
	}

	/**
	 * ʱ��Ƚ�(��ȷ������)
	 * 
	 * @param time1
	 *            ʱ��1
	 * @param time2
	 *            ʱ��2
	 * @param addMM
	 *            ʱ��2Ҫ�ӵķ�����
	 * @return boolean �ȽϽ��
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
	 * ʱ��Ƚ�(��ȷ������)
	 * 
	 * @param time1
	 *            ʱ��1
	 * @param time2
	 *            ʱ��2
	 * @param addMM
	 *            ʱ��2Ҫ�ӵķ�����
	 * @return boolean �ȽϽ��
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
	 * ʱ��Ƚ�(��ȷ������)
	 * 
	 * @param time1
	 *            ʱ��1
	 * @param time2
	 *            ʱ��2
	 * @return boolean �ȽϽ��
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
	 * ʱ��Ƚ�(��ȷ����)
	 * 
	 * @param time1
	 *            ʱ��1
	 * @param time2
	 *            ʱ��2
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
	 * ʱ��Ӽ�(��ȷ������)
	 * 
	 * @param time
	 *            ʱ��
	 * @param minutes
	 *            Ҫ��ӻ���ٵķ�����
	 * @param tag
	 *            �Ӽ���ʶ(0Ϊ���ʱ��,1Ϊ����ʱ��)
	 * @return String ��ӻ����֮���ʱ��
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
	 * ʱ��Ӽ�(��ȷ������)
	 * 
	 * @param time
	 *            ʱ��
	 * @param minutes
	 *            Ҫ��ӻ���ٵķ�����
	 * @param tag
	 *            �Ӽ���ʶ(0Ϊ���ʱ��,1Ϊ����ʱ��)
	 * @return String ��ӻ����֮���ʱ��
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
	 * ����2��ʱ���
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
	 * ��ȡ��ǰϵͳʱ��(��ȷ����)
	 * 
	 * @return nowDate ��ǰϵͳʱ��
	 * */
	public static String getSimpleDateTime() {
		// ʱ��ת����
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		// ��ǰʱ��
		Date date = new Date();
		// ��ǰʱ��ת��
		String nowDate = format.format(date);
		return nowDate;
	}

	public static boolean comparisonDate(String date) throws ParseException {
		java.util.Date nowdate = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		Date d = sdf.parse(date);

		boolean flag = d.before(nowdate);
		if (flag)
			System.out.print("���ڽ���");
		else
			System.out.print("���ڽ���");
		return flag;
	}

	public static int getHour() {
		return new Date().getHours();
	}

	/**
	 * ����ת��Ϊʱ���
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
	 * ����ת��Ϊʱ���
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
	 * ʱ���תΪ����
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
	 * ʱ���תΪ����
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
	 * ����ʱ��ת��
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
			result = "���� " + getHourAndMin(timesamp);
			break;
		case 1:
			result = "���� " + getHourAndMin(timesamp);
			break;
		case 2:
			result = "ǰ�� " + getHourAndMin(timesamp);
			break;

		default:
			// result = temp + "��ǰ ";
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
	 * ��ȡӪҵʱ��
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
	 * ��ȡ��ǰʱ�������
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
