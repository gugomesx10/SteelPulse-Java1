package br.com.fiap.steelpulse.domain.model;

import java.util.Date;

public class Acesso {
    private int id;
    private Date dataAcesso;
    private int idPagina;
    private int idUsuario;

    public Acesso(int id, Date dataAcesso, int idPagina, int idUsuario) {
        this.id = id;
        this.dataAcesso = dataAcesso;
        this.idPagina = idPagina;
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Acesso(Date dataAcesso, int idPagina) {
        this.dataAcesso = dataAcesso;
        this.idPagina = idPagina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataAcesso() {
        return dataAcesso;
    }

    public void setDataAcesso(Date dataAcesso) {
        this.dataAcesso = dataAcesso;
    }

    public int getIdPagina() {
        return idPagina;
    }

    public void setIdPagina(int idPagina) {
        this.idPagina = idPagina;
    }
}