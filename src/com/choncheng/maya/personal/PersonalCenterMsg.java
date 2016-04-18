package com.choncheng.maya.personal;

import java.io.Serializable;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�PersonalCenterMsg
 * @�������� ��Ϣʵ��
 * @�����ˣ��
 * @����ʱ�䣺2015-8-22 ����7:40:07
 * @�汾�ţ�v1.0
 * 
 */
public class PersonalCenterMsg implements Serializable {
	private String id;// ��Ϣid
	private String uid;// �û�id
	private String uname;// �û�����
	private String head_img;// �û�ͷ��
	private String shop_id;// �̼�id
	private String shop_logo;
	private String shop_name;
	private String create_time;
	private String is_read;// �Ƿ��Ѷ� 1�� �Ѷ� 2��δ��
	private String title;// ����
	private String content;
	private String status;// 1:��Ч 2����Ч 3��ɾ��
	private String u_to_s;// 1���û������̼ҵ� 2���̼ҷ����û���

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
