package br.com.fiap.steelpulse.infrastructure.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnection {

    Connection getConnection() throws SQLException;
}
