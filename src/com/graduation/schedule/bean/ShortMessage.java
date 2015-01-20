package com.graduation.schedule.bean;

public class ShortMessage extends BaseBean{
	private String message = null ;//短信内容
	private String phoneNumber = null ;
	


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public ShortMessage() {
		// TODO Auto-generated constructor stub
		setStyle(SHORT_MESSAGE);//设置类型
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
