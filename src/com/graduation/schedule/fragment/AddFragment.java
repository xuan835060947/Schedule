package com.graduation.schedule.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.graduation.schedule.R;
import com.graduation.schedule.activity.ImportActivity;
import com.graduation.schedule.activity.ListActivity;
import com.graduation.schedule.activity.MakeCallActivity;
import com.graduation.schedule.activity.NoteListActivity;
import com.graduation.schedule.activity.SendMSGActivity;
import com.graduation.schedule.scroll_component.BaseFragment;
import com.graduation.schedule.util.LightComtroller;

public class AddFragment extends BaseFragment {

	private View addList;
	private View makeCall;
	private View send_msg;
	private View importantDate;
	private View note;
	private View flash;
	private LightComtroller lightCtler = new LightComtroller();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.index_fragment_add, null);
		addList = view.findViewById(R.id.bt_list);
		makeCall = view.findViewById(R.id.bt_make_call);
		send_msg = view.findViewById(R.id.bt_send_msg);
		importantDate = view.findViewById(R.id.bt_important_date);
		flash = view.findViewById(R.id.flash);
		note = view.findViewById(R.id.bt_note);
		setListener();
		return view;

	}

	private void setListener() {

		addList.setOnClickListener(addlist_oc);
		makeCall.setOnClickListener(makeCall_oc);
		send_msg.setOnClickListener(sendMsg_oc);
		importantDate.setOnClickListener(imprtDate_oc);
		note.setOnClickListener(note_oc);
		flash.setOnClickListener(flash_oc);
	}

	OnClickListener addlist_oc = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			intent = new Intent(activity, ListActivity.class);

			startActivity(intent);
		}
	}, imprtDate_oc = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			intent = new Intent(activity, ImportActivity.class);
			startActivity(intent);
		}
	}, sendMsg_oc = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			intent = new Intent(activity, SendMSGActivity.class);
			startActivity(intent);
		}
	}, makeCall_oc = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			intent = new Intent(activity, MakeCallActivity.class);
			startActivity(intent);
		}
	}, note_oc = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			intent = new Intent(activity, NoteListActivity.class);
			startActivity(intent);
		}
	};
	OnClickListener flash_oc = new OnClickListener() {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			if (!view.isSelected()) {
				view.setSelected(true);
				lightCtler.openLight();
			} else {

				view.setSelected(false);
				lightCtler.closeLight();
			}
		}
	};

}
