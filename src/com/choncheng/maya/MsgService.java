package com.choncheng.maya;

 
import java.util.List;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import com.alibaba.fastjson.JSON;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.comm.entity.User;
import com.choncheng.maya.db.MessageDBHelper;
import com.choncheng.maya.homepage.HomePageActivity;
import com.choncheng.maya.personal.PersonalCenterMsg;
import com.choncheng.maya.utils.LocalSettings;

/**
 * Created by Administrator on 2015/11/19.
 */
public class MsgService extends Service {
	  public static final String ACTION = "com.choucheng.ccoa.service.PollingService";
		protected User mUser = null;
	 
 	    private NotificationManager mManager;
	    private String msg = null;
	    private String  newmsg ;
	    @Override
	    public IBinder onBind(Intent intent) {
	        return null;
	    }

	    @Override
	    public void onCreate() {

	    }

	    @Override
	    public void onStart(Intent intent, int startId) {
	    	getMsg();
	    }

	    public void getMsg(){
	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	                while(true){
	                    try {
	                        Thread.sleep(5000);
							mUser = MyApplication.getInstance().getUser();
							if (mUser != null) {
								leavemsgMsg(mUser.getUcode(), "");
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                    
	                }
	            }
	        }).start();
	    }

	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	     }

		private void leavemsgMsg(String ucode, String shop_id) {
			API.leavemsgMsg(getApplicationContext(), ucode, shop_id, new ResponseHandler() {

				@Override
				public void onRese(String data, int state) {
 
 					if (state == STATE_OK) {
						// 获取到消息列表

						List<PersonalCenterMsg> msgList = JSON.parseArray(data,
								PersonalCenterMsg.class);

						if (msgList != null) {
							for (int i = 0; i < msgList.size(); i++) {
								PersonalCenterMsg msg = msgList.get(i);
								// 1：用户发个商家的 2：商家发给用户的
								msg.setU_to_s("2");
							}

							if (!msgList.isEmpty()) {
 								LocalSettings.putBoolean(LocalSettings.KEY_NEW_MSG,
										true);
								MessageDBHelper dbHelper = new MessageDBHelper(
										getApplicationContext());
								dbHelper.saveMessage(msgList);
								EventBus.getDefault().post("",API.REFRESH_MESSAGE);
							} 
						}
					}

				}
			});
		}
}
