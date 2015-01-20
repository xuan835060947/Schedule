package com.graduation.schedule.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.graduation.schedule.bean.Time;

//到了时间后就会去打开哪个Activity
public class AlarmService {
	String tag = "AlarmService";
	AlarmManager alarm = null;
	Context context = null;
	Class<?> cls;
	Bundle bundle;

	public AlarmService(Context context, Class<?> cls, Bundle bundle) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.cls = cls;
		this.bundle = bundle;
		alarm = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);

	}

	public void startIn(Time time) {
		Intent intent = new Intent(context, MyNotificationService.class);
		intent.putExtras(bundle);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.YEAR, time.getYear());
		calendar.set(Calendar.MONTH, time.getMonth()-1);
		calendar.set(Calendar.DAY_OF_MONTH, time.getDay());
		calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
		calendar.set(Calendar.MINUTE, time.getMinute());
//		calendar.set(Calendar.SECOND, 0);
		
		
		if (calendar.getTimeInMillis() > System.currentTimeMillis()) {
			Log.v(tag, "star   ______________start");
			Log.v(tag, new Gson().toJson(time));
			Log.v("start ", String.valueOf(calendar.getTimeInMillis()));
			Log.v("start ", String.valueOf(new Date().getTime()));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Log.v(tag, df.format(calendar.getTime()));
			Log.v(tag,"id="+ String.valueOf(bundle.getInt("id")));
			PendingIntent pIntent = PendingIntent.getService(context, bundle.getInt("id"),
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
			alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
					pIntent);
		}
	}
}
