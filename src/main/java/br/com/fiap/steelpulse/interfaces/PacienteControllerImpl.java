package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Paciente;
import br.com.fiap.steelpulse.domain.service.PacienteService;
import java.util.List;

public class PacienteControllerImpl implements PacienteController {

    private final PacienteService pacienteService;

    public PacienteControllerImpl(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Override
    public Paciente criarPaciente(Paciente paciente) {
        return pacienteService.criar(paciente);
    }

    @Override
    public List<Paciente> listarPacientes() {
        return pacienteService.listar();
    }

    @Override
    public Paciente buscarPorCpf(String cpf) {
        return pacienteService.buscarPorCpf(cpf);
    }

    @Override
    public Paciente atualizarPaciente(String cpf, Paciente paciente) {
        return pacienteService.atualizar(cpf, paciente);
    }

    @Override
    public void deletarPaciente(String cpf) {
        pacienteService.deletar(cpf);
    }
}
