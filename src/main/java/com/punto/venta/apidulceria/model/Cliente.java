package com.punto.venta.apidulceria.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="cliente")
public class Cliente implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idCliente;
    private Integer idSucursal;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String rfc;
    private String mayorista;
    private int limiteCredito;
    private boolean eliminado;
    private String estatusCliente;
    private String propietario;
}
