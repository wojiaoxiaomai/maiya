package com.choncheng.maya.utils;


/**
 * 生成用户认证码和随机数字
 *@作者	饶正勇
 *@创建日期	2009-12-4
 *@版本	2.0
 */
public class RandomNumber
{
	/**
	 * 生成6位用户认证码
	 * @return long sRand 用户认证码
	 * */
	public static long get6RandomNumber(){
		long sRand = (long)Math.round(Math.random()*(899999)+100000);
		return sRand;
	}
	/**
	 * 生成8位随机数字
	 * @return long sRand 8位随机数字
	 * */
	public static long get8RandomNumber()
	{
		long sRand = (long)Math.round(Math.random()*(89999999)+10000000);
		return sRand;
	}
	/**
	 * 生成字符型主键
	 * @return uuid 字符型主键
	 * 	 */
	public static String randomUUidPK() {
		/**
		 * 定义一个固定值9999999999999L
		 * 用这个时间减去现在的时间戳，这样得到的一个值是按时间递减的，
		 * 那么在数据库插入uuid的时候，uuid按排列顺序是排在mysql的最前面的
		 * 主要目的是不用按时间顺序排序，查询数据库
		 */
		long fd=9999999999999L;//定义固定值
		long g=fd-System.currentTimeMillis();
		try {
			return String.valueOf(g+""+get6RandomNumber()+get8RandomNumber());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return "";
	}
}


