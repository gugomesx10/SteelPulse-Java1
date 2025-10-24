package br.com.fiap.steelpulse.application.service;

import br.com.fiap.steelpulse.domain.exceptions.EntidadeNaoLocalizada;
import br.com.fiap.steelpulse.domain.model.Paciente;
import br.com.fiap.steelpulse.domain.repository.PacienteRepository;
import br.com.fiap.steelpulse.domain.service.PacienteService;

import java.util.List;

public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente criar(Paciente paciente) {
        try {
            this.pacienteRepository.buscarPorCpf(paciente.getCpf());
        } catch (EntidadeNaoLocalizada e) {
            Paciente pacienteSalvo = this.pacienteRepository.salvar(paciente);
            return pacienteSalvo;
        }
        throw new RuntimeException("Paciente já cadastrado");
    }

    @Override
    public void desativar(Long id, Long version) {
    }

    @Override
    public List<Paciente> listar() {
        return List.of();
    }

    @Override
    public Paciente buscarPorCpf(String cpf) {
        return null;
    }

    @Override
    public Paciente atualizar(String cpf, Paciente paciente) {
        return null;
    }

    @Override
    public void deletar(String cpf) {

    }
}
