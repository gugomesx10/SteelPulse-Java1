package br.com.fiap.steelpulse.application;

import br.com.fiap.steelpulse.domain.model.Acesso;
import br.com.fiap.steelpulse.domain.repository.AcessoRepository;
import br.com.fiap.steelpulse.domain.service.AcessoService;

import java.util.ArrayList;
import java.util.Date;

public class AcessoServiceImpl implements AcessoService {

    private final AcessoRepository acessoRepository;

    public AcessoServiceImpl(AcessoRepository acessoRepository) {
        this.acessoRepository = acessoRepository;
    }

    @Override
    public Acesso criarAcesso(int idPagina, int idUsuario) {
        Acesso acesso = new Acesso(new Date(), idPagina);
        acesso.setIdUsuario(idUsuario);
        return acessoRepository.criarAcesso(acesso);
    }

    @Override
    public Acesso editarAcesso(int idAcesso, Acesso acesso) {
        acesso.setId(idAcesso);
        return acessoRepository.editarAcesso(acesso, idAcesso);
    }

    @Override
    public void deletarAcesso(int idAcesso) {
        acessoRepository.deletarAcesso(idAcesso);
    }

    @Override
    public ArrayList<Acesso> listarAcessos() {
        return acessoRepository.listarAcesso();
    }

    @Override
    public ArrayList<Acesso> listarAcessosPorUsuario(int userId){
        return acessoRepository.listarByIdUsuario(userId);
    }
}
