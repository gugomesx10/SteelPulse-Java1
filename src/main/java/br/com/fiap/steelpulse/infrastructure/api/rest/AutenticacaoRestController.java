package br.com.fiap.steelpulse.infrastructure.api.rest;

import br.com.fiap.steelpulse.domain.model.Autenticacao;
import br.com.fiap.steelpulse.dto.input.AutenticacaoInputDto;
import br.com.fiap.steelpulse.dto.output.AutenticacaoOutputDto;
import br.com.fiap.steelpulse.interfaces.AutenticacaoController;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import br.com.fiap.steelpulse.mapper.AutenticacaoMapper;

@Path("/acesso")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutenticacaoRestController {
    private final AutenticacaoController autenticacaoController;
    public AutenticacaoRestController(AutenticacaoController autenticacaoController) {
        this.autenticacaoController = autenticacaoController;
    }

    @POST
    public Response create(AutenticacaoInputDto dto) {
        try {
            Autenticacao autenticacaoCriado = autenticacaoController.createAcesso(dto.getIdPagina(), dto.getIdUsuario());
            AutenticacaoOutputDto acessoSaida = AutenticacaoMapper.toDto(autenticacaoCriado);
            return Response.status(Response.Status.CREATED).entity(acessoSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, AutenticacaoInputDto dto) {
        try {
            Autenticacao autenticacao = AutenticacaoMapper.toModel(dto);
            Autenticacao autenticacaoEditado = autenticacaoController.atualizarAcesso(id, autenticacao);
            AutenticacaoOutputDto acessoSaida = AutenticacaoMapper.toDto(autenticacaoEditado);
            return Response.ok(acessoSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            autenticacaoController.deletarAcesso(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{userid}")
    public Response listAllByUser(@PathParam("userId") int userId) {
        try {
            List<Autenticacao> autenticacaoList = autenticacaoController.listarAcessosPorUsuario(userId);
            List<AutenticacaoOutputDto> acessoSaida = new ArrayList<>();
            for (Autenticacao autenticacao : autenticacaoList) {
                acessoSaida.add(AutenticacaoMapper.toDto(autenticacao));
            }
            return Response.ok(acessoSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}
