package com.choncheng.maya.utils;


/**
 * �����û���֤����������
 *@����	������
 *@��������	2009-12-4
 *@�汾	2.0
 */
public class RandomNumber
{
	/**
	 * ����6λ�û���֤��
	 * @return long sRand �û���֤��
	 * */
	public static long get6RandomNumber(){
		long sRand = (long)Math.round(Math.random()*(899999)+100000);
		return sRand;
	}
	/**
	 * ����8λ�������
	 * @return long sRand 8λ�������
	 * */
	public static long get8RandomNumber()
	{
		long sRand = (long)Math.round(Math.random()*(89999999)+10000000);
		return sRand;
	}
	/**
	 * �����ַ�������
	 * @return uuid �ַ�������
	 * 	 */
	public static String randomUUidPK() {
		/**
		 * ����һ���̶�ֵ9999999999999L
		 * �����ʱ���ȥ���ڵ�ʱ����������õ���һ��ֵ�ǰ�ʱ��ݼ��ģ�
		 * ��ô�����ݿ����uuid��ʱ��uuid������˳��������mysql����ǰ���
		 * ��ҪĿ���ǲ��ð�ʱ��˳�����򣬲�ѯ���ݿ�
		 */
		long fd=9999999999999L;//����̶�ֵ
		long g=fd-System.currentTimeMillis();
		try {
			return String.valueOf(g+""+get6RandomNumber()+get8RandomNumber());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return "";
	}
}


