package com.choncheng.maya.comm.entity;

import java.io.Serializable;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�JsonResult
 * @�������� http���󷵻صĽ��ʵ��
 * @�����ˣ��
 * @����ʱ�䣺2015-8-12 ����10:00:26
 * @�汾�ţ�v1.0
 * 
 */
public class JsonResult implements Serializable {
	private Status status;
	private String data;
	private PageInfo paging;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public PageInfo getPaging() {
		return paging;
	}

	public void setPaging(PageInfo paging) {
		this.paging = paging;
	}

}
