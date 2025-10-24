package br.com.fiap.steelpulse.application.service;

import br.com.fiap.steelpulse.domain.exceptions.EntidadeNaoLocalizada;
import br.com.fiap.steelpulse.domain.model.Contrato;
import br.com.fiap.steelpulse.domain.repository.ContratoRepository;
import br.com.fiap.steelpulse.domain.service.ContratoService;
import br.com.fiap.steelpulse.infrastructure.exceptions.InfraestruturaException;

import java.util.List;

public class ContratoServiceImpl implements ContratoService {

    private final ContratoRepository contratoRepository;

    public ContratoServiceImpl(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    @Override
    public void criar(Contrato contrato) {
        try {
            contratoRepository.salvar(contrato);
        } catch (InfraestruturaException e) {
            throw new RuntimeException("Erro ao criar contrato: " + e.getMessage(), e);
        }
    }

    @Override
    public Contrato buscarPorId(Long id) {
        try {
            return contratoRepository.buscarPorId(id);
        } catch (EntidadeNaoLocalizada e) {
            throw new RuntimeException("Contrato não encontrado com ID: " + id);
        } catch (InfraestruturaException e) {
            throw new RuntimeException("Erro ao buscar contrato: " + e.getMessage(), e);
        }
    }

    @Override
    public Contrato atualizar(Contrato contrato) {
        try {
            return contratoRepository.editar(contrato);
        } catch (EntidadeNaoLocalizada e) {
            throw new RuntimeException("Contrato não encontrado para atualização: " + contrato.getId());
        } catch (InfraestruturaException e) {
            throw new RuntimeException("Erro ao atualizar contrato: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Contrato> listarPorPaciente(String cpf) {
        try {
            return contratoRepository.buscarPorPacienteCpf(cpf);
        } catch (InfraestruturaException e) {
            throw new RuntimeException("Erro ao listar contratos do paciente " + cpf + ": " + e.getMessage(), e);
        }
    }

    @Override
    public Contrato buscarAtivoPorPaciente(String cpf) {
        try {
            return contratoRepository.buscarAtivoPorPacienteCpf(cpf);
        } catch (EntidadeNaoLocalizada e) {
            throw new RuntimeException("Paciente " + cpf + " não possui contrato ativo");
        } catch (InfraestruturaException e) {
            throw new RuntimeException("Erro ao buscar contrato ativo do paciente " + cpf, e);
        }
    }

    @Override
    public void finalizarContrato(Long id, Long version) {
        try {
            contratoRepository.finalizar(id, version);
        } catch (EntidadeNaoLocalizada e) {
            throw new RuntimeException("Contrato não encontrado para finalização: " + id);
        } catch (InfraestruturaException e) {
            throw new RuntimeException("Erro ao finalizar contrato: " + e.getMessage(), e);
        }
    }
}
