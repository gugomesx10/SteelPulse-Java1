package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Paciente;
import java.util.List;

public interface PacienteController {
    Paciente criarPaciente(Paciente paciente);
    List<Paciente> listarPacientes();
    Paciente buscarPorCpf(String cpf);
    Paciente atualizarPaciente(String cpf, Paciente paciente);
    void deletarPaciente(String cpf);
}
