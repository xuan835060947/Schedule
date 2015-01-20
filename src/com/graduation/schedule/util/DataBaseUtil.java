package com.graduation.schedule.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.ImportantDate;
import com.graduation.schedule.bean.Listing;
import com.graduation.schedule.bean.Note;
import com.graduation.schedule.bean.Phone;
import com.graduation.schedule.bean.ShortMessage;
import com.graduation.schedule.service.ImportantDateService;
import com.graduation.schedule.service.ListingService;
import com.graduation.schedule.service.NoteService;
import com.graduation.schedule.service.PhoneService;
import com.graduation.schedule.service.ShortMessageService;
import com.graduation.schedule.serviceImpl.ImportantDateServiceImpl;
import com.graduation.schedule.serviceImpl.ListingServiceImpl;
import com.graduation.schedule.serviceImpl.NoteServiceImpl;
import com.graduation.schedule.serviceImpl.PhoneServiceImpl;
import com.graduation.schedule.serviceImpl.ShortMessageServiceImpl;

public class DataBaseUtil {

	private static ImportantDateService ids;
	private static ListingService ls;
	private static PhoneService ps;
	private static ShortMessageService sms;
	private static NoteService ns;

	private static List<BaseBean> his_sm_list;
	private static List<BaseBean> his_p_list;
	private static List<BaseBean> his_l_list;
	private static List<BaseBean> his_id_list;

	private static List<BaseBean> cur_id_list;
	private static List<BaseBean> cur_l_list;
	private static List<BaseBean> cur_p_list;
	private static List<BaseBean> cur_sm_list;

	private static List<List<BaseBean>> lists_his = new ArrayList<List<BaseBean>>();
	private static List<List<BaseBean>> lists_cur = new ArrayList<List<BaseBean>>();
	private static List<BaseBean> lists_note = new ArrayList<BaseBean>();

	public static void setData(Context context) {

		ids = new ImportantDateServiceImpl(context);
		ls = new ListingServiceImpl(context);
		ps = new PhoneServiceImpl(context);
		sms = new ShortMessageServiceImpl(context);
		ns = new NoteServiceImpl(context);

		notifyCur();
		notifyHis();
	}

	public static void notifyAllList() {
		notifyCur();
		notifyHis();
	}

	public static void notifyCur() {

		lists_cur.clear();

		cur_id_list = ids.queryCur();
		cur_l_list = ls.queryCur();
		cur_p_list = ps.queryCur();
		cur_sm_list = sms.queryCur();

		lists_cur.add(cur_p_list);
		lists_cur.add(cur_sm_list);
		lists_cur.add(cur_l_list);
		lists_cur.add(cur_id_list);

	}

	public static void notifyHis() {

		lists_his.clear();

		his_id_list = ids.queryHis();
		his_l_list = ls.queryHis();
		his_p_list = ps.queryHis();
		his_sm_list = sms.queryHis();

		lists_his.add(his_p_list);
		lists_his.add(his_sm_list);
		lists_his.add(his_l_list);
		lists_his.add(his_id_list);
	}

	public static List<List<BaseBean>> getHisList() {
		return lists_his;
	}

	public static List<List<BaseBean>> getCurList() {
		return lists_cur;
	}

	public static boolean deleteBaseBase(BaseBean bb) {
		boolean result;
		switch (bb.getStyle()) {

		case BaseBean.PHONE:
			result = ps.deletePhone(((Phone) bb));
			break;
		case BaseBean.SHORT_MESSAGE:
			result = sms.deleteShortMessage((ShortMessage) bb);
			break;
		case BaseBean.LISTING:
			result = ls.deleteListing((Listing) bb);
			break;
		case BaseBean.IMPORTANT_DATE:
			result = ids.deleteImportantDate((ImportantDate) bb);
			break;
		case BaseBean.NOTE:
			result = ns.deleteNote((Note) bb);
			break;
		default:
			result = false;
			break;
		}
		return result;
	}

	public static void notifyNotes() {

		if (lists_note != null)
			lists_note.clear();
		lists_note = ns.queryAll();
	}

	public static List<BaseBean> getNotes() {
		if (lists_note == null || lists_note.size() == 0) {
			lists_note = ns.queryAll();
		}
		return lists_note;
	}

	public static boolean updateNote(Note note) {
		return ns.updateNote(note);
	}
}
