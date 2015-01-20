package com.graduation.schedule.serviceImpl;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.Note;
import com.graduation.schedule.service.NoteService;
import com.graduation.schedule.serviceImpl.util.DataUtil;
import com.graduation.schedule.serviceImpl.util.MySQLiteOpenHelper;
import com.graduation.schedule.util.GsonUtil;

public class NoteServiceImpl implements NoteService {
	private DataUtil dataUtil = new DataUtil();
	static SQLiteDatabase database;
	private MySQLiteOpenHelper helper;

	public NoteServiceImpl(Context context) {

		helper = new MySQLiteOpenHelper(context, "schedule.db3", null, 1);
	}

	@Override
	public boolean updateNote(Note note) {
		// TODO Auto-generated method stub
		Log.e("NoteService", GsonUtil.gson.toJson(note));
		try {
			String sql = "insert into note(reason,startTime,endTime,title, content)"
					+ "values(?,?,?,?,?) ";
			helper.getWritableDatabase().execSQL(
					sql,
					new String[] { note.getReason(), dataUtil.now(), "",
							note.getTitle(), note.getContent() });
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteNote(Note note) {
		// TODO Auto-generated method stub
		try {
			String sql = "delete from note where(_id = ?)";
			helper.getWritableDatabase().execSQL(sql,
					new String[] { String.valueOf(note.getId()) });
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<BaseBean> queryAll() {
		// TODO Auto-generated method stub
		try {

			String sql = "select * from note ";
			Cursor cursor = helper.getReadableDatabase().rawQuery(sql,
					new String[] {});
			return dataUtil.queryToListNote(cursor);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
