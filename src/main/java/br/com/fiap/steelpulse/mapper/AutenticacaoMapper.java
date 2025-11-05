package br.com.fiap.steelpulse.mapper;

import br.com.fiap.steelpulse.domain.model.Autenticacao;
import br.com.fiap.steelpulse.dto.input.AutenticacaoInputDto;
import br.com.fiap.steelpulse.dto.output.AutenticacaoOutputDto;

public class AutenticacaoMapper {
    public static AutenticacaoOutputDto toDto(Autenticacao autenticacaoCriado) {
        return new AutenticacaoOutputDto(autenticacaoCriado.getId(), autenticacaoCriado.getDataAcesso(),
                autenticacaoCriado.getIdPagina(), autenticacaoCriado.getIdUsuario());
    }

    public static Autenticacao toModel(AutenticacaoInputDto dto) {
        return new Autenticacao(dto.getId(), dto.getDataAcesso(),
                dto.getIdPagina(), dto.getIdUsuario());
    }
}
