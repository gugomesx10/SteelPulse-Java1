package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Acesso;
import br.com.fiap.steelpulse.domain.service.AcessoService;

import java.util.ArrayList;

public class AcessoControllerImpl implements AcessoController {

    private final AcessoService acessoService;

    public AcessoControllerImpl(AcessoService acessoService) {
        this.acessoService = acessoService;
    }

    @Override
    public Acesso createAcesso(int paginaId, int idUsuario) {
        return acessoService.criarAcesso(paginaId, idUsuario);
    }

    @Override
    public void deletarAcesso(int idAcesso) {
        acessoService.deletarAcesso(idAcesso);
    }

    @Override
    public Acesso atualizarAcesso(int idAcesso, Acesso acesso) {
        return acessoService.editarAcesso(idAcesso, acesso);
    }

    @Override
    public ArrayList<Acesso> listarAcessos() {
        return acessoService.listarAcessos();
    }

    @Override
    public ArrayList<Acesso> listarAcessosPorUsuario(int userId) {
        return acessoService.listarAcessosPorUsuario(userId);
    }
}
