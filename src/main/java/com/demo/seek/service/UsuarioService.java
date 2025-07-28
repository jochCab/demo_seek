package com.demo.seek.service;

import com.demo.seek.entity.Usuario;
import com.demo.seek.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;



    public Page<Usuario> findById(Long id, Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findById(id, pageable);
        return usuarios;
    }

    public Usuario findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        return usuario;
    }

    public Boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);

    }

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    public Page<Usuario> findAll(Pageable pageable){
        return usuarioRepository.findAll(pageable);
    }

}
