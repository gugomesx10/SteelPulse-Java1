package br.com.fiap.steelpulse.dto.output;

public class PacienteOutputDto {
    private int userId;
    private String name;
    private String email;
    private String senha;
    private boolean isFuncionario;

    public PacienteOutputDto(int userId, String name, String email, String senha) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.isFuncionario = false;
    }

    public void setFuncionario(boolean funcionario) {
        isFuncionario = funcionario;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}