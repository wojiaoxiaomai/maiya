package com.choncheng.maya.personal;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @项目名称：Maya
 * @类名称：PersonalOrder
 * @类描述： 我的订单实体
 * @创建人：李波
 * @创建时间：2015-8-11 下午7:48:28
 * @版本号：v1.0
 * 
 */
public class PersonalOrder implements Serializable {
	private String order_id;
	private String create_time;
	private List<PersonalOrderGoods> goods_lists;
	private float goods_amount;// 产品价格
	private float shipping_fee;// 运费
	private String order_number;// 订单编号
	private int pay_way;// 支付方式1 : 在线支付 2： 货到付款
	private int hav_status;// 1: 未发货 2：发货中 3：已收货
	private int delivery_type;// 配送方式 1:送货上门 ,定时派送 （有个时间段） 2：送货上门 及时派送 2:上门自取
	private int pay_status;
	private int is_over;
	private int is_contents;//1：评论 2：未评论
	private int apply_status;//1:无申请状态  2：申请退款   3:退款成功  4：退款失败 
	//==>2016.15
	private String best_start_time;
	private String best_end_time;
	//<==2016.1.5

	public int getApply_status() {
		return apply_status;
	}

	public void setApply_status(int apply_status) {
		this.apply_status = apply_status;
	}

	public int getIs_contents() {
		return is_contents;
	}

	public void setIs_contents(int is_contents) {
		this.is_contents = is_contents;
	}

	public int getIs_over() {
		return is_over;
	}

	public void setIs_over(int is_over) {
		this.is_over = is_over;
	}

	public int getPay_status() {
		return pay_status;
	}

	public void setPay_status(int pay_status) {
		this.pay_status = pay_status;
	}

	private PersonalOrderAddress order_address;// 地址信息实体

	public PersonalOrderAddress getOrder_address() {
		return order_address;
	}

	public void setOrder_address(PersonalOrderAddress order_address) {
		this.order_address = order_address;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public int getPay_way() {
		return pay_way;
	}

	public void setPay_way(int pay_way) {
		this.pay_way = pay_way;
	}

	public int getHav_status() {
		return hav_status;
	}

	public void setHav_status(int hav_status) {
		this.hav_status = hav_status;
	}

	public int getDelivery_type() {
		return delivery_type;
	}

	public void setDelivery_type(int delivery_type) {
		this.delivery_type = delivery_type;
	}

	public List<PersonalOrderGoods> getGoods_lists() {
		return goods_lists;
	}

	public void setGoods_lists(List<PersonalOrderGoods> goods_lists) {
		this.goods_lists = goods_lists;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public float getGoods_amount() {
		return goods_amount;
	}

	public void setGoods_amount(float goods_amount) {
		this.goods_amount = goods_amount;
	}

	public float getShipping_fee() {
		return shipping_fee;
	}

	public void setShipping_fee(float shipping_fee) {
		this.shipping_fee = shipping_fee;
	}

	public String getBest_start_time() {
		return best_start_time;
	}

	public void setBest_start_time(String best_start_time) {
		this.best_start_time = best_start_time;
	}

	public String getBest_end_time() {
		return best_end_time;
	}

	public void setBest_end_time(String best_end_time) {
		this.best_end_time = best_end_time;
	}

}
