package com.choncheng.maya.api;

public interface ResponseHandler {
	public static final int STATE_OK = 1;// ��������
	public static final int STATE_EMPTY = 0;// û������
	public static final int STATE_ERROR = -1;// ����(�����Լ��������쳣��)
	public static final int STATE_FAIL = 2;// ʧ��

	public void onRese(String data, int state);
}