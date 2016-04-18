package com.choncheng.maya.comm.entity;

import java.io.Serializable;

/**
 * 
 * @项目名称：Maya
 * @类名称：Status
 * @类描述： http请求返回的status
 * @创建人：李波
 * @创建时间：2015-8-12 下午9:59:19
 * @版本号：v1.0
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
