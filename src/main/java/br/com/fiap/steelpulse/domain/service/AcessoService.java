package br.com.fiap.steelpulse.domain.service;

import br.com.fiap.steelpulse.domain.model.Acesso;

import java.util.ArrayList;

public interface AcessoService {
    Acesso criarAcesso(int idPagina, int idUsuario);
    Acesso editarAcesso(int idPagina, Acesso acesso);
    void deletarAcesso(int idAcesso);
    ArrayList<Acesso> listarAcessos();
    ArrayList<Acesso> listarAcessosPorUsuario(int userId);
}
