package br.com.fiap.steelpulse.infrastructure.persistence;

import br.com.fiap.steelpulse.domain.model.Acesso;
import br.com.fiap.steelpulse.domain.repository.AcessoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JdbcAcessoRepository implements AcessoRepository {
    private final DatabaseConnection databaseConnection;

    public JdbcAcessoRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Acesso criarAcesso(Acesso acesso) {
        String sql = "INSERT INTO Acesso (data_acesso, id_pagina, id_usuario) VALUES (?, ?, ?)";
        try (
                Connection connection = this.databaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})
        ) {
            ps.setDate(1, new java.sql.Date(acesso.getDataAcesso().getTime()));
            ps.setInt(2, acesso.getIdPagina());
            ps.setInt(3, acesso.getIdUsuario());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating acesso failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    acesso.setId(id);
                } else {
                    throw new SQLException("Creating acesso failed, no ID obtained.");
                }
            }
            return acesso;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar acesso", e);
        }
    }


    @Override
    public Acesso editarAcesso(Acesso acesso, int idAcesso) {
        String sql = "UPDATE Acesso SET data_acesso = ?, id_pagina = ?, id_usuario=? WHERE id = ?";
        try (
                Connection connection = this.databaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setDate(1, new java.sql.Date(acesso.getDataAcesso().getTime()));
            ps.setInt(2, acesso.getIdPagina());
            ps.setInt(3, acesso.getIdUsuario());
            ps.setInt(4, idAcesso);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("No acesso found with id: " + acesso.getId());
            }
            return acesso;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar acesso", e);
        }
    }

    @Override
    public void deletarAcesso(int id) {
        String sql = "DELETE FROM Acesso WHERE id = ?";
        try (
                Connection connection = this.databaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhum acesso encontrado com o ID: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar acesso", e);
        }
    }

    @Override
    public ArrayList<Acesso> listarAcesso() {
        ArrayList<Acesso> acessos = new ArrayList<>();
        String sql = "SELECT id, data_acesso, id_pagina, id_usuario FROM Acesso";
        try (
                Connection connection = this.databaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                java.sql.Date dataAcesso = rs.getDate("data_acesso");
                int idPagina = rs.getInt("id_pagina");
                int idUsuario = rs.getInt("id_usuario");

                acessos.add(new Acesso(id, new java.util.Date(dataAcesso.getTime()), idPagina, idUsuario));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar acessos", e);
        }
        return acessos;
    }

    @Override
    public ArrayList<Acesso> listarByIdUsuario(int idUsuario) {
        ArrayList<Acesso> acessos = new ArrayList<>();
        String sql = "SELECT id, data_acesso, id_pagina, id_usuario FROM Acesso WHERE id_usuario = ?";
        try (
                Connection connection = this.databaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    java.sql.Date dataAcesso = rs.getDate("data_acesso");
                    int idPagina = rs.getInt("id_pagina");
                    int idUsuarioResult = rs.getInt("id_usuario");

                    acessos.add(new Acesso(id, new java.util.Date(dataAcesso.getTime()), idPagina, idUsuarioResult));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar acessos por id_usuario", e);
        }
        return acessos;
    }

}
