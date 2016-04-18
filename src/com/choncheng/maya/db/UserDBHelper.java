package com.choncheng.maya.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.choncheng.maya.comm.entity.User;
import com.choncheng.maya.utils.RandomNumber;

/**
 * 
 * 
 * @项目名称：Maya
 * @类名称：UserDBHelper
 * @类描述： 用户基础数据库
 * @创建人：李波(对应字段解释在user实体中)
 * @创建时间：2015-8-16 上午8:28:45
 * @版本号：v1.0
 * 
 */
public class UserDBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "user";
	private static final int DATABASE_VERSION = 2;
	private static final Object dbLock = new Object();

	private static final String TABLE_CREATE = "CREATE TABLE " + DATABASE_NAME
			+ " (" + Column._ID + " TEXT NOT NULL PRIMARY KEY ," + Column.UCODE
			+ " TEXT, " + Column.REAL_NAME + " TEXT, " + Column.NACK_NAME
			+ " TEXT, " + Column.SEX + " TEXT, " + Column.HEAD_IMG + " TEXT, "
			+ Column.BIRTHDAY + " TEXT, " + Column.AUTOGRAPH + " TEXT, "
			+ Column.EXPAND1 + " TEXT, " + Column.EXPAND2 + " TEXT, "
			+ Column.EXPAND3 + " TEXT, " + Column.EXPAND4 + " TEXT, "
			+ Column.TEL + " TEXT, " + Column.DISTRICT_ID + " TEXT, "
			+ Column.CREATE_TIME + " TEXT, " + Column.UID + " TEXT, "
			+ Column.PHONE + " TEXT, " + Column.PASSWORD + " TEXT, "
			+ Column.USER_INTEGRAL + " TEXT, " + Column.SHOP_INTEGRAL
			+ " TEXT, " + Column.MONEY + " TEXT, " + Column.FREEZE_MONEY
			+ " TEXT, " + Column.DEVICE_TYPE + " TEXT, " + Column.DEVICE_NUMBER
			+ " TEXT, " + Column.LNG + " TEXT, " + Column.LAT + " TEXT, "
			+ Column.PROVINCE_ID + " TEXT, " + Column.CITY_ID + " TEXT, "
			+ Column.LOGIN_TIME + " TEXT, " + Column.LOGIN_NUM + " TEXT);";

	public static final class Column {
		public static final String _ID = "_id";
		public static final String REAL_NAME = "real_name";
		public static final String NACK_NAME = "nack_name";
		public static final String SEX = "sex";
		public static final String HEAD_IMG = "head_img";
		public static final String BIRTHDAY = "birthday";
		public static final String AUTOGRAPH = "autograph";
		public static final String EXPAND1 = "expand1";
		public static final String EXPAND2 = "expand2";
		public static final String EXPAND3 = "expand3";
		public static final String EXPAND4 = "expand4";
		public static final String TEL = "tel";// 联系电话
		public static final String PROVINCE_ID = "province_id";
		public static final String CITY_ID = "city_id";
		public static final String DISTRICT_ID = "district_id";
		public static final String CREATE_TIME = "create_time";
		public static final String UID = "uid";
		public static final String PHONE = "phone";
		public static final String PASSWORD = "password";
		public static final String USER_INTEGRAL = "user_integral";
		public static final String SHOP_INTEGRAL = "shop_integral";
		public static final String MONEY = "money";
		public static final String FREEZE_MONEY = "freeze_money";
		public static final String DEVICE_TYPE = "device_type";
		public static final String DEVICE_NUMBER = "device_number";
		public static final String LNG = "lng";
		public static final String LAT = "lat";
		public static final String UCODE = "ucode";
		public static final String LOGIN_TIME = "login_time";
		public static final String LOGIN_NUM = "login_num";
	}

	private SQLiteDatabase mSqLiteDatabase;

	public UserDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mSqLiteDatabase = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
		onCreate(db);
	}

	public long insert(ContentValues values) {
		synchronized (dbLock) {
			return mSqLiteDatabase.insert(DATABASE_NAME, null, values);
		}
	}

	public int delete(String whereClause, String[] whereArgs) {
		synchronized (dbLock) {
			return mSqLiteDatabase
					.delete(DATABASE_NAME, whereClause, whereArgs);
		}
	}

	public int update(ContentValues values, String whereClause,
			String[] whereArgs) {
		synchronized (dbLock) {
			return mSqLiteDatabase.update(DATABASE_NAME, values, whereClause,
					whereArgs);
		}
	}

	public Cursor query(String[] columns, String selection,
			String[] selectionArgs, String orderBy) {
		return query(DATABASE_NAME, columns, selection, selectionArgs, null,
				null, orderBy, null);
	}

	public Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) {
		synchronized (dbLock) {
			return mSqLiteDatabase.query(table, columns, selection,
					selectionArgs, groupBy, having, orderBy, limit);
		}
	}

	/**
	 * 数据库是否为空
	 * 
	 * @return
	 */
	public boolean isEmptyDB() {
		boolean isEmpty = false;
		Cursor cursor = null;
		try {
			cursor = query(null, null, null, null);
			if (cursor.getColumnCount() <= 0) {
				isEmpty = true;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			closeDb();

		}
		return isEmpty;
	}

	/**
	 * 查询 用户数据
	 * 
	 * @return
	 */
	public User getUser() {
		Cursor cursor = null;
		User user = null;
		try {
			cursor = query(null, null, null, null);
			if (cursor.moveToFirst()) {
				String phone = cursor.getString(cursor
						.getColumnIndex(Column.PHONE));
				String sex = cursor
						.getString(cursor.getColumnIndex(Column.SEX));
				String head_img = cursor.getString(cursor
						.getColumnIndex(Column.HEAD_IMG));
				String nack_name = cursor.getString(cursor
						.getColumnIndex(Column.NACK_NAME));
				String birthday = cursor.getString(cursor
						.getColumnIndex(Column.BIRTHDAY));
				String tel = cursor
						.getString(cursor.getColumnIndex(Column.TEL));
				String ucode = cursor.getString(cursor
						.getColumnIndex(Column.UCODE));
				String user_integral = cursor.getString(cursor
						.getColumnIndex(Column.USER_INTEGRAL));
				String password = cursor.getString(cursor
						.getColumnIndex(Column.PASSWORD));
				user = new User();
				user.setPhone(phone);
				user.setSex(sex);
				user.setHead_img(head_img);
				user.setNack_name(nack_name);
				user.setBirthday(birthday);
				user.setUser_integral(user_integral);
				user.setUcode(ucode);
				user.setTel(tel);
				user.setPassword(password);
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			closeDb();
		}
		return user;
	}

	/**
	 * 保存用户数据
	 * 
	 * @param user
	 */
	public void saveUser(User user) {
		try {
			delete(null, null);
			ContentValues values = new ContentValues();
			values.put(UserDBHelper.Column._ID, RandomNumber.randomUUidPK());
			values.put(UserDBHelper.Column.REAL_NAME, user.getReal_name());
			values.put(UserDBHelper.Column.NACK_NAME, user.getNack_name());
			values.put(UserDBHelper.Column.SEX, user.getSex());
			values.put(UserDBHelper.Column.HEAD_IMG, user.getHead_img());
			values.put(UserDBHelper.Column.BIRTHDAY, user.getBirthday());
			values.put(UserDBHelper.Column.AUTOGRAPH, user.getAutograph());
			values.put(UserDBHelper.Column.EXPAND1, user.getExpand1());
			values.put(UserDBHelper.Column.EXPAND2, user.getExpand2());
			values.put(UserDBHelper.Column.EXPAND3, user.getExpand3());
			values.put(UserDBHelper.Column.EXPAND4, user.getExpand4());
			values.put(UserDBHelper.Column.TEL, user.getTel());
			values.put(UserDBHelper.Column.PROVINCE_ID, user.getProvince_id());
			values.put(UserDBHelper.Column.CITY_ID, user.getCity_id());
			values.put(UserDBHelper.Column.DISTRICT_ID, user.getDistrict_id());
			values.put(UserDBHelper.Column.CREATE_TIME, user.getCreate_time());
			values.put(UserDBHelper.Column.UID, user.getUid());
			values.put(UserDBHelper.Column.PHONE, user.getPhone());
			values.put(UserDBHelper.Column.PASSWORD, user.getPassword());
			values.put(UserDBHelper.Column.USER_INTEGRAL,
					user.getUser_integral());
			values.put(UserDBHelper.Column.SHOP_INTEGRAL,
					user.getShop_integral());
			values.put(UserDBHelper.Column.MONEY, user.getMoney());
			values.put(UserDBHelper.Column.FREEZE_MONEY, user.getFreeze_money());
			values.put(UserDBHelper.Column.DEVICE_TYPE, user.getDevice_type());
			values.put(UserDBHelper.Column.DEVICE_NUMBER,
					user.getDevice_number());
			values.put(UserDBHelper.Column.LNG, user.getLng());
			values.put(UserDBHelper.Column.LAT, user.getLat());
			values.put(UserDBHelper.Column.UCODE, user.getUcode());
			values.put(UserDBHelper.Column.LOGIN_TIME, user.getLogin_time());
			values.put(UserDBHelper.Column.LOGIN_NUM, user.getLogin_num());
			insert(values);
		} finally {
			closeDb();
		}

	}

	/**
	 * 删除用户数据
	 */
	public void deleteUser() {
		try {
			delete(null, null);
		} finally {
			closeDb();
		}

	}

	public void closeDb() {
		if (mSqLiteDatabase != null && mSqLiteDatabase.isOpen()) {
			mSqLiteDatabase.close();
		}
	}
}
