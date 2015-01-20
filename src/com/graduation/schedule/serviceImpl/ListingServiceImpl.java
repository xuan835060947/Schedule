package com.graduation.schedule.serviceImpl;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.Listing;
import com.graduation.schedule.bean.Time;
import com.graduation.schedule.service.ListingService;
import com.graduation.schedule.serviceImpl.util.DataUtil;
import com.graduation.schedule.serviceImpl.util.MySQLiteOpenHelper;

public class ListingServiceImpl implements ListingService{

	private DataUtil dataUtil = new DataUtil();
	static SQLiteDatabase database;
	private MySQLiteOpenHelper helper;
	static Gson gson = new Gson();
	
	public ListingServiceImpl(Context context) {
		// TODO Auto-generated constructor stub
		helper = new MySQLiteOpenHelper(context,"schedule.db3",null,1);
	}
	
	@Override
	public boolean updateListing(Listing listing) {
		// TODO Auto-generated method stub
		try{
			String sql = "insert into listing(reason,startTime,endTime,willDoList)" +
					"values(?,?,?,?) " ;
			helper.getWritableDatabase().execSQL(sql, new String[]{
					listing.getReason(),dataUtil.now(),
					dataUtil.timeToString(listing.getEndTime()),gson.toJson(listing.getWillDoList())});
			return true;
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public List<BaseBean> queryListing(Listing listing) {
		// TODO Auto-generated method stub
		try {
			if (listing != null) {
				String sql = "select * from listing where(_id = ? )";
				Cursor cursor = helper.getReadableDatabase().rawQuery(sql,
						new String[] { String.valueOf(listing.getId()) });
				return dataUtil.queryToListListing(cursor);
			} else {
				String sql = "select * from listing ";
				Cursor cursor = helper.getReadableDatabase().rawQuery(sql,
						new String[] {});
				return dataUtil.queryToListListing(cursor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteListing(Listing listing) {
		// TODO Auto-generated method stub
		try{
			String sql = "delete from listing where(_id = ?)";
			helper.getWritableDatabase().execSQL(sql, new String[]{String.valueOf(listing.getId())});
			return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public List<BaseBean> queryHis() {
		List<BaseBean> list = queryListing(null);
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
		List<BaseBean> list = queryListing(null);
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


