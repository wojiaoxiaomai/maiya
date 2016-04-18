package com.choncheng.maya.shoppingcart;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @项目名称：Maya
 * @类名称：GoodsDetail
 * @类描述：产品详情实体
 * @创建人：李波
 * @创建时间：2015-8-16 下午8:25:24
 * @版本号：v1.0
 * 
 */
public class GoodsDetail implements Serializable {
	private String shop_id;
	private String goods_id;
	private String spec_id;
	private String goods_sn;// 产品编码
	private String goods_name;
	private String goods_image;
	private String spec_qty;
	private String spec_name_1;
	private String spec_name_2;
	private String is_recommended;
	private String cate_name;
	private String cate_id;
	private String status;
	private String goods_price;
	private String sales;
	private String expend1;
	private String tag;
	private int stock;//实际库存
	private String description;// 产品描述 请求地址
	private String old_price;
	private List<Images> images;
	private int virtual_sales;// 虚拟销售量

	public String getOld_price() {
		return old_price;
	}

	public void setOld_price(String old_price) {
		this.old_price = old_price;
	}

	public String getShop_id() {
		return shop_id;
	}

	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
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

	public String getGoods_sn() {
		return goods_sn;
	}

	public void setGoods_sn(String goods_sn) {
		this.goods_sn = goods_sn;
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

	public String getSpec_qty() {
		return spec_qty;
	}

	public void setSpec_qty(String spec_qty) {
		this.spec_qty = spec_qty;
	}

	public String getSpec_name_1() {
		return spec_name_1;
	}

	public void setSpec_name_1(String spec_name_1) {
		this.spec_name_1 = spec_name_1;
	}

	public String getSpec_name_2() {
		return spec_name_2;
	}

	public void setSpec_name_2(String spec_name_2) {
		this.spec_name_2 = spec_name_2;
	}

	public String getIs_recommended() {
		return is_recommended;
	}

	public void setIs_recommended(String is_recommended) {
		this.is_recommended = is_recommended;
	}

	public String getCate_name() {
		return cate_name;
	}

	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}

	public String getCate_id() {
		return cate_id;
	}

	public void setCate_id(String cate_id) {
		this.cate_id = cate_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getExpend1() {
		return expend1;
	}

	public void setExpend1(String expend1) {
		this.expend1 = expend1;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Images> getImages() {
		return images;
	}

	public void setImages(List<Images> images) {
		this.images = images;
	}

	public int getVirtual_sales() {
		return virtual_sales;
	}

	public void setVirtual_sales(int virtual_sales) {
		this.virtual_sales = virtual_sales;
	}

}
