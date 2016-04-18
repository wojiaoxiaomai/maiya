package com.choncheng.maya.shoppingcart;

import java.io.Serializable;

import com.choncheng.maya.comm.entity.Goods;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�ShoppingCart
 * @�������� �ҵĹ��ﳵʵ��
 * @�����ˣ��
 * @����ʱ�䣺2015-8-19 ����10:14:58
 * @�汾�ţ�v1.0
 * 
 */
public class ShoppingCart implements Serializable {
	private String cart_id;
	private String goods_id;
	private String spec_id;
	private String specification;
	private String uid;
	private String goods_name;
	private String goods_image;
	private String shop_id;
	private int quantity;
	private String create_time;
	private GoodsDetail goods_info;
	private int virtual_sales;
	private boolean checked = true;// �Ƿ���ѡ��״̬Ĭ��Ϊtrue

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getCart_id() {
		return cart_id;
	}

	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getSpec_id() {
		return spec_id;
	}

	public void setSpec_id(String spec_id) {
		this.spec_id = spec_id;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public GoodsDetail getGoods_info() {
		return goods_info;
	}

	public void setGoods_info(GoodsDetail goods_info) {
		this.goods_info = goods_info;
	}

	public int getVirtual_sales() {
		return virtual_sales;
	}

	public void setVirtual_sales(int virtual_sales) {
		this.virtual_sales = virtual_sales;
	}

}
