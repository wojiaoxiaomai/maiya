package com.choncheng.maya;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.category.CategoryActivity;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.customview.AlertDialog;
import com.choncheng.maya.db.UserDBHelper;
import com.choncheng.maya.homepage.HomePageActivity;
import com.choncheng.maya.personal.PersonalCenterActivity;
import com.choncheng.maya.personal.PersonalCenterSettingActivity;
import com.choncheng.maya.shoppingcart.ShoppingCartActivity;
import com.choncheng.maya.utils.AppInfoUtil;
import com.choncheng.maya.utils.CommUtils;
import com.choncheng.maya.utils.UpdateVersion;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�MainActivity
 * @�������� ��ҳ�棨��ҳ�����࣬�������ģ����ﳵ��
 * @�����ˣ��
 * @����ʱ�䣺2015-8-7 ����8:59:08
 * @�汾�ţ�v1.0
 * 
 */
public class MainActivity extends TabActivity implements OnTouchListener {
	private TabHost mTabHost;

	private RelativeLayout mTabLayout1;
	private RelativeLayout mTabLayout2;
	private RelativeLayout mTabLayout3;
	private RelativeLayout mTabLayout4;

	private ImageView mTabImg1;
	private ImageView mTabImg2;
	private ImageView mTabImg3;
	private ImageView mTabImg4;

	private TextView mTabTxt1;
	private TextView mTabTxt2;
	private TextView mTabTxt3;
	private TextView mTabTxt4;

	public static final String TAB_1 = "TAB_1";
	public static final String TAB_2 = "TAB_2";
	public static final String TAB_3 = "TAB_3";
	public static final String TAB_4 = "TAB_4";
	private long exitTime = 0;
	private TextView mShopcartNumberTxt;// ���ﳵ������
	private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(
					Constants.ACTION_UPDATE_SHOPCART_NUMBER)) {
				// �յ����¹��ﳵ�����Ĺ㲥
				updateCartNumberView();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById();
		initView();
		shopInTime();
		appversionAppNew();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.ACTION_UPDATE_SHOPCART_NUMBER);
		registerReceiver(myBroadcastReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (myBroadcastReceiver != null) {
			unregisterReceiver(myBroadcastReceiver);
		}
	}

	/**
	 * ��ȡ���ﳵ������
	 */
	private void shopcartlistsCartNumber() {
		if (MyApplication.getInstance().getUser() != null) {
			API.shopcartlistsCartNumber(this, MyApplication.getInstance()
					.getUser().getUcode(), new ResponseHandler() {

				@Override
				public void onRese(String data, int state) {
					switch (state) {
					case STATE_OK:
						JSONObject jsonObject = JSON.parseObject(data);
						if (jsonObject != null) {
							int cart_number = jsonObject
									.getIntValue("cart_number");
							MyApplication.getInstance().setShopCartNumber(
									cart_number);
							updateCartNumberView();
						}
						break;
					case STATE_FAIL:
						break;

					default:
						break;
					}
				}
			});
		}
	}

	/**
	 * ��ʾ���ﳵ����
	 */
	private void updateCartNumberView() {
		int cart_number = MyApplication.getInstance().getShopCartNumber();
		if (cart_number <= 0) {
			mShopcartNumberTxt.setVisibility(View.GONE);
		} else {
			mShopcartNumberTxt.setVisibility(View.VISIBLE);
			mShopcartNumberTxt.setText(cart_number + "");
			if (cart_number > 99) {
				mShopcartNumberTxt.setText("99+");
			}
		}
	}

	private void findViewById() {

		mTabLayout1 = (RelativeLayout) findViewById(R.id.tab1);
		mTabLayout2 = (RelativeLayout) findViewById(R.id.tab2);
		mTabLayout3 = (RelativeLayout) findViewById(R.id.tab3);
		mTabLayout4 = (RelativeLayout) findViewById(R.id.tab4);
		mTabLayout1.setOnTouchListener(this);
		mTabLayout2.setOnTouchListener(this);
		mTabLayout3.setOnTouchListener(this);
		mTabLayout4.setOnTouchListener(this);

		mTabImg1 = (ImageView) findViewById(R.id.tab1_img);
		mTabImg2 = (ImageView) findViewById(R.id.tab2_img);
		mTabImg3 = (ImageView) findViewById(R.id.tab3_img);
		mTabImg4 = (ImageView) findViewById(R.id.tab4_img);

		mTabTxt1 = (TextView) findViewById(R.id.tab1_txt);
		mTabTxt2 = (TextView) findViewById(R.id.tab2_txt);
		mTabTxt3 = (TextView) findViewById(R.id.tab3_txt);
		mTabTxt4 = (TextView) findViewById(R.id.tab4_txt);
		mShopcartNumberTxt = (TextView) findViewById(R.id.cart_number_txt);
	}

	private void initView() {
		mTabHost = getTabHost();
		Intent i_1 = new Intent(this, HomePageActivity.class);
		Intent i_2 = new Intent(this, CategoryActivity.class);
		Intent i_3 = new Intent(this, PersonalCenterActivity.class);
		Intent i_4 = new Intent(this, ShoppingCartActivity.class);

		mTabHost.addTab(mTabHost.newTabSpec(TAB_1).setIndicator(TAB_1)
				.setContent(i_1));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_2).setIndicator(TAB_2)
				.setContent(i_2));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_3).setIndicator(TAB_3)
				.setContent(i_3));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_4).setIndicator(TAB_4)
				.setContent(i_4));
		mTabHost.setCurrentTabByTag(TAB_1);
		// �ж��û���¼״̬
		Intent it = getIntent();
		if (it != null
				&& it.getBooleanExtra(Extra.IS_FROM_CHANGEPASSWORDNEXTACTIVITY,
						false)) {
			// ������޸����룬��ô�´ε�¼��ʱ�����������������¼
		} else {
			UserDBHelper dbHelper = new UserDBHelper(this);
			MyApplication.getInstance().setUser(dbHelper.getUser());
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			switch (v.getId()) {
			case R.id.tab1:
				MyApplication.getInstance().setShopCartNeedback(false);
				mTabHost.setCurrentTabByTag(TAB_1);
				mTabImg1.setImageResource(R.drawable.home_ic_press);
				mTabTxt1.setTextColor(getResources().getColor(R.color.green));

				mTabImg2.setImageResource(R.drawable.fenlei_ic_normal);
				mTabTxt2.setTextColor(getResources().getColor(
						R.color.textview_grey));
				mTabImg3.setImageResource(R.drawable.grcenter_ic_normal);
				mTabTxt3.setTextColor(getResources().getColor(
						R.color.textview_grey));
				mTabImg4.setImageResource(R.drawable.gouwuche_ic_normal);
				mTabTxt4.setTextColor(getResources().getColor(
						R.color.textview_grey));
				break;
			case R.id.tab2:
				MyApplication.getInstance().setShopCartNeedback(false);
				mTabHost.setCurrentTabByTag(TAB_2);
				mTabImg1.setImageResource(R.drawable.home_ic_normal);
				mTabTxt1.setTextColor(getResources().getColor(
						R.color.textview_grey));
				mTabImg2.setImageResource(R.drawable.fenlei_ic_press);
				mTabTxt2.setTextColor(getResources().getColor(R.color.green));
				mTabImg3.setImageResource(R.drawable.grcenter_ic_normal);
				mTabTxt3.setTextColor(getResources().getColor(
						R.color.textview_grey));
				mTabImg4.setImageResource(R.drawable.gouwuche_ic_normal);
				mTabTxt4.setTextColor(getResources().getColor(
						R.color.textview_grey));
				break;
			case R.id.tab3:
				MyApplication.getInstance().setShopCartNeedback(false);
				mTabHost.setCurrentTabByTag(TAB_3);
				mTabImg1.setImageResource(R.drawable.home_ic_normal);
				mTabTxt1.setTextColor(getResources().getColor(
						R.color.textview_grey));
				mTabImg2.setImageResource(R.drawable.fenlei_ic_normal);
				mTabTxt2.setTextColor(getResources().getColor(
						R.color.textview_grey));
				mTabImg3.setImageResource(R.drawable.grcenter_ic_press);
				mTabTxt3.setTextColor(getResources().getColor(R.color.green));
				mTabImg4.setImageResource(R.drawable.gouwuche_ic_normal);
				mTabTxt4.setTextColor(getResources().getColor(
						R.color.textview_grey));
				break;
			case R.id.tab4:
				MyApplication.getInstance().setShopCartNeedback(false);

				if (!MyApplication.getInstance().isLogin()) {
					CommUtils.lauchLoginActivity(this);
				} else {
					// ��¼��
					MyApplication.getInstance().setShopCartNeedUpdate(true);

					mTabHost.setCurrentTabByTag(TAB_4);
					mTabImg1.setImageResource(R.drawable.home_ic_normal);
					mTabTxt1.setTextColor(getResources().getColor(
							R.color.textview_grey));
					mTabImg2.setImageResource(R.drawable.fenlei_ic_normal);
					mTabTxt2.setTextColor(getResources().getColor(
							R.color.textview_grey));
					mTabImg3.setImageResource(R.drawable.grcenter_ic_normal);
					mTabTxt3.setTextColor(getResources().getColor(
							R.color.textview_grey));
					mTabImg4.setImageResource(R.drawable.gouwuche_ic_press);
					mTabTxt4.setTextColor(getResources()
							.getColor(R.color.green));
				}
				break;
			default:
				break;
			}
		}
		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		shopcartlistsCartNumber();

		boolean isShowPersonCenter = MyApplication.getInstance()
				.getShowPersonalCenter();
		if (isShowPersonCenter) {
			mTabHost.setCurrentTabByTag(TAB_3);
			mTabImg1.setImageResource(R.drawable.home_ic_normal);
			mTabTxt1.setTextColor(getResources()
					.getColor(R.color.textview_grey));
			mTabImg2.setImageResource(R.drawable.fenlei_ic_normal);
			mTabTxt2.setTextColor(getResources()
					.getColor(R.color.textview_grey));
			mTabImg3.setImageResource(R.drawable.grcenter_ic_press);
			mTabTxt3.setTextColor(getResources().getColor(R.color.green));
			mTabImg4.setImageResource(R.drawable.gouwuche_ic_normal);
			mTabTxt4.setTextColor(getResources()
					.getColor(R.color.textview_grey));
		}
		MyApplication.getInstance().setShowPersonalCenter(false);

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
							if (value.contains(".")) {
								value = value.substring(0,
										value.lastIndexOf("."));

							}
							MyApplication.getInstance().setEndTime(
									Long.parseLong(value));

						}

					}
				}
			}
		});
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.dispatchKeyEvent(event);
	}

	private void exit() {
		if ((System.currentTimeMillis() - exitTime) > 3000) {
			Toast.makeText(this, R.string.exit_app_tip, Toast.LENGTH_SHORT)
					.show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
		}
	}

	/**
	 * ��ȡ�°���Ϣ
	 */
	private void appversionAppNew() {
		API.appversionAppNew(this, 1, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				if (state == STATE_OK) {
					Log.e("ssssssssssssssss", data);
					JSONObject jsonObject = JSON.parseObject(data);
					if (jsonObject != null) {
						String number = jsonObject.getString("number");
						if (!TextUtils.isEmpty(number)) {
							if (number.equals(AppInfoUtil
									.getVersion(MainActivity.this))) {
							} else {
								// ����и���
								showUpdateDialog();
							}
						}
					}
				} else {
				}
			}
		});
	}

	/*
	 * �������������ʾ��
	 */
	private void showUpdateDialog() {
		new AlertDialog(MainActivity.this)
				.builder()
				.setTitle(getString(R.string.soft_update))
				.setMsg(getString(R.string.soft_update_content))
				.setNegativeButton(getString(R.string.cancle),
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// ȡ������
							}
						})
				.setPositiveButton(getString(R.string.down_now),
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// ���������°汾
								new UpdateVersion(MainActivity.this,
										Constants.DOWNLOAD_APK_URL);
							}
						}).show();
	}

}
