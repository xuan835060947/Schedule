package com.graduation.schedule.activity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TimePicker;

import com.graduation.schedule.R;

public abstract class AbstractSecondActivity extends AbstractBaseActivity {

	protected EditText etPhoneNumber;
	protected int day;
	protected int minute;
	protected int hour;
	protected EditText et_day;
	protected EditText et_hour;
	protected EditText et_minute;
	protected OnClickListener v_rtn_on, v_save_on, v_getMinute_on;

	protected void setup(final Context context) {
		super.setup();
		et_day = (EditText) findViewById(R.id.remind_time_day);
		et_hour = (EditText) findViewById(R.id.remind_time_hour);
		et_minute = (EditText) findViewById(R.id.remind_time_minute);
		etPhoneNumber = (EditText) findViewById(R.id.phone_number);
		v_getMinute_on = new OnClickListener() {

			@Override
			public void onClick(View v) {
				new TimePickerDialog(context,
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker tp, int hourOfDay,
									int minuteCur) {
								hour = hourOfDay;
								minute = minuteCur;
								et_hour.setText(String.valueOf(hour));
								et_minute.setText(String.valueOf(minute));

							}
						}, 0, 30, true).show();
			}
		};

		et_hour.setOnClickListener(v_getMinute_on);
		et_minute.setOnClickListener(v_getMinute_on);

	}

}
