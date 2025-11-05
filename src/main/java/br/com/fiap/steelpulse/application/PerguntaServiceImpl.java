package br.com.fiap.steelpulse.application;

import br.com.fiap.steelpulse.domain.model.Pergunta;
import br.com.fiap.steelpulse.domain.repository.PerguntaRepository;
import br.com.fiap.steelpulse.domain.service.PerguntaService;
import br.com.fiap.steelpulse.infrastructure.exceptions.PerguntaException;

import java.util.ArrayList;
import java.util.Date;

public class PerguntaServiceImpl implements PerguntaService {

    private final PerguntaRepository perguntaRepository;

    public PerguntaServiceImpl(PerguntaRepository perguntaRepository) {
        this.perguntaRepository = perguntaRepository;
    }

    @Override
    public Pergunta criarPergunta(String titulo, String autor, String assunto, String email, String celular) {
        return perguntaRepository.criarPergunta( titulo,  autor,  new Date(), assunto, email, celular);
    }

    @Override
    public Pergunta responderPergunta(int id, String resposta, int autor) {
        return perguntaRepository.responderPergunta(id, resposta, autor);
    }

    @Override
    public void deletarPergunta(int id) {
        perguntaRepository.deletarPergunta(id);
    }

    @Override
    public ArrayList<Pergunta> listarPerguntas() {
        return perguntaRepository.listarPerguntas();
    }
}
