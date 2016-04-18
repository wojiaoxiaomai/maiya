package com.choncheng.maya.pay;

import java.io.Serializable;

/**
 * 
 * @项目名称：Maya
 * @类名称：PayInfo
 * @类描述： 提交的给支付宝的订单信息(字段说明在PayUtils)
 * @创建人：李波
 * @创建时间：2015-8-28 下午1:27:34
 * @版本号：v1.0
 * 
 */
public class PayInfo implements Serializable {
	private String partner;
	private String seller_id;
	private String out_trade_no;
	private String subject;
	private String body;
	private String total_fee;
	private String notify_url;
	private String service;
	private String payment_type;
	private String _input_charset;
	private String it_b_pay;
	private String show_url;
	private String prestr;
	private String sign;

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String get_input_charset() {
		return _input_charset;
	}

	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}

	public String getIt_b_pay() {
		return it_b_pay;
	}

	public void setIt_b_pay(String it_b_pay) {
		this.it_b_pay = it_b_pay;
	}

	public String getShow_url() {
		return show_url;
	}

	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}

	public String getPrestr() {
		return prestr;
	}

	public void setPrestr(String prestr) {
		this.prestr = prestr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
