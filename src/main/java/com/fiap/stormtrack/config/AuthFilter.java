package com.fiap.stormtrack.config;

import com.fiap.stormtrack.model.User;
import com.fiap.stormtrack.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("FILTER");

        // tem header de autenticação
        var header = request.getHeader("Authorization");
        if (header == null){
            System.out.println("Sem header Authorization");
            filterChain.doFilter(request, response);
            return;
        }

        // tem bearer?
        if(!header.startsWith("Bearer ")){
            System.out.println("Token não começa com Bearer");
            response.setStatus(401);
            response.getWriter().write("""
                        {"message": "Token deve começar com Bearer" }
                    """);
            return;
        }

        // validar token
        var token = header.replace("Bearer ", "");
        User user = tokenService.getUserFromToken(token);

        //autenticar usuario
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }


}
