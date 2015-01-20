package com.graduation.schedule.component;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.graduation.schedule.R;
import com.graduation.schedule.util.ToastUtil;

@SuppressLint("NewApi")
public class AddToDoListCompontent extends LinearLayout {

	private List<ListLine> mlist = new ArrayList<ListLine>();
	private static LinearLayout.LayoutParams layoutParams_Line;
	private static LinearLayout.LayoutParams layoutParams_iv;
	private static LinearLayout.LayoutParams layoutParams_tv;
	private static Drawable todolist;
	private static Drawable addItem;
	private static Drawable deleteItem;
	private String hint = "待办事项";

	static {
		layoutParams_Line = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams_Line.setMargins(15, 25, 15, 15);

		layoutParams_iv = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		layoutParams_tv = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	}

	public AddToDoListCompontent(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}

	public AddToDoListCompontent(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}

	public AddToDoListCompontent(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}

	private void init() {
		initDrawable();
		addItem(new ListLine(todolist, false));
		addItem(new ListLine(addItem, true));
	}

	private void clickItem(ListLine listLine, boolean isAdd, View v) {
		int index = mlist.indexOf(listLine);
		if (isAdd) {
			if (mlist.size() >= 10) {
				ToastUtil.show(getContext(),
						"Make sure that you are a super man.");
				return;
			}
			if (index != 0) {
				((ImageView) v).setImageDrawable(deleteItem);
				mlist.get(index).changeState();
			}
			addItem(new ListLine(addItem, true));
		} else
			deleteItem(index);
	}

	public void setHint(String hint) {
		this.hint = hint;
		for (int i = 0; i < mlist.size(); i++) {
			mlist.get(i).setHint(hint);
		}
	}

	private void initDrawable() {
		todolist = getContext().getResources()
				.getDrawable(R.drawable.icon_list);
		addItem = getContext().getResources().getDrawable(R.drawable.add_list);
		deleteItem = getContext().getResources().getDrawable(
				R.drawable.delete_list);
	}

	public List<String> getWillDoList() {
		List<String> willDoList = new ArrayList<String>();
		for (int i = 0; i < mlist.size(); i++) {
			String str = mlist.get(i).getText();
			if (str != null && !str.trim().equals(""))
				willDoList.add(str);
		}
		return willDoList;
	}

	private void addItem(ListLine child) {
		mlist.add(child);
		addView(child.getView());
	}

	private void deleteItem(int location) {
		removeViewAt(location);
		mlist.remove(location);
	}

	class ListLine {

		private boolean isAdd = true;
		private LinearLayout view;
		private ImageView iv;
		private EditText ev;

		private ListLine(Drawable draw, boolean needOnclick) {
			// TODO Auto-generated constructor stub
			super();
			view = new LinearLayout(getContext());
			view.setLayoutParams(layoutParams_Line);
			view.setOrientation(LinearLayout.HORIZONTAL);
			iv = new ImageView(getContext());
			iv.setImageDrawable(draw);
			iv.setLayoutParams(layoutParams_iv);
			ev = new EditText(getContext());
			ev.setLayoutParams(layoutParams_tv);
			ev.setHint(hint);

			if (needOnclick) {

				iv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						clickItem(ListLine.this, isAdd, v);
					}
				});
			}
			view.addView(iv);
			view.addView(ev);
		}

		private View getView() {
			return view;
		}

		private void changeState() {
			isAdd = !isAdd;
		}

		private String getText() {

			return ev.getText().toString();
		}

		private void setHint(String hint) {
			ev.setHint(hint);
		}
	}
}
