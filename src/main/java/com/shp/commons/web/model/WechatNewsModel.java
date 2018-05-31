package com.shp.commons.web.model;

import java.util.List;


public class WechatNewsModel {
	
	private String touser;
	private String msgtype = "news";
	private WechatArticleModel news;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public WechatArticleModel getNews() {
		return news;
	}
	public void setNews(WechatArticleModel news) {
		this.news = news;
	}
	
	
}
