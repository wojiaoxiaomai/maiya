package com.choncheng.maya.customview.doublescrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ScrollView;

/**
 * Created by ysnow on 2015/4/20.
 */
public class DoulbleInnerScroolView extends ScrollView {
	private int mScreenHeight;
	public float oldY;
	private int t;

	public DoulbleInnerScroolView(Context context) {
		this(context, null);
	}

	public DoulbleInnerScroolView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DoulbleInnerScroolView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(metrics);
		mScreenHeight = metrics.heightPixels;// �õ���Ļ�Ŀ��(����)
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:
			// �ڻ�����ʱ���õ�ǰֵ��������õ�YS,�����ж������ϻ����������»���
			float Y = ev.getY();
			float Ys = Y - oldY;

			// �õ�scrollview����ռ�ĸ߶�
			int childHeight = this.getChildAt(0).getMeasuredHeight();
			// �ӿؼ��߶ȼ�ȥscrollview���ϻ����ľ���
			int padding = childHeight - t;
			// Ys<0��ʾ��ָ�������ϻ�����padding==mScreenHeight��ʾ��scrollview�Ѿ��������˵ײ�
			if (Ys < 0 && padding == mScreenHeight) {
				// �ö�����scrollview���»�û����¼�
				getParent().getParent().requestDisallowInterceptTouchEvent(
						false);
			}
			break;
		case MotionEvent.ACTION_DOWN:
			// ��ָ���µ�ʱ�򣬻�û����¼���Ҳ�����ö���scrollviewʧȥ�����¼�
			getParent().getParent().requestDisallowInterceptTouchEvent(true);
			// ���Ҽ�¼Y��ֵ
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
		// t��ʾ��scrollview���ϻ����ľ���
		this.t = t;
		super.onScrollChanged(l, t, oldl, oldt);
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
