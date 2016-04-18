package com.choncheng.maya.utils;

import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.personal.PersonalOrder;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�OrderUtils
 * @�������� ����������
 * @�����ˣ��
 * @����ʱ�䣺2015-8-25 ����10:10:52
 * @�汾�ţ�v1.0
 * 
 */
public class OrderUtils {
	/**
	 * ͨ��order����ȡ������״̬����������ջ��������ۣ�����ɣ��˿��У�
	 * 
	 * @param order
	 * @return
	 */
	public static int getOrderType(PersonalOrder order) {
		int type = Constants.ORDER_ALL;

		// ������
		if (order.getPay_way() == Constants.PAY_WAY_1
				&& order.getPay_status() == Constants.PAY_STATUS_1
				&& order.getIs_over() == Constants.IS_OVER_2) {
			type = Constants.ORDER_NO_PAY;
			// �˿���
		} else if (order.getApply_status() == Constants.APPLY_STATUS_2
				&& order.getIs_over() == Constants.IS_OVER_2) {

			type = Constants.ORDER_APPLY_ING;
			// ���ջ�
		} else if ((order.getPay_way() == Constants.PAY_WAY_2 || (order
				.getPay_way() == Constants.PAY_WAY_1 && order.getPay_status() == Constants.PAY_STATUS_3))
				&& order.getIs_over() == Constants.IS_OVER_2
				&& order.getHav_status() != Constants.HAV_STATUS_3) {

			type = Constants.ORDER_NO_GET;
			// ������
		} else if (order.getIs_over() == Constants.IS_OVER_1
				&& order.getIs_contents() == Constants.IS_CONTENTS_2) {
			type = Constants.ORDER_NO_COMMENTS;
			// ���
		} else if (order.getIs_over() == Constants.IS_OVER_1
				&& order.getIs_contents() == Constants.IS_CONTENTS_1) {
			type = Constants.ORDER_IS_OVER;
		}
		return type;
	}
}
