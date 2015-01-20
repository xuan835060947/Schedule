package com.graduation.schedule.scroll_component;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.graduation.schedule.R;
import com.graduation.schedule.R.color;

public abstract class PagerScrollerActivity extends FragmentActivity implements
		OnPageChangeListener {

	private ViewPager viewPager;
	protected FragmentsAdapter adapter;
	protected ArrayList<TabInfo> tabs;
	private TitleIndicator title;
	private int defaultTab = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_main);

		initView();
	}

	private void initView() throws IndexOutOfBoundsException,
			NullPointerException {
		setTabsAndAdapter(); // 抽象方法，用于实例化Fragment页面以及标题栏

		viewPager = (ViewPager) findViewById(R.id.vPager);
		// 设置viewpager内部页面之间的间距
		viewPager.setPageMargin(getResources().getDimensionPixelSize(
				R.dimen.fragment_viewpager_margin));
		// 设置viewpager内部页面间距的drawable
		viewPager.setPageMarginDrawable(color.s_green);

		viewPager.setAdapter(adapter);

		// 必须让viewPager设置此OnPageChangeListener的实现类，才能对滑动和页面状态监听
		viewPager.setOnPageChangeListener(this);

		title = (TitleIndicator) findViewById(R.id.title);
		title.init(tabs, viewPager);

		// 判断默认页面数值是否溢出
		try {
			if (0 > defaultTab || defaultTab >= tabs.size()) {
				Log.v("default", String.valueOf(defaultTab));
				throw new IndexOutOfBoundsException();
			} else {

				title.setDefaultTab(defaultTab);
				viewPager.setCurrentItem(defaultTab);
			}

		} catch (NullPointerException e) {
			throw e;

		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// 读者可在纸上画出多个页面的示意图，自己进行计算便能得出这个结果
		title.onScroll((viewPager.getWidth() + viewPager.getPageMargin())
				* position + positionOffsetPixels);
	}

	@Override
	public void onPageSelected(int position) {
		title.setCurrentTab(position);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	public void setDefaultTab(int index) {
		this.defaultTab = index;
	}

	protected abstract void setTabsAndAdapter();
}
