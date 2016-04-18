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
	 * �Ƿ��Ѿ���¼��
	 */
	private boolean isLogin = false;
	/**
	 * �û���Ϣ
	 */
	private User user = null;

	private List<ShoppingCart> shoppingCartList = null;// ���ﳵ�б�

	private boolean shopCartNeedback = true;// ���ﳵ�Ƿ���Ҫ���ؼ� ֻ����ҳtab��Ҫ�������Ķ���Ҫ
	private boolean shopCartNeedUpdate = true;// ���ﳵ�Ƿ�ˢ��
	private boolean showPersonalCenter = false;// ��ת�������棬�Ƿ���ʾ�������ģ�Ĭ�ϲ���

	private long loseTime = 0;// ������Ķ���ʧЧʱ��(��λ����)

	private List<PersonalOrderDetail> personalOrderCommentsList = null;// �������е���Ʒlist

	private long beginTime = 0;// Ӫҵ��ʼʱ��
	private long endTime = 0;// Ӫҵ����ʱ��

	private int shopCartNumber = 0;// ���ﳵ������
	private SharedPreferences sharedPreferences;
	private String goodsUrl;// ��Ʒ����url

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
	 * ͼƬ������
	 * 
	 * @param context
	 */
	private void initImageLoader(Context context) {
		//������ѿ��ͼƬ����
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
                      .diskCache(new UnlimitedDiskCache(new File(path + "/cache")))  //��໺��ͼƬ���������������ɾ�������ͼƬ
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

	// �Ƿ��Ѿ���¼��
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
	 * ���ativity
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
	 * �ر�activity
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
	 * �˳�
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
