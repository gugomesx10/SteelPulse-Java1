package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Agendamento;
import br.com.fiap.steelpulse.dto.output.AgendamentoOutputDto;

import java.util.Date;
import java.util.List;

public interface AgendamentoController {

    Agendamento createAgendamento(String descricao, int pacienteId, Date agendamento);

    void deletarAgendamento(int id);

    List<Agendamento> listarAgendamento();

    void confirmarAgendamento(int idAgendamento);

    List<Agendamento> listarAgendamentosPorUsuario(int userId);

    List<AgendamentoOutputDto> listarAgendamentos();
}
