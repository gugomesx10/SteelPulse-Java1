package br.com.fiap.steelpulse.application;

import br.com.fiap.steelpulse.application.exceptions.CredencialException;
import br.com.fiap.steelpulse.domain.model.Autenticacao;
import br.com.fiap.steelpulse.domain.model.Agendamento;
import br.com.fiap.steelpulse.domain.model.Paciente;
import br.com.fiap.steelpulse.domain.repository.AutenticacaoRepository;
import br.com.fiap.steelpulse.domain.repository.AgendamentoRepository;
import br.com.fiap.steelpulse.domain.repository.FormularioRepository;
import br.com.fiap.steelpulse.domain.repository.PacienteRepository;
import br.com.fiap.steelpulse.domain.service.PacienteService;

import java.util.ArrayList;

public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final FormularioRepository formularioRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final AutenticacaoRepository autenticacaoRepository;

    public PacienteServiceImpl(PacienteRepository pacienteRepository, FormularioRepository formularioRepository,
                               AgendamentoRepository agendamentoRepository, AutenticacaoRepository autenticacaoRepository) {
        this.pacienteRepository = pacienteRepository;
        this.formularioRepository = formularioRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.autenticacaoRepository = autenticacaoRepository;
    }

    @Override
    public Paciente criarUsuario(String nome, String email, String senha, boolean funcionario)  {
        return pacienteRepository.criarUsuario(nome, email, senha, funcionario);
    }

    @Override
    public Paciente alterarNome(int id, String nome) {
        return pacienteRepository.alterarNome(id,nome);
    }

    @Override
    public Paciente alterarEmail(String email) {
        return null;
    }

    @Override
    public ArrayList<Paciente> listarUsuarios() {
        return (ArrayList<Paciente>) pacienteRepository.listarUsuarios();
    }

    @Override
    public void deletarUsuario(int id) {
        for (Agendamento agendamento : agendamentoRepository.listarAgendamentos()){
            if(agendamento.getUserId() == id){
                agendamentoRepository.deletarAgendamento(agendamento.getId());
            }
        }
        for (Autenticacao autenticacao : autenticacaoRepository.listarAcesso()){
            if(autenticacao.getId() == id){
                autenticacaoRepository.deletarAcesso(id);
            }
        }
        pacienteRepository.deletarUsuario(id);
    }

    @Override
    public Paciente login(String email, String senha) {
        if (loginExiste(email, senha)){
            return pacienteRepository.getUsuarioByEmail(email);
        }
        throw new CredencialException("Autenticacao incorreto!");
    }

    private boolean loginExiste(String email, String senha){
        ArrayList<Paciente> listaPaciente = this.listarUsuarios();
        for(Paciente paciente : listaPaciente){
            if(paciente.getEmail().equals(email) && paciente.getSenha().equals(senha)){
                return true;
            }
        }
        return false;
    }

}
