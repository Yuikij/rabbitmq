package com.shp.commons.web.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.shp.commons.web.service.MessageService;
import com.shp.commons.web.service.SMSMsgSendService;
import com.shp.commons.web.service.VoiceMsgSendService;
import com.shp.commons.web.service.WechatMsgSendService;

import freemarker.template.Configuration;

@Service
public class MessageServiceImpl implements MessageService {

	private static final Logger logger = Logger.getLogger(MessageServiceImpl.class);
	
	@Autowired
	Configuration freemarkerConfiguration;

	@Autowired
	SMSMsgSendService sMSMsgSendService;
	
	@Autowired
	VoiceMsgSendService voiceMsgSendService;
	
	@Autowired
	WechatMsgSendService wechatMsgSendService;
	
	@Override
	@Async
	public void sendSMS(Object object) {
		sMSMsgSendService.sendMessage(object);
	}

	@Override
	@Async
	public void sendWechat(Object object) {
		StringBuffer content = new StringBuffer();
		@SuppressWarnings("unchecked")
		Map<String, Object> model = (Map<String, Object>) object;
		String action = (String) model.get("action");
		try {
			content.append(FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(action), model));
			String message = content.toString();
			if(message != null && !message.isEmpty()){
				model.put("message", message);
				wechatMsgSendService.sendMessage(model);
			}else{
				logger.error("null template");
			}

		} catch (Exception e) {
			logger.error("free marker template processing error: " + e.getMessage());
		}
	}

	@Override
	@Async
	public void voiceCall(Object object) {
		voiceMsgSendService.sendMessage(object);
	}

	@Override
	@Async
	public void sendEmail(Object object) {
		// TODO Auto-generated method stub

	}

}
