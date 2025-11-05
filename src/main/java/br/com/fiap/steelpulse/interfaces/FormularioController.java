package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Formulario;

import java.util.ArrayList;

public interface FormularioController {
    Formulario criarPergunta(String titulo, String autor, String assunto, String email, String celular);
    Formulario responderPergunta(int id, String resposta, int autor);
    void deletarPergunta(int id);
    ArrayList<Formulario> listarPerguntas();
}
