package com.graduation.schedule.bean;

import java.util.ArrayList;
import java.util.List;

public class Listing extends BaseBean{
	private List<String> willDoList = null; //想要做的事情的列表

	public Listing() {
		// TODO Auto-generated constructor stub
		setStyle(LISTING);
	}
	
	public void addList(String willDo){
		if(willDo == null){
			willDoList = new ArrayList<String>();
		}else{
			willDoList.add(willDo);
		}
	}
	
	public List<String> getWillDoList() {
		return willDoList;
	}

	public void setWillDoList(List<String> willDoList) {
		this.willDoList = willDoList;
	}
	
	
}
