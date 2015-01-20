package com.graduation.schedule.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.graduation.schedule.R;
import com.graduation.schedule.adapter.MyExpandableListAdapter;
import com.graduation.schedule.scroll_component.BaseFragment;
import com.graduation.schedule.util.DataBaseUtil;

public class CurrentFragment extends BaseFragment {

	private ExpandableListView elv;
	private MyExpandableListAdapter mela;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.index_fragment_current_list, null);
		mela = new MyExpandableListAdapter(getActivity(),
				DataBaseUtil.getCurList(), true);
		elv = (ExpandableListView) view.findViewById(R.id.current_list);
		elv.setAdapter(mela);
		Log.e("CurrentFrag", "OnCreateView");
		return view;

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		mela.notifyDataSetChanged();
		super.onResume();
	}
	

	
}
