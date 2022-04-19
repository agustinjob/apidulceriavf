package com.punto.venta.apidulceria.model;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
@Entity
@Table(name="producto")
public class Producto implements Serializable{
  
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idProducto;
    private Integer idSucursal;
    private String codigo;
    private String descripcion;
    private float precioCosto;
    private float precioVenta;
    private float precioMayoreo;//
    private float precioDistribuidor;//
    private float cantidad;
    private float inventarioMinimo;
    private boolean eliminado;
    private String area;
    private String propietario;
    @Transient
    private Integer idUSuario;


}
