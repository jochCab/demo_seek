package com.demo.seek.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;


@Data
@Entity
@Table(name = "usuarios")
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellido;

    @Column(name = "documento", nullable = false)
    private String documento;


    @Column(name = "fecha_nacimiento")
    private Timestamp fechaNacimiento;


    private String email;


    private String password;



    @Column(name = "codigo_verificacion")
    private Integer codigoVerificacion;

    private String celular;


    public Usuario() {
    }

    public Usuario(String nombre, String apellido, Timestamp fechaNacimiento, Boolean activo, String documento, String email, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.setDocumento(documento);
        this.email = email;
        this.password = password;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }


    public Timestamp getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Timestamp fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

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
    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }
}
