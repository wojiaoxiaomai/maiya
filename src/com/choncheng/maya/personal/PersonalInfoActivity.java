package com.choncheng.maya.personal;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.comm.entity.User;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.db.UserDBHelper;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.DateTime;
import com.choncheng.maya.utils.FileUtils;
import com.choncheng.maya.utils.ImageUtils;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�PersonalInfoActivity
 * @�������� ������Ϣ
 * @�����ˣ��
 * @����ʱ�䣺2015-8-9 ����8:15:00
 * @�汾�ţ�v1.0
 * 
 */
public class PersonalInfoActivity extends BaseActivity {
	private static final String TAG = "PersonalInfoActivity";
	private ImageView mHeadImageView;
	private EditText mNackNameEdit;
	private RadioGroup mGroup;
	private RadioButton mBtn1;// ��
	private RadioButton mBtn2;// Ů
	private RadioButton mBtn3;// ����
	private int selectSex = 3;// ѡ����Ա�
	private EditText mBirthdayEdit;
	private EditText mPhoneEdit;
	private Button mSaveBtn;
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
		setContentView(R.layout.personal_info_activity);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.person_center, true);
		mHeadImageView = (ImageView) findViewById(R.id.head_image);
		mHeadImageView.setOnClickListener(this);
		mNackNameEdit = (EditText) findViewById(R.id.nack_name_edit);
		mGroup = (RadioGroup) findViewById(R.id.group);
		mBtn1 = (RadioButton) findViewById(R.id.btn_1);
		mBtn2 = (RadioButton) findViewById(R.id.btn_2);
		mBtn3 = (RadioButton) findViewById(R.id.btn_3);
		mGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.btn_1:
					selectSex = 1;
					break;
				case R.id.btn_2:
					selectSex = 2;
					break;
				case R.id.btn_3:
					selectSex = 3;
					break;
				default:
					break;
				}
			}
		});
		mPhoneEdit = (EditText) findViewById(R.id.phone_edit);
		mBirthdayEdit = (EditText) findViewById(R.id.birthday_edit);
		mBirthdayEdit.setOnClickListener(this);
		mPhoneEdit = (EditText) findViewById(R.id.phone_edit);
		mSaveBtn = (Button) findViewById(R.id.save_btn);
		mSaveBtn.setOnClickListener(this);
		if (mUser != null) {
			if (mUser.getHead_img().startsWith(Constants.FILE_START)) {
				imageLoader.displayImage(mUser.getHead_img(), mHeadImageView,
						BitmapUtil.getCircleOption());
			} else {
				if (!mUser.getHead_img().endsWith("")) {
					imageLoader.displayImage(
							Constants.SERVER + mUser.getHead_img(), mHeadImageView,
							BitmapUtil.getCircleOption());
				}
				
			}

			mNackNameEdit.setText(mUser.getNack_name());
			String sexStr = mUser.getSex();
			if (sexStr.equals("1")) {
				mBtn1.setChecked(true);
			} else if (sexStr.equals("2")) {
				mBtn2.setChecked(true);
			} else {
				mBtn3.setChecked(true);
			}
			if (!TextUtils.isEmpty(mUser.getBirthday())
					&& !mUser.getBirthday().equals("0")) {
				mBirthdayEdit.setText(DateTime.getDatebyStemp(mUser
						.getBirthday()));
			} else {
				mBirthdayEdit.setText("");
			}
			mPhoneEdit.setText(mUser.getTel());

		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.head_image:
			showSelectHeadDialog();
			break;

		case R.id.save_btn:
			String nack_name = mNackNameEdit.getText().toString().trim();
			String birthday = mBirthdayEdit.getText().toString();
			String phone = mPhoneEdit.getText().toString().trim();
			if (!TextUtils.isEmpty(phone) && phone.length() != 11) {
				showToast(R.string.mobile_length_wrong);
				return;
			}
			if (DEBUG) {
				Log.d(TAG,
						"birthday:" + birthday + ","
								+ DateTime.getTimeStemp(birthday));
			}
			usereditEditInfo(nack_name, selectSex, null,
					DateTime.getTimeStemp(birthday) + "", phone);
			break;
		case R.id.birthday_edit:
			showDatepickerDlg();
			break;
		default:
			break;
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
					PersonalInfoActivity.this, uri);
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
				usereditEditInfo(null, 0, protraitFile, null, null);
			} else {
				showToast(R.string.save_head_fail);
			}
			break;
		}
	}

	/**
	 * �޸��û�����
	 * 
	 * @param nack_name
	 *            �ǳ�
	 * @param sex
	 *            �Ա� �Ա� 1���� 2Ů 3:���� 4������
	 * @param file
	 *            ͷ��
	 * @param birthday����
	 * @param tel
	 *            ��ϵ�绰
	 */
	private void usereditEditInfo(final String nack_name, final int sex,
			final File file, final String birthday, final String tel) {
		if (mUser != null) {
			showProgressDialog(R.string.loading_upload_head);
			API.usereditEditInfo(this,mUser.getUcode(), null, nack_name, sex, file,
					birthday, null, null, null, null, null, tel, 0, 0, 0,
					new ResponseHandler() {

						@Override
						public void onRese(String data, int state) {
							dismissDialog();
							switch (state) {
							case STATE_OK:
								UserDBHelper dbHelper = new UserDBHelper(
										PersonalInfoActivity.this);
								ContentValues values = new ContentValues();

								showToast(R.string.save_person_info_sucess);
								if (file != null) {
									// �ϴ���ͷ��
									mUser.setHead_img(Constants.FILE_START
											+ protraitPath);
									imageLoader.displayImage(
											mUser.getHead_img(),
											mHeadImageView,
											BitmapUtil.getCircleOption());
									values.put(UserDBHelper.Column.HEAD_IMG,
											Constants.FILE_START + protraitPath);
								} else {
									// �޸ĵ�����������
									mUser.setNack_name(nack_name);
									mUser.setSex(sex + "");
									mUser.setTel(tel);
									mUser.setBirthday(birthday);
									values.put(UserDBHelper.Column.NACK_NAME,
											nack_name);
									values.put(UserDBHelper.Column.SEX, sex);
									values.put(UserDBHelper.Column.TEL, tel);
									values.put(UserDBHelper.Column.BIRTHDAY,
											birthday);
								}
								dbHelper.update(values, null, null);
								dbHelper.closeDb();
								finish();
								AnimUtil.setFromRightToLeft(PersonalInfoActivity.this);
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

	// ʱ��ѡ��ؼ�
	private void showDatepickerDlg() {
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog dialog = new DatePickerDialog(this, dateListener,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		dialog.setTitle(R.string.select_date);
		dialog.show();
	}

	DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// monthOfYearҪ��1
			String selectDate = year + "-" + String.valueOf(monthOfYear + 1)
					+ "-" + dayOfMonth;

			try {
				if (!DateTime.comparisonDate(selectDate)) {
					showToast(R.string.select_date_wrong);
					return;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			mBirthdayEdit.setText(selectDate);
		}

	};
}
