package com.choncheng.maya.login;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.BaseActivity;
import com.choncheng.maya.MainActivity;
import com.choncheng.maya.R;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.comm.entity.Advertisement;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.homepage.HomePageActivity;
import com.choncheng.maya.shoppingcart.GoodsActivity;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.CommUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class WelcomeActivity extends BaseActivity {
	AutoScrollViewPager viewPager;
	ImageView yonghu_img;
	TextView  tv_tiaoguo;
    private int[] ids = {R.drawable.introbg11x,R.drawable.introbg22,R.drawable.introbg33};
    private int[] ids2 = {R.drawable.startimg};
    String is="123";
    private List<View> guides = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		intial();

	}

	private void intial() {
		// TODO Auto-generated method stub
		viewPager = (AutoScrollViewPager) findViewById(R.id.contentPager);
		yonghu_img = (ImageView) findViewById(R.id.yonghu_img);
		tv_tiaoguo = (TextView) findViewById(R.id.tv_tiaoguo);

		viewPager.setInterval(5000);
		viewPager.setCycle(false);
		viewPager.setStopScrollWhenTouch(true);
		viewPager.setBorderAnimation(true);
		initADData();
	 
		  viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				 if (arg0 == ids.length - 1) {// 到最后一张了
					 new Handler().postDelayed (new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
					            viewPager.stopAutoScroll();

								Intent it = new Intent(WelcomeActivity.this,
										MainActivity.class);
							 
								startActivity(it);
								 
									//overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
									//Android内置的
									 overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
								 finish();
								 }
						}, 3000);
	                } 
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	 /**
     * 初始化广告信息
     */
    private void initADData(){
     //  is=SaveUtil.get(this, "welcome", "ok");
        if(!is.equals("ok")){
        	tv_tiaoguo.setVisibility(View.VISIBLE);
        	tv_tiaoguo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
		            SaveUtil.save(WelcomeActivity.this, "welcome", "ok","ok");
		            viewPager.stopAutoScroll();
		            Intent it = new Intent(WelcomeActivity.this,
							MainActivity.class);
				 
					startActivity(it);
					 
						//overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						//Android内置的
						 overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
					 finish();
				}
			});
            for (int id:ids) {
                ImageView iv = new ImageView(this);
                iv.setBackgroundResource(id);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                iv.setLayoutParams(params);
                iv.setScaleType(ImageView.ScaleType.CENTER);
                guides.add(iv);
                
            }
            SaveUtil.save(WelcomeActivity.this, "welcome", "ok","ok");
        }else{
            for (int id:ids2) {
                ImageView iv = new ImageView(WelcomeActivity.this);
                iv.setBackgroundResource(id);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                iv.setLayoutParams(params);
                iv.setScaleType(ImageView.ScaleType.CENTER);
                guides.add(iv);
            }
        }
        if(guides.size()>1){
    		//加载首页的binner图
    		adlistsLists(API.ADLISTS_BANNER);
    		adlistsLists(API.ADLISTS_RECOMMEND);
    		adlistsLists(API.ADLISTS_BOTTOM);
    		//引导页
            AllViewPagerAdapter adapter = new AllViewPagerAdapter(this,guides);
            viewPager.setAdapter(adapter);
            viewPager.startAutoScroll();
        }else{
            AllViewPagerAdapter adapter = new AllViewPagerAdapter(this,guides);
            viewPager.setAdapter(adapter);
            //2015.12.17 禁止
            //yonghu_img.setImageResource(R.drawable.yujian);
			//Animation  mAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_touming); 

            /**播放透明动画**//*
            yonghu_img.startAnimation(mAnimation);*/  
			 new Handler().postDelayed (new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent it = new Intent(WelcomeActivity.this,
							MainActivity.class);
				 
					startActivity(it);
					 
						//overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						//Android内置的
						 overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
					 finish();
					 }
			}, 500);
           // handler.sendEmptyMessage(FinalVarible.GETDATA);
        }
    }
    
    //========================>
    /**
	 * 获取广告
	 * 
	 * @param type_id
	 *            1:首页头部广告位 2： 中部广告位 3尾部广告位
	 */
	private void adlistsLists(final int type_id) {
		API.adlistsLists(type_id, new ResponseHandler() {

			@Override
			public void onRese(final String data, int state) {
				if (state == STATE_OK) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							List<Advertisement> adList = JSON.parseArray(data,
									Advertisement.class);
							for (Advertisement ad:adList) {
								ImageLoader.getInstance().loadImageSync(CommUtils
										.getImageUrl(ad.getAd_image()), 
										BitmapUtil.getBannerOption());
							}
							
						}
					}).start();
					
				}
			}
		});
		//<==初始化binner图
	}

}
