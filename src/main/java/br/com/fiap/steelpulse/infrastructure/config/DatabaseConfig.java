package br.com.fiap.steelpulse.infrastructure.config;

import br.com.fiap.steelpulse.domain.repository.AcessoRepository;
import br.com.fiap.steelpulse.domain.repository.AgendamentoRepository;
import br.com.fiap.steelpulse.domain.repository.PerguntaRepository;
import br.com.fiap.steelpulse.domain.repository.UsuarioRepository;
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
    public AcessoRepository acessoRepository(DatabaseConnection databaseConnection) {
        return new JdbcAcessoRepository(databaseConnection);
    }

    @ApplicationScoped
    public PerguntaRepository perguntaRepository(DatabaseConnection databaseConnection) {
        return new JdbcPerguntaRepository(databaseConnection);
    }

    @ApplicationScoped
    public UsuarioRepository usuarioRepository(DatabaseConnection databaseConnection) {
        return new JdbcUsuarioRepository(databaseConnection);
    }

}
