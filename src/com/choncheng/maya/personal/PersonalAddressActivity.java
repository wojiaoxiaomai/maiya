package com.choncheng.maya.personal;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.customview.swipelistview.SwipeMenu;
import com.choncheng.maya.customview.swipelistview.SwipeMenuCreator;
import com.choncheng.maya.customview.swipelistview.SwipeMenuItem;
import com.choncheng.maya.customview.swipelistview.SwipeMenuListView;
import com.choncheng.maya.customview.swipelistview.SwipeMenuListView.OnMenuItemClickListener;
import com.choncheng.maya.personal.PersonalAddressAdapter.UpdateAddressListCallback;
import com.choncheng.maya.shoppingcart.GoodsSettlementActivity;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.AppInfoUtil;

/**
 * 
 * @项目名称：Maya
 * @类名称：PersonalAddressActivity
 * @类描述：我的 订单
 * @创建人：李波
 * @创建时间：2015-8-9 上午8:15:00
 * @版本号：v1.0
 * 
 */
public class PersonalAddressActivity extends BaseActivity implements
		UpdateAddressListCallback {
	private Button mAddAddressBtn;
	private SwipeMenuListView mListView;
	private List<PersonalAddress> mList;
	private PersonalAddressAdapter mAdapter;
	private View mEmptyView;// listview为空的时候显示的

	private boolean isFromGoodssettmentActivity = false;// 是否来自结算

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_add_activity);
		MyApplication.getInstance().addActivity(this);
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mUser != null) {
			useraddressLists(mUser.getUcode());
		}
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.dizhiguanli, true);
		if (getIntent() != null) {
			isFromGoodssettmentActivity = getIntent().getBooleanExtra(
					Extra.GOODSSETTLEMENT, false);
		}
		mAddAddressBtn = (Button) findViewById(R.id.add_address_btn);
		mAddAddressBtn.setOnClickListener(this);

		initListView();

	}

	private void initListView() {
		mEmptyView = findViewById(R.id.no_address_view);
		mListView = (SwipeMenuListView) findViewById(R.id.listview);
		mList = new ArrayList<PersonalAddress>();
		mAdapter = new PersonalAddressAdapter(mList, this, this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = null;
				if (isFromGoodssettmentActivity) {
					it = new Intent();
					it.putExtra(Extra.PERSONAL_ADDRESS, mList.get(position));
					setResult(RESULT_OK, it);
					finish();
				} else {
					it = new Intent(PersonalAddressActivity.this,
							PersonalAddressAddActivity.class);
					it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					it.putExtra(Extra.ADDRESS, mList.get(position));
					startActivity(it);
					AnimUtil.setFromLeftToRight(PersonalAddressActivity.this);
				}

			}
		});
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				// set item width
				// openItem.setWidth(dp2px(90));
				// set item title
				openItem.setTitle("Open");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				// menu.addMenuItem(openItem);

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(getResources()
						.getColor(R.color.green)));
				// set item width
				deleteItem.setWidth(AppInfoUtil.dip2px(
						PersonalAddressActivity.this, 90));
				// set a icon
				deleteItem.setIcon(R.drawable.delete_pic);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		mListView.setMenuCreator(creator);

		// step 2. listener item click event
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu,
					int index) {
				PersonalAddress address = mList.get(position);
				switch (index) {
				case 0:
					// 删除
					if (mUser != null) {
						useraddressCancel(mUser.getUcode(),
								address.getAddr_id(), address);
					}
					break;
				}
				return false;
			}
		});

	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (!checkNetwork()) {
			return;
		}
		if (v.getId() == R.id.add_address_btn) {
			Intent it = new Intent(this, PersonalAddressAddActivity.class);
			it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(it);
			AnimUtil.setFromLeftToRight(this);
		}
	}

	/**
	 * 获取地址列表
	 * 
	 * @param ucode
	 */
	private void useraddressLists(String ucode) {
		showProgressDialog(R.string.loading_get_address);
		API.useraddressLists(this,ucode, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					mList.clear();
					List<PersonalAddress> addressList = JSON.parseArray(data,
							PersonalAddress.class);
					if (addressList != null) {
						mList.addAll(addressList);
					}
					if (!mList.isEmpty()) {
						mEmptyView.setVisibility(View.GONE);
					}
					mAdapter.notifyDataSetChanged();
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
	 * 取消地址
	 * 
	 * @param ucode
	 * @param addr_id
	 */
	private void useraddressCancel(String ucode, String addr_id,
			final PersonalAddress address) {
		showProgressDialog(R.string.loading_cancle_address);
		API.useraddressCancel(this,ucode, addr_id, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					mList.remove(address);
					mAdapter.notifyDataSetChanged();
					updateListView();
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
	public void setDefaultAdd(String addr_id) {
		// 设置默认地址
		if (mUser != null) {
			useraddressSetDefault(mUser.getUcode(), addr_id);
		}
	}

	/**
	 * 更新listview,为空的时候更换界面
	 */
	private void updateListView() {
		if (mList == null || mList.isEmpty()) {
			mEmptyView.setVisibility(View.VISIBLE);
		} else {
			mEmptyView.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置默认地址
	 * 
	 * @param ucode
	 * @param addr_id
	 */
	private void useraddressSetDefault(String ucode, String addr_id) {
		showProgressDialog(R.string.loading);
		API.useraddressSetDefault(this,ucode, addr_id, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					if (mUser != null) {
						useraddressLists(mUser.getUcode());
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
