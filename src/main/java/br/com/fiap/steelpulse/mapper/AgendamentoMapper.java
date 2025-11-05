package br.com.fiap.steelpulse.mapper;

import br.com.fiap.steelpulse.domain.model.Agendamento;
import br.com.fiap.steelpulse.dto.input.AgendamentoInputDto;
import br.com.fiap.steelpulse.dto.output.AgendamentoOutputDto;

public class AgendamentoMapper {
    public static AgendamentoOutputDto toDto(Agendamento agendamentoCriado) {
        return new AgendamentoOutputDto(
                agendamentoCriado.getId(),
                agendamentoCriado.getData(),
                agendamentoCriado.getDescricao(),
                agendamentoCriado.getUserId(),
                agendamentoCriado.isConfirmado());
    }

    public static Agendamento toModel(AgendamentoInputDto dto) {
        return new Agendamento(
                dto.getId(),
                dto.getData(),
                dto.getDescricao(),
                dto.getUserId(),
                dto.isConfirmado()
        );
    }
}
