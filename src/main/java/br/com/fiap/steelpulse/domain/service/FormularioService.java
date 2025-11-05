package br.com.fiap.steelpulse.domain.service;

import br.com.fiap.steelpulse.domain.model.Formulario;

import java.util.ArrayList;

public interface FormularioService {
    Formulario criarPergunta(String titulo, String autor, String assunto, String email, String celular);
    Formulario responderPergunta(int id, String resposta, int autor);
    void deletarPergunta(int id);
    ArrayList<Formulario> listarPerguntas();

}
