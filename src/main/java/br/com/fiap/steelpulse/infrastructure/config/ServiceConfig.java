
package br.com.fiap.steelpulse.infrastructure.config;

import br.com.fiap.steelpulse.application.service.PacienteServiceImpl;
import br.com.fiap.steelpulse.domain.repository.PacienteRepository;
import br.com.fiap.steelpulse.domain.service.PacienteService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServiceConfig {

    @ApplicationScoped
    public PacienteService clienteService(PacienteRepository clienteRepository) {
        return new PacienteServiceImpl(clienteRepository);
    }
}
