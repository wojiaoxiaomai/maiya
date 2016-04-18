package com.choncheng.maya.banner;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.choncheng.maya.R;
import com.choncheng.maya.utils.AppInfoUtil;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.CommUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ScrollImage extends RelativeLayout {

	private ImageScrollView imageScrollView = null;
	private PageControlView pageControlView = null;

	public ScrollImage(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.scroll_image, ScrollImage.this);

		imageScrollView = (ImageScrollView) this
				.findViewById(R.id.myImageScrollView);

		pageControlView = (PageControlView) this
				.findViewById(R.id.myPageControlView);
	}

	/**
	 * 装bitmap显示
	 * 
	 * @param list
	 */
	public void setBitmapList(List<Bitmap> list) {
		int num = list.size();
		imageScrollView.removeAllViews();
		ImageView imageView;
		for (int i = 0; i < num; i++) {
			imageView = new ImageView(getContext());
			// imageView.setImageBitmap(list.get(i));
			BitmapDrawable background = new BitmapDrawable(list.get(i));
			imageView.setBackgroundDrawable(background);
			imageScrollView.addView(imageView);
		}
		pageControlView.setCount(imageScrollView.getChildCount());
		pageControlView.generatePageControl(0);
		imageScrollView.setScrollToScreenCallback(pageControlView);
	}

	/**
	 * 装图片url显示
	 * 
	 * @param list
	 */
	public void setImageList(List<String> list, final Context context) {
		int num = list.size();
		imageScrollView.removeAllViews();
		ImageView imageView;
		for (int i = 0; i < num; i++) {
			imageView = new ImageView(context);
			imageView.setScaleType(ScaleType.FIT_XY);
			ImageLoader.getInstance().displayImage(CommUtils
					.getImageUrl(list.get(i)), imageView,
					BitmapUtil.getBannerOption());
			imageScrollView.addView(imageView);
			imageView.setTag(list.get(i));
			/*ImageLoader.getInstance().displayImage(CommUtils
					.getImageUrl(list.get(i)), imageView,
					BitmapUtil.getBannerOption(),
					new SimpleImageLoadingListener() {

						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							super.onLoadingComplete(imageUri, view, loadedImage);
							if(view.getTag().equals(imageUri)){
								ImageView imageView=(ImageView) view;
								imageView.setImageBitmap(loadedImage);
							}
						}

					}, null);*/
			
		}
		pageControlView.setCount(imageScrollView.getChildCount());
		pageControlView.generatePageControl(0);
		imageScrollView.setScrollToScreenCallback(pageControlView);
	}

	public void setHeight(int height) {
		android.view.ViewGroup.LayoutParams la = getLayoutParams();
		la.height = height;
		android.view.ViewGroup.LayoutParams lap = imageScrollView
				.getLayoutParams();
		lap.height = height;
	}

	public void setWidth(int width) {
		android.view.ViewGroup.LayoutParams la = getLayoutParams();
		la.width = width;
		android.view.ViewGroup.LayoutParams lap = imageScrollView
				.getLayoutParams();
		lap.width = width;
	}

	public void start(int time) {
		imageScrollView.start(time);
	}

	public void stop() {
		imageScrollView.stop();
	}

	public int getPosition() {
		return imageScrollView.getCurrentScreenIndex();
	}

	public void setClickListener(OnClickListener clickListener) {
		imageScrollView.setClickListener(clickListener);
	}
}
