package br.com.fiap.steelpulse.infrastructure.config;

import br.com.fiap.steelpulse.domain.repository.PacienteRepository;
import br.com.fiap.steelpulse.domain.repository.ContratoRepository;
import br.com.fiap.steelpulse.infrastructure.persistence.DatabaseConnection;
import br.com.fiap.steelpulse.infrastructure.persistence.DatabaseConnectionImpl;
import br.com.fiap.steelpulse.infrastructure.persistence.JdbcPacienteRepository;
import br.com.fiap.steelpulse.infrastructure.persistence.JdbcContratoRepository;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DatabaseConfig {

    @ApplicationScoped
    public DatabaseConnection databaseConnection(AgroalDataSource dataSource) {
        return new DatabaseConnectionImpl(dataSource);
    }

    @ApplicationScoped
    public ContratoRepository contratoRepository(DatabaseConnection databaseConnection) {
        return new JdbcContratoRepository(databaseConnection);
    }

    @ApplicationScoped
    public PacienteRepository pacienteRepository(DatabaseConnection databaseConnection) {
        return new JdbcPacienteRepository(databaseConnection);
    }

}
