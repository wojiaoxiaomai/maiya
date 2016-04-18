package com.choncheng.maya.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.utils.AppInfoUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

/**
 * 
 * @项目名称：Maya
 * @类名称：GoodsCommentsActivity
 * @类描述： 商品评价
 * @创建人：李波
 * @创建时间：2015-8-11 下午2:34:10
 * @版本号：v1.0
 * 
 */
public class GoodsCommentsActivity extends BaseActivity implements
		OnRefreshListener2<ListView> {
	private RadioGroup mGroup;
	private RadioButton mBtn1;// 全部
	private RadioButton mBtn2;// 好
	private RadioButton mBtn3;// 中
	private RadioButton mBtn4;// 差
	private ImageView mLine;
	private int mScreenWidth;
	private float mCurrentCheckedRadioLeft;// 当前被选中的RadioButton距离左侧的距离
	private ListView mListView;
	private List<GoodsComments> mList;
	private GoodsCommentsAdapter mAdapter;
	private String goods_id;
	private PullToRefreshListView mPullToRefreshListView;
	private int page = Constants.PAGE;
	private boolean mLoadOver = false;
	private static final int LOADOVER = 1;
	private int level = 0;// 0全部1好2中3差
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
		setContentView(R.layout.goods_comments_activity);
		initView();
		Intent it = getIntent();
		if (it != null) {
			goods_id = it.getStringExtra(Extra.GOODS_ID);
			goodsevaluationLists(goods_id, level);
			goodsevaluationContentsNumber(goods_id, 0, mBtn1);
			goodsevaluationContentsNumber(goods_id, 1, mBtn2);
			goodsevaluationContentsNumber(goods_id, 2, mBtn3);
			goodsevaluationContentsNumber(goods_id, 3, mBtn4);
		}
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.good_comments, true);
		initRadioGroup();
		initListView();
	}

	private void initListView() {
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview);
		mPullToRefreshListView.setMode(Mode.BOTH);
		mPullToRefreshListView.setOnRefreshListener(this);
		mListView = mPullToRefreshListView.getRefreshableView();
		mList = new ArrayList<GoodsComments>();
		mAdapter = new GoodsCommentsAdapter(mList, this);
		mListView.setAdapter(mAdapter);
	}

	private void initRadioGroup() {
		mLine = (ImageView) findViewById(R.id.line);
		mScreenWidth = AppInfoUtil.getWith(this);
		LinearLayout.LayoutParams params = new LayoutParams(mScreenWidth / 4, 5);
		mLine.setLayoutParams(params);
		mGroup = (RadioGroup) findViewById(R.id.group);
		mBtn1 = (RadioButton) findViewById(R.id.btn_1);
		mBtn2 = (RadioButton) findViewById(R.id.btn_2);
		mBtn3 = (RadioButton) findViewById(R.id.btn_3);
		mBtn4 = (RadioButton) findViewById(R.id.btn_4);
		mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int radioButtonId = group.getCheckedRadioButtonId();
				// 根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton) findViewById(radioButtonId);
				AnimationSet animationSet = new AnimationSet(true);
				TranslateAnimation translateAnimation;
				translateAnimation = new TranslateAnimation(
						mCurrentCheckedRadioLeft, rb.getLeft(), 0f, 0f);
				animationSet.addAnimation(translateAnimation);
				animationSet.setFillBefore(true);
				animationSet.setFillAfter(true);
				animationSet.setDuration(300);

				mLine.startAnimation(animationSet);// 开始上面蓝色横条图片的动画切换
				mCurrentCheckedRadioLeft = rb.getLeft();// 更新当前蓝色横条距离左边的距离
				mLine.setLayoutParams(new LinearLayout.LayoutParams(rb
						.getRight() - rb.getLeft(), 4));

				page = Constants.PAGE;
				mLoadOver = false;
				mList.clear();
				mAdapter.notifyDataSetChanged();
				switch (checkedId) {
				case R.id.btn_1:
					goodsevaluationLists(goods_id, 0);
					level = 0;
					break;
				case R.id.btn_2:
					goodsevaluationLists(goods_id, 1);
					level = 1;
					break;

				case R.id.btn_3:
					goodsevaluationLists(goods_id, 2);
					level = 2;
					break;

				case R.id.btn_4:
					goodsevaluationLists(goods_id, 3);
					level = 3;
					break;
				default:
					break;
				}
			}
		});
	};

	/**
	 * 获取评论列表
	 * 
	 * @param goods_id
	 */
	private void goodsevaluationLists(String goods_id, int level) {
		showProgressDialog(R.string.loading);
		API.goodsevaluationLists(goods_id, level, 0, page,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							if (!TextUtils.isEmpty(data)) {
								List<GoodsComments> goodsCommentList = JSON
										.parseArray(data, GoodsComments.class);
								if (goodsCommentList != null) {
									mList.addAll(goodsCommentList);
								}

								if (goodsCommentList.size() < Constants.PAGESIZE) {
									mLoadOver = true;
								}
							} else {
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

	/**
	 * 获取评论数量
	 * 
	 * @param goods_id
	 * @param level
	 * @param textView
	 */
	private void goodsevaluationContentsNumber(String goods_id,
			final int level, final RadioButton radioButton) {
		API.goodsevaluationContentsNumber(goods_id, level, 0,
				new ResponseHandler() {
					@Override
					public void onRese(String data, int state) {
						switch (state) {
						case STATE_OK:
							JSONObject jsonObject = JSON.parseObject(data);
							if (jsonObject != null) {
								String totalcount = jsonObject
										.getString("totalcount");
								if (!TextUtils.isEmpty(totalcount)) {
									switch (level) {
									case 0:
										radioButton.setText("全部\n" + "("
												+ totalcount + ")");
										break;
									case 1:
										radioButton.setText("好评\n" + "("
												+ totalcount + ")");
										break;
									case 2:
										radioButton.setText("中评\n" + "("
												+ totalcount + ")");
										break;
									case 3:
										radioButton.setText("差评\n" + "("
												+ totalcount + ")");
										break;
									default:
										break;
									}
								}
							}
							break;

						default:
							break;
						}
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
		// collectgoodsLists(mUser.getUcode(), page, false);

		goodsevaluationLists(goods_id, level);

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
			// collectgoodsLists(mUser.getUcode(), page, false);
			goodsevaluationLists(goods_id, level);
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
