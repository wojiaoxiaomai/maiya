package com.choncheng.maya.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.choncheng.maya.R;
import com.nostra13.universalimageloader.utils.L;

/**
 * 
 * @Description: TODO APP版本更新
 * 
 * @author 李波
 * 
 * @date 2015年4月1日 下午3:36:10
 * 
 */
public class UpdateVersion {

	// 检测url是否是有效的下载地址
	private static final int CHECK_URL = 2;
	// 正在下载
	private static final int DOWN = 1;
	// 下载完成
	private static final int DOWN_FINISH = 0;
	// 下载新apk的地址
	private String fileSavePath;
	// 获取新apk的下载数据量,更新下载滚动条
	private int progress;
	// 是否取消下载
	private boolean cancelUpdate = false;
	// 上下文
	private Context context;
	// ProgressBar
	private ProgressBar progressBar;
	// 下载框
	private Dialog downLoadDialog;
	// 下载线程
	private Thread downLoadThread;
	// 下载存入sd的文件名
	String sdAppFileName = "maiya";
	int num = 0;
	// APP最新版本信息
	String downloadUrl;
	// 线程，更新UI
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch ((Integer) msg.what) {
			case DOWN:// 下载时显示下载框
				progressBar.setProgress(progress);
				break;
			case DOWN_FINISH:// 下载完成后的提示
				Toast.makeText(context, "文件下载完成,正在安装更新", Toast.LENGTH_SHORT)
						.show();
				// 安装新版apk
				installAPK();
				break;
			case CHECK_URL:
				int state = (Integer) msg.obj;
				if (state == 200) {
					// 显示下载对话框
					showDownloadDialog();
				} else {
					showUpdateUrlFeildDialog();
				}
				break;
			default:
				break;
			}
		}

	};

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            上下文
	 */
	public UpdateVersion(Context context, String downloadUrl) {
		super();
		this.context = context;
		this.downloadUrl = downloadUrl;
		showUpdateDialog();
	}

	/**
	 * 地址错误提示框
	 */
	private void showUpdateUrlFeildDialog() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("软件更新");
		builder.setMessage("下载地址链接错误！");
		// 更新
		builder.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 取消对话框
				dialog.dismiss();
			}
		});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	/**
	 * 更新提示框
	 */
	private void showUpdateDialog() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				URL url;
				try {
					url = new URL(downloadUrl);

					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					int state = conn.getResponseCode();

					Message msg = Message.obtain();
					msg.what = CHECK_URL;
					msg.obj = state;
					handler.sendMessage(msg);

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	protected void shutdown() {
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 下载的提示框
	 */
	protected void showDownloadDialog() {
		// 构造软件下载对话框
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("正在更新");
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.downloaddialog, null);
		progressBar = (ProgressBar) v.findViewById(R.id.updateProgress);
		builder.setView(v);
		// 取消更新
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置取消状态
				cancelUpdate = true;
				downloadApkThread.interrupted();

			}
		});
		downLoadDialog = builder.create();
		downLoadDialog.show();
		// 现在文件
		downloadApk();
	}

	/**
	 * 下载apk,不能占用主线程.所以另开的线程
	 */
	private void downloadApk() {
		new downloadApkThread().start();

	}

	/**
	 * 判断是否可更新
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	private boolean isUpdate() {
		downLoadThread = new Thread(runnable);
		downLoadThread.start();
		try {
			downLoadThread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;

	}

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			try {
				// 把version.xml放到网络上，然后获取文件信息
				URL url = new URL(downloadUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setReadTimeout(5 * 1000);
				conn.setRequestMethod("GET");// 必须要大写
			} catch (Exception e) {
				e.printStackTrace();
				L.e("erro", e + "");
			}

		}
	};

	/**
	 * 安装apk文件
	 */
	private void installAPK() {
		File apkfile = new File(fileSavePath, sdAppFileName + num + ".apk");
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		// System.out.println("filepath=" + apkfile.toString() + "  " +
		// apkfile.getPath());
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		context.startActivity(i);
		android.os.Process.killProcess(android.os.Process.myPid());// 如果不加上这句的话在apk安装完成之后点击单开会崩溃
	}

	/**
	 * 卸载应用程序(没有用到)
	 */
	public void uninstallAPK() {
		Uri packageURI = Uri.parse("package:com.example.updateversion");
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		context.startActivity(uninstallIntent);
	}

	/**
	 * 下载apk的方法
	 * 
	 * @author
	 * 
	 */
	public class downloadApkThread extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 获得存储卡的路径
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					fileSavePath = sdpath + "download";
					URL url = new URL(downloadUrl);
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setReadTimeout(5 * 1000);// 设置超时时间
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Charser",
							"GBK,utf-8;q=0.7,*;q=0.3");
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(fileSavePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdir();
					}
					num++;
					File apkFile = new File(fileSavePath, sdAppFileName + num
							+ ".apk");
					if (apkFile.exists()) {
						num++;
						apkFile = new File(fileSavePath, sdAppFileName + num
								+ ".apk");
					}
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						Message message = new Message();
						message.what = DOWN;
						handler.sendMessage(message);
						if (numread <= 0) {
							// 下载完成
							// 取消下载对话框显示
							downLoadDialog.dismiss();
							Message message2 = new Message();
							message2.what = DOWN_FINISH;
							handler.sendMessage(message2);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();

				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
