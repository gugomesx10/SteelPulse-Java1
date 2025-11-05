package br.com.fiap.steelpulse.mapper;

import br.com.fiap.steelpulse.domain.model.Usuario;
import br.com.fiap.steelpulse.dto.input.UsuarioInputDto;
import br.com.fiap.steelpulse.dto.output.UsuarioOutputDto;

public class UsuarioMapper {
    public static UsuarioOutputDto toDto(Usuario usuarioCriado) {
        return new UsuarioOutputDto(
                usuarioCriado.getUserId(),
                usuarioCriado.getName(),
                usuarioCriado.getEmail(),
                usuarioCriado.getSenha()
        );
    }

    public static Usuario toModel(UsuarioInputDto dto) {
        return new Usuario(
                dto.getUserId(),
                dto.getName(),
                dto.getEmail(),
                dto.getSenha()
        );
    }
}
