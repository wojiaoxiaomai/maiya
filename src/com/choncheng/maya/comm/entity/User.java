package com.choncheng.maya.comm.entity;

import java.io.Serializable;

import com.choncheng.maya.contants.Constants;

/**
 * 
 * @项目名称：Maya
 * @类名称：User
 * @类描述： 用户信息
 * @创建人：李波
 * @创建时间：2015-8-13 下午12:39:44
 * @版本号：v1.0
 * 
 */
public class User implements Serializable {

	private String real_name;
	private String nack_name;
	private String sex;// 性别 1：男 2女 3:中性 4：保密
	private String head_img;
	private String birthday;
	private String autograph;
	private String expand1;
	private String expand2;
	private String expand3;
	private String expand4;
	private String tel;
	private String province_id;
	private String city_id;
	private String district_id;
	private String create_time;
	private String uid;
	private String phone;
	private String password;
	private String user_integral;// 用户积分
	private String shop_integral;// 超市积分
	private String money;// 用户余额
	private String freeze_money;// o冻结价格
	private String device_type;
	private String device_number;
	private String lng;
	private String lat;
	private String ucode;// 用户校验码
	private String login_time;
	private String login_num;

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getNack_name() {
		return nack_name;
	}

	public void setNack_name(String nack_name) {
		this.nack_name = nack_name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHead_img() {
		return head_img;
	}

	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public String getExpand1() {
		return expand1;
	}

	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}

	public String getExpand2() {
		return expand2;
	}

	public void setExpand2(String expand2) {
		this.expand2 = expand2;
	}

	public String getExpand3() {
		return expand3;
	}

	public void setExpand3(String expand3) {
		this.expand3 = expand3;
	}

	public String getExpand4() {
		return expand4;
	}

	public void setExpand4(String expand4) {
		this.expand4 = expand4;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser_integral() {
		return user_integral;
	}

	public void setUser_integral(String user_integral) {
		this.user_integral = user_integral;
	}

	public String getShop_integral() {
		return shop_integral;
	}

	public void setShop_integral(String shop_integral) {
		this.shop_integral = shop_integral;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getFreeze_money() {
		return freeze_money;
	}

	public void setFreeze_money(String freeze_money) {
		this.freeze_money = freeze_money;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public String getDevice_number() {
		return device_number;
	}

	public void setDevice_number(String device_number) {
		this.device_number = device_number;
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

	public String getUcode() {
		return ucode;
	}

	public void setUcode(String ucode) {
		this.ucode = ucode;
	}

	public String getLogin_time() {
		return login_time;
	}

	public void setLogin_time(String login_time) {
		this.login_time = login_time;
	}

	public String getLogin_num() {
		return login_num;
	}

	public void setLogin_num(String login_num) {
		this.login_num = login_num;
	}

	@Override
	public String toString() {
		return "User [real_name=" + real_name + ", nack_name=" + nack_name
				+ ", sex=" + sex + ", head_img=" + head_img + ", birthday="
				+ birthday + ", autograph=" + autograph + ", expand1="
				+ expand1 + ", expand2=" + expand2 + ", expand3=" + expand3
				+ ", expand4=" + expand4 + ", tel=" + tel + ", province_id="
				+ province_id + ", city_id=" + city_id + ", district_id="
				+ district_id + ", create_time=" + create_time + ", uid=" + uid
				+ ", phone=" + phone + ", password=" + password
				+ ", user_integral=" + user_integral + ", shop_integral="
				+ shop_integral + ", money=" + money + ", freeze_money="
				+ freeze_money + ", device_type=" + device_type
				+ ", device_number=" + device_number + ", lng=" + lng
				+ ", lat=" + lat + ", ucode=" + ucode + ", login_time="
				+ login_time + ", login_num=" + login_num + "]";
	}

}
