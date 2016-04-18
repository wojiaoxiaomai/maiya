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
	 * 联系客服拨打电话
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
	 * 获取带有￥的钱
	 * 
	 * @param money
	 * @return
	 */
	public static String getMoney(String money) {
		String rsult = "";
		if (TextUtils.isEmpty(money)) {
			rsult = "";
		} else {
			rsult = "￥" + money;
		}
		return rsult;
	}

	/**
	 * 获取图片的地址（由于服务端返回的是相对地址）
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
	 * 保留两位小数
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
	 * 启动登录页面(提示用户登录)
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
	 * 启动登录页面(用户在其他地方登录了)
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

	// 更新购物车数量
	private static void updateShopCartNumber(Context context) {
		MyApplication.getInstance().setShopCartNumber(0);
		Intent intent = new Intent();
		intent.setAction(Constants.ACTION_UPDATE_SHOPCART_NUMBER);
		context.sendBroadcast(intent);
	}
}
