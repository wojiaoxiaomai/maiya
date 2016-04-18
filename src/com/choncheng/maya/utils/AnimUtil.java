package com.choncheng.maya.utils;

import android.app.Activity;

import com.choncheng.maya.R;

/**
 * 
 * @项目名称：Maya
 * @类名称：AnimUtil
 * @类描述： 动画切换
 * @创建人：李波
 * @创建时间：2015-8-7 下午10:49:33
 * @版本号：v1.0
 * 
 */
public class AnimUtil {
	public static void setFromLeftToRight(Activity activity) {
		activity.overridePendingTransition(R.anim.push_left_in,
				R.anim.push_left_out);
	}

	public static void setFromRightToLeft(Activity activity) {
		activity.overridePendingTransition(R.anim.push_right_in,
				R.anim.push_right_out);
	}
}
