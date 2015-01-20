package com.graduation.schedule.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.graduation.schedule.R;
import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.service.AlarmEventService;
import com.graduation.schedule.service.AlarmService;
import com.graduation.schedule.service.MyNotificationService;
import com.graduation.schedule.util.GsonUtil;

public abstract class AbstractBaseActivity extends Activity {
	protected View vReturn;
	protected View vSave;
	protected TextView tvTitle;
	protected OnClickListener v_rtn_on, v_save_on;
	protected BaseBean bb;

	protected void setup() {

		tvTitle = (TextView) findViewById(R.id.title_name);
		vReturn = findViewById(R.id.title_return);
		vSave = findViewById(R.id.title_other);
		v_rtn_on = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				back();
			}
		};

		v_save_on = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				save();
			}
		};
		vReturn.setOnClickListener(v_rtn_on);
		vSave.setOnClickListener(v_save_on);
	}

	protected void startService() {

		Bundle bundle = new Bundle();
		bundle.putInt("notification", 1);
		bundle.putInt("style", bb.getStyle());
		bundle.putString("bean", GsonUtil.gson.toJson(bb));
		bundle.putInt("id", ++AlarmEventService.notificationNum);
		AlarmService as = new AlarmService(this, MyNotificationService.class,
				bundle);
		as.startIn(bb.getEndTime());
	}

	protected abstract void save();

	protected abstract void back();
}
