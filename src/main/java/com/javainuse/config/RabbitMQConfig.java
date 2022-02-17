	 
package com.javainuse.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Bean
	DirectExchange deadLetterExchange() {
		return new DirectExchange("deadLetterExchange");
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange("javainuseExchange");
	}

	@Bean
	Queue dlq() {
		return QueueBuilder.durable("deadLetter.queue")
				.withArgument("x-dead-letter-exchange", "javainuseExchange")
				.withArgument("x-dead-letter-routing-key", "javainuse")
				.withArgument("x-message-ttl", 10000)
				.build();
	}

	@Bean
	Queue queue() {
		return QueueBuilder.durable("javainuse.queue")
				.withArgument("x-dead-letter-exchange", "deadLetterExchange")
				.withArgument("x-dead-letter-routing-key", "deadLetter").build();
	}
	
	@Bean
	Queue parkingLOt() {
		return QueueBuilder.durable("parkingLot").build();
	}
	
	@Bean
	Binding parkingLOtBinding() {
		return BindingBuilder.bind(parkingLOt()).to(deadLetterExchange()).with("parkingLotKey");
	}

	@Bean
	Binding DLQbinding() {
		return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("deadLetter");
	}

	@Bean
	Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with("javainuse");
	}
	
	@Bean
	public Jackson2JsonMessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}
	
	
	

}