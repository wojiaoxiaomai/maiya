package com.choncheng.maya.utils;

import android.app.Activity;

import com.choncheng.maya.R;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�AnimUtil
 * @�������� �����л�
 * @�����ˣ��
 * @����ʱ�䣺2015-8-7 ����10:49:33
 * @�汾�ţ�v1.0
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
