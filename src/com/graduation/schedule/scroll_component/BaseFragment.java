package com.graduation.schedule.scroll_component;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment {
	protected View view;
	protected Intent intent;
	protected Activity activity;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = activity;
	}

}
