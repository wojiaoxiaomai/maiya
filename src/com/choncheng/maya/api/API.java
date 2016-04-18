package com.choncheng.maya.api;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.http.Header;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.comm.entity.JsonResult;
import com.choncheng.maya.comm.entity.PageInfo;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.utils.CommUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�API
 * @��������
 * @�����ˣ��
 * @����ʱ�䣺2015-9-5 ����10:37:25
 * @�汾�ţ�v1.0
 * 
 */
public class API {
	private static final boolean DEBUG = true;
	// ����ɹ���
	private static final int SUCESS_CODE = 0;
	// ��¼��ʱ��
	private static final int FAIL_LOGIN_OUT = 10001;
	private static final String TAG = "API";
	/**
	 * ע����֤��-��ȡ
	 */
	private static final String REGIST_CODE = "regist/code";
	/**
	 * ��֤�ֻ���--�Ƿ�ע��
	 */
	private static final String REGIST_CHECK_PHONE = "regist/check_phone";
	private static final String REGIST_CHECK_CODE = "regist/check_code";
	private static final String REGIST_REGIST = "regist/regist";
	private static final String LOGIN_LOGIN = "login/login";
	private static final String USEREDIT_PASSWORD_CODE = "useredit/password_code";
	private static final String USEREDIT_FIND_PASSWORD = "useredit/find_password";
	private static final String USEREDIT_EDIT_PASSWORD = "useredit/edit_password";
	private static final String ORDERLISTS_OVER_NUMBER = "orderlists/over_number";
	private static final String ADLISTS_LISTS = "adlists/lists";
	public static final int ADLISTS_BANNER = 1;
	public static final int ADLISTS_RECOMMEND = 2;
	public static final int ADLISTS_BOTTOM = 3;

	private static final String ADGOODS_LISTS = "adgoods/lists";
	private static final String GOODSCATE_LISTS = "goodscate/lists";
	private static final String GOODSLISTS_LISTS = "goodslists/lists";
	private static final String ADLISTS_INFO = "adlists/info";
	private static final String USEREDIT_EDIT_INFO = "useredit/edit_info";
	private static final String GOODSINFO_INFO = "goodsinfo/info";
	private static final String GOODSEVALUATION_CONTENTS_NUMBER = "Goodsevaluation/contents_number";
	private static final String GOODSEVALUATION_LISTS = "Goodsevaluation/lists";
	private static final String COLLECTGOODS_COLLECT = "Collectgoods/collect";
	private static final String COLLECTGOODS_CANCEL = "Collectgoods/cancel";
	private static final String COLLECTGOODS_LISTS = "Collectgoods/lists";
	private static final String COLLECTGOODS_IS_COLLECT = "Collectgoods/is_collect";
	private static final String SHOPCARTADD_ADD = "Shopcartadd/add";
	private static final String SHOPCARTLISTS_LISTS = "Shopcartlists/lists";
	private static final String USERADDRESS_LISTS = "useraddress/lists";
	private static final String USERADDRESS_CANCEL = "useraddress/cancel";
	private static final String USERADDRESS_SET_DEFAULT = "useraddress/set_default";
	private static final String USERADDRESS_ADD = "useraddress/add";
	private static final String USERADDRESS_EDIT = "useraddress/edit";
	private static final String SHOPCARTCANCEL_DELETE_SELECTED = "Shopcartcancel/delete_selected";
	private static final String SHOPCAR_EDIT_QUANTITY = "Shopcartadd/edit_quantity";
	private static final String ORDERLISTS_ALL = "orderlists/all";
	private static final String ORDERLISTS_NO_PAY = "orderlists/no_pay";
	private static final String ORDERLISTS_NO_GET = "orderlists/no_get";
	private static final String ORDERLISTS_NO_CONTENTS = "orderlists/no_contents";
	private static final String ORDERLISTS_IS_OVER = "orderlists/is_over";
	private static final String ORDERLISTS_APPLY_ING = "orderlists/apply_ing";
	private static final String LEAVEMSG_MSG = "leavemsg/msg";
	private static final String LEAVEMSG_SEND_MSG = "leavemsg/send_msg";
	private static final String AREA_LISTS = "area/lists";
	private static final String SHIPPINGFEE_FEE = "shippingfee/fee";
	private static final String ORDERINFO_INFO = "orderinfo/info";
	private static final String ORDERADD_ADD = "orderadd/add";
	private static final String ORDER_LOSE_TIME = "order/lose_time";
	private static final String ORDERSTATUS_ORDER_CANCEL = "orderstatus/order_cancel";
	private static final String ORDERSURE_SURE = "ordersure/sure";
	private static final String ORDERAPPLY_APPLYING = "orderapply/applying";
	private static final String ORDERAPPLY_CANCEL_ORDER = "orderapply/cancel_apply";
	private static final String GOODSEVALUATION_ADD_EVAL = "Goodsevaluationadd/add";
	private static final String ORDERSTATUS_HIDE = "orderstatus/hide";
	private static final String SHOP_IN_TIME = "shop/in_time";
	private static final String ORDERADD_BUY_AGAIN = "orderadd/buy_again";
	private static final String SHOPCARTLISTS_CART_NUMBER = "Shopcartlists/cart_number";
	private static final String PAYALIPAY_DO_ENCRYPT = "Payalipay/do_encrypt";
	private static final String APPVERSION_APP_NEW = "appversion/app_new";
	public static final String REFRESH_MESSAGE = "REFRESH_MESSAGE";
	public static final String NO_MESSAGE = "NO_MESSAGE";

	public static final String MSG_ACTION = "com.choucheng.service";


	/**
	 * ע���ʱ���ȡ��֤��
	 * 
	 * @param phone
	 *            �ֻ�����
	 * @param response
	 */
	public static void registCode(String phone, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("phone", phone);
		client.post(Constants.SERVER_API + REGIST_CODE, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, REGIST_CODE + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��֤�ֻ���--�Ƿ�ע��
	 * 
	 * @param phone
	 * @param response
	 */
	public static void registCheckPhone(String phone,
			final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("phone", phone);
		client.post(Constants.SERVER_API + REGIST_CHECK_PHONE, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, REGIST_CHECK_PHONE + "result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��֤-�ֻ�-��֤��
	 * 
	 * @param phone
	 * @param response
	 */
	public static void registCheckCode(String phone, String code,
			final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("phone", phone);
		params.put("code", code);
		client.post(Constants.SERVER_API + REGIST_CHECK_CODE, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, REGIST_CHECK_CODE + "result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ע���ֻ�-�������
	 * 
	 * @param phone
	 *            �ֻ���
	 * @param password
	 *            ����
	 * @param response
	 */
	public static void registRegist(String phone, String password,
			final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("phone", phone);
		params.put("password", password);
		client.post(Constants.SERVER_API + REGIST_REGIST, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, REGIST_REGIST + "result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��¼
	 * 
	 * @param phone
	 * @param password
	 * @param response
	 */
	public static void loginLogin(String phone, String password,
			final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("phone", phone);
		params.put("password", password);
		client.post(Constants.SERVER_API + LOGIN_LOGIN, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, LOGIN_LOGIN + "result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ����-����-��ȡ��֤��
	 * 
	 * @param phone
	 *            �ֻ���
	 * @param response
	 */
	public static void usereditPasswordCode(String phone,
			final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("phone", phone);
		client.post(Constants.SERVER_API + USEREDIT_PASSWORD_CODE, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, USEREDIT_PASSWORD_CODE + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * �һ�����-��������
	 * 
	 * @param phone�ֻ���
	 * @param code��֤��
	 * @param password����
	 * @param response
	 */
	public static void usereditFindPassword(String phone, String code,
			String password, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("phone", phone);
		params.put("code", code);
		params.put("password", password);
		client.post(Constants.SERVER_API + USEREDIT_FIND_PASSWORD, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, USEREDIT_FIND_PASSWORD + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * �޸�����
	 * 
	 * @param ucode
	 *            �û�У����
	 * @param password
	 * @param response
	 */
	public static void usereditEditPassword(final Context context,
			String ucode, String password, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("password", password);
		client.post(Constants.SERVER_API + USEREDIT_EDIT_PASSWORD, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, USEREDIT_EDIT_PASSWORD + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ���򶩵���
	 * 
	 * @param ucode�û�Ч����
	 * @param response
	 */
	public static void orderlistsOverNumber(final Context context,
			String ucode, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		client.post(Constants.SERVER_API + ORDERLISTS_OVER_NUMBER, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERLISTS_OVER_NUMBER + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
//									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
//										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
//									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��ȡ����Ĺ��λ-�б�
	 * 
	 * @param type_id
	 *            1:��ҳͷ�����λ 2�� �в����λ 3β�����λ
	 * @param response
	 */
	public static void adlistsLists(int type_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("type_id", type_id);
	
		client.post(Constants.SERVER_API + ADLISTS_LISTS, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ADLISTS_LISTS + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ĳ������µĲ�Ʒ
	 * 
	 * @param ad_id
	 *            ���λid
	 * @param currentpage  ��ǰҳ
	 *             
	 * @param response
	 */
	public static void adgoodsLists(String ad_id,final String currentpage, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ad_id", ad_id);
		if(!currentpage.equals("-1")){
			//���ҳ������Ƽ���Ʒ�����з�ҳ����
			params.put("page",currentpage);
			params.put("type", "1");
		}else{
			//app��ҳ����30��
			params.put("type", "2");
		}
		
		client.post(Constants.SERVER_API + ADGOODS_LISTS, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ADGOODS_LISTS + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									//==>2015.12.30 �ѷ�ҳ����Ҳ����
									if(currentpage.equals("-1")){
										response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);
		                             }else{
		                            	response.onRese(new String(arg2), ResponseHandler.STATE_OK); 
		                             }
									//<==2015.12.30

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * �����б�
	 * 
	 * @param cate_id
	 *            ����id ���Ǳش�
	 * @param response
	 */
	public static void goodscateLists(String cate_id,
			final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		if (!TextUtils.isEmpty(cate_id)) {
			params.put("cate_id", cate_id);
		}
		client.post(Constants.SERVER_API + GOODSCATE_LISTS, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, GOODSCATE_LISTS + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ĳ����������
	 * 
	 * @param ad_id
	 *            ���id
	 * @param response
	 */
	public static void adlistsInfo(String ad_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ad_id", ad_id);
		client.post(Constants.SERVER_API + ADLISTS_INFO, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ADLISTS_INFO + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * �޸Ļ������ϣ���int�͵�Ϊ0�Ļ���ʾ������
	 * 
	 * @param ucode
	 *            �ش�
	 * @param real_name
	 * @param nack_name
	 * @param sex
	 * @param head_img
	 * @param birthday
	 * @param autograph
	 * @param expand1
	 * @param expand2
	 * @param expand3
	 * @param expand4
	 * @param tel
	 * @param province_id
	 *            ��0��ʶ����
	 * @param city_id
	 * @param district_id
	 * @param response
	 */
	public static void usereditEditInfo(final Context context, String ucode,
			String real_name, String nack_name, int sex, File head_img,
			String birthday, String autograph, String expand1, String expand2,
			String expand3, String expand4, String tel, int province_id,
			int city_id, int district_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		if (!TextUtils.isEmpty(real_name)) {
			params.put("real_name", real_name);
		}
		if (!TextUtils.isEmpty(nack_name)) {
			params.put("nack_name", nack_name);
		}
		if (sex != 0) {
			params.put("sex", sex);
		}

		if (head_img != null) {
			try {
				params.put("head_img", head_img);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (!TextUtils.isEmpty(birthday)) {
			params.put("birthday", birthday);
		}
		if (!TextUtils.isEmpty(autograph)) {
			params.put("autograph", autograph);
		}
		if (!TextUtils.isEmpty(expand1)) {
			params.put("expand1", expand1);
		}
		if (!TextUtils.isEmpty(expand2)) {
			params.put("expand2", expand2);
		}
		if (!TextUtils.isEmpty(expand3)) {
			params.put("expand3", expand3);
		}
		if (!TextUtils.isEmpty(expand4)) {
			params.put("expand4", expand4);
		}

		if (!TextUtils.isEmpty(tel)) {
			params.put("tel", tel);
		}
		if (province_id != 0) {
			params.put("province_id", province_id);
		}
		if (city_id != 0) {
			params.put("city_id", city_id);
		}
		if (district_id != 0) {
			params.put("district_id", district_id);
		}
		client.post(Constants.SERVER_API + USEREDIT_EDIT_INFO, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, USEREDIT_EDIT_INFO + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��Ʒ����
	 * 
	 * @param goods_id
	 *            ��Ʒid
	 * @param response
	 */
	public static void goodsinfoInfo(String goods_id,
			final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("goods_id", goods_id);
		client.post(Constants.SERVER_API + GOODSINFO_INFO, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, GOODSINFO_INFO + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��Ʒ��������
	 * 
	 * @param goods_id
	 *            ��Ʒid true
	 * @param level
	 *            ���۵ȼ� 1������ 2������ 3������ false
	 * @param spec_id
	 *            ��Ʒ���id false
	 * @param response
	 */
	public static void goodsevaluationContentsNumber(String goods_id,
			int level, int spec_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("goods_id", goods_id);
		if (level != 0) {
			params.put("level", level);
		}
		if (spec_id != 0) {
			params.put("spec_id", spec_id);
		}
		client.post(Constants.SERVER_API + GOODSEVALUATION_CONTENTS_NUMBER,
				params, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, GOODSEVALUATION_CONTENTS_NUMBER
										+ " result: " + new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��Ʒ�����б�
	 * 
	 * @param goods_id
	 * @param level
	 *            ���۵ȼ���(����Ϊȫ������) 1������ 2������ 3������
	 * @param spec_id
	 * @param response
	 */
	public static void goodsevaluationLists(String goods_id, int level,
			int spec_id, int page, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("goods_id", goods_id);
		if (level != 0) {
			params.put("level", level);
		}
		if (spec_id != 0) {
			params.put("spec_id", spec_id);
		}
		params.put("page", page);
		params.put("pagesize", Constants.PAGESIZE);
		client.post(Constants.SERVER_API + GOODSEVALUATION_LISTS, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, GOODSEVALUATION_LISTS + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * �ղز�Ʒ
	 * 
	 * @param ucode
	 * @param goods_id
	 * @param response
	 */
	public static void collectgoodsCollect(final Context context, String ucode,
			String goods_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("goods_id", goods_id);
		client.post(Constants.SERVER_API + COLLECTGOODS_COLLECT, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, COLLECTGOODS_COLLECT + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ȡ���ղ�
	 * 
	 * @param ucode
	 * @param goods_id
	 * @param response
	 */
	public static void collectgoodsCancel(final Context context, String ucode,
			String goods_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("goods_id", goods_id);
		client.post(Constants.SERVER_API + COLLECTGOODS_CANCEL, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, COLLECTGOODS_CANCEL + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * �û����ղ��б�
	 * 
	 * @param ucode
	 * @param page
	 * @param pagesize
	 *            ÿҳ��¼��
	 * @param response
	 */
	public static void collectgoodsLists(final Context context, String ucode,
			int page, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("page", page);
		params.put("pagesize", Constants.PAGESIZE);
		client.post(Constants.SERVER_API + COLLECTGOODS_LISTS, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, COLLECTGOODS_LISTS + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * �����û��Ĺ��ﳵ
	 * 
	 * @param ucode
	 * @param goods_id
	 * @param spec_id���id
	 * @param specification���
	 * @param shop_id
	 * @param goods_name
	 * @param goods_image
	 * @param quantity
	 *            ����
	 * @param response
	 */
	public static void shopcartaddAdd(final Context context, String ucode,
			String goods_id, String spec_id, String specification,
			String shop_id, String goods_name, String goods_image,
			String quantity, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("goods_id", goods_id);
		params.put("spec_id", spec_id);
		params.put("specification", specification);
		params.put("shop_id", shop_id);
		params.put("goods_name", goods_name);
		params.put("goods_image", goods_image);
		params.put("quantity", quantity);
		client.post(Constants.SERVER_API + SHOPCARTADD_ADD, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, SHOPCARTADD_ADD + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * �û��Ĺ��ﳵ�б�
	 * 
	 * @param ucode
	 * @param page
	 *            ҳ��
	 * @param pagesize
	 *            ÿҳ�ļ�¼��
	 * @param response
	 */
	public static void shopcartlistsLists(final Context context, String ucode,
			int page, int pagesize, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		// params.put("page", page);
		// params.put("pagesize", pagesize);
		client.post(Constants.SERVER_API + SHOPCARTLISTS_LISTS, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, SHOPCARTLISTS_LISTS + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��ַ�б�
	 * 
	 * @param ucode
	 * @param response
	 */
	public static void useraddressLists(final Context context, String ucode,
			final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		client.post(Constants.SERVER_API + USERADDRESS_LISTS, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, USERADDRESS_LISTS + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * 
	 * @param ucode
	 *            ȡ����ַ
	 * @param addr_id��ַid
	 * @param response
	 */
	public static void useraddressCancel(final Context context, String ucode,
			String addr_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("addr_id", addr_id);
		client.post(Constants.SERVER_API + USERADDRESS_CANCEL, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, USERADDRESS_CANCEL + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ����Ĭ�ϵ�ַ
	 * 
	 * @param ucode
	 * @param addr_id
	 *            ��ַid
	 * @param response
	 */
	public static void useraddressSetDefault(final Context context,
			String ucode, String addr_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("addr_id", addr_id);
		client.post(Constants.SERVER_API + USERADDRESS_SET_DEFAULT, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, USERADDRESS_SET_DEFAULT
										+ " result: " + new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ����ջ���ַ
	 * 
	 * @param ucode
	 * @param link_uname
	 * @param tel
	 * @param extend1
	 *            С�����ֵ߽�����
	 * @param address
	 *            ��ϸ��ַ
	 * @param response
	 */
	public static void useraddressAdd(final Context context, String ucode,
			String link_uname, String tel, String extend1, String address,
			final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("link_uname", link_uname);
		params.put("tel", tel);
		params.put("extend1", extend1);
		params.put("address", address);
		client.post(Constants.SERVER_API + USERADDRESS_ADD, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, USERADDRESS_ADD + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * �༭-�ջ���ַ
	 * 
	 * @param ucode
	 * @param addr_id
	 *            ��ַid
	 * @param link_uname
	 * @param tel
	 * @param extend1
	 *            С�����ֵ߽�����
	 * @param address
	 *            ��ϸ��ַ
	 * @param response
	 */
	public static void useraddressEdit(final Context context, String ucode,
			String addr_id, String link_uname, String tel, String extend1,
			String address, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("addr_id", addr_id);
		params.put("link_uname", link_uname);
		params.put("tel", tel);
		params.put("extend1", extend1);
		params.put("address", address);
		client.post(Constants.SERVER_API + USERADDRESS_EDIT, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, USERADDRESS_EDIT + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ɾ�� ѡ�еĹ��ﳵ
	 * 
	 * @param ucode
	 * @param cart_id
	 * @param response
	 */
	public static void shopcartcancelDeleteSelected(final Context context,
			String ucode, String cart_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("cart_id", cart_id);
		client.post(Constants.SERVER_API + SHOPCARTCANCEL_DELETE_SELECTED,
				params, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, SHOPCARTCANCEL_DELETE_SELECTED
										+ " result: " + new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * �޸ĵ������ﳵ������
	 * 
	 * @param ucode
	 * @param cart_id
	 * @param quantity����
	 * @param response
	 */
	public static void shopcarEditQuantity(final Context context, String ucode,
			String cart_id, int quantity, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("cart_id", cart_id);
		params.put("quantity", quantity);
		client.post(Constants.SERVER_API + SHOPCAR_EDIT_QUANTITY, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, SHOPCAR_EDIT_QUANTITY + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ����Ʒ�Ƿ��ղ�
	 * 
	 * @param ucode
	 * @param goods_id
	 * @param response
	 */
	public static void collectgoodsIsCollect(final Context context,
			String ucode, String goods_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("goods_id", goods_id);
		client.post(Constants.SERVER_API + COLLECTGOODS_IS_COLLECT, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, COLLECTGOODS_IS_COLLECT
										+ " result: " + new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ���ж���
	 * 
	 * @param context
	 * @param ucode
	 *            �û���֤��
	 * @param page
	 *            �ڼ�ҳ
	 * @param pagesize
	 *            ÿҳ��С
	 * @param response
	 */
	public static void orderlistsAll(final Context context, String ucode,
			int page, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("page", page);
		params.put("pagesize", Constants.PAGESIZE);
		client.post(Constants.SERVER_API + ORDERLISTS_ALL, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERLISTS_ALL + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * δ֧���Ķ���
	 * 
	 * @param ucode
	 * @param response
	 */
	public static void orderlistsNoPay(final Context context, String ucode,
			int page, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("page", page);
		params.put("pagesize", Constants.PAGESIZE);
		client.post(Constants.SERVER_API + ORDERLISTS_NO_PAY, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERLISTS_NO_PAY + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * δ�ջ��Ķ���
	 * 
	 * @param ucode
	 * @param response
	 */
	public static void orderlistsNoGet(final Context context, String ucode,
			int page, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("page", page);
		params.put("pagesize", Constants.PAGESIZE);
		client.post(Constants.SERVER_API + ORDERLISTS_NO_GET, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERLISTS_NO_GET + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * δ���۵Ķ���
	 * 
	 * @param ucode
	 * @param response
	 */
	public static void orderlistsNoContents(final Context context,
			String ucode, int page, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("page", page);
		params.put("pagesize", Constants.PAGESIZE);
		client.post(Constants.SERVER_API + ORDERLISTS_NO_CONTENTS, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERLISTS_NO_CONTENTS + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��ɵĶ���
	 * 
	 * @param ucode
	 * @param response
	 */
	public static void orderlistsIsOver(final Context context, String ucode,
			int page, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("page", page);
		params.put("pagesize", Constants.PAGESIZE);
		client.post(Constants.SERVER_API + ORDERLISTS_IS_OVER, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERLISTS_IS_OVER + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��������Ķ������˿��У�
	 * 
	 * @param ucode
	 * @param response
	 */
	public static void orderlistsApplying(final Context context, String ucode,
			int page, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("page", page);
		params.put("pagesize", Constants.PAGESIZE);
		client.post(Constants.SERVER_API + ORDERLISTS_APPLY_ING, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERLISTS_APPLY_ING + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��Ʒ�б�
	 * 
	 * @param shop_id
	 * @param cate_id
	 *            ��ǰ���ࣨ���һ�����ࣩ
	 * @param cate_id_1
	 *            �������ࣨһ�����ࣩ
	 * @param cate_id_2
	 * @param cate_id_3
	 * @param cate_id_4
	 * @param select
	 *            �����ؼ���
	 * @param page
	 *            ҳ��
	 * @param pagesize
	 *            ÿҳ��¼��
	 * @param sort_price
	 *            �۸����� 1������ 2�� ����
	 * @param sort_sales
	 *            �������� 1������ 2�� ����
	 * @param response
	 */
	public static void goodslistsLists(String shop_id, int cate_id,
			int cate_id_1, int cate_id_2, int cate_id_3, int cate_id_4,
			String select, int page, int pagesize, int sort_price,
			int sort_sales, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		if (!TextUtils.isEmpty(shop_id)) {
			params.put("shop_id", shop_id);
		}
		if (cate_id != 0) {
			params.put("cate_id", cate_id);
		}
		if (cate_id_1 != 0) {
			params.put("cate_id_1", cate_id_1);
		}
		if (cate_id_2 != 0) {
			params.put("cate_id_2", cate_id_2);
		}
		if (cate_id_3 != 0) {
			params.put("cate_id_3", cate_id_3);
		}
		if (cate_id_4 != 0) {
			params.put("cate_id_4", cate_id_4);
		}

		if (!TextUtils.isEmpty(select)) {
			params.put("select", select);
		}
		if (page != 0) {
			params.put("page", page);
		}
		if (pagesize != 0) {
			params.put("pagesize", pagesize);
		}
		if (sort_price != 0) {
			params.put("sort_price", sort_price);
		}
		if (sort_sales != 0) {
			params.put("sort_sales", sort_sales);
		}
		if (DEBUG) {
			Log.e(TAG, GOODSLISTS_LISTS + " params: "
					+params.toString());
		}
		client.post(Constants.SERVER_API + GOODSLISTS_LISTS, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, GOODSLISTS_LISTS + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									PageInfo pageInfo=jsonResult.getPaging();
									if(pageInfo!=null){
										if(pageInfo.getNumberofpage()*pageInfo.getPage()>=pageInfo.getTotalcount()){
											//û�и���������
											response.onRese(jsonResult.getData(),
													ResponseHandler.STATE_EMPTY);
										}else{
											response.onRese(jsonResult.getData(),
													ResponseHandler.STATE_OK);
										}
									}
									

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��Ϣ�б�
	 * 
	 * @param ucode
	 *            �û�У����
	 * @param shop_id�̼�id
	 *            (�Ǳش�)
	 * @param response
	 */
	public static void leavemsgMsg(final Context context, String ucode,
			String shop_id, final ResponseHandler response) {
		//AsyncHttpClient client = new AsyncHttpClient();
		SyncHttpClient client = new SyncHttpClient();

		 
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		if (!TextUtils.isEmpty(shop_id)) {
			params.put("shop_id", shop_id);
		}
		client.post(Constants.SERVER_API + LEAVEMSG_MSG, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, LEAVEMSG_MSG + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ������Ϣ
	 * 
	 * @param ucode
	 * @param content
	 *            ����
	 * @param response
	 */
	public static void leavemsgSendMsg(final Context context, String ucode,
			String content, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		// �ȹ̶���1����������ָ���̼�shop_id
		params.put("shop_id", 1);
		params.put("content", content);
		client.post(Constants.SERVER_API + LEAVEMSG_SEND_MSG, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, LEAVEMSG_SEND_MSG + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * С������
	 * 
	 * @param ucode
	 * @param area
	 *            ����
	 * @param response
	 */
	public static void areaLists(final Context context, String area,
			final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("area", area);
		client.post(Constants.SERVER_API + AREA_LISTS, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, AREA_LISTS + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��ȡ�˷�
	 * 
	 * @param response
	 */
	public static void shippingfeeFee(final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		client.post(Constants.SERVER_API + SHIPPINGFEE_FEE, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, SHIPPINGFEE_FEE + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��������������
	 * 
	 * @param ucode
	 * @param order_id
	 *            ����id
	 * @param response
	 */
	public static void orderinfoInfo(final Context context, String ucode,
			String order_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("order_id", order_id);
		client.post(Constants.SERVER_API + ORDERINFO_INFO, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERINFO_INFO + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��Ӷ���
	 * 
	 * @param ucode
	 * @param cart_id
	 *            ���ﳵid ���ö��ŷָ� ��
	 * @param pay_way
	 *            ֧����ʽ�� 1 : ����֧�� 2�� ��������
	 * @param delivery_type���ͷ�ʽ
	 *            1:�ͻ����� ,��ʱ���� ���и�ʱ��Σ� 2���ͻ����� ��ʱ���� 2:������ȡ
	 * 
	 * @param best_start_time������Ϳ�ʼʱ��
	 * @param bend_end_tiem������ͽ���ʱ��
	 * @param link_name
	 *            ��ϵ��
	 * @param tel
	 *            �绰
	 * @param area
	 *            ����
	 * @param address��ϸ��ַ
	 * @param response
	 */
	public static void orderaddAdd(final Context context, String ucode,
			String cart_id, int pay_way, int delivery_type,
			int best_start_time, int best_end_time, String link_name,
			String tel, String area, String area_id, String address,
			final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("cart_id", cart_id);
		params.put("pay_way", pay_way);
		params.put("delivery_type", delivery_type);
		if (best_start_time != 0) {
			params.put("best_start_time", best_start_time);
		}
		if (best_end_time != 0) {
			params.put("best_end_time", best_end_time);
		}

		params.put("link_name", link_name);
		params.put("tel", tel);
		params.put("area", area);
		params.put("area_id", area_id);
		params.put("address", address);
		if (DEBUG) {
			Log.e(TAG, ORDERADD_ADD + " params: "
					+ params.toString());
		}
		client.post(Constants.SERVER_API + ORDERADD_ADD, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERADD_ADD + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��֧���Ķ���ʧЧʱ��
	 * 
	 * @param ucode
	 * @param response
	 */
	public static void orderLoseTime(final Context context, String ucode,
			final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		client.post(Constants.SERVER_API + ORDER_LOSE_TIME, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDER_LOSE_TIME + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
//									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
//										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
//									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ȡ������
	 * 
	 * @param ucode
	 * @param order_id������
	 * @param response
	 */
	public static void orderstatusOrderCancel(final Context context,
			String ucode, String order_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("order_id", order_id);
		client.post(Constants.SERVER_API + ORDERSTATUS_ORDER_CANCEL, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERSTATUS_ORDER_CANCEL
										+ " result: " + new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ȷ���ջ�
	 * 
	 * @param ucode
	 * @param order_id����id
	 * @param response
	 */
	public static void ordersureSure(final Context context, String ucode,
			String order_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("order_id", order_id);
		client.post(Constants.SERVER_API + ORDERSURE_SURE, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERSURE_SURE + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * �����˿�
	 * 
	 * @param ucode
	 * @param order_id
	 * @param title�������
	 * @param content��������
	 * @param response
	 */
	public static void orderapplyApplying(final Context context, String ucode,
			String order_id, String title, String content,
			final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("order_id", order_id);
		params.put("title", title);
		params.put("content", content);
		client.post(Constants.SERVER_API + ORDERAPPLY_APPLYING, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERAPPLY_APPLYING + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ȡ�������˿�
	 * 
	 * @param ucode
	 * @param order_id����id
	 * @param response
	 */
	public static void orderapplyCancelOrder(final Context context,
			String ucode, String order_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("order_id", order_id);
		client.post(Constants.SERVER_API + ORDERAPPLY_CANCEL_ORDER, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERAPPLY_CANCEL_ORDER
										+ " result: " + new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ���Ӳ�Ʒ����
	 * 
	 * @param ucode
	 *            ��¼�����
	 * @param order_id
	 *            ����id
	 * @param goods_id
	 *            ��Ʒid �ö��ŷָ�
	 * @param spec_id
	 *            ���id �ö��ŷָ�
	 * @param level
	 *            ���۵ȼ� 1������ 2������ 3������, �ö��ŷָ�
	 * @param content
	 *            ���۵������ö��ŷָ�
	 * @param response
	 */
	public static void goodsevaluationAddEval(final Context context,
			String ucode, String order_id, String goods_id, String spec_id,
			String level, String content, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("order_id", order_id);
		params.put("goods_id", goods_id);
		params.put("spec_id", spec_id);
		params.put("level", level);
		params.put("content", content);
		if (DEBUG) {
			Log.e(TAG,
					GOODSEVALUATION_ADD_EVAL + " params:" + params.toString());
		}
		client.post(Constants.SERVER_API + GOODSEVALUATION_ADD_EVAL, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, GOODSEVALUATION_ADD_EVAL
										+ " result: " + new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ɾ������
	 * 
	 * @param ucode
	 *            �û�У����
	 * @param order_id
	 *            ����id
	 * @param response
	 */
	public static void orderstatusHide(final Context context, String ucode,
			String order_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("order_id", order_id);
		client.post(Constants.SERVER_API + ORDERSTATUS_HIDE, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERSTATUS_HIDE + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ϵͳ�̼���Ӫʱ��
	 * 
	 * @param response
	 */

	public static void shopInTime(final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		client.post(Constants.SERVER_API + SHOP_IN_TIME, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, SHOP_IN_TIME + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * �ٴι���
	 * 
	 * @param ucode
	 *            �û�У����
	 * @param order_id
	 *            ����id
	 * @param response
	 */
	public static void orderaddBuyAgain(final Context context, String ucode,
			String order_id, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("order_id", order_id);
		client.post(Constants.SERVER_API + ORDERADD_BUY_AGAIN, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, ORDERADD_BUY_AGAIN + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ���ﳵ��Ʒ����
	 * 
	 * @param context
	 * @param ucode
	 * @param response
	 */
	public static void shopcartlistsCartNumber(final Context context,
			String ucode, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		client.post(Constants.SERVER_API + SHOPCARTLISTS_CART_NUMBER, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, SHOPCARTLISTS_CART_NUMBER
										+ " result: " + new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									// if (jsonResult.getStatus().getCode() ==
									// FAIL_LOGIN_OUT) {
									// CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									// }
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ֧��������(����֧��������Ϣ)
	 * 
	 * @param context
	 * @param ucode�û�У����
	 * @param order_number
	 *            ������
	 * @param response
	 */
	public static void payalipayDoEncrypt(final Context context, String ucode,
			String order_number, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		params.put("order_number", order_number);
		client.post(Constants.SERVER_API + PAYALIPAY_DO_ENCRYPT, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, PAYALIPAY_DO_ENCRYPT + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
									if (jsonResult.getStatus().getCode() == FAIL_LOGIN_OUT) {
										CommUtils.lauchLoginActivityByLogingOtherPlace(context);
									}
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}

	/**
	 * ��ȡ���°汾��Ϣ
	 * 
	 * @param context
	 * @param user_type
	 *            �汾���� 1���û� 2���̼� 3������
	 * @param response
	 */
	public static void appversionAppNew(final Context context, int user_type,
			final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("device_type", 1);
		params.put("user_type", user_type);
		client.post(Constants.SERVER_API + APPVERSION_APP_NEW, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
						if (arg2 != null && arg2.length > 0) {
							JsonResult jsonResult = JSON.parseObject(
									new String(arg2), JsonResult.class);
							if (DEBUG) {
								Log.e(TAG, APPVERSION_APP_NEW + " result: "
										+ new String(arg2));
							}
							if (jsonResult != null) {
								if (jsonResult.getStatus().getCode() == SUCESS_CODE) {
									response.onRese(jsonResult.getData(),
											ResponseHandler.STATE_OK);

								} else {
									response.onRese(jsonResult.getStatus()
											.getMsg(),
											ResponseHandler.STATE_FAIL);
								}
							}
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						response.onRese(null, ResponseHandler.STATE_ERROR);
					}
				});
	}
}
