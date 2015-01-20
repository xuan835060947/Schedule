package com.graduation.schedule.bean;

import java.util.Calendar;

import android.util.Log;

import com.google.gson.Gson;

public class Time {

	private int year = 0;
	private int month = 0;
	private int day = 0;
	private int hour = 0;
	private int minute = 0;

	public Time() {
		// TODO Auto-generated constructor stub
		setTime();
	}

	public Time(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	private void setTime() {

		Calendar cal = Calendar.getInstance();
		this.year = cal.get(Calendar.YEAR);
		this.minute = cal.get(Calendar.MINUTE);
		this.day = cal.get(Calendar.DAY_OF_MONTH);
		this.hour = cal.get(Calendar.HOUR_OF_DAY);
		this.month = cal.get(Calendar.MONTH) + 1;

		Log.v("Listing", new Gson().toJson(this));
	}
}
