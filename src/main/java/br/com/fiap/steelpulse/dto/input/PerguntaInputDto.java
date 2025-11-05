package br.com.fiap.steelpulse.dto.input;

import java.util.Date;

public class PerguntaInputDto{

    private int id;
    private String titulo;
    private String autorDaPergunta;
    private String email;
    private String celular;
    private String assunto;
    private Date data;

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAutorDaPergunta(String autorDaPergunta) {
        this.autorDaPergunta = autorDaPergunta;
    }

    public PerguntaInputDto(String titulo, String autor, Date data) {
        this.titulo = titulo;
        this.autorDaPergunta = autor;
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutorDaPergunta() {
        return autorDaPergunta;
    }
}
