package br.com.fiap.steelpulse.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Paciente {

    private int userId;

    @JsonProperty("nome")
    private String name;

    private String email;
    private String senha;

    @JsonProperty("funcionario")
    private boolean isFuncionario;

    public Paciente() {}

    public Paciente(int userId, String name, String email, String senha) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.senha = senha;
    }

    public Paciente(int userId, String name, String email, String senha, boolean isFuncionario) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.isFuncionario = isFuncionario;
    }

    public boolean isFuncionario() {
        return isFuncionario;
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
