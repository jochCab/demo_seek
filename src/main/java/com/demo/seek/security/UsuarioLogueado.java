package com.demo.seek.security;

import com.demo.seek.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioLogueado implements UserDetails {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioLogueado(String nombre, String apellido, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UsuarioLogueado build(Usuario usuario){
        List<GrantedAuthority> authorities = null; /*usuario.getPermisos().stream().map(rol -> new SimpleGrantedAuthority(rol
                        .getNombre())).collect(Collectors.toList());*/
        return new UsuarioLogueado(usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), usuario.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}
