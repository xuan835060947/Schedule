package com.graduation.schedule.bean;

public class BaseBean {
	private long id = 0; // 唯一标识
	protected String reason = "";// 备注,用户自设
	protected Time startTime = null;// 开始设置提醒的时间
	protected Time endTime = null; // 提醒的时间

	protected int style = 0; // 类型:用下面的常量标记
	public static final int PHONE = 1;
	public static final int SHORT_MESSAGE = 2;
	public static final int LISTING = 3;
	public static final int IMPORTANT_DATE = 4;
	public static final int NOTE = 5;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

}
