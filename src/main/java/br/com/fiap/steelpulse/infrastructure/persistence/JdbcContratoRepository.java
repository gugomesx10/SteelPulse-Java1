package br.com.fiap.steelpulse.infrastructure.persistence;

import br.com.fiap.steelpulse.domain.exceptions.EntidadeNaoLocalizada;
import br.com.fiap.steelpulse.domain.model.Contrato;
import br.com.fiap.steelpulse.domain.model.Exame;
import br.com.fiap.steelpulse.domain.repository.ContratoRepository;
import br.com.fiap.steelpulse.infrastructure.exceptions.InfraestruturaException;

import java.sql.*;
import java.util.List;

public class JdbcContratoRepository implements ContratoRepository {

    private final DatabaseConnection databaseConnection;

    public JdbcContratoRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void salvar(Contrato contrato) {
        Connection conn = null;
        PreparedStatement stmtContrato = null;
        PreparedStatement stmtExame = null;

        try {
            conn = this.databaseConnection.getConnection();
            conn.setAutoCommit(false);

            // SQL
            String sqlContrato = """
                    INSERT INTO CONTRATO ( CPF, DATA_INICIO, DATA_FIM, VERSION, CREATED_AT, LAST_UPDATE)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """;

            stmtContrato = conn.prepareStatement(sqlContrato, new String[]{"ID"});
            stmtContrato.setString(1, contrato.getPacienteCpf());
            stmtContrato.setTimestamp(2, Timestamp.valueOf(contrato.getDataInicio()));
            stmtContrato.setTimestamp(3, contrato.getDataFim() != null ? Timestamp.valueOf(contrato.getDataFim()) : null);
            stmtContrato.setLong(4, contrato.getVersao());
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            stmtContrato.setTimestamp(5, currentTimestamp);
            stmtContrato.setTimestamp(6, currentTimestamp);

            int affectedRows = stmtContrato.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                throw new InfraestruturaException("Erro ao salvar contrato, nenhuma linha foi afetada");
            }

            // id
            Long contratoId = null;
            try (ResultSet generatedKeys = stmtContrato.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    contratoId = generatedKeys.getLong(1);
                } else {
                    conn.rollback();
                    throw new InfraestruturaException("Erro ao salvar contrato, nenhuma linha foi afetada");
                }
            }

            // SQL
            String sqlExame = """
                    INSERT INTO EXAME (CONTRATO_ID, TIPO, DATA_EXAME, RESULTADO, CONTRATO_VERSION, CREATED_AT, LAST_UPDATED)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                    """;

            stmtExame = conn.prepareStatement(sqlExame);
            for (Exame exame : contrato.getExames()) {
                stmtExame.setLong(1, contratoId);
                stmtExame.setString(2, exame.getTipo());
                stmtExame.setTimestamp(3, Timestamp.valueOf(exame.getDataExame()));
                stmtExame.setString(4, exame.getResultado());
                stmtExame.setLong(5, contrato.getVersao());
                stmtExame.setTimestamp(6, currentTimestamp);
                stmtExame.setTimestamp(7, currentTimestamp);
                stmtExame.addBatch();
            }

            stmtExame.executeBatch();
            conn.commit();
            contrato.getPacienteCpf();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                }
            }
            throw new InfraestruturaException("Erro ao salvar contrato", e);
        } finally {
            try {
                if (stmtContrato != null) {
                    stmtContrato.close();
                }
                if (stmtExame != null) {
                    stmtExame.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException ex) {

            }
        }
    }

    @Override
    public Contrato buscarPorId(Long id) throws EntidadeNaoLocalizada {
        return null;
    }

    @Override
    public Contrato editar(Contrato contrato) {
        return null;
    }

    @Override
    public List<Contrato> buscarPorPacienteCpf(String cpf) {
        return List.of();
    }

    @Override
    public Contrato buscarAtivoPorPacienteCpf(String cpf) {
        return null;
    }

    @Override
    public List<Contrato> buscarPorClienteCpf(String cpf) {
        return null;
    }

    @Override
    public Contrato buscarAtivoPorClienteCpf(String cpf) {
        return null;
    }

    @Override
    public void finalizar(Long id, Long version) {
    }
}
