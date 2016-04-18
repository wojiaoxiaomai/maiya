package com.choncheng.maya.api;

public interface ResponseHandler {
	public static final int STATE_OK = 1;// 正常返回
	public static final int STATE_EMPTY = 0;// 没有数据
	public static final int STATE_ERROR = -1;// 出错(网络以及服务器异常等)
	public static final int STATE_FAIL = 2;// 失败

	public void onRese(String data, int state);
}