package com.graduation.schedule.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.util.Log;

import com.graduation.schedule.bean.Time;

public class TimeUtil {
	public static Time getEndTime(int hour, int minute) {

		Time endTime = new Time();
		endTime.setDay(endTime.getDay());
		endTime.setHour(endTime.getHour() + hour);
		endTime.setMinute(endTime.getMinute() + minute);
		if (endTime.getMinute() >= 60) {
			endTime.setMinute(endTime.getMinute() - 60);
			endTime.setHour(endTime.getHour() + 1);
		}

		if (endTime.getHour() >= 24) {
			endTime.setHour(endTime.getHour() - 24);
			endTime.setDay(endTime.getDay() + 1);
		}
		return endTime;
	}

	public static String getTimeStr(Calendar calendar) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		return df.format(calendar.getTime());
	}

	public static String getDateStr(Time time) {
		StringBuffer timeStr = new StringBuffer();
		timeStr.append(time.getYear() + "-");

		if (time.getMonth() < 10)
			timeStr.append("0");
		timeStr.append(time.getMonth() + "-");

		if (time.getDay() < 10)
			timeStr.append("0");
		timeStr.append(time.getDay());

		return timeStr.toString();
	}

	public static boolean afterNow(Time time) {

		Time curTime = new Time();
		if (curTime.getYear() > time.getYear())
			return false;
		else if (curTime.getMonth() > time.getMonth())
			return false;
		else if (curTime.getDay() > time.getDay())
			return false;
		else if (curTime.getHour() > time.getHour())
			return false;
		else if (curTime.getMinute() > curTime.getMinute())
			return false;
		else
			return true;
	}
}
