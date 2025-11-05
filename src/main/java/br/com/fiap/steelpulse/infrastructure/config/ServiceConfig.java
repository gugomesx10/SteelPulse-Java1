
package br.com.fiap.steelpulse.infrastructure.config;

import br.com.fiap.steelpulse.application.*;
import br.com.fiap.steelpulse.domain.repository.AutenticacaoRepository;
import br.com.fiap.steelpulse.domain.repository.AgendamentoRepository;
import br.com.fiap.steelpulse.domain.repository.FormularioRepository;
import br.com.fiap.steelpulse.domain.repository.PacienteRepository;
import br.com.fiap.steelpulse.domain.service.*;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServiceConfig {

    @ApplicationScoped
    public AutenticacaoService acessoService(AutenticacaoRepository autenticacaoRepository) {
        return new AutenticacaoServiceImpl(autenticacaoRepository);
    }

    @ApplicationScoped
    public AgendamentoService agendamentoService(AgendamentoRepository agendamentoRepository) {
        return new AgendamentoServiceImpl(agendamentoRepository);
    }

    @ApplicationScoped
    public FormularioService perguntaService(FormularioRepository formularioRepository) {
        return new FormularioServiceImpl(formularioRepository);
    }

    @ApplicationScoped
    public PacienteService usuarioService(PacienteRepository pacienteRepository, AgendamentoRepository agendamentoRepository,
                                          FormularioRepository formularioRepository, AutenticacaoRepository autenticacaoRepository) {
        return new PacienteServiceImpl(pacienteRepository, formularioRepository, agendamentoRepository, autenticacaoRepository);
    }
}
