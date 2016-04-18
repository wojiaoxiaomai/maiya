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
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�GoodsSettlementActivity
 * @�������� ����
 * @�����ˣ��
 * @����ʱ�䣺2015-8-12 ����1:57:51
 * @�汾�ţ�v1.0
 * 
 */
public class GoodsSettlementActivity extends BaseActivity implements
		DateTimeCallback {
	private static final String TAG = "GoodsSettlementActivity";
	private ListView mListView;
	private List<PersonalOrderDetail> mList;
	private PersonalOrderDetailAdapter mAdapter;

	private Button mGotoBtn;
	private View mAddressContainer;// address���ؼ�
	private View mAddressView;// ��ַ��Ϣ
	private TextView mAddressEmptyTxt;// ��ַΪ�յ���Ϣ
	private TextView mNameTxt;// �ջ�������
	private TextView mTelTxt;// �ջ��˵绰
	private TextView mAreaTxt;// �ջ�������
	private TextView mAddressDetailTxt;// ��ϸ��ַ

	private PersonalAddress mPersonalAddress = null;// ��ַ��Ϣʵ��
	private List<ShoppingCart> mShoppingCartList = null;// ���ﳵ�б�

	private TextView mAllMoneyTxt;// �ܽ��
	private TextView mFareTxt;// �˷�
	private TextView mFareLimitTxt;// �˷����յ�
	private View mDeliveryTypeView;// ���ͷ�ʽ
	private int mDeliVeryType = Constants.DELIVERY_TYPE_2;// Ĭ�ϼ�ʱ����
	private TextView mDeliveryTypeTxt;
	private int best_start_time = 0;// ��ʱ���͵�ʱ�򣬿�ʼʱ��
	private int best_end_time = 0;

	private RadioGroup mPayWayGroup;// ֧����ʽ ֧����ʽ�� 1 : ����֧�� 2�� ��������
	private int mPayWay = Constants.PAY_WAY_1;// Ĭ��֧����֧��
	private RadioButton mCODBtn;// ��������

	private String cart_id = null;// ���ﳵid ��������ﳵ����Ʒ �ö��ŷָ� ��
	private TextView mBusinessTime;// Ӫҵʱ��
	private long beginTime = 0;// Ӫҵ��ʼʱ��
	private long endTime = 0;

	private View mSubmitView;// �ύ����
	private View mDateView;// ����
	private TextView mDateCancleTxt;// ȡ��ѡ������
	private TextView mDateSureTxt;// ȷ��ѡ������
	// �Զ����iosʱ��ռ����
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView time;
	private WheelView min;
	private WheelView sec;

	private int mSelectYear;// ѡ�����
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
			mBusinessTime.setText("Ӫҵʱ�� " + DateTime.getBusinessHour());
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
							// ֧����֧��
							mPayWay = Constants.PAY_WAY_1;
							break;
						case R.id.btn_2:
							// ��������
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
	 * ��ʼ���ؼ�
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
		numericWheelAdapter1.setLabel("��");
		year.setViewAdapter(numericWheelAdapter1);
		year.setCyclic(true);// �Ƿ��ѭ������
		year.addScrollingListener(scrollListener);

		month = (WheelView) findViewById(R.id.month);
		NumericWheelAdapter numericWheelAdapter2 = new NumericWheelAdapter(
				this, 1, 12, "%02d");
		numericWheelAdapter2.setLabel("��");
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
		numericWheelAdapter3.setLabel("ʱ");
		min.setViewAdapter(numericWheelAdapter3);
		min.setCyclic(true);
		min.addScrollingListener(scrollListener);

		sec = (WheelView) findViewById(R.id.sec);
		NumericWheelAdapter numericWheelAdapter4 = new NumericWheelAdapter(
				this, 0, 59, "%02d");
		numericWheelAdapter4.setLabel("��");
		sec.setViewAdapter(numericWheelAdapter4);
		sec.setCyclic(true);
		sec.addScrollingListener(scrollListener);

		year.setVisibleItems(7);// ������ʾ����
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
	 * ��ʼ����ַ��Ϣ
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
			// �ύ�˶��������жϵ�ַ
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
					//����Ǽ�ʱ���ͣ���Ҫ����ʱ���жϣ��ж��Ƿ���Ӫҵʱ����
					Calendar calendar=Calendar.getInstance();
					int currentHour=calendar.get(Calendar.HOUR_OF_DAY);
					int currentMin=calendar.get(Calendar.MINUTE);
					int currentTime = currentHour * 3600 + currentMin * 60;
					if(currentTime<beginTime||currentTime>endTime){
						Toast.makeText(this, "��Ӫҵʱ�䲻�����µ�", Toast.LENGTH_LONG).show();
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
			// ���ͷ�ʽ,����ǻ����������ֻ�����з�ʽ�������֧�������ַ�ʽ
			if (mPayWay == Constants.PAY_WAY_2) {
				showTwoDialog();
			} else {
				showThreeDialog();
			}
			break;

		case R.id.date_canlce_txt:
			// ȡ��ѡ��ʱ����ʱ��
			mDateView.setVisibility(View.GONE);
			mSubmitView.setVisibility(View.VISIBLE);
			mListView.setEnabled(true);
			mDeliveryTypeView.setEnabled(true);
			break;
		case R.id.date_sure_txt:
			// ��ʱ����ѡ��ʱ��

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

			// �ж��ǲ���ѡ����ǰ��ʱ��
			boolean flag = isTimeComparisonMinutes(date,
					DateTime.getDateMinutes());
			if (flag) {
				// ѡ����֮ǰ��ʱ��
				showToast(R.string.date_before_wrong);
				return;
			}
			// �ж�Ҫ��Ӫҵ��Χ֮��
			int selBeinTime = mSelectHour * 3600 + mSelectMin * 60;
			// Ĭ�����ͽ���ʱ����+����Сʱ
			int selEndTime = mSelectHour * 3600 + (mSelectMin + 30) * 60;
			if (selBeinTime > beginTime && selBeinTime < endTime) {
				// ��ȷ��ѡ����ʱ�䣬����ʾ�� ����ֵ
				//==>2015.12.30 ��ʱ���͵�ʱ���
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

	// �����ѡ��Ļ�������Ļ�������ʾ2�����ͷ�ʽ
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
					// ��ʱ����
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

	// �����ѡ���֧�����Ļ�������ʾ�������ͷ�ʽ
	private void showThreeDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.delivery_type);
		final CharSequence[] items = { getString(R.string.delivery_type_1),
				getString(R.string.delivery_type_2),
				getString(R.string.delivery_type_3) };
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				// ѡ��������������ô����ֻ��֧����֧�����ַ�ʽ��

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
	 * ��ʾ����ʱ��ѡ��ؼ�
	 */
	private void showDateTimeDialog() {
		// DateTimePickDialogUtil dateTimePicKDialog = new
		// DateTimePickDialogUtil(
		// GoodsSettlementActivity.this, getDate(), this);
		// dateTimePicKDialog.dateTimePicKDialog();

		// �ı�����ѡ����ʽ
		mDateView.setVisibility(View.VISIBLE);
		mListView.setEnabled(false);
		mDeliveryTypeView.setEnabled(false);
		mSubmitView.setVisibility(View.GONE);
	}

	/**
	 * ����ʱ��ؼ�
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
	 * ϵͳ�̼ҵ���Ӫʱ��
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
							mBusinessTime.setText("Ӫҵʱ�� "
									+ DateTime.getBusinessHour());
						}

					}
				}
			}
		});
	}

	/**
	 * ��ȡ��ַ��Ϣ
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
							// �����ύ�ɹ�,����ת����������
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
		// ѡ���ʱ����Ҫ���жϣ�1.����ѡ��ǰʱ��֮ǰ��ʱ�� 2.Ҫ��Ӫҵʱ�䷶Χ֮��

		if (TextUtils.isEmpty(dateTime)) {
			// ȡ����ѡ��ʱ��
			return;
		}
		boolean flag = isTimeComparisonMinutes(dateTime,
				DateTime.getDateMinutes());
		if (flag) {
			// ѡ����֮ǰ��ʱ��
			showToast(R.string.date_before_wrong);
			return;
		}
		// �ж�Ҫ��Ӫҵ��Χ֮��

		int selBeinTime = hour * 3600 + minute * 60;
		// Ĭ�����ͽ���ʱ����+����Сʱ
		int selEndTime = hour * 3600 + (minute + 30) * 60;
		if (selBeinTime > beginTime && selBeinTime < endTime) {
			// ��ȷ��ѡ����ʱ�䣬����ʾ�� ����ֵ
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
	 * ��ȡ��ǰʱ��
	 */
	private String getDate() {
		// ʱ��ת���� 2014��8��23�� 17:44
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd��  HH:mm");
		// ��ǰʱ��
		Date date = new Date();
		// ��ǰʱ��ת��
		String nowDate = format.format(date);
		return nowDate;
	}

	/**
	 * ʱ��Ƚ�
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
		numericWheelAdapter.setLabel("��");
		day.setViewAdapter(numericWheelAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
