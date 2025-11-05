package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Formulario;
import br.com.fiap.steelpulse.domain.service.FormularioService;

import java.util.ArrayList;

public class FormularioControllerImpl implements FormularioController {

    private final FormularioService formularioService;

    public FormularioControllerImpl(FormularioService formularioService) {
        this.formularioService = formularioService;
    }

    @Override
    public Formulario criarPergunta(String titulo, String autor, String assunto, String email, String celular) {
        return formularioService.criarPergunta(titulo, autor, assunto, email, celular);
    }

    @Override
    public Formulario responderPergunta(int id, String resposta, int autor) {
        return formularioService.responderPergunta(id, resposta, autor);
    }

    @Override
    public void deletarPergunta(int id) {
        formularioService.deletarPergunta(id);
    }

    @Override
    public ArrayList<Formulario> listarPerguntas() {
        return formularioService.listarPerguntas();
    }
}
