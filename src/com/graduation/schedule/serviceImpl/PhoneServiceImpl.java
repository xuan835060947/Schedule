package com.graduation.schedule.serviceImpl;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.Phone;
import com.graduation.schedule.bean.Time;
import com.graduation.schedule.service.PhoneService;
import com.graduation.schedule.serviceImpl.util.DataUtil;
import com.graduation.schedule.serviceImpl.util.MySQLiteOpenHelper;


public class PhoneServiceImpl implements PhoneService {

	private DataUtil dataUtil = new DataUtil();
	static SQLiteDatabase database;
	private MySQLiteOpenHelper helper;
	
	public PhoneServiceImpl(Context context) {
		// TODO Auto-generated constructor stub
		//创建数据存放位置
		helper = new MySQLiteOpenHelper(context,"schedule.db3",null,1);
	}
	
	@Override
	public boolean updatePhone(Phone phone) {
		// TODO Auto-generated method stub
		try{
		String sql = "insert into phone(reason,startTime,endTime,phoneNumber)" +
				"values(?,?,?,?) " ;
		helper.getWritableDatabase().execSQL(sql, new String[]{
				phone.getReason(),dataUtil.now(),
				dataUtil.timeToString(phone.getEndTime()),phone.getPhoneNumber()});
		return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<BaseBean> queryPhone(Phone phone) {
		// TODO Auto-generated method stub
		try{
		if(phone != null){
			String sql = "select * from phone where(_id = ? )";
			Cursor cursor = helper.getReadableDatabase().rawQuery(sql,
					new String[]{String.valueOf(phone.getId())});
			return dataUtil.queryToListPhone(cursor);
		}else{
			String sql = "select * from phone ";
			Cursor cursor = helper.getReadableDatabase().rawQuery(sql,
					new String[]{});
			return dataUtil.queryToListPhone(cursor);
		}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean deletePhone(Phone phone) {
		// TODO Auto-generated method stub
		try{
		String sql = "delete from phone where(_id = ?)";
		helper.getWritableDatabase().execSQL(sql, new String[]{String.valueOf(phone.getId())});
		return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<BaseBean> queryHis() {
		List<BaseBean> list = queryPhone(null);
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
		List<BaseBean> list = queryPhone(null);
		for(int i =0 ;list!=null&&i<list.size();i++){
			Time time = list.get(i).getEndTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.YEAR, time.getYear());
			calendar.set(Calendar.MONTH, time.getMonth()-1);
			calendar.set(Calendar.DAY_OF_MONTH, time.getDay());
			calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
			calendar.set(Calendar.MINUTE, time.getMinute());

			//当前信息
			if (calendar.getTimeInMillis() < System.currentTimeMillis()){
				list.remove(i);
			}
					
					
		}
		
		return list;
	}
	
}
