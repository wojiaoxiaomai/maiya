package com.choncheng.maya.shoppingcart;

import java.io.Serializable;

import com.choncheng.maya.comm.entity.User;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�GoodsComments
 * @�������� ��Ʒ����ʵ��
 * @�����ˣ��
 * @����ʱ�䣺2015-8-11 ����3:15:19
 * @�汾�ţ�v1.0
 * 
 */
public class GoodsComments implements Serializable {
	private User userinfo;
	private String content;
	private int level;
	private String create_time;

	public User getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(User userinfo) {
		this.userinfo = userinfo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

}
