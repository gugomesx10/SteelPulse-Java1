package br.com.fiap.steelpulse.domain.service;

import br.com.fiap.steelpulse.domain.model.Agendamento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface AgendamentoService {
    Agendamento getAgendamento(int id);
    Agendamento criarAgendamento(String descricao, int pacienteId, Date agendamento);
    ArrayList<Agendamento> listarAgendamentos();
    void deletarAgendamento(int idAgendamento);
    void confirmarAgendamento(int idAgendamento);

    List<Agendamento> listarAgendamentosPorUsuario(int userId);
}
