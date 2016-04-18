package com.choncheng.maya.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.util.Log;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�PayUtils
 * @�������� ֧��������
 * @�����ˣ��
 * @����ʱ�䣺2015-8-28 ����1:31:52
 * @�汾�ţ�v1.0
 * 
 */
public class PayUtils {

	/**
	 * �ύ��֧������pay��Ϣ
	 * 
	 * @param payInfo
	 * @return
	 */
	public static String getPayInfo(PayInfo payInfo) {
		String sign = payInfo.getSign();
		String prestr = payInfo.getPrestr();
		prestr = prestr.replaceAll("\'","\"");
		try {
			// �����sign ��URL����
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String result = prestr + "&sign=\"" + sign + "\"&"
				+ getSignType();
		return result;
	}

	/**
	 * ��ȡ���ύ�Ķ�����Ϣ
	 * 
	 * @param payInfo
	 * @return
	 */
	public static String getOrderInfo(PayInfo payInfo) {
		// ���������ID
		String orderInfo = "partner=" + "\"" + payInfo.getPartner() + "\"";

		// ����֧�����˺�
		orderInfo += "&seller_id=" + "\"" + payInfo.getSeller_id() + "\"";

		// �̻���վΨһ������
		orderInfo += "&out_trade_no=" + "\"" + payInfo.getOut_trade_no() + "\"";

		// ��Ʒ����
		orderInfo += "&subject=" + "\"" + payInfo.getSubject() + "\"";

		// ��Ʒ����
		orderInfo += "&body=" + "\"" + payInfo.getBody() + "\"";

		// ��Ʒ���
		orderInfo += "&total_fee=" + "\"" + payInfo.getTotal_fee() + "\"";

		// �������첽֪ͨҳ��·��
		orderInfo += "&notify_url=" + "\"" + payInfo.getNotify_url() + "\"";

		// �ӿ����ƣ� �̶�ֵ
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// ֧�����ͣ� �̶�ֵ
		orderInfo += "&payment_type=\"1\"";

		// �������룬 �̶�ֵ
		orderInfo += "&_input_charset=\"utf-8\"";

		// ����δ����׵ĳ�ʱʱ��
		// Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
		// ȡֵ��Χ��1m��15d��
		// m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
		// �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
		orderInfo += "&it_b_pay=\"30m\"";

		// ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
		orderInfo += "&return_url=\"m.alipay.com\"";

		// �������п�֧���������ô˲���������ǩ���� �̶�ֵ
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the sign type we use. ��ȡǩ����ʽ
	 * 
	 */
	public static String getSignType() {
		return "sign_type=\"RSA\"";
	}
}
