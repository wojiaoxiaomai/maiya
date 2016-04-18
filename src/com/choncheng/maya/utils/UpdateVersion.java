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
 * @Description: TODO APP�汾����
 * 
 * @author �
 * 
 * @date 2015��4��1�� ����3:36:10
 * 
 */
public class UpdateVersion {

	// ���url�Ƿ�����Ч�����ص�ַ
	private static final int CHECK_URL = 2;
	// ��������
	private static final int DOWN = 1;
	// �������
	private static final int DOWN_FINISH = 0;
	// ������apk�ĵ�ַ
	private String fileSavePath;
	// ��ȡ��apk������������,�������ع�����
	private int progress;
	// �Ƿ�ȡ������
	private boolean cancelUpdate = false;
	// ������
	private Context context;
	// ProgressBar
	private ProgressBar progressBar;
	// ���ؿ�
	private Dialog downLoadDialog;
	// �����߳�
	private Thread downLoadThread;
	// ���ش���sd���ļ���
	String sdAppFileName = "maiya";
	int num = 0;
	// APP���°汾��Ϣ
	String downloadUrl;
	// �̣߳�����UI
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch ((Integer) msg.what) {
			case DOWN:// ����ʱ��ʾ���ؿ�
				progressBar.setProgress(progress);
				break;
			case DOWN_FINISH:// ������ɺ����ʾ
				Toast.makeText(context, "�ļ��������,���ڰ�װ����", Toast.LENGTH_SHORT)
						.show();
				// ��װ�°�apk
				installAPK();
				break;
			case CHECK_URL:
				int state = (Integer) msg.obj;
				if (state == 200) {
					// ��ʾ���ضԻ���
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
	 * ���췽��
	 * 
	 * @param context
	 *            ������
	 */
	public UpdateVersion(Context context, String downloadUrl) {
		super();
		this.context = context;
		this.downloadUrl = downloadUrl;
		showUpdateDialog();
	}

	/**
	 * ��ַ������ʾ��
	 */
	private void showUpdateUrlFeildDialog() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("�������");
		builder.setMessage("���ص�ַ���Ӵ���");
		// ����
		builder.setPositiveButton("ȷ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ȡ���Ի���
				dialog.dismiss();
			}
		});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	/**
	 * ������ʾ��
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
	 * ���ص���ʾ��
	 */
	protected void showDownloadDialog() {
		// ����������ضԻ���
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("���ڸ���");
		// �����ضԻ������ӽ�����
		final LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.downloaddialog, null);
		progressBar = (ProgressBar) v.findViewById(R.id.updateProgress);
		builder.setView(v);
		// ȡ������
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// ����ȡ��״̬
				cancelUpdate = true;
				downloadApkThread.interrupted();

			}
		});
		downLoadDialog = builder.create();
		downLoadDialog.show();
		// �����ļ�
		downloadApk();
	}

	/**
	 * ����apk,����ռ�����߳�.���������߳�
	 */
	private void downloadApk() {
		new downloadApkThread().start();

	}

	/**
	 * �ж��Ƿ�ɸ���
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
				// ��version.xml�ŵ������ϣ�Ȼ���ȡ�ļ���Ϣ
				URL url = new URL(downloadUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setReadTimeout(5 * 1000);
				conn.setRequestMethod("GET");// ����Ҫ��д
			} catch (Exception e) {
				e.printStackTrace();
				L.e("erro", e + "");
			}

		}
	};

	/**
	 * ��װapk�ļ�
	 */
	private void installAPK() {
		File apkfile = new File(fileSavePath, sdAppFileName + num + ".apk");
		if (!apkfile.exists()) {
			return;
		}
		// ͨ��Intent��װAPK�ļ�
		Intent i = new Intent(Intent.ACTION_VIEW);
		// System.out.println("filepath=" + apkfile.toString() + "  " +
		// apkfile.getPath());
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		context.startActivity(i);
		android.os.Process.killProcess(android.os.Process.myPid());// ������������Ļ���apk��װ���֮�������������
	}

	/**
	 * ж��Ӧ�ó���(û���õ�)
	 */
	public void uninstallAPK() {
		Uri packageURI = Uri.parse("package:com.example.updateversion");
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		context.startActivity(uninstallIntent);
	}

	/**
	 * ����apk�ķ���
	 * 
	 * @author
	 * 
	 */
	public class downloadApkThread extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				// �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// ��ô洢����·��
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					fileSavePath = sdpath + "download";
					URL url = new URL(downloadUrl);
					// ��������
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setReadTimeout(5 * 1000);// ���ó�ʱʱ��
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Charser",
							"GBK,utf-8;q=0.7,*;q=0.3");
					// ��ȡ�ļ���С
					int length = conn.getContentLength();
					// ����������
					InputStream is = conn.getInputStream();

					File file = new File(fileSavePath);
					// �ж��ļ�Ŀ¼�Ƿ����
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
					// ����
					byte buf[] = new byte[1024];
					// д�뵽�ļ���
					do {
						int numread = is.read(buf);
						count += numread;
						// ���������λ��
						progress = (int) (((float) count / length) * 100);
						// ���½���
						Message message = new Message();
						message.what = DOWN;
						handler.sendMessage(message);
						if (numread <= 0) {
							// �������
							// ȡ�����ضԻ�����ʾ
							downLoadDialog.dismiss();
							Message message2 = new Message();
							message2.what = DOWN_FINISH;
							handler.sendMessage(message2);
							break;
						}
						// д���ļ�
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// ���ȡ����ֹͣ����.
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
