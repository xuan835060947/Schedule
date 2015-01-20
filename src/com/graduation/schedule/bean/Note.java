package com.graduation.schedule.bean;

public class Note extends BaseBean{

	private String title;
	private String Content;



	public Note() {
		// TODO Auto-generated constructor stub
		setStyle(NOTE);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
