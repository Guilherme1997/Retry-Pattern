package com.consulta.emprestimo.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ClienteResponse {
    private String nomeCompleto;
    private String cpf;
    private String documentoIdentidade;
    private String endereco;
    private String telefone;
    private LocalDate dataNascimento;
    private String email;
    private String statusCredito;
    private boolean elegivelParaEmprestimo;
    private boolean fraudesDetectadas;
    private LocalDate dataUltimaVerificacao;
    private double limiteCredito;
    private boolean identidadeConfirmada;
}
