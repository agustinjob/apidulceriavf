package com.punto.venta.apidulceria.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="sucursal")
public class Sucursal {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long idSucursal;
    private String nombre;
    private String direccion;
    private String imagen;
    private String idUsuario;
    private String propietario;
    private String estatusSucursal;
    
}

