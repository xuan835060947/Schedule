package com.graduation.schedule.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.CallLog;

import com.graduation.schedule.bean.Call;

public class CallUtil {
	public static List<Call> getCalls(ContentResolver cr) {

		List<Call> calls = new ArrayList<Call>();
		Call call = null;
		Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, null, null, null,
				null);

		if (cursor.getCount() <= 0) {
			return null;
		}

		cursor.moveToFirst();
		do {
			call = new Call();

			/* Reading Name */
			String nameTemp = cursor.getString(cursor
					.getColumnIndex(CallLog.Calls.CACHED_NAME));
			if (nameTemp == null) {
				nameTemp = "";
			}

			if ("".equals(nameTemp)) {
				call.setName("");
			} else {
				call.setName(nameTemp);
			}
			/* Reading Date */
			call.setDate(cursor.getLong(cursor
					.getColumnIndex(CallLog.Calls.DATE)));

			/* Reading duration */
			call.setDuration(cursor.getLong(cursor
					.getColumnIndex(CallLog.Calls.DURATION)));

			/* Reading Date */
			call.setType(cursor.getInt(cursor
					.getColumnIndex(CallLog.Calls.TYPE)));

			call.setPhoneNumber(cursor.getString(cursor
					.getColumnIndex(CallLog.Calls.NUMBER)));

			calls.add(call);
		} while (cursor.moveToNext());

		return calls;
	}

}
