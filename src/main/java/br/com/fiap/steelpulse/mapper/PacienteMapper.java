package br.com.fiap.steelpulse.mapper;

import br.com.fiap.steelpulse.domain.model.Paciente;
import br.com.fiap.steelpulse.dto.input.PacienteInputDto;
import br.com.fiap.steelpulse.dto.output.PacienteOutputDto;

public class PacienteMapper {
    public static PacienteOutputDto toDto(Paciente pacienteCriado) {
        return new PacienteOutputDto(
                pacienteCriado.getUserId(),
                pacienteCriado.getName(),
                pacienteCriado.getEmail(),
                pacienteCriado.getSenha()
        );
    }

    public static Paciente toModel(PacienteInputDto dto) {
        return new Paciente(
                dto.getUserId(),
                dto.getName(),
                dto.getEmail(),
                dto.getSenha()
        );
    }
}
