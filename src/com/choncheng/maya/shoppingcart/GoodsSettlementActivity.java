package com.choncheng.maya.shoppingcart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.R.integer;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.customview.datepicker.adapter.NumericWheelAdapter;
import com.choncheng.maya.customview.datepicker.view.OnWheelScrollListener;
import com.choncheng.maya.customview.datepicker.view.WheelView;
import com.choncheng.maya.personal.PersonalAddress;
import com.choncheng.maya.personal.PersonalAddressActivity;
import com.choncheng.maya.personal.PersonalOrderDetail;
import com.choncheng.maya.personal.PersonalOrderDetailActivity;
import com.choncheng.maya.personal.PersonalOrderDetailAdapter;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.DateTime;
import com.choncheng.maya.utils.DateTimePickDialogUtil;
import com.choncheng.maya.utils.DateTimePickDialogUtil.DateTimeCallback;

/**
 * 
 * @项目名称：Maya
 * @类名称：GoodsSettlementActivity
 * @类描述： 结算
 * @创建人：李波
 * @创建时间：2015-8-12 下午1:57:51
 * @版本号：v1.0
 * 
 */
public class GoodsSettlementActivity extends BaseActivity implements
		DateTimeCallback {
	private static final String TAG = "GoodsSettlementActivity";
	private ListView mListView;
	private List<PersonalOrderDetail> mList;
	private PersonalOrderDetailAdapter mAdapter;

	private Button mGotoBtn;
	private View mAddressContainer;// address父控件
	private View mAddressView;// 地址信息
	private TextView mAddressEmptyTxt;// 地址为空的信息
	private TextView mNameTxt;// 收货人姓名
	private TextView mTelTxt;// 收货人电话
	private TextView mAreaTxt;// 收货人区域
	private TextView mAddressDetailTxt;// 详细地址

	private PersonalAddress mPersonalAddress = null;// 地址信息实体
	private List<ShoppingCart> mShoppingCartList = null;// 购物车列表

	private TextView mAllMoneyTxt;// 总金额
	private TextView mFareTxt;// 运费
	private TextView mFareLimitTxt;// 运费起收点
	private View mDeliveryTypeView;// 派送方式
	private int mDeliVeryType = Constants.DELIVERY_TYPE_2;// 默认即时派送
	private TextView mDeliveryTypeTxt;
	private int best_start_time = 0;// 定时配送的时候，开始时间
	private int best_end_time = 0;

	private RadioGroup mPayWayGroup;// 支付方式 支付方式： 1 : 在线支付 2： 货到付款
	private int mPayWay = Constants.PAY_WAY_1;// 默认支付宝支付
	private RadioButton mCODBtn;// 货到付款

	private String cart_id = null;// 购物车id （多个购物车的商品 用逗号分隔 ）
	private TextView mBusinessTime;// 营业时间
	private long beginTime = 0;// 营业开始时间
	private long endTime = 0;

	private View mSubmitView;// 提交订单
	private View mDateView;// 日期
	private TextView mDateCancleTxt;// 取消选择日期
	private TextView mDateSureTxt;// 确定选择日期
	// 自定义仿ios时间空间相关
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView time;
	private WheelView min;
	private WheelView sec;

	private int mSelectYear;// 选择的年
	private int mSelectMonth;
	private int mSelectDay;
	private int mSelectHour;
	private int mSelectMin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_settlement_activity);
		initView();

		beginTime = MyApplication.getInstance().getBeginTime();
		endTime = MyApplication.getInstance().getEndTime();
		if (beginTime == 0 || endTime == 0) {
			shopInTime();
		} else {
			mBusinessTime.setText("营业时间 " + DateTime.getBusinessHour());
		}
		if (mUser != null) {
			mPersonalAddress = null;
			useraddressLists(mUser.getUcode());
		}
	}

	protected void initView() {
		new SetTopView(this, R.string.jiesuan, true);
		mSubmitView = findViewById(R.id.submit_view);
		mBusinessTime = (TextView) findViewById(R.id.business_time);
		mGotoBtn = (Button) findViewById(R.id.go_btn);
		mGotoBtn.setOnClickListener(this);
		mAllMoneyTxt = (TextView) findViewById(R.id.all_money);
		mFareTxt = (TextView) findViewById(R.id.fare_txt);
		mFareLimitTxt = (TextView) findViewById(R.id.fare_limit_txt);
		Intent it = getIntent();
		if (it != null) {
			mAllMoneyTxt.setText(it.getStringExtra(Extra.ALL_MONEY));
			mFareTxt.setText(it.getStringExtra(Extra.FARE));
			mFareLimitTxt.setText(it.getStringExtra(Extra.FARE_LIMIT));
		}

		mDeliveryTypeView = findViewById(R.id.delivery_type_view);
		mDeliveryTypeView.setOnClickListener(this);
		mDeliveryTypeTxt = (TextView) findViewById(R.id.delivery_type_txt);

		mPayWayGroup = (RadioGroup) findViewById(R.id.group);
		mCODBtn = (RadioButton) findViewById(R.id.btn_2);
		mPayWayGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.btn_1:
							// 支付宝支付
							mPayWay = Constants.PAY_WAY_1;
							break;
						case R.id.btn_2:
							// 货到付款
							mPayWay = Constants.PAY_WAY_2;
							break;
						default:
							break;
						}
					}
				});
		initListView();
		initAddressView();
		initDateView();
	};

	/**
	 * 初始化控件
	 */
	private void initDateView() {
		mDateView = findViewById(R.id.date_view);
		mDateCancleTxt = (TextView) findViewById(R.id.date_canlce_txt);
		mDateCancleTxt.setOnClickListener(this);
		mDateSureTxt = (TextView) findViewById(R.id.date_sure_txt);
		mDateSureTxt.setOnClickListener(this);
		Calendar c = Calendar.getInstance();
		int curYear = c.get(Calendar.YEAR);
		int curMonth = c.get(Calendar.MONTH);
		int curDay = c.get(Calendar.DAY_OF_MONTH);
		int curHour = c.get(Calendar.HOUR_OF_DAY);
		int curMin = c.get(Calendar.MINUTE);
		mSelectYear = curYear;
		mSelectMonth = curMonth;
		mSelectDay = curDay;
		mSelectHour = curHour;
		mSelectMin = curMin;
		year = (WheelView) findViewById(R.id.year);
		NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(
				this, 1950, curYear + 50);
		numericWheelAdapter1.setLabel("年");
		year.setViewAdapter(numericWheelAdapter1);
		year.setCyclic(true);// 是否可循环滑动
		year.addScrollingListener(scrollListener);

		month = (WheelView) findViewById(R.id.month);
		NumericWheelAdapter numericWheelAdapter2 = new NumericWheelAdapter(
				this, 1, 12, "%02d");
		numericWheelAdapter2.setLabel("月");
		month.setViewAdapter(numericWheelAdapter2);
		month.setCyclic(true);
		month.addScrollingListener(scrollListener);

		day = (WheelView) findViewById(R.id.day);
		initDay(curYear, curMonth);
		day.setCyclic(true);
		day.addScrollingListener(scrollListener);

		min = (WheelView) findViewById(R.id.min);
		NumericWheelAdapter numericWheelAdapter3 = new NumericWheelAdapter(
				this, 0, 23, "%02d");
		numericWheelAdapter3.setLabel("时");
		min.setViewAdapter(numericWheelAdapter3);
		min.setCyclic(true);
		min.addScrollingListener(scrollListener);

		sec = (WheelView) findViewById(R.id.sec);
		NumericWheelAdapter numericWheelAdapter4 = new NumericWheelAdapter(
				this, 0, 59, "%02d");
		numericWheelAdapter4.setLabel("分");
		sec.setViewAdapter(numericWheelAdapter4);
		sec.setCyclic(true);
		sec.addScrollingListener(scrollListener);

		year.setVisibleItems(7);// 设置显示行数
		month.setVisibleItems(7);
		day.setVisibleItems(7);
		// time.setVisibleItems(7);
		min.setVisibleItems(7);
		sec.setVisibleItems(7);

		year.setCurrentItem(curYear - 1950);
		month.setCurrentItem(curMonth);
		day.setCurrentItem(curDay - 1);
		min.setCurrentItem(curHour);
		sec.setCurrentItem(curMin);
	}

	/*
	 * 初始化地址信息
	 */
	private void initAddressView() {
		mAddressContainer = findViewById(R.id.address_view);
		mAddressContainer.setOnClickListener(this);
		mAddressView = findViewById(R.id.address_not_emptey_view);
		mAddressEmptyTxt = (TextView) findViewById(R.id.address_empty_txt);

		mNameTxt = (TextView) findViewById(R.id.name_txt);
		mTelTxt = (TextView) findViewById(R.id.tel_txt);
		mAreaTxt = (TextView) findViewById(R.id.area_txt);
		mAddressDetailTxt = (TextView) findViewById(R.id.address_detail_txt);
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
				Intent it = new Intent(GoodsSettlementActivity.this,
						GoodsDetailActivity.class);
				it.putExtra(Extra.GOODS_ID, mList.get(position).getGoods_id());
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(it);
				AnimUtil.setFromLeftToRight(GoodsSettlementActivity.this);
			}
		});
		mShoppingCartList = MyApplication.getInstance().getShoppingCartList();
		if (mShoppingCartList != null) {
			for (int i = 0; i < mShoppingCartList.size(); i++) {
				PersonalOrderDetail orderDetail = new PersonalOrderDetail();
				orderDetail.setGoods_id(mShoppingCartList.get(i).getGoods_id());
				orderDetail.setGoods_name(mShoppingCartList.get(i)
						.getGoods_name());
				orderDetail.setGoods_price(mShoppingCartList.get(i)
						.getGoods_info().getGoods_price());
				orderDetail.setOld_price(mShoppingCartList.get(i)
						.getGoods_info().getOld_price());
				orderDetail.setQuantity(mShoppingCartList.get(i).getQuantity()
						+ "");
				orderDetail.setGoods_image(mShoppingCartList.get(i)
						.getGoods_image());
				orderDetail.setExpend1(mShoppingCartList.get(i).getGoods_info()
						.getExpend1());
				mList.add(orderDetail);
			}

			if (!mShoppingCartList.isEmpty()) {
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < mShoppingCartList.size(); i++) {
					String cartId = mShoppingCartList.get(i).getCart_id();
					stringBuilder.append(cartId).append(",");
				}
				cart_id = stringBuilder.toString();
				if (DEBUG) {
					Log.d(TAG, "cartID:" + stringBuilder.toString());
				}
			}
		}
		mAdapter.notifyDataSetChanged();
	};

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent it = null;
		switch (v.getId()) {
		case R.id.go_btn:
			// it = new Intent(this, PersonalOrderDetailActivity.class);
			// it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
			// | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//
			// startActivity(it);
			// 提交了订单，先判断地址
			if (mPersonalAddress == null) {
				showToast(R.string.add_address_tip);
				return;
			}

			if (mShoppingCartList == null) {
				showToast(R.string.add_goods_tip);
				return;
			}

			if (TextUtils.isEmpty(cart_id)) {
				showToast(R.string.add_goods_tip);
				return;
			}
			if (mUser != null) {
				//==>2015.12.9
				if(mDeliVeryType== Constants.DELIVERY_TYPE_2){
					//如果是及时派送，需要进行时间判断，判断是否在营业时间内
					Calendar calendar=Calendar.getInstance();
					int currentHour=calendar.get(Calendar.HOUR_OF_DAY);
					int currentMin=calendar.get(Calendar.MINUTE);
					int currentTime = currentHour * 3600 + currentMin * 60;
					if(currentTime<beginTime||currentTime>endTime){
						Toast.makeText(this, "非营业时间不可以下单", Toast.LENGTH_LONG).show();
						return;
					}
				}
				//<==2015.12.9
				orderaddAdd(mUser.getUcode(), cart_id, mPayWay, mDeliVeryType,
							best_start_time, best_end_time,
							mPersonalAddress.getLink_uname(),
							mPersonalAddress.getTel(),
							mPersonalAddress.getExtend1(),
							mPersonalAddress.getAddr_id(),
							mPersonalAddress.getAddress());
			
			}
			break;
		case R.id.address_view:
			it = new Intent(this, PersonalAddressActivity.class);
			it.putExtra(Extra.GOODSSETTLEMENT, true);
			startActivityForResult(it, 0);
			AnimUtil.setFromLeftToRight(this);
			break;
		case R.id.delivery_type_view:
			// 派送方式,如果是货到付款，就是只有两中方式，如果是支付宝三种方式
			if (mPayWay == Constants.PAY_WAY_2) {
				showTwoDialog();
			} else {
				showThreeDialog();
			}
			break;

		case R.id.date_canlce_txt:
			// 取消选择定时派送时间
			mDateView.setVisibility(View.GONE);
			mSubmitView.setVisibility(View.VISIBLE);
			mListView.setEnabled(true);
			mDeliveryTypeView.setEnabled(true);
			break;
		case R.id.date_sure_txt:
			// 定时派送选择时间

			String date = new StringBuilder()
					.append((year.getCurrentItem() + 1950))
					.append("-")
					.append((month.getCurrentItem() + 1) < 10 ? "0"
							+ (month.getCurrentItem() + 1) : (month
							.getCurrentItem() + 1))
					.append("-")
					.append(((day.getCurrentItem() + 1) < 10) ? "0"
							+ (day.getCurrentItem() + 1) : (day
							.getCurrentItem() + 1))
					.append(" ")
					.append((min.getCurrentItem()) < 10 ? "0"
							+ (min.getCurrentItem()) : (min.getCurrentItem()))
					.append(":")
					.append((sec.getCurrentItem()) < 10 ? "0"
							+ (sec.getCurrentItem()) : (sec.getCurrentItem()))
					.toString();

			// 判断是不是选择以前的时间
			boolean flag = isTimeComparisonMinutes(date,
					DateTime.getDateMinutes());
			if (flag) {
				// 选择了之前的时间
				showToast(R.string.date_before_wrong);
				return;
			}
			// 判断要在营业范围之内
			int selBeinTime = mSelectHour * 3600 + mSelectMin * 60;
			// 默认配送结束时间是+个半小时
			int selEndTime = mSelectHour * 3600 + (mSelectMin + 30) * 60;
			if (selBeinTime > beginTime && selBeinTime < endTime) {
				// 正确的选择了时间，就显示并 设置值
				//==>2015.12.30 定时派送的时间戳
				//best_start_time = selBeinTime;
				//best_end_time = selEndTime;
				best_start_time=(int) DateTime.getTimeStemp2(date);
				best_end_time=best_start_time+30*60;
				//<==2015.12.30

				int end_hour = selEndTime / 3600;
				int end_minute = (selEndTime - end_hour * 3600) / 60;
				String endTimeNimute = end_hour + ":" + end_minute;
				if (end_hour < 10) {
					endTimeNimute = "0" + end_hour + ":" + end_minute;
				}
				if (end_minute < 10) {
					endTimeNimute = end_hour + ":" + "0" + end_minute;
				}
				if (end_hour < 10 && end_minute < 10) {
					endTimeNimute = "0" + end_hour + ":" + "0" + end_minute;
				}
				mDeliVeryType = Constants.DELIVERY_TYPE_1;
				mDeliveryTypeTxt.setText(R.string.delivery_type_1);
				mDeliveryTypeTxt.setText(getString(R.string.delivery_type_1)
						+ date + "-" + endTimeNimute);

				mDateView.setVisibility(View.GONE);
				mSubmitView.setVisibility(View.VISIBLE);
				
				mListView.setEnabled(true);
				mDeliveryTypeView.setEnabled(true);
			} else {
				showToast(R.string.date_not_in_busineess);
			}
			break;
		default:
			break;
		}

	}

	// 如果是选择的货到付款的话，就显示2种派送方式
	private void showTwoDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.delivery_type);
		final CharSequence[] items = { getString(R.string.delivery_type_1),
				getString(R.string.delivery_type_2) };
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					// 定时派送
					showDateTimeDialog();
					mDeliveryTypeTxt.setText(items[which]);
					break;
				case 1:
					mDeliVeryType = which + 1;
					mDeliveryTypeTxt.setText(items[which]);
					best_end_time = 0;
					best_start_time = 0;
					hideDateTimeDialog();
					break;
				default:
					break;
				}
			}
		});
		builder.create().show();
	}

	// 如果是选择的支付宝的话，就显示三种派送方式
	private void showThreeDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.delivery_type);
		final CharSequence[] items = { getString(R.string.delivery_type_1),
				getString(R.string.delivery_type_2),
				getString(R.string.delivery_type_3) };
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				// 选择了上门自提那么，就只有支付宝支付这种方式了

				switch (which) {
				case 0:
					mCODBtn.setVisibility(View.VISIBLE);
					showDateTimeDialog();
					mDeliveryTypeTxt.setText(items[which]);
					break;
				case 1:
					mCODBtn.setVisibility(View.VISIBLE);
					mDeliVeryType = which + 1;
					mDeliveryTypeTxt.setText(items[which]);
					best_end_time = 0;
					best_start_time = 0;
					hideDateTimeDialog();
					break;
				case 2:
					mCODBtn.setVisibility(View.INVISIBLE);
					mDeliVeryType = which + 1;
					mDeliveryTypeTxt.setText(items[which]);
					best_end_time = 0;
					best_start_time = 0;
					hideDateTimeDialog();
					break;
				default:
					break;
				}
			}
		});
		builder.create().show();
	}

	/**
	 * 显示日期时间选择控件
	 */
	private void showDateTimeDialog() {
		// DateTimePickDialogUtil dateTimePicKDialog = new
		// DateTimePickDialogUtil(
		// GoodsSettlementActivity.this, getDate(), this);
		// dateTimePicKDialog.dateTimePicKDialog();

		// 改变日期选择样式
		mDateView.setVisibility(View.VISIBLE);
		mListView.setEnabled(false);
		mDeliveryTypeView.setEnabled(false);
		mSubmitView.setVisibility(View.GONE);
	}

	/**
	 * 隐藏时间控件
	 */
	private void hideDateTimeDialog() {
		mDateView.setVisibility(View.GONE);
		mSubmitView.setVisibility(View.VISIBLE);
		
		mListView.setEnabled(true);
		mDeliveryTypeView.setEnabled(true);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == 0) {
			mPersonalAddress = (PersonalAddress) data
					.getSerializableExtra(Extra.PERSONAL_ADDRESS);
			if (mPersonalAddress != null) {
				mNameTxt.setText(mPersonalAddress.getLink_uname());
				mTelTxt.setText(mPersonalAddress.getTel());
				mAreaTxt.setText(mPersonalAddress.getExtend1());
				mAddressDetailTxt.setText(mPersonalAddress.getAddress());
				mAddressView.setVisibility(View.VISIBLE);
				mAddressEmptyTxt.setVisibility(View.GONE);
			} else {
				mAddressView.setVisibility(View.GONE);
				mAddressEmptyTxt.setVisibility(View.VISIBLE);
			}
		}
	}

	/**
	 * 系统商家的运营时间
	 */
	private void shopInTime() {
		API.shopInTime(new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				if (state == STATE_OK) {
					JSONObject jsonObject = JSON.parseObject(data);
					if (jsonObject != null) {
						String expend1 = jsonObject.getString("expend1");
						String value = jsonObject.getString("value");

						if (!TextUtils.isEmpty(expend1)
								&& !TextUtils.isEmpty(value)) {
							if (expend1.contains(".")) {
								expend1 = expend1.substring(0,
										expend1.lastIndexOf("."));

							}
							MyApplication.getInstance().setBeginTime(
									Long.parseLong(expend1));
							beginTime = MyApplication.getInstance()
									.getBeginTime();
							if (value.contains(".")) {
								value = value.substring(0,
										value.lastIndexOf("."));
							}
							MyApplication.getInstance().setEndTime(
									Long.parseLong(value));
							endTime = MyApplication.getInstance().getEndTime();
							mBusinessTime.setText("营业时间 "
									+ DateTime.getBusinessHour());
						}

					}
				}
			}
		});
	}

	/**
	 * 获取地址信息
	 * 
	 * @param ucode
	 */
	private void useraddressLists(String ucode) {
		showProgressDialog(R.string.loading_get_address);
		API.useraddressLists(this, ucode, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				List<PersonalAddress> addressList = null;
				switch (state) {
				case STATE_OK:
					addressList = JSON.parseArray(data, PersonalAddress.class);
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

				if (addressList != null && !addressList.isEmpty()) {
					mAddressView.setVisibility(View.VISIBLE);
					mAddressEmptyTxt.setVisibility(View.GONE);
					mPersonalAddress = addressList.get(0);
					if (mPersonalAddress != null) {
						mNameTxt.setText(mPersonalAddress.getLink_uname());
						mTelTxt.setText(mPersonalAddress.getTel());
						mAreaTxt.setText(mPersonalAddress.getExtend1());
						mAddressDetailTxt.setText(mPersonalAddress.getAddress());
					}
				} else {
					mAddressView.setVisibility(View.GONE);
					mAddressEmptyTxt.setVisibility(View.VISIBLE);
				}
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
	private void orderaddAdd(String ucode, String cart_id, final int pay_way,
			int delivery_type, int best_start_time, int best_end_time,
			String link_name, String tel, String area, String area_id,
			String address) {
		showProgressDialog(R.string.loading_submit_order);
		API.orderaddAdd(this, ucode, cart_id, pay_way, delivery_type,
				best_start_time, best_end_time, link_name, tel, area, area_id,
				address, new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {

						case STATE_OK:
							// 订单提交成功,就跳转到订单详情
							MyApplication.getInstance().setShopCartNeedUpdate(
									true);
							JSONObject jsonObject = JSON.parseObject(data);
							if (jsonObject != null) {
								String order_id = jsonObject
										.getString("order_id");
								Intent it = new Intent(
										GoodsSettlementActivity.this,
										PersonalOrderDetailActivity.class);
								if (pay_way == Constants.PAY_WAY_2) {
									it.putExtra(Extra.ORDER_TYPE,
											Constants.ORDER_NO_GET);
								}
								it.putExtra(Extra.ORDER_ID, order_id);
								it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
										| Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(it);
								finish();
								AnimUtil.setFromLeftToRight(GoodsSettlementActivity.this);
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
	public void onSelect(String dateTime, int hour, int minute) {
		// 选择的时间需要做判断，1.不能选当前时间之前的时间 2.要在营业时间范围之内

		if (TextUtils.isEmpty(dateTime)) {
			// 取消了选择时间
			return;
		}
		boolean flag = isTimeComparisonMinutes(dateTime,
				DateTime.getDateMinutes());
		if (flag) {
			// 选择了之前的时间
			showToast(R.string.date_before_wrong);
			return;
		}
		// 判断要在营业范围之内

		int selBeinTime = hour * 3600 + minute * 60;
		// 默认配送结束时间是+个半小时
		int selEndTime = hour * 3600 + (minute + 30) * 60;
		if (selBeinTime > beginTime && selBeinTime < endTime) {
			// 正确的选择了时间，就显示并 设置值
			best_start_time = selBeinTime;
			best_end_time = selEndTime;

			int end_hour = selEndTime / 3600;
			int end_minute = (selEndTime - end_hour * 3600) / 60;
			String endTimeNimute = end_hour + ":" + end_minute;
			if (end_hour < 10) {
				endTimeNimute = "0" + end_hour + ":" + end_minute;
			}
			if (end_minute < 10) {
				endTimeNimute = end_hour + ":" + "0" + end_minute;
			}
			if (end_hour < 10 && end_minute < 10) {
				endTimeNimute = "0" + end_hour + ":" + "0" + end_minute;
			}
			mDeliVeryType = Constants.DELIVERY_TYPE_1;
			mDeliveryTypeTxt.setText(R.string.delivery_type_1);
			mDeliveryTypeTxt.setText(getString(R.string.delivery_type_1)
					+ dateTime + "-" + endTimeNimute);
		} else {
			showToast(R.string.date_not_in_busineess);
		}

	}

	/*
	 * 获取当前时间
	 */
	private String getDate() {
		// 时间转换器 2014年8月23日 17:44
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
		// 当前时间
		Date date = new Date();
		// 当前时间转换
		String nowDate = format.format(date);
		return nowDate;
	}

	/**
	 * 时间比较
	 */
	private boolean isTimeComparisonMinutes(String time1, String time2) {
		SimpleDateFormat lFormat;
		SimpleDateFormat lFormat1;
		Date date1 = new Date();
		Date date2 = new Date();
		boolean flag = false;
		lFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		lFormat1 = new SimpleDateFormat("yyyyMMddHHmm");
		try {
			date1 = lFormat.parse(time1);
			date2 = lFormat.parse(time2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date2 = new Date(date2.getTime());
		String gRtnStr1 = lFormat1.format(date1);
		String gRtnStr2 = lFormat1.format(date2);
		if (Double.parseDouble(gRtnStr1) < Double.parseDouble(gRtnStr2)) {
			flag = true;
		}
		return flag;
	}

	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			mSelectYear = (year.getCurrentItem() + 1950);
			mSelectMonth = month.getCurrentItem() + 1;
			mSelectDay = day.getCurrentItem() + 1;
			mSelectHour = min.getCurrentItem();
			mSelectMin = sec.getCurrentItem();

			initDay(mSelectYear, mSelectMonth);

			String birthday = new StringBuilder()
					.append((year.getCurrentItem() + 1950))
					.append("-")
					.append((month.getCurrentItem() + 1) < 10 ? "0"
							+ (month.getCurrentItem() + 1) : (month
							.getCurrentItem() + 1))
					.append("-")
					.append(((day.getCurrentItem() + 1) < 10) ? "0"
							+ (day.getCurrentItem() + 1) : (day
							.getCurrentItem() + 1)).toString();
			if (DEBUG) {
				Log.e(TAG, "date:" + birthday);
				Log.e(TAG, "hour:" + (min.getCurrentItem()));
				Log.e(TAG, "min:" + (sec.getCurrentItem()));
			}
		}
	};

	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}

	/**
	 */
	private void initDay(int arg1, int arg2) {
		NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(this,
				1, getDay(arg1, arg2), "%02d");
		numericWheelAdapter.setLabel("日");
		day.setViewAdapter(numericWheelAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
