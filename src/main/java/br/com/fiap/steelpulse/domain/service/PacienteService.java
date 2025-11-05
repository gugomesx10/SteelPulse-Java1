package br.com.fiap.steelpulse.domain.service;

import br.com.fiap.steelpulse.domain.model.Paciente;

import java.util.ArrayList;

public interface PacienteService {
    Paciente criarUsuario(String nome, String email, String senha, boolean funcionario );
    Paciente alterarNome(int id, String nome);
    Paciente alterarEmail(String email);
    ArrayList<Paciente> listarUsuarios();
    void deletarUsuario(int id);
    Paciente login(String email, String senha);
}
