package br.com.fiap.steelpulse.application;

import br.com.fiap.steelpulse.application.exceptions.CredencialException;
import br.com.fiap.steelpulse.domain.model.Acesso;
import br.com.fiap.steelpulse.domain.model.Agendamento;
import br.com.fiap.steelpulse.domain.model.Pergunta;
import br.com.fiap.steelpulse.domain.model.Usuario;
import br.com.fiap.steelpulse.domain.repository.AcessoRepository;
import br.com.fiap.steelpulse.domain.repository.AgendamentoRepository;
import br.com.fiap.steelpulse.domain.repository.PerguntaRepository;
import br.com.fiap.steelpulse.domain.repository.UsuarioRepository;
import br.com.fiap.steelpulse.domain.service.UsuarioService;

import java.util.ArrayList;

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerguntaRepository perguntaRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final AcessoRepository acessoRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PerguntaRepository perguntaRepository,
                              AgendamentoRepository agendamentoRepository, AcessoRepository acessoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.perguntaRepository = perguntaRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.acessoRepository = acessoRepository;
    }

    @Override
    public Usuario criarUsuario(String nome, String email, String senha, boolean funcionario)  {
        return usuarioRepository.criarUsuario(nome, email, senha, funcionario);
    }

    @Override
    public Usuario alterarNome(int id, String nome) {
        return usuarioRepository.alterarNome(id,nome);
    }

    @Override
    public Usuario alterarEmail(String email) {
        return null;
    }

    @Override
    public ArrayList<Usuario> listarUsuarios() {
        return (ArrayList<Usuario>) usuarioRepository.listarUsuarios();
    }

    @Override
    public void deletarUsuario(int id) {
        for (Agendamento agendamento : agendamentoRepository.listarAgendamentos()){
            if(agendamento.getUserId() == id){
                agendamentoRepository.deletarAgendamento(agendamento.getId());
            }
        }
        for (Acesso acesso : acessoRepository.listarAcesso()){
            if(acesso.getId() == id){
                acessoRepository.deletarAcesso(id);
            }
        }
        usuarioRepository.deletarUsuario(id);
    }

    @Override
    public Usuario login(String email, String senha) {
        if (loginExiste(email, senha)){
            return usuarioRepository.getUsuarioByEmail(email);
        }
        throw new CredencialException("Login incorreto!");
    }

    private boolean loginExiste(String email, String senha){
        ArrayList<Usuario> listaUsuario = this.listarUsuarios();
        for(Usuario usuario: listaUsuario){
            if(usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)){
                return true;
            }
        }
        return false;
    }

}
