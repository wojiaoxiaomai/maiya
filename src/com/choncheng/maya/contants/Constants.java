package com.choncheng.maya.contants;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�Constants
 * @�������� ����
 * @�����ˣ��
 * @����ʱ�䣺2015-8-8 ����8:29:29
 * @�汾�ţ�v1.0
 * 
 */
public class Constants {

	// �ͻ��绰
	public static final String CALL_NUMBER = "028-86523627";
	// ������ַ
	public static final String SERVER = "http://maiya.zgcom.cn";

	// api��ַ
	public static final String SERVER_API = SERVER + "/api.php/";

	// apk���ص�ַ
	public static final String DOWNLOAD_APK_URL = Constants.SERVER_API
			+ "appversion/download_new?device_type=1&user_type=1";
	// ��֤�볬ʱʱ��60s
	public static final long CODE_TIME_OUT = 60000;
	/*
	 * bannerͼƬ��ѵ���ʱ��
	 */
	public static final int BANNER_TIME = 4000;
	public static final String FILE_START = "file://";

	/**
	 * �ӵڼ�ҳ��ʼ
	 */
	public static final int PAGE = 1;
	/**
	 * ��ҳÿһҳ�ö���
	 */
	public static final int PAGESIZE = 10;
	public static final int LOADING_OVER_SLEEP = 400;
	// ���͵ķ�ʽ 1:��ʱ���� ���и�ʱ��Σ� 2�� ��ʱ���� 3:������ȡ
	public static final int DELIVERY_TYPE_1 = 1;
	public static final int DELIVERY_TYPE_2 = 2;
	public static final int DELIVERY_TYPE_3 = 3;

	// ֧����ʽ�� 1 : ����֧�� 2�� ��������
	public static final int PAY_WAY_1 = 1;
	public static final int PAY_WAY_2 = 2;
	// ֧��״̬�� 1�� δ֧�� 2�� ������ 3�� ��֧��
	public static final int PAY_STATUS_1 = 1;
	public static final int PAY_STATUS_2 = 2;
	public static final int PAY_STATUS_3 = 3;
	// �����Ƿ��Ѿ����1����� 2�� δ���
	public static final int IS_OVER_1 = 1;
	public static final int IS_OVER_2 = 2;

	// 1: δ���� 2�������� 3�����ջ�
	public static final int HAV_STATUS_1 = 1;
	public static final int HAV_STATUS_2 = 2;
	public static final int HAV_STATUS_3 = 3;
	// ��������״̬ 1���� 2��δ����
	public static final int IS_CONTENTS_1 = 1;
	public static final int IS_CONTENTS_2 = 2;
	// apply_status 1:������״̬ 2�������˿� 3:�˿�ɹ� 4���˿�ʧ��
	public static final int APPLY_STATUS_1 = 1;
	public static final int APPLY_STATUS_2 = 2;
	public static final int APPLY_STATUS_3 = 3;
	public static final int APPLY_STATUS_4 = 4;
	// �ҵĶ�����״̬
	public static final int ORDER_ALL = 0;// ȫ��
	public static final int ORDER_NO_PAY = 1;// ������
	public static final int ORDER_NO_GET = 2;// ���ջ�
	public static final int ORDER_NO_COMMENTS = 3;// ������
	public static final int ORDER_IS_OVER = 4;
	public static final int ORDER_APPLY_ING = 5;
	// ���͸��¹��ﳵ�㲥
	public static final String ACTION_UPDATE_SHOPCART_NUMBER = "action_update_shopcart_number";
	public static final String SP_NAME = "sp_name";

	// Ϊ�����������еķ��࣬��ʶ 1Ϊͨ���ؼ��������õ���2Ϊ�����б��е����2����ã�3Ϊͨ�����3�����
	public static final int SEARCH_TYPE_KEY = 1;// �ؼ�������
	public static final int SEARCH_TYPE_SECOND = 2;// ����Ŀ¼
	public static final int SEARCH_TYPE_THIRD = 3;// ����Ŀ¼
}
