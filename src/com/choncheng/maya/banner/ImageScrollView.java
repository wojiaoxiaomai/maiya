package com.choncheng.maya.banner;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class ImageScrollView extends ViewGroup {
	private Scroller scroller = null;
	private GestureDetector gestureDetector = null;
	private int currentScreenIndex = 0;
	private boolean fling = false;
	
	Handler handler;
	int time;
	OnClickListener clickListener;

	public ImageScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		for ( int i = 0; i < getChildCount(); i++ ){
			View child = getChildAt(i);
			child.setVisibility(View.VISIBLE);
			child.measure(right - left, bottom - top);
			child.layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());
		}
	}

	private void initView(final Context context) {
		this.scroller = new Scroller(context);
		handler = new Handler();

		this.gestureDetector = new GestureDetector(new OnGestureListener() {
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				
				if(clickListener!=null){
					clickListener.onClick(ImageScrollView.this);
				}
				
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				if((distanceX > 0 && getScrollX() < getWidth() * (getChildCount() - 1))
						|| (distanceX < 0 && getScrollX() > 0)){// ��ֹ���һҳ֮ǰ�ƶ�?
					scrollBy((int) distanceX, 0);
				}
				return true;
			}

			@Override
			public void onLongPress(MotionEvent e) {
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				if(Math.abs(velocityX) > ViewConfiguration.get(context)
						.getScaledMinimumFlingVelocity()){
					if(velocityX > 0 && currentScreenIndex >= 0){
						fling = true;
						scrollToScreen((currentScreenIndex - 1 + getChildCount()) % getChildCount());
					} else if(velocityX < 0 && currentScreenIndex <= getChildCount() - 1){
						fling = true;
						scrollToScreen((currentScreenIndex + 1 + getChildCount()) % getChildCount());
					}
				}
				return true;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				return false;
			}
		});
	}

	public void scrollToScreen(int whichScreen) {
		if(whichScreen != currentScreenIndex && getFocusedChild() != null
				&& getFocusedChild() == getChildAt(currentScreenIndex)){
			getFocusedChild().clearFocus(); // ����
		}

		final int delta = whichScreen * getWidth() - getScrollX();
		int show = 0;
		if(Math.abs(delta) < getWidth()*(getChildCount()-1) - getWidth()/2)
			show = Math.abs(delta) * 2;
		scroller.startScroll(getScrollX(), 0, delta, 0, show);
		invalidate();

		currentScreenIndex = whichScreen; // ���õ�ǰ��Ļ����
		if(scrollToScreenCallback != null){ // ˢ��ԲȦ
			scrollToScreenCallback.callback(currentScreenIndex);
		}
	}

	@Override
	public void computeScroll() {
		if(scroller.computeScrollOffset()){
			scrollTo(scroller.getCurrX(), 0);
			postInvalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);

		if(event.getAction() == MotionEvent.ACTION_UP){
			
			handler.removeCallbacks(next);
			if(time>=500)
				handler.postDelayed(next, time);
			
			if(!fling){
				snapToDestination();
			}
			fling = false;
		}
		return true;
	}

	private void snapToDestination() {
		scrollToScreen((getScrollX() + (getWidth() / 2)) / getWidth());
	}

	interface ScrollToScreenCallback {
		public void callback(int currentIndex);
	}

	private ScrollToScreenCallback scrollToScreenCallback;

	public void setScrollToScreenCallback(ScrollToScreenCallback scrollToScreenCallback) {
		this.scrollToScreenCallback = scrollToScreenCallback;
	}
	
	public void start(int time){
		if(time>500)
			this.time = time;
		else
			this.time = 500;
		handler.postDelayed(next, time);
	}
	
	public void stop(){
		handler.removeCallbacks(next);
	}
	
	Runnable next = new Runnable() {
		
		@Override
		public void run() {
			scrollToScreen((currentScreenIndex+1)%getChildCount());
			handler.postDelayed(next, time);
		}
	};

	public int getCurrentScreenIndex() {
		return currentScreenIndex;
	}

	public void setClickListener(OnClickListener clickListener) {
		this.clickListener = clickListener;
	}
}