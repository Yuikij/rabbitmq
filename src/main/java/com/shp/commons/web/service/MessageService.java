package com.shp.commons.web.service;

public interface MessageService {
	public void sendSMS(Object object);
	public void sendEmail(Object object);
	public void sendWechat(Object object);
	public void voiceCall(Object object);
}
