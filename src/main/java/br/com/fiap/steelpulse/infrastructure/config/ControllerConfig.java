package br.com.fiap.steelpulse.infrastructure.config;

import br.com.fiap.steelpulse.domain.service.AutenticacaoService;
import br.com.fiap.steelpulse.domain.service.AgendamentoService;
import br.com.fiap.steelpulse.domain.service.FormularioService;
import br.com.fiap.steelpulse.domain.service.PacienteService;
import br.com.fiap.steelpulse.interfaces.*;
import jakarta.enterprise.context.ApplicationScoped;

public class ControllerConfig {

    @ApplicationScoped
    public AutenticacaoController acessoController(AutenticacaoService autenticacaoService) {
        return new AutenticacaoControllerImpl(autenticacaoService);
    }

    @ApplicationScoped
    public AgendamentoController agendamentoController(AgendamentoService agendamentoService) {
        return new AgendamentoControllerImpl(agendamentoService);
    }

    @ApplicationScoped
    public FormularioController perguntaController(FormularioService formularioService) {
        return new FormularioControllerImpl(formularioService);
    }

    @ApplicationScoped
    public PacienteController usuarioController(PacienteService pacienteService) {
        return new PacienteControllerImpl(pacienteService);
    }
}
