package com.choncheng.maya.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.choncheng.maya.R;
import com.choncheng.maya.banner.ImageScrollView.ScrollToScreenCallback;

public class PageControlView extends LinearLayout implements
		ScrollToScreenCallback {
	private Context context;
	private int count;

	public PageControlView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	public void callback(int currentIndex) {
		generatePageControl(currentIndex);
	}

	public void generatePageControl(int currentIndex) {
		this.removeAllViews();

		for (int i = 0; i < this.count; i++) {
			ImageView iv = new ImageView(context);
			if (currentIndex == i) {
				iv.setImageResource(R.drawable.banner_pic_now);
			} else {
				iv.setImageResource(R.drawable.bannerd_pic_notnow);
			}
			iv.setLayoutParams(new LayoutParams(1, 0));
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.leftMargin = 8;
			layoutParams.rightMargin = 8;
			iv.setLayoutParams(layoutParams);
			this.addView(iv);
		}
	}

	public void setCount(int count) {
		this.count = count;
	}
}