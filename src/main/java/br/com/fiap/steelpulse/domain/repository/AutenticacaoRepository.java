package br.com.fiap.steelpulse.domain.repository;

import br.com.fiap.steelpulse.domain.model.Autenticacao;

import java.util.ArrayList;

public interface AutenticacaoRepository {
    Autenticacao criarAcesso(Autenticacao autenticacao);

    Autenticacao editarAcesso(Autenticacao autenticacao, int idAcesso);

    void deletarAcesso(int id);

    ArrayList<Autenticacao> listarAcesso();

    ArrayList<Autenticacao> listarByIdUsuario(int idUsuario);
}
