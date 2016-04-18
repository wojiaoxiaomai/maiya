package com.choncheng.maya.personal;

import java.io.Serializable;

/**
 * 
 * @项目名称：Maya
 * @类名称：PersonalCenterMsg
 * @类描述： 消息实体
 * @创建人：李波
 * @创建时间：2015-8-22 下午7:40:07
 * @版本号：v1.0
 * 
 */
public class PersonalCenterMsg implements Serializable {
	private String id;// 消息id
	private String uid;// 用户id
	private String uname;// 用户名称
	private String head_img;// 用户头像
	private String shop_id;// 商家id
	private String shop_logo;
	private String shop_name;
	private String create_time;
	private String is_read;// 是否已读 1： 已读 2：未读
	private String title;// 标题
	private String content;
	private String status;// 1:有效 2：无效 3：删除
	private String u_to_s;// 1：用户发个商家的 2：商家发给用户的

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getHead_img() {
		return head_img;
	}

	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public String getShop_logo() {
		return shop_logo;
	}

	public void setShop_logo(String shop_logo) {
		this.shop_logo = shop_logo;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getIs_read() {
		return is_read;
	}

	public void setIs_read(String is_read) {
		this.is_read = is_read;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getU_to_s() {
		return u_to_s;
	}

	public void setU_to_s(String u_to_s) {
		this.u_to_s = u_to_s;
	}

}
