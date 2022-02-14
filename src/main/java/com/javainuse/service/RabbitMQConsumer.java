package com.javainuse.service;

import java.util.List;
import java.util.Map;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.javainuse.exception.InvalidSalaryException;

@Component
public class RabbitMQConsumer {
	
    @Autowired
    private RabbitTemplate rabbitTemplate;
       
	@RabbitListener(queues = "javainuse.queue")
	public void recievedMessage(Message message) throws InvalidSalaryException {
		
		if(hasExceededRetry(message)) {
			final List<Map<String,?>> xDeathHeader = message.getMessageProperties().getXDeathHeader();
			System.out.println(xDeathHeader);
			return;
		}
     
		if (1==1) {
			this.rabbitTemplate.send("deadLetterExchange","parkingLotKey",message);
			throw new InvalidSalaryException();
		}
		
		System.out.println(message);


	}
	
	private boolean hasExceededRetry(Message message) {
		
		final List<Map<String,?>> xDeathHeader = message.getMessageProperties().getXDeathHeader();
		
		if(xDeathHeader != null && xDeathHeader.size() >=1) {
			final Long count = (Long) xDeathHeader.get(0).get("count");
			return count >= 3;
		}
		
		return false;
		
	}
	
}