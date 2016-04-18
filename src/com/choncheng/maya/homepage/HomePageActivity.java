package com.choncheng.maya.homepage;

import java.util.ArrayList;
import java.util.List;

import org.simple.eventbus.Subscriber;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MainActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.banner.ScrollImage;
import com.choncheng.maya.comm.entity.Advertisement;
import com.choncheng.maya.comm.entity.Goods;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.customview.AlertDialog;
import com.choncheng.maya.customview.AllListView;
import com.choncheng.maya.db.MessageDBHelper;
import com.choncheng.maya.personal.PersonalCenterMsg;
import com.choncheng.maya.personal.PersonalCenterMsgActivity;
import com.choncheng.maya.personal.PersonalCenterSettingActivity;
import com.choncheng.maya.personal.PersonalCollectionActivity;
import com.choncheng.maya.personal.PersonalOrderActivity;
import com.choncheng.maya.search.SearchActivity;
import com.choncheng.maya.shoppingcart.GoodsActivity;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.AppInfoUtil;
import com.choncheng.maya.utils.CommUtils;
import com.choncheng.maya.utils.LocalSettings;

/**
 * 
 * @项目名称：Maya
 * @类名称：HomePageActivity
 * @类描述： 首页
 * @创建人：李波
 * @创建时间：2015-8-7 下午8:37:21
 * @版本号：v1.0
 * 
 */
public class HomePageActivity extends BaseActivity {
	private ScrollImage mScrollImage;
	private List<Advertisement> mScroolList;
	private AllListView mRecommendGoodsListView;// 中间推荐栏listview
	private RecommendGoodsAdapter mRecommendGoodsAdapter;
	private List<Advertisement> mRecommendGoodsList;

	private AllListView mDiscountAndHotGoodsListView;// 下面特惠和推荐等
	private DiscountAndHotGoodsAdapter mDiscountAndHotGoodsAdapter;
	private List<DiscountAndHotGoods> mDiscountAndHotGoodsList;

	private ImageView mMsgImageView;
	private ImageView mMsgTipImageView;// 红点提示
	private View mOrderView;
	private View mCollectionView;
	private View mCallView;
	private EditText mSearchEdit;
	private ScrollView scrollView;

	private static final int SCROOL_TO_HEAD = 1;
	private Handler mHander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == SCROOL_TO_HEAD) {
				scrollView.post(new Runnable() {
					public void run() {
						scrollView.scrollTo(0, 20);
					}
				});
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page_activity);
		initView();
		adlistsLists(API.ADLISTS_BANNER);
		adlistsLists(API.ADLISTS_RECOMMEND);
		adlistsLists(API.ADLISTS_BOTTOM);
		waitScroolToHead();
	}

	private void waitScroolToHead() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mHander.sendEmptyMessage(SCROOL_TO_HEAD);
			}
		}).start();
	}

	/**
	 * 获取广告
	 * 
	 * @param type_id
	 *            1:首页头部广告位 2： 中部广告位 3尾部广告位
	 */
	private void adlistsLists(final int type_id) {
		API.adlistsLists(type_id, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				switch (type_id) {
				case API.ADLISTS_BANNER:
					if (state == STATE_OK) {
						List<Advertisement> adList = JSON.parseArray(data,
								Advertisement.class);
						if (adList != null) {
							mScroolList.addAll(adList);
						}
						List<String> imageList = new ArrayList<String>();
						for (int i = 0; i < mScroolList.size(); i++) {
							imageList.add(CommUtils.getImageUrl(mScroolList
									.get(i).getAd_image()));
						}
						/*mScrollImage.setHeight(AppInfoUtil.dip2px(
								HomePageActivity.this, 180));*/
						mScrollImage.setImageList(imageList,
								HomePageActivity.this);
						mScrollImage.start(Constants.BANNER_TIME);
					}
					break;
				case API.ADLISTS_RECOMMEND:
					if (state == STATE_OK) {
						List<Advertisement> adList = JSON.parseArray(data,
								Advertisement.class);
						if (adList != null) {
							mRecommendGoodsList.addAll(adList);
						}
						mRecommendGoodsAdapter.notifyDataSetChanged();
					}
					break;
				case API.ADLISTS_BOTTOM:
					if (state == STATE_OK) {
						List<Advertisement> adList = JSON.parseArray(data,
								Advertisement.class);
						if (adList != null) {
							for (int i = 0; i < adList.size(); i++) {
								adgoodsLists(adList.get(i).getAd_id(),
										adList.get(i));
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

	/**
	 * 某个广告下的产品
	 * 
	 * @param ad_id
	 */
	private void adgoodsLists(String ad_id, final Advertisement ad) {
		API.adgoodsLists(ad_id,"-1", new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				if (state == STATE_OK) {
					List<Goods> goodsList = JSON.parseArray(data, Goods.class);
					if (goodsList != null) {
						DiscountAndHotGoods good = new DiscountAndHotGoods();
						if (!goodsList.isEmpty()) {
							good.setAdvertisement(ad);
							good.setGoodList(goodsList);
							mDiscountAndHotGoodsList.add(good);
							mDiscountAndHotGoodsAdapter.notifyDataSetChanged();
						}
					}

				}
			}
		});
	}

	@Override
	protected void initView() {
		super.initView();

		scrollView = (ScrollView) findViewById(R.id.scroolview);
		mSearchEdit = (EditText) findViewById(R.id.search_edit);
		mSearchEdit.setOnClickListener(this);
		mMsgImageView = (ImageView) findViewById(R.id.msg_image);
		mMsgTipImageView = (ImageView) findViewById(R.id.msg_tip_image);
		mOrderView = findViewById(R.id.dingdan_view);
		mCollectionView = findViewById(R.id.shouchang_view);
		mCallView = findViewById(R.id.call_view);
		mMsgImageView.setOnClickListener(this);
		mOrderView.setOnClickListener(this);
		mCollectionView.setOnClickListener(this);
		mCallView.setOnClickListener(this);
		mRecommendGoodsListView = (AllListView) findViewById(R.id.recommend_listview);
		mRecommendGoodsList = new ArrayList<Advertisement>();
		mRecommendGoodsAdapter = new RecommendGoodsAdapter(mRecommendGoodsList,
				this);
		mRecommendGoodsListView.setAdapter(mRecommendGoodsAdapter);
		mRecommendGoodsListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent it = new Intent(HomePageActivity.this,
								GoodsActivity.class);
						it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_CLEAR_TOP);
						it.putExtra(Extra.AD_ID,
								mRecommendGoodsList.get(position).getAd_id());
						startActivity(it);
						AnimUtil.setFromLeftToRight(HomePageActivity.this);
					}
				});

		mDiscountAndHotGoodsListView = (AllListView) findViewById(R.id.discount_hot_listview);
		mDiscountAndHotGoodsList = new ArrayList<DiscountAndHotGoods>();
		mDiscountAndHotGoodsAdapter = new DiscountAndHotGoodsAdapter(
				mDiscountAndHotGoodsList, this);
		mDiscountAndHotGoodsListView.setAdapter(mDiscountAndHotGoodsAdapter);

		mScroolList = new ArrayList<Advertisement>();
		mScrollImage = (ScrollImage) findViewById(R.id.simage);
		mScrollImage.setClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				 
				try {
					Intent it = new Intent(HomePageActivity.this,
							GoodsActivity.class);
					it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					it.putExtra(Extra.AD_ID,
							mScroolList.get(mScrollImage.getPosition()).getAd_id());
					startActivity(it);
					AnimUtil.setFromLeftToRight(HomePageActivity.this);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent it = null;
		switch (v.getId()) {
		case R.id.msg_image:
			if (MyApplication.getInstance().isLogin()) {
				it = new Intent(this, PersonalCenterMsgActivity.class);
				LocalSettings.putBoolean(LocalSettings.KEY_NEW_MSG, false);
				mMsgTipImageView.setVisibility(View.GONE);
			} else {
				CommUtils.lauchLoginActivity(this);
			}

			break;
		case R.id.dingdan_view:

			if (MyApplication.getInstance().isLogin()) {
				it = new Intent(this, PersonalOrderActivity.class);
			} else {
				CommUtils.lauchLoginActivity(this);
			}
			break;
		case R.id.shouchang_view:

			if (MyApplication.getInstance().isLogin()) {
				it = new Intent(this, PersonalCollectionActivity.class);
			} else {
				CommUtils.lauchLoginActivity(this);
			}
			break;

		case R.id.call_view:
			showCallDialog();
			break;
		case R.id.search_edit:
			it = new Intent(this, SearchActivity.class);
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
	 * 拨打电话二次确认提示框
	 */
	private void showCallDialog() {
		new AlertDialog(HomePageActivity.this)
				.builder()
				.setMsg(Constants.CALL_NUMBER)
				.setNegativeButton(getString(R.string.cancle),
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// 取消
							}
						})
				.setPositiveButton(getString(R.string.call_now),
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								CommUtils.call(HomePageActivity.this);
							}
						}).show();
	}

	/**
	 * 消息列表
	 * 
	 * @param ucode
	 *            用户校验码
	 * @param shop_id商家id
	 *            (非必传)
	 * @param response
	 */
	private void leavemsgMsg(String ucode, String shop_id) {
		API.leavemsgMsg(this, ucode, shop_id, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				if (state == STATE_OK) {
					// 获取到消息列表
					List<PersonalCenterMsg> msgList = JSON.parseArray(data,
							PersonalCenterMsg.class);

					if (msgList != null) {
						for (int i = 0; i < msgList.size(); i++) {
							PersonalCenterMsg msg = msgList.get(i);
							// 1：用户发个商家的 2：商家发给用户的
							msg.setU_to_s("2");
						}

						if (!msgList.isEmpty()) {
							mMsgTipImageView.setVisibility(View.VISIBLE);
							LocalSettings.putBoolean(LocalSettings.KEY_NEW_MSG,
									true);
							MessageDBHelper dbHelper = new MessageDBHelper(
									HomePageActivity.this);
							dbHelper.saveMessage(msgList);
						}
					}
				}

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean isNewMessage = LocalSettings.getBoolean(
				LocalSettings.KEY_NEW_MSG, false);
		// mMsgTipImageView.setVisibility(isNewMessage ? View.VISIBLE :
		// View.GONE);
		/*if (mUser != null) {
			leavemsgMsg(mUser.getUcode(), "");
		}*/
		scrollView.post(new Runnable() {
			public void run() {
				scrollView.scrollTo(0, 20);
			}
		});

	}

	@Subscriber(tag = API.REFRESH_MESSAGE)
	public void refresh(String user) {
		mMsgTipImageView.setVisibility(View.VISIBLE);
		//addNotification("", "你有一条新消息");
	}
	 
	 private void addNotification(String type,String contentText) {

		        Intent intent=new Intent(this, MainActivity.class);
		        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
		        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		        Notification.Builder builder = new Notification.Builder(this);
		        builder.setContentIntent(pendingIntent)
		                .setSmallIcon(R.drawable.app_ic)//设置状态栏里面的图标（小图标）
		                .setWhen(System.currentTimeMillis())//设置时间发生时间
		                .setAutoCancel(true)//设置可以清除
		                .setContentTitle(getString(R.string.app_name))//设置下拉列表里的标题
		                .setContentText(contentText);//设置上下文内容
		        Notification notification = builder.getNotification();
		       /* if(Setings.getInstance(MainApp.getInstance()).isMsg_seting_voice()&&Setings.getInstance(MainApp.getInstance()).isMsg_seting_shake()){
		            if(!inNotdisturbanceTime()){
		                notification.defaults = Notification.DEFAULT_ALL;//Notification.DEFAULT_ALL
		            }
		        }else if(Setings.getInstance(MainApp.getInstance()).isMsg_seting_voice()) {
		            if(!inNotdisturbanceTime()) {
		                notification.defaults = Notification.DEFAULT_SOUND;//Notification.DEFAULT_ALL

		            }
		        }else if(Setings.getInstance(MainApp.getInstance()).isMsg_seting_shake()){
		            if(!inNotdisturbanceTime()) {
		                notification.defaults = Notification.DEFAULT_VIBRATE;//Notification.DEFAULT_ALL
		            }
		        }else {
//		            notification.defaults = Notification.DEFAULT_ALL;//Notification.DEFAULT_ALL
		        }*/
		        //  notification.audioStreamType= android.media.AudioManager.ADJUST_LOWER; //响铃模式 必须的AudioManager中的值
		        nm.notify(2, notification);//显示通知 break;

		    }
}
