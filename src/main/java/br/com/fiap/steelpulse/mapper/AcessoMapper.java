package br.com.fiap.steelpulse.mapper;

import br.com.fiap.steelpulse.domain.model.Acesso;
import br.com.fiap.steelpulse.dto.input.AcessoInputDto;
import br.com.fiap.steelpulse.dto.output.AcessoOutputDto;

public class AcessoMapper {
    public static AcessoOutputDto toDto(Acesso acessoCriado) {
        return new AcessoOutputDto(acessoCriado.getId(), acessoCriado.getDataAcesso(),
                acessoCriado.getIdPagina(), acessoCriado.getIdUsuario());
    }

    public static Acesso toModel(AcessoInputDto dto) {
        return new Acesso(dto.getId(), dto.getDataAcesso(),
                dto.getIdPagina(), dto.getIdUsuario());
    }
}
