package com.choncheng.maya.personal;

import java.io.Serializable;
import java.util.List;

import com.choncheng.maya.comm.entity.Goods;
import com.choncheng.maya.shoppingcart.GoodsDetail;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�PersonalCollection
 * @�������� �ղع���ʵ��
 * @�����ˣ��
 * @����ʱ�䣺2015-8-9 ����11:23:31
 * @�汾�ţ�v1.0
 * 
 */
public class PersonalCollection implements Serializable {
	private long create_time;
	private String collect_id;
	private GoodsDetail good_info;

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	public String getCollect_id() {
		return collect_id;
	}

	public void setCollect_id(String collect_id) {
		this.collect_id = collect_id;
	}

	public GoodsDetail getGood_info() {
		return good_info;
	}

	public void setGood_info(GoodsDetail good_info) {
		this.good_info = good_info;
	}

}
