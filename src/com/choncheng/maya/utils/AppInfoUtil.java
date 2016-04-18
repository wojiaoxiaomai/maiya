package com.choncheng.maya.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.choncheng.maya.R;

/**
 * 
 * @��Ŀ���ƣ�
 * @�����ƣ�AppInfoUtil
 * @�������� ��ȡapplication��Ϣ�Ĺ�����
 * @�����ˣ��
 * @����ʱ�䣺2014-9-26 ����9:10:29
 * @�汾�ţ�v1.0
 * 
 */
public class AppInfoUtil {
	/**
	 * 
	 * @return
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * apk�ļ��Ƿ��Ѿ���װ
	 * 
	 * @param context
	 * @param pkgName
	 *            ��Ӧ�İ���
	 * @return
	 */
	public static boolean isAppInstalled(Context context, String pkgName) {
		PackageManager pm = context.getPackageManager();
		boolean installed = false;
		try {
			pm.getPackageInfo(pkgName, PackageManager.GET_ACTIVITIES);
			installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			installed = false;
		}
		return installed;
	}

	/**
	 * sd���Ƿ����
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isVaiSDCard() {
		boolean result = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		return result;
	}

	/**
	 * �����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * ��ȡ��Ļ �Ŀ�
	 */
	public static int getWith(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		// ��ȡ��Ļ��Ϣ
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		return screenWidth;
	}

	/**
	 * ��ȡ��Ļ �ĸ�
	 */
	public static int getHeight(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		// ��ȡ��Ļ��Ϣ
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenHeight = dm.heightPixels;
		return screenHeight;
	}

	public static boolean fileIsExists(String path) {
		File file = new File(path);
		return file.exists();
	}

	public static boolean copyApkFromAssets(Context context, String fileName,
			String path) {
		boolean copyIsFinish = false;
		try {
			InputStream is = context.getAssets().open(fileName);
			File file = new File(path);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
			copyIsFinish = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return copyIsFinish;
	}

	public static void installApp(Context context, String path) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://" + path),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	public static boolean startAppliction(Context context, String packageName) {
		boolean isStartSucessed = false;
		Intent intent = new Intent();
		PackageInfo packinfo;
		try {
			packinfo = context.getPackageManager().getPackageInfo(packageName,
					PackageManager.GET_ACTIVITIES);

			ActivityInfo[] activityinfos = packinfo.activities;
			if (activityinfos != null && activityinfos.length > 0) {
				String className = activityinfos[0].name;
				intent.setClassName(packageName, className);
				context.startActivity(intent);
				isStartSucessed = true;
			} else {
				isStartSucessed = false;
			}
		} catch (NameNotFoundException e) {// ʹ��C����ʵ�ֵ�Ӧ�ó�����DDMS��û�ж�Ӧ�İ���
			e.printStackTrace();
			isStartSucessed = false;
		}
		return isStartSucessed;

	}

}
