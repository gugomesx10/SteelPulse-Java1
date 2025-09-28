package br.com.fiap.steelpulse.domain.repository;

import br.com.fiap.steelpulse.domain.exceptions.EntidadeNaoLocalizada;
import br.com.fiap.steelpulse.domain.model.Paciente;
import java.util.List;

public interface PacienteRepository {
    Paciente salvar(Paciente paciente);
    Paciente buscarPorCpf(String cpf) throws EntidadeNaoLocalizada;
    Paciente editar(Paciente paciente);
    List<Paciente> buscarTodos();
    void desativar(String cpf, Long versao);
    void reativar(String cpf, Long versao);
}
