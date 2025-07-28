package com.demo.seek.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginUsuario {


    @NotBlank
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    private String password;

    private Integer codigoVerificacion;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCodigoVerificacion() {
        return codigoVerificacion;
    }

    public void setCodigoVerificacion(Integer codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }
}
