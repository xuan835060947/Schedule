package com.graduation.schedule.bean;


public class Phone extends BaseBean {

	private String phoneNumber;

	
	public Phone() {
		// TODO Auto-generated constructor stub
		setStyle(PHONE);//设置类型
		
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	

}
