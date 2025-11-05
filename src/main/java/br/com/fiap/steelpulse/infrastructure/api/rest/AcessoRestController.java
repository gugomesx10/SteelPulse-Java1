package br.com.fiap.steelpulse.infrastructure.api.rest;

import br.com.fiap.steelpulse.domain.model.Acesso;
import br.com.fiap.steelpulse.dto.input.AcessoInputDto;
import br.com.fiap.steelpulse.dto.output.AcessoOutputDto;
import br.com.fiap.steelpulse.interfaces.AcessoController;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import br.com.fiap.steelpulse.mapper.AcessoMapper;

@Path("/acesso")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AcessoRestController {
    private final AcessoController acessoController;
    public AcessoRestController(AcessoController acessoController) {
        this.acessoController = acessoController;
    }

    @POST
    public Response create(AcessoInputDto dto) {
        try {
            Acesso acessoCriado = acessoController.createAcesso(dto.getIdPagina(), dto.getIdUsuario());
            AcessoOutputDto acessoSaida = AcessoMapper.toDto(acessoCriado);
            return Response.status(Response.Status.CREATED).entity(acessoSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, AcessoInputDto dto) {
        try {
            Acesso acesso = AcessoMapper.toModel(dto);
            Acesso acessoEditado = acessoController.atualizarAcesso(id, acesso);
            AcessoOutputDto acessoSaida = AcessoMapper.toDto(acessoEditado);
            return Response.ok(acessoSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            acessoController.deletarAcesso(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{userid}")
    public Response listAllByUser(@PathParam("userId") int userId) {
        try {
            List<Acesso> acessoList = acessoController.listarAcessosPorUsuario(userId);
            List<AcessoOutputDto> acessoSaida = new ArrayList<>();
            for (Acesso acesso : acessoList) {
                acessoSaida.add(AcessoMapper.toDto(acesso));
            }
            return Response.ok(acessoSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}
