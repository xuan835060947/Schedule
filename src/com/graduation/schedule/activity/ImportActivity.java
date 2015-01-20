package com.graduation.schedule.activity;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.gson.Gson;
import com.graduation.schedule.R;
import com.graduation.schedule.bean.ImportantDate;
import com.graduation.schedule.bean.Time;
import com.graduation.schedule.service.ImportantDateService;
import com.graduation.schedule.serviceImpl.ImportantDateServiceImpl;
import com.graduation.schedule.util.DataBaseUtil;
import com.graduation.schedule.util.ToastUtil;

public class ImportActivity extends AbstractBaseActivity {

	private EditText et_what_today;
	private EditText et_date;
	private EditText et_remark;
	private int year;
	private int month;
	private int day;
	private ImportantDateService ids;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_importent_date);
		super.setup();

		et_what_today = (EditText) findViewById(R.id.et_what_today);
		et_date = (EditText) findViewById(R.id.et_date);
		et_remark = (EditText) findViewById(R.id.et_remark);

		et_date.setOnClickListener(date_oc);
	}

	@Override
	protected void save() {
		// TODO Auto-generated method stub
		String str = et_what_today.getText().toString();
		if (str == null || str.equals("")) {

			ToastUtil.show(this, "那天是什么重要日子啊？");
		} else {
			ImportantDate id = new ImportantDate();
			id.setWhatDate(str);
			id.setEndTime(new Time(year, month, day));
			Log.e("Time", new Gson().toJson(new Time(year, month, day)));
			id.setReason(et_remark.getText().toString());
			ids = new ImportantDateServiceImpl(this);
			bb = id;
			if (ids.updateImportantDate(id)) {
				DataBaseUtil.notifyCur();
				startService();
				ToastUtil.show(this, "添加成功！");
				finish();
			} else
				ToastUtil.show(this, "系统异常!");
		}
	}

	@Override
	protected void back() {
		// TODO Auto-generated method stub
		finish();
	}

	OnClickListener date_oc = new OnClickListener() {

		public void callClick(View v) {
			onClick(v);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.v("Onclck", "true");
			Calendar c = Calendar.getInstance();
			new DatePickerDialog(
					ImportActivity.this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int curyear,
								int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							year = curyear;
							month = monthOfYear + 1;
							day = dayOfMonth;
							Time t = new Time();
							if (t.getYear() < year
									|| (t.getYear() == year && t.getMonth() < month)
									|| (t.getYear() == year
											&& t.getMonth() == month && t
											.getDay() < day)) {

								et_date.setText(String.valueOf(year) + "年  "
										+ String.valueOf(month) + "月  "
										+ String.valueOf(day) + "日");
							} else {
								ToastUtil.show(ImportActivity.this,
										"时光逆转！臣妾做不到啊。。。");
								callClick(et_date);
							}
						}
					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
					c.get(Calendar.DAY_OF_MONTH) + 1).show();
		}
	};

}
