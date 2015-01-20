package com.graduation.schedule.adapter;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DatabaseUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.schedule.R;
import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.ImportantDate;
import com.graduation.schedule.bean.Listing;
import com.graduation.schedule.bean.Phone;
import com.graduation.schedule.bean.ShortMessage;
import com.graduation.schedule.util.DataBaseUtil;
import com.graduation.schedule.util.TimeUtil;
import com.graduation.schedule.util.ToastUtil;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

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
	private LayoutInflater inflater;
	private Context context;
	private List<List<BaseBean>> lists = null;
	private boolean isCur = false;

	public MyExpandableListAdapter(Context context, List<List<BaseBean>> lists,
			boolean isCur) {
		super();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.lists = lists;
		this.isCur = isCur;

	}

	// 获取指定组位置、指定子列表项处的子列表项数据
	@Override
	public BaseBean getChild(int groupPosition, int childPosition) {
		return lists.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return lists.get(groupPosition).get(childPosition).getId();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return lists.get(groupPosition).size();
	}

	// 获取指定组位置处的组数据
	@Override
	public String getGroup(int groupPosition) {
		switch (groupPosition) {
		case 0:
			return "打电话";
		case 1:
			return "发短信";
		case 2:
			return "清单";
		case 3:
			return "重要日期";
		default:
			return "错误";
		}
	}

	@Override
	public int getGroupCount() {
		return lists.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	// 该方法决定每个子选项的外观
	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = inflater.inflate(R.layout.line_child, null);
		TextView content = (TextView) view.findViewById(R.id.item_content);
		ImageView img = (ImageView) view.findViewById(R.id.item_img);
		content.setText(getChild(groupPosition, childPosition).toString());
		strBuf.setLength(0);
		switch (groupPosition) {
		case 0:
			img.setImageDrawable(context.getResources().getDrawable(
					R.drawable.phone_click));
			strBuf.append("打电话给："
					+ ((Phone) getChild(groupPosition, childPosition))
							.getPhoneNumber());
			break;
		case 1:
			ShortMessage sm = (ShortMessage) getChild(groupPosition,
					childPosition);
			img.setImageDrawable(context.getResources().getDrawable(
					R.drawable.message_click));
			strBuf.append("发信息：\"" + sm.getMessage() + "\"  给-"
					+ sm.getPhoneNumber());
			break;
		case 2:
			Listing listing = (Listing) getChild(groupPosition, childPosition);
			img.setImageDrawable(context.getResources().getDrawable(
					R.drawable.todo_click));
			strBuf.append("记得做");
			for (int i = 0; i < listing.getWillDoList().size(); i++) {
				if (i == 0) {
					strBuf.append(listing.getWillDoList().get(i));
				} else
					strBuf.append("、" + listing.getWillDoList().get(i));
			}
			break;
		case 3:
			ImportantDate id = (ImportantDate) getChild(groupPosition,
					childPosition);
			img.setImageDrawable(context.getResources().getDrawable(
					R.drawable.important_click));
			strBuf.append("重要提示：");
			strBuf.append(id.getWhatDate());
			strBuf.append(" - " + TimeUtil.getDateStr(id.getEndTime()));

			break;
		}
		content.setText(strBuf.toString());

		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showAlertDialog(groupPosition, childPosition);
			}
		});
		return view;
	}

	// 该方法决定每个组选项的外观
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View group = inflater.inflate(R.layout.line_group, null);
		TextView title = (TextView) group.findViewById(R.id.group_title);
		title.setText(getGroup(groupPosition) + "("
				+ String.valueOf(getChildrenCount(groupPosition)) + ")");
		return group;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	private void showAlertDialog(int groupPosition, int childPosition) {
		final BaseBean bb = getChild(groupPosition, childPosition);

		if (dialog == null) {

			dialog = new AlertDialog.Builder(context).create();
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
		switch (groupPosition) {
		case 0:
			iv_img.setImageDrawable(context.getResources().getDrawable(
					R.drawable.phone_click));
			strBuf.append("打给: " + ((Phone) bb).getPhoneNumber());
			strBuf2.append("备注:" + bb.getReason());

			break;
		case 1:
			iv_img.setImageDrawable(context.getResources().getDrawable(
					R.drawable.message_click));

			strBuf.append("发信息给 : " + ((ShortMessage) bb).getPhoneNumber());
			strBuf2.append("内容为：" + ((ShortMessage) bb).getMessage());
			break;
		case 2:
			iv_img.setImageDrawable(context.getResources().getDrawable(
					R.drawable.todo_click));
			strBuf.append("记得 ");
			for (int i = 0; i < ((Listing) bb).getWillDoList().size(); i++) {
				if (i == 0) {
					strBuf.append(((Listing) bb).getWillDoList().get(i));
				} else
					strBuf.append("、" + ((Listing) bb).getWillDoList().get(i));
			}
			break;
		case 3:
			iv_img.setImageDrawable(context.getResources().getDrawable(
					R.drawable.important_click));
			strBuf.append("重要提示：");
			strBuf.append(((ImportantDate) bb).getWhatDate());
			break;
		default:
			break;
		}
		tv_content_1.setText(strBuf.toString());
		if (strBuf2.toString() == null || strBuf2.toString().equals(""))
			tv_content_2.setVisibility(View.GONE);
		else {
			tv_content_2.setText(strBuf2.toString());
			tv_content_2.setVisibility(View.VISIBLE);
		}
		tv_content_date.setText(TimeUtil.getDateStr(bb.getEndTime()));
		tv_title.setText(getGroup(groupPosition));

		dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "删除",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (DataBaseUtil.deleteBaseBase(bb)) {
							if (DataBaseUtil.deleteBaseBase(bb)) {
								ToastUtil.show(context, "删除成功");
								if (isCur) {
									DataBaseUtil.notifyCur();
								} else
									DataBaseUtil.notifyHis();
								notifyDataSetChanged();
							}
						}
						;
					}
				});
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "处理",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				});
		dialog.show();
	}

	public boolean deleteAllData() {

		try {
			for (int i = 0; i < lists.size(); i++) {
				for (int j = 0; j < lists.get(i).size(); j++) {
					DataBaseUtil.deleteBaseBase(lists.get(i).get(j));
				}
			}
			DataBaseUtil.notifyHis();
			notifyDataSetChanged();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public int getNumber(){
		
		int number = 0;
		for (int i = 0; i < lists.size(); i++) {
			for (int j = 0; j < lists.get(i).size(); j++) {
				number++;
			}
		}
		return number;
	}
}
