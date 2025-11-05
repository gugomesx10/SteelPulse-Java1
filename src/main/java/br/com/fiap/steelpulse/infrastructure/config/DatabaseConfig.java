package br.com.fiap.steelpulse.infrastructure.config;

import br.com.fiap.steelpulse.domain.repository.AutenticacaoRepository;
import br.com.fiap.steelpulse.domain.repository.AgendamentoRepository;
import br.com.fiap.steelpulse.domain.repository.FormularioRepository;
import br.com.fiap.steelpulse.domain.repository.PacienteRepository;
import br.com.fiap.steelpulse.infrastructure.persistence.*;
import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DatabaseConfig {

    @ApplicationScoped
    public DatabaseConnection databaseConnection(AgroalDataSource dataSource) {
        return new DatabaseConnectionImpl(dataSource);
    }

    @ApplicationScoped
    public AgendamentoRepository agendamentoRepository(DatabaseConnection databaseConnection) {
        return new JdbcAgendamentoRepository(databaseConnection);
    }

    @ApplicationScoped
    public AutenticacaoRepository autenticacaoRepository(DatabaseConnection databaseConnection) {
        return new JdbcAutenticacaoRepository(databaseConnection);
    }

    @ApplicationScoped
    public FormularioRepository formularioRepository(DatabaseConnection databaseConnection) {
        return new JdbcFormularioRepository(databaseConnection);
    }

    @ApplicationScoped
    public PacienteRepository pacienteRepository(DatabaseConnection databaseConnection) {
        return new JdbcPacienteRepository(databaseConnection);
    }

}
