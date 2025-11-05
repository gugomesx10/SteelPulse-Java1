package br.com.fiap.steelpulse.infrastructure.api.rest;

import br.com.fiap.steelpulse.domain.model.Paciente;
import br.com.fiap.steelpulse.dto.input.PacienteInputDto;
import br.com.fiap.steelpulse.dto.output.PacienteOutputDto;
import br.com.fiap.steelpulse.interfaces.PacienteController;
import br.com.fiap.steelpulse.mapper.PacienteMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteRestController {

    private final PacienteController pacienteController;
    public PacienteRestController(PacienteController pacienteController){
        this.pacienteController = pacienteController;
    }

    @POST
    public Response create(PacienteInputDto dto) {
        try {
            Paciente pacienteCriado = pacienteController.criarUsuario(dto.getName(), dto.getEmail(), dto.getSenha(), dto.isFuncionario());
            PacienteOutputDto usuarioSaida = PacienteMapper.toDto(pacienteCriado);
            return Response.status(Response.Status.CREATED).entity(usuarioSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, PacienteInputDto dto) {
        try {
            Paciente paciente = PacienteMapper.toModel(dto);
            Paciente pacienteEditado = pacienteController.alterarNome(id, paciente.getName());
            PacienteOutputDto usuarioSaida = PacienteMapper.toDto(pacienteEditado);
            return Response.ok(usuarioSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            pacienteController.deletarUsuario(id);
            return Response.noContent().build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response listAll() {
        try {
            List<Paciente> pacienteList = pacienteController.listarUsuarios();
            List<PacienteOutputDto> usuarioSaida = new ArrayList<>();
            for (Paciente paciente : pacienteList) {
                usuarioSaida.add(PacienteMapper.toDto(paciente));
            }
            return Response.ok(usuarioSaida).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    public Response login(PacienteInputDto dto) {
        try{
            Paciente pacienteSaida = pacienteController.login(dto.getEmail(), dto.getSenha());
            return Response.ok(pacienteSaida).build();
        }catch (RuntimeException e){
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

}
