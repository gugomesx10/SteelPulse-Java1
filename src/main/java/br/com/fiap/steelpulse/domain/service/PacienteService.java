package br.com.fiap.steelpulse.domain.service;

import br.com.fiap.steelpulse.domain.model.Paciente;

public interface PacienteService {

    Paciente criar(Paciente paciente);
    void desativar(Long id, Long version);
}
