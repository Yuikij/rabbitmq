package com.shp.commons.web.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shp.commons.web.model.MessageModel;
@Component
public class HeheSender {
	   @Autowired
	    private AmqpTemplate rabbitTemplate;
	
	   public void send() {
		      String msg1 = "I am topic.mesaage msg======";
		      MessageModel messageModel = new MessageModel();
		      messageModel.setAction("call");
		      messageModel.setDoor("11");
		      messageModel.setMobile("18114926281");
		      messageModel.setPlateNo("11");
		      messageModel.setProgress("11");
		      
		
		    	  this.rabbitTemplate.convertAndSend("hehehe1", "topic.message", msg1);
		      
		     
	        

	    }
	
}
