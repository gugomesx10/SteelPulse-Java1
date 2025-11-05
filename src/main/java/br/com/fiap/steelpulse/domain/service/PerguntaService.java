package br.com.fiap.steelpulse.domain.service;

import br.com.fiap.steelpulse.domain.model.Pergunta;

import java.util.ArrayList;
import java.util.Date;

public interface PerguntaService {
    Pergunta criarPergunta(String titulo, String autor, String assunto, String email, String celular);
    Pergunta responderPergunta(int id, String resposta, int autor);
    void deletarPergunta(int id);
    ArrayList<Pergunta> listarPerguntas();

}
