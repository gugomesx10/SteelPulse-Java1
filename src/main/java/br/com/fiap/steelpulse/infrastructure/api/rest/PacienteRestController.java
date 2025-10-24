package br.com.fiap.steelpulse.infrastructure.api.rest;

import br.com.fiap.steelpulse.domain.model.Paciente;
import br.com.fiap.steelpulse.interfaces.PacienteController;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteRestController {

    private final PacienteController pacienteController;

    @Inject
    public PacienteRestController(PacienteController pacienteController) {
        this.pacienteController = pacienteController;
    }

    /**
     * Endpoint para verificação do status da API
     */
    @GET
    @Path("/status")
    public Response status() {
        return Response.ok(Map.of(
                "status", "OK",
                "mensagem", "API de Pacientes operacional",
                "versao", "1.0.0"
        )).build();
    }

    /**
     * Criação de um novo paciente.
     * As validações de CPF, e-mail, telefone e endereço são feitas na própria entidade Paciente.
     */
    @POST
    public Response criarPaciente(Paciente pacienteInput) {
        try {
            Paciente paciente = this.pacienteController.criarPaciente(pacienteInput);
            return Response.status(Response.Status.CREATED).entity(paciente).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro", "Parâmetro inválido: " + e.getMessage()))
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", "Erro inesperado: " + e.getMessage()))
                    .build();
        }

    }

    /**
     * Lista todos os pacientes cadastrados
     */
    @GET
    public Response listarPacientes() {
        try {
            List<Paciente> pacientes = this.pacienteController.listarPacientes();
            if (pacientes.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.ok(pacientes).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", "Erro ao listar pacientes: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * Busca paciente pelo CPF
     */
    @GET
    @Path("/{cpf}")
    public Response buscarPorCpf(@PathParam("cpf") String cpf) {
        try {
            Paciente paciente = this.pacienteController.buscarPorCpf(cpf);

            if (paciente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of(
                                "erro", "Paciente não encontrado",
                                "cpf", cpf
                        ))
                        .build();
            }

            return Response.ok(paciente).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of(
                            "erro", "CPF inválido",
                            "detalhe", e.getMessage()
                    ))
                    .build();

        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of(
                            "erro", "Erro interno ao buscar paciente",
                            "detalhe", e.getMessage()
                    ))
                    .build();
        }
    }


    /**
     * Atualiza os dados de um paciente
     */
    @PUT
    @Path("/{cpf}")
    public Response atualizarPaciente(@PathParam("cpf") String cpf, Paciente pacienteInput) {
        try {
            Paciente pacienteAtualizado = this.pacienteController.atualizarPaciente(cpf, pacienteInput);
            return Response.ok(pacienteAtualizado).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }

    /**
     * Remove um paciente pelo CPF
     */
    @DELETE
    @Path("/{cpf}")
    public Response deletarPaciente(@PathParam("cpf") String cpf) {
        try {
            this.pacienteController.deletarPaciente(cpf);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }
}
