package com.spring.cloudamqp.listener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.cloudamqp.SimpleMessageDto;

public class RabbitMqMessageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		
		
		// Creating Object of ObjectMapper define in Jakson Api 
		String jsonString ="";
        ObjectMapper ObjMapper = new ObjectMapper(); 
        SimpleMessageDto simpleMessage = null;
        
		try {
			Object obj = getObject(message.getBody());
			simpleMessage = (SimpleMessageDto) obj;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       
        try {
			 jsonString = ObjMapper.writeValueAsString(simpleMessage);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("message = [ "+ jsonString +  "]");		
	}
	
	/* De serialize the byte array to object */
	private static Object getObject(byte[] byteArr) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(byteArr);
		ObjectInput in = new ObjectInputStream(bis);
		return in.readObject();
	}

}
