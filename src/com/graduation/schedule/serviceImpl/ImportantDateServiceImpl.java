package com.graduation.schedule.serviceImpl;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.ImportantDate;
import com.graduation.schedule.bean.Time;
import com.graduation.schedule.service.ImportantDateService;
import com.graduation.schedule.serviceImpl.util.DataUtil;
import com.graduation.schedule.serviceImpl.util.MySQLiteOpenHelper;

public class ImportantDateServiceImpl implements ImportantDateService {

	private DataUtil dataUtil = new DataUtil();
	static SQLiteDatabase database;
	private MySQLiteOpenHelper helper;

	public ImportantDateServiceImpl(Context context) {
		// TODO Auto-generated constructor stub
		helper = new MySQLiteOpenHelper(context, "schedule.db3", null, 1);
	}

	@Override
	public boolean updateImportantDate(ImportantDate importantDate) {
		// TODO Auto-generated method stub
		try {
			String sql = "insert into importantdate(reason,startTime,endTime,whatDate)"
					+ "values(?,?,?,?) ";
			helper.getWritableDatabase().execSQL(
					sql,
					new String[] { importantDate.getReason(), dataUtil.now(),
							dataUtil.timeToString(importantDate.getEndTime()),
							importantDate.getWhatDate() });
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<BaseBean> queryImportantDate(ImportantDate importantDate) {
		// TODO Auto-generated method stub
		try {
			if (importantDate != null) {
				String sql = "select * from importantDate where(_id = ? )";
				Cursor cursor = helper.getReadableDatabase().rawQuery(sql,
						new String[] { String.valueOf(importantDate.getId()) });
				return dataUtil.queryToListImportantDate(cursor);
			} else {
				String sql = "select * from importantDate ";
				Cursor cursor = helper.getReadableDatabase().rawQuery(sql,
						new String[] {});
				return dataUtil.queryToListImportantDate(cursor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteImportantDate(ImportantDate importantDate) {
		try {
			String sql = "delete from importantdate where(_id = ?)";
			helper.getWritableDatabase().execSQL(sql,
					new String[] { String.valueOf(importantDate.getId()) });
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<BaseBean> queryHis() {
		// TODO Auto-generated method stub
		List<BaseBean> list = queryImportantDate(null);
		for(int i =0 ;list!=null&&i<list.size();i++){
			Time time = list.get(i).getEndTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.YEAR, time.getYear());
			calendar.set(Calendar.MONTH, time.getMonth()-1);
			calendar.set(Calendar.DAY_OF_MONTH, time.getDay());
			calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
			calendar.set(Calendar.MINUTE, time.getMinute());

			//历史信息,时间较小,删除时间大的
			if (calendar.getTimeInMillis() > System.currentTimeMillis()){
				list.remove(i);
			}
					
					
		}
		
		return list;
	}

	@Override
	public List<BaseBean> queryCur() {
		// TODO Auto-generated method stub
		List<BaseBean> list = queryImportantDate(null);
		for(int i =0 ;list!=null&&i<list.size();i++){
			Time time = list.get(i).getEndTime();
			//将endTime转为Calendar
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.YEAR, time.getYear());
			calendar.set(Calendar.MONTH, time.getMonth()-1);
			calendar.set(Calendar.DAY_OF_MONTH, time.getDay());
			calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
			calendar.set(Calendar.MINUTE, time.getMinute());

			//当前信息,删除时间小的
			if (calendar.getTimeInMillis() < System.currentTimeMillis()){
				list.remove(i);
			}
					
					
		}
		
		return list;
	}

}
