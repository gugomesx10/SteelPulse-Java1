package br.com.fiap.steelpulse.infrastructure.api.rest;

import br.com.fiap.steelpulse.domain.model.Paciente;
import br.com.fiap.steelpulse.interfaces.PacienteController;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

    @GET
    @Path("/ola")
    public Response olaMundo(){
        return Response.ok(
                        Map.of("mensagem", "API REST para gestão de saúde iniciada com sucesso. Bem-vindo à plataforma de pacientes!"))
                .build();
    }

    @POST
    public Response criarPaciente(Paciente pacienteInput) {
        try{
            Paciente paciente = this.pacienteController.criarPaciente(pacienteInput);
            return Response.status(Response.Status.CREATED).entity(paciente).build();
        } catch (RuntimeException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
