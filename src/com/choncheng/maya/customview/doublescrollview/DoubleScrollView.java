package com.choncheng.maya.customview.doublescrollview;

import com.choncheng.maya.MyApplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ScrollView;


/**   
*    
* @��Ŀ���ƣ�Maya   
* @�����ƣ�DoubleScrollView   
* @��������  ��Ʒ���黬��ҳ 
* @�����ˣ�� 
* @����ʱ�䣺2015-9-22 ����12:40:55   
* @�汾�ţ�v1.0   
*    
*/ 
public class DoubleScrollView extends ScrollView {
	private int mScreenHeight;
	// private int mOnePage;
	// private int mMenuPadding=220;

	private DoulbleInnerScroolView wrapperMenu;
	private DoubleInnerWebView wrapperContent;
	private boolean isSetted = false;
	private boolean ispageOne = true;

	public DoubleScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DoubleScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// �����Ļ�Ŀ�Ⱥͼ������õ�ƫ����������ֵ,�������menu�Ŀ��
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(metrics);
		mScreenHeight = metrics.heightPixels;// �õ���Ļ�Ŀ��(����)
	}

	public DoubleScrollView(Context context) {
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (!isSetted) {
			// �õ�����Ŀؼ�
			final LinearLayout wrapper = (LinearLayout) getChildAt(0);
			wrapperMenu = (DoulbleInnerScroolView) wrapper.getChildAt(0);
			wrapperContent = (DoubleInnerWebView) wrapper.getChildAt(1);
			WebSettings settings = wrapperContent.getSettings();
			settings.setJavaScriptEnabled(true);
			wrapperContent.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					wrapperContent.loadUrl(url);
					return true;
				}
			});
			// ����������View�ĸ߶�Ϊ�ֻ��ĸ߶�
			wrapperMenu.getLayoutParams().height = mScreenHeight;
			wrapperContent.getLayoutParams().height = mScreenHeight-100;
			isSetted = true;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			this.scrollTo(0, 0);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			// ��������ߵľ���
			int scrollY = getScrollY();
			int creteria = mScreenHeight / 5;// �������پ���
			if (ispageOne) {
				if (scrollY <= creteria) {
					// ��ʾ�˵�
					this.smoothScrollTo(0, 0);
				} else {
					// ���ز˵�
					this.smoothScrollTo(0, mScreenHeight);
					 wrapperContent.loadUrl(MyApplication.getInstance().getGoodsUrl());
					this.setFocusable(false);
					ispageOne = false;
				}
			} else {
				int scrollpadding = mScreenHeight - scrollY;
				if (scrollpadding >= creteria) {
					this.smoothScrollTo(0, 0);
					ispageOne = true;
				} else {
					this.smoothScrollTo(0, mScreenHeight);
					 wrapperContent.loadUrl(MyApplication.getInstance().getGoodsUrl());
				}
			}

			return true;
		}
		return super.onTouchEvent(ev);
	}

	public void closeMenu() {
		if (ispageOne)
			return;
		this.smoothScrollTo(0, 0);
		ispageOne = true;
	}

	public void openMenu() {
		if (!ispageOne)
			return;
		this.smoothScrollTo(0, mScreenHeight);
		ispageOne = false;
	}

	/**
	 * �򿪺͹رղ˵�
	 */
	public void toggleMenu() {
		if (ispageOne) {
			openMenu();
		} else {
			closeMenu();
		}
	}

	private float xDistance, yDistance, xLast, yLast;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			xLast = ev.getX();
			yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();

			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);
			xLast = curX;
			yLast = curY;

			if (xDistance > yDistance) {
				return false;
			}
		}

		return super.onInterceptTouchEvent(ev);
	}
}
