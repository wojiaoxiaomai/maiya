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
 * @项目名称：Maya
 * @类名称：API
 * @类描述：
 * @创建人：李波
 * @创建时间：2015-9-5 上午10:37:25
 * @版本号：v1.0
 * 
 */
public class API {
	private static final boolean DEBUG = true;
	// 请求成功码
	private static final int SUCESS_CODE = 0;
	// 登录超时码
	private static final int FAIL_LOGIN_OUT = 10001;
	private static final String TAG = "API";
	/**
	 * 注册验证码-获取
	 */
	private static final String REGIST_CODE = "regist/code";
	/**
	 * 验证手机号--是否注册
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
	 * 注册的时候获取验证码
	 * 
	 * @param phone
	 *            手机号码
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
	 * 验证手机号--是否注册
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
	 * 验证-手机-验证码
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
	 * 注册手机-填充资料
	 * 
	 * @param phone
	 *            手机号
	 * @param password
	 *            密码
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
	 * 登录
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
	 * 忘记-密码-获取验证码
	 * 
	 * @param phone
	 *            手机号
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
	 * 找回密码-重置密码
	 * 
	 * @param phone手机号
	 * @param code验证码
	 * @param password密码
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
	 * 修改密码
	 * 
	 * @param ucode
	 *            用户校验码
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
	 * 购买订单数
	 * 
	 * @param ucode用户效验码
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
	 * 获取具体的广告位-列表
	 * 
	 * @param type_id
	 *            1:首页头部广告位 2： 中部广告位 3尾部广告位
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
	 * 某个广告下的产品
	 * 
	 * @param ad_id
	 *            广告位id
	 * @param currentpage  当前页
	 *             
	 * @param response
	 */
	public static void adgoodsLists(String ad_id,final String currentpage, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ad_id", ad_id);
		if(!currentpage.equals("-1")){
			//广告页里面的推荐商品，进行分页处理
			params.put("page",currentpage);
			params.put("type", "1");
		}else{
			//app首页数据30个
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
									//==>2015.12.30 把翻页数据也返回
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
	 * 分类列表
	 * 
	 * @param cate_id
	 *            分类id 不是必传
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
	 * 某个广告的详情
	 * 
	 * @param ad_id
	 *            广告id
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
	 * 修改基本资料（传int型的为0的话表示不传）
	 * 
	 * @param ucode
	 *            必传
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
	 *            传0标识不传
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
	 * 产品详情
	 * 
	 * @param goods_id
	 *            产品id
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
	 * 产品评论数量
	 * 
	 * @param goods_id
	 *            产品id true
	 * @param level
	 *            评价等级 1：好评 2：中评 3：差评 false
	 * @param spec_id
	 *            产品规格id false
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
	 * 产品评价列表
	 * 
	 * @param goods_id
	 * @param level
	 *            评价等级：(不传为全部评论) 1：好评 2：中评 3：差评
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
	 * 收藏产品
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
	 * 取消收藏
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
	 * 用户的收藏列表
	 * 
	 * @param ucode
	 * @param page
	 * @param pagesize
	 *            每页记录数
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
	 * 增加用户的购物车
	 * 
	 * @param ucode
	 * @param goods_id
	 * @param spec_id规格id
	 * @param specification规格
	 * @param shop_id
	 * @param goods_name
	 * @param goods_image
	 * @param quantity
	 *            数量
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
	 * 用户的购物车列表
	 * 
	 * @param ucode
	 * @param page
	 *            页码
	 * @param pagesize
	 *            每页的记录数
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
	 * 地址列表
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
	 *            取消地址
	 * @param addr_id地址id
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
	 * 设置默认地址
	 * 
	 * @param ucode
	 * @param addr_id
	 *            地址id
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
	 * 添加收货地址
	 * 
	 * @param ucode
	 * @param link_uname
	 * @param tel
	 * @param extend1
	 *            小区或者街道名字
	 * @param address
	 *            详细地址
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
	 * 编辑-收货地址
	 * 
	 * @param ucode
	 * @param addr_id
	 *            地址id
	 * @param link_uname
	 * @param tel
	 * @param extend1
	 *            小区或者街道名字
	 * @param address
	 *            详细地址
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
	 * 删除 选中的购物车
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
	 * 修改单个购物车的数量
	 * 
	 * @param ucode
	 * @param cart_id
	 * @param quantity数量
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
	 * 检测产品是否收藏
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
	 * 所有订单
	 * 
	 * @param context
	 * @param ucode
	 *            用户验证码
	 * @param page
	 *            第几页
	 * @param pagesize
	 *            每页大小
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
	 * 未支付的订单
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
	 * 未收货的订单
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
	 * 未评价的订单
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
	 * 完成的订单
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
	 * 正在申请的订单（退款中）
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
	 * 产品列表
	 * 
	 * @param shop_id
	 * @param cate_id
	 *            当前分类（最后一级分类）
	 * @param cate_id_1
	 *            顶级分类（一级分类）
	 * @param cate_id_2
	 * @param cate_id_3
	 * @param cate_id_4
	 * @param select
	 *            搜索关键字
	 * @param page
	 *            页码
	 * @param pagesize
	 *            每页记录数
	 * @param sort_price
	 *            价格排序 1：降序 2： 升序
	 * @param sort_sales
	 *            销量排序 1：降序 2： 升序
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
											//没有更多数据了
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
	 * 消息列表
	 * 
	 * @param ucode
	 *            用户校验码
	 * @param shop_id商家id
	 *            (非必传)
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
	 * 发送消息
	 * 
	 * @param ucode
	 * @param content
	 *            内容
	 * @param response
	 */
	public static void leavemsgSendMsg(final Context context, String ucode,
			String content, final ResponseHandler response) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("ucode", ucode);
		// 先固定传1，后续有用指定商家shop_id
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
	 * 小区社区
	 * 
	 * @param ucode
	 * @param area
	 *            名字
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
	 * 获取运费
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
	 * （三）订单详情
	 * 
	 * @param ucode
	 * @param order_id
	 *            订单id
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
	 * 添加订单
	 * 
	 * @param ucode
	 * @param cart_id
	 *            购物车id （用逗号分隔 ）
	 * @param pay_way
	 *            支付方式： 1 : 在线支付 2： 货到付款
	 * @param delivery_type配送方式
	 *            1:送货上门 ,定时派送 （有个时间段） 2：送货上门 及时派送 2:上门自取
	 * 
	 * @param best_start_time最佳配送开始时间
	 * @param bend_end_tiem最近配送结束时间
	 * @param link_name
	 *            联系人
	 * @param tel
	 *            电话
	 * @param area
	 *            区域
	 * @param address详细地址
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
	 * 待支付的订单失效时间
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
	 * 取消订单
	 * 
	 * @param ucode
	 * @param order_id订单号
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
	 * 确定收货
	 * 
	 * @param ucode
	 * @param order_id订单id
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
	 * 申请退款
	 * 
	 * @param ucode
	 * @param order_id
	 * @param title申请标题
	 * @param content申请内容
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
	 * 取消申请退款
	 * 
	 * @param ucode
	 * @param order_id订单id
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
	 * 增加产品评论
	 * 
	 * @param ucode
	 *            登录检测码
	 * @param order_id
	 *            订单id
	 * @param goods_id
	 *            产品id 用逗号分隔
	 * @param spec_id
	 *            规格id 用逗号分隔
	 * @param level
	 *            评价等级 1：好评 2：中评 3：差评, 用逗号分隔
	 * @param content
	 *            评价的内容用逗号分隔
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
	 * 删除订单
	 * 
	 * @param ucode
	 *            用户校验码
	 * @param order_id
	 *            订单id
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
	 * 系统商家运营时间
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
	 * 再次购买
	 * 
	 * @param ucode
	 *            用户校验码
	 * @param order_id
	 *            订单id
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
	 * 购物车产品件数
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
	 * 支付宝加密(返回支付订单信息)
	 * 
	 * @param context
	 * @param ucode用户校验码
	 * @param order_number
	 *            订单号
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
	 * 获取最新版本信息
	 * 
	 * @param context
	 * @param user_type
	 *            版本类型 1：用户 2：商家 3：其他
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
