package com.choncheng.maya.utils;

import android.graphics.Bitmap;

import com.choncheng.maya.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�BitmapUtil
 * @�������� bitmap������
 * @�����ˣ��
 * @����ʱ�䣺2015-8-14 ����1:41:30
 * @�汾�ţ�v1.0
 * 
 */
public class BitmapUtil {

	/**
	 * ��ȡbanner��ͼƬ����
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
	 * Ĭ������
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
	 * Ĭ������
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
	 * ���ﳵ����
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
	 * Բ��ͷ��
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
	 * Բ��ͷ��(��Ϣ����)
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
