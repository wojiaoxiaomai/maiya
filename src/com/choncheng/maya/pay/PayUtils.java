package com.choncheng.maya.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.util.Log;

/**
 * 
 * @项目名称：Maya
 * @类名称：PayUtils
 * @类描述： 支付工具类
 * @创建人：李波
 * @创建时间：2015-8-28 下午1:31:52
 * @版本号：v1.0
 * 
 */
public class PayUtils {

	/**
	 * 提交给支付宝的pay信息
	 * 
	 * @param payInfo
	 * @return
	 */
	public static String getPayInfo(PayInfo payInfo) {
		String sign = payInfo.getSign();
		String prestr = payInfo.getPrestr();
		prestr = prestr.replaceAll("\'","\"");
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String result = prestr + "&sign=\"" + sign + "\"&"
				+ getSignType();
		return result;
	}

	/**
	 * 获取待提交的订单信息
	 * 
	 * @param payInfo
	 * @return
	 */
	public static String getOrderInfo(PayInfo payInfo) {
		// 合作者身份ID
		String orderInfo = "partner=" + "\"" + payInfo.getPartner() + "\"";

		// 卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + payInfo.getSeller_id() + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + payInfo.getOut_trade_no() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + payInfo.getSubject() + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + payInfo.getBody() + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + payInfo.getTotal_fee() + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + payInfo.getNotify_url() + "\"";

		// 接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public static String getSignType() {
		return "sign_type=\"RSA\"";
	}
}
