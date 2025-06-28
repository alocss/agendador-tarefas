package com.alexribeiro.agendadortarefas.infrastructure.security;

import com.alexribeiro.agendadortarefas.business.dto.UsuarioDTO;
import com.alexribeiro.agendadortarefas.infrastructure.security.client.UsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioClient client;

    // Método customizado para buscar usuário com token
    public UserDetails loadUserByUsernameAndToken(String username, String token) throws UsernameNotFoundException {
        UsuarioDTO usuarioDTO = client.buscaUsuarioPorEmail(username, token);
        if (usuarioDTO == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + username);
        }
        return User.withUsername(usuarioDTO.getEmail())
                .password(usuarioDTO.getSenha())
                .authorities("USER")
                .build();
    }

    // Mantém o método padrão para compatibilidade (não usar token aqui)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Use loadUserByUsernameAndToken para autenticação com token.");
    }
}

