package com.choncheng.maya.contants;

import android.R.integer;

/**
 * 
 * @项目名称：Maya
 * @类名称：Status
 * @类描述：根据状态值获取结果
 * @创建人：李波
 * @创建时间：2015-8-13 下午10:03:08
 * @版本号：v1.0
 * 
 */
public class Status {
	/**
	 * 根据订单的数量来获取vip等级
	 * 
	 * @param status
	 * @return
	 */
	public static String getVip(String statusStr) {
		int status = Integer.parseInt(statusStr);
		String vip = "LV0";
		if (status <= 0) {
			vip = "LV0";
		} else if (status == 1) {
			vip = "LV1";
		} else if (status >= 2 && status < 4) {
			vip = "LV2";
		} else if (status >= 4 && status <= 10) {
			vip = "LV3";
		} else if (status >= 11 && status <= 20) {
			vip = "LV4";
		} else if (status >= 21 && status <= 30) {
			vip = "LV5";
		} else if (status >= 31 && status <= 50) {
			vip = "LV6";
		} else if (status >= 51 && status <= 70) {
			vip = "LV7";
		} else if (status >= 71 && status <= 90) {
			vip = "LV8";
		} else if (status >= 91 && status <= 110) {
			vip = "LV9";
		} else if (status >= 111 && status <= 140) {
			vip = "LV10";
		} else if (status >= 141 && status <= 170) {
			vip = "LV11";
		} else if (status >= 171 && status <= 200) {
			vip = "LV12";
		} else if (status >= 201 && status <= 230) {
			vip = "LV13";
		} else if (status >= 231 && status <= 260) {
			vip = "LV14";
		} else if (status >= 261 && status <= 290) {
			vip = "LV15";
		} else if (status >= 291 && status <= 350) {
			vip = "LV16";
		} else if (status >= 351 && status <= 400) {
			vip = "LV17";
		} else if (status >= 401 && status <= 450) {
			vip = "LV18";
		} else if (status >= 451 && status <= 500) {
			vip = "LV19";
		} else if (status >= 501 && status <= 600) {
			vip = "LV20";
		} else {
			vip = "LV20";
		}

		return vip;
	}

	/**
	 * 获取评论的等级（好，中，差）
	 * 
	 * @param leval
	 * @return
	 */
	public static String getLeval(int leval) {
		String result = "";
		if (leval == 1) {
			result = "好评";
		} else if (leval == 2) {
			result = "中评";
		} else if (leval == 3) {
			result = "差评";
		}
		return result;
	}

	/**
	 * 获取支付方式
	 * 
	 * @param pay_way
	 * @return
	 */
	public static String getPayWay(int pay_way) {
		String result = "";
		if (pay_way == Constants.PAY_WAY_1) {
			result = "支付宝支付";
		} else if (pay_way == Constants.PAY_WAY_2) {
			result = "货到付款";
		}

		return result;
	}

	/**
	 * 1: 未发货 2：发货中 3：已收货
	 * 
	 * @param has_status
	 * @return
	 */
	public static String getHasStatus(int has_status) {
		String result = "";
		if (has_status == 1) {
			result = "未发货";
		} else if (has_status == 2) {
			result = "发货中";
		} else if (has_status == 3) {
			result = "已收货";
		}

		return result;
	}

	/**
	 * 获取配送方式
	 * 
	 * @param delivery_type
	 *            配送方式 1:送货上门 ,定时派送 （有个时间段） 2：送货上门 及时派送 2:上门自取
	 * @return
	 */
	public static String getDeliveryType(int delivery_type) {
		String result = "";
		if (delivery_type == Constants.DELIVERY_TYPE_1) {
			result = "定时派送";
		} else if (delivery_type == Constants.DELIVERY_TYPE_2) {
			result = "即时派送";
		} else if (delivery_type == Constants.DELIVERY_TYPE_3) {
			result = "上门自取";
		}

		return result;
	}
}
