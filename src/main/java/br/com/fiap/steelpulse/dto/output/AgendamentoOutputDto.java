package br.com.fiap.steelpulse.dto.output;

import java.util.Date;

public class AgendamentoOutputDto {

    private int id;
    private Date data;
    private String descricao;
    private int userId;
    private boolean confirmado;

    public AgendamentoOutputDto(int id, String descricao, Date data, int userId) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.userId = userId;
        this.confirmado = false;
    }

    public AgendamentoOutputDto(Date data, String descricao, int userId, boolean confirmado) {
        this.data = data;
        this.descricao = descricao;
        this.userId = userId;
        this.confirmado = confirmado;
    }

    public AgendamentoOutputDto() {
    }

    public AgendamentoOutputDto(int id, Date data, String descricao, int userId, boolean isConfirmado) {
        this.id = id;
        this.data = data;
        this.descricao = descricao;
        this.userId = userId;
        this.confirmado = isConfirmado;
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

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
}
