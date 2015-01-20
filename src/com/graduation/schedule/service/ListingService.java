package com.graduation.schedule.service;

import java.util.List;

import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.Listing;


public interface ListingService {
	public boolean updateListing(Listing listing);
	public List<BaseBean> queryListing(Listing listing);
	public boolean deleteListing(Listing listing);
	public List<BaseBean> queryHis();
	public List<BaseBean> queryCur();
}
