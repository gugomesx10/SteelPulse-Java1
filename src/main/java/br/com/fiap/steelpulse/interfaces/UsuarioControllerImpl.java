package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Usuario;
import br.com.fiap.steelpulse.domain.service.UsuarioService;

import java.util.ArrayList;

public class UsuarioControllerImpl implements UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioControllerImpl(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public Usuario criarUsuario(String nome, String email, String senha, boolean funcionario) {
        return usuarioService.criarUsuario(nome, email, senha, funcionario);
    }

    @Override
    public Usuario alterarNome(int id, String nome) {
        return usuarioService.alterarNome(id, nome);
    }

    @Override
    public Usuario alterarEmail(String email) {
        return usuarioService.alterarEmail(email);
    }

    @Override
    public ArrayList<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @Override
    public Usuario login(String email, String senha) {
        return usuarioService.login(email, senha);
    }

    @Override
    public void deletarUsuario(int id) {
        usuarioService.deletarUsuario(id);
    }
}
