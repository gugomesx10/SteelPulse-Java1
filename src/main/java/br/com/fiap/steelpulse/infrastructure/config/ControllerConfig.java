package br.com.fiap.steelpulse.infrastructure.config;

import br.com.fiap.steelpulse.domain.model.Agendamento;
import br.com.fiap.steelpulse.domain.service.AcessoService;
import br.com.fiap.steelpulse.domain.service.AgendamentoService;
import br.com.fiap.steelpulse.domain.service.PerguntaService;
import br.com.fiap.steelpulse.domain.service.UsuarioService;
import br.com.fiap.steelpulse.interfaces.*;
import jakarta.enterprise.context.ApplicationScoped;

public class ControllerConfig {

    @ApplicationScoped
    public AcessoController acessoController(AcessoService acessoService) {
        return new AcessoControllerImpl(acessoService);
    }

    @ApplicationScoped
    public AgendamentoController agendamentoController(AgendamentoService agendamentoService) {
        return new AgendamentoControllerImpl(agendamentoService);
    }

    @ApplicationScoped
    public PerguntaController perguntaController(PerguntaService perguntaService) {
        return new PerguntaControllerImpl(perguntaService);
    }

    @ApplicationScoped
    public UsuarioController usuarioController(UsuarioService usuarioService) {
        return new UsuarioControllerImpl(usuarioService);
    }
}
