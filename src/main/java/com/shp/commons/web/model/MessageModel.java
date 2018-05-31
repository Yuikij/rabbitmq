package com.shp.commons.web.model;

import java.io.Serializable;

public class MessageModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7621350376289676325L;

	private String action;
	
	private String mobile;
	
	private String plateNo;
	
	private String door;
	
	private String progress;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getDoor() {
		return door;
	}

	public void setDoor(String door) {
		this.door = door;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}
	
	


}
