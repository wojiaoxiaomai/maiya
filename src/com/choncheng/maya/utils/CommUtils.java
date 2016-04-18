package com.choncheng.maya.utils;

import java.math.BigDecimal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.customview.AlertDialog;
import com.choncheng.maya.login.LoginActivity;
import com.choncheng.maya.shoppingcart.ShoppingCartActivity;

public class CommUtils {
	/**
	 * ��ϵ�ͷ�����绰
	 * 
	 * @param context
	 */
	public static void call(Context context) {
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
				+ Constants.CALL_NUMBER));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * ��ȡ���У���Ǯ
	 * 
	 * @param money
	 * @return
	 */
	public static String getMoney(String money) {
		String rsult = "";
		if (TextUtils.isEmpty(money)) {
			rsult = "";
		} else {
			rsult = "��" + money;
		}
		return rsult;
	}

	/**
	 * ��ȡͼƬ�ĵ�ַ�����ڷ���˷��ص�����Ե�ַ��
	 * 
	 * @param url
	 * @return
	 */
	public static String getImageUrl(String url) {
		if (url.startsWith("http://")) {
			return url;
		}
		return Constants.SERVER + url;
	}

	/**
	 * ������λС��
	 * 
	 * @param data
	 * @return
	 */
	public static double getTwoPointDouble(double data) {
		BigDecimal b = new BigDecimal(data);
		data = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return data;
	}

	/**
	 * ������¼ҳ��(��ʾ�û���¼)
	 * 
	 * @param context
	 */
	public static void lauchLoginActivity(final Context context) {

		new AlertDialog(context)
				.builder()
				.setMsg(context.getString(R.string.login_please))
				.setNegativeButton(context.getString(R.string.sure),
						new OnClickListener() {
							@Override
							public void onClick(View v) {
								MyApplication.getInstance().exit();
								Intent it = new Intent(context,
										LoginActivity.class);
								it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
										| Intent.FLAG_ACTIVITY_CLEAR_TOP);
								context.startActivity(it);
							}
						}).show();

	}

	/**
	 * ������¼ҳ��(�û��������ط���¼��)
	 * 
	 * @param context
	 */
	public static void lauchLoginActivityByLogingOtherPlace(
			final Context context) {
		new AlertDialog(context)
				.builder()
				.setMsg(context.getString(R.string.login_other_place))
				.setNegativeButton(context.getString(R.string.sure),
						new OnClickListener() {
							@Override
							public void onClick(View v) {
								MyApplication.getInstance().exit();
								updateShopCartNumber(context);
								Intent it = new Intent(context,
										LoginActivity.class);
								it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
										| Intent.FLAG_ACTIVITY_CLEAR_TOP);
								context.startActivity(it);
							}
						}).show();

	}

	// ���¹��ﳵ����
	private static void updateShopCartNumber(Context context) {
		MyApplication.getInstance().setShopCartNumber(0);
		Intent intent = new Intent();
		intent.setAction(Constants.ACTION_UPDATE_SHOPCART_NUMBER);
		context.sendBroadcast(intent);
	}
}
