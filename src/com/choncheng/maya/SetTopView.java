package com.choncheng.maya;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.choncheng.maya.utils.AnimUtil;

/**
 * 
 * @��Ŀ���ƣ�
 * @�����ƣ�SetTopView
 * @��������ͷ����������ʽ
 * @�����ˣ��
 * @����ʱ�䣺2014-9-24 ����3:11:39
 * @�汾�ţ�v1.0
 * 
 */
public class SetTopView implements OnClickListener {
	private Activity mActivity;
	private TextView mTitle;
	private ImageView mBackView;

	public SetTopView(Activity activity, String title_text_string) {
		mActivity = activity;
		mTitle = (TextView) mActivity.findViewById(R.id.title_text);
		mBackView = (ImageView) mActivity.findViewById(R.id.back_img);
		mTitle.setText(title_text_string);
		mBackView.setOnClickListener(this);
	}

	public SetTopView(Activity activity, int title_text_id) {
		mActivity = activity;
		mTitle = (TextView) mActivity.findViewById(R.id.title_text);
		mBackView = (ImageView) mActivity.findViewById(R.id.back_img);
		mTitle.setText(activity.getResources().getString(title_text_id));
		mBackView.setOnClickListener(this);
	}

	/**
	 * 
	 * @param activity
	 * @param title_text_id
	 * @param backisvisible
	 *            ���ؼ��Ƿ�ɼ�
	 */
	public SetTopView(Activity activity, int title_text_id,
			boolean backisvisible) {
		mActivity = activity;
		mTitle = (TextView) mActivity.findViewById(R.id.title_text);
		mBackView = (ImageView) mActivity.findViewById(R.id.back_img);
		mTitle.setText(activity.getResources().getString(title_text_id));
		mBackView.setOnClickListener(this);
		mBackView.setVisibility(backisvisible ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.back_img) {
			mActivity.finish();
			AnimUtil.setFromRightToLeft(mActivity);
		}

	}

}
