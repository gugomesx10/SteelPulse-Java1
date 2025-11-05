package br.com.fiap.steelpulse.mapper;

import br.com.fiap.steelpulse.domain.model.Pergunta;
import br.com.fiap.steelpulse.dto.input.PerguntaInputDto;
import br.com.fiap.steelpulse.dto.output.PerguntaOutputDto;

public class PerguntaMapper {
    public static PerguntaOutputDto toDto(Pergunta perguntaCriado) {
        return new PerguntaOutputDto(
                perguntaCriado.getId(),
                perguntaCriado.getTitulo(),
                perguntaCriado.getCorpo(),
                perguntaCriado.getAutorDaPergunta(),
                perguntaCriado.getAutorDaReposta(),
                perguntaCriado.getData()
        );
    }

}
