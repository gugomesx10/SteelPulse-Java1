package br.com.fiap.steelpulse.domain.model;

public class PlanoDeSaude {

    private String nome;
    private String codigoPlano;
    private String tipoPlano;

    public PlanoDeSaude(String nome, String codigoPlano, String tipoPlano) {
        this.nome = nome;
        this.codigoPlano = codigoPlano;
        this.tipoPlano = tipoPlano;
    }

    public PlanoDeSaude(String unimed, String planoBásico) {
    }

    public String getNome() {
        return nome;
    }

    public String getCodigoPlano() {
        return codigoPlano;
    }

    public String getTipoPlano() {
        return tipoPlano;
    }

    @Override
    public String toString() {
        return "PlanoDeSaude{" +
                "nome='" + nome + '\'' +
                ", codigoPlano='" + codigoPlano + '\'' +
                ", tipoPlano='" + tipoPlano + '\'' +
                '}';
    }
}
