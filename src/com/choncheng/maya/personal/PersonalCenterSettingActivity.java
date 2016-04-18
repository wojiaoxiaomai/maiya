package com.choncheng.maya.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MainActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.customview.AlertDialog;
import com.choncheng.maya.db.UserDBHelper;
import com.choncheng.maya.homepage.HomePageActivity;
import com.choncheng.maya.login.LoginActivity;
import com.choncheng.maya.login.RegisterNextActivity;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.AppInfoUtil;
import com.choncheng.maya.utils.CommUtils;
import com.choncheng.maya.utils.UpdateVersion;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�PersonalCenterSettingActivity
 * @�������� �����������ý���
 * @�����ˣ��
 * @����ʱ�䣺2015-8-7 ����10:31:01
 * @�汾�ţ�v1.0
 * 
 */
public class PersonalCenterSettingActivity extends BaseActivity {
	private View mVersionView;
	private View mAboutView;
	private View mXieyiView;
	private View mKefuView;
	private TextView mVersionNameTxt;
	private Button mExitBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_setting_activity);
		MyApplication.getInstance().addActivity(this);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.person_center_setting, true);
		mExitBtn = (Button) findViewById(R.id.exit_btn);
		mExitBtn.setOnClickListener(this);
		if (MyApplication.getInstance().isLogin()) {
			mExitBtn.setEnabled(true);
		} else {
			mExitBtn.setEnabled(false);
		}
		mVersionView = findViewById(R.id.version_view);
		mAboutView = findViewById(R.id.about_view);
		mXieyiView = findViewById(R.id.xieyi_view);
		mKefuView = findViewById(R.id.kefu_view);

		mVersionView.setOnClickListener(this);
		mAboutView.setOnClickListener(this);
		mXieyiView.setOnClickListener(this);
		mKefuView.setOnClickListener(this);
		mVersionNameTxt = (TextView) findViewById(R.id.version_name_txt);
		mVersionNameTxt.setText(AppInfoUtil.getVersion(this));
	};

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent it = null;
		switch (v.getId()) {
		case R.id.version_view:
			// �������
			appversionAppNew();
			break;
		case R.id.about_view:
			it = new Intent(this, AboutUsActivity.class);
			break;
		case R.id.xieyi_view:
			it = new Intent(this, XieyiActivity.class);
			break;
		case R.id.kefu_view:
			showCallDialog();
			break;
		case R.id.exit_btn:
			// �˳���ǰ��¼
			MyApplication.getInstance().exit();
			UserDBHelper dbHelper = new UserDBHelper(this);
			dbHelper.deleteUser();
			it = new Intent(this, MainActivity.class);
			it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// it.putExtra(Extra.PERSON_CENTER, true);
			MyApplication.getInstance().setShowPersonalCenter(true);
			startActivity(it);
			break;
		default:
			break;
		}
		if (null != it) {
			startActivity(it);
			AnimUtil.setFromLeftToRight(this);
		}
	}

	/**
	 * ��ȡ�°���Ϣ
	 */
	private void appversionAppNew() {
		showProgressDialog(R.string.loading_getversion);
		API.appversionAppNew(this, 1, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				if (state == STATE_OK) {
					JSONObject jsonObject = JSON.parseObject(data);
					if (jsonObject != null) {
						String number = jsonObject.getString("number");
						if (!TextUtils.isEmpty(number)) {
							if (number.equals(AppInfoUtil
									.getVersion(PersonalCenterSettingActivity.this))) {
								showToast(R.string.version_newest);
							} else {
								// ����и���
								showUpdateDialog();
							}
						}
					}
				} else {
					showToast(R.string.version_newest);
				}
			}
		});
	}

	/*
	 * �������������ʾ��
	 */
	private void showUpdateDialog() {
		new AlertDialog(PersonalCenterSettingActivity.this)
				.builder()
				.setTitle(getString(R.string.soft_update))
				.setMsg(getString(R.string.soft_update_content))
				.setNegativeButton(getString(R.string.cancle),
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// ȡ������
							}
						})
				.setPositiveButton(getString(R.string.down_now),
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// ���������°汾
								new UpdateVersion(
										PersonalCenterSettingActivity.this,
										Constants.DOWNLOAD_APK_URL);
							}
						}).show();
	}

	/*
	 * ����绰����ȷ����ʾ��
	 */
	private void showCallDialog() {
		new AlertDialog(PersonalCenterSettingActivity.this)
				.builder()
				.setMsg(Constants.CALL_NUMBER)
				.setNegativeButton(getString(R.string.cancle),
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// ȡ��
							}
						})
				.setPositiveButton(getString(R.string.call_now),
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								CommUtils
										.call(PersonalCenterSettingActivity.this);
							}
						}).show();
	}

}
