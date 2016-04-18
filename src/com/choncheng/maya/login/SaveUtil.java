package com.choncheng.maya.login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SaveUtil {
 
   public static void save(Context context,String name,String title,String content){
	 //实例化SharedPreferences对象（第一步） 
	   SharedPreferences mySharedPreferences= context.getSharedPreferences(name, 
	   Activity.MODE_PRIVATE); 
	   //实例化SharedPreferences.Editor对象（第二步） 
	   SharedPreferences.Editor editor = mySharedPreferences.edit(); 
	   //用putString的方法保存数据 
	   editor.putString(title, content); 
	 
	   //提交当前数据 
	   editor.commit(); 
   }
   public static String get(Context  context,String name,String title){
	 //实例化SharedPreferences对象（第一步） 
	   SharedPreferences mySharedPreferences= context.getSharedPreferences(name, 
	   Activity.MODE_PRIVATE); 
	   
	   return mySharedPreferences.getString(title, "no");
	  
   }
 

}
