package br.com.fiap.steelpulse.dto.input;

public class PacienteInputDto {
    private int userId;
    private String name;
    private String email;
    private String senha;
    private boolean isFuncionario;

    public PacienteInputDto(int userId, String name, String email, String senha, boolean isFuncionario) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.isFuncionario = isFuncionario;
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

    public boolean isFuncionario() {
        return isFuncionario;
    }
}
