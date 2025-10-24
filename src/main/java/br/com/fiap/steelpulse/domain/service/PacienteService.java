package br.com.fiap.steelpulse.domain.service;

import br.com.fiap.steelpulse.domain.model.Paciente;

import java.util.List;

public interface PacienteService {

    Paciente criar(Paciente paciente);
    void desativar(Long id, Long version);

    List<Paciente> listar();

    Paciente buscarPorCpf(String cpf);

    Paciente atualizar(String cpf, Paciente paciente);

    void deletar(String cpf);
}
