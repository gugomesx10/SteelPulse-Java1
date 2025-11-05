package br.com.fiap.steelpulse.domain.model;

import java.util.Date;

public class Agendamento {

    private int id;
    private Date data;
    private String descricao;
    private int userId;
    private boolean confirmado;

    public Agendamento() {}

    public Agendamento(int id, String descricao, Date data, int userId) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.userId = userId;
        this.confirmado = false;
    }

    public Agendamento(Date data, String descricao, int userId, boolean confirmado) {
        this.data = data;
        this.descricao = descricao;
        this.userId = userId;
        this.confirmado = confirmado;
    }

    public Agendamento(int id, Date data, String descricao, int userId, boolean confirmado) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.userId = userId;
        this.confirmado = confirmado;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }
}
