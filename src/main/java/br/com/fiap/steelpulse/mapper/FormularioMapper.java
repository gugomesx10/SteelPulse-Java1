package br.com.fiap.steelpulse.mapper;

import br.com.fiap.steelpulse.domain.model.Formulario;
import br.com.fiap.steelpulse.dto.output.FormularioOutputDto;

public class FormularioMapper {
    public static FormularioOutputDto toDto(Formulario formularioCriado) {
        return new FormularioOutputDto(
                formularioCriado.getId(),
                formularioCriado.getTitulo(),
                formularioCriado.getCorpo(),
                formularioCriado.getAutorDaPergunta(),
                formularioCriado.getAutorDaReposta(),
                formularioCriado.getData()
        );
    }

}
