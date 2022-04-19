package com.punto.venta.apidulceria.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ventas")
public class Ventas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVenta;
    private Integer idSucursal;
    private int idTicket;
    private String nombre;
    private String codigo;
    private float precioVenta;
    private float precioCosto;
    private float cantidad;
    private float importe;
    private Date fecha;
    private int idUsuario;
    private int idCliente;
    private String mayoreo;
    private boolean revisada=false;
    private boolean devolucion=false;
    private boolean devocompleta=false;
    private String tipoCompra="Efectivo";
    private boolean finalizada=true;
    private String propietario;
    





}


   
