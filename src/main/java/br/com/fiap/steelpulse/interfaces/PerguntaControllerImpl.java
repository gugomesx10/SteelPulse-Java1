package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Pergunta;
import br.com.fiap.steelpulse.domain.service.PerguntaService;

import java.util.ArrayList;
import java.util.Date;

public class PerguntaControllerImpl implements PerguntaController {

    private final PerguntaService perguntaService;

    public PerguntaControllerImpl(PerguntaService perguntaService) {
        this.perguntaService = perguntaService;
    }

    @Override
    public Pergunta criarPergunta(String titulo, String autor, String assunto, String email, String celular) {
        return perguntaService.criarPergunta(titulo, autor, assunto, email, celular);
    }

    @Override
    public Pergunta responderPergunta(int id, String resposta, int autor) {
        return perguntaService.responderPergunta(id, resposta, autor);
    }

    @Override
    public void deletarPergunta(int id) {
        perguntaService.deletarPergunta(id);
    }

    @Override
    public ArrayList<Pergunta> listarPerguntas() {
        return perguntaService.listarPerguntas();
    }
}
