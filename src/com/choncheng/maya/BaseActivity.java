package com.choncheng.maya;

import org.simple.eventbus.EventBus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.choncheng.maya.comm.entity.User;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.NetworkTool;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @项目名称：Maya
 * @类名称：BaseActivity
 * @类描述：基类
 * @创建人：李波
 * @创建时间：2015-8-7 下午8:06:58
 * @版本号：v1.0
 * 
 */
public class BaseActivity extends Activity implements OnClickListener {
	protected boolean DEBUG = false;// 是否调试模式
	public ProgressDialog mDialog = null;
	private static final String TAG = "BaseActivity";
	private static final int WHAT_DELAY = 1;
	private static final long DELAY = 6000;// 超时关闭dialog;
	protected User mUser = null;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (WHAT_DELAY == msg.what) {
				dismissDialog();
				
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		mUser = MyApplication.getInstance().getUser();
		EventBus.getDefault().register(this);
	}

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			AnimUtil.setFromRightToLeft(this);
			return false;
		}
		return false;

	};

	/**
	 * 显示加载进度条
	 * 
	 * @param stringId
	 */
	protected void showProgressDialog(int stringId) {
		if (mDialog == null) {
			mDialog = new ProgressDialog(this);
		}
		mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mDialog.setCancelable(true);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.show();
		mDialog.setContentView(R.layout.progress_dialog_layout);
		TextView msg = (TextView) mDialog.getWindow()
				.findViewById(R.id.message);
		msg.setText(stringId);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mHandler.sendEmptyMessage(WHAT_DELAY);
			}
		}).start();
	}

	protected void showProgressDialog(int stringId, boolean cancel) {
		if (mDialog == null) {
			mDialog = new ProgressDialog(this);
		}
		mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mDialog.setCancelable(cancel);
		mDialog.setCanceledOnTouchOutside(cancel);
		mDialog.show();
		mDialog.setContentView(R.layout.progress_dialog_layout);
		TextView msg = (TextView) mDialog.getWindow()
				.findViewById(R.id.message);
		msg.setText(stringId);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mHandler.sendEmptyMessage(WHAT_DELAY);
			}
		}).start();
	}

	protected void initView() {

	}

	protected void dismissDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}

	protected void showToast(int stringId) {
		Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
	}

	protected void showToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	protected boolean checkNetwork() {
		boolean isConectedNetwork = NetworkTool.checkNetworkState(this);
		if (!isConectedNetwork) {
			showToast(R.string.cannot_connet_network);
		}
		return isConectedNetwork;
	}

	@Override
	public void onClick(View v) {
		if (!checkNetwork()) {
			return;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mUser = MyApplication.getInstance().getUser();
	}

	@Override
	protected void onPause() {
		super.onPause();
		dismissDialog();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dismissDialog();
		EventBus.getDefault().unregister(this);

	}

}
