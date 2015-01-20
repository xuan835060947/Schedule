package com.graduation.schedule.service;

import java.util.List;

import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.ShortMessage;

public interface ShortMessageService {
	public boolean updateShortMessage(ShortMessage shortMessage);// 更新插入ShortMessage

	public List<BaseBean> queryShortMessage(ShortMessage shortMessage);
	// shortMessage如果传空则默认传回所有电话提醒
	// 如果shortMessage的id不为0则返回该id的ShortMessage
	public boolean deleteShortMessage(ShortMessage shortMessage);
	public List<BaseBean> queryHis();
	public List<BaseBean> queryCur();
}
