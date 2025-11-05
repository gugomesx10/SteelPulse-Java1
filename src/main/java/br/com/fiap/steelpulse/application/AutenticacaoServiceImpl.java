package br.com.fiap.steelpulse.application;

import br.com.fiap.steelpulse.domain.model.Autenticacao;
import br.com.fiap.steelpulse.domain.repository.AutenticacaoRepository;
import br.com.fiap.steelpulse.domain.service.AutenticacaoService;

import java.util.ArrayList;
import java.util.Date;

public class AutenticacaoServiceImpl implements AutenticacaoService {

    private final AutenticacaoRepository autenticacaoRepository;

    public AutenticacaoServiceImpl(AutenticacaoRepository autenticacaoRepository) {
        this.autenticacaoRepository = autenticacaoRepository;
    }

    @Override
    public Autenticacao criarAcesso(int idPagina, int idUsuario) {
        Autenticacao autenticacao = new Autenticacao(new Date(), idPagina);
        autenticacao.setIdUsuario(idUsuario);
        return autenticacaoRepository.criarAcesso(autenticacao);
    }

    @Override
    public Autenticacao editarAcesso(int idAcesso, Autenticacao autenticacao) {
        autenticacao.setId(idAcesso);
        return autenticacaoRepository.editarAcesso(autenticacao, idAcesso);
    }

    @Override
    public void deletarAcesso(int idAcesso) {
        autenticacaoRepository.deletarAcesso(idAcesso);
    }

    @Override
    public ArrayList<Autenticacao> listarAcessos() {
        return autenticacaoRepository.listarAcesso();
    }

    @Override
    public ArrayList<Autenticacao> listarAcessosPorUsuario(int userId){
        return autenticacaoRepository.listarByIdUsuario(userId);
    }
}
