package com.choncheng.maya.login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SaveUtil {
 
   public static void save(Context context,String name,String title,String content){
	 //ʵ����SharedPreferences���󣨵�һ���� 
	   SharedPreferences mySharedPreferences= context.getSharedPreferences(name, 
	   Activity.MODE_PRIVATE); 
	   //ʵ����SharedPreferences.Editor���󣨵ڶ����� 
	   SharedPreferences.Editor editor = mySharedPreferences.edit(); 
	   //��putString�ķ����������� 
	   editor.putString(title, content); 
	 
	   //�ύ��ǰ���� 
	   editor.commit(); 
   }
   public static String get(Context  context,String name,String title){
	 //ʵ����SharedPreferences���󣨵�һ���� 
	   SharedPreferences mySharedPreferences= context.getSharedPreferences(name, 
	   Activity.MODE_PRIVATE); 
	   
	   return mySharedPreferences.getString(title, "no");
	  
   }
 

}
