package br.com.fiap.steelpulse.dto.input;

public class RespostaInputDto {
    private String corpo;
    private int autorDaResposta;

    public RespostaInputDto(String resposta, int autorDaResposta) {
        this.corpo = resposta;
        this.autorDaResposta = autorDaResposta;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public int getAutorDaReposta() {
        return autorDaResposta;
    }

    public void setAutorDaReposta(int autorDaResposta) {
        this.autorDaResposta = autorDaResposta;
    }
}
