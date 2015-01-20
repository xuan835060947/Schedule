package com.graduation.schedule.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.graduation.schedule.R;

public class CheckCompontent extends LinearLayout {

	public CheckCompontent(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CheckCompontent(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CheckCompontent(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private View curItem;

	public interface Checked {
		void check();
	}

	public abstract class CheckItem implements Checked {

		private RelativeLayout item;
		private ImageView item_iv;
		private TextView item_tv;

		/**
		 * 
		 * @param context
		 * @param draw
		 * @param desc
		 */
		public CheckItem(Context context, Drawable draw, String desc) {

			LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
			itemParams.setMargins(5, 5, 5, 5);
			item = new RelativeLayout(context);
			item.setPadding(0, 10, 0, 10);
			item.setLayoutParams(itemParams);
			item.setBackground(getResources().getDrawable(
					R.drawable.bt_background_selector));
			item.setGravity(Gravity.CENTER);
			setImageView(context, draw);
			setTextView(context, desc);
			item.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					selected(v);
					check();
				}
			});

		}

		public RelativeLayout getItem() {
			return item;
		}

		private void setImageView(Context context, Drawable draw) {
			if (draw != null) {
				item_iv = new ImageView(context);
				RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				rllp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				rllp.addRule(RelativeLayout.CENTER_HORIZONTAL);
				item_iv.setLayoutParams(rllp);
				item_iv.setScaleType(ScaleType.FIT_CENTER);
				item_iv.setImageDrawable(draw);
				item_iv.setId(1);
				item.addView(item_iv);
			}
		}

		private void setTextView(Context context, String desc) {
			if (desc == null || desc.equals(""))
				return;

			item_tv = new TextView(context);
			RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			rllp.addRule(RelativeLayout.BELOW, 1);
			rllp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			item_tv.setLayoutParams(rllp);
			item_tv.setText(desc);
			item_tv.setGravity(Gravity.CENTER);
			item.addView(item_tv);
		}

		public void selected(View v) {
			// TODO Auto-generated method stub
			if (curItem != null && !v.equals(curItem)) {
				curItem.setSelected(false);
			}
			curItem = v;
			curItem.setSelected(true);
		}

	}
}