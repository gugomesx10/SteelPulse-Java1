package br.com.fiap.steelpulse.infrastructure.persistence;

import br.com.fiap.steelpulse.domain.exceptions.EntidadeNaoLocalizada;
import br.com.fiap.steelpulse.domain.model.Contrato;
import br.com.fiap.steelpulse.domain.model.Exame;
import br.com.fiap.steelpulse.domain.model.PlanoDeSaude;
import br.com.fiap.steelpulse.domain.repository.ContratoRepository;
import br.com.fiap.steelpulse.infrastructure.exceptions.InfraestruturaException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcContratoRepository implements ContratoRepository {

    private final DatabaseConnection databaseConnection;

    public JdbcContratoRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Salva um contrato e seus exames associados.
     */
    @Override
    public void salvar(Contrato contrato) {
        String sqlContrato = """
                INSERT INTO CONTRATO (CPF, DATA_INICIO, DATA_FIM, VERSION, CREATED_AT, LAST_UPDATE)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        String sqlExame = """
                INSERT INTO EXAME (CONTRATO_ID, TIPO, DATA_EXAME, RESULTADO, CONTRATO_VERSION, CREATED_AT, LAST_UPDATE)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = this.databaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtContrato = conn.prepareStatement(sqlContrato, Statement.RETURN_GENERATED_KEYS)) {
                stmtContrato.setString(1, contrato.getPacienteCpf());
                stmtContrato.setTimestamp(2, Timestamp.valueOf(contrato.getDataInicio()));
                stmtContrato.setTimestamp(3, contrato.getDataFim() != null ? Timestamp.valueOf(contrato.getDataFim()) : null);
                stmtContrato.setLong(4, contrato.getVersao());
                Timestamp now = new Timestamp(System.currentTimeMillis());
                stmtContrato.setTimestamp(5, now);
                stmtContrato.setTimestamp(6, now);

                int affectedRows = stmtContrato.executeUpdate();
                if (affectedRows == 0) {
                    conn.rollback();
                    throw new InfraestruturaException("Erro ao salvar contrato, nenhuma linha foi afetada");
                }

                Long contratoId;
                try (ResultSet generatedKeys = stmtContrato.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        contratoId = generatedKeys.getLong(1);
                    } else {
                        conn.rollback();
                        throw new InfraestruturaException("Erro ao recuperar o ID do contrato gerado");
                    }
                }

                // Inserção dos exames
                try (PreparedStatement stmtExame = conn.prepareStatement(sqlExame)) {
                    for (Exame exame : contrato.getExames()) {
                        stmtExame.setLong(1, contratoId);
                        stmtExame.setString(2, exame.getTipo());
                        stmtExame.setTimestamp(3, Timestamp.valueOf(exame.getDataRealizacao()));
                        stmtExame.setString(4, exame.getResultado());
                        stmtExame.setLong(5, contrato.getVersao());
                        stmtExame.setTimestamp(6, now);
                        stmtExame.setTimestamp(7, now);
                        stmtExame.addBatch();
                    }
                    stmtExame.executeBatch();
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw new InfraestruturaException("Erro ao salvar contrato e exames", e);
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new InfraestruturaException("Erro ao salvar contrato", e);
        }
    }

    /**
     * Busca um contrato pelo ID.
     */
    @Override
    public Contrato buscarPorId(Long id) {
        String sqlContrato = """
                SELECT ID, CPF, DATA_INICIO, DATA_FIM, VERSION
                FROM CONTRATO
                WHERE ID = ?
                """;

        String sqlExames = """
                SELECT ID, TIPO, RESULTADO, DATA_EXAME, CONTRATO_VERSION
                FROM EXAME
                WHERE CONTRATO_ID = ?
                """;

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmtContrato = conn.prepareStatement(sqlContrato)) {

            stmtContrato.setLong(1, id);
            ResultSet rsContrato = stmtContrato.executeQuery();

            if (!rsContrato.next()) {
                throw new EntidadeNaoLocalizada("Contrato não encontrado com ID: " + id);
            }

            String cpf = rsContrato.getString("CPF");
            LocalDateTime dataInicio = rsContrato.getTimestamp("DATA_INICIO").toLocalDateTime();
            Timestamp dataFimTS = rsContrato.getTimestamp("DATA_FIM");
            LocalDateTime dataFim = (dataFimTS != null ? dataFimTS.toLocalDateTime() : null);
            Long versao = rsContrato.getLong("VERSION");

            List<Exame> exames = new ArrayList<>();
            try (PreparedStatement stmtExames = conn.prepareStatement(sqlExames)) {
                stmtExames.setLong(1, id);
                ResultSet rsExame = stmtExames.executeQuery();

                while (rsExame.next()) {
                    Long exameId = rsExame.getLong("ID");
                    String tipo = rsExame.getString("TIPO");
                    String resultado = rsExame.getString("RESULTADO");
                    String dataExame = rsExame.getString("DATA_EXAME");
                    Long versaoExame = rsExame.getLong("CONTRATO_VERSION");

                    Exame exame = new Exame(exameId, null, tipo, null, resultado, dataExame, tipo);
                    exames.add(exame);
                }
            }

            return new Contrato(id, null, dataInicio, dataFim, versao, exames, new PlanoDeSaude("N/A", "Básico"));

        } catch (SQLException | EntidadeNaoLocalizada e) {
            throw new InfraestruturaException("Erro ao buscar contrato por ID", e);
        }
    }

    /**
     * Atualiza os dados de um contrato (como dataFim ou versão).
     */
    @Override
    public Contrato editar(Contrato contrato) {
        String sql = """
                UPDATE CONTRATO
                SET DATA_FIM = ?, VERSION = ?, LAST_UPDATE = ?
                WHERE ID = ?
                """;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, contrato.getDataFim() != null ? Timestamp.valueOf(contrato.getDataFim()) : null);
            stmt.setLong(2, contrato.getVersao());
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.setLong(4, contrato.getId());

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new EntidadeNaoLocalizada("Contrato não encontrado para atualização: ID " + contrato.getId());
            }

            return contrato;
        } catch (SQLException | EntidadeNaoLocalizada e) {
            throw new InfraestruturaException("Erro ao atualizar contrato", e);
        }
    }

    /**
     * Busca todos os contratos de um paciente pelo CPF.
     */
    @Override
    public List<Contrato> buscarPorPacienteCpf(String cpf) {
        String sql = """
                SELECT ID, DATA_INICIO, DATA_FIM, VERSION
                FROM CONTRATO
                WHERE CPF = ?
                ORDER BY DATA_INICIO DESC
                """;

        List<Contrato> contratos = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("ID");
                LocalDateTime dataInicio = rs.getTimestamp("DATA_INICIO").toLocalDateTime();
                Timestamp fimTS = rs.getTimestamp("DATA_FIM");
                LocalDateTime dataFim = fimTS != null ? fimTS.toLocalDateTime() : null;
                Long versao = rs.getLong("VERSION");

                contratos.add(new Contrato(id, cpf, List.of(), dataInicio, dataFim, versao));
            }

        } catch (SQLException e) {
            throw new InfraestruturaException("Erro ao buscar contratos por CPF", e);
        }

        return contratos;
    }

    /**
     * Busca o contrato ativo de um paciente (DATA_FIM nula ou futura).
     */
    @Override
    public Contrato buscarAtivoPorPacienteCpf(String cpf) {
        String sql = """
                SELECT ID, DATA_INICIO, DATA_FIM, VERSION
                FROM CONTRATO
                WHERE CPF = ? AND (DATA_FIM IS NULL OR DATA_FIM > CURRENT_TIMESTAMP)
                ORDER BY DATA_INICIO DESC
                LIMIT 1
                """;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Long id = rs.getLong("ID");
                LocalDateTime dataInicio = rs.getTimestamp("DATA_INICIO").toLocalDateTime();
                Timestamp fimTS = rs.getTimestamp("DATA_FIM");
                LocalDateTime dataFim = fimTS != null ? fimTS.toLocalDateTime() : null;
                Long versao = rs.getLong("VERSION");

                return new Contrato(id, cpf, List.of(), dataInicio, dataFim, versao);
            } else {
                throw new EntidadeNaoLocalizada("Nenhum contrato ativo encontrado para CPF: " + cpf);
            }

        } catch (SQLException | EntidadeNaoLocalizada e) {
            throw new InfraestruturaException("Erro ao buscar contrato ativo por CPF", e);
        }
    }

    @Override
    public List<Contrato> buscarPorClienteCpf(String cpf) {
        return List.of();
    }

    @Override
    public Contrato buscarAtivoPorClienteCpf(String cpf) {
        return null;
    }

    /**
     * Finaliza (encerra) um contrato.
     */
    @Override
    public void finalizar(Long id, Long version) {
        String sql = """
                UPDATE CONTRATO
                SET DATA_FIM = ?, VERSION = ?, LAST_UPDATE = ?
                WHERE ID = ? AND VERSION = ?
                """;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Timestamp now = new Timestamp(System.currentTimeMillis());
            stmt.setTimestamp(1, now);
            stmt.setLong(2, version);
            stmt.setTimestamp(3, now);
            stmt.setLong(4, id);
            stmt.setLong(5, version - 1);

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new EntidadeNaoLocalizada("Contrato não encontrado para finalização: ID " + id);
            }

        } catch (SQLException | EntidadeNaoLocalizada e) {
            throw new InfraestruturaException("Erro ao finalizar contrato", e);
        }
    }
}
