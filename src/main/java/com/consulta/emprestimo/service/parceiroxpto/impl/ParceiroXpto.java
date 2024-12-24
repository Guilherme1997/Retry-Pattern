package com.consulta.emprestimo.service.parceiroxpto.impl;

import com.consulta.emprestimo.request.ClienteRequest;
import com.consulta.emprestimo.response.ClienteResponse;

public interface ParceiroXpto {

    ClienteResponse obterDadosCliente(ClienteRequest clienteRequest);
}
