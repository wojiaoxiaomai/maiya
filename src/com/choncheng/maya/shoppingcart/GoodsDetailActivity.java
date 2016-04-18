package com.choncheng.maya.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.banner.ScrollImage;
import com.choncheng.maya.comm.entity.User;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.customview.AlertDialog;
import com.choncheng.maya.homepage.HomePageActivity;
import com.choncheng.maya.login.LoginActivity;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.AppInfoUtil;
import com.choncheng.maya.utils.CommUtils;
import com.choncheng.maya.utils.DateTime;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�GoodsDetailActivity
 * @�������� ��Ʒ����ҳ
 * @�����ˣ��
 * @����ʱ�䣺2015-8-10 ����4:43:25
 * @�汾�ţ�v1.0
 * 
 */
public class GoodsDetailActivity extends BaseActivity {
	private ImageView mBackIamgeView;

	// banner���
	private ScrollImage mScrollImage;
	private List<Bitmap> mScrollImageBitmapList;
	private int width;

	private View mCommentsView;
	private View mCallView;
	private TextView mCallTxt;// ����绰
	private Button mAddtoShopcarBtn;
	private Button mMinuBtn;// ��������
	private Button mAddBtn;// ��������
	private EditText mQuantityEdit;// ��ʾ����
	private ImageView mShopcarImage;

	private TextView mBeforePriceTxt;
	private TextView mNameTxt;
	private TextView mPriceTxt;
	private TextView mPinlunNum;// ��������
	private String goods_id;
	private TextView mUserNameTxt;// �����û���
	private TextView mDateTxt;// ����ʱ��
	private TextView mContentTxt;// ��������

//	private WebView mWebView;// �����϶������webview
	private TextView mGoodsStateTxt;// �Ƿ��л���ʾ
	private TextView mTagTxt1;// ��Ʒ����ı�ǩ1.2.3
	private TextView mTagTxt2;
	private TextView mTagTxt3;
	private ImageView mCollectImage;// �ղذ�ť

	private boolean isCollect = false;// �Ƿ��Ѿ���ӵ��ղ�
	private GoodsDetail goodsDetail;// ��Ʒ������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_detail_activity);
		MyApplication.getInstance().addActivity(this);
		initView();
		Intent it = getIntent();
		if (it != null) {
			goods_id = it.getStringExtra(Extra.GOODS_ID);
			if (!TextUtils.isEmpty(goods_id)) {
				goodsinfoInfo(goods_id);
				goodsevaluationContentsNumber(goods_id);
				goodsevaluationLists(goods_id);
				if (MyApplication.getInstance().isLogin()) {
					collectgoodsIsCollect(mUser.getUcode(), goods_id);
				}
			}

		}

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void initView() {
		super.initView();
		width = AppInfoUtil.getWith(this);

		mScrollImage = (ScrollImage) findViewById(R.id.simage);
		mScrollImageBitmapList = new ArrayList<Bitmap>();
		// scroollImageFail();

		mBackIamgeView = (ImageView) findViewById(R.id.back_img);
		mBackIamgeView.setOnClickListener(this);
		mCollectImage = (ImageView) findViewById(R.id.collect_image);
		mCollectImage.setOnClickListener(this);
		mCommentsView = findViewById(R.id.comments_view);
		mCommentsView.setOnClickListener(this);
		mCallView = findViewById(R.id.call_view);
		mCallView.setOnClickListener(this);
		mCallTxt = (TextView) findViewById(R.id.call_txt_view);
		// mCallTxt.setOnClickListener(this);

		mAddtoShopcarBtn = (Button) findViewById(R.id.add_shop_btn);
		mAddtoShopcarBtn.setOnClickListener(this);
		mShopcarImage = (ImageView) findViewById(R.id.shopcar_image);
		mShopcarImage.setOnClickListener(this);
		mBeforePriceTxt = (TextView) findViewById(R.id.before_price_txt);

		mNameTxt = (TextView) findViewById(R.id.name_txt);
		mPriceTxt = (TextView) findViewById(R.id.price_txt);
		mPinlunNum = (TextView) findViewById(R.id.pinglun_num);

		mUserNameTxt = (TextView) findViewById(R.id.user_name_txt);
		mDateTxt = (TextView) findViewById(R.id.date_txt);
		mContentTxt = (TextView) findViewById(R.id.content_txt);
		mGoodsStateTxt = (TextView) findViewById(R.id.goods_state_txt);
		mTagTxt1 = (TextView) findViewById(R.id.tag_txt_1);
		mTagTxt2 = (TextView) findViewById(R.id.tag_txt_2);
		mTagTxt3 = (TextView) findViewById(R.id.tag_txt_3);

//		mWebView = (WebView) findViewById(R.id.webview);
//		mWebView.getSettings().setJavaScriptEnabled(true);
		// ���ÿ���֧������
		// webview.getSettings().setSupportZoom(true);
		// ���ó������Ź���
		// webview.getSettings().setBuiltInZoomControls(true);
		// ������Ҫ��ʾ����ҳ
//		mWebView.getSettings().setUseWideViewPort(true);
//		mWebView.getSettings().setLoadWithOverviewMode(true);

		mMinuBtn = (Button) findViewById(R.id.minus_btn);
		mMinuBtn.setOnClickListener(this);
		mQuantityEdit = (EditText) findViewById(R.id.quantity_edit);
		mAddBtn = (Button) findViewById(R.id.add_btn);
		mAddBtn.setOnClickListener(this);
	}

	/*
	 * ��ѵͼƬ��ȡʧ�ܵ�ʱ��
	 */
	private void scroollImageFail() {
//		mScrollImageBitmapList.clear();
//		mScrollImageBitmapList.add(BitmapFactory.decodeResource(getResources(),
//				R.drawable.goods_default));
//		mScrollImage.setHeight(width);
//		mScrollImage.setBitmapList(mScrollImageBitmapList);
//		mScrollImage.start(3000);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int quantity = Integer.parseInt(mQuantityEdit.getText().toString()
				.trim());
		Intent it = null;
		switch (v.getId()) {
		case R.id.back_img:
			finish();
			AnimUtil.setFromRightToLeft(this);
			break;
		case R.id.comments_view:
			it = new Intent(this, GoodsCommentsActivity.class);
			it.putExtra(Extra.GOODS_ID, goods_id);
			break;
		case R.id.call_view:
			showCallDialog();
			break;
		case R.id.minus_btn:
			// ��������
			if (quantity == 1) {
				showToast(R.string.goods_atlest_num);
				return;
			}
			quantity--;
			mQuantityEdit.setText(quantity + "");
			break;

		case R.id.add_btn:
			// ��������
			quantity++;
			mQuantityEdit.setText(quantity + "");
			break;
		case R.id.add_shop_btn:
			// ���빺�ﳵ,���ж��Ƿ��л���û�л��Ͳ��ܼ�����
			if (MyApplication.getInstance().isLogin()) {
				if (goodsDetail != null) {
					if (goodsDetail.getStock() - goodsDetail.getVirtual_sales() > 0) {
						// �л���
						shopcartaddAdd(MyApplication.getInstance().getUser()
								.getUcode(), goodsDetail.getGoods_id(),
								goodsDetail.getSpec_id(),
								goodsDetail.getGoods_sn(),
								goodsDetail.getShop_id(),
								goodsDetail.getGoods_name(),
								goodsDetail.getGoods_image(), quantity + "");
					} else {
						showToast(R.string.godds_empty);
					}
				}
			} else {
				it = new Intent(this, LoginActivity.class);
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			}
			break;
		case R.id.shopcar_image:
			if (MyApplication.getInstance().isLogin()) {
				MyApplication.getInstance().setShopCartNeedUpdate(true);
				MyApplication.getInstance().setShopCartNeedback(true);
				it = new Intent(this, ShoppingCartActivity.class);
			} else {
				it = new Intent(this, LoginActivity.class);
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			}

			break;
		case R.id.collect_image:
			// �ղز�Ʒ
			if (MyApplication.getInstance().isLogin() && mUser != null) {
				if (!TextUtils.isEmpty(goods_id)) {
					if (isCollect) {
						collectgoodsCancel(mUser.getUcode(), goods_id);
					} else {
						collectgoodsCollect(mUser.getUcode(), goods_id);
					}
				}

			} else {
				it = new Intent(this, LoginActivity.class);
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			}
			break;
		default:
			break;
		}
		if (null != it) {
			startActivity(it);
			AnimUtil.setFromLeftToRight(this);
		}
	}

	/*
	 * ����绰����ȷ����ʾ��
	 */
	private void showCallDialog() {
		new AlertDialog(GoodsDetailActivity.this)
				.builder()
				.setMsg(Constants.CALL_NUMBER)
				.setNegativeButton(getString(R.string.cancle),
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// ȡ��
							}
						})
				.setPositiveButton(getString(R.string.call_now),
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								CommUtils.call(GoodsDetailActivity.this);
							}
						}).show();
	}

	/**
	 * ��Ʒ����
	 * 
	 * @param goods_id
	 */
	private void goodsinfoInfo(String goods_id) {
		showProgressDialog(R.string.loading);
		API.goodsinfoInfo(goods_id, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					goodsDetail = JSON.parseObject(data, GoodsDetail.class);
					if (goodsDetail != null) {
						mNameTxt.setText(goodsDetail.getGoods_name());
						mPriceTxt.setText(CommUtils.getMoney(goodsDetail
								.getGoods_price()));
						mBeforePriceTxt.setText(CommUtils.getMoney(goodsDetail
								.getOld_price()));
						mBeforePriceTxt.getPaint().setFlags(
								Paint.STRIKE_THRU_TEXT_FLAG);
						
						//==>2015.12.9 �޸ļ۸�չʾ
						if(goodsDetail.getGoods_price().equals(goodsDetail.getOld_price())){
							mBeforePriceTxt.setVisibility(View.INVISIBLE);
						}else{
							mBeforePriceTxt.setVisibility(View.VISIBLE);
							mBeforePriceTxt.setText(CommUtils.getMoney(goodsDetail.getOld_price()));
						}
						//<==2015.12.9 

						List<String> tagList = JSON.parseArray(
								goodsDetail.getExpend1(), String.class);
						if (tagList != null) {
							int size = tagList.size();
							switch (size) {
							case 0:
								mTagTxt1.setVisibility(View.GONE);
								mTagTxt2.setVisibility(View.GONE);
								mTagTxt3.setVisibility(View.GONE);
								break;
							case 1:
								mTagTxt1.setVisibility(View.VISIBLE);
								mTagTxt2.setVisibility(View.GONE);
								mTagTxt3.setVisibility(View.GONE);
								mTagTxt1.setText(tagList.get(0));
								break;
							case 2:
								mTagTxt1.setVisibility(View.VISIBLE);
								mTagTxt2.setVisibility(View.VISIBLE);
								mTagTxt3.setVisibility(View.GONE);
								mTagTxt1.setText(tagList.get(0));
								mTagTxt2.setText(tagList.get(1));
								break;
							case 3:
								mTagTxt1.setVisibility(View.VISIBLE);
								mTagTxt2.setVisibility(View.VISIBLE);
								mTagTxt3.setVisibility(View.VISIBLE);

								mTagTxt1.setText(tagList.get(0));
								mTagTxt2.setText(tagList.get(1));
								mTagTxt3.setText(tagList.get(2));
								break;
							default:
								break;
							}

						}
						List<String> imageList = new ArrayList<String>();
						for (int i = 0; i < goodsDetail.getImages().size(); i++) {
							imageList.add(Constants.SERVER
									+ goodsDetail.getImages().get(i)
											.getImage_url());
						}
						mScrollImage.setHeight(width);
						mScrollImage.setImageList(imageList,
								GoodsDetailActivity.this);
						mScrollImage.start(Constants.BANNER_TIME);

						if (goodsDetail.getStock()
								- goodsDetail.getVirtual_sales() > 0) {
							mGoodsStateTxt.setText(R.string.goods_not_empty);
							mGoodsStateTxt.setTextColor(getResources()
									.getColor(R.color.green));
							mGoodsStateTxt
									.setBackgroundDrawable(getResources()
											.getDrawable(
													R.drawable.goods_not_empty_bg));
						} else {
							mGoodsStateTxt.setText(R.string.goods_empty);
							mGoodsStateTxt.setTextColor(getResources()
									.getColor(R.color.red));
							mGoodsStateTxt.setBackgroundDrawable(getResources()
									.getDrawable(R.drawable.goods_empty_bg));
						}
						String des = goodsDetail.getDescription();
						if (!des.startsWith("http://")) {
							des = "http://" + des;
						}
						if (!TextUtils.isEmpty(des)) {
							MyApplication.getInstance().setGoodsUrl(des);
//							mWebView.loadUrl(des);
//							mWebView.setWebViewClient(new MyWebViewClient());
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

	/**
	 * ��ȡ��������
	 * 
	 * @param goods_id
	 */
	private void goodsevaluationContentsNumber(String goods_id) {
		API.goodsevaluationContentsNumber(goods_id, 0, 0,
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
									mPinlunNum.setText("��Ʒ����(" + totalcount
											+ ")");
								}
							}
							break;

						default:
							break;
						}
					}
				});
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @param goods_id
	 */
	private void goodsevaluationLists(String goods_id) {
		API.goodsevaluationLists(goods_id, 1, 0, Constants.PAGE,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						if (state == STATE_OK) {
							if (!TextUtils.isEmpty(data)) {
								List<GoodsComments> goodList = JSON.parseArray(
										data, GoodsComments.class);
								if (goodList != null && !goodList.isEmpty()) {
									GoodsComments goodsComments = goodList
											.get(0);
									if (goodsComments != null) {
										mUserNameTxt.setText(goodsComments
												.getUserinfo().getNack_name());
										mDateTxt.setText(DateTime
												.getDateAndSecondbyStemp(goodsComments
														.getCreate_time()));
										mContentTxt.setText(goodsComments
												.getContent());
									}
								}

							}
						}
					}
				});
	}

	// Web��ͼ
	private class MyWebViewClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	/**
	 * �ղ�
	 * 
	 * @param ucode
	 * @param goods_id
	 */
	private void collectgoodsCollect(String ucode, String goods_id) {
		showProgressDialog(R.string.loading);
		API.collectgoodsCollect(this, ucode, goods_id, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					isCollect = true;
					mCollectImage
							.setImageResource(R.drawable.shoucang_icon_press);
					showToast(R.string.collect_good_sucess);
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
	 * ȡ���ղ�
	 * 
	 * @param ucode
	 * @param goods_id
	 */
	private void collectgoodsCancel(String ucode, String goods_id) {
		showProgressDialog(R.string.loading);
		API.collectgoodsCancel(this, ucode, goods_id, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					isCollect = false;
					mCollectImage
							.setImageResource(R.drawable.shoucang_icon_normal);
					showToast(R.string.cancle_good_sucess);
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
	 * ��ӵ����ﳵ
	 * 
	 * @param ucode
	 * @param goods_id
	 * @param spec_id
	 * @param specification
	 * @param shop_id
	 * @param goods_name
	 * @param goods_image
	 * @param quantity
	 */
	private void shopcartaddAdd(String ucode, String goods_id, String spec_id,
			String specification, String shop_id, String goods_name,
			String goods_image, String quantity) {
		showProgressDialog(R.string.loading);
		API.shopcartaddAdd(this, ucode, goods_id, spec_id, specification,
				shop_id, goods_name, goods_image, quantity,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						dismissDialog();
						switch (state) {
						case STATE_OK:
							showToast(R.string.addto_shopcar_sucess);
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
	 * ����Ʒ�Ƿ��ղ�
	 * 
	 * @param ucode
	 * @param goods_id
	 */
	private void collectgoodsIsCollect(String ucode, String goods_id) {
		API.collectgoodsIsCollect(this, ucode, goods_id, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				switch (state) {
				case STATE_OK:
					JSONObject jsonObject = JSON.parseObject(data);
					if (jsonObject != null) {
						String is_collect = jsonObject.getString("is_collect");
						if (!TextUtils.isEmpty(is_collect)) {
							isCollect = true;
							mCollectImage
									.setImageResource(R.drawable.shoucang_icon_press);
						}
					}

					break;

				default:
					break;
				}
			}
		});
	}
}
