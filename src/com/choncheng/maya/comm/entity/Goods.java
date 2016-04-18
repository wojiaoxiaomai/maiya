package com.choncheng.maya.comm.entity;

import java.io.Serializable;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�Goods
 * @��������(����) ��Ʒʵ��
 * @�����ˣ��
 * @����ʱ�䣺2015-8-9 ����11:46:16
 * @�汾�ţ�v1.0
 * 
 */
public class Goods implements Serializable {
	private String goods_id;
	private String goods_name;
	private String goods_image;
	private String goods_price;
	private String old_price;
	private String spec_id;
	private String goods_sn;
	private int stock;
	private int virtual_sales;

	public String getSpec_id() {
		return spec_id;
	}

	public void setSpec_id(String spec_id) {
		this.spec_id = spec_id;
	}

	public String getGoods_sn() {
		return goods_sn;
	}

	public void setGoods_sn(String goods_sn) {
		this.goods_sn = goods_sn;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getVirtual_sales() {
		return virtual_sales;
	}

	public void setVirtual_sales(int virtual_sales) {
		this.virtual_sales = virtual_sales;
	}

	public String getOld_price() {
		return old_price;
	}

	public void setOld_price(String old_price) {
		this.old_price = old_price;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getGoods_image() {
		return goods_image;
	}

	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}

	public String getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}

}
