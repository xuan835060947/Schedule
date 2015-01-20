package com.graduation.schedule.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.graduation.schedule.R;
import com.graduation.schedule.adapter.CallListAdapter;
import com.graduation.schedule.util.CallUtil;

public class CallLogActivity extends Activity {
	private View vReturn;
	private View vSave;
	private ListView calllog;
	private TextView tvTitle;
	private OnClickListener v_rtn_on;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calllog);

		tvTitle = (TextView) findViewById(R.id.title_name);
		vReturn = findViewById(R.id.title_return);
		vSave = findViewById(R.id.title_other);
		vSave.setVisibility(View.GONE);
		calllog = (ListView) findViewById(R.id.calllog);
		tvTitle.setText("通话记录");
		v_rtn_on = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(Activity.RESULT_CANCELED);
				finish();
			}
		};
		vReturn.setOnClickListener(v_rtn_on);
		calllog.setAdapter(new CallListAdapter(this, CallUtil
				.getCalls(getContentResolver())));
	}

}
