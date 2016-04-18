package com.choncheng.maya;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.choncheng.maya.comm.entity.User;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.personal.PersonalOrderDetail;
import com.choncheng.maya.shoppingcart.ShoppingCart;
import com.choncheng.maya.utils.LocalSettings;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApplication extends Application {
	private static MyApplication mInstance;
	private List<Activity> mList = new ArrayList<Activity>();
	/**
	 * 是否已经登录过
	 */
	private boolean isLogin = false;
	/**
	 * 用户信息
	 */
	private User user = null;

	private List<ShoppingCart> shoppingCartList = null;// 购物车列表

	private boolean shopCartNeedback = true;// 购物车是否需要返回键 只有主页tab不要，其他的都需要
	private boolean shopCartNeedUpdate = true;// 购物车是否刷新
	private boolean showPersonalCenter = false;// 跳转到主界面，是否显示个人中心，默认不是

	private long loseTime = 0;// 待付款的订单失效时间(单位是秒)

	private List<PersonalOrderDetail> personalOrderCommentsList = null;// 待评价中的商品list

	private long beginTime = 0;// 营业开始时间
	private long endTime = 0;// 营业结束时间

	private int shopCartNumber = 0;// 购物车的数量
	private SharedPreferences sharedPreferences;
	private String goodsUrl;// 商品描述url

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		sharedPreferences = getSharedPreferences(Constants.SP_NAME,
				Context.MODE_PRIVATE);
		initImageLoader(mInstance);
	}

	public static MyApplication getInstance() {
		return mInstance;
	}

	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}

	/**
	 * 图片库配置
	 * 
	 * @param context
	 */
	private void initImageLoader(Context context) {
		//调整麦芽的图片缓存
		String path= Environment.getExternalStorageDirectory()+"/MaYa";
		 ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
	                context).threadPriority(Thread.NORM_PRIORITY - 2)
	                .denyCacheImageMultipleSizesInMemory()
	                .tasksProcessingOrder(QueueProcessingType.LIFO)
	                  .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
	                  .memoryCacheSize(10 * 1024 * 1024)
	                  .memoryCacheSizePercentage(13) // default
	                  .diskCacheSize(50 * 1024 * 1024)
	                  .diskCacheFileNameGenerator(new Md5FileNameGenerator())
	                  .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                      .diskCache(new UnlimitedDiskCache(new File(path + "/cache")))  //最多缓存图片张数，如果超出则删掉最早的图片
	                  .memoryCacheExtraOptions(480, 480) // default=device screen dimensions
	                  .diskCacheFileCount(100)
	                  .writeDebugLogs()
	                  .build();
		ImageLoader.getInstance().init(config);
	}

	public void setGoodsUrl(String goodsUrl) {
		this.goodsUrl = goodsUrl;
	}

	public String getGoodsUrl() {
		return goodsUrl;
	}

	// 是否已经登录了
	public boolean isLogin() {
		if (user == null) {
			isLogin = false;
		} else {
			isLogin = true;
		}
		return isLogin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setShoppingCartList(List<ShoppingCart> shoppingCartList) {
		this.shoppingCartList = shoppingCartList;
	}

	public List<ShoppingCart> getShoppingCartList() {
		return shoppingCartList;
	}

	public void setShowPersonalCenter(boolean show) {
		showPersonalCenter = show;
	}

	public boolean getShowPersonalCenter() {
		return showPersonalCenter;
	}

	public void setLostTime(long loseTime) {
		this.loseTime = loseTime;
	}

	public long getLostTime() {
		return loseTime;
	}

	// shopCartNumber

	public void setShopCartNumber(int shopCartNumber) {
		this.shopCartNumber = shopCartNumber;
	}

	public int getShopCartNumber() {
		return shopCartNumber;
	}

	public void setPersonalOrderCommentsList(
			List<PersonalOrderDetail> personalOrderCommentsList) {
		this.personalOrderCommentsList = personalOrderCommentsList;
	}

	public List<PersonalOrderDetail> getPersonalOrderCommentsList() {
		return personalOrderCommentsList;
	}

	/**
	 * 添加ativity
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	public void setShopCartNeedback(boolean needback) {
		shopCartNeedback = needback;
	}

	public boolean getShopCartNeedback() {
		return shopCartNeedback;
	}

	public void setShopCartNeedUpdate(boolean update) {
		this.shopCartNeedUpdate = update;
	}

	public boolean getShopCartNeedUpdate() {
		return shopCartNeedUpdate;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getEndTime() {
		return endTime;
	}

	/**
	 * 关闭activity
	 */
	public void finishActivitys() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 退出
	 */
	public void exit() {
		isLogin = false;
		user = null;
		shoppingCartList = null;
		personalOrderCommentsList = null;
		shopCartNumber = 0;
		beginTime = 0;
		endTime = 0;
		LocalSettings.putBoolean(LocalSettings.KEY_NEW_MSG, false);
		LocalSettings.putBoolean(LocalSettings.KEY_AUTO_LOGIN, false);
		// finishActivitys();
	}
}
