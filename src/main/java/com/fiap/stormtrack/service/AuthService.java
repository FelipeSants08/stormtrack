package com.fiap.stormtrack.service;

import com.fiap.stormtrack.model.Credentials;
import com.fiap.stormtrack.model.Token;
import com.fiap.stormtrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(username, null));
        System.out.println("User found:" + user);
        return user;
    }

    public Token login(Credentials credentials){
        var user = repository.findByEmail(credentials.email())
                .orElseThrow( () -> new UsernameNotFoundException("Usuário não encontrado", null));

        if(!passwordEncoder.matches(credentials.password(), user.getPassword()) ){
            throw new BadCredentialsException("Senha incorreta");
        }

        return tokenService.createToken(user);
    }

}
