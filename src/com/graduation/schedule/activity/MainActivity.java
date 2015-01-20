package com.graduation.schedule.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.graduation.schedule.R;
import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.ImportantDate;
import com.graduation.schedule.bean.Listing;
import com.graduation.schedule.bean.Phone;
import com.graduation.schedule.bean.ShortMessage;
import com.graduation.schedule.fragment.AddFragment;
import com.graduation.schedule.fragment.CurrentFragment;
import com.graduation.schedule.fragment.HistoryFragment;
import com.graduation.schedule.fragment.WebFragment;
import com.graduation.schedule.scroll_component.FragmentsAdapter;
import com.graduation.schedule.scroll_component.PagerScrollerActivity;
import com.graduation.schedule.scroll_component.TabInfo;
import com.graduation.schedule.service.AlarmEventService;
import com.graduation.schedule.util.DataBaseUtil;
import com.graduation.schedule.util.InitNotification;
import com.graduation.schedule.util.TimeUtil;
import com.graduation.schedule.util.ToastUtil;

public class MainActivity extends PagerScrollerActivity {

	protected TextView tvTitle;
	protected OnClickListener v_rtn_on, v_save_on;

	private AlertDialog dialog = null;
	private View contentView = null;
	private View titleView = null;
	private ImageView iv_img = null;
	private TextView tv_title = null;
	private ImageView iv_cancel = null;
	private TextView tv_content_1 = null;
	private TextView tv_content_2 = null;
	private TextView tv_content_date = null;
	private StringBuffer strBuf = new StringBuffer();
	private StringBuffer strBuf2 = new StringBuffer();
	private LayoutInflater inflater = null;
	private Intent doit = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		inflater = getLayoutInflater();
		if (!AlarmEventService.running) {
			Intent intent = new Intent();
			intent.setAction("AlarmEventService");
			startService(intent);
		}
		startType();
	}

	@Override
	protected void setTabsAndAdapter() {
		// TODO Auto-generated method stub

		setup();
		DataBaseUtil.setData(this);
		tabs = new ArrayList<TabInfo>();
		tabs.add(new TabInfo(0, "3D报表", new WebFragment()));
		tabs.add(new TabInfo(1, "历史记录", new HistoryFragment()));
		tabs.add(new TabInfo(2, "新增提醒", new AddFragment()));
		tabs.add(new TabInfo(3, "任务清单", new CurrentFragment()));

		this.adapter = new FragmentsAdapter(this, getSupportFragmentManager(),
				tabs);
		if (!InitNotification.isStart) {
			InitNotification in = new InitNotification(this);
			in.setup();
		}
	}

	protected void setup() {

		View vReturn = findViewById(R.id.title_return);
		View vSave = findViewById(R.id.title_other);
		ImageView iv = (ImageView) vReturn.findViewById(R.id.iv_back);
		tvTitle = (TextView) findViewById(R.id.title_name);

		iv.setVisibility(View.GONE);
		vSave.setVisibility(View.GONE);
		tvTitle.setText("Schedule");

	}

	private void showAlertDialog(String beanStr, int style) {

		BaseBean bb = new BaseBean();

		if (dialog == null) {

			dialog = new AlertDialog.Builder(this).create();
			titleView = inflater.inflate(R.layout.dialog_title, null);
			contentView = inflater.inflate(R.layout.dialog_content, null);

			iv_img = (ImageView) titleView.findViewById(R.id.title_dialog_img);
			tv_title = (TextView) titleView
					.findViewById(R.id.title_dialog_title);

			iv_cancel = (ImageView) titleView
					.findViewById(R.id.title_dialog_cancel);
			iv_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			tv_content_1 = (TextView) contentView
					.findViewById(R.id.tv_content_1);
			tv_content_2 = (TextView) contentView
					.findViewById(R.id.tv_content_2);

			tv_content_date = (TextView) contentView
					.findViewById(R.id.tv_content_date);

			dialog.setCustomTitle(titleView);
			dialog.setView(contentView);
		}

		strBuf.setLength(0);
		strBuf2.setLength(0);
		switch (style) {

		case BaseBean.PHONE:
			bb = new Gson().fromJson(beanStr, Phone.class);
			iv_img.setImageDrawable(getResources().getDrawable(
					R.drawable.phone_click));
			strBuf.append("打给: " + ((Phone) bb).getPhoneNumber());
			strBuf2.append("备注:" + bb.getReason());
			doit = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ ((Phone) bb).getPhoneNumber()));
			break;
		case BaseBean.SHORT_MESSAGE:
			bb = new Gson().fromJson(beanStr, ShortMessage.class);
			iv_img.setImageDrawable(getResources().getDrawable(
					R.drawable.message_click));

			strBuf.append("发信息给 : " + ((ShortMessage) bb).getPhoneNumber());
			strBuf2.append("内容为：" + ((ShortMessage) bb).getMessage());
			Uri uri = Uri
					.parse("smsto:" + ((ShortMessage) bb).getPhoneNumber());
			doit = new Intent(Intent.ACTION_SENDTO, uri);
			doit.putExtra("sms_body", ((ShortMessage) bb).getMessage());
			break;
		case BaseBean.LISTING:

			bb = new Gson().fromJson(beanStr, Listing.class);
			iv_img.setImageDrawable(getResources().getDrawable(
					R.drawable.todo_click));
			strBuf.append("记得 ");
			for (int i = 0; i < ((Listing) bb).getWillDoList().size(); i++) {
				if (i == 0) {
					strBuf.append(((Listing) bb).getWillDoList().get(i));
				} else
					strBuf.append("、" + ((Listing) bb).getWillDoList().get(i));
			}
			break;
		case BaseBean.IMPORTANT_DATE:

			bb = new Gson().fromJson(beanStr, ImportantDate.class);
			iv_img.setImageDrawable(getResources().getDrawable(
					R.drawable.important_click));
			strBuf.append("重要提示：");
			strBuf.append(((ImportantDate) bb).getWhatDate());
			strBuf2.append(TimeUtil.getDateStr(((ImportantDate) bb)
					.getEndTime()));
			break;
		default:
			break;
		}
		tv_content_1.setText(strBuf.toString());
		tv_content_2.setText(strBuf2.toString());

		tv_content_date.setText(TimeUtil.getDateStr(bb.getEndTime()));
		tv_title.setText("提醒");
		final BaseBean fbb = bb;
		dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "删除",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (DataBaseUtil.deleteBaseBase(fbb)) {
							if (DataBaseUtil.deleteBaseBase(fbb)) {
								ToastUtil.show(MainActivity.this, "删除成功");
								DataBaseUtil.notifyAllList();
							}
						}
					}
				});
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "处理",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						if (doit != null)
							startActivity(doit);
					}
				});
		dialog.show();
	}

	private void startType() {

		Intent startIntent = getIntent();

		Bundle bundle = startIntent.getExtras();
		if ((bundle != null) && (bundle.getInt("notification", 0) == 1)) {

			Log.v("MainActivity", bundle.getString("bean"));
			showAlertDialog(bundle.getString("bean"), bundle.getInt("style"));
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		ToastUtil.cancel();
		super.onDestroy();

	}
}
