package br.com.fiap.steelpulse.interfaces;

import br.com.fiap.steelpulse.domain.model.Agendamento;
import br.com.fiap.steelpulse.domain.service.AgendamentoService;
import br.com.fiap.steelpulse.dto.output.AgendamentoOutputDto;
import br.com.fiap.steelpulse.mapper.AgendamentoMapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AgendamentoControllerImpl implements AgendamentoController {

    private final AgendamentoService agendamentoService;

    public AgendamentoControllerImpl(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @Override
    public Agendamento createAgendamento(String descricao, int pacienteId, Date data) {
        return agendamentoService.criarAgendamento(descricao, pacienteId, data);
    }

    @Override
    public void deletarAgendamento(int id) {
        agendamentoService.deletarAgendamento(id);
    }

    @Override
    public List<Agendamento> listarAgendamento() {
        return agendamentoService.listarAgendamentos();
    }

    @Override
    public void confirmarAgendamento(int idAgendamento) {
        agendamentoService.confirmarAgendamento(idAgendamento);
    }

    @Override
    public List<Agendamento> listarAgendamentosPorUsuario(int userId) {
        return agendamentoService.listarAgendamentosPorUsuario(userId);
    }

    @Override
    public List<AgendamentoOutputDto> listarAgendamentos() {
        return agendamentoService.listarAgendamentos()
                .stream()
                .map(AgendamentoMapper::toDto)
                .collect(Collectors.toList());
    }
}
