package com.choncheng.maya.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.utils.AnimUtil;

/**
 * 
 * @项目名称：Maya
 * @类名称：RegisterActivity
 * @类描述： 会员注册
 * @创建人：李波
 * @创建时间：2015-8-8 上午8:50:25
 * @版本号：v1.0
 * 
 */
/**
 * 
 * @项目名称：Maya
 * @类名称：RegisterActivity
 * @类描述：
 * @创建人：李波
 * @创建时间：2015-8-12 下午10:24:33
 * @版本号：v1.0
 * 
 */

public class RegisterActivity extends BaseActivity implements
		View.OnFocusChangeListener {
	private EditText mMobileEdit;
	private ImageButton mMobileDelBtn;
	private ImageView mMobileLineImage;
	private EditText mCodeEdit;
	private ImageButton mCodeDelBtn;
	private ImageView mCodeLineImage;
	private TextView mGetCodeTxt;
	private Button mNextBtn;

	private MyCountDownTimer mCountDownTimer;// 倒计时

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		MyApplication.getInstance().addActivity(this);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.member_register, true);
		mMobileEdit = (EditText) findViewById(R.id.mobile_edit);
		mMobileDelBtn = (ImageButton) findViewById(R.id.mobile_delete);
		mMobileDelBtn.setOnClickListener(this);
		mMobileEdit.setOnFocusChangeListener(this);
		mMobileLineImage = (ImageView) findViewById(R.id.mobile_line_image);
		mCodeEdit = (EditText) findViewById(R.id.code_edit);
		mCodeDelBtn = (ImageButton) findViewById(R.id.code_delete);
		mCodeLineImage = (ImageView) findViewById(R.id.code_line_image);
		mCodeDelBtn.setOnClickListener(this);
		mCodeEdit.setOnFocusChangeListener(this);
		mGetCodeTxt = (TextView) findViewById(R.id.getcode_txt);
		mGetCodeTxt.setOnClickListener(this);
		mNextBtn = (Button) findViewById(R.id.next_btn);
		mNextBtn.setOnClickListener(this);

		// 输入手机号监听变化
		mMobileEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String code = mCodeEdit.getText().toString().trim();
				String mobile = mMobileEdit.getText().toString().trim();

				if (s.length() > 0) {
					mMobileDelBtn.setVisibility(View.VISIBLE);
				} else {
					mMobileDelBtn.setVisibility(View.GONE);
				}
				if (code.length() > 5 && mobile.length() == 11) {
					mNextBtn.setEnabled(true);
				} else {
					mNextBtn.setEnabled(false);
				}

			}
		});

		mCodeEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String code = mCodeEdit.getText().toString().trim();
				String mobile = mMobileEdit.getText().toString().trim();

				if (code.length() > 0) {
					mCodeDelBtn.setVisibility(View.VISIBLE);
				} else {
					mCodeDelBtn.setVisibility(View.GONE);
				}
				if (code.length() > 5 && mobile.length() == 11) {
					mNextBtn.setEnabled(true);
				} else {
					mNextBtn.setEnabled(false);
				}
			}
		});
	};

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (!checkNetwork()) {
			return;
		}
		String code = mCodeEdit.getText().toString().trim();
		String mobile = mMobileEdit.getText().toString().trim();
		switch (v.getId()) {
		case R.id.mobile_delete:
			mMobileEdit.setText("");
			break;
		case R.id.code_delete:
			mCodeEdit.setText("");
			break;
		case R.id.getcode_txt:
			// 获取验证码
			if (TextUtils.isEmpty(mobile)) {
				showToast(R.string.mobile_empty);
				return;
			}
			if (mobile.length() != 11) {
				showToast(R.string.mobile_length_wrong);
				return;
			}
			checkPhone(mobile);
			break;
		case R.id.next_btn:
			registCheckCode(mobile, code);
			break;
		default:
			break;
		}
	}

	/**
	 * 验证手机号是否已经注册
	 * 
	 * @param Phone
	 */
	private void checkPhone(final String phone) {
		showProgressDialog(R.string.loading_getcode);
		API.registCheckPhone(phone, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case ResponseHandler.STATE_OK:
					JSONObject jsonObject = JSON.parseObject(data);
					if (jsonObject != null) {
						int status = jsonObject.getIntValue("status");
						// 0: 未激活 1有效 2：无效 3:删除 -1: 未注册
						switch (status) {
						case 0:
							showToast(R.string.weijihuo);
							break;
						case 1:
							showToast(R.string.register_done);
							break;
						case 2:
							showToast(R.string.register_wuxiao);
							break;
						case 3:
							showToast(R.string.register_mobile_del);
							break;
						case -1:
							registCode(phone);
							break;
						default:
							break;
						}
					}
					break;
				case ResponseHandler.STATE_FAIL:
					showToast(data);
					break;
				case ResponseHandler.STATE_ERROR:
					showToast(R.string.http_respone_error);
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * 获取验证码
	 * 
	 * @param phone
	 */
	private void registCode(String phone) {
		showProgressDialog(R.string.loading_getcode);
		API.registCode(phone, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					// 验证码获取成功后倒计时
					showToast(R.string.code_has_send);
					mCountDownTimer = new MyCountDownTimer(
							Constants.CODE_TIME_OUT, 1000);
					mCountDownTimer.start();
					break;
				case STATE_FAIL:
					showToast(data);
					break;
				case STATE_ERROR:
					showToast(R.string.http_respone_error);
					break;
				default:
					break;
				}
			}
		});
	}

	/**
	 * 验证验证码
	 * 
	 * @param phone
	 * @param code
	 */
	private void registCheckCode(final String phone, String code) {
		showProgressDialog(R.string.loading_checkcode);
		API.registCheckCode(phone, code, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					Intent it = new Intent(RegisterActivity.this,
							RegisterNextActivity.class);
					it.putExtra(Extra.PHONE, phone);
					startActivity(it);
					AnimUtil.setFromLeftToRight(RegisterActivity.this);
					break;
				case STATE_FAIL:
					showToast(data);
					break;
				case STATE_ERROR:
					showToast(R.string.http_respone_error);
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.mobile_edit:
			if (hasFocus) {
				mMobileLineImage.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.denglu_bt_press));
			} else {
				mMobileLineImage.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.denglu_bt_normal));
			}
			break;
		case R.id.code_edit:
			if (hasFocus) {
				mCodeLineImage.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.denglu_bt_press));
			} else {
				mCodeLineImage.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.denglu_bt_normal));
			}
			break;
		default:
			break;
		}
	}

	// private void oncancel(View view) {
	// mCountDownTimer.cancel();
	// }
	//
	// private void restart(View view) {
	// mCountDownTimer.start();
	// }

	/**
	 * 
	 * @param millisInFuture
	 *            表示以毫秒为单位 倒计时的总数
	 * 
	 *            例如 millisInFuture=1000 表示1秒
	 * 
	 * @param countDownInterval
	 *            表示 间隔 多少微秒 调用一次 onTick 方法
	 * 
	 *            例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
	 * 
	 */

	private class MyCountDownTimer extends CountDownTimer {

		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			mGetCodeTxt.setEnabled(true);
			mGetCodeTxt.setText("重新获取验证码");
			mGetCodeTxt.setTextColor(getResources().getColor(R.color.green));
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mGetCodeTxt.setText("重新获取验证码(" + millisUntilFinished / 1000 + ")");
			mGetCodeTxt.setTextColor(getResources().getColor(
					R.color.textview_hint_grey));
			mGetCodeTxt.setEnabled(false);
		}
	}
}
