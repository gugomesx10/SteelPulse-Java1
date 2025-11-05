package br.com.fiap.steelpulse.domain.service;

import br.com.fiap.steelpulse.domain.model.Usuario;

import java.util.ArrayList;

public interface UsuarioService {
    Usuario criarUsuario(String nome, String email, String senha, boolean funcionario );
    Usuario alterarNome(int id, String nome);
    Usuario alterarEmail(String email);
    ArrayList<Usuario> listarUsuarios();
    void deletarUsuario(int id);
    Usuario login(String email, String senha);
}
