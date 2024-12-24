package com.consulta.emprestimo.service;

import java.util.List;
import java.util.Map;

import com.consulta.emprestimo.exception.InvalidRequestException;
import com.consulta.emprestimo.request.ClienteRequest;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ConsultaEmprestimoConsumer {
	
    @Autowired
    private RabbitTemplate rabbitTemplate;

	@Value("${consulta.emprestimo.dead.letter.exchange}")
	private String consultaEmprestimoDeadLetterExchange;

	@Value("${consulta.emprestimo.parking.lot.key}")
	private String consultaEmprestimoParkingLotKey;

	@Value("${consulta.emprestimo.tempo.reprocessamento}")
	private int consultaEmprestimoTempoProcessamento;

	@RabbitListener(queues = "${consulta.emprestimo.queue}")
	public void recievedMessage(ClienteRequest clienteRequest, Message message) throws InvalidRequestException {
		if(hasExceededRetry(message)) {
			this.rabbitTemplate.send(consultaEmprestimoDeadLetterExchange,consultaEmprestimoParkingLotKey,message);
			return;
		}
		obterDadosDoParceiro(clienteRequest);
	}

	private void obterDadosDoParceiro(ClienteRequest clienteRequest) throws InvalidRequestException {
		//Estamos simulando uma indisponibilidade do parceiro para a POC.
		throw new InvalidRequestException();
	}
	
	private boolean hasExceededRetry(Message message) {
		final List<Map<String,?>> xDeathHeader = message.getMessageProperties().getXDeathHeader();
		if(xDeathHeader != null && !xDeathHeader.isEmpty()) {
			final Long count = (Long) xDeathHeader.getFirst().get("count");
			return count >= consultaEmprestimoTempoProcessamento;
		}
		return false;
	}
}