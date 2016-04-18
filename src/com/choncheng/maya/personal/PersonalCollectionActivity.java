package com.choncheng.maya.personal;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.comm.entity.Goods;
import com.choncheng.maya.comm.entity.User;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.shoppingcart.GoodsDetailActivity;
import com.choncheng.maya.utils.AnimUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

/**
 * 
 * @项目名称：Maya
 * @类名称：PersonalCollectionActivity
 * @类描述： 收藏管理
 * @创建人：李波
 * @创建时间：2015-8-9 上午8:15:00
 * @版本号：v1.0
 * 
 */
public class PersonalCollectionActivity extends BaseActivity implements
		OnRefreshListener2<ListView> {
	
	private ListView mListView;
	private PersonalCollectionAdapter mAdapter;
	private List<PersonalCollection> mList;
	private PullToRefreshListView mPullToRefreshListView;
	private int page = Constants.PAGE;
	private boolean mLoadOver = false;
	private static final int LOADOVER = 1;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == LOADOVER) {
				showToast(R.string.loading_over);
				mAdapter.notifyDataSetChanged();
				mPullToRefreshListView.onRefreshComplete();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_collection_activity);
		MyApplication.getInstance().addActivity(this);
		initView();
		if (mUser != null) {
			collectgoodsLists(mUser.getUcode(), page, true);
		}
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.scgl, true);
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview);
		mPullToRefreshListView.setMode(Mode.BOTH);
		mPullToRefreshListView.setOnRefreshListener(this);
		mListView = mPullToRefreshListView.getRefreshableView();
		mList = new ArrayList<PersonalCollection>();
		mAdapter = new PersonalCollectionAdapter(mList, this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(PersonalCollectionActivity.this,
						GoodsDetailActivity.class);
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				it.putExtra(Extra.GOODS_ID, mList.get(position-1).getGood_info()
						.getGoods_id());
				startActivity(it);
				AnimUtil.setFromLeftToRight(PersonalCollectionActivity.this);
			}
		});
	}

	private void collectgoodsLists(String ucode, int page, boolean showLoading) {
		if (showLoading) {
			showProgressDialog(R.string.loading);
		}
		API.collectgoodsLists(this, ucode, page, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					List<PersonalCollection> collectionList = JSON.parseArray(
							data, PersonalCollection.class);
					if (collectionList != null) {
						mList.addAll(collectionList);
					}
					if (collectionList.size() < Constants.PAGESIZE) {
						mLoadOver = true;
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
				mPullToRefreshListView.onRefreshComplete();
			}
		});
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		String lable = DateUtils.formatDateTime(getApplicationContext(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(lable);
		page = Constants.PAGE;
		mLoadOver = false;
		mList.clear();
		collectgoodsLists(mUser.getUcode(), page, false);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		String lable = DateUtils.formatDateTime(getApplicationContext(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
		refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(lable);

		if (!mLoadOver) {
			page++;
			collectgoodsLists(mUser.getUcode(), page, false);
		} else {
			loadOver();
		}

	}

	/*
	 * 加载更多完成时调用
	 */
	private void loadOver() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(Constants.LOADING_OVER_SLEEP);
					mHandler.sendEmptyMessage(LOADOVER);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
