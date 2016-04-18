package com.choncheng.maya.utils;

import com.choncheng.maya.MyApplication;

/**
 * 
 * @��Ŀ���ƣ�
 * @�����ƣ�LocalSettings
 * @���������洢
 * @�����ˣ��
 * @����ʱ�䣺2015-1-23 ����10:12:25
 * @�汾�ţ�v1.0
 * 
 */
public class LocalSettings {
	/*
	 * ����Ϣ
	 */
	public static final String KEY_NEW_MSG = "key_new_msg";
	
	//�Ƿ��Զ���¼
	public static final String KEY_AUTO_LOGIN = "key_auto_login";
	public static void putString(String key, String value) {
		MyApplication.getInstance().getSharedPreferences().edit()
				.putString(key, value).commit();
	}

	public static String getString(String key, String defValue) {
		return MyApplication.getInstance().getSharedPreferences()
				.getString(key, defValue);
	}

	public static void putBoolean(String key, boolean value) {
		MyApplication.getInstance().getSharedPreferences().edit()
				.putBoolean(key, value).commit();
	}

	public static boolean getBoolean(String key, boolean defValue) {
		return MyApplication.getInstance().getSharedPreferences()
				.getBoolean(key, defValue);
	}

	public static int getInt(String key, int defValue) {
		return MyApplication.getInstance().getSharedPreferences()
				.getInt(key, defValue);
	}

	public static void putInt(String key, int value) {
		MyApplication.getInstance().getSharedPreferences().edit()
				.putInt(key, value).commit();
	}
}
