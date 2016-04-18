package com.choncheng.maya.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MainActivity;
import com.choncheng.maya.R;
import com.choncheng.maya.api.API;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.homepage.HomePageActivity;
import com.choncheng.maya.shoppingcart.GoodsActivity;
import com.choncheng.maya.utils.AnimUtil;

public class IndexActivity  extends BaseActivity {
	ImageView  yonghu_img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index_layout);
		intial();
	
	}
	private void intial() {
		// TODO Auto-generated method stub
		yonghu_img=(ImageView) findViewById(R.id.yonghu_img);
		 /**加载透明动画**/
			Animation  mAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_touming);  

		    /**播放透明动画**/
			yonghu_img.startAnimation(mAnimation);  
			 new Handler().postDelayed (new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					String is=SaveUtil.get(IndexActivity.this, "welcome", "ok");
					 if(!is.equals("ok")){
						 Intent it = new Intent(IndexActivity.this,
									WelcomeActivity.class);
							startActivity(it);
					 }else {
						 Intent it = new Intent(IndexActivity.this,
									MainActivity.class);
							startActivity(it);
					}
					
					 
						//overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						//Android内置的
						 overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
					 finish();
					 }
			}, 3000);
		
	}

}
