package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Usuario;

import java.util.ArrayList;

public interface UsuarioController {
    Usuario criarUsuario(String nome, String email, String senha, boolean funcionario);
    Usuario alterarNome(int id, String nome);
    Usuario alterarEmail(String email);
    ArrayList<Usuario> listarUsuarios();
    Usuario login(String email, String senha);
    void deletarUsuario(int id);
}