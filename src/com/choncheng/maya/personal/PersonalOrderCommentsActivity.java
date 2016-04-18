package com.choncheng.maya.personal;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.RadioGroup;

import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.customview.ResizeListView;

/**
 * 
 * @项目名称：Maya
 * @类名称：PersonalOrderCommentsActivity
 * @类描述：我的 订单商品 评价评论页
 * @创建人：李波
 * @创建时间：2015-8-9 上午8:15:00
 * @版本号：v1.0
 * 
 */
public class PersonalOrderCommentsActivity extends BaseActivity {
	private RadioGroup mGroup;

	private ResizeListView mListView;
	private List<PersonalOrderDetail> mList;
	private PersonalOrderCommentsAdapter mAdapter;

	private Button mSubmitBtn;// 提交按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_order_comments_activity);
		MyApplication.getInstance().addActivity(this);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.pingjia, true);
		initRadioGroup();
		initLitView();
		mSubmitBtn = (Button) findViewById(R.id.submit_btn);
		mSubmitBtn.setOnClickListener(this);
	}

	private void initLitView() {
		mListView = (ResizeListView) findViewById(R.id.listview);
//		mListView.setOnScrollListener(new MyScrollListener());
		mList = new ArrayList<PersonalOrderDetail>();
		mAdapter = new PersonalOrderCommentsAdapter(mList, this);
		mListView.setAdapter(mAdapter);
		List<PersonalOrderDetail> personalOrderDetailList = MyApplication
				.getInstance().getPersonalOrderCommentsList();
		if (personalOrderDetailList != null) {
			mList.addAll(personalOrderDetailList);
		}
		mAdapter.notifyDataSetChanged();
	};

	private void initRadioGroup() {
		mGroup = (RadioGroup) findViewById(R.id.group);
		mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.btn_1:
					for (int i = 0; i < mList.size(); i++) {
						mList.get(i).setLevel(1);
					}
					break;
				case R.id.btn_2:
					for (int i = 0; i < mList.size(); i++) {
						mList.get(i).setLevel(2);
					}
					break;
				case R.id.btn_3:
					for (int i = 0; i < mList.size(); i++) {
						mList.get(i).setLevel(3);
					}
					break;
				default:
					break;
				}
				mAdapter.notifyDataSetChanged();
			}
		});
	};

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (!checkNetwork()) {
			return;
		}
		if (mUser == null) {
			return;
		}
		if (v.getId() == R.id.submit_btn) {
			// 提交按钮
			if (mList.isEmpty()) {
				return;
			}

			StringBuilder goods_id_builder = new StringBuilder();
			StringBuilder spec_id_builder = new StringBuilder();
			StringBuilder level_builder = new StringBuilder();
			StringBuilder content_builder = new StringBuilder();
			for (int i = 0; i < mList.size(); i++) {
				PersonalOrderDetail orderDetail = mList.get(i);
				goods_id_builder.append(orderDetail.getGoods_id())
						.append("&-&");
				spec_id_builder.append(orderDetail.getSpec_id()).append("&-&");
				level_builder.append(orderDetail.getLevel()).append("&-&");
				content_builder.append(orderDetail.getComments_content())
						.append("&-&");
			}

			String order_id = mList.get(0).getOrder_id();
			showProgressDialog(R.string.loading_submit_comments);
			API.goodsevaluationAddEval(this, mUser.getUcode(), order_id,
					goods_id_builder.toString(), spec_id_builder.toString(),
					level_builder.toString(), content_builder.toString(),
					new ResponseHandler() {

						@Override
						public void onRese(String data, int state) {
							dismissDialog();
							switch (state) {
							case STATE_OK:
								showToast(R.string.order_comments_sucess);
								Intent it = new Intent(
										PersonalOrderCommentsActivity.this,
										PersonalOrderActivity.class);
								it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
										| Intent.FLAG_ACTIVITY_CLEAR_TOP);
								it.putExtra(Extra.ORDER_TYPE,
										Constants.ORDER_IS_OVER);
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
	}

	protected class MyScrollListener implements OnScrollListener {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// do nothing
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
				View currentFocus = getCurrentFocus();
				if (currentFocus != null) {
					currentFocus.clearFocus();
				}
			}
		}

	}
}
