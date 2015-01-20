package com.graduation.schedule.service;

import java.util.List;

import com.graduation.schedule.bean.BaseBean;
import com.graduation.schedule.bean.Note;

public interface NoteService {
	public boolean updateNote(Note note);// 更新插入ShortMessage

	public boolean deleteNote(Note note);

	public List<BaseBean> queryAll();

}
