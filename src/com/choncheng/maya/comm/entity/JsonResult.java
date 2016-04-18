package com.choncheng.maya.comm.entity;

import java.io.Serializable;

/**
 * 
 * @项目名称：Maya
 * @类名称：JsonResult
 * @类描述： http请求返回的结果实体
 * @创建人：李波
 * @创建时间：2015-8-12 下午10:00:26
 * @版本号：v1.0
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
