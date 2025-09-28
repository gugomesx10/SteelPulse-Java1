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

    @Override
    public Paciente salvar(Paciente paciente) {
        String sql = """
                INSERT INTO PACIENTE (NOME, CPF, TELEFONE, EMAIL, ANO_NASCIMENTO, ATIVO, VERSION,
                CEP, NUMERO, COMPLEMENTO, CREATED_AT, LAST_UPDATE)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = this.databaseConnection.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

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

                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                stmt.setTimestamp(11, currentTimestamp);
                stmt.setTimestamp(12, currentTimestamp);

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new InfraestruturaException("Erro ao salvar, nenhuma linha foi afetada");
                }

                return paciente;

            }
        } catch (SQLException e) {
            throw new InfraestruturaException("Erro ao salvar paciente", e);
        }
    }

    @Override
    public Paciente buscarPorCpf(String cpf) throws EntidadeNaoLocalizada {
        String sql = """
                SELECT NOME, CPF, TELEFONE, EMAIL, ANO_NASCIMENTO, ATIVO, VERSION,
                CEP, NUMERO, COMPLEMENTO FROM PACIENTE WHERE CPF = ?
                """;

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String nome = resultSet.getString("NOME");
                String cpfFromDB = resultSet.getString("CPF");
                Integer anoNascimento = resultSet.getInt("ANO_NASCIMENTO");
                String telefone = resultSet.getString("TELEFONE");
                String email = resultSet.getString("EMAIL");
                Long versao = resultSet.getLong("VERSION");
                String cep = resultSet.getString("CEP");
                String complemento = resultSet.getString("COMPLEMENTO");
                String numero = resultSet.getString("NUMERO");
                Boolean ativo = resultSet.getBoolean("ATIVO");


                Endereco endereco = new Endereco(cep, numero, complemento);

                resultSet.close();

                return new Paciente(nome, cpfFromDB, ativo, "123456789@fiap.com", 1990, new Endereco("12345-678", "123", "complemento"));
            }

        } catch (SQLException e) {
            throw new EntidadeNaoLocalizada("Erro ao buscar paciente por cpf");
        }
        throw new EntidadeNaoLocalizada("Paciente nao encontrado");
    }

    @Override
    public Paciente editar(Paciente paciente) {
        return null;
    }

    @Override
    public List<Paciente> buscarTodos() {
        String sql = """
                SELECT NOME, CPF, ATIVO
                FROM PACIENTE ORDER BY NOME
                """;

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            List<Paciente> pacientes = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nome = rs.getString("NOME");
                String cpf = rs.getString("CPF");
                Boolean ativo = rs.getBoolean("ATIVO");

                Paciente paciente = new Paciente(nome, cpf, ativo, "123456789@fiap.com", 1990, new Endereco("12345-678", "123", "complemento"));
                pacientes.add(paciente);
            }

            return pacientes;
        } catch (SQLException e) {
            throw new InfraestruturaException("Erro ao buscar todos os pacientes", e);
        }
    }

    @Override
    public void desativar(String cpf, Long versao) {
        String sql = """
                UPDATE PACIENTE SET ATIVO = FALSE, VERSION = ?, LAST_UPDATE = ?
                WHERE CPF = ? AND VERSION = ?
                """;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, versao);
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            stmt.setTimestamp(2, currentTimestamp);
            stmt.setString(3, cpf);
            stmt.setLong(4, versao - 1);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new InfraestruturaException("Erro ao desativar paciente, nenhuma linha foi afetada");
            }

        } catch (SQLException e) {
            throw new InfraestruturaException("Erro ao desativar paciente", e);
        }
    }

    @Override
    public void reativar(String cpf, Long versao) {
    }
}
