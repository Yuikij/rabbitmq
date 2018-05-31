package com.shp.commons;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.shp.commons.web.service.MessageService;

public class Demo {

	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setUsername("guest");
		factory.setPassword("guest");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel(); 
//        //声明交换器
//        String exchangeName = "hello-exchange";
//        channel.exchangeDeclare(exchangeName, "direct", true);
//
//        String routingKey = "hola";
//        //发布消息
//        byte[] messageBodyBytes = "quit".getBytes();
//        channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);
		channel.exchangeDeclare("hehehe", "topic");
		 String queueName = channel.queueDeclare().getQueue();
		 channel.queueBind(queueName, "hehehe", "American.*.13");
		//channel.queueDeclare("biubiu~2", true, false, false, null);
		channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
	        @Override
	        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
	        	@SuppressWarnings({ "unchecked", "rawtypes" })
	        	Map<String, Object> messageModel = (HashMap)SerializationUtils.deserialize(body);
	            System.out.println(messageModel.get("door"));
	            
	        }
	});

  


	}

}
