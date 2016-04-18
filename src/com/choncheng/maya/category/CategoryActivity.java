package com.choncheng.maya.category;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.login.LoginActivity;
import com.choncheng.maya.personal.PersonalCenterMsgActivity;
import com.choncheng.maya.search.SearchActivity;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.LocalSettings;

/**
 * 
 * @项目名称：Maya
 * @类名称：CategoryActivity
 * @类描述： 分类
 * @创建人：李波
 * @创建时间：2015-8-7 下午8:41:44
 * @版本号：v1.0
 * 
 */
public class CategoryActivity extends BaseActivity {
	private ListView mCategoryFirstLevalListView;
	private List<Category> mCategoryFirstLevalList;
	private CategoryFirstLevalAdapter mCategoryFirstLevalAdapter;

	private ListView mCategorySecondLevalListView;
	private List<CategorySecondLeval> mCategorySecondLevalList;
	private CategorySecondLevalAdapter mCategorySecondLevalAdapter;
	private EditText mSearchEdit;

	private ImageView mMsgImg;
	private ImageView mMsgTipImageView;// 红点提示
	
	private String title = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_activity);
		initView();
		getFirstLevalCategory();
	}

	@Override
	protected void initView() {
		super.initView();
		mMsgImg = (ImageView) findViewById(R.id.msg_img);
		mMsgImg.setOnClickListener(this);
		mMsgTipImageView = (ImageView) findViewById(R.id.msg_tip_image);
		mSearchEdit = (EditText) findViewById(R.id.search_edit);
		mSearchEdit.setOnClickListener(this);
		mCategoryFirstLevalListView = (ListView) findViewById(R.id.left_listview);
		mCategoryFirstLevalList = new ArrayList<Category>();
		mCategoryFirstLevalAdapter = new CategoryFirstLevalAdapter(
				mCategoryFirstLevalList, this);
		mCategoryFirstLevalListView.setAdapter(mCategoryFirstLevalAdapter);

		mCategoryFirstLevalListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						mCategoryFirstLevalAdapter.setIndex(position);
						mCategoryFirstLevalAdapter.notifyDataSetChanged();
						mCategoryFirstLevalListView.setSelection(position);

						mCategorySecondLevalList.clear();
						mCategorySecondLevalAdapter.notifyDataSetChanged();
						// 获取二级菜单
						String cate_id = mCategoryFirstLevalList.get(position)
								.getCart_id();
						
						title = mCategoryFirstLevalList.get(position)
								.getCate_name();
						
						getSecondLevalCategory(cate_id,title);
					}
				});

		// 二级菜单
		
		mCategorySecondLevalListView = (ListView) findViewById(R.id.right_listview);
		mCategorySecondLevalList = new ArrayList<CategorySecondLeval>();
		mCategorySecondLevalAdapter = new CategorySecondLevalAdapter(
				mCategorySecondLevalList, this);
		mCategorySecondLevalListView.setAdapter(mCategorySecondLevalAdapter);
	};

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent it = null;
		switch (v.getId()) {
		case R.id.search_edit:
			it = new Intent(this, SearchActivity.class);
			break;
		case R.id.msg_img:
			if (MyApplication.getInstance().isLogin()) {
				it = new Intent(this, PersonalCenterMsgActivity.class);
				LocalSettings.putBoolean(LocalSettings.KEY_NEW_MSG, false);
			} else {
				it = new Intent(this, LoginActivity.class);
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			}
			break;
		default:
			break;
		}
		if (it != null) {
			startActivity(it);
			AnimUtil.setFromLeftToRight(this);
		}
	}

	/**
	 * 获取第一级菜单的列表
	 */
	private void getFirstLevalCategory() {
		API.goodscateLists(null, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				switch (state) {
				case STATE_OK:
					List<Category> categoryLevals = JSON.parseArray(data,
							Category.class);
					if (categoryLevals != null) {
						for (int i = 0; i < categoryLevals.size(); i++) {
							if (categoryLevals.get(i).getP_id().equals("0")) {
								// 顶级菜单
								mCategoryFirstLevalList.add(categoryLevals
										.get(i));
							}
						}
						if (!categoryLevals.isEmpty()) {
							mCategorySecondLevalList.clear();
							mCategorySecondLevalAdapter.notifyDataSetChanged();
							// 获取二级菜单
							String cate_id = mCategoryFirstLevalList.get(0)
									.getCart_id();
							title = mCategoryFirstLevalList.get(0).getCate_name();
							Log.i("msg", title);
							getSecondLevalCategory(cate_id,title);
						}
					}
					mCategoryFirstLevalAdapter.notifyDataSetChanged();
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
	 * 获取二级菜单
	 * 
	 * @param cate_id
	 */
	private void getSecondLevalCategory(String cate_id,String name) {
		showProgressDialog(R.string.loading, false);
		API.goodscateLists(cate_id, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {

				switch (state) {
				case STATE_OK:
					List<Category> categoryLevals = JSON.parseArray(data,
							Category.class);
					if (categoryLevals != null) {
						for (int i = 0; i < categoryLevals.size(); i++) {
							getThreeLevalCategory(categoryLevals.get(i)
									.getCart_id(), categoryLevals.get(i));
							categoryLevals.get(i).setTitle(title);
						}
					}
					break;
				case STATE_FAIL:
					showToast(data);
					updateSecondLevalListView();
					break;
				case STATE_ERROR:
					showToast(R.string.http_respone_error);
					updateSecondLevalListView();
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * 获取三级菜单
	 * 
	 * @param cate_id
	 */
	private void getThreeLevalCategory(String cate_id, final Category category) {
		API.goodscateLists(cate_id, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					List<Category> categoryLevals = JSON.parseArray(data,
							Category.class);
					if (categoryLevals != null) {
						CategorySecondLeval secondLeval = new CategorySecondLeval();
						secondLeval.setCategory(category);
						secondLeval.setThreeLevalList(categoryLevals);
						mCategorySecondLevalList.add(secondLeval);
					}
					mCategorySecondLevalAdapter.notifyDataSetChanged();
					break;
				case STATE_FAIL:
					showToast(data);
					updateSecondLevalListView();
					break;
				case STATE_ERROR:
					showToast(R.string.http_respone_error);
					updateSecondLevalListView();
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * 加载失败的时候，刷新二级菜单页面
	 */
	private void updateSecondLevalListView() {
		mCategorySecondLevalList.clear();
		mCategorySecondLevalAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean isNewMessage = LocalSettings.getBoolean(
				LocalSettings.KEY_NEW_MSG, false);
		mMsgTipImageView.setVisibility(isNewMessage ? View.VISIBLE : View.GONE);
	}
}
