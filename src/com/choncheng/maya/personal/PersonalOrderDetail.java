package com.choncheng.maya.personal;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @项目名称：Maya
 * @类名称：PersonalOrder
 * @类描述： 我的订单详情实体
 * @创建人：李波
 * @创建时间：2015-8-11 下午7:48:28
 * @版本号：v1.0
 * 
 */
public class PersonalOrderDetail implements Serializable {
	private String goods_id;
	private String quantity;
	private String goods_name;
	private String goods_image;
	private String old_price;
	private String goods_price;
	private String spec_id;
	private int level;
	private String order_id;
	private String comments_content = "";// 评论内容
	private String expend1;;

	public String getExpend1() {
		return expend1;
	}

	public void setExpend1(String expend1) {
		this.expend1 = expend1;
	}

	public String getComments_content() {
		return comments_content;
	}

	public void setComments_content(String comments_content) {
		this.comments_content = comments_content;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getSpec_id() {
		return spec_id;
	}

	public void setSpec_id(String spec_id) {
		this.spec_id = spec_id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
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

	public String getOld_price() {
		return old_price;
	}

	public void setOld_price(String old_price) {
		this.old_price = old_price;
	}

	public String getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}

}
