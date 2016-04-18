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
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�PersonalOrderDetailActivity
 * @���������ҵ� �������飨����������Ѹ���˿����Լ��ύ������ĵȵĶ�������ҳ��
 * @�����ˣ��
 * @����ʱ�䣺2015-8-9 ����8:15:00
 * @�汾�ţ�v1.0
 * 
 * @record
 * //==>2016.1.5 
 *   1.�������ʱ��
 */
public class PersonalOrderDetailActivity extends BaseActivity {
	private static final String TAG = "PersonalOrderDetailActivity";
	private ScrollView mScrollView;
	private ListView mListView;
	private List<PersonalOrderDetail> mList;
	private PersonalOrderDetailAdapter mAdapter;
	private PersonalOrder mOrder = null;// ��������
	private TextView order_number_txt;// �������
	private TextView create_time_txt;// �µ�ʱ��
	private TextView pay_way_txt;// ֧����ʽ
	private TextView hav_status_txt;// 1: δ���� 2�������� 3�����ջ�
	private TextView delivery_type_txt;// ���ͷ�ʽ
	private TextView delivery_time;//����ʱ�� 2016.1.5

	private TextView link_uname_txt;// ����
	private TextView tel_txt;// �绰
	private TextView area_txt;// ����
	private TextView address_txt;// ��ϸ��ַ

	private TextView goods_amount_txt;// ��Ʒ�ܽ��
	private TextView shipping_fee_txt;// �˷�
	private TextView shoud_pay_txt;// Ӧ�����

	private int orderType = Constants.ORDER_NO_PAY;// ������״̬�����߲�ͬ��״̬����ͬ�Ĳ���
	private Button mFirstButton;// ��һ����ť������orderType��ͬ����ͬ
	private Button mSecondButton;

	private View mApplyView;// �˿�����ݸ��ؼ�
	private EditText mApplayEdit;// �����˿�����
	private Button mApplayBtn;// �����˿��ύ

	private boolean mIsBuyAagin = false;// �Ƿ����ٴι�����Ʒ
	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				Result resultObj = new Result((String) msg.obj);
				String resultStatus = resultObj.resultStatus;

				// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
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
					// �ж�resultStatus Ϊ�ǡ�9000����������֧��ʧ��
					// ��8000��
					// ����֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
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
				// "�����Ϊ��" + msg.obj, Toast.LENGTH_SHORT).show();
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
	 * ��֧������ʧЧʱ��ӿڣ� order/lose_time
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
	 * ��ʼ����ť
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
			// ���ջ��������������1.֧��������ʾȷ���ջ��������˿� 2.����������ʾ������...

			break;
		case Constants.ORDER_NO_COMMENTS:
			// ������
			mFirstButton.setText(R.string.go_comments);
			mSecondButton.setVisibility(View.GONE);
			break;
		case Constants.ORDER_IS_OVER:
			// �����
			mFirstButton.setText(R.string.buy_again);
			mSecondButton.setText(R.string.delete);
			break;
		case Constants.ORDER_APPLY_ING:
			// �˿���
			mFirstButton.setText(R.string.cancle_tuikuan);
			mSecondButton.setVisibility(View.GONE);
			break;
		default:
			break;
		}

	}

	/**
	 * ��ʼ�����ÿؼ�
	 */
	private void initMoneyView() {
		goods_amount_txt = (TextView) findViewById(R.id.goods_amount_txt);
		shipping_fee_txt = (TextView) findViewById(R.id.shipping_fee_txt);
		shoud_pay_txt = (TextView) findViewById(R.id.shoud_pay_txt);
	}

	/**
	 * ��ʼ��������Ϣ
	 */
	private void initOrderView() {
		order_number_txt = (TextView) findViewById(R.id.order_number_txt);
		create_time_txt = (TextView) findViewById(R.id.create_time_txt);
		pay_way_txt = (TextView) findViewById(R.id.pay_way_txt);
		hav_status_txt = (TextView) findViewById(R.id.hav_status_txt);
		delivery_type_txt = (TextView) findViewById(R.id.delivery_type_txt);
		//==>2016.1.5 ����ʱʱ��
		delivery_time=(TextView)findViewById(R.id.delivery_time);
		//<==2016.1.5
	}

	/**
	 * ��ʼ����ַ��Ϣ
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
	 * ��������
	 * 
	 * @param ucode
	 * @param order_id
	 *            ����id
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
						// ������Ϣ
						order_number_txt.setText(mOrder.getOrder_number());
						create_time_txt.setText(DateTime
								.getDateAndSecondbyStemp(mOrder
										.getCreate_time()));
						pay_way_txt.setText(Status.getPayWay(mOrder
								.getPay_way()));
						hav_status_txt.setText(Status.getHasStatus(mOrder
								.getHav_status()));
						//==>2016.1.5 ����Ƕ�ʱ���ͣ�����Ҫ��ʾ��ʱ����ʱ��
						if(mOrder.getDelivery_type()==Constants.DELIVERY_TYPE_1){
							//��ʱ����
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
						// ��ַ��Ϣ
						PersonalOrderAddress orderAddress = mOrder
								.getOrder_address();
						if (orderAddress != null) {
							link_uname_txt.setText(orderAddress.getLink_uname());
							tel_txt.setText(orderAddress.getTel());
							area_txt.setText(orderAddress.getArea());
							address_txt.setText(orderAddress.getAddress());
						}
						// ��Ʒ��Ϣ

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

						// ������Ϣ
						goods_amount_txt.setText(CommUtils.getMoney(mOrder
								.getGoods_amount() + ""));
						shipping_fee_txt.setText(CommUtils.getMoney(mOrder
								.getShipping_fee() + ""));

						shoud_pay_txt.setText(CommUtils.getMoney((mOrder
								.getGoods_amount() + mOrder.getShipping_fee())
								+ ""));

						// ��ʼ����ť ���ջ��������������1.֧��������ʾȷ���ջ��������˿� 2.����������ʾ������...
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
						// ��ʼ�� �������������� 1.������Ч 2.������Ч
						if (orderType == Constants.ORDER_NO_PAY) {
							if (System.currentTimeMillis() / 1000 > MyApplication
									.getInstance().getLostTime()
									+ Long.parseLong(mOrder.getCreate_time())) {
								// ˵�������Ѿ�ʧЧ��,��ִ��ȡ������

								mFirstButton.setText(R.string.order_invalid);
								mFirstButton.setEnabled(false);
								mSecondButton.setText(R.string.cancle_order);
							} else {
								// ������Ч������֧��
								mFirstButton.setText(R.string.pay_now);
								mSecondButton.setText(R.string.cancle_order);
							}
						}

						if (mIsBuyAagin == true) {
							// ������ٴι���Ļ�����ʼ��order_type;
							int pay_way = mOrder.getPay_way();
							if (pay_way == Constants.PAY_WAY_1) {
								// ֧����֧��
								orderType = Constants.ORDER_NO_PAY;
								mFirstButton.setText(R.string.pay_now);
								mSecondButton.setText(R.string.cancle_order);
							} else if (pay_way == Constants.PAY_WAY_2) {
								// ��������
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
				// ��֧��
				if (System.currentTimeMillis() / 1000 > MyApplication
						.getInstance().getLostTime()
						+ Long.parseLong(mOrder.getCreate_time())) {
					// ˵�������Ѿ�ʧЧ��,�޷����

					if (DEBUG) {
						Log.e(TAG, "order_no_pay is losttime");
					}

				} else {
					// ������Ч������֧��(֧��������)
					// showToast(R.string.pay_now);
					payalipayDoEncrypt(mOrder.getOrder_number());
				}
				break;
			case Constants.ORDER_NO_GET:
				// ���ջ�(֧����֧���ģ�btn1:ȷ���ջ���btn2.�����˿��������ľ�ֻ��ʾ��������...������������)

				if (mOrder.getPay_way() == Constants.PAY_WAY_1) {
					// ���������֧����,ȷ���ջ�
					ordersureSure(mOrder.getOrder_id());
				} else if (mOrder.getPay_way() == Constants.PAY_WAY_2) {
					// ��������,����������ֻ��ʾ������..
				}
				break;
			case Constants.ORDER_NO_COMMENTS:
				// ������
				MyApplication.getInstance().setPersonalOrderCommentsList(mList);
				Intent it = new Intent(this,
						PersonalOrderCommentsActivity.class);
				startActivity(it);
				break;
			case Constants.ORDER_APPLY_ING:
				// ȡ�������˿�
				orderapplyCancelOrder(mOrder.getOrder_id());
				break;
			case Constants.ORDER_IS_OVER:
				// ����ɵĶ������������ٴι���(ԭ�����ڵ���Ʒ������ӵ��µĶ����С���������ԭ��Ʒ���ݣ�ԭ�����·��İ�ť״̬��ΪȻ���ر�ע��ģ������û�����ڴι����ʱ��Ҫ�ж�ʱ��
				// �����Ӫҵʱ������ Ҫ���������ʾ��
				// �Բ����ѳ���Ӫҵʱ�䣬����XXX-XXX���µ���Ȼ��ص��ҵĶ���ҳ�档
				// ͬʱ��Ҫ�ж�һ�� ԭ�����ڵ���Ʒ�Ƿ��л��������һ����Ʒȱ�������������ʾ��ԭ�����ڲ�����Ʒȱ�����������µ�����
				// long currentTime = System.currentTimeMillis() / 1000;
				// if (currentTime < MyApplication.getInstance().getBeginTime()
				// || currentTime > MyApplication.getInstance()
				// .getEndTime()) {
				// // ����Ӫҵʱ�䷶Χ
				// showToast(R.string.extend_time);
				// return;
				// }

				if (DateTime.getCurrentTime() > MyApplication.getInstance()
						.getBeginTime()
						&& DateTime.getCurrentTime() < MyApplication
								.getInstance().getEndTime()) {
					// ��Ӫҵ��Χ֮��
					orderaddBuyAgain(mOrder.getOrder_id());
				} else {
					// ����Ӫҵʱ�䷶Χ
					showToast(R.string.extend_time);
				}

				break;
			default:
				break;
			}
			break;
		case R.id.btn_2:
			// �ڶ�����ť
			switch (orderType) {
			case Constants.ORDER_NO_PAY:
				if (System.currentTimeMillis() / 1000 > MyApplication
						.getInstance().getLostTime()
						+ Long.parseLong(mOrder.getCreate_time())) {
					// ������ЧЧ����µ�ȡ������
					orderstatusOrderCancel(mOrder.getOrder_id());

				} else {
					// ������Ч����µ�ȡ������
					orderstatusOrderCancel(mOrder.getOrder_id());
				}
				break;
			case Constants.ORDER_NO_GET:
				// ���ջ�
				if (mOrder.getPay_way() == Constants.PAY_WAY_1) {
					// ���������֧���ģ����������˿�

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
					// ��������,������
				}

				break;
			case Constants.ORDER_IS_OVER:
				// ����ɵĶ�����������ɾ����������
				orderstatusHide(mOrder.getOrder_id());
				break;
			default:
				break;
			}
			break;

		case R.id.apply_btn:
			// �����˿�
			String content = mApplayEdit.getText().toString();
			orderapplyApplying(mOrder.getOrder_id(), content);
			break;

		case R.id.back_img:
			// ���ذ�ť (�������ؼ�һ���Ĵ���)��Ҫ���⴦������� ��������Ļ�����ת�����ջ�
			// ҳ�棬���������֧������û��֧���Ļ���ת��������
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
	 * �ر�acitivity�����¼������ ��������Ļ�����ת�����ջ� ҳ�棬���������֧������û��֧���Ļ���ת��������
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
	 * ȡ������
	 * 
	 * @param ucode
	 * @param order_id������
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
							// ����ȡ���ɹ���ˢ��ҳ��
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
	 * ȷ���ջ�
	 * 
	 * @param ucode
	 * @param order_id����id
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
							// �ջ��ɹ���ˢ��ҳ������������
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
	 * �����˿�
	 * 
	 * @param ucode
	 * @param order_id
	 * @param title�������
	 * @param content��������
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
							// �����˿��ύ�ɹ�
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
	 * ȡ�������˿�
	 * 
	 * @param ucode
	 * @param order_id����id
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
							// ȡ�������˿� ���سɹ�
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
	 * ɾ������
	 * 
	 * @param ucode
	 *            �û�У����
	 * @param order_id
	 *            ����id
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
							// ɾ�����������سɹ�
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
	 * �ٴι���
	 * 
	 * @param ucode
	 *            �û�У����
	 * @param order_id
	 *            ����id
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
							// �ٴι��򷵻سɹ�(�����µ�order_id),��ô��ť��Ϊ ��������,ˢ��ҳ��,
							JSONObject jsonObject = JSON.parseObject(data);
							if (jsonObject != null) {
								String order_id = jsonObject
										.getString("order_id");
								if (!TextUtils.isEmpty(order_id)) {
									// ����order_id��ȡ������Ϣ ˢ�½���
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
	 * ֧��������(����֧��������Ϣ)
	 * 
	 * @param context
	 * @param ucode�û�У����
	 * @param order_number
	 *            ������
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
								// ��ʼ����֧��sdk����֧��
								Runnable payRunnable = new Runnable() {

									@Override
									public void run() {
										// ����PayTask ����
										PayTask alipay = new PayTask(
												PersonalOrderDetailActivity.this);
										// ����֧���ӿ�
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
