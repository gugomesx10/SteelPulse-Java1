package br.com.fiap.steelpulse.infrastructure.api.rest;

import br.com.fiap.steelpulse.domain.model.Usuario;
import br.com.fiap.steelpulse.dto.input.UsuarioInputDto;
import br.com.fiap.steelpulse.dto.output.UsuarioOutputDto;
import br.com.fiap.steelpulse.interfaces.UsuarioController;
import br.com.fiap.steelpulse.mapper.UsuarioMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioRestController {

    private final UsuarioController usuarioController;
    public UsuarioRestController(UsuarioController usuarioController){
        this.usuarioController = usuarioController;
    }

    @POST
    public Response create(UsuarioInputDto dto) {
        try {
            Usuario usuarioCriado = usuarioController.criarUsuario(dto.getName(), dto.getEmail(), dto.getSenha(), dto.isFuncionario());
            UsuarioOutputDto usuarioSaida = UsuarioMapper.toDto(usuarioCriado);
            return Response.status(Response.Status.CREATED).entity(usuarioSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, UsuarioInputDto dto) {
        try {
            Usuario usuario = UsuarioMapper.toModel(dto);
            Usuario usuarioEditado = usuarioController.alterarNome(id, usuario.getName());
            UsuarioOutputDto usuarioSaida = UsuarioMapper.toDto(usuarioEditado);
            return Response.ok(usuarioSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            usuarioController.deletarUsuario(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response listAll() {
        try {
            List<Usuario> usuarioList = usuarioController.listarUsuarios();
            List<UsuarioOutputDto> usuarioSaida = new ArrayList<>();
            for (Usuario usuario : usuarioList) {
                usuarioSaida.add(UsuarioMapper.toDto(usuario));
            }
            return Response.ok(usuarioSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    public Response login(UsuarioInputDto dto) {
        try{
            Usuario usuarioSaida = usuarioController.login(dto.getEmail(), dto.getSenha());
            return Response.ok(usuarioSaida).build();
        }catch (RuntimeException e){
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

}
