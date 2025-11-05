package br.com.fiap.steelpulse.domain.model;

import java.util.Date;

public class Formulario {

    private int id;
    private String titulo;
    private String corpo;
    private String autorDaPergunta;
    private int autorDaReposta;
    private Date data;

    public Formulario(String titulo, String autor, Date data) {
        this.titulo = titulo;
        this.autorDaPergunta = autor;
        this.data = data;
    }

    public Formulario(int id, String titulo, String corpo, String autorPergunta, int autorResposta, Date data) {
        this.id = id;
        this.titulo = titulo;
        this.corpo = corpo;
        this.autorDaPergunta = autorPergunta;
        this.autorDaReposta = autorResposta;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void responderPergunta(int idAutor, String resposta) {
        setAutorDaReposta(idAutor);
        setResposta(resposta);
    }

    private void setAutorDaReposta(int idAutor){
        this.autorDaReposta = idAutor;
    }

    private void setResposta(String resposta){
        this.corpo = resposta;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCorpo() {
        return corpo;
    }

    public String getAutorDaPergunta() {
        return autorDaPergunta;
    }

    public int getAutorDaReposta() {
        return autorDaReposta;
    }

    public Date getData() {
        return data;
    }
}
