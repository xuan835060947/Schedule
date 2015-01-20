package com.graduation.schedule.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.graduation.schedule.R;
import com.graduation.schedule.activity.MainActivity;
import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.ImportantDate;
import com.graduation.schedule.bean.Listing;
import com.graduation.schedule.bean.Phone;
import com.graduation.schedule.bean.ShortMessage;
import com.graduation.schedule.bean.Time;

public class MyNotificationService extends Service {

	String tag = "MyNotificationService";
	Gson gson = new Gson();
	static final int PHONE = 1;
	static final int SHORT_MESSAGE =2;
	static final int LISTING = 3;
	static final int IMPORTANT_DATE =4;
	String contentText;
	Bundle bundle ;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.v(tag, "onCreate");
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.v(tag, "onStartCommand");
		bundle = intent.getExtras();
		int style = bundle.getInt("style");
		int id = bundle.getInt("id");
		String json = bundle.getString("bean");
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		notificationBaseBeanData(MainActivity.class,sortJson(json,style),
				id,contentText);
		return super.onStartCommand(intent, flags, startId);
	}
	
	@SuppressLint("NewApi")
	public void notificationBaseBeanData(Class<?> cls,BaseBean baseBean,int id,String contentText){
		try{
		NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
			Intent intent = new Intent(MyNotificationService.this,cls);
			intent.putExtras(bundle);
			PendingIntent pIntent = PendingIntent.getActivity(MyNotificationService.this,
					0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
			Calendar calendar = Calendar.getInstance();
			Time endTime=baseBean.getEndTime();
			calendar.set(Calendar.YEAR, endTime.getYear());
			calendar.set(Calendar.MONTH, endTime.getMonth()-1);
			calendar.set(Calendar.DAY_OF_MONTH, endTime.getDay());
			calendar.set(Calendar.HOUR_OF_DAY, endTime.getHour());
			calendar.set(Calendar.MINUTE, endTime.getMinute());
			
			Log.v("start alarm", "star   ______________start");
			Log.v("start ", String.valueOf(calendar.getTimeInMillis()));
			Log.v("start ", String.valueOf(new Date().getTime() ));
			Log.v("start ", String.valueOf(System.currentTimeMillis() ));
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Log.v(tag, df.format(new Date().getTime()));
			Log.v(tag, df.format(calendar.getTime()));
			Notification notification = new Notification.Builder(this)
			.setAutoCancel(true)
			.setTicker("消息")
			.setSmallIcon(R.drawable.logo)
			.setContentTitle(baseBean.getReason())
			.setContentText(contentText)
			.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS)
			.setWhen(calendar.getTimeInMillis())
			.setContentIntent(pIntent)
			.build();
			Log.v(tag, "id = " + String.valueOf(id));
		nm.notify(id, notification);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public BaseBean sortJson(String json,int style){
		switch(style){
		case PHONE:
			Phone phone = gson.fromJson(json, Phone.class);
			contentText ="记得打电话给" + phone.getPhoneNumber();
			return phone;
		case SHORT_MESSAGE:
			ShortMessage shortMessage = gson.fromJson(json, ShortMessage.class);
			contentText = "记得发短信给:" + shortMessage.getPhoneNumber();
			return shortMessage;
		case IMPORTANT_DATE:
			ImportantDate importantDate = gson.fromJson(json, ImportantDate.class);
			contentText = "有重要的日子:" + importantDate.getWhatDate();
			return importantDate;
		case LISTING:
			Listing listing = gson.fromJson(json, Listing.class);
			contentText = "清单提醒,有许多事情需要做哦";
			return listing;
		default:return null;
		}
	}
}
