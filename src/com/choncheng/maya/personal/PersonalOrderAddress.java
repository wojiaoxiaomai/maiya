package com.choncheng.maya.personal;

import java.io.Serializable;

/**
 * 
 * @项目名称：Maya
 * @类名称：PersonalOrderAddress
 * @类描述： 订单详情中的个人地址信息
 * @创建人：李波
 * @创建时间：2015-8-23 下午11:35:05
 * @版本号：v1.0
 * 
 */
public class PersonalOrderAddress implements Serializable {
	private String addr_id;
	private String area_id;
	private String area;
	private String link_uname;
	private String tel;
	private String address;

	public String getAddr_id() {
		return addr_id;
	}

	public void setAddr_id(String addr_id) {
		this.addr_id = addr_id;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLink_uname() {
		return link_uname;
	}

	public void setLink_uname(String link_uname) {
		this.link_uname = link_uname;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
