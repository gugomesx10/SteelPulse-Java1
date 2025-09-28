package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Paciente;
import br.com.fiap.steelpulse.domain.service.PacienteService;

public class PacienteControllerImpl implements PacienteController {

    private final PacienteService pacienteService;

    public PacienteControllerImpl(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Override
    public Paciente criarPaciente(Paciente paciente) {
        return pacienteService.criar(paciente);
    }
}
