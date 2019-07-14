package com.spring.cloudamqp.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.spring.cloudamqp.SimpleMessageDto;

@Component
public class SimpleMessageListener {
	
	@RabbitListener(queues = "MyQueue")
    public void processOrder(SimpleMessageDto message) {
        System.out.println(message.getName() +" : " + message.getDescription() );
    }

}
