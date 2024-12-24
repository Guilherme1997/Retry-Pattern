	 
package com.consulta.emprestimo.config;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${consulta.emprestimo.exchange}")
	private String consultaEmprestimoExchange;

	@Value("${consulta.emprestimo.queue}")
	private String consultaEmprestimoQueue;

	@Value("${consulta.emprestimo.key}")
	private String consultaEmprestimoKey;

	@Value("${consulta.emprestimo.dead.letter.exchange}")
	private String consultaEmprestimoDeadLetterExchange;

	@Value("${consulta.emprestimo.dead.letter.queue}")
	private String consultaEmprestimoDeadLetterQueue;

	@Value("${consulta.emprestimo.parking.lot}")
	private String consultaEmprestimoParkingLot;

	@Value("${consulta.emprestimo.parking.lot.key}")
	private String consultaEmprestimoParkingLotKey;

	@Value("${consulta.emprestimo.dead.letter.queue.key}")
	private String consultaEmprestimoDeadLetterQueueKey;

	@Value("${consulta.emprestimo.dead.letter.queue.time.to.live}")
	private String timeToLive;


	@Bean
	DirectExchange deadLetterExchange() {
		return new DirectExchange(consultaEmprestimoDeadLetterExchange);
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(consultaEmprestimoExchange);
	}

	@Bean
	Queue dlq() {
		return QueueBuilder.durable(consultaEmprestimoDeadLetterQueue)
				.withArgument("x-dead-letter-exchange", consultaEmprestimoExchange)
				.withArgument("x-dead-letter-routing-key", consultaEmprestimoKey)
				.withArgument("x-message-ttl", timeToLive)
				.build();
	}

	@Bean
	Queue queue() {
		return QueueBuilder.durable(consultaEmprestimoQueue)
				.withArgument("x-dead-letter-exchange", consultaEmprestimoDeadLetterExchange)
				.withArgument("x-dead-letter-routing-key", consultaEmprestimoDeadLetterQueueKey).build();
	}
	
	@Bean
	Queue parkingLOt() {
		return QueueBuilder.durable(consultaEmprestimoParkingLot).build();
	}
	
	@Bean
	Binding parkingLOtBinding() {
		return BindingBuilder.bind(parkingLOt()).to(deadLetterExchange()).with(consultaEmprestimoParkingLotKey);
	}

	@Bean
	Binding DLQbinding() {
		return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with(consultaEmprestimoDeadLetterQueueKey);
	}

	@Bean
	Binding binding() {
		return BindingBuilder.bind(queue()).to(exchange()).with(consultaEmprestimoKey);
	}
	
	@Bean
	public Jackson2JsonMessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}

}