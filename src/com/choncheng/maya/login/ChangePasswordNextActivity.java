package com.choncheng.maya.login;

import org.w3c.dom.UserDataHandler;

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
import com.choncheng.maya.MainActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.comm.entity.User;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.db.UserDBHelper;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.CommUtils;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�RegisterNextActivity
 * @���������޸�������һ��
 * @�����ˣ��
 * @����ʱ�䣺2015-8-13 ����11:00:19
 * @�汾�ţ�v1.0
 * 
 */
public class ChangePasswordNextActivity extends BaseActivity implements
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
		setContentView(R.layout.change_password_next_activity);
		MyApplication.getInstance().addActivity(this);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.mimaxiugai, true);
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
		// �����ֻ��ż����仯
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
			User user = MyApplication.getInstance().getUser();
			if (user != null) {
				usereditEditPassword(user.getUcode(), pwd);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * �һ�������������
	 * 
	 * @param phone
	 * @param code
	 * @param password
	 */
	private void usereditEditPassword(String ucode, final String password) {
		showProgressDialog(R.string.loading);
		API.usereditEditPassword(this, ucode, password, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					// �޸�����ɹ� ϵͳ���������������½

					mUser.setPassword(password);
					MyApplication.getInstance().setUser(mUser);
					Intent it = new Intent(ChangePasswordNextActivity.this,
							MainActivity.class);
					it.putExtra(Extra.IS_FROM_CHANGEPASSWORDNEXTACTIVITY, true);
					it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);

					MyApplication.getInstance().setShowPersonalCenter(true);
					// ɾ���������ݣ��´���Ҫ���µ�¼
					UserDBHelper dbHelper = new UserDBHelper(
							ChangePasswordNextActivity.this);
					dbHelper.deleteUser();
					startActivity(it);
					// showToast(R.string.pwd_modify_sucess);
					// MyApplication.getInstance().exit();
					// MyApplication.getInstance().setShowPersonalCenter(true);
					// CommUtils
					// .lauchLoginActivity(ChangePasswordNextActivity.this);
					// AnimUtil.setFromLeftToRight(ChangePasswordNextActivity.this);
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
	 * EditText��������
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
