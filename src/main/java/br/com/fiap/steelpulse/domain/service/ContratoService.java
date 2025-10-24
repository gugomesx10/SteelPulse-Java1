package br.com.fiap.steelpulse.domain.service;

import br.com.fiap.steelpulse.domain.model.Contrato;
import java.util.List;

public interface ContratoService {

    void criar(Contrato contrato);

    Contrato buscarPorId(Long id);

    Contrato atualizar(Contrato contrato);

    List<Contrato> listarPorPaciente(String cpf);

    Contrato buscarAtivoPorPaciente(String cpf);

    void finalizarContrato(Long id, Long version);
}
