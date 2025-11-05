package br.com.fiap.steelpulse.domain.repository;

import br.com.fiap.steelpulse.domain.model.Paciente;

import java.util.List;

public interface PacienteRepository {
    Paciente criarUsuario(String nome, String email, String senha, boolean funcionario );
    Paciente alterarSenha(String senha);
    Paciente alterarEmail(String email);
    Paciente alterarNome(int id, String novoNome);

    void deletarUsuario(int id);
    List<Paciente> listarUsuarios();

    Paciente getUsuarioByEmail(String email);
}
