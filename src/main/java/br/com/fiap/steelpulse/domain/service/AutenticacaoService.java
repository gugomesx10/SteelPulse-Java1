package br.com.fiap.steelpulse.domain.service;

import br.com.fiap.steelpulse.domain.model.Autenticacao;

import java.util.ArrayList;

public interface AutenticacaoService {
    Autenticacao criarAcesso(int idPagina, int idUsuario);
    Autenticacao editarAcesso(int idPagina, Autenticacao autenticacao);
    void deletarAcesso(int idAcesso);
    ArrayList<Autenticacao> listarAcessos();
    ArrayList<Autenticacao> listarAcessosPorUsuario(int userId);
}
