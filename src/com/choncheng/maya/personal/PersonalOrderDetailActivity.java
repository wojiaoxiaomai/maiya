package com.choncheng.maya.personal;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.contants.Status;
import com.choncheng.maya.pay.PayInfo;
import com.choncheng.maya.pay.PayUtils;
import com.choncheng.maya.pay.Result;
import com.choncheng.maya.shoppingcart.GoodsDetailActivity;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.CommUtils;
import com.choncheng.maya.utils.DateTime;
import com.choncheng.maya.utils.OrderUtils;

/**
 * 
 * @项目名称：Maya
 * @类名称：PersonalOrderDetailActivity
 * @类描述：我的 订单详情（包括待付款，已付款，退款中以及提交订单后的等的订单详情页）
 * @创建人：李波
 * @创建时间：2015-8-9 上午8:15:00
 * @版本号：v1.0
 * 
 * @record
 * //==>2016.1.5 
 *   1.添加派送时间
 */
public class PersonalOrderDetailActivity extends BaseActivity {
	private static final String TAG = "PersonalOrderDetailActivity";
	private ScrollView mScrollView;
	private ListView mListView;
	private List<PersonalOrderDetail> mList;
	private PersonalOrderDetailAdapter mAdapter;
	private PersonalOrder mOrder = null;// 订单详情
	private TextView order_number_txt;// 订单编号
	private TextView create_time_txt;// 下单时间
	private TextView pay_way_txt;// 支付方式
	private TextView hav_status_txt;// 1: 未发货 2：发货中 3：已收货
	private TextView delivery_type_txt;// 派送方式
	private TextView delivery_time;//派送时间 2016.1.5

	private TextView link_uname_txt;// 姓名
	private TextView tel_txt;// 电话
	private TextView area_txt;// 区域
	private TextView address_txt;// 详细地址

	private TextView goods_amount_txt;// 商品总金额
	private TextView shipping_fee_txt;// 运费
	private TextView shoud_pay_txt;// 应付金额

	private int orderType = Constants.ORDER_NO_PAY;// 订单的状态，更具不同的状态做不同的操作
	private Button mFirstButton;// 第一个按钮，更具orderType不同而不同
	private Button mSecondButton;

	private View mApplyView;// 退款的内容父控件
	private EditText mApplayEdit;// 申请退款内容
	private Button mApplayBtn;// 申请退款提交

	private boolean mIsBuyAagin = false;// 是否是再次购买商品
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				Result resultObj = new Result((String) msg.obj);
				String resultStatus = resultObj.resultStatus;

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					showToast(R.string.pay_sucess);
					Intent it = new Intent(PersonalOrderDetailActivity.this,
							PersonalOrderActivity.class);
					it.putExtra(Extra.ORDER_TYPE, Constants.ORDER_NO_GET);
					it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(it);
					finish();

				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”
					// 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						showToast(R.string.pay_suring);
					} else {
						showToast(R.string.pay_fail);
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				// Toast.makeText(PersonalOrderDetailActivity.this,
				// "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_order_detail_activity);
		initView();
		if (mUser != null) {
			if (getIntent() != null) {
				orderinfoInfo(mUser.getUcode(),
						getIntent().getStringExtra(Extra.ORDER_ID));
			}
			if (MyApplication.getInstance().getLostTime() == 0) {
				orderLoseTime(mUser.getUcode());
			}
		}

	}

	/**
	 * 待支付订单失效时间接口： order/lose_time
	 */
	private void orderLoseTime(String ucode) {
		API.orderLoseTime(this, ucode, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				if (state == STATE_OK) {
					JSONObject jsonObject = JSON.parseObject(data);
					if (jsonObject != null) {
						MyApplication.getInstance().setLostTime(
								jsonObject.getLongValue("lose_time"));
					}
				}
			}
		});
	}

	@Override
	protected void initView() {
		super.initView();
		// new SetTopView(this, R.string.order_detail, true);
		TextView tiltleTxt = (TextView) findViewById(R.id.title_text);
		tiltleTxt.setText(R.string.order_detail);
		ImageView backImage = (ImageView) findViewById(R.id.back_img);
		backImage.setOnClickListener(this);
		mScrollView = (ScrollView) findViewById(R.id.scroll_view);
		initOrderView();
		initAddressView();
		initListView();
		initMoneyView();
		initBtnView();
	}

	/*
	 * 初始化按钮
	 */
	private void initBtnView() {
		mApplyView = findViewById(R.id.apply_view);
		mApplayEdit = (EditText) findViewById(R.id.apply_edit);
		mApplayBtn = (Button) findViewById(R.id.apply_btn);
		mApplayBtn.setOnClickListener(this);
		mFirstButton = (Button) findViewById(R.id.btn_1);
		mSecondButton = (Button) findViewById(R.id.btn_2);
		mFirstButton.setOnClickListener(this);
		mSecondButton.setOnClickListener(this);
		if (getIntent() != null) {
			orderType = getIntent().getIntExtra(Extra.ORDER_TYPE,
					Constants.ORDER_NO_PAY);
		}
		switch (orderType) {
		case Constants.ORDER_NO_PAY:
			mFirstButton.setText(R.string.pay_now);
			mSecondButton.setText(R.string.cancle_order);

			break;
		case Constants.ORDER_NO_GET:
			// 待收货，有两种情况，1.支付宝，显示确认收货，申请退款 2.货到付款显示发货中...

			break;
		case Constants.ORDER_NO_COMMENTS:
			// 待评价
			mFirstButton.setText(R.string.go_comments);
			mSecondButton.setVisibility(View.GONE);
			break;
		case Constants.ORDER_IS_OVER:
			// 已完成
			mFirstButton.setText(R.string.buy_again);
			mSecondButton.setText(R.string.delete);
			break;
		case Constants.ORDER_APPLY_ING:
			// 退款中
			mFirstButton.setText(R.string.cancle_tuikuan);
			mSecondButton.setVisibility(View.GONE);
			break;
		default:
			break;
		}

	}

	/**
	 * 初始化费用控件
	 */
	private void initMoneyView() {
		goods_amount_txt = (TextView) findViewById(R.id.goods_amount_txt);
		shipping_fee_txt = (TextView) findViewById(R.id.shipping_fee_txt);
		shoud_pay_txt = (TextView) findViewById(R.id.shoud_pay_txt);
	}

	/**
	 * 初始化订单信息
	 */
	private void initOrderView() {
		order_number_txt = (TextView) findViewById(R.id.order_number_txt);
		create_time_txt = (TextView) findViewById(R.id.create_time_txt);
		pay_way_txt = (TextView) findViewById(R.id.pay_way_txt);
		hav_status_txt = (TextView) findViewById(R.id.hav_status_txt);
		delivery_type_txt = (TextView) findViewById(R.id.delivery_type_txt);
		//==>2016.1.5 派送时时间
		delivery_time=(TextView)findViewById(R.id.delivery_time);
		//<==2016.1.5
	}

	/**
	 * 初始化地址信息
	 */
	private void initAddressView() {
		link_uname_txt = (TextView) findViewById(R.id.link_uname_txt);
		tel_txt = (TextView) findViewById(R.id.tel_txt);
		area_txt = (TextView) findViewById(R.id.area_txt);
		address_txt = (TextView) findViewById(R.id.address_txt);
	}

	private void initListView() {
		mListView = (ListView) findViewById(R.id.listview);
		mList = new ArrayList<PersonalOrderDetail>();
		mAdapter = new PersonalOrderDetailAdapter(mList, this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(PersonalOrderDetailActivity.this,
						GoodsDetailActivity.class);
				it.putExtra(Extra.GOODS_ID, mList.get(position).getGoods_id());
				startActivity(it);
				AnimUtil.setFromLeftToRight(PersonalOrderDetailActivity.this);
			}
		});
	};

	/**
	 * 订单详情
	 * 
	 * @param ucode
	 * @param order_id
	 *            订单id
	 */
	private void orderinfoInfo(String ucode, String order_id) {
		showProgressDialog(R.string.loading);
		API.orderinfoInfo(this, ucode, order_id, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					mOrder = JSON.parseObject(data, PersonalOrder.class);
					if (mOrder != null) {
						// 订单信息
						order_number_txt.setText(mOrder.getOrder_number());
						create_time_txt.setText(DateTime
								.getDateAndSecondbyStemp(mOrder
										.getCreate_time()));
						pay_way_txt.setText(Status.getPayWay(mOrder
								.getPay_way()));
						hav_status_txt.setText(Status.getHasStatus(mOrder
								.getHav_status()));
						//==>2016.1.5 如果是定时派送，则需要显示定时派送时间
						if(mOrder.getDelivery_type()==Constants.DELIVERY_TYPE_1){
							//定时派送
							findViewById(R.id.ll_delivery_time).setVisibility(View.VISIBLE);
							long startT=0;
							try{
								startT=Long.parseLong(mOrder.getBest_start_time().trim());
							}catch(Exception e){
								 startT=0;
							}
							if(startT>1000000000){
								String starttime=DateTime.getDateMinutes(startT);
								long endT=0;
								try{
									endT=Long.parseLong(mOrder.getBest_end_time().trim());
								}catch(Exception e){
									endT=0;
								}
								String endtime=DateTime.getDateOnlyMinutes(endT);
								delivery_time.setText(starttime+" - "+endtime);
							}
						}else{
							findViewById(R.id.ll_delivery_time).setVisibility(View.GONE);
						}
						delivery_type_txt.setText(Status.getDeliveryType(mOrder
								.getDelivery_type()));
						//==>2016.1.5
						// 地址信息
						PersonalOrderAddress orderAddress = mOrder
								.getOrder_address();
						if (orderAddress != null) {
							link_uname_txt.setText(orderAddress.getLink_uname());
							tel_txt.setText(orderAddress.getTel());
							area_txt.setText(orderAddress.getArea());
							address_txt.setText(orderAddress.getAddress());
						}
						// 商品信息

						List<PersonalOrderGoods> orderGoodsList = mOrder
								.getGoods_lists();
						mList.clear();
						if (orderGoodsList != null) {
							for (int i = 0; i < orderGoodsList.size(); i++) {
								PersonalOrderDetail orderDetail = new PersonalOrderDetail();

								PersonalOrderGoods orderGoods = orderGoodsList
										.get(i);
								orderDetail.setGoods_id(orderGoods
										.getGoods_id());
								orderDetail.setGoods_image(orderGoods
										.getGoods_image());
								orderDetail.setGoods_name(orderGoods
										.getGoods_name());
								orderDetail.setGoods_price(orderGoods
										.getBuy_price());
								orderDetail.setOld_price(orderGoods
										.getShop_price());
								orderDetail.setQuantity(orderGoods
										.getQuantity());
								orderDetail.setLevel(1);
								orderDetail.setSpec_id(orderGoods
										.getSpecification());
								orderDetail.setOrder_id(orderGoods
										.getOrder_id());
								orderDetail.setOld_price(orderGoods
										.getOld_price());
								orderDetail.setExpend1(orderGoods.getExpend1());
								mList.add(orderDetail);
							}
							mAdapter.notifyDataSetChanged();
						}

						// 费用信息
						goods_amount_txt.setText(CommUtils.getMoney(mOrder
								.getGoods_amount() + ""));
						shipping_fee_txt.setText(CommUtils.getMoney(mOrder
								.getShipping_fee() + ""));

						shoud_pay_txt.setText(CommUtils.getMoney((mOrder
								.getGoods_amount() + mOrder.getShipping_fee())
								+ ""));

						// 初始化按钮 待收货，有两种情况，1.支付宝，显示确认收货，申请退款 2.货到付款显示发货中...
						if (orderType == Constants.ORDER_NO_GET) {
							if (mOrder.getPay_way() == Constants.PAY_WAY_1) {
								mFirstButton.setText(R.string.sure_get);
								mSecondButton.setText(R.string.apply_tuikuan);
							} else if (mOrder.getPay_way() == Constants.PAY_WAY_2) {
								mFirstButton.setText(R.string.deliverying);
								mFirstButton.setEnabled(false);
								mSecondButton.setVisibility(View.GONE);
							}
						}
						// 初始化 待付款的两种情况 1.订单有效 2.订单无效
						if (orderType == Constants.ORDER_NO_PAY) {
							if (System.currentTimeMillis() / 1000 > MyApplication
									.getInstance().getLostTime()
									+ Long.parseLong(mOrder.getCreate_time())) {
								// 说明订单已经失效了,就执行取消订单

								mFirstButton.setText(R.string.order_invalid);
								mFirstButton.setEnabled(false);
								mSecondButton.setText(R.string.cancle_order);
							} else {
								// 订单有效，立即支付
								mFirstButton.setText(R.string.pay_now);
								mSecondButton.setText(R.string.cancle_order);
							}
						}

						if (mIsBuyAagin == true) {
							// 如果是再次购买的话，初始化order_type;
							int pay_way = mOrder.getPay_way();
							if (pay_way == Constants.PAY_WAY_1) {
								// 支付宝支付
								orderType = Constants.ORDER_NO_PAY;
								mFirstButton.setText(R.string.pay_now);
								mSecondButton.setText(R.string.cancle_order);
							} else if (pay_way == Constants.PAY_WAY_2) {
								// 货到付款
								orderType = Constants.ORDER_NO_GET;
								mFirstButton.setText(R.string.deliverying);
								mFirstButton.setEnabled(false);
								mSecondButton.setVisibility(View.GONE);
							}
							// mIsBuyAagin = false;
						}
					}
					break;
				case STATE_FAIL:
					showToast(data);
					break;
				case STATE_ERROR:
					showToast(R.string.http_respone_error);
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (!checkNetwork()) {
			return;
		}
		if (mOrder == null) {
			if (DEBUG) {
				Log.e(TAG, "mOrder is null");
			}
			return;
		}
		switch (v.getId()) {
		case R.id.btn_1:
			switch (orderType) {
			case Constants.ORDER_NO_PAY:
				// 待支付
				if (System.currentTimeMillis() / 1000 > MyApplication
						.getInstance().getLostTime()
						+ Long.parseLong(mOrder.getCreate_time())) {
					// 说明订单已经失效了,无法点击

					if (DEBUG) {
						Log.e(TAG, "order_no_pay is losttime");
					}

				} else {
					// 订单有效，立即支付(支付宝立即)
					// showToast(R.string.pay_now);
					payalipayDoEncrypt(mOrder.getOrder_number());
				}
				break;
			case Constants.ORDER_NO_GET:
				// 待收货(支付宝支付的，btn1:确认收货，btn2.申请退款；货到付款的就只显示“发货中...”，不做操作)

				if (mOrder.getPay_way() == Constants.PAY_WAY_1) {
					// 如果是在线支付的,确认收货
					ordersureSure(mOrder.getOrder_id());
				} else if (mOrder.getPay_way() == Constants.PAY_WAY_2) {
					// 货到付款,不做操作，只显示派送中..
				}
				break;
			case Constants.ORDER_NO_COMMENTS:
				// 待评论
				MyApplication.getInstance().setPersonalOrderCommentsList(mList);
				Intent it = new Intent(this,
						PersonalOrderCommentsActivity.class);
				startActivity(it);
				break;
			case Constants.ORDER_APPLY_ING:
				// 取消申请退款
				orderapplyCancelOrder(mOrder.getOrder_id());
				break;
			case Constants.ORDER_IS_OVER:
				// 已完成的订单，这里是再次购买(原订单内的商品重新添加到新的订单中。订单保留原商品数据，原订单下方的按钮状态变为然后特别注意的！：在用户点击在次购买的时候。要判定时间
				// 如果在营业时间以外 要给予错误提示：
				// 对不起，已超出营业时间，请于XXX-XXX内下单。然后回到我的订单页面。
				// 同时还要判定一下 原订单内的商品是否有货。如果有一个商品缺货。给予错误提示：原订单内部分商品缺货，请重新下单购买。
				// long currentTime = System.currentTimeMillis() / 1000;
				// if (currentTime < MyApplication.getInstance().getBeginTime()
				// || currentTime > MyApplication.getInstance()
				// .getEndTime()) {
				// // 不在营业时间范围
				// showToast(R.string.extend_time);
				// return;
				// }

				if (DateTime.getCurrentTime() > MyApplication.getInstance()
						.getBeginTime()
						&& DateTime.getCurrentTime() < MyApplication
								.getInstance().getEndTime()) {
					// 在营业范围之内
					orderaddBuyAgain(mOrder.getOrder_id());
				} else {
					// 不在营业时间范围
					showToast(R.string.extend_time);
				}

				break;
			default:
				break;
			}
			break;
		case R.id.btn_2:
			// 第二个按钮
			switch (orderType) {
			case Constants.ORDER_NO_PAY:
				if (System.currentTimeMillis() / 1000 > MyApplication
						.getInstance().getLostTime()
						+ Long.parseLong(mOrder.getCreate_time())) {
					// 订单无效效情况下的取消订单
					orderstatusOrderCancel(mOrder.getOrder_id());

				} else {
					// 订单有效情况下的取消订单
					orderstatusOrderCancel(mOrder.getOrder_id());
				}
				break;
			case Constants.ORDER_NO_GET:
				// 待收货
				if (mOrder.getPay_way() == Constants.PAY_WAY_1) {
					// 如果是在线支付的，就是申请退款

					mApplyView.setVisibility(View.VISIBLE);
					mApplayBtn.setVisibility(View.VISIBLE);
					mFirstButton.setVisibility(View.GONE);
					mSecondButton.setVisibility(View.GONE);
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
						}
					});

				} else if (mOrder.getPay_way() == Constants.PAY_WAY_2) {
					// 货到付款,隐藏了
				}

				break;
			case Constants.ORDER_IS_OVER:
				// 已完成的订单，这里是删除订单功能
				orderstatusHide(mOrder.getOrder_id());
				break;
			default:
				break;
			}
			break;

		case R.id.apply_btn:
			// 申请退款
			String content = mApplayEdit.getText().toString();
			orderapplyApplying(mOrder.getOrder_id(), content);
			break;

		case R.id.back_img:
			// 返回按钮 (与物理返回键一样的处理)需要特殊处理，如果是 货到付款的话就跳转到待收货
			// 页面，如果是在线支付并且没有支付的话跳转到待付款
			finishActivity();
			break;
		default:
			break;
		}
	}

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finishActivity();
			return false;
		}
		return false;

	};

	/**
	 * 关闭acitivity处理事件如果是 货到付款的话就跳转到待收货 页面，如果是在线支付并且没有支付的话跳转到待付款
	 */
	private void finishActivity() {
		Intent it = null;
		if (mOrder != null) {
			it = new Intent(this, PersonalOrderActivity.class);
			it.putExtra(Extra.ORDER_TYPE, OrderUtils.getOrderType(mOrder));
		}
		if (it != null) {
			it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(it);
		}
		finish();
		AnimUtil.setFromRightToLeft(this);
	}

	/**
	 * 取消订单
	 * 
	 * @param ucode
	 * @param order_id订单号
	 * @param response
	 */
	private void orderstatusOrderCancel(String order_id) {
		showProgressDialog(R.string.loading_cancle_order);
		API.orderstatusOrderCancel(this, mUser.getUcode(), order_id,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							// 订单取消成功，刷新页面
							showToast(R.string.order_cancle_sucess);

							Intent it = new Intent(
									PersonalOrderDetailActivity.this,
									PersonalOrderActivity.class);
							it.putExtra(Extra.ORDER_TYPE, orderType);
							it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
									| Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(it);
							finish();
							break;
						case STATE_FAIL:
							showToast(data);
							break;

						case STATE_ERROR:
							showToast(R.string.http_respone_error);
							break;
						default:
							break;
						}
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
	private void ordersureSure(String order_id) {
		showProgressDialog(R.string.loading);
		API.ordersureSure(this, mUser.getUcode(), order_id,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							// 收货成功，刷新页面跳到待评价
							showToast(R.string.order_get_sucess);
							Intent it = new Intent(
									PersonalOrderDetailActivity.this,
									PersonalOrderActivity.class);
							it.putExtra(Extra.ORDER_TYPE,
									Constants.ORDER_NO_COMMENTS);
							it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
									| Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(it);
							finish();
							break;
						case STATE_FAIL:
							showToast(data);
							break;

						case STATE_ERROR:
							showToast(R.string.http_respone_error);
							break;
						default:
							break;
						}
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
	private void orderapplyApplying(String order_id, String content) {
		showProgressDialog(R.string.loading);
		API.orderapplyApplying(this, mUser.getUcode(), order_id, " ", content,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							// 申请退款提交成功
							showToast(R.string.order_applay_sucess);
							Intent it = new Intent(
									PersonalOrderDetailActivity.this,
									PersonalOrderActivity.class);
							it.putExtra(Extra.ORDER_TYPE,
									Constants.ORDER_APPLY_ING);
							it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
									| Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(it);
							finish();
							break;
						case STATE_FAIL:
							showToast(data);
							break;

						case STATE_ERROR:
							showToast(R.string.http_respone_error);
							break;
						default:
							break;
						}
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
	private void orderapplyCancelOrder(String order_id) {
		showProgressDialog(R.string.loading);
		API.orderapplyCancelOrder(this, mUser.getUcode(), order_id,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							// 取消申请退款 返回成功
							showToast(R.string.order_applay_cancle_sucess);

							Intent it = new Intent(
									PersonalOrderDetailActivity.this,
									PersonalOrderActivity.class);
							it.putExtra(Extra.ORDER_TYPE,
									Constants.ORDER_NO_GET);
							it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
									| Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(it);
							finish();
							break;
						case STATE_FAIL:
							showToast(data);
							break;

						case STATE_ERROR:
							showToast(R.string.http_respone_error);
							break;
						default:
							break;
						}
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
	private void orderstatusHide(String order_id) {
		showProgressDialog(R.string.loading);
		API.orderstatusHide(this, mUser.getUcode(), order_id,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							// 删除订单，返回成功
							showToast(R.string.order_del_sucess);
							Intent it = new Intent(
									PersonalOrderDetailActivity.this,
									PersonalOrderActivity.class);
							it.putExtra(Extra.ORDER_TYPE, Constants.ORDER_ALL);
							it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
									| Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(it);
							finish();
							break;
						case STATE_FAIL:
							showToast(data);
							break;

						case STATE_ERROR:
							showToast(R.string.http_respone_error);
							break;
						default:
							break;
						}
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
	private void orderaddBuyAgain(String order_id) {
		showProgressDialog(R.string.loading);
		API.orderaddBuyAgain(this, mUser.getUcode(), order_id,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							// 再次购买返回成功(返回新的order_id),那么按钮变为 立即购买,刷新页面,
							JSONObject jsonObject = JSON.parseObject(data);
							if (jsonObject != null) {
								String order_id = jsonObject
										.getString("order_id");
								if (!TextUtils.isEmpty(order_id)) {
									// 更具order_id获取订单信息 刷新界面
									mIsBuyAagin = true;
									if (mUser != null) {
										orderinfoInfo(mUser.getUcode(),
												order_id);
									}
									// mOrder.setOrder_id(order_id);
									// mOrder.setCreate_time(System
									// .currentTimeMillis() / 1000 + "");
									// create_time_txt.setText(DateTime
									// .getDateMinutes());
									// orderType = Constants.ORDER_NO_PAY;
									// mFirstButton.setText(R.string.pay_now);
									// mSecondButton
									// .setText(R.string.cancle_order);
								}
							}

							break;
						case STATE_FAIL:
							showToast(data);
							break;

						case STATE_ERROR:
							showToast(R.string.http_respone_error);
							break;
						default:
							break;
						}
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
	private void payalipayDoEncrypt(String order_number) {
		showProgressDialog(R.string.loading);
		API.payalipayDoEncrypt(this, mUser.getUcode(), order_number,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							final PayInfo payInfo = JSON.parseObject(data,
									PayInfo.class);
							if (payInfo != null) {
								// 开始调用支付sdk进行支付
								Runnable payRunnable = new Runnable() {

									@Override
									public void run() {
										// 构造PayTask 对象
										PayTask alipay = new PayTask(
												PersonalOrderDetailActivity.this);
										// 调用支付接口
										String result = alipay.pay(PayUtils
												.getPayInfo(payInfo));
										Message msg = new Message();
										msg.what = SDK_PAY_FLAG;
										msg.obj = result;
										mHandler.sendMessage(msg);
									}
								};

								Thread payThread = new Thread(payRunnable);
								payThread.start();
							} else {
								showToast(R.string.pay_submit_order_fail);
							}
							break;
						case STATE_FAIL:
							showToast(data);
							break;
						case STATE_ERROR:
							showToast(R.string.http_respone_error);
							break;
						default:
							break;
						}
					}
				});
	}
}
