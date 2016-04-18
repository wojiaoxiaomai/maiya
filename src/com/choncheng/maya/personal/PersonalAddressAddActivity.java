package com.choncheng.maya.personal;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.comm.entity.Area;
import com.choncheng.maya.contants.Extra;

/**
 * 
 * @项目名称：Maya
 * @类名称：PersonalAddressAddActivity
 * @类描述：地址管理 添加地址
 * @创建人：李波
 * @创建时间：2015-8-9 上午8:15:00
 * @版本号：v1.0
 * 
 */
public class PersonalAddressAddActivity extends BaseActivity {
	private EditText mNameEdit;
	private EditText mTelEdit;
	private EditText mStreetEdit;// 小区或者街道名字
	private EditText mAddrEdit;
	private Button mSaveBtn;
	private PersonalAddress mAddress = null;
	private ListView mListView;
	private View mListViewContainer;// listview的父控件
	private List<Area> mList;
	private PersonalAddressAddAdapter mAdapter;
	private Button mSearchImage;// 搜索区域

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_address_add_activity);
		MyApplication.getInstance().addActivity(this);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.dizhiguanli, true);
		mSearchImage = (Button) findViewById(R.id.search_img);
		mSearchImage.setOnClickListener(this);
		mNameEdit = (EditText) findViewById(R.id.name_edit);
		mTelEdit = (EditText) findViewById(R.id.tel_edit);
		mStreetEdit = (EditText) findViewById(R.id.street_edit);
		mAddrEdit = (EditText) findViewById(R.id.addr_edit);
		//==>2016.4.16 屏蔽自动搜索，有问题
		/*mStreetEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mListViewContainer.setVisibility(View.GONE);
				mAddrEdit.setEnabled(false);
				mAddrEdit.setText("");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String area = s.toString();
				mList.clear();
				if (area.length() > 0) {
					mListViewContainer.setVisibility(View.VISIBLE);
					// 开始查询
					// areaLists(area);
				} else {
					mListViewContainer.setVisibility(View.GONE);
				}
				mAdapter.notifyDataSetChanged();
			}
		});*/
		mNameEdit.addTextChangedListener(mywather);
		mTelEdit.addTextChangedListener(mywather);
		mStreetEdit.addTextChangedListener(mywather);
		mAddrEdit.addTextChangedListener(mywather);
		//<==2016.4.16 屏蔽自动搜索
	
		mSaveBtn = (Button) findViewById(R.id.save_btn);
		mSaveBtn.setOnClickListener(this);
		mListViewContainer = findViewById(R.id.listview_container);
		mListView = (ListView) findViewById(R.id.listview);
		mList = new ArrayList<Area>();
		mAdapter = new PersonalAddressAddAdapter(mList, this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mStreetEdit.setText(mList.get(position).getArea());
				mListViewContainer.setVisibility(View.GONE);
				mAddrEdit.setEnabled(true);
				findViewById(R.id.ll_detailAddress).setVisibility(View.VISIBLE);
			}
		});
		Intent it = getIntent();
		if (it != null) {
			mAddress = (PersonalAddress) it.getSerializableExtra(Extra.ADDRESS);
			if (mAddress != null) {
				mNameEdit.setText(mAddress.getLink_uname());
				mTelEdit.setText(mAddress.getTel());
				mStreetEdit.setText(mAddress.getExtend1());
				mAddrEdit.setText(mAddress.getAddress());
				mAddrEdit.setEnabled(true);
				findViewById(R.id.ll_detailAddress).setVisibility(View.VISIBLE);
			}
		}
	}
	
	
	@Override
	public void onBackPressed() {
		if(mListViewContainer.isShown()){
			mListViewContainer.setVisibility(View.GONE);
		}else{
			super.onBackPressed();
		}
		
	}
	

	/**
	 * 按钮是否可以点击
	 */
	private void showButtonEnable(){
		String name = mNameEdit.getText().toString().trim();
		String tel = mTelEdit.getText().toString().trim();
		String street = mStreetEdit.getText().toString().trim();
		String addr = mAddrEdit.getText().toString().trim();
		if (TextUtils.isEmpty(name)) {
			mSaveBtn.setEnabled(false);
			return;
		}
		if (TextUtils.isEmpty(tel)) {
			mSaveBtn.setEnabled(false);
			return;
		}
		if (TextUtils.isEmpty(street)) {
			mSaveBtn.setEnabled(false);
			return;
		}
		if (TextUtils.isEmpty(addr)) {
			mSaveBtn.setEnabled(false);
			return;
		}
		mSaveBtn.setEnabled(true);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (!checkNetwork()) {
			return;
		}
		switch (v.getId()) {
		case R.id.save_btn:
			String name = mNameEdit.getText().toString().trim();
			String tel = mTelEdit.getText().toString().trim();
			String street = mStreetEdit.getText().toString().trim();
			String addr = mAddrEdit.getText().toString().trim();
			if (TextUtils.isEmpty(name)) {
				showToast(R.string.name_empty);
				return;
			}
			if (TextUtils.isEmpty(tel)) {
				showToast(R.string.mobile_empty);
				return;
			}
			if (tel.length() != 11) {
				showToast(R.string.mobile_length_wrong);
				return;
			}
			if (TextUtils.isEmpty(street)) {
				showToast(R.string.street_empty);
				return;
			}
			if (TextUtils.isEmpty(addr)) {
				showToast(R.string.add_empty);
				return;
			}

			if (mUser != null) {

				if (mAddress == null) {
					// 新增地址
					useraddressAdd(mUser.getUcode(), name, tel, street, addr);
				} else {
					// 编辑地址
					useraddressEdit(mUser.getUcode(), mAddress.getAddr_id(),
							name, tel, street, addr);
				}

			}
			break;
		case R.id.search_img:
			// 搜索区域
			String area = mStreetEdit.getText().toString();
			if (TextUtils.isEmpty(area)) {
				showToast(R.string.street_empty);
				return;
			}
			areaLists(area);
			hiddenKeyBord(mStreetEdit);
			break;
		default:
			break;
		}
	}

	/**
	 * 新增地址
	 * 
	 * @param ucode
	 * @param link_uname
	 * @param tel
	 * @param extend1
	 * @param address
	 */
	private void useraddressAdd(String ucode, String link_uname, String tel,
			String extend1, String address) {
		showProgressDialog(R.string.loading_save_add);
		API.useraddressAdd(this, ucode, link_uname, tel, extend1, address,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:

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
	 * 修改地址信息
	 * 
	 * @param ucode
	 * @param addr_id
	 * @param link_uname
	 * @param tel
	 * @param extend1
	 * @param address
	 */
	private void useraddressEdit(String ucode, String addr_id,
			String link_uname, String tel, String extend1, String address) {
		showProgressDialog(R.string.loading_save_add);
		API.useraddressEdit(this, ucode, addr_id, link_uname, tel, extend1,
				address, new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
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
	 * 小区社区
	 * 
	 * @param ucode
	 * @param area
	 *            名字
	 * @param response
	 */
	private void areaLists(String area) {
		showProgressDialog(R.string.loading);
		API.areaLists(this, area, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				mList.clear();
				switch (state) {
				case STATE_OK:
					List<Area> areaList = JSON.parseArray(data, Area.class);
					if (areaList != null) {
						mList.addAll(areaList);
					}
					if (mList.isEmpty()) {
						showToast(R.string.area_not_support);
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
				mAdapter.notifyDataSetChanged();
				if (mList.isEmpty()) {
					mListViewContainer.setVisibility(View.GONE);
				} else {
					mListViewContainer.setVisibility(View.VISIBLE);
				}

			}
		});
	}
	
	
	TextWatcher mywather=new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			 showButtonEnable();
		}
	};
	
	
	/**
 	 * 键盘管理--隐藏
 	 */
     public void hiddenKeyBord(View v) {
         InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
         imm.hideSoftInputFromWindow(v.getWindowToken(),
                 InputMethodManager.HIDE_NOT_ALWAYS);
     }

}
