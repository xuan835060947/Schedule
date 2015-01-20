package com.graduation.schedule.activity;

import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.graduation.schedule.R;
import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.Note;
import com.graduation.schedule.serviceImpl.util.DataUtil;
import com.graduation.schedule.util.DataBaseUtil;
import com.graduation.schedule.util.GsonUtil;

public class NoteListActivity extends AbstractBaseActivity {

	private LinearLayout left;
	private LinearLayout right;
	private static Drawable title;
	private static Drawable time;
	private static LayoutParams paramsItem = new LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	private static Random r = new Random(49);
	private static LayoutParams paramsItemText = new LayoutParams(
			LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	private int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_list);
		super.setup();
		((ImageView) vSave).setImageDrawable(getResources().getDrawable(
				R.drawable.add_note_selector));
		left = (LinearLayout) findViewById(R.id.left);
		right = (LinearLayout) findViewById(R.id.right);
		title = getResources().getDrawable(R.drawable.ab_bottom_solid_reminder);
		time = getResources().getDrawable(
				R.drawable.abs__ab_bottom_solid_light_holo);
		paramsItem.setMargins(10, 10, 10, 10);
		paramsItemText.setMargins(5, 0, 5, 0);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		left.removeAllViews();
		right.removeAllViews();
		addView(DataBaseUtil.getNotes());
		super.onResume();
	}

	@SuppressLint("NewApi")
	private void addView(List<BaseBean> notes) {

		boolean isLeft = true;
		LinearLayout line;
		TextView tv_title;
		ImageView iv_line;
		TextView tv_time;
		for (int i = 0; notes != null && i < notes.size(); i++) {
			final Note note = (Note) notes.get(i);
			int size = r.nextInt(8);
			line = new LinearLayout(getBaseContext());
			tv_title = new TextView(getBaseContext());
			iv_line = new ImageView(getBaseContext());
			tv_time = new TextView(getBaseContext());
			line.setLayoutParams(paramsItem);
			line.setOrientation(LinearLayout.VERTICAL);
			line.setBackground(getResources().getDrawable(
					R.drawable.abs__list_focused_holo));
			tv_time.setLayoutParams(paramsItemText);
			tv_title.setLayoutParams(paramsItemText);
			tv_title.setText(note.getTitle());
			tv_title.setTextSize(18 + size);
			tv_title.setTextColor(Color.BLACK);
			tv_title.getPaint().setFakeBoldText(true);

			iv_line.setImageDrawable(getResources().getDrawable(
					R.drawable.line_horizontal));

			tv_time.setText(DataUtil.timeToString(note.getStartTime()));
			tv_time.setTextSize(12 + size);
			tv_time.setTextColor(Color.GRAY);
			line.addView(tv_title);
			line.addView(iv_line);
			line.addView(tv_time);
			line.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(NoteListActivity.this,
							NoteInputActivity.class);
					intent.putExtra("note", GsonUtil.gson.toJson(note));
					startActivity(intent);
				}
			});
			if (isLeft) {
				left.addView(line);
				isLeft = !isLeft;
			} else {
				right.addView(line);
				isLeft = !isLeft;
			}
		}
	}

	private void add() {
		Intent intent = new Intent(this, NoteInputActivity.class);
		startActivity(intent);
	}

	@Override
	protected void save() {
		// TODO Auto-generated method stub
		add();
	}

	@Override
	protected void back() {
		// TODO Auto-generated method stub
		finish();
	}

}
