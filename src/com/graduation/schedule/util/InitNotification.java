package com.graduation.schedule.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.graduation.schedule.R;
import com.graduation.schedule.activity.ImportActivity;
import com.graduation.schedule.activity.ListActivity;
import com.graduation.schedule.activity.MainActivity;
import com.graduation.schedule.activity.MakeCallActivity;
import com.graduation.schedule.activity.SendMSGActivity;

public class InitNotification extends Notification {

	private final String Tag = "InitNotification:";
	private Context context;
	private RemoteViews view = null;
	private Notification notification;
	private NotificationManager manager = null;
	private Intent intent = null;
	private PendingIntent pIntent = null;// 更新显示
	public static boolean isStart = false;

	public InitNotification(Context context) {
		// TODO Auto-generated constructor stub
		Log.v(Tag, "Init Notification构造函数");
		isStart = true;
		this.context = context;

	}

	public void setup() {
		notification = new Notification();
		manager = (NotificationManager) context
				.getSystemService(Activity.NOTIFICATION_SERVICE);
		view = new RemoteViews(context.getPackageName(),
				R.layout.init_notification);
		// pIntent = PendingIntent.getService(NotificationTest.this, 0, intent,
		// 0);
		intent = new Intent(context, MainActivity.class);
		pIntent = PendingIntent.getActivity(context, 0, intent, 0);
		notification.icon = R.drawable.ic_launcher;
		notification.tickerText = "schedule";

		// BroadcastReceiver onClickReceiver = new BroadcastReceiver() {
		//
		// @Override
		// public void onReceive(Context context, Intent intent) {
		// if (intent.getAction().equals(STATUS_BAR_COVER_CLICK_ACTION)) {
		// //在这里处理点击事件
		// }
		// };
		// IntentFilter filter = new IntentFilter();
		// filter.addAction(STATUS_BAR_COVER_CLICK_ACTION);
		// registerReceiver(onClickReceiver, filter);

		Intent phoneIntent = new Intent(context, MakeCallActivity.class);
		PendingIntent pendButtonIntent = PendingIntent.getActivity(context, 0,
				phoneIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.phone, pendButtonIntent);
		// R.id.trackname为你要监听按钮的id
		Intent smsIntent = new Intent(context, SendMSGActivity.class);
		PendingIntent smsPIntent = PendingIntent.getActivity(context, 1,
				smsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.short_message, smsPIntent);

		Intent listingIntent = new Intent(context, ListActivity.class);
		PendingIntent listingPIntent = PendingIntent.getActivity(context, 1,
				listingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.listing, listingPIntent);

		Intent idIntent = new Intent(context, ImportActivity.class);
		PendingIntent idPIntent = PendingIntent.getActivity(context, 1,
				idIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.important_date, idPIntent);

		Intent intentAll = new Intent(context, MainActivity.class);
		PendingIntent pIntentAll = PendingIntent.getActivity(context, 1,
				intentAll, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.contentView = view;
		notification.contentIntent = pIntentAll;
		manager.notify(1299, notification);
	}

	// public void setProgress(int progress)
	// {
	// Log.v(Tag, "setProgress!");
	// view.setProgressBar(R.id.pb, 100, progress, false);
	// view.setTextViewText(R.id.tv, progress+"%");//关键部分，如果你不重新更新通知，进度条是不会更新的
	// notification.contentView = view;
	// notification.contentIntent = pIntent;
	// // manager.notify(0, notification);
	// activity.runOnUiThread(new Runnable(){
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// Log.v(Tag, "run的manager.notify");
	// manager.notify(0, notification);
	// }
	// });
	// }

	// public void setImage(File file)
	// {
	//
	// Bitmap bitmap = ImageUtil.decodeFile(file, 100, 100, true);
	// view.setImageViewBitmap(R.id.image, bitmap);
	// }

	// public void finish(String name)
	// {
	// view = new RemoteViews(activity.getPackageName(),
	// R.layout.sup_upload_notification_finish);
	// view.setTextViewText(R.id.tv, name+"  上传完成");
	// notification.contentView = view;
	//
	// activity.runOnUiThread(new Runnable(){
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// Log.v(Tag, "run的manager.notify");
	// manager.notify(0, notification);
	// }
	// });
	//
	// }
}
