package com.choncheng.maya.personal;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.comm.entity.User;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.contants.Status;
import com.choncheng.maya.db.UserDBHelper;
import com.choncheng.maya.login.ChangePasswordActivity;
import com.choncheng.maya.login.LoginActivity;
import com.choncheng.maya.login.RegisterActivity;
import com.choncheng.maya.shoppingcart.ShoppingCartActivity;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.CommUtils;
import com.choncheng.maya.utils.FileUtils;
import com.choncheng.maya.utils.ImageUtils;
import com.choncheng.maya.utils.LocalSettings;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�PersonalCenterActivity
 * @�������� ��������
 * @�����ˣ��
 * @����ʱ�䣺2015-8-7 ����8:44:33
 * @�汾�ţ�v1.0
 * 
 */
public class PersonalCenterActivity extends BaseActivity {
	private static final String TAG = "PersonalCenterActivity";
	private ImageView mSettingImg;
	private ImageView mMsgImg;
	private ImageView mMsgTipImageView;// �����ʾ
	private Button mLoginBtn;
	private Button mRegisterBtn;

	private View mNotLoginView;// û�е�¼�Ľ���
	private ScrollView mLoginView;// ��¼�ɹ��Ľ���
	private ImageView mHeadImageView;
	private View mTopView;// �����ת������Ϣ
	private View mAllOrderView;// ������ж���
	private View mDaifukuanView;// ������
	private View mDaishouhuoView;//
	private View mDaipingjiaView;
	private View mWanchengView;
	private View mTuikuanzhongView;

	private TextView mPhoneTxt;// �û��ֻ���
	private TextView mJifenTxt;// ����
	private TextView mVipTxt;// Vip�ȼ�
	private View mPersonalInfoView;// ���Ƹ�������
	private View mCollectionView;// �ղع���
	private View mMsgCenterView;
	private ImageView mMsgCenterTipImage;// �����ʾ
	private View mShopcarView;
	private View mChagePwdView;
	private View mAddView;

	private final static int CROP = 200;
	private final static String FILE_SAVEPATH = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/maiya/Portrait/";
	private File protraitFile;
	private Bitmap protraitBitmap;
	private String protraitPath;
	private Uri origUri;
	private Uri cropUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_activity);
		initView();

	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.person_center, false);
		initTitleView();
		initNotLoginView();
		initLoginView();
	}

	/**
	 * ��ʼ����¼�ɹ���Ľ���
	 */
	private void initLoginView() {
		mPhoneTxt = (TextView) findViewById(R.id.phone_txt);
		mJifenTxt = (TextView) findViewById(R.id.jifen_txt);
		mVipTxt = (TextView) findViewById(R.id.vip_txt);
		mHeadImageView = (ImageView) findViewById(R.id.head_image);
		mHeadImageView.setOnClickListener(this);
		mTopView = findViewById(R.id.personal_info_view);
		mTopView.setOnClickListener(this);

		// ��������
		mAllOrderView = findViewById(R.id.all_order_view);
		mAllOrderView.setOnClickListener(this);
		mDaifukuanView = findViewById(R.id.daifukuan_view);
		mDaifukuanView.setOnClickListener(this);
		mDaishouhuoView = findViewById(R.id.daishouhuo_view);
		mDaishouhuoView.setOnClickListener(this);
		mDaipingjiaView = findViewById(R.id.daipingjia_view);
		mDaipingjiaView.setOnClickListener(this);
		mWanchengView = findViewById(R.id.wancheng_view);
		mWanchengView.setOnClickListener(this);
		mTuikuanzhongView = findViewById(R.id.tuikuanzhong_view);
		mTuikuanzhongView.setOnClickListener(this);
		// ��������

		//
		mPersonalInfoView = findViewById(R.id.personal_infomation_view);
		mPersonalInfoView.setOnClickListener(this);
		mCollectionView = findViewById(R.id.collection_view);
		mCollectionView.setOnClickListener(this);
		mMsgCenterView = findViewById(R.id.msg_center_view);
		mMsgCenterView.setOnClickListener(this);
		mMsgCenterTipImage = (ImageView) findViewById(R.id.msg_center_tip_image);
		mShopcarView = findViewById(R.id.shop_car_view);
		mShopcarView.setOnClickListener(this);
		mChagePwdView = findViewById(R.id.change_pwd_view);
		mChagePwdView.setOnClickListener(this);
		mAddView = findViewById(R.id.add_view);
		mAddView.setOnClickListener(this);
	}

	/**
	 * ��ʼ������ �����Լ���Ϣ����view
	 */
	private void initTitleView() {
		mSettingImg = (ImageView) findViewById(R.id.setting_img);
		mMsgImg = (ImageView) findViewById(R.id.msg_img);
		mMsgTipImageView = (ImageView) findViewById(R.id.msg_tip_image);
		mSettingImg.setOnClickListener(this);
		mMsgImg.setOnClickListener(this);
	}

	/**
	 * ��ʼ��û�е�¼��view
	 */
	private void initNotLoginView() {
		mLoginBtn = (Button) findViewById(R.id.login_btn);
		mRegisterBtn = (Button) findViewById(R.id.register_btn);

		mLoginBtn.setOnClickListener(this);
		mRegisterBtn.setOnClickListener(this);

		mNotLoginView = findViewById(R.id.not_login_view);
		mLoginView = (ScrollView) findViewById(R.id.login_scrollview);
	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean isNewMessage = LocalSettings.getBoolean(
				LocalSettings.KEY_NEW_MSG, false);
		mMsgTipImageView.setVisibility(isNewMessage ? View.VISIBLE : View.GONE);
		mMsgCenterTipImage.setVisibility(isNewMessage ? View.VISIBLE
				: View.GONE);
		if (MyApplication.getInstance().isLogin()) {
			mNotLoginView.setVisibility(View.GONE);
			mLoginView.setVisibility(View.VISIBLE);
		} else {
			mNotLoginView.setVisibility(View.VISIBLE);
			mLoginView.setVisibility(View.GONE);
		}
		if (mUser != null) {
			// ��ȡʧЧʱ��
			orderLoseTime(mUser.getUcode());
			if (mUser.getHead_img().startsWith(Constants.FILE_START)) {
				imageLoader.displayImage(mUser.getHead_img(), mHeadImageView,
						BitmapUtil.getCircleOption());
			} else {
				if (!mUser.getHead_img().equals("")) {
					imageLoader.displayImage(
							Constants.SERVER + mUser.getHead_img(), mHeadImageView,
							BitmapUtil.getCircleOption());
				}
				
			}
			// mPhoneTxt.setText(mUser.getPhone());
			// ��ʾ�û��ǳ�
			if (TextUtils.isEmpty(mUser.getNack_name())) {
				mPhoneTxt.setText(mUser.getPhone());
			} else {
				mPhoneTxt.setText(mUser.getNack_name());
			}
			String user_integral = mUser.getUser_integral();
			if (TextUtils.isEmpty(user_integral)) {
				user_integral = "0";
			}
			mJifenTxt.setText("���֣�" + user_integral);

			// ˢ�»��֣�

			loginLogin(mUser.getPhone(), mUser.getPassword());
			// ͨ����ȡ�Ķ�����������ת���õ�vip�ȼ�
			API.orderlistsOverNumber(this, mUser.getUcode(),
					new ResponseHandler() {

						@Override
						public void onRese(String data, int state) {
							if (state == STATE_OK) {
								JSONObject jsonObject = JSON.parseObject(data);
								if (jsonObject != null) {
									String over_number = jsonObject
											.getString("over_number");
									if (!TextUtils.isEmpty(over_number)) {
										mVipTxt.setText(Status
												.getVip(over_number) + "��Ա");
									}
								}
							}
						}
					});
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (!checkNetwork()) {
			return;
		}
		Intent it = null;
		switch (v.getId()) {
		case R.id.setting_img:
			it = new Intent(this, PersonalCenterSettingActivity.class);
			break;
		case R.id.msg_img:
			if (MyApplication.getInstance().isLogin()) {
				it = new Intent(this, PersonalCenterMsgActivity.class);
				LocalSettings.putBoolean(LocalSettings.KEY_NEW_MSG, false);
			} else {
				CommUtils.lauchLoginActivity(this);
			}
			break;
		case R.id.login_btn:
			it = new Intent(this, LoginActivity.class);
			it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			it.putExtra(Extra.IS_SHOW_REGISTER, false);
			break;
		case R.id.register_btn:
			it = new Intent(this, RegisterActivity.class);
			it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TOP);
			break;
		case R.id.head_image:
			showSelectHeadDialog();
			break;
		case R.id.personal_info_view:
			it = new Intent(this, PersonalInfoActivity.class);
			break;
		case R.id.all_order_view:
			it = new Intent(this, PersonalOrderActivity.class);
			it.putExtra(Extra.ORDER_TYPE, Constants.ORDER_ALL);
			break;
		case R.id.daifukuan_view:
			it = new Intent(this, PersonalOrderActivity.class);
			it.putExtra(Extra.ORDER_TYPE, Constants.ORDER_NO_PAY);
			break;
		case R.id.daishouhuo_view:
			it = new Intent(this, PersonalOrderActivity.class);
			it.putExtra(Extra.ORDER_TYPE, Constants.ORDER_NO_GET);
			break;

		case R.id.daipingjia_view:
			it = new Intent(this, PersonalOrderActivity.class);
			it.putExtra(Extra.ORDER_TYPE, Constants.ORDER_NO_COMMENTS);
			break;
		case R.id.wancheng_view:
			it = new Intent(this, PersonalOrderActivity.class);
			it.putExtra(Extra.ORDER_TYPE, Constants.ORDER_IS_OVER);
			break;
		case R.id.tuikuanzhong_view:
			it = new Intent(this, PersonalOrderActivity.class);
			it.putExtra(Extra.ORDER_TYPE, Constants.ORDER_APPLY_ING);
			break;
		case R.id.personal_infomation_view:
			it = new Intent(this, PersonalInfoActivity.class);
			break;
		case R.id.collection_view:
			it = new Intent(this, PersonalCollectionActivity.class);
			break;

		case R.id.msg_center_view:
			it = new Intent(this, PersonalCenterMsgActivity.class);
			LocalSettings.putBoolean(LocalSettings.KEY_NEW_MSG, false);
			break;
		case R.id.shop_car_view:
			it = new Intent(this, ShoppingCartActivity.class);
			MyApplication.getInstance().setShopCartNeedUpdate(true);
			MyApplication.getInstance().setShopCartNeedback(true);

			break;
		case R.id.change_pwd_view:
			it = new Intent(this, ChangePasswordActivity.class);
			break;

		case R.id.add_view:
			it = new Intent(this, PersonalAddressActivity.class);
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
	 * ѡ��ͷ��
	 */
	private void showSelectHeadDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.edit_head);
		CharSequence[] items = { getString(R.string.img_from_camera),
				getString(R.string.img_from_album) };
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					// ѡ������
					startActionCamera();
					break;
				case 1:
					// ѡ�����
					startImagePick();
					break;
				default:
					break;
				}
			}
		});
		builder.create().show();
	}

	/**
	 * �������
	 * 
	 * @param output
	 */
	private void startActionCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, this.getCameraTempFile());
		startActivityForResult(intent,
				ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
	}

	// ���ձ���ľ���·��
	private Uri getCameraTempFile() {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(FILE_SAVEPATH);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			showToast(R.string.save_head_fail);
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		// ��Ƭ����
		String cropFileName = "osc_camera_" + timeStamp + ".jpg";
		// �ü�ͷ��ľ���·��
		protraitPath = FILE_SAVEPATH + cropFileName;
		protraitFile = new File(protraitPath);
		cropUri = Uri.fromFile(protraitFile);
		this.origUri = this.cropUri;
		return this.cropUri;
	}

	// �ü�ͷ��ľ���·��
	private Uri getUploadTempFile(Uri uri) {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(FILE_SAVEPATH);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			showToast(R.string.save_head_fail);
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

		// ����Ǳ�׼Uri
		if (TextUtils.isEmpty(thePath)) {
			thePath = ImageUtils.getAbsoluteImagePath(
					PersonalCenterActivity.this, uri);
		}
		String ext = FileUtils.getFileFormat(thePath);
		ext = TextUtils.isEmpty(ext) ? "jpg" : ext;
		// ��Ƭ����
		String cropFileName = "osc_crop_" + timeStamp + "." + ext;
		// �ü�ͷ��ľ���·��
		protraitPath = FILE_SAVEPATH + cropFileName;
		protraitFile = new File(protraitPath);

		cropUri = Uri.fromFile(protraitFile);
		return this.cropUri;
	}

	/**
	 * ���պ�ü�
	 * 
	 * @param data
	 *            ԭʼͼƬ
	 * @param output
	 *            �ü���ͼƬ
	 */
	private void startActionCrop(Uri data) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(data, "image/*");
		intent.putExtra("output", this.getUploadTempFile(data));
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// �ü������
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", CROP);// ���ͼƬ��С
		intent.putExtra("outputY", CROP);
		intent.putExtra("scale", true);// ȥ�ڱ�
		intent.putExtra("scaleUpIfNeeded", true);// ȥ�ڱ�
		startActivityForResult(intent,
				ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
	}

	/**
	 * ѡ��ͼƬ�ü�
	 * 
	 * @param output
	 */
	private void startImagePick() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(Intent.createChooser(intent, "ѡ��ͼƬ"),
				ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
	}

	@Override
	protected void onActivityResult(final int requestCode,
			final int resultCode, final Intent data) {
		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
		case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
			startActionCrop(origUri);// ���պ�ü�
			break;
		case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
			startActionCrop(data.getData());// ѡͼ��ü�
			break;
		case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
			if (DEBUG) {
				Log.d(TAG, "protraitPath :" + protraitPath);
			}
			if (!TextUtils.isEmpty(protraitPath)) {
				usereditEditInfo(protraitFile);
			} else {
				showToast(R.string.save_head_fail);
			}
			break;
		}
	}

	/**
	 * �ϴ��û���ͷ��
	 */
	private void usereditEditInfo(File file) {
		if (mUser != null) {
			showProgressDialog(R.string.loading_upload_head);
			API.usereditEditInfo(this, mUser.getUcode(), null, null, 0, file,
					null, null, null, null, null, null, null, 0, 0, 0,
					new ResponseHandler() {

						@Override
						public void onRese(String data, int state) {
							dismissDialog();
							switch (state) {
							case STATE_OK:
								showToast(R.string.save_head_sucess);
								mUser.setHead_img(Constants.FILE_START
										+ protraitPath);
								imageLoader.displayImage(mUser.getHead_img(),
										mHeadImageView,
										BitmapUtil.getCircleOption());
								UserDBHelper dbHelper = new UserDBHelper(
										PersonalCenterActivity.this);
								ContentValues values = new ContentValues();
								values.put(UserDBHelper.Column.HEAD_IMG,
										Constants.FILE_START + protraitPath);
								dbHelper.update(values, null, null);
								dbHelper.closeDb();
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

	}

	/**
	 * ��֧������ʧЧʱ��ӿڣ� order/lose_time
	 */
	private void orderLoseTime(String ucode) {
		API.orderLoseTime(this, ucode, new ResponseHandler() {

			@Override
			public void onRese(String data, int state) {
				if (state == STATE_OK) {
					JSONObject jsonObject = JSON.parseObject(data);
					if (jsonObject != null) {
						MyApplication.getInstance().setLostTime(
								jsonObject.getLongValue("lose_time"));
					}
				}
			}
		});
	}

	// ˢ�»���
	private void loginLogin(String phone, String password) {
		API.loginLogin(phone, password, new ResponseHandler() {
			@Override
			public void onRese(String data, int state) {
				dismissDialog();
				switch (state) {
				case STATE_OK:
					if (!TextUtils.isEmpty(data)) {
						User user = JSON.parseObject(data, User.class);
						user.setPassword(mUser.getPassword());
						mUser = user;
						// �����ݿ⣬
						boolean isAutoLogin = LocalSettings.getBoolean(
								LocalSettings.KEY_AUTO_LOGIN, false);
						if (isAutoLogin) {
							UserDBHelper dbHelper = new UserDBHelper(
									PersonalCenterActivity.this);
							dbHelper.saveUser(mUser);
						} else {

						}
						MyApplication.getInstance().setUser(mUser);
						String user_integral = mUser.getUser_integral();
						if (TextUtils.isEmpty(user_integral)) {
							user_integral = "0";
						}
						mJifenTxt.setText("���֣�" + user_integral);
					}
					break;
				default:
					break;
				}
			}
		});
	}
}
