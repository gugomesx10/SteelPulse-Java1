package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Paciente;
import br.com.fiap.steelpulse.domain.service.PacienteService;

import java.util.ArrayList;

public class PacienteControllerImpl implements PacienteController {

    private final PacienteService pacienteService;

    public PacienteControllerImpl(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Override
    public Paciente criarUsuario(String nome, String email, String senha, boolean funcionario) {
        return pacienteService.criarUsuario(nome, email, senha, funcionario);
    }

    @Override
    public Paciente alterarNome(int id, String nome) {
        return pacienteService.alterarNome(id, nome);
    }

    @Override
    public Paciente alterarEmail(String email) {
        return pacienteService.alterarEmail(email);
    }

    @Override
    public ArrayList<Paciente> listarUsuarios() {
        return pacienteService.listarUsuarios();
    }

    @Override
    public Paciente login(String email, String senha) {
        return pacienteService.login(email, senha);
    }

    @Override
    public void deletarUsuario(int id) {
        pacienteService.deletarUsuario(id);
    }
}
