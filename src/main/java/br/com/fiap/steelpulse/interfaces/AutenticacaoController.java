package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Autenticacao;

import java.util.ArrayList;

public interface AutenticacaoController {

    Autenticacao createAcesso(int paginaId, int idUsuario);

    void deletarAcesso(int idAcesso);

    Autenticacao atualizarAcesso(int idPagina, Autenticacao autenticacao);

    ArrayList<Autenticacao> listarAcessos();

    ArrayList<Autenticacao> listarAcessosPorUsuario(int userId);
}
