package com.shp.commons.web.service.impl;


import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shp.commons.web.model.WechatArticleItemModel;
import com.shp.commons.web.model.WechatArticleModel;
import com.shp.commons.web.model.WechatNewsModel;
import com.shp.commons.web.service.WechatMsgSendService;
import com.shp.commons.web.util.JsonUtil;

@Service
public class WXMsgSendServiceImpl implements WechatMsgSendService {
	
	private static final Logger logger = Logger.getLogger(WXMsgSendServiceImpl.class);
	private static String kfMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	@SuppressWarnings("unchecked")
	public void sendMessage(Object object) {
	
		Map<String, Object> model = (Map<String, Object>) object;
		String message = (String) model.get("message");
		String codeUrl = (String) model.get("qrCode");
		List<String> dest = (List<String>) model.get("openId");
		String token = (String) model.get("token");
		logger.info("message=:"+message+"/"+"codeUrl=:"+codeUrl+"/"+"dest=:"+dest.size()+"token=:"+token);
		if(token != null && !token.isEmpty() && !dest.isEmpty()){
			String url = kfMsgUrl+token;
			for(String openId:dest){
				HttpHeaders headers = createHeaders();				
				WechatNewsModel news = new WechatNewsModel();
				setNewsModel(news,openId,message,codeUrl);
				try {
					String json = JsonUtil.object2JsonString(news);	
					HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);					
					ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,String.class);
					logger.info("response from wechat: " + response.getBody());
				} catch (JsonProcessingException e) {
					logger.error("exception json processing ");
				}
			}
		}
	}
	
	public void setNewsModel(WechatNewsModel news,String openId,String message,String codeUrl){
		List<WechatArticleItemModel> articles = new ArrayList<WechatArticleItemModel>(); 
		WechatArticleItemModel article = new WechatArticleItemModel();
		WechatArticleModel art = new WechatArticleModel();
		article.setDescription("FEEDS发运调度系统通知");
		article.setPicurl("");
		if(codeUrl != null){
			article.setUrl(codeUrl);
		}else{
			article.setUrl("");
		}
		article.setTitle(message);
		articles.add(article);
		art.setArticles(articles);
		news.setNews(art);
		news.setTouser(openId);
	}
	
	
	public HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		Charset utf8 = Charset.forName("UTF-8");
		MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", utf8);
		// new MediaType("application", "json", StandardCharsets.UTF_8)
		headers.setContentType(mediaType);
		return headers;
	}

}
