package br.com.fiap.steelpulse.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Contrato {

    private Long id;
    private Paciente paciente;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Long versao;
    private List<Exame> exames;
    private PlanoDeSaude planoDeSaude;

    // Construtor
    public Contrato(Long id, Paciente paciente, LocalDateTime dataInicio, LocalDateTime dataFim, Long versao, List<Exame> exames, PlanoDeSaude planoDeSaude) {
        this.id = id;
        this.paciente = paciente;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.versao = versao;
        this.exames = exames;
        this.planoDeSaude = planoDeSaude;
    }

    public Contrato(Long contratoId, String pacienteCpf, List<Exame> exames, LocalDateTime dataInicio, LocalDateTime dataFim, Long versao) {
    }

    // Métodos getters
    public Long getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getPacienteCpf() {
        return paciente.getCpf();
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public Long getVersao() {
        return versao;
    }

    public List<Exame> getExames() {
        return exames;
    }

    public PlanoDeSaude getPlanoDeSaude() {
        return planoDeSaude;
    }

    public boolean isAtivo() {
        return dataFim == null || dataFim.isAfter(LocalDateTime.now());
    }

    public void finalizarContrato() {
        this.dataFim = LocalDateTime.now();
    }
}
