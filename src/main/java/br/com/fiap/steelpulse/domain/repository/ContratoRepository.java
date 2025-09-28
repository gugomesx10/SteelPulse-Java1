package br.com.fiap.steelpulse.domain.repository;

import br.com.fiap.steelpulse.domain.exceptions.EntidadeNaoLocalizada;
import br.com.fiap.steelpulse.domain.model.Contrato;
import java.util.List;

public interface ContratoRepository {
    void salvar(Contrato contrato);
    Contrato buscarPorId(Long id) throws EntidadeNaoLocalizada;
    Contrato editar(Contrato contrato);
    List<Contrato> buscarPorPacienteCpf(String cpf);
    Contrato buscarAtivoPorPacienteCpf(String cpf);

    List<Contrato> buscarPorClienteCpf(String cpf);

    Contrato buscarAtivoPorClienteCpf(String cpf);

    void finalizar(Long id, Long version);
}
