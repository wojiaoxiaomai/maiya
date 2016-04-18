package com.choncheng.maya.customview.doublescrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by ysnow on 2015/4/20.
 */
public class DoubleInnerWebView extends WebView {
	public float oldY;
	private int t;

	public DoubleInnerWebView(Context context) {
		super(context);
	}

	public DoubleInnerWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DoubleInnerWebView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:

			float Y = ev.getY();
			float Ys = Y - oldY;
			// Ys>0��ʾ�������»���->t==0��ʾһ���������˶���
			if (Ys > 0 && t == 0) {
				// Ȼ���ö����Ǹ�scrolLview���������¼�
				getParent().getParent().requestDisallowInterceptTouchEvent(
						false);
			}
			break;
		case MotionEvent.ACTION_DOWN:
			// ͬ���ǻ�û����¼�->��¼λ��
			getParent().getParent().requestDisallowInterceptTouchEvent(true);
			oldY = ev.getY();
			break;
		case MotionEvent.ACTION_UP:
			getParent().getParent().requestDisallowInterceptTouchEvent(true);

			break;
		}

		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		this.t = t;
		super.onScrollChanged(l, t, oldl, oldt);
	}

}
