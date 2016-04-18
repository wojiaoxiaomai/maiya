package com.choncheng.maya.search;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.R;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.shoppingcart.GoodsDetail;
import com.choncheng.maya.shoppingcart.GoodsDetailActivity;
import com.choncheng.maya.utils.AnimUtil;

/**
 * 
 * @项目名称：Maya
 * @类名称：SearchActivity
 * @类描述： 搜索商品
 * @创建人：李波
 * @创建时间：2015-8-9 下午9:50:09
 * @版本号：v1.0
 * 
 */
public class SearchActivity extends BaseActivity {
	private TextView mSearchTxt;
	private ImageView mBackImage;
	private EditText mSeachEdit;// 关键字搜索
	private ImageView mSeachClearImage;// 清除关键字
	private ListView mListView;
	private List<GoodsDetail> mList;
	private SearchAdapter mAdapter;
	private View mEmptyView;// listview为空的时候显示

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		mBackImage = (ImageView) findViewById(R.id.back_image);
		mBackImage.setOnClickListener(this);
		mSearchTxt = (TextView) findViewById(R.id.search_txt);
		mSearchTxt.setOnClickListener(this);
		mSeachClearImage = (ImageView) findViewById(R.id.search_delete_image);
		mSeachClearImage.setOnClickListener(this);
		mSeachEdit = (EditText) findViewById(R.id.search_edit);
		mSeachEdit.setFilters(new InputFilter[] { filter });
		mSeachEdit.addTextChangedListener(new TextWatcher() {

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
				String select = s.toString();
				// 不及时查询了点击查询再查询
				// if (TextUtils.isEmpty(select)) {
				// mSeachClearImage.setVisibility(View.GONE);
				// mList.clear();
				// mAdapter.notifyDataSetChanged();
				// updateListView();
				// } else {
				// mSeachClearImage.setVisibility(View.VISIBLE);
				// goodslistsLists("", 0, 0, 0, 0, 0, select, 0, 0, 0, 0);
				// }

				if (select.length() > 0) {
					mSearchTxt.setTextColor(getResources().getColor(
							R.color.green));
				} else {
					mSearchTxt.setTextColor(getResources().getColor(
							R.color.textview_grey));
				}
			}
		});

		mListView = (ListView) findViewById(R.id.listview);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(SearchActivity.this,
						GoodsDetailActivity.class);
				it.putExtra(Extra.GOODS_ID, mList.get(position).getGoods_id());
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(it);
				AnimUtil.setFromLeftToRight(SearchActivity.this);
			}
		});
		mList = new ArrayList<GoodsDetail>();
		mAdapter = new SearchAdapter(mList, this);
		mListView.setAdapter(mAdapter);
		mEmptyView = findViewById(R.id.empty_view);

	};

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.search_txt:
			Intent it = new Intent(this, SearchFilterActivity.class);
			it.putExtra(Extra.SELECT, mSeachEdit.getText().toString());
			it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);

			startActivity(it);
			AnimUtil.setFromLeftToRight(this);
			break;
		case R.id.back_image:
			finish();
			AnimUtil.setFromRightToLeft(this);
			break;
		case R.id.search_delete_image:
			mSeachEdit.setText("");
			break;
		default:
			break;
		}
	}

	/**
	 * 产品列表
	 * 
	 * @param shop_id
	 * @param cate_id
	 *            当前分类（最后一级分类）
	 * @param cate_id_1
	 *            顶级分类（一级分类）
	 * @param cate_id_2
	 * @param cate_id_3
	 * @param cate_id_4
	 * @param select
	 *            搜索关键字
	 * @param page
	 *            页码
	 * @param pagesize
	 *            每页记录数
	 * @param sort_price
	 *            价格排序 1：降序 2： 升序
	 * @param sort_sales
	 *            销量排序 1：降序 2： 升序
	 * @param response
	 */
	private void goodslistsLists(String shop_id, int cate_id, int cate_id_1,
			int cate_id_2, int cate_id_3, int cate_id_4, String select,
			int page, int pagesize, int sort_price, int sort_sales) {
		showProgressDialog(R.string.loading);
		API.goodslistsLists(shop_id, cate_id, cate_id_1, cate_id_2, cate_id_3,
				cate_id_4, select, page, pagesize, sort_price, sort_sales,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						mList.clear();
						switch (state) {
						case STATE_OK:
							List<GoodsDetail> goodsList = JSON.parseArray(data,
									GoodsDetail.class);
							if (goodsList != null) {
								mList.addAll(goodsList);
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
						updateListView();
					}

				});
	}

	private void updateListView() {
		if (mList == null || mList.isEmpty()) {
			mEmptyView.setVisibility(View.VISIBLE);
		} else {
			mEmptyView.setVisibility(View.GONE);
		}
	}

	/*
	 * EditText输入限制
	 */
	private InputFilter filter = new InputFilter() {
		@Override
		public CharSequence filter(CharSequence source, int start, int end,
				Spanned dest, int dstart, int dend) {
			if (source.equals(" ") || source.toString().contentEquals("\n"))
				return "";
			else
				return null;
		}
	};
}
