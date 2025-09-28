package br.com.fiap.steelpulse.domain.model;

import java.time.LocalDateTime;

public class Exame {

    private Long id;
    private Paciente paciente;
    private String tipoExame;
    private LocalDateTime dataRealizacao;
    private String dataExame;
    private String resultado;
    private String tipo;

    public Exame(Long id, Paciente paciente, String tipoExame, LocalDateTime dataRealizacao, String resultado, String dataExame, String tipo) {
        this.id = id;
        this.paciente = paciente;
        this.tipoExame = tipoExame;
        this.dataRealizacao = dataRealizacao;
        this.resultado = resultado;
        this.tipo = tipo;
        this.dataExame = dataExame;
    }

    public Long getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getTipoExame() {
        return tipoExame;
    }

    public LocalDateTime getDataRealizacao() {
        return dataRealizacao;
    }

    public String getDataExame() {
        return dataExame;
    }

    public String getResultado() {
        return resultado;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Exame{" +
                "id=" + id +
                ", paciente=" + (paciente != null ? paciente.getCpf() : "Sem paciente") +
                ", tipoExame='" + tipoExame + '\'' +
                ", dataRealizacao=" + dataRealizacao +
                ", resultado='" + resultado + '\'' +
                ", dataExame=" + dataExame +
                ", tipo=" + tipo + '\'' +
                '}';
    }
}
