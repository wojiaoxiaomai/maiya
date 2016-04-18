package com.choncheng.maya.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Text;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.banner.ScrollImage;
import com.choncheng.maya.comm.entity.Advertisement;
import com.choncheng.maya.comm.entity.Goods;
import com.choncheng.maya.comm.entity.JsonResult;
import com.choncheng.maya.comm.entity.PageInfo;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.homepage.DiscountAndHotGoods;
import com.choncheng.maya.homepage.HomePageActivity;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.AppInfoUtil;
import com.choncheng.maya.utils.CommUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * 
 * @项目名称：Maya
 * @类名称：GoodsActivity
 * @类描述： 商品页（点击banner或者推荐栏后跳转到的商品页）
 * @创建人：李波
 * @创建时间：2015-8-10 下午4:04:39
 * @版本号：v1.0
 * 
 *==>2015.12.29
     1.添加上拉翻页 zuoyan		  
 * 
 */
public class GoodsActivity extends BaseActivity {
	private GridView mGridView;
	private GoodsAdapter mAdapter;
	private List<Goods> mList;

	// banner相关
	private ScrollImage mScrollImage;
	private List<Bitmap> mScrollImageBitmapList;
	private int width;

	private ImageView mBackImage;
	private ImageView mShopcarImage;

	private String mAdId;// 广告id
	//==>2015.12.29
	private PullToRefreshScrollView mScrollView;
	private int currentpage=1;
	//<==2015.12.29

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_activity);
		MyApplication.getInstance().addActivity(this);
		initView();
		Intent it = getIntent();
		if (it != null) {
			mAdId = it.getStringExtra(Extra.AD_ID);
			adgoodsLists();
			adlistsInfo(mAdId);
		}
	}

	@Override
	protected void initView() {
		super.initView();

		mGridView = (GridView) findViewById(R.id.gridview);
		//==>2015.12.29
		mScrollView=(PullToRefreshScrollView)findViewById(R.id.mcrollview);
		mScrollView.setMode(Mode.PULL_FROM_END);//下拉
		mScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				currentpage++;
				adgoodsLists();
			}
		});
		//<==2015.12.29
		mList = new ArrayList<Goods>();
		mAdapter = new GoodsAdapter(mList, this);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(GoodsActivity.this,
						GoodsDetailActivity.class);
				it.putExtra(Extra.GOODS_ID, mList.get(position).getGoods_id());
				startActivity(it);
				AnimUtil.setFromLeftToRight(GoodsActivity.this);
			}
		});
		width = AppInfoUtil.getWith(this);
		mScrollImage = (ScrollImage) findViewById(R.id.simage);
		mScrollImageBitmapList = new ArrayList<Bitmap>();
		mBackImage = (ImageView) findViewById(R.id.back_img);
		mBackImage.setOnClickListener(this);
		mShopcarImage = (ImageView) findViewById(R.id.shopcar_img);
		mShopcarImage.setOnClickListener(this);

	}

	/*
	 * 轮训图片获取失败的时候
	 */
	private void scroollImageFail() {
//		mScrollImageBitmapList.clear();
//		mScrollImageBitmapList.add(BitmapFactory.decodeResource(getResources(),
//				R.drawable.goods_default));
//		mScrollImageBitmapList.add(BitmapFactory.decodeResource(getResources(),
//				R.drawable.goods_default));
//		mScrollImage.setHeight(width);
//		mScrollImage.setBitmapList(mScrollImageBitmapList);
//		mScrollImage.start(3000);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.back_img:
			finish();
			AnimUtil.setFromRightToLeft(this);
			break;
		case R.id.shopcar_img:
			if (MyApplication.getInstance().isLogin()) {
				Intent it = new Intent(this, ShoppingCartActivity.class);
				MyApplication.getInstance().setShopCartNeedUpdate(true);
				MyApplication.getInstance().setShopCartNeedback(true);
				startActivity(it);
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
	 * 某个广告下的产品
	 * 
	 * @param ad_id
	 */
	private void adgoodsLists() {
		showProgressDialog(R.string.loading);
		API.adgoodsLists(mAdId, currentpage+"",new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					//==>2015.12.30 添加翻页处理
					mScrollView.onRefreshComplete();
					JsonResult jsonResult = JSON.parseObject(
							data, JsonResult.class);
					
					PageInfo pageInfo=jsonResult.getPaging();
					if(pageInfo!=null){
						if(pageInfo.getPage()==pageInfo.getNumberofpage()){
							mScrollView.setMode(Mode.DISABLED);
						}
					}
					List<Goods> goodsList = JSON.parseArray(jsonResult.getData(), Goods.class);
					if (goodsList != null) {
						if(currentpage==1){
							mList.clear();
						}
						mList.addAll(goodsList);
					}
					//<==2015.12.30
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
	 * 某个广告的详情
	 * 
	 * @param ad_id
	 *            广告id
	 * @param response
	 */
	private void adlistsInfo(String ad_id) {
		API.adlistsInfo(ad_id, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				switch (state) {
				case STATE_OK:

					// 取banner图
					JSONObject jsonObject = JSON.parseObject(data);
					if (jsonObject != null) {
						String photos = jsonObject.getString("photos");
						if (!TextUtils.isEmpty(photos)) {
							List<String> photoList = JSON.parseArray(photos,
									String.class);

							if (photoList != null && !photoList.isEmpty()) {
								/*List<String> imageList = new ArrayList<String>();
								for (int i = 0; i < photoList.size(); i++) {
									imageList.add(CommUtils
											.getImageUrl(photoList.get(i)));
								}*/
								mScrollImage.setHeight(AppInfoUtil
										.getWith(GoodsActivity.this));
								mScrollImage.setImageList(photoList,
										GoodsActivity.this);
								mScrollImage.start(Constants.BANNER_TIME);
							} else {
//								showToast(R.string.data_emptye);
							}
						}
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
