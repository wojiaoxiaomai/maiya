package com.choncheng.maya.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MsgService;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.comm.entity.User;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.db.UserDBHelper;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.LocalSettings;
import com.choncheng.maya.utils.PollingUtil;

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
public class LoginActivity extends BaseActivity implements
		OnFocusChangeListener {
	private TextView mForgetPwdTxt;
	private EditText mMobileEditText;
	private ImageButton mMobileDelBtn;
	private ImageView mMobileLineImage;

	private EditText mPwdEditText;
	private ImageButton mPwdDelBtn;
	private ImageView mPwdLineImage;
	private Button mLoginBtn;

	private CheckBox mAutoLoginCheckBox;

	private boolean isShowRegister = true;// 除了个人中心，所有其他地方在跳转登陆界面都要带着会员注册
	private TextView mRegisterTxt;// 注册

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		MyApplication.getInstance().addActivity(this);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.member_login, true);
		Intent it = getIntent();
		if (it != null) {
			isShowRegister = it.getBooleanExtra(Extra.IS_SHOW_REGISTER, true);
		}
		mRegisterTxt = (TextView) findViewById(R.id.register_txt);
		mRegisterTxt.setVisibility(isShowRegister ? View.VISIBLE : View.GONE);
		mRegisterTxt.setOnClickListener(this);
		mAutoLoginCheckBox = (CheckBox) findViewById(R.id.auto_checkbox);
		mForgetPwdTxt = (TextView) findViewById(R.id.forget_pwd_txt);
		mForgetPwdTxt.setOnClickListener(this);

		mMobileEditText = (EditText) findViewById(R.id.mobile_edit);
		mMobileDelBtn = (ImageButton) findViewById(R.id.mobile_delete);
		mMobileLineImage = (ImageView) findViewById(R.id.mobile_line_image);
		mMobileEditText.setOnFocusChangeListener(this);
		mPwdEditText = (EditText) findViewById(R.id.pwd_edit);
		mPwdDelBtn = (ImageButton) findViewById(R.id.pwd_delete);
		mPwdLineImage = (ImageView) findViewById(R.id.pwd_line_image);
		mPwdEditText.setOnFocusChangeListener(this);
		mLoginBtn = (Button) findViewById(R.id.login_btn);
		mMobileDelBtn.setOnClickListener(this);
		mPwdDelBtn.setOnClickListener(this);
		mLoginBtn.setOnClickListener(this);

		mPwdEditText.setFilters(new InputFilter[] { filter });
		mPwdEditText.addTextChangedListener(new TextWatcher() {

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
				if (s.length() > 0) {
					mPwdDelBtn.setVisibility(View.VISIBLE);
					if (s.length() > 5
							&& mMobileEditText.getText().toString().length() == 11) {
						mLoginBtn.setEnabled(true);
					} else {
						mLoginBtn.setEnabled(false);
					}
				} else {
					mPwdDelBtn.setVisibility(View.GONE);
					mLoginBtn.setEnabled(false);
				}
			}
		});
		mMobileEditText.addTextChangedListener(new TextWatcher() {

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
				if (s.length() > 0) {
					mMobileDelBtn.setVisibility(View.VISIBLE);
					if (s.length() == 11
							&& mPwdEditText.getText().toString().length() > 5) {
						mLoginBtn.setEnabled(true);
					}
				} else {
					mMobileDelBtn.setVisibility(View.GONE);
					mLoginBtn.setEnabled(false);
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
		String mobile = mMobileEditText.getText().toString();
		String pwd = mPwdEditText.getText().toString();
		Intent it = null;
		switch (v.getId()) {
		case R.id.forget_pwd_txt:
			it = new Intent(this, ForgetPasswordActivity.class);
			break;
		case R.id.mobile_delete:
			mMobileEditText.setText("");
			break;
		case R.id.pwd_delete:
			mPwdEditText.setText("");
			break;
		case R.id.login_btn:
			loginLogin(mobile, pwd);
			break;
		case R.id.register_txt:
			it = new Intent(this, RegisterActivity.class);
			it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			break;
		default:
			break;
		}
		if (null != it) {
			startActivity(it);
			AnimUtil.setFromLeftToRight(this);
		}
	}

	private void loginLogin(String phone, final String password) {
		showProgressDialog(R.string.loading_login);
		API.loginLogin(phone, password, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					User user = JSON.parseObject(data, User.class);
					user.setPassword(password);
					MyApplication.getInstance().setUser(user);
					if (mAutoLoginCheckBox.isChecked()) {
						// 如果选择了自动登录，就存在用户数据
						LocalSettings.putBoolean(LocalSettings.KEY_AUTO_LOGIN,
								true);
						UserDBHelper dbHelper = new UserDBHelper(
								LoginActivity.this);
						dbHelper.saveUser(user);
					} else {
						LocalSettings.putBoolean(LocalSettings.KEY_AUTO_LOGIN,
								false);
						UserDBHelper dbHelper = new UserDBHelper(
								LoginActivity.this);
						dbHelper.deleteUser();
					}
			        PollingUtil.startPollingService(LoginActivity.this, 5, MsgService.class, API.MSG_ACTION);

					finish();
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
		case R.id.pwd_edit:
			if (hasFocus) {
				mPwdLineImage.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.denglu_bt_press));
			} else {
				mPwdLineImage.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.denglu_bt_normal));
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
