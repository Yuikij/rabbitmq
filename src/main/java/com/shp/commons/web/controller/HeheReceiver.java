package com.shp.commons.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shp.commons.web.model.MessageModel;
import com.shp.commons.web.service.MessageService;
@Component
@RabbitListener(queues = "topic.message")
public class HeheReceiver {

	@Autowired
	  MessageService messageService;
	  @RabbitHandler
	    public void process(String mes) throws Exception {
		  ObjectMapper mapper=new ObjectMapper();
		  @SuppressWarnings("unchecked")
		Map<String, Object> messageModel = mapper.readValue(mes.getBytes("utf-8"),Map.class);
		  System.out.println("topicMessageReceiver  : " +messageModel.get("door"));
		  messageService.sendSMS(messageModel);;
	    }
	
}
