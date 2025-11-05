package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Acesso;

import java.util.ArrayList;

public interface AcessoController {

    Acesso createAcesso(int paginaId, int idUsuario);

    void deletarAcesso(int idAcesso);

    Acesso atualizarAcesso(int idPagina, Acesso acesso);

    ArrayList<Acesso> listarAcessos();

    ArrayList<Acesso> listarAcessosPorUsuario(int userId);
}
