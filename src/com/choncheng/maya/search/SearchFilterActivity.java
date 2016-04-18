package com.choncheng.maya.search;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.choncheng.maya.category.Category;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.shoppingcart.GoodsDetail;
import com.choncheng.maya.shoppingcart.GoodsDetailActivity;
import com.choncheng.maya.shoppingcart.ShoppingCartActivity;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.CommUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 
 * @项目名称：Maya
 * @类名称：SearchActivity
 * @类描述： 过滤搜索商品（按分类，价格，销量排序）
 * @创建人：李波
 * @创建时间：2015-8-9 下午9:50:09
 * @版本号：v1.0
 * 
 */
public class SearchFilterActivity extends BaseActivity {
	private static final String TAG = "SearchFilterActivity";
	private PullToRefreshListView mListView;
	private SearchFilterAdapter mAdapter;
	private List<GoodsDetail> mList;

	private ImageView mBackImage;
	private EditText mSearchEdit;
	private int sort_price = 2;// 价格排序 1：降序 2： 升序
	private int sort_sales = 1;// 销量排序 1：降序 2： 升序

	private View mPriceView;// 价格排序
	private ImageView mPriceImage;
	private View mSaleView;// 销量排序
	private ImageView mSaleImage;
	private View mCategoryView;// 分类
	private View mEmptyView;// listview为空的时候
	private int cate_id = 0;// 当前分类id
	private String p_id = "0";// 当前分类id的父id
	private String select = "";// 搜索关键字
	private int search_type = Constants.SEARCH_TYPE_KEY;// 搜索类型：1.普通搜索2来自分类中的二级目录3来自分类中三级目录
	private List<Category> mCategoryList;
	private boolean isCategory = false;// 是否来自分类，来自分类需要显示悬浮购物车
	private ImageView mShopcartImage;// 购物车图标
	private static final int DIALOG_ONE = 1;
	private static final int DIALOG_TWO = 2;
	private int show_dialog_number = DIALOG_ONE;// dialog弹几次
	
	private String title = "";
	private Intent it;
	private int currrentpage=1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_filter_activity);
		initView();
		goodslistsLists("", cate_id, 0, 0, 0, 0, select, currrentpage, 0, 0, 0);
	}

	@Override
	protected void initView() {
		super.initView();
		mCategoryList = new ArrayList<Category>();
		mShopcartImage = (ImageView) findViewById(R.id.shopcar_img);
		mShopcartImage.setOnClickListener(this);
		mEmptyView = findViewById(R.id.empty_view);
		mPriceView = findViewById(R.id.price_view);
		mPriceView.setOnClickListener(this);
		mPriceImage = (ImageView) findViewById(R.id.price_image);
		mSaleView = findViewById(R.id.sale_view);
		mSaleView.setOnClickListener(this);
		mSaleImage = (ImageView) findViewById(R.id.sale_image);
		mCategoryView = findViewById(R.id.category_view);
		mCategoryView.setOnClickListener(this);
		mSearchEdit = (EditText) findViewById(R.id.search_edit);
		mSearchEdit.setOnClickListener(this);
		mBackImage = (ImageView) findViewById(R.id.back_image);
		mBackImage.setOnClickListener(this);
		mListView = (PullToRefreshListView) findViewById(R.id.listview);
		mListView.setMode(Mode.PULL_FROM_END); 
		mList = new ArrayList<GoodsDetail>();
		mAdapter = new SearchFilterAdapter(mList, this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(SearchFilterActivity.this,
						GoodsDetailActivity.class);
				//==>2015.12.26因为添加了上下拉，所有positon变了
				//it.putExtra(Extra.GOODS_ID, mList.get(position).getGoods_id());
				it.putExtra(Extra.GOODS_ID, mList.get(position-1).getGoods_id());
				//<==2015.12.26
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(it);
				AnimUtil.setFromLeftToRight(SearchFilterActivity.this);
			}
		});
		mListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				Log.i("TAG", "next_____pageinfo");
				goodslistsLists("", cate_id, 0, 0, 0, 0, select, currrentpage, 0, 0, 0);
			}
		});
		// 初始化过滤数据
		it = getIntent();
		if (it != null) {
			cate_id = it.getIntExtra(Extra.CATE_ID, 0);
			select = it.getStringExtra(Extra.SELECT);
			isCategory = it.getBooleanExtra(Extra.IS_FROM_CATEGORYACTIVITY,
					false);
			p_id = it.getStringExtra(Extra.P_ID);
			search_type = it.getIntExtra(Extra.SEARCH_TYPE,
					Constants.SEARCH_TYPE_KEY);
			mShopcartImage.setVisibility(isCategory ? View.VISIBLE : View.GONE);
			mSearchEdit.setText(select);
		}
	};

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back_image:
			finish();
			AnimUtil.setFromRightToLeft(this);
			break;
		case R.id.search_edit:
			it  = new Intent(this, SearchActivity.class);
			it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(it);
			AnimUtil.setFromLeftToRight(this);
			break;
		case R.id.price_view:
			// 价格排序
			if (sort_price == 1) {
				sort_price = 2;
				mPriceImage.setImageResource(R.drawable.fenlei_ic_jgpx_normal);
			} else {
				sort_price = 1;
				mPriceImage.setImageResource(R.drawable.fenlei_ic_rqpx_normal);
			}
			currrentpage=1;//==>2015.12.7
			goodslistsLists("", cate_id, 0, 0, 0, 0, select, currrentpage, 0, sort_price,
					0);
			break;

		case R.id.sale_view:
			if (sort_sales == 1) {
				sort_sales = 2;
				mSaleImage.setImageResource(R.drawable.fenlei_ic_jgpx_normal);
			} else {
				sort_sales = 1;
				mSaleImage.setImageResource(R.drawable.fenlei_ic_rqpx_normal);
			}
			goodslistsLists("", cate_id, 0, 0, 0, 0, select, 0, 0, 0,
					sort_sales);
			break;
		case R.id.category_view:
			// 选择分类 分三种情况
			// 1.如果普通搜索（关键字）那么这里显示分类所在的上级目录即可2.通过分类中的二级目录来，那么显示二级所有目录，在选择进入三级目录
			// 3.通过分类中的三级目录进入，那么只显示三级目录所有的条目
			// getCategoryList();
			if (DEBUG) {
				Log.e(TAG, "search_type:" + search_type + ",p_id:" + p_id);
			}
			
			it = getIntent();
			switch (search_type) {
			case Constants.SEARCH_TYPE_KEY:
				// 只显示一次dialog.
				showCategoryListKeyDialog();
				break;
			case Constants.SEARCH_TYPE_SECOND:
				// 2级目录显示2次dialog.
				title = it.getStringExtra(Extra.TITLE);
				if(title!=null){
					show_dialog_number = DIALOG_TWO;
					getCategoryList(p_id,title);
				}
				
				break;
			case Constants.SEARCH_TYPE_THIRD:
				// 三级目录只显示一次dialog.
				title = it.getStringExtra(Extra.TITLE);
				if(title!=null){
					show_dialog_number = DIALOG_ONE;
					getCategoryList(p_id,title);
				}
				break;
			default:
				show_dialog_number = DIALOG_ONE;
				getCategoryList(p_id,title);
				break;
			}
			break;
		case R.id.shopcar_img:
			// 购物车
			if (MyApplication.getInstance().isLogin()) {
				Intent intent = new Intent(this, ShoppingCartActivity.class);
				MyApplication.getInstance().setShopCartNeedUpdate(true);
				MyApplication.getInstance().setShopCartNeedback(true);
				startActivity(intent);
				AnimUtil.setFromLeftToRight(this);
			} else {
				CommUtils.lauchLoginActivity(this);
			}
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
						mListView.onRefreshComplete();
						if(currrentpage==1){
							mList.clear();
							mListView.setMode(Mode.PULL_FROM_END);
						}
						
						switch (state) {
						case STATE_OK:
							List<GoodsDetail> goodsList = JSON.parseArray(data,
									GoodsDetail.class);
							if (goodsList != null) {
								mList.addAll(goodsList);
								currrentpage++;
							}

							if (search_type == Constants.SEARCH_TYPE_KEY) {
								// 如果是通过关键字搜索得到的结果
								mCategoryList.clear();
								for (int i = 0; i < mList.size(); i++) {
									Category category = new Category();
									GoodsDetail goodsDetail = mList.get(i);
									category.setCate_name(goodsDetail
											.getCate_name());
									category.setCart_id(goodsDetail
											.getCate_id());
									mCategoryList.add(category);
								}
							}
							break;
							//==>2015.12.7
						case STATE_EMPTY:
							//没有更多数据了
							List<GoodsDetail> goodsList2 = JSON.parseArray(data,
									GoodsDetail.class);
							if (goodsList2 != null) {
								mList.addAll(goodsList2);
								mListView.setMode(Mode.DISABLED);
							}

							if (search_type == Constants.SEARCH_TYPE_KEY) {
								// 如果是通过关键字搜索得到的结果
								mCategoryList.clear();
								for (int i = 0; i < mList.size(); i++) {
									Category category = new Category();
									GoodsDetail goodsDetail = mList.get(i);
									category.setCate_name(goodsDetail
											.getCate_name());
									category.setCart_id(goodsDetail
											.getCate_id());
									mCategoryList.add(category);
								}
							}
							break;
							//<==2015.12.7 

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

	/**
	 * 刷新listview
	 */
	private void updateListView() {
		if (mList == null || mList.isEmpty()) {
			mEmptyView.setVisibility(View.VISIBLE);
		} else {
			mEmptyView.setVisibility(View.GONE);
		}
	}

	/**
	 * 获取分类的列表
	 */
	private void getCategoryList(String cart_id,final String name) {
		showProgressDialog(R.string.loading);
		API.goodscateLists(cart_id, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					mCategoryList.clear();
					List<Category> categoryLevals = JSON.parseArray(data,
							Category.class);
					if (categoryLevals != null) {
						for (int i = 0; i < categoryLevals.size(); i++) {
							mCategoryList.add(categoryLevals.get(i));
						}
					}
					showCategoryListDialog(name);
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
	 * 通过关键字搜索显示分类dialog
	 */
	private void showCategoryListKeyDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.select_category);
		if (mCategoryList.isEmpty()) {
			return;
		}
		List<String> cartegoryArrayList = new ArrayList<String>();
		cartegoryArrayList.add(mCategoryList.get(0).getCate_name());
		for (int i = 1; i < mCategoryList.size(); i++) {
			String name = mCategoryList.get(i).getCate_name();
			String name1 = mCategoryList.get(i-1).getCate_name();
			if (!name.equals(name1)) {
				cartegoryArrayList.add(mCategoryList.get(i).getCate_name());
			}
			
		}
		String[] items = cartegoryArrayList
				.toArray(new String[cartegoryArrayList.size()]);
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				currrentpage=1;
				goodslistsLists(
						"",
						Integer.parseInt(mCategoryList.get(which).getCart_id()),
						0, 0, 0, 0, select, currrentpage, 0, 0, 0);
			}
		});
		builder.create().show();
	}

	// 显示分类框
	private void showCategoryListDialog(String name) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(name);
		if (mCategoryList.isEmpty()) {
			return;
		}
		List<String> cartegoryArrayList = new ArrayList<String>();
		for (int i = 0; i < mCategoryList.size(); i++) {
			cartegoryArrayList.add(mCategoryList.get(i).getCate_name());
		}
		String[] items = cartegoryArrayList
				.toArray(new String[cartegoryArrayList.size()]);
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (show_dialog_number == DIALOG_ONE) {
					currrentpage=1;
					goodslistsLists("", Integer.parseInt(mCategoryList.get(
							which).getCart_id()), 0, 0, 0, 0, select, currrentpage, 0, 0,
							0);
					
				} else if (show_dialog_number == DIALOG_TWO) {
					show_dialog_number = DIALOG_ONE;
					getCategoryList(mCategoryList.get(which).getCart_id(),mCategoryList.get(which).getCate_name());
					
					search_type = 0;
					title = mCategoryList.get(which).getCate_name();
					p_id = mCategoryList.get(which).getCart_id();
					
					mCategoryList.clear();
					
					
				}
			}
		});
		builder.create().show();
	}
}
