
package br.com.fiap.steelpulse.infrastructure.config;

import br.com.fiap.steelpulse.application.*;
import br.com.fiap.steelpulse.domain.repository.AcessoRepository;
import br.com.fiap.steelpulse.domain.repository.AgendamentoRepository;
import br.com.fiap.steelpulse.domain.repository.PerguntaRepository;
import br.com.fiap.steelpulse.domain.repository.UsuarioRepository;
import br.com.fiap.steelpulse.domain.service.*;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServiceConfig {

    @ApplicationScoped
    public AcessoService acessoService(AcessoRepository acessoRepository) {
        return new AcessoServiceImpl(acessoRepository);
    }

    @ApplicationScoped
    public AgendamentoService agendamentoService(AgendamentoRepository agendamentoRepository) {
        return new AgendamentoServiceImpl(agendamentoRepository);
    }

    @ApplicationScoped
    public PerguntaService perguntaService(PerguntaRepository perguntaRepository) {
        return new PerguntaServiceImpl(perguntaRepository);
    }

    @ApplicationScoped
    public UsuarioService usuarioService(UsuarioRepository usuarioRepository, AgendamentoRepository agendamentoRepository,
                                         PerguntaRepository perguntaRepository,  AcessoRepository acessoRepository) {
        return new UsuarioServiceImpl(usuarioRepository,  perguntaRepository, agendamentoRepository, acessoRepository);
    }
}
