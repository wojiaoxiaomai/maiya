package com.choncheng.maya.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.choncheng.maya.personal.PersonalCenterMsg;

/**
 * 
 * @项目名称：Maya
 * @类名称：MessageDBHelper
 * @类描述： 消息本地存储
 * @创建人：李波
 * @创建时间：2015-8-25 上午9:46:08
 * @版本号：v1.0
 * 
 */
public class MessageDBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "message";
	private static final int DATABASE_VERSION = 1;
	private static final Object dbLock = new Object();

	private static final String TABLE_CREATE = "CREATE TABLE " + DATABASE_NAME
			+ " (" + Column.ID + " TEXT NOT NULL PRIMARY KEY ," + Column.UID
			+ " TEXT, " + Column.UNAME + " TEXT, " + Column.HEAD_IMG
			+ " TEXT, " + Column.SHOP_ID + " TEXT, " + Column.SHOP_LOGO
			+ " TEXT, " + Column.SHOP_NAME + " TEXT, " + Column.CREATE_TIME
			+ " TEXT, " + Column.IS_READ + " TEXT, " + Column.U_TO_S
			+ " TEXT, " + Column.TITLE + " TEXT, " + Column.CONTENT + " TEXT, "
			+ Column.STATUS + " TEXT);";

	public static final class Column {
		public static final String ID = "id";
		public static final String UID = "uid";
		public static final String UNAME = "uname";
		public static final String HEAD_IMG = "head_img";
		public static final String SHOP_ID = "shop_id";
		public static final String SHOP_LOGO = "shop_logo";
		public static final String SHOP_NAME = "shop_name";
		public static final String CREATE_TIME = "create_time";
		public static final String IS_READ = "is_read";// 是否已读 1： 已读 2：未读
		public static final String TITLE = "title";
		public static final String CONTENT = "content";
		public static final String U_TO_S = "u_to_s";// 用户发个商家的 2：商家发给用户的
		public static final String STATUS = "status";
	}

	private SQLiteDatabase mSqLiteDatabase;

	public MessageDBHelper(Context context) {
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
	 * 查询 消息列表
	 * 
	 * @return
	 */
	public List<PersonalCenterMsg> getMessageList() {
		Cursor cursor = null;
		List<PersonalCenterMsg> msgList = new ArrayList<PersonalCenterMsg>();
		try {
			cursor = query(null, null, null, Column.CREATE_TIME + " ASC");
			while (cursor.moveToNext()) {
				PersonalCenterMsg msg = new PersonalCenterMsg();
				String head_img = cursor.getString(cursor
						.getColumnIndex(Column.HEAD_IMG));
				String create_time = cursor.getString(cursor
						.getColumnIndex(Column.CREATE_TIME));
				String content = cursor.getString(cursor
						.getColumnIndex(Column.CONTENT));
				String u_to_s = cursor.getString(cursor
						.getColumnIndex(Column.U_TO_S));
				msg.setContent(content);
				msg.setU_to_s(u_to_s);
				msg.setHead_img(head_img);
				msg.setCreate_time(create_time);
				msgList.add(msg);
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			closeDb();
		}
		return msgList;
	}

	/**
	 * 保存单条消息
	 * 
	 * @param user
	 */
	public void saveMessage(PersonalCenterMsg msg) {
		try {
			ContentValues values = new ContentValues();
			values.put(MessageDBHelper.Column.ID, msg.getId());
			values.put(MessageDBHelper.Column.CREATE_TIME, msg.getCreate_time());
			values.put(MessageDBHelper.Column.CONTENT, msg.getContent());
			values.put(MessageDBHelper.Column.U_TO_S, msg.getU_to_s());
			values.put(MessageDBHelper.Column.HEAD_IMG, msg.getHead_img());
			insert(values);
		} finally {
			closeDb();
		}

	}

	/**
	 * 保存多条消息
	 * 
	 * @param msgList
	 */
	public void saveMessage(List<PersonalCenterMsg> msgList) {
		try {
			for (int i = 0; i < msgList.size(); i++) {
				PersonalCenterMsg msg = msgList.get(i);
				ContentValues values = new ContentValues();
				values.put(MessageDBHelper.Column.ID, msg.getId());
				values.put(MessageDBHelper.Column.CREATE_TIME,
						msg.getCreate_time());
				values.put(MessageDBHelper.Column.CONTENT, msg.getContent());
				values.put(MessageDBHelper.Column.U_TO_S, msg.getU_to_s());
				values.put(MessageDBHelper.Column.HEAD_IMG, msg.getHead_img());
				insert(values);
			}
		} finally {
			closeDb();
		}

	}

	/**
	 * 删除所有消息
	 */
	public void deleteMessageList() {
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
