package br.com.fiap.steelpulse.infrastructure.api.rest;

import br.com.fiap.steelpulse.domain.model.Agendamento;
import br.com.fiap.steelpulse.dto.input.AgendamentoInputDto;
import br.com.fiap.steelpulse.dto.output.AgendamentoOutputDto;
import br.com.fiap.steelpulse.interfaces.AgendamentoController;
import br.com.fiap.steelpulse.mapper.AgendamentoMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("/agendamento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendamentoRestController {

    private final AgendamentoController agendamentoController;

    public AgendamentoRestController(AgendamentoController agendamentoController){
        this.agendamentoController = agendamentoController;
    }

    // -----------------------
    // 1️⃣ Criar agendamento
    // -----------------------
    @POST
    public Response create(AgendamentoInputDto dto) {
        try {
            Agendamento agendamentoCriado = agendamentoController
                    .createAgendamento(dto.getDescricao(), dto.getUserId(), dto.getData());
            AgendamentoOutputDto agendamentoSaida = AgendamentoMapper.toDto(agendamentoCriado);
            return Response.status(Response.Status.CREATED).entity(agendamentoSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }

    // -----------------------
    // 2️⃣ Confirmar agendamento
    // -----------------------
    @PUT
    @Path("/confirmar/{id}")
    public Response confirmarAgendamento(@PathParam("id") int id) {
        try {
            agendamentoController.confirmarAgendamento(id);
            return Response.ok(Map.of("mensagem", "Agendamento confirmado com sucesso")).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }

    // -----------------------
    // 3️⃣ Deletar agendamento
    // -----------------------
    @DELETE
    @Path("/{id}")
    public Response deletarAgendamento(@PathParam("id") int id) {
        try {
            agendamentoController.deletarAgendamento(id);
            return Response.ok(Map.of("mensagem", "Agendamento deletado com sucesso")).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }

    // -----------------------
    // 4️⃣ Listar agendamentos de um usuário
    // -----------------------
    @GET
    @Path("/{userId}")
    public Response listarAgendamentosPorUsuario(@PathParam("userId") int userId) {
        try {
            var agendamentos = agendamentoController.listarAgendamentosPorUsuario(userId);

            if (agendamentos == null || agendamentos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("mensagem", "Nenhum agendamento encontrado para este usuário"))
                        .build();
            }

            return Response.ok(agendamentos).build();

        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }

    // -----------------------
    // 5️⃣ Listar todos os agendamentos (GET geral)
    // -----------------------
    @GET
    public Response listarAgendamentos() {
        try {
            var agendamentos = agendamentoController.listarAgendamentos();

            if (agendamentos == null || agendamentos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("mensagem", "Nenhum agendamento encontrado"))
                        .build();
            }

            return Response.ok(agendamentos).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }
}
