package com.choncheng.maya.personal;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @项目名称：Maya
 * @类名称：PersonalOrderGoods
 * @类描述： 我的订单中商品的信息
 * @创建人：李波
 * @创建时间：2015-8-20 下午10:20:57
 * @版本号：v1.0
 * 
 */
public class PersonalOrderGoods implements Serializable {
	private String id;
	private String order_id;
	private String goods_id;
	private String spec_id;
	private String specification;
	private String goods_name;
	private String goods_image;
	private String shop_price;
	private String buy_price;
	private String quantity;
	private String is_contents;
	private String status;
	private String old_price;
	private String expend1;

	public String getOld_price() {
		return old_price;
	}

	public void setOld_price(String old_price) {
		this.old_price = old_price;
	}

	public String getExpend1() {
		return expend1;
	}

	public void setExpend1(String expend1) {
		this.expend1 = expend1;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
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

	public String getShop_price() {
		return shop_price;
	}

	public void setShop_price(String shop_price) {
		this.shop_price = shop_price;
	}

	public String getBuy_price() {
		return buy_price;
	}

	public void setBuy_price(String buy_price) {
		this.buy_price = buy_price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getIs_contents() {
		return is_contents;
	}

	public void setIs_contents(String is_contents) {
		this.is_contents = is_contents;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
