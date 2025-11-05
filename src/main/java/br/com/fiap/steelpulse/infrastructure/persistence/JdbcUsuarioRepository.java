package br.com.fiap.steelpulse.infrastructure.persistence;

import br.com.fiap.steelpulse.domain.model.Usuario;
import br.com.fiap.steelpulse.domain.repository.UsuarioRepository;
import br.com.fiap.steelpulse.infrastructure.exceptions.InfraestruturaException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUsuarioRepository implements UsuarioRepository {
    private final String tableNome = "USUARIOSAUDE";
    private final DatabaseConnection databaseConnection;

    public JdbcUsuarioRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Usuario criarUsuario(String nome, String email, String senha, boolean funcionario) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = this.databaseConnection.getConnection();
            connection.setAutoCommit(false);

            String sqlUsuario = "INSERT INTO "+tableNome+" (NAME, EMAIL, SENHA, IS_FUNCIONARIO) VALUES (?, ?, ?, ?) ";
            preparedStatement = connection.prepareStatement(sqlUsuario, new String[]{"ID"});
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, senha);
            preparedStatement.setString(4, funcionario ? "Y" : "N");

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                connection.rollback();
                throw new InfraestruturaException("Erro ao inserir novo usuário, nenhuma linha afetada");
            }
            int usuarioId;
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuarioId = generatedKeys.getBigDecimal(1).toBigInteger().intValue();
                } else {
                    connection.rollback();
                    throw new InfraestruturaException("Erro ao salvar usuário, nenhum ID gerado");
                }
            }
            connection.commit();
            return new Usuario(usuarioId, nome, email, senha);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InfraestruturaException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Usuario alterarSenha(String senha) {
        return null;
    }

    @Override
    public Usuario alterarEmail(String email) {
        return null;
    }

    @Override
    public Usuario alterarNome(int id, String novoNome) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.databaseConnection.getConnection();
            connection.setAutoCommit(false);

            String sql = "UPDATE " + tableNome + " SET NAME = ? WHERE ID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, novoNome);
            preparedStatement.setInt(2, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                connection.rollback();
                throw new InfraestruturaException("Nenhum usuário encontrado com o ID informado para atualização de nome.");
            }

            connection.commit();

            // Recupera o usuário atualizado
            String selectSql = "SELECT ID, NAME, EMAIL, SENHA FROM " + tableNome + " WHERE ID = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {
                selectStatement.setInt(1, id);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int usuarioId = resultSet.getInt("ID");
                        String nome = resultSet.getString("NAME");
                        String email = resultSet.getString("EMAIL");
                        String senha = resultSet.getString("SENHA");
                        return new Usuario(usuarioId, nome, email, senha);
                    } else {
                        throw new InfraestruturaException("Erro ao recuperar usuário após atualização de nome.");
                    }
                }
            }

        } catch (SQLException e) {
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException("Erro ao alterar nome do usuário: " + e.getMessage(), e);
        } catch (InfraestruturaException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void deletarUsuario(int id) {
        String sql = "DELETE FROM " + tableNome + " WHERE ID = ?";
        try (Connection connection = this.databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);
            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                connection.rollback();
                throw new InfraestruturaException("Nenhum usuário encontrado com o ID informado para exclusão.");
            }

            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage(), e);
        } catch (InfraestruturaException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.databaseConnection.getConnection();
            String sql = "SELECT ID, NAME, EMAIL, SENHA FROM " + tableNome;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nome = resultSet.getString("NAME");
                String email = resultSet.getString("EMAIL");
                String senha = resultSet.getString("SENHA");

                usuarios.add(new Usuario(id, nome, email, senha));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return usuarios;
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.databaseConnection.getConnection();
            String sql = "SELECT ID, NAME, EMAIL, SENHA FROM " + tableNome + " WHERE EMAIL = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nome = resultSet.getString("NAME");
                String senha = resultSet.getString("SENHA");

                return new Usuario(id, nome, email, senha);
            } else {
                throw new InfraestruturaException("Usuário não encontrado com o email informado: " + email);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por email: " + e.getMessage(), e);
        } catch (InfraestruturaException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
