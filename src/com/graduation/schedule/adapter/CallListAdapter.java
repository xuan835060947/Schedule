package com.graduation.schedule.adapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.graduation.schedule.R;
import com.graduation.schedule.bean.Call;
import com.graduation.schedule.util.TimeUtil;

public class CallListAdapter extends BaseAdapter {

	private List<Call> calls;
	private LayoutInflater inflater;
	private Activity activity;
	private Calendar calendar = Calendar.getInstance();
	private Date date = new Date();

	public CallListAdapter(Activity activity, List<Call> calls) {
		super();
		this.activity = activity;
		this.inflater = activity.getLayoutInflater();
		this.calls = calls;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return calls.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.call_item, null);
			holder = new ViewHolder();
			holder.tv_call = (TextView) convertView.findViewById(R.id.call);
			holder.tv_time = (TextView) convertView.findViewById(R.id.time);
			holder.tv_name = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		date.setTime(calls.get(position).getDate());
		calendar.setTime(date);
		holder.tv_call.setText(calls.get(position).getPhoneNumber());
		holder.tv_time.setText(TimeUtil.getTimeStr(calendar));
		if (calls.get(position).getName() != "") {
			holder.tv_name.setText(calls.get(position).getName() + ":");
		}
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("List", "Click");
				Intent intent = activity.getIntent();
				intent.putExtra("phone_number", calls.get(position)
						.getPhoneNumber());
				activity.setResult(Activity.RESULT_OK, intent);
				activity.finish();

			}
		});
		return convertView;
	}

	static class ViewHolder {
		TextView tv_call;
		TextView tv_time;
		TextView tv_name;

	}
}
