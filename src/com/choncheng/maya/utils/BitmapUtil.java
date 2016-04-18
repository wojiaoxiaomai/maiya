package com.choncheng.maya.utils;

import android.graphics.Bitmap;

import com.choncheng.maya.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 
 * @项目名称：Maya
 * @类名称：BitmapUtil
 * @类描述： bitmap工具类
 * @创建人：李波
 * @创建时间：2015-8-14 下午1:41:30
 * @版本号：v1.0
 * 
 */
public class BitmapUtil {

	/**
	 * 获取banner的图片配置
	 * 
	 * @return
	 */
	public static DisplayImageOptions getBannerOption() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_stub)
				.showImageOnFail(R.drawable.ic_stub)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
//				.considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		return options;
	}

	/**
	 * 默认配置
	 * 
	 * @return
	 */
	public static DisplayImageOptions getDefaultOption() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_stub)
				.showImageOnFail(R.drawable.ic_stub).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		return options;
	}
	/**
	 * 默认配置
	 * 
	 * @return
	 */
	public static DisplayImageOptions getDefaultOption2() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_stub)
				.showImageOnFail(R.drawable.ic_stub).cacheInMemory(false)
				.cacheOnDisk(true).considerExifParams(true)
				.resetViewBeforeLoading(false)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		return options;
	}
	
	/**
	 * 购物车配置
	 * 
	 * @return
	 */
	public static DisplayImageOptions getShopcartOption() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_stub)
				.showImageOnFail(R.drawable.ic_stub).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		return options;
	}

	/**
	 * 圆形头像
	 * 
	 * @return
	 */
	public static DisplayImageOptions getCircleOption() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_stub)
				.showImageOnFail(R.drawable.ic_stub).cacheInMemory(false)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new RoundedBitmapDisplayer(150))
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		return options;
	}

	/**
	 * 圆形头像(消息特用)
	 * 
	 * @return
	 */
	public static DisplayImageOptions getMessageCircleOption() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.logo_homepage)
				.showImageForEmptyUri(R.drawable.logo_homepage)
				.showImageOnFail(R.drawable.logo_homepage).cacheInMemory(false)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new RoundedBitmapDisplayer(150))
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		return options;
	}
}
