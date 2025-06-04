package com.tecsup.demo.domain.entities;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @Column(name = "codigo", length = 10)
    private String codigo;

    @Column(name = "nombres", length = 50)
    private String nombres;

    @Column(name = "apellidos", length = 50)
    private String apellidos;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // 👈 para convertir correctamente desde formulario
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nac")
    private Date fechaNac;

    @Column(name = "sexo", length = 1)
    private String sexo;

    // 🔹 Constructor vacío
    public Alumno() {
    }

    // 🔹 Constructor con campos
    public Alumno(String codigo, String nombres, String apellidos, Date fechaNac, String sexo) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNac = fechaNac;
        this.sexo = sexo;
    }

    // 🔹 Getters y Setters

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
