package com.graduation.schedule.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.graduation.schedule.R;
import com.graduation.schedule.bean.ShortMessage;
import com.graduation.schedule.component.CheckCompontent;
import com.graduation.schedule.service.ShortMessageService;
import com.graduation.schedule.serviceImpl.ShortMessageServiceImpl;
import com.graduation.schedule.util.DataBaseUtil;
import com.graduation.schedule.util.GsonUtil;
import com.graduation.schedule.util.TimeUtil;
import com.graduation.schedule.util.ToastUtil;

public class SendMSGActivity extends AbstractSecondActivity {

	private TextView tvContent;
	private ShortMessageService sms;
	private ShortMessage sm;
	protected View vContants;
	protected View vCallHistory;
	protected View vInputPhone;
	private CheckCompontent mcc;
	private CheckCompontent.CheckItem ccci_contants;
	private CheckCompontent.CheckItem ccci_call_history;
	private CheckCompontent.CheckItem ccci_input_phone;
	private EditText et_phone_number;
	private String username;
	private String usernumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		super.setup(this);
		setChild();
		tvContent = (TextView) findViewById(R.id.msg_content);
		et_phone_number = (EditText) findViewById(R.id.phone_number);
		tvTitle.setText("发信息");
	}

	@Override
	protected void save() {
		// TODO Auto-generated method stub
		day = Integer.valueOf(et_day.getText().toString());
		if (day == 0 && hour == 0 && minute == 0) {
			ToastUtil.show(this, "请输入时间！");
		} else if (etPhoneNumber.getText().toString().length() < 3) {
			ToastUtil.show(this, "请输入正确号码！");
		} else {
			sm = new ShortMessage();
			sm.setEndTime(TimeUtil.getEndTime(hour, minute));
			sm.setMessage(tvContent.getText().toString());
			sm.setPhoneNumber(etPhoneNumber.getText().toString().trim());
			sms = new ShortMessageServiceImpl(getBaseContext());

			Log.e("StartService", GsonUtil.gson.toJson(sm));
			bb = sm;
			if (sms.updateShortMessage(sm)) {
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

	private void setChild() {

		et_phone_number = (EditText) findViewById(R.id.phone_number);
		mcc = (CheckCompontent) findViewById(R.id.title_phone);
		ccci_contants = mcc.new CheckItem(this, getResources().getDrawable(
				R.drawable.contacts_selector), getString(R.string.contacts)) {

			@Override
			public void check() {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI), 0);
			}
		};
		ccci_call_history = mcc.new CheckItem(this, getResources().getDrawable(
				R.drawable.call_history_selector),
				getString(R.string.call_history)) {

			@Override
			public void check() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SendMSGActivity.this,
						CallLogActivity.class);
				startActivityForResult(intent, 1);
			}
		};
		ccci_input_phone = mcc.new CheckItem(this, getResources().getDrawable(
				R.drawable.input_phone_selector),
				getString(R.string.input_phone_number)) {

			@Override
			public void check() {
				// TODO Auto-generated method stub

			}
		};
		mcc.addView(ccci_contants.getItem());
		mcc.addView(ccci_call_history.getItem());
		mcc.addView(ccci_input_phone.getItem());
		ccci_input_phone.selected(ccci_input_phone.getItem());
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 0) {
				ContentResolver reContentResolverol = getContentResolver();
				Uri contactData = data.getData();
				@SuppressWarnings("deprecation")
				Cursor cursor = managedQuery(contactData, null, null, null,
						null);
				cursor.moveToFirst();
				username = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				String contactId = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				Cursor phone = reContentResolverol.query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = " + contactId, null, null);
				while (phone.moveToNext()) {
					usernumber = phone
							.getString(phone
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					et_phone_number.setText(usernumber.replace(" ", ""));
				}
			} else if (requestCode == 1) {

				et_phone_number.setText(data.getStringExtra("phone_number")
						.replace(" ", ""));
			}

		}
	}
}
