package com.graduation.schedule.service;

import java.util.List;

import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.ImportantDate;


public interface ImportantDateService {
	public boolean updateImportantDate(ImportantDate importantDate);
	public List<BaseBean> queryImportantDate(ImportantDate importantDate);
	public boolean deleteImportantDate(ImportantDate importantDate);
	public List<BaseBean> queryHis();
	public List<BaseBean> queryCur();
}

