package com.choncheng.maya.personal;

import android.os.Bundle;
import android.widget.TextView;

import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.utils.AppInfoUtil;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�XieyiActivity
 * @��������Э��
 * @�����ˣ��
 * @����ʱ�䣺2015-8-8 ����8:31:52
 * @�汾�ţ�v1.0
 * 
 */
public class XieyiActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_xieyi_activity);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.xieyi, true);
	};
}
