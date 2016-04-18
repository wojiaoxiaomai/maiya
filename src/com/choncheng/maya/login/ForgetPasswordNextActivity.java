package com.choncheng.maya.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.utils.AnimUtil;

/**
 * 
 * @项目名称：Maya
 * @类名称：RegisterNextActivity
 * @类描述：忘记密码（找回密码）下一步
 * @创建人：李波
 * @创建时间：2015-8-13 上午11:00:19
 * @版本号：v1.0
 * 
 */
public class ForgetPasswordNextActivity extends BaseActivity implements
		View.OnFocusChangeListener {

	private EditText mPwdEdit;
	private ImageButton mPwdDelBtn;
	private ImageView mPwdLineImage;

	private EditText mPwdAgainEdit;
	private ImageButton mPwdAgainDelBtn;
	private ImageView mPwdAgainLineImage;
	private Button mNextBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forget_password_next_activity);
		MyApplication.getInstance().addActivity(this);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.forget_password, true);
		mPwdEdit = (EditText) findViewById(R.id.pwd_edit);
		mPwdDelBtn = (ImageButton) findViewById(R.id.pwd_delete);
		mPwdDelBtn.setOnClickListener(this);
		mPwdEdit.setOnFocusChangeListener(this);
		mPwdLineImage = (ImageView) findViewById(R.id.pwd_line_image);
		mPwdAgainEdit = (EditText) findViewById(R.id.pwd_again_edit);
		mPwdAgainDelBtn = (ImageButton) findViewById(R.id.pwd_again_delete);
		mPwdAgainLineImage = (ImageView) findViewById(R.id.pwd_again_line_image);
		mPwdAgainDelBtn.setOnClickListener(this);
		mPwdAgainEdit.setOnFocusChangeListener(this);
		mNextBtn = (Button) findViewById(R.id.next_btn);
		mNextBtn.setOnClickListener(this);

		mPwdEdit.setFilters(new InputFilter[] { filter });
		mPwdAgainEdit.setFilters(new InputFilter[] { filter });
		// 输入手机号监听变化
		mPwdEdit.addTextChangedListener(new TextWatcher() {

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
				String pwd = mPwdAgainEdit.getText().toString().trim();
				String pwdAgain = mPwdEdit.getText().toString().trim();

				if (s.length() > 0) {
					mPwdDelBtn.setVisibility(View.VISIBLE);
				} else {
					mPwdDelBtn.setVisibility(View.GONE);
				}
				if (pwd.length() >= 6 && pwdAgain.length() >= 6) {
					mNextBtn.setEnabled(true);
				} else {
					mNextBtn.setEnabled(false);
				}

			}
		});

		mPwdAgainEdit.addTextChangedListener(new TextWatcher() {

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
				String pwd = mPwdAgainEdit.getText().toString().trim();
				String pwdAgain = mPwdEdit.getText().toString().trim();
				if (s.length() > 0) {
					mPwdAgainDelBtn.setVisibility(View.VISIBLE);
				} else {
					mPwdAgainDelBtn.setVisibility(View.GONE);
				}
				if (pwd.length() >= 6 && pwdAgain.length() >= 6) {
					mNextBtn.setEnabled(true);
				} else {
					mNextBtn.setEnabled(false);
				}

			}
		});
	};

	@Override
	public void onClick(View v) {
		String pwd = mPwdEdit.getText().toString().trim();
		String pwdAgain = mPwdAgainEdit.getText().toString().trim();
		switch (v.getId()) {
		case R.id.pwd_delete:
			mPwdEdit.setText("");
			break;
		case R.id.pwd_again_delete:
			mPwdAgainEdit.setText("");
			break;
		case R.id.next_btn:
			if (!pwd.equals(pwdAgain)) {
				showToast(R.string.pwd_not_same);
				return;
			}
			usereditFindPassword(getIntent().getStringExtra(Extra.PHONE),
					getIntent().getStringExtra(Extra.CODE), pwd);
			break;
		default:
			break;
		}
	}

	/**
	 * 重置密码
	 * 
	 * @param phone
	 * @param code
	 * @param password
	 */
	private void usereditFindPassword(String phone, String code, String password) {
		showProgressDialog(R.string.loading);
		API.usereditFindPassword(phone, code, password, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					// 重置密码成功
					showToast(R.string.pwd_modify_sucess);
					Intent it = new Intent(ForgetPasswordNextActivity.this,
							LoginActivity.class);
					it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(it);
					MyApplication.getInstance().finishActivitys();
					AnimUtil.setFromLeftToRight(ForgetPasswordNextActivity.this);
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
				mPwdLineImage.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.denglu_bt_press));
			} else {
				mPwdLineImage.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.denglu_bt_normal));
			}
			break;
		case R.id.code_edit:
			if (hasFocus) {
				mPwdAgainLineImage.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.denglu_bt_press));
			} else {
				mPwdAgainLineImage.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.denglu_bt_normal));
			}
			break;
		default:
			break;
		}
	}

	/*
	 * EditText输入限制
	 */
	private InputFilter filter = new InputFilter() {
		@Override
		public CharSequence filter(CharSequence source, int start, int end,
				Spanned dest, int dstart, int dend) {
			if (source.equals(" ") || source.toString().contentEquals("\n"))
				return "";
			else
				return null;
		}
	};
}
