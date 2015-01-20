package com.graduation.schedule.serviceImpl;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.ShortMessage;
import com.graduation.schedule.bean.Time;
import com.graduation.schedule.service.ShortMessageService;
import com.graduation.schedule.serviceImpl.util.DataUtil;
import com.graduation.schedule.serviceImpl.util.MySQLiteOpenHelper;


public class ShortMessageServiceImpl implements ShortMessageService {
	private DataUtil dataUtil = new DataUtil();
	static SQLiteDatabase database;
	private MySQLiteOpenHelper helper;

	public ShortMessageServiceImpl(Context context) {
		// TODO Auto-generated constructor stub
		helper = new MySQLiteOpenHelper(context, "schedule.db3", null, 1);
	}

	@Override
	public boolean updateShortMessage(ShortMessage shortMessage) {
		// TODO Auto-generated method stub
		try {
			String sql = "insert into shortMessage(reason,startTime,endTime,message,phoneNumber)"
					+ "values(?,?,?,?,?) ";
			helper.getWritableDatabase().execSQL(
					sql,
					new String[] { shortMessage.getReason(), dataUtil.now(),
							dataUtil.timeToString(shortMessage.getEndTime()),
							shortMessage.getMessage(),shortMessage.getPhoneNumber() });
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public List<BaseBean> queryShortMessage(ShortMessage shortMessage) {
		// TODO Auto-generated method stub
		try {
			if (shortMessage != null) {
				String sql = "select * from shortMessage where(_id = ? )";
				Cursor cursor = helper.getReadableDatabase().rawQuery(sql,
						new String[] { String.valueOf(shortMessage.getId()) });
				return dataUtil.queryToListShortMessage(cursor);
			} else {
				String sql = "select * from shortMessage ";
				Cursor cursor = helper.getReadableDatabase().rawQuery(sql,
						new String[] {});
				return dataUtil.queryToListShortMessage(cursor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteShortMessage(ShortMessage shortMessage) {
		try{
			String sql = "delete from shortmessage where(_id = ?)";
			helper.getWritableDatabase().execSQL(sql, new String[]{String.valueOf(shortMessage.getId())});
			return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public List<BaseBean> queryHis() {
		List<BaseBean> list = queryShortMessage(null);
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
		List<BaseBean> list = queryShortMessage(null);
		for(int i =0 ;list!=null&&i<list.size();i++){
			Time time = list.get(i).getEndTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.YEAR, time.getYear());
			calendar.set(Calendar.MONTH, time.getMonth()-1);
			calendar.set(Calendar.DAY_OF_MONTH, time.getDay());
			calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
			calendar.set(Calendar.MINUTE, time.getMinute());

			//当前信息息,时间较大,删除时间小的
			if (calendar.getTimeInMillis() < System.currentTimeMillis()){
				list.remove(i);
			}
					
					
		}
		
		return list;
	}

}
