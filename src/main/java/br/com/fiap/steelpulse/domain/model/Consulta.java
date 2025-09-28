package br.com.fiap.steelpulse.domain.model;

import java.time.LocalDateTime;

public class Consulta {

    private Long id;
    private Paciente paciente;
    private String medico;
    private LocalDateTime dataHora;
    private String especialidade;

    public Consulta(Long id, Paciente paciente, String medico, LocalDateTime dataHora, String especialidade) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.especialidade = especialidade;
    }

    public Long getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getMedico() {
        return medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", paciente=" + paciente +
                ", medico='" + medico + '\'' +
                ", dataHora=" + dataHora +
                ", especialidade='" + especialidade + '\'' +
                '}';
    }
}
