package com.choncheng.maya.utils;

import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.personal.PersonalOrder;

/**
 * 
 * @项目名称：Maya
 * @类名称：OrderUtils
 * @类描述： 订单工具类
 * @创建人：李波
 * @创建时间：2015-8-25 下午10:10:52
 * @版本号：v1.0
 * 
 */
public class OrderUtils {
	/**
	 * 通过order来获取订单的状态（待付款，待收货，待评价，已完成，退款中）
	 * 
	 * @param order
	 * @return
	 */
	public static int getOrderType(PersonalOrder order) {
		int type = Constants.ORDER_ALL;

		// 待付款
		if (order.getPay_way() == Constants.PAY_WAY_1
				&& order.getPay_status() == Constants.PAY_STATUS_1
				&& order.getIs_over() == Constants.IS_OVER_2) {
			type = Constants.ORDER_NO_PAY;
			// 退款中
		} else if (order.getApply_status() == Constants.APPLY_STATUS_2
				&& order.getIs_over() == Constants.IS_OVER_2) {

			type = Constants.ORDER_APPLY_ING;
			// 待收货
		} else if ((order.getPay_way() == Constants.PAY_WAY_2 || (order
				.getPay_way() == Constants.PAY_WAY_1 && order.getPay_status() == Constants.PAY_STATUS_3))
				&& order.getIs_over() == Constants.IS_OVER_2
				&& order.getHav_status() != Constants.HAV_STATUS_3) {

			type = Constants.ORDER_NO_GET;
			// 待评论
		} else if (order.getIs_over() == Constants.IS_OVER_1
				&& order.getIs_contents() == Constants.IS_CONTENTS_2) {
			type = Constants.ORDER_NO_COMMENTS;
			// 完成
		} else if (order.getIs_over() == Constants.IS_OVER_1
				&& order.getIs_contents() == Constants.IS_CONTENTS_1) {
			type = Constants.ORDER_IS_OVER;
		}
		return type;
	}
}
