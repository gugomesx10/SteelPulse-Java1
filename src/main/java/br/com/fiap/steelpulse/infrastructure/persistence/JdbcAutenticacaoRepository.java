package br.com.fiap.steelpulse.infrastructure.persistence;

import br.com.fiap.steelpulse.domain.model.Autenticacao;
import br.com.fiap.steelpulse.domain.repository.AutenticacaoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JdbcAutenticacaoRepository implements AutenticacaoRepository {
    private final DatabaseConnection databaseConnection;

    public JdbcAutenticacaoRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Autenticacao criarAcesso(Autenticacao autenticacao) {
        String sql = "INSERT INTO Autenticacao (data_acesso, id_pagina, id_usuario) VALUES (?, ?, ?)";
        try (
                Connection connection = this.databaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})
        ) {
            ps.setDate(1, new java.sql.Date(autenticacao.getDataAcesso().getTime()));
            ps.setInt(2, autenticacao.getIdPagina());
            ps.setInt(3, autenticacao.getIdUsuario());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating autenticacao failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    autenticacao.setId(id);
                } else {
                    throw new SQLException("Creating autenticacao failed, no ID obtained.");
                }
            }
            return autenticacao;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar autenticacao", e);
        }
    }


    @Override
    public Autenticacao editarAcesso(Autenticacao autenticacao, int idAcesso) {
        String sql = "UPDATE Autenticacao SET data_acesso = ?, id_pagina = ?, id_usuario=? WHERE id = ?";
        try (
                Connection connection = this.databaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setDate(1, new java.sql.Date(autenticacao.getDataAcesso().getTime()));
            ps.setInt(2, autenticacao.getIdPagina());
            ps.setInt(3, autenticacao.getIdUsuario());
            ps.setInt(4, idAcesso);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("No autenticacao found with id: " + autenticacao.getId());
            }
            return autenticacao;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar autenticacao", e);
        }
    }

    @Override
    public void deletarAcesso(int id) {
        String sql = "DELETE FROM Autenticacao WHERE id = ?";
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
    public ArrayList<Autenticacao> listarAcesso() {
        ArrayList<Autenticacao> autenticacaos = new ArrayList<>();
        String sql = "SELECT id, data_acesso, id_pagina, id_usuario FROM Autenticacao";
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

                autenticacaos.add(new Autenticacao(id, new java.util.Date(dataAcesso.getTime()), idPagina, idUsuario));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar autenticacaos", e);
        }
        return autenticacaos;
    }

    @Override
    public ArrayList<Autenticacao> listarByIdUsuario(int idUsuario) {
        ArrayList<Autenticacao> autenticacaos = new ArrayList<>();
        String sql = "SELECT id, data_acesso, id_pagina, id_usuario FROM Autenticacao WHERE id_usuario = ?";
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

                    autenticacaos.add(new Autenticacao(id, new java.util.Date(dataAcesso.getTime()), idPagina, idUsuarioResult));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar autenticacaos por id_usuario", e);
        }
        return autenticacaos;
    }

}
