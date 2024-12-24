package com.consulta.emprestimo.service.parceiroxpto.impl;

import com.consulta.emprestimo.request.ClienteRequest;
import com.consulta.emprestimo.response.ClienteResponse;

import java.time.LocalDate;

public class ParceiroXptoImpl implements ParceiroXpto{
    @Override
    public ClienteResponse obterDadosCliente(ClienteRequest clienteRequest) {
       return ClienteResponse.builder()
                .nomeCompleto("João Silva")
                .cpf("123.456.789-00")
                .documentoIdentidade("MG12345678")
                .endereco("Rua das Flores, 123")
                .telefone("31987654321")
                .dataNascimento(LocalDate.of(1990, 5, 15))
                .email("joao.silva@example.com")
                .statusCredito("Aprovado")
                .elegivelParaEmprestimo(true)
                .fraudesDetectadas(false)
                .dataUltimaVerificacao(LocalDate.now())
                .limiteCredito(5000.0)
                .identidadeConfirmada(true)
                .build();
    }
}
