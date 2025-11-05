package br.com.fiap.steelpulse.infrastructure.api.rest;

import br.com.fiap.steelpulse.domain.model.Pergunta;
import br.com.fiap.steelpulse.dto.input.PerguntaInputDto;
import br.com.fiap.steelpulse.dto.input.RespostaInputDto;
import br.com.fiap.steelpulse.dto.output.PerguntaOutputDto;
import br.com.fiap.steelpulse.interfaces.PerguntaController;
import br.com.fiap.steelpulse.mapper.PerguntaMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/pergunta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PerguntaRestController {

    private final PerguntaController perguntaController;
    public PerguntaRestController(PerguntaController perguntaController){
        this.perguntaController = perguntaController;
    }

    @POST
    public Response create(PerguntaInputDto dto) {
        try {
            Pergunta perguntaCriado = perguntaController.criarPergunta(dto.getTitulo(), dto.getAutorDaPergunta(),
                    dto.getAssunto(), dto.getEmail(), dto.getCelular());
            PerguntaOutputDto perguntaSaida = PerguntaMapper.toDto(perguntaCriado);
            return Response.status(Response.Status.CREATED).entity(perguntaSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, RespostaInputDto dto) {
        try {
            Pergunta perguntaEditado = perguntaController.responderPergunta(id, dto.getCorpo(), dto.getAutorDaReposta());
            PerguntaOutputDto perguntaSaida = PerguntaMapper.toDto(perguntaEditado);
            return Response.ok(perguntaSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            perguntaController.deletarPergunta(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response listAll() {
        try {
            List<Pergunta> perguntaList = perguntaController.listarPerguntas();
            List<PerguntaOutputDto> perguntaSaida = new ArrayList<>();
            for (Pergunta pergunta : perguntaList) {
                perguntaSaida.add(PerguntaMapper.toDto(pergunta));
            }
            return Response.ok(perguntaSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}
