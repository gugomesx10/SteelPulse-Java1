package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Paciente;

import java.util.ArrayList;

public interface PacienteController {
    Paciente criarUsuario(String nome, String email, String senha, boolean funcionario);
    Paciente alterarNome(int id, String nome);
    Paciente alterarEmail(String email);
    ArrayList<Paciente> listarUsuarios();
    Paciente login(String email, String senha);
    void deletarUsuario(int id);
}