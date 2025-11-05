package br.com.fiap.steelpulse.infrastructure.api.rest;

import br.com.fiap.steelpulse.domain.model.Formulario;
import br.com.fiap.steelpulse.dto.input.FormularioInputDto;
import br.com.fiap.steelpulse.dto.input.RespostaInputDto;
import br.com.fiap.steelpulse.dto.output.FormularioOutputDto;
import br.com.fiap.steelpulse.interfaces.FormularioController;
import br.com.fiap.steelpulse.mapper.FormularioMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/pergunta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FormularioRestController {

    private final FormularioController formularioController;
    public FormularioRestController(FormularioController formularioController){
        this.formularioController = formularioController;
    }

    @POST
    public Response create(FormularioInputDto dto) {
        try {
            Formulario formularioCriado = formularioController.criarPergunta(dto.getTitulo(), dto.getAutorDaPergunta(),
                    dto.getAssunto(), dto.getEmail(), dto.getCelular());
            FormularioOutputDto perguntaSaida = FormularioMapper.toDto(formularioCriado);
            return Response.status(Response.Status.CREATED).entity(perguntaSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, RespostaInputDto dto) {
        try {
            Formulario formularioEditado = formularioController.responderPergunta(id, dto.getCorpo(), dto.getAutorDaReposta());
            FormularioOutputDto perguntaSaida = FormularioMapper.toDto(formularioEditado);
            return Response.ok(perguntaSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            formularioController.deletarPergunta(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response listAll() {
        try {
            List<Formulario> formularioList = formularioController.listarPerguntas();
            List<FormularioOutputDto> perguntaSaida = new ArrayList<>();
            for (Formulario formulario : formularioList) {
                perguntaSaida.add(FormularioMapper.toDto(formulario));
            }
            return Response.ok(perguntaSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}
