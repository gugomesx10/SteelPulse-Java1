package br.com.fiap.steelpulse.domain.repository;

import br.com.fiap.steelpulse.domain.model.Pergunta;

import java.util.ArrayList;
import java.util.Date;

public interface PerguntaRepository {
    ArrayList<Pergunta> listarPerguntas();
    void deletarPergunta(int id);
    Pergunta responderPergunta(int id, String resposta, int autor);
    Pergunta criarPergunta(String titulo, String autor, Date date, String assunto, String email, String celular);
}
