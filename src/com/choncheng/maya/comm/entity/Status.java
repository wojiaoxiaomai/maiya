package com.choncheng.maya.comm.entity;

import java.io.Serializable;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�Status
 * @�������� http���󷵻ص�status
 * @�����ˣ��
 * @����ʱ�䣺2015-8-12 ����9:59:19
 * @�汾�ţ�v1.0
 * 
 */
public class Status implements Serializable {
	private String msg;
	private int code;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
