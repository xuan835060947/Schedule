package com.graduation.schedule.serviceImpl.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.ImportantDate;
import com.graduation.schedule.bean.Listing;
import com.graduation.schedule.bean.Note;
import com.graduation.schedule.bean.Phone;
import com.graduation.schedule.bean.ShortMessage;
import com.graduation.schedule.bean.Time;

public class DataUtil {
	static Gson gson = new Gson();

	@SuppressWarnings("deprecation")
	public Timestamp timeToTimeStamp(Time time) {
		return new Timestamp(time.getYear(), time.getMonth(), time.getDay(),
				time.getHour(), time.getMinute(), 0, 0);
	}

	public List<BaseBean> queryToListPhone(Cursor cursor) {
		List<BaseBean> list = new ArrayList<BaseBean>();
		while (cursor.moveToNext()) {
			Phone phone = new Phone();
			// 前四行是basebean内容,顺序是id、reason、starttime、endtime
			cursorToBaseBean(phone, cursor);
			phone.setPhoneNumber(cursor.getString(4));
			list.add(phone);
		}
		return list;
	}

	public List<BaseBean> queryToListShortMessage(Cursor cursor) {
		List<BaseBean> list = new ArrayList<BaseBean>();
		while (cursor.moveToNext()) {
			ShortMessage shortMessage = new ShortMessage();
			cursorToBaseBean(shortMessage, cursor);
			shortMessage.setMessage(cursor.getString(4));
			shortMessage.setPhoneNumber(cursor.getString(5));
			list.add(shortMessage);
		}
		return list;
	}

	public List<BaseBean> queryToListListing(Cursor cursor) {
		List<BaseBean> list = new ArrayList<BaseBean>();
		while (cursor.moveToNext()) {
			Listing listing = new Listing();
			cursorToBaseBean(listing, cursor);
			List<String> gsonList = gson.fromJson(cursor.getString(4),
					new TypeToken<List<String>>() {
					}.getType());
			listing.setWillDoList(gsonList);
			list.add(listing);
		}
		return list;
	}

	public List<BaseBean> queryToListImportantDate(Cursor cursor) {
		List<BaseBean> list = new ArrayList<BaseBean>();
		while (cursor.moveToNext()) {
			ImportantDate importantDate = new ImportantDate();
			cursorToBaseBean(importantDate, cursor);
			importantDate.setWhatDate(cursor.getString(4));

			list.add(importantDate);
		}
		return list;
	}

	public List<BaseBean> queryToListNote(Cursor cursor) {
		List<BaseBean> list = new ArrayList<BaseBean>();
		while (cursor.moveToNext()) {

			Note note = new Note();
			cursorToBaseBean(note, cursor);
			note.setTitle(cursor.getString(4));
			note.setContent(cursor.getString(5));
			list.add(note);
		}
		return list;
	}

	public void cursorToBaseBean(BaseBean baseBean, Cursor cursor) {

		baseBean.setId(cursor.getInt(0));
		baseBean.setReason(cursor.getString(1));
		baseBean.setStartTime(timestringToTime(cursor.getString(2)));
		baseBean.setEndTime(timestringToTime(cursor.getString(3)));

	}

	public Time timestringToTime(String timestampString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date;
		try {
			date = sdf.parse(timestampString);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			Time time = new Time();
			time.setYear(calendar.get(Calendar.YEAR));
			time.setMonth(calendar.get(Calendar.MONTH) + 1);
			time.setDay(calendar.get(Calendar.DAY_OF_MONTH));
			time.setHour(calendar.get(Calendar.HOUR_OF_DAY));
			time.setMinute(calendar.get(Calendar.MINUTE));
			Log.v("dataUtil", new Gson().toJson(time));
			return time;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public String now() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateformat.format(new Date());
	}

	public static String timeToString(Time time) {

		String str = String.valueOf(time.getYear()) + "-"
				+ String.valueOf(time.getMonth()) + "-"
				+ String.valueOf(time.getDay()) + " "
				+ String.valueOf(time.getHour()) + ":"
				+ String.valueOf(time.getMinute());
		return str;
	}
}
