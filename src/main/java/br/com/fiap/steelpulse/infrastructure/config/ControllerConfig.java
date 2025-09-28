package br.com.fiap.steelpulse.infrastructure.config;

import br.com.fiap.steelpulse.domain.service.PacienteService;
import br.com.fiap.steelpulse.interfaces.PacienteController;
import br.com.fiap.steelpulse.interfaces.PacienteControllerImpl;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ControllerConfig {

    @ApplicationScoped
    public PacienteController pacienteController(PacienteService pacienteService) {
        return new PacienteControllerImpl(pacienteService);
    }
}
