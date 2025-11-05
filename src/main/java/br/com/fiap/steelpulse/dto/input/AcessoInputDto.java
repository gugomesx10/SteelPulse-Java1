package br.com.fiap.steelpulse.dto.input;

import java.util.Date;

public class AcessoInputDto {
    private int id;
    private Date dataAcesso;
    private int idPagina;
    private int idUsuario;

    public AcessoInputDto(int id, Date dataAcesso, int idPagina, int idUsuario) {
        this.id = id;
        this.dataAcesso = dataAcesso;
        this.idPagina = idPagina;
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
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


    public int getIdPagina() {
        return idPagina;
    }

}
