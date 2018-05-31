package com.shp.commons;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shp.commons.web.controller.HeheSender;
import com.shp.commons.web.service.MessageService;





@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AppTest {
	@Autowired
	  MessageService messageService;
	  @Autowired
	    private HeheSender helloSender;


	@Test
	public void contextLoads()  throws IOException, TimeoutException, InterruptedException{
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		factory.setUsername("guest");
		factory.setPassword("guest");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel(); 
		channel.exchangeDeclare("hehehe", "topic");
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, "hehehe", "American.*.13");
	    channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
		        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
		            //System.out.println(new String(body, "UTF-8"));
		        	Map<String, Object> messageModel = (HashMap)SerializationUtils.deserialize(body);
		            System.out.println(messageModel.get("door"));
		            messageService.sendSMS(messageModel);
		        }
		        
		});
		
	    synchronized (this){
            // 因为以上接收消息的方法是异步的（非阻塞），当采用单元测试方式执行该方法时，程序会在打印消息前结束，因此使用wait来防止程序提前终止。若使用main方法执行，则不需要担心该问题。
            wait();
        }
		
		
		
		
		
//		Map<String, Object> messageModel = new HashMap<String, Object>();
//		
//			messageModel.put("action", "call");
//			messageModel.put("mobile", "18114926281");
//			messageModel.put("plateNo", "11");
//			messageModel.put("door", "11");
//			messageModel.put("progress", "11");
//			messageService.sendSMS(messageModel);
	
		
	}
	
//	@Test
//	public void testAll() throws IOException, TimeoutException{
////		List<User> users = userDao.getAll();
////		System.out.println(users);
//		ConnectionFactory factory = new ConnectionFactory();
//		factory.setHost("localhost");
//		Connection connection = factory.newConnection();
//		Channel channel = connection.createChannel();
//		channel.queueDeclare("hello", false, false, false, null);
//	}
//	
	@Test
	public void contextLoads_1() {
		 helloSender.send();
	}

}
