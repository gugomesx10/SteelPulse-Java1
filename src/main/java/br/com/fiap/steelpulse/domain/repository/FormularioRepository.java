package br.com.fiap.steelpulse.domain.repository;

import br.com.fiap.steelpulse.domain.model.Formulario;

import java.util.ArrayList;
import java.util.Date;

public interface FormularioRepository {
    ArrayList<Formulario> listarPerguntas();
    void deletarPergunta(int id);
    Formulario responderPergunta(int id, String resposta, int autor);
    Formulario criarPergunta(String titulo, String autor, Date date, String assunto, String email, String celular);
}
