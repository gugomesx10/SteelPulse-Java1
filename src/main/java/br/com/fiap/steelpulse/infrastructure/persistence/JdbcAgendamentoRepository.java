package br.com.fiap.steelpulse.infrastructure.persistence;

import br.com.fiap.steelpulse.domain.model.Agendamento;
import br.com.fiap.steelpulse.domain.repository.AgendamentoRepository;
import br.com.fiap.steelpulse.infrastructure.exceptions.AgendamentoException;
import br.com.fiap.steelpulse.infrastructure.exceptions.InfraestruturaException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class JdbcAgendamentoRepository implements AgendamentoRepository {

    private final DatabaseConnection databaseConnection;

    public JdbcAgendamentoRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void confirmarAgendamento(int agendamentoId) {
        try (Connection connection = this.databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE AGENDAMENTO SET CONFIRMADO = ? WHERE ID = ?")) {

            connection.setAutoCommit(false);
            preparedStatement.setString(1, "Y");
            preparedStatement.setInt(2, agendamentoId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                connection.rollback();
                throw new InfraestruturaException("Erro ao atualizar agendamento, nenhuma linha afetada");
            }

            connection.commit();
        } catch (SQLException | InfraestruturaException e) {
            throw new RuntimeException("Erro ao confirmar agendamento", e);
        }
    }

    @Override
    public Agendamento criarAgendamento(Date data, String descricao, int userId) {
        Long agendamentoId = null;

        try (Connection connection = this.databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     """
                     INSERT INTO AGENDAMENTO (DATA, DESCRICAO, USERID)
                     VALUES (?, ?, ?)
                     """,
                     new String[]{"ID"})) {

            connection.setAutoCommit(false);

            preparedStatement.setDate(1, new java.sql.Date(data.getTime()));
            preparedStatement.setString(2, descricao);
            preparedStatement.setInt(3, userId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                connection.rollback();
                throw new InfraestruturaException("Erro ao inserir agendamento, nenhuma linha afetada");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    agendamentoId = generatedKeys.getLong(1);
                } else {
                    connection.rollback();
                    throw new InfraestruturaException("Erro ao salvar agendamento, nenhum ID gerado");
                }
            }

            connection.commit();
        } catch (SQLException | InfraestruturaException e) {
            throw new RuntimeException("Erro ao criar agendamento", e);
        }

        return new Agendamento(agendamentoId.intValue(), descricao, data, userId);
    }

    @Override
    public Agendamento getAgendamento(int agendamentoId) {
        try (Connection connection = this.databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     """
                     SELECT ID, DATA, DESCRICAO, USERID, CONFIRMADO
                     FROM AGENDAMENTO
                     WHERE ID = ?
                     """)) {

            preparedStatement.setInt(1, agendamentoId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Agendamento agendamento = new Agendamento();
                    agendamento.setId(resultSet.getInt("ID"));
                    agendamento.setData(resultSet.getDate("DATA"));
                    agendamento.setDescricao(resultSet.getString("DESCRICAO"));
                    agendamento.setUserId(resultSet.getInt("USERID"));

                    String confirmado = resultSet.getString("CONFIRMADO");
                    agendamento.setConfirmado("Y".equalsIgnoreCase(confirmado));

                    return agendamento;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar agendamento", e);
        }
        return null;
    }

    @Override
    public ArrayList<Agendamento> listarAgendamentos() {
        ArrayList<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT ID, DATA, DESCRICAO, USERID, CONFIRMADO FROM AGENDAMENTO";

        try (Connection connection = this.databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                Date data = resultSet.getDate("DATA");
                String descricao = resultSet.getString("DESCRICAO");
                int userId = resultSet.getInt("USERID");
                String confirmado = resultSet.getString("CONFIRMADO");

                boolean isConfirmado = "Y".equalsIgnoreCase(confirmado);

                Agendamento agendamento = new Agendamento(id, data, descricao, userId, isConfirmado);
                agendamentos.add(agendamento);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar agendamentos", e);
        }

        return agendamentos;
    }

    @Override
    public void deletarAgendamento(int idAgendamento) {
        try (Connection connection = this.databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM AGENDAMENTO WHERE ID = ?")) {

            connection.setAutoCommit(false);
            preparedStatement.setInt(1, idAgendamento);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                connection.rollback();
                throw new InfraestruturaException("Nenhum agendamento encontrado com ID: " + idAgendamento);
            }

            connection.commit();
        } catch (SQLException | InfraestruturaException e) {
            throw new RuntimeException("Erro ao deletar agendamento", e);
        }
    }

    @Override
    public ArrayList<Agendamento> listarAgendamentosPorUsuario(int userId) {
        ArrayList<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT ID, DATA, DESCRICAO, USERID, CONFIRMADO FROM AGENDAMENTO WHERE USERID = ?";

        try (Connection connection = this.databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    Date data = resultSet.getDate("DATA");
                    String descricao = resultSet.getString("DESCRICAO");
                    String confirmado = resultSet.getString("CONFIRMADO");

                    boolean isConfirmado = "Y".equalsIgnoreCase(confirmado);

                    Agendamento agendamento = new Agendamento(id, data, descricao, userId, isConfirmado);
                    agendamentos.add(agendamento);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar agendamentos por usuário", e);
        }

        return agendamentos;
    }
}
