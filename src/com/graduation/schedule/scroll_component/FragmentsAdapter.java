package com.graduation.schedule.scroll_component;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentsAdapter extends FragmentPagerAdapter {

    ArrayList<TabInfo> tabs    = null;
    Context            context = null;

    public FragmentsAdapter(Context context, FragmentManager fm, ArrayList<TabInfo> tabs) {
        super(fm);
        this.tabs = tabs;
        this.context = context;
    }

    @Override
    public Fragment getItem(int index) {
        // TODO Auto-generated method stub
        return tabs.get(index).getFragment();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return tabs.size();
    }

}
