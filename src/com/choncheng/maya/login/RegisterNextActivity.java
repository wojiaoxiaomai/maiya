package com.choncheng.maya.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MainActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.comm.entity.User;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.db.UserDBHelper;
import com.choncheng.maya.personal.XieyiActivity;
import com.choncheng.maya.utils.AnimUtil;

/**
 * 
 * @项目名称：Maya
 * @类名称：RegisterNextActivity
 * @类描述：注册下一步
 * @创建人：李波
 * @创建时间：2015-8-13 上午11:00:19
 * @版本号：v1.0
 * 
 */
public class RegisterNextActivity extends BaseActivity implements
		View.OnFocusChangeListener {

	private EditText mPwdEdit;
	private ImageButton mPwdDelBtn;
	private ImageView mPwdLineImage;

	private EditText mPwdAgainEdit;
	private ImageButton mPwdAgainDelBtn;
	private ImageView mPwdAgainLineImage;
	private Button mNextBtn;

	private CheckBox mCheckBox;
	private TextView mXieyiTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_next_activity);
		MyApplication.getInstance().addActivity(this);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.member_register, true);
		mCheckBox = (CheckBox) findViewById(R.id.check_box);
		mXieyiTxt = (TextView) findViewById(R.id.xieyi_txt);
		mXieyiTxt.setOnClickListener(this);
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
		// checkbox是否同意服务
		mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				String pwd = mPwdAgainEdit.getText().toString().trim();
				String pwdAgain = mPwdEdit.getText().toString().trim();
				if (pwd.length() >= 6 && pwdAgain.length() >= 6
						&& mCheckBox.isChecked()) {
					mNextBtn.setEnabled(true);
				} else {
					mNextBtn.setEnabled(false);
				}
			}
		});
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
				if (pwd.length() >= 6 && pwdAgain.length() >= 6
						&& mCheckBox.isChecked()) {
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
				if (pwd.length() >= 6 && pwdAgain.length() >= 6
						&& mCheckBox.isChecked()) {
					mNextBtn.setEnabled(true);
				} else {
					mNextBtn.setEnabled(false);
				}

			}
		});
	};

	@Override
	public void onClick(View v) {
		String pwd = mPwdEdit.getText().toString();
		String pwdAgain = mPwdAgainEdit.getText().toString();
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
			registRegist(getIntent().getStringExtra(Extra.PHONE), pwd);
			break;
		case R.id.xieyi_txt:
			Intent it = new Intent(this, XieyiActivity.class);
			startActivity(it);
			AnimUtil.setFromLeftToRight(this);
			break;
		default:
			break;
		}
	}

	private void registRegist(String phone, final String password) {
		showProgressDialog(R.string.loading_register);
		API.registRegist(phone, password, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					// 注册成功获取到用户信息
					User user = JSON.parseObject(data, User.class);
					user.setPassword(password);
					MyApplication.getInstance().setUser(user);
					MyApplication.getInstance().finishActivitys();

					// 保存用户数据，注册默认自动登录
					UserDBHelper dbHelper = new UserDBHelper(
							RegisterNextActivity.this);
					dbHelper.saveUser(user);
					Intent it = new Intent(RegisterNextActivity.this,
							MainActivity.class);
					it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					it.putExtra(Extra.PERSON_CENTER, true);
					MyApplication.getInstance().setShowPersonalCenter(true);
					startActivity(it);
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

		if (!hasFocus) {
			String pwd = mPwdEdit.getText().toString();
			String pwdAagin = mPwdAgainEdit.getText().toString();
			if (pwd.length() < 6 || pwdAagin.length() < 6) {
				showToast(R.string.pwd_length_tip);
			}
		}
		switch (v.getId()) {
		case R.id.pwd_edit:
			if (hasFocus) {
				mPwdLineImage.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.denglu_bt_press));
			} else {
				mPwdLineImage.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.denglu_bt_normal));
			}
			break;
		case R.id.pwd_again_edit:
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
