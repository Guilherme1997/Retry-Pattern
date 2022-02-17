package com.javainuse.service;

import java.util.List;
import java.util.Map;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.javainuse.exception.InvalidSalaryException;
import com.javainuse.model.Employee;

@Component
public class RabbitMQConsumer {
	
    @Autowired
    private RabbitTemplate rabbitTemplate;
       
	@RabbitListener(queues = "javainuse.queue")
	public void recievedMessage(Employee employee, Message message) throws InvalidSalaryException {
		
		if(hasExceededRetry(message)) {
			this.rabbitTemplate.send("deadLetterExchange","parkingLotKey",message);
			return;
		}
     
		if (employee.getSalary() == 10) {
			throw new InvalidSalaryException();
		}
		
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