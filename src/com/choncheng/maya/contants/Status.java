package com.choncheng.maya.contants;

import android.R.integer;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�Status
 * @������������״ֵ̬��ȡ���
 * @�����ˣ��
 * @����ʱ�䣺2015-8-13 ����10:03:08
 * @�汾�ţ�v1.0
 * 
 */
public class Status {
	/**
	 * ���ݶ�������������ȡvip�ȼ�
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
	 * ��ȡ���۵ĵȼ����ã��У��
	 * 
	 * @param leval
	 * @return
	 */
	public static String getLeval(int leval) {
		String result = "";
		if (leval == 1) {
			result = "����";
		} else if (leval == 2) {
			result = "����";
		} else if (leval == 3) {
			result = "����";
		}
		return result;
	}

	/**
	 * ��ȡ֧����ʽ
	 * 
	 * @param pay_way
	 * @return
	 */
	public static String getPayWay(int pay_way) {
		String result = "";
		if (pay_way == Constants.PAY_WAY_1) {
			result = "֧����֧��";
		} else if (pay_way == Constants.PAY_WAY_2) {
			result = "��������";
		}

		return result;
	}

	/**
	 * 1: δ���� 2�������� 3�����ջ�
	 * 
	 * @param has_status
	 * @return
	 */
	public static String getHasStatus(int has_status) {
		String result = "";
		if (has_status == 1) {
			result = "δ����";
		} else if (has_status == 2) {
			result = "������";
		} else if (has_status == 3) {
			result = "���ջ�";
		}

		return result;
	}

	/**
	 * ��ȡ���ͷ�ʽ
	 * 
	 * @param delivery_type
	 *            ���ͷ�ʽ 1:�ͻ����� ,��ʱ���� ���и�ʱ��Σ� 2���ͻ����� ��ʱ���� 2:������ȡ
	 * @return
	 */
	public static String getDeliveryType(int delivery_type) {
		String result = "";
		if (delivery_type == Constants.DELIVERY_TYPE_1) {
			result = "��ʱ����";
		} else if (delivery_type == Constants.DELIVERY_TYPE_2) {
			result = "��ʱ����";
		} else if (delivery_type == Constants.DELIVERY_TYPE_3) {
			result = "������ȡ";
		}

		return result;
	}
}
