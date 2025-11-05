package br.com.fiap.steelpulse.infrastructure.persistence;

import br.com.fiap.steelpulse.domain.model.Pergunta;
import br.com.fiap.steelpulse.domain.repository.PerguntaRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class JdbcPerguntaRepository implements PerguntaRepository {

    private final DatabaseConnection databaseConnection;

    public JdbcPerguntaRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public ArrayList<Pergunta> listarPerguntas() {
        ArrayList<Pergunta> perguntas = new ArrayList<>();
        String sql = "SELECT id, titulo, corpo, autor_da_pergunta, autor_da_resposta, data FROM Pergunta";
        try (
                Connection connection = this.databaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String corpo = rs.getString("corpo");
                String autorPergunta = rs.getString("autor_da_pergunta");
                int autorResposta = rs.getObject("autor_da_resposta") != null ? rs.getInt("autor_da_resposta") : -1;
                Date data = rs.getDate("data");
                perguntas.add(new Pergunta(id, titulo, corpo, autorPergunta, autorResposta, data));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar perguntas", e);
        }
        return perguntas;
    }

    @Override
    public void deletarPergunta(int id) {
        String sql = "DELETE FROM Pergunta WHERE id = ?";
        try (
                Connection connection = this.databaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new RuntimeException("Nenhuma pergunta encontrada com id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar pergunta", e);
        }
    }

    @Override
    public Pergunta responderPergunta(int id, String resposta, int autor) {
        String sql = "UPDATE Pergunta SET corpo = ?, autor_da_resposta = ? WHERE id = ?";
        try (
                Connection connection = this.databaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, resposta);
            ps.setInt(2, autor);
            ps.setInt(3, id);
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new RuntimeException("Nenhuma pergunta encontrada para responder com id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao responder pergunta", e);
        }
        return getPerguntaById(id);
    }

    @Override
    public Pergunta criarPergunta(String titulo, String autor, Date date, String assunto, String email, String celular) {
        String sql = "INSERT INTO Pergunta (titulo, corpo, autor_da_pergunta, data, assunto, email, celular) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (
                Connection connection = this.databaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"})
        ) {
            ps.setString(1, titulo);
            ps.setString(2, "");
            ps.setString(3, autor);
            ps.setDate(4, new java.sql.Date(date.getTime()));
            ps.setString(5, assunto);
            ps.setString(6, email);
            ps.setString(7, celular);
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new RuntimeException("Erro ao criar pergunta");
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return new Pergunta(id, titulo, "", autor, -1, date);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar pergunta", e);
        }
        return null;
    }

    private Pergunta getPerguntaById(int id) {
        String sql = "SELECT id, titulo, corpo, autor_da_pergunta, autor_da_resposta, data FROM Pergunta WHERE id = ?";
        try (
                Connection connection = this.databaseConnection.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pergunta(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("corpo"),
                            rs.getString("autor_da_pergunta"),
                            rs.getObject("autor_da_resposta") != null ? rs.getInt("autor_da_resposta") : null,
                            rs.getDate("data")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pergunta", e);
        }
        return null;
    }

}
