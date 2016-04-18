package com.choncheng.maya.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

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
import com.choncheng.maya.shoppingcart.ShoppingCartAdapter.CheckboxCallback;
import com.choncheng.maya.shoppingcart.ShoppingCartAdapter.DeleteshopCartCallback;
import com.choncheng.maya.shoppingcart.ShoppingCartAdapter.QuantityCallback;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.CommUtils;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�ShoppingCartActivity
 * @�������� ���ﳵ
 * @�����ˣ��
 * @����ʱ�䣺2015-8-7 ����8:47:18
 * @�汾�ţ�v1.0
 * 
 */
public class ShoppingCartActivity extends BaseActivity implements
		DeleteshopCartCallback, CheckboxCallback, QuantityCallback {
	private ListView mListView;
	private List<ShoppingCart> mList;
	private ShoppingCartAdapter mAdapter;
	private View mEmptyView;// listviewΪ�յ�ʱ����ʾ
	private Button mGotoBtn;
	private static final String TAG = "ShoppingCartActivity";
	private CheckBox mAllCheckbox;// ȫѡ

	private static final double FARE_LIMIT = 30.00;
	private static final double FARE = 3.0;
	private double fareLimit = FARE_LIMIT;// Ĭ�ϵ��˷����յ�
	private double fare = FARE;// Ĭ����Ҫ֧�����˷ѣ�������ʵ�����30Ԫ��

	private TextView mAllMoneyTxt;// �ܽ��
	private TextView mFareTxt;// �˷�
	private TextView mFareLimitTxt;// ���ö�Ǯ���˷���ʾ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_cart_activity);
		initView();
		shippingfeeFee();

	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.shop_car, false);
		mAllMoneyTxt = (TextView) findViewById(R.id.all_money);
		mFareTxt = (TextView) findViewById(R.id.fare_txt);
		mFareLimitTxt = (TextView) findViewById(R.id.fare_limit_txt);
		mEmptyView = findViewById(R.id.listview_empty);
		mGotoBtn = (Button) findViewById(R.id.go_btn);
		mGotoBtn.setOnClickListener(this);
		mListView = (ListView) findViewById(R.id.listview);
		mList = new ArrayList<ShoppingCart>();
		mAdapter = new ShoppingCartAdapter(mList, this, this, this, this);

		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(ShoppingCartActivity.this,
						GoodsDetailActivity.class);
				it.putExtra(Extra.GOODS_ID, mList.get(position).getGoods_id());
				startActivity(it);
				AnimUtil.setFromLeftToRight(ShoppingCartActivity.this);
			}
		});
		mAllCheckbox = (CheckBox) findViewById(R.id.all_checkbox);
		mAllCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					// ȫѡ
					for (int i = 0; i < mList.size(); i++) {
						mList.get(i).setChecked(isChecked);
					}
				} else {
					// ȡ��ȫѡ
					if (getCheckSize() == mList.size()) {
						for (int i = 0; i < mList.size(); i++) {
							mList.get(i).setChecked(isChecked);
						}
					}
				}
				mAdapter.notifyDataSetChanged();
				updateMoney();
				updateGotoBtn();
			}
		});

	};

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.go_btn:
			Intent it = new Intent(this, GoodsSettlementActivity.class);
			MyApplication.getInstance().setShoppingCartList(
					getShoppingCartList());
			it.putExtra(Extra.ALL_MONEY, mAllMoneyTxt.getText().toString());
			it.putExtra(Extra.FARE, mFareTxt.getText().toString());
			it.putExtra(Extra.FARE_LIMIT, mFareLimitTxt.getText().toString());
			startActivity(it);
			AnimUtil.setFromLeftToRight(this);
			break;

		default:
			break;
		}
	}

	// ��ȡ�Ѿ�ѡ���˵Ĺ��ﳵ��Ʒ�б�
	private List<ShoppingCart> getShoppingCartList() {
		List<ShoppingCart> list = new ArrayList<ShoppingCart>();
		for (int i = 0; i < mList.size(); i++) {
			if (mList.get(i).isChecked()) {
				list.add(mList.get(i));
			}
		}
		return list;
	}

	@Override
	protected void onResume() {
		super.onResume();
		new SetTopView(this, R.string.shop_car, MyApplication.getInstance()
				.getShopCartNeedback());

		if (MyApplication.getInstance().isLogin()) {
			if (mUser != null
					&& MyApplication.getInstance().getShopCartNeedUpdate()) {
				shopcartlistsLists(mUser.getUcode(), 1);
			}
		}

	}

	/**
	 * ��ȡ���ﳵ��Ϣ
	 * 
	 * @param ucode
	 * @param page
	 *            �ڼ�ҳ�ӵ�һҳ��ʼ
	 */
	private void shopcartlistsLists(String ucode, int page) {
		showProgressDialog(R.string.loading);
		API.shopcartlistsLists(this, ucode, page, Constants.PAGESIZE,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							mList.clear();
							List<ShoppingCart> shoppingCartList = JSON
									.parseArray(data, ShoppingCart.class);
							if (shoppingCartList != null) {
								mList.addAll(shoppingCartList);
							}
							for (int i = 0; i < mList.size(); i++) {
								// �޻�����Ϊfalse
								if (mList.get(i).getGoods_info().getStock()
										- mList.get(i).getVirtual_sales() <= 0) {
									mList.get(i).setChecked(false);
								}
							}
							mAdapter.notifyDataSetChanged();
							updateShopCartNumber();
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
						updateListView();
						updateAllCheckbox();
						updateMoney();
						updateGotoBtn();
					}
				});
	}

	@Override
	public void onDel(int positon) {
		// ɾ����Ʒ
		if (mUser != null) {
			shopcartcancelDeleteSelected(mUser.getUcode(), mList.get(positon)
					.getCart_id(), positon);
		}

	}

	/**
	 * ɾ����Ʒ
	 * 
	 * @param ucode
	 * @param cart_id
	 */
	private void shopcartcancelDeleteSelected(String ucode, String cart_id,
			final int position) {
		showProgressDialog(R.string.loading_del_shopcart);
		API.shopcartcancelDeleteSelected(this, ucode, cart_id,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							// ˢ�¹��ﳵ
							// if (mUser != null) {
							// shopcartlistsLists(mUser.getUcode(), 1);
							// }
							mList.remove(mList.get(position));
							mAdapter.notifyDataSetChanged();

							updateShopCartNumber();

							updateMoney();
							updateGotoBtn();
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

	// ���¹��ﳵ����
	private void updateShopCartNumber() {
		MyApplication.getInstance().setShopCartNumber(mList.size());
		Intent intent = new Intent();
		intent.setAction(Constants.ACTION_UPDATE_SHOPCART_NUMBER);
		ShoppingCartActivity.this.sendBroadcast(intent);
	}

	/**
	 * ��ȡ�˷�
	 */
	private void shippingfeeFee() {
		API.shippingfeeFee(new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				if (state == STATE_OK) {
					JSONObject jsonObject = JSON.parseObject(data);
					if (jsonObject != null) {
						fareLimit = jsonObject.getDoubleValue("expend1");
						fare = jsonObject.getDoubleValue("value");
						mFareLimitTxt.setText("(������" + Math.round(fareLimit)
								+ "Ԫ�����˷�)");
					}
				}
			}
		});
	}

	/**
	 * ����listviwe��ʱ
	 */
	private void updateListView() {
		if (mList == null || mList.isEmpty()) {
			mEmptyView.setVisibility(View.VISIBLE);
		} else {
			mEmptyView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onCheck(int position, boolean checked) {
		// ѡ���ȡ������Ʒ
		updateAllCheckbox();
		updateMoney();
		updateGotoBtn();
	}

	/**
	 * ˢ��ȫѡ��״̬
	 */
	private void updateAllCheckbox() {
		if (getCheckSize() == mList.size()) {
			mAllCheckbox.setChecked(true);
		} else {
			mAllCheckbox.setChecked(false);
		}
	}

	/**
	 * ˢ�·��ã������ܽ����˷ѣ�
	 */
	private void updateMoney() {
		if (!mList.isEmpty() && getCheckSize() > 0) {
			double allMoney = 0;
			for (int i = 0; i < mList.size(); i++) {
				if (mList.get(i).isChecked()) {
					// ����ѡ�����Ʒ�۸�
					double price = Double.parseDouble(mList.get(i)
							.getGoods_info().getGoods_price());
					price *= mList.get(i).getQuantity();
					allMoney += price;
				}
			}

			if (allMoney < FARE_LIMIT) {
				// ����С�����˷����յ�
				allMoney += fare;
				mFareTxt.setText(CommUtils.getMoney(CommUtils
						.getTwoPointDouble(fare) + ""));
			} else {
				mFareTxt.setText(CommUtils.getMoney("0.0"));
			}

			mAllMoneyTxt.setText(CommUtils.getMoney(CommUtils
					.getTwoPointDouble(allMoney) + ""));
		} else {
			mFareTxt.setText(CommUtils.getMoney("0.0"));
			mAllMoneyTxt.setText(CommUtils.getMoney("0.0"));
		}
	}

	/**
	 * ���½��㰴ť���������list����0�ſ��Խ��㡣���򲻿�ѡ��ѡ
	 */
	private void updateGotoBtn() {
		if (getCheckSize() > 0) {
			mGotoBtn.setEnabled(true);
			mGotoBtn.setText("����(" + getCheckSize() + ")");
		} else {
			mGotoBtn.setEnabled(false);
			mGotoBtn.setText(R.string.jiesuan);
		}
	}

	/**
	 * ����ȫѡ������
	 * 
	 * 
	 */
	private int getCheckSize() {
		int size = 0;
		for (int i = 0; i < mList.size(); i++) {
			if (mList.get(i).isChecked()) {
				size++;
			}
		}
		return size;
	}

	@Override
	public void onQuantityChange(int position, boolean checked) {
		if (checked) {
			updateMoney();
		}
	}
}
