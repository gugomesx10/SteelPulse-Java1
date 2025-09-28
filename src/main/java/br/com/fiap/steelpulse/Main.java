package br.com.fiap.steelpulse;

import br.com.fiap.steelpulse.domain.exceptions.EntidadeNaoLocalizada;
import br.com.fiap.steelpulse.domain.model.*;
import br.com.fiap.steelpulse.infrastructure.persistence.DatabaseConnectionImpl;
import br.com.fiap.steelpulse.infrastructure.persistence.JdbcContratoRepository;
import br.com.fiap.steelpulse.infrastructure.persistence.JdbcPacienteRepository;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    @Inject
    DatabaseConnectionImpl connection;  // Injeção de dependência do Quarkus

    public static void main(String[] args) throws SQLException, EntidadeNaoLocalizada {
        Main main = new Main();
        main.testarBanco();
    }

    public void testarBanco() throws SQLException, EntidadeNaoLocalizada {
        System.out.println("Conexão estabelecida com o banco.");

        // Criação de paciente
        Paciente paciente = new Paciente("Gustavo Gomes Martins", "35098286824", true, "rm555999@fiap.com", 1990, new Endereco("04530-000", "295", "82"));
        JdbcPacienteRepository pacienteRepository = new JdbcPacienteRepository(connection);
        pacienteRepository.salvar(paciente);
        System.out.println("Paciente criado: " + paciente);

        // Busca do paciente por CPF
        Paciente pacienteBuscado = pacienteRepository.buscarPorCpf("35098286824");
        System.out.println("Paciente buscado: " + pacienteBuscado);

        // Criação de contrato
        PlanoDeSaude planoDeSaude = new PlanoDeSaude("Unimed", "Plano Básico");
        Exame exame = new Exame(1L, paciente, "Exame de Sangue", LocalDateTime.now(), "Resultado Ok", "20/09/25", "o+");
        List<Exame> exames = List.of(exame);
        Contrato contrato = new Contrato(1L, paciente, LocalDateTime.now(), null, 1L, exames, planoDeSaude);

        // Salvar o contrato
        JdbcContratoRepository contratoRepository = new JdbcContratoRepository(connection);
        contratoRepository.salvar(contrato);
        System.out.println("Contrato criado: " + contrato);

        // Busca de contrato
        List<Contrato> contratos = contratoRepository.buscarPorPacienteCpf("12345678900");
        System.out.println("Contratos encontrados: " + contratos);
    }
}
