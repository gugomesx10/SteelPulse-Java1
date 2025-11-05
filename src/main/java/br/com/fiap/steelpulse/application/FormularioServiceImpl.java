package br.com.fiap.steelpulse.application;

import br.com.fiap.steelpulse.domain.model.Formulario;
import br.com.fiap.steelpulse.domain.repository.FormularioRepository;
import br.com.fiap.steelpulse.domain.service.FormularioService;

import java.util.ArrayList;
import java.util.Date;

public class FormularioServiceImpl implements FormularioService {

    private final FormularioRepository formularioRepository;

    public FormularioServiceImpl(FormularioRepository formularioRepository) {
        this.formularioRepository = formularioRepository;
    }

    @Override
    public Formulario criarPergunta(String titulo, String autor, String assunto, String email, String celular) {
        return formularioRepository.criarPergunta( titulo,  autor,  new Date(), assunto, email, celular);
    }

    @Override
    public Formulario responderPergunta(int id, String resposta, int autor) {
        return formularioRepository.responderPergunta(id, resposta, autor);
    }

    @Override
    public void deletarPergunta(int id) {
        formularioRepository.deletarPergunta(id);
    }

    @Override
    public ArrayList<Formulario> listarPerguntas() {
        return formularioRepository.listarPerguntas();
    }
}
