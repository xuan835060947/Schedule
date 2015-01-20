package com.graduation.schedule.bean;

public class ImportantDate extends BaseBean{
	private String whatDate = null ; //什么日子
	
	public ImportantDate() {
		// TODO Auto-generated constructor stub
		setStyle(IMPORTANT_DATE);
	}

	public String getWhatDate() {
		return whatDate;
	}

	public void setWhatDate(String whatDate) {
		this.whatDate = whatDate;
	}
	
	
}
