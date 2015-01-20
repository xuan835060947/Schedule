package com.graduation.schedule.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.graduation.schedule.R;
import com.graduation.schedule.bean.Listing;
import com.graduation.schedule.component.AddToDoListCompontent;
import com.graduation.schedule.component.CheckCompontent;
import com.graduation.schedule.service.ListingService;
import com.graduation.schedule.serviceImpl.ListingServiceImpl;
import com.graduation.schedule.util.DataBaseUtil;
import com.graduation.schedule.util.TimeUtil;
import com.graduation.schedule.util.ToastUtil;

public class ListActivity extends AbstractSecondActivity {

	private AddToDoListCompontent add_todolist_c;
	private EditText et_remark;
	private ListingService ls;
	private CheckCompontent mcc;
	private CheckCompontent.CheckItem cci_shop;
	private CheckCompontent.CheckItem cci_todo;

	private CheckCompontent.CheckItem cci_take;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		super.setup(this);
		add_todolist_c = (AddToDoListCompontent) findViewById(R.id.add_todolist_c);
		setChild();
		et_remark = (EditText) findViewById(R.id.et_remark);
	}

	@Override
	protected void save() {
		// TODO Auto-generated method stub
		day = Integer.valueOf(et_day.getText().toString());
		if (day == 0 && hour == 0 && minute == 0) {
			ToastUtil.show(this, "请输入时间！");
		} else {
			if (add_todolist_c.getWillDoList().size() == 0)
				ToastUtil.show(this, "请输入具体事项");
		}
		Listing listing = new Listing();
		listing.setWillDoList(add_todolist_c.getWillDoList());
		if (et_remark.getText().toString() != null)
			listing.setReason(et_remark.getText().toString());
		listing.setEndTime(TimeUtil.getEndTime(hour, minute));
		ls = new ListingServiceImpl(this);
		Log.v("Listing", new Gson().toJson(listing));
		bb = listing;
		if (ls.updateListing(listing)) {
			DataBaseUtil.notifyCur();
			startService();
			ToastUtil.show(this, "添加成功！");
			finish();
		} else {
			ToastUtil.show(this, "系统异常!");
		}
	}

	@Override
	protected void back() {
		// TODO Auto-generated method stub
		finish();
	}

	private void setChild() {
		mcc = (CheckCompontent) findViewById(R.id.title_list);
		cci_shop = mcc.new CheckItem(this, getResources().getDrawable(
				R.drawable.shopping_selector),
				getString(R.string.shopping_list)) {

			@Override
			public void check() {
				// TODO Auto-generated method stub
				add_todolist_c.setHint("要买什么...");
			}
		};
		cci_todo = mcc.new CheckItem(this, getResources().getDrawable(
				R.drawable.todo_selector), getString(R.string.todolist)) {

			@Override
			public void check() {
				// TODO Auto-generated method stub
				add_todolist_c.setHint("要做什么...");
			}
		};
		cci_take = mcc.new CheckItem(this, getResources().getDrawable(
				R.drawable.take_selector), getString(R.string.take)) {

			@Override
			public void check() {
				// TODO Auto-generated method stub
				add_todolist_c.setHint("要带什么...");

			}
		};
		mcc.addView(cci_shop.getItem());
		mcc.addView(cci_todo.getItem());
		mcc.addView(cci_take.getItem());
		cci_todo.selected(cci_todo.getItem());
		add_todolist_c.setHint("要做什么...");
	}

}
