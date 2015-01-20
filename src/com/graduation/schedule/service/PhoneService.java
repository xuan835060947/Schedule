package com.graduation.schedule.service;

import java.util.List;

import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.Phone;

public interface PhoneService {

	public boolean updatePhone(Phone phone); // 修改或设置电话提醒

	public List<BaseBean> queryPhone(Phone phone);// 如果传空则默认传回所有电话提醒
	// 如果Phone的id不为0则返回该id的Phone
	public boolean deletePhone(Phone phone);
	public List<BaseBean> queryHis();
	public List<BaseBean> queryCur();
}
