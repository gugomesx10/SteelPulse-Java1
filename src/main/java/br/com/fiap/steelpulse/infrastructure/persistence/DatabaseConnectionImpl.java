package br.com.fiap.steelpulse.infrastructure.persistence;

import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class DatabaseConnectionImpl implements DatabaseConnection {

    @Inject
    DataSource dataSource;

    @Inject
    private DatabaseConnectionImpl connection;

    public DatabaseConnectionImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }
}
