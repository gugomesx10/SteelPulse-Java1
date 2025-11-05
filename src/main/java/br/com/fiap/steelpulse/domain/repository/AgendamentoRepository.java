package br.com.fiap.steelpulse.domain.repository;

import br.com.fiap.steelpulse.domain.model.Agendamento;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface AgendamentoRepository{
    void confirmarAgendamento(int agendamentoId);
    Agendamento criarAgendamento(Date data, String conteudo, int pacienteId);

    Agendamento getAgendamento(int id);

    ArrayList<Agendamento> listarAgendamentos();

    void deletarAgendamento(int idAgendamento);

    List<Agendamento> listarAgendamentosPorUsuario(int userId);
}
