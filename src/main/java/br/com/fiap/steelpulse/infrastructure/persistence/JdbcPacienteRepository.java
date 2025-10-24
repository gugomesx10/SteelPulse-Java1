package br.com.fiap.steelpulse.infrastructure.persistence;

import br.com.fiap.steelpulse.domain.exceptions.EntidadeNaoLocalizada;
import br.com.fiap.steelpulse.domain.model.Paciente;
import br.com.fiap.steelpulse.domain.model.Endereco;
import br.com.fiap.steelpulse.domain.repository.PacienteRepository;
import br.com.fiap.steelpulse.infrastructure.exceptions.InfraestruturaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcPacienteRepository implements PacienteRepository {

    private final DatabaseConnection databaseConnection;

    public JdbcPacienteRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Salva um novo paciente no banco de dados.
     */
    @Override
    public Paciente salvar(Paciente paciente) {
        String sql = """
                INSERT INTO PACIENTE (
                    NOME, CPF, TELEFONE, EMAIL, ANO_NASCIMENTO, ATIVO, VERSION,
                    CEP, NUMERO, COMPLEMENTO, CREATED_AT, LAST_UPDATE
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getTelefone());
            stmt.setString(4, paciente.getEmail());
            stmt.setInt(5, paciente.getAnoNascimento());
            stmt.setBoolean(6, paciente.isAtivo());
            stmt.setLong(7, paciente.getVersao());
            stmt.setString(8, paciente.getEndereco().getCep());
            stmt.setString(9, paciente.getEndereco().getNumero());
            stmt.setString(10, paciente.getEndereco().getComplemento());

            Timestamp now = new Timestamp(System.currentTimeMillis());
            stmt.setTimestamp(11, now);
            stmt.setTimestamp(12, now);

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new InfraestruturaException("Erro ao salvar paciente: nenhuma linha afetada");
            }

            return paciente;

        } catch (SQLException e) {
            throw new InfraestruturaException("Erro ao salvar paciente", e);
        }
    }

    /**
     * Busca um paciente pelo CPF.
     */
    @Override
    public Paciente buscarPorCpf(String cpf) {
        String sql = """
                SELECT NOME, CPF, TELEFONE, EMAIL, ANO_NASCIMENTO, ATIVO, VERSION,
                       CEP, NUMERO, COMPLEMENTO
                FROM PACIENTE
                WHERE CPF = ?
                """;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("NOME");
                    String telefone = rs.getString("TELEFONE");
                    String email = rs.getString("EMAIL");
                    Integer anoNascimento = rs.getInt("ANO_NASCIMENTO");
                    Boolean ativo = rs.getBoolean("ATIVO");
                    Long versao = rs.getLong("VERSION");
                    String cep = rs.getString("CEP");
                    String numero = rs.getString("NUMERO");
                    String complemento = rs.getString("COMPLEMENTO");

                    Endereco endereco = new Endereco(cep, numero, complemento);
                    Paciente paciente = new Paciente(nome, cpf, ativo, email, anoNascimento, endereco);
                    paciente.incrementarVersao();
                    return paciente;
                } else {
                    throw new EntidadeNaoLocalizada("Paciente não encontrado com CPF: " + cpf);
                }
            }

        } catch (SQLException | EntidadeNaoLocalizada e) {
            throw new InfraestruturaException("Erro ao buscar paciente por CPF", e);
        }
    }

    @Override
    public Paciente editar(Paciente paciente) {
        return null;
    }

    @Override
    public List<Paciente> buscarTodos() {
        return List.of();
    }

    /**
     * Atualiza os dados de um paciente.
     */
    @Override
    public Paciente atualizar(String cpf, Paciente paciente) {
        String sql = """
                UPDATE PACIENTE
                SET NOME = ?, TELEFONE = ?, EMAIL = ?, ANO_NASCIMENTO = ?, ATIVO = ?,
                    CEP = ?, NUMERO = ?, COMPLEMENTO = ?, LAST_UPDATE = ?
                WHERE CPF = ?
                """;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getTelefone());
            stmt.setString(3, paciente.getEmail());
            stmt.setInt(4, paciente.getAnoNascimento());
            stmt.setBoolean(5, paciente.isAtivo());
            stmt.setString(6, paciente.getEndereco().getCep());
            stmt.setString(7, paciente.getEndereco().getNumero());
            stmt.setString(8, paciente.getEndereco().getComplemento());
            stmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
            stmt.setString(10, cpf);

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new EntidadeNaoLocalizada("Paciente não encontrado para atualização: " + cpf);
            }

            return paciente;
        } catch (SQLException | EntidadeNaoLocalizada e) {
            throw new InfraestruturaException("Erro ao atualizar paciente", e);
        }
    }

    /**
     * Deleta um paciente pelo CPF.
     */
    @Override
    public void deletar(String cpf) {
        String sql = "DELETE FROM PACIENTE WHERE CPF = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new EntidadeNaoLocalizada("Paciente não encontrado para exclusão: " + cpf);
            }

        } catch (SQLException | EntidadeNaoLocalizada e) {
            throw new InfraestruturaException("Erro ao deletar paciente", e);
        }
    }

    /**
     * Lista todos os pacientes cadastrados.
     */
    @Override
    public List<Paciente> listarTodos() {
        String sql = """
                SELECT NOME, CPF, TELEFONE, EMAIL, ANO_NASCIMENTO, ATIVO, VERSION,
                       CEP, NUMERO, COMPLEMENTO
                FROM PACIENTE ORDER BY NOME
                """;

        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("NOME");
                String cpf = rs.getString("CPF");
                String telefone = rs.getString("TELEFONE");
                String email = rs.getString("EMAIL");
                Integer anoNascimento = rs.getInt("ANO_NASCIMENTO");
                Boolean ativo = rs.getBoolean("ATIVO");
                Long versao = rs.getLong("VERSION");
                String cep = rs.getString("CEP");
                String numero = rs.getString("NUMERO");
                String complemento = rs.getString("COMPLEMENTO");

                Endereco endereco = new Endereco(cep, numero, complemento);
                Paciente paciente = new Paciente(nome, cpf, ativo, email, anoNascimento, endereco);
                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            throw new InfraestruturaException("Erro ao listar pacientes", e);
        }

        return pacientes;
    }

    /**
     * Desativa um paciente (muda o campo ativo para false).
     */
    @Override
    public void desativar(String cpf, Long versao) {
        String sql = """
                UPDATE PACIENTE
                SET ATIVO = FALSE, VERSION = ?, LAST_UPDATE = ?
                WHERE CPF = ? AND VERSION = ?
                """;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Timestamp now = new Timestamp(System.currentTimeMillis());
            stmt.setLong(1, versao);
            stmt.setTimestamp(2, now);
            stmt.setString(3, cpf);
            stmt.setLong(4, versao - 1);

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new EntidadeNaoLocalizada("Erro ao desativar: paciente não encontrado com CPF: " + cpf);
            }

        } catch (SQLException | EntidadeNaoLocalizada e) {
            throw new InfraestruturaException("Erro ao desativar paciente", e);
        }
    }

    /**
     * Reativa um paciente (muda o campo ativo para true).
     */
    @Override
    public void reativar(String cpf, Long versao) {
        String sql = """
                UPDATE PACIENTE
                SET ATIVO = TRUE, VERSION = ?, LAST_UPDATE = ?
                WHERE CPF = ? AND VERSION = ?
                """;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Timestamp now = new Timestamp(System.currentTimeMillis());
            stmt.setLong(1, versao);
            stmt.setTimestamp(2, now);
            stmt.setString(3, cpf);
            stmt.setLong(4, versao - 1);

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new EntidadeNaoLocalizada("Erro ao reativar: paciente não encontrado com CPF: " + cpf);
            }

        } catch (SQLException | EntidadeNaoLocalizada e) {
            throw new InfraestruturaException("Erro ao reativar paciente", e);
        }
    }
}
