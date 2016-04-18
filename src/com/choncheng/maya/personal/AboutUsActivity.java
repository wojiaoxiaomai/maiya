package com.choncheng.maya.personal;

import android.os.Bundle;
import android.widget.TextView;

import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.R;
import com.choncheng.maya.SetTopView;
import com.choncheng.maya.utils.AppInfoUtil;

/**
 * 
 * @项目名称：Maya
 * @类名称：AboutUsActivity
 * @类描述： 关于我们
 * @创建人：李波
 * @创建时间：2015-8-8 上午7:56:09
 * @版本号：v1.0
 * 
 */
public class AboutUsActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_aboutus_activity);
		initView();
	}

	@Override
	protected void initView() {
		super.initView();
		new SetTopView(this, R.string.about_us, true);
		
	};
}
