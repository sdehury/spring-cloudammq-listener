package com.spring.cloudamqp.listener.config;

import java.net.URI;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import com.spring.cloudamqp.listener.RabbitMqMessageListener;

@Configuration
public class RabbitMqListenerConfig implements RabbitListenerConfigurer {

	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.vhost}")
	private String vhost;

	@Value("${spring.rabbitmq.username}")
	private String username;

	@Value("${spring.rabbitmq.password}")
	private String password;

	@Value("${spring.rabbitmq.port}")
	private int port;

	@Value("${spring.rabbitmq.uri}")
	private URI uri;

	private static final String MY_QUEUE = "MyQueue";

	@Bean
	Queue myQueue() {
		return new Queue(MY_QUEUE, true);
	}

	@Bean
	Exchange topicExchangeQ() {
		return ExchangeBuilder.topicExchange("TopicExchange").durable(true).build();
	}

	@Bean
	Binding binding() {
		return BindingBuilder.bind(myQueue()).to(topicExchangeQ()).with("topic").noargs();
	}

	@Bean
	ConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(uri);

		return cachingConnectionFactory;
	}

	@Bean
	MessageListenerContainer msageListenerContainer() {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
		simpleMessageListenerContainer.setQueues(myQueue());
		simpleMessageListenerContainer.setMessageListener(new RabbitMqMessageListener());

		return simpleMessageListenerContainer;
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}

	@Bean
	MessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
		messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
		return messageHandlerMethodFactory;
	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

}
