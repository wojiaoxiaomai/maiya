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
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�ChangePasswordActivity
 * @�������� �޸�����
 * @�����ˣ��
 * @����ʱ�䣺2015-8-8 ����8:50:25
 * @�汾�ţ�v1.0
 * 
 */
public class ChangePasswordActivity extends BaseActivity implements
		View.OnFocusChangeListener {
	private EditText mMobileEdit;
	private ImageButton mMobileDelBtn;
	private ImageView mMobileLineImage;
	private EditText mCodeEdit;
	private ImageButton mCodeDelBtn;
	private ImageView mCodeLineImage;
	private TextView mGetCodeTxt;
	private Button mNextBtn;

	private MyCountDownTimer mCountDownTimer;// ����ʱ

	private String mCode;// �ӿڷ��ص�code���뱾���û�����ĶԱ�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password_activity);
		MyApplication.getInstance().addActivity(this);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.mimaxiugai, true);
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

		// �����ֻ��ż����仯
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
			// ��ȡ��֤��
			if (TextUtils.isEmpty(mobile)) {
				showToast(R.string.mobile_empty);
				return;
			}
			if (mobile.length() != 11) {
				showToast(R.string.mobile_length_wrong);
				return;
			}
			usereditPasswordCode(mobile);
			break;
		case R.id.next_btn:
			// ��֤��֤���Ƿ���ȷ
			if (!TextUtils.isEmpty(mCode)) {
				if (mCode.equals(code)) {
					Intent it = new Intent(this,
							ChangePasswordNextActivity.class);
					startActivity(it);
					AnimUtil.setFromLeftToRight(this);
				} else {
					showToast(R.string.code_wrong);
				}
			} else {
				showToast(R.string.code_again);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * ����-����-��ȡ��֤��
	 * 
	 * @param Phone
	 */
	private void usereditPasswordCode(final String phone) {
		showProgressDialog(R.string.loading_getcode);
		API.usereditPasswordCode(phone, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case ResponseHandler.STATE_OK:
					JSONObject jsonObject = JSON.parseObject(data);
					if (jsonObject != null) {
						String code = jsonObject.getString("code");
						if (!TextUtils.isEmpty(code)) {
							mCode = code;
						}
					}
					// ��֤���ȡ�ɹ��󵹼�ʱ
					mCountDownTimer = new MyCountDownTimer(
							Constants.CODE_TIME_OUT, 1000);
					mCountDownTimer.start();
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
	 *            ��ʾ�Ժ���Ϊ��λ ����ʱ������
	 * 
	 *            ���� millisInFuture=1000 ��ʾ1��
	 * 
	 * @param countDownInterval
	 *            ��ʾ ��� ����΢�� ����һ�� onTick ����
	 * 
	 *            ����: countDownInterval =1000 ; ��ʾÿ1000�������һ��onTick()
	 * 
	 */

	private class MyCountDownTimer extends CountDownTimer {

		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			mGetCodeTxt.setEnabled(true);
			mGetCodeTxt.setText("���»�ȡ��֤��");
			mGetCodeTxt.setTextColor(getResources().getColor(R.color.green));
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mGetCodeTxt.setText("���»�ȡ��֤��(" + millisUntilFinished / 1000 + ")");
			mGetCodeTxt.setTextColor(getResources().getColor(
					R.color.textview_hint_grey));
			mGetCodeTxt.setEnabled(false);
		}
	}
}
