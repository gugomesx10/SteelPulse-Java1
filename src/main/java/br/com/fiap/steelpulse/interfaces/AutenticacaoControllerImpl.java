package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Autenticacao;
import br.com.fiap.steelpulse.domain.service.AutenticacaoService;

import java.util.ArrayList;

public class AutenticacaoControllerImpl implements AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoControllerImpl(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @Override
    public Autenticacao createAcesso(int paginaId, int idUsuario) {
        return autenticacaoService.criarAcesso(paginaId, idUsuario);
    }

    @Override
    public void deletarAcesso(int idAcesso) {
        autenticacaoService.deletarAcesso(idAcesso);
    }

    @Override
    public Autenticacao atualizarAcesso(int idAcesso, Autenticacao autenticacao) {
        return autenticacaoService.editarAcesso(idAcesso, autenticacao);
    }

    @Override
    public ArrayList<Autenticacao> listarAcessos() {
        return autenticacaoService.listarAcessos();
    }

    @Override
    public ArrayList<Autenticacao> listarAcessosPorUsuario(int userId) {
        return autenticacaoService.listarAcessosPorUsuario(userId);
    }
}
