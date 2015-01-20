package com.graduation.schedule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.schedule.R;
import com.graduation.schedule.adapter.MyExpandableListAdapter;
import com.graduation.schedule.scroll_component.BaseFragment;
import com.graduation.schedule.util.DataBaseUtil;
import com.graduation.schedule.util.ToastUtil;

public class HistoryFragment extends BaseFragment {

	private ExpandableListView elv;
	private MyExpandableListAdapter mela;
	private ImageView iv_clear;
	private TextView tv_number;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.index_fragment_history, null);
		iv_clear = (ImageView) view.findViewById(R.id.iv_clear);
		tv_number = (TextView) view.findViewById(R.id.tv_number);
		mela = new MyExpandableListAdapter(getActivity(),
				DataBaseUtil.getHisList(), false);
		
		iv_clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				deleteData();
			}
		});
		tv_number.setText(String.valueOf(mela.getNumber()));
		elv = (ExpandableListView) view.findViewById(R.id.history);
		elv.setAdapter(mela);
		return view;

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		mela.notifyDataSetChanged();
		super.onResume();
	}
	
	private void deleteData(){
		ToastUtil.show(getActivity(), "删除中...");
		if(mela.deleteAllData()){
			tv_number.setText("0");
		}
		else ToastUtil.show(getActivity(), "系统异常，请重新启动！");

		ToastUtil.show(getActivity(), "删除完成...");
	}
}
