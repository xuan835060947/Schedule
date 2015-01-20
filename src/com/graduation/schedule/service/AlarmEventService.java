package com.graduation.schedule.service;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.serviceImpl.ImportantDateServiceImpl;
import com.graduation.schedule.serviceImpl.ListingServiceImpl;
import com.graduation.schedule.serviceImpl.PhoneServiceImpl;
import com.graduation.schedule.serviceImpl.ShortMessageServiceImpl;


public class AlarmEventService extends Service {

	String tag = "AlarmEventService";
	Gson gson = new Gson();
	static final int PHONE = 1;
	static final int SHORT_MESSAGE =2;
	static final int LISTING = 3;
	static final int IMPORTANT_DATE =4;
	public static int notificationNum = 123;
	public static boolean running = false;
	public AlarmEventService() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		running = true;
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.v(tag, "onStartCommand");
		getSql();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.v(tag, "service Destroy");
		running = false;
		super.onDestroy();
	}
	
	public void getSql(){
		Log.v(tag, "getSql");
		
		
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				PhoneService ps= new PhoneServiceImpl(AlarmEventService.this);
				ShortMessageService sms = new ShortMessageServiceImpl(AlarmEventService.this);
				ImportantDateService ids = new ImportantDateServiceImpl(AlarmEventService.this);
				ListingService ls = new ListingServiceImpl(AlarmEventService.this);
				
				final List<BaseBean> lp = ps.queryPhone(null);
				final List<BaseBean> lsm = sms.queryShortMessage(null);
				final List<BaseBean> lid = ids.queryImportantDate(null);
				final List<BaseBean> ll = ls.queryListing(null);
				
				alartList(lp);
				alartList(lsm);
				alartList(lid);
				alartList(ll);
				
			}
		}.start();
				
		
	}
	
	public void alartList(List list){
		int style=0;
		if(list.size() != 0){
			style = ((BaseBean)(list.get(0))).getStyle();
		}
		
		for(int i =0 ;i<list.size();i++){
//			Log.v(tag,gson.toJson(list.get(i)));
			Bundle bundle = new Bundle();
			bundle.putInt("notification", 1);
			bundle.putInt("style",  style);
			bundle.putString( "bean",gson.toJson(list.get(i)) );
			bundle.putInt("id", notificationNum);
			notificationNum++;
			
			AlarmService as = new AlarmService(AlarmEventService.this,MyNotificationService.class,bundle);
			as.startIn(((BaseBean)(list.get(i))).getEndTime());
		}
	}
	
}
