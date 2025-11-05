package br.com.fiap.steelpulse.domain.repository;

import br.com.fiap.steelpulse.domain.model.Acesso;

import java.util.ArrayList;

public interface AcessoRepository {
    Acesso criarAcesso(Acesso acesso);

    Acesso editarAcesso(Acesso acesso, int idAcesso);

    void deletarAcesso(int id);

    ArrayList<Acesso> listarAcesso();

    ArrayList<Acesso> listarByIdUsuario(int idUsuario);
}
