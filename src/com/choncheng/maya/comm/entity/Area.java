package com.choncheng.maya.comm.entity;

import java.io.Serializable;

/**
 * 
 * @项目名称：Maya
 * @类名称：Area
 * @类描述：区域实体
 * @创建人：李波
 * @创建时间：2015-8-22 下午10:53:20
 * @版本号：v1.0
 * 
 */
public class Area implements Serializable {
	private String area_id;// 区域id
	private String area;// 区域
	private String sort;
	private String lng;
	private String lat;
	private String province_id;// 省id
	private String city_id;
	private String district_id;// 区县id
	private String address;
	private String create_time;
	private String update_time;
	private String status;// 状态1： 有效 2：无效 3;删除

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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getProvince_id() {
		return province_id;
	}

	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(String district_id) {
		this.district_id = district_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
