package com.demo.seek.security.service;

import com.demo.seek.entity.Usuario;
import com.demo.seek.security.UsuarioLogueado;
import com.demo.seek.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Email no encontrado: " + email);
        }
        return UsuarioLogueado.build(usuario);
    }
}
