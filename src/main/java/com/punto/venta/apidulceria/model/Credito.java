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
@Table(name="credito")
public class Credito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCredito;
    private Integer idCliente;
    private Integer idTicket;
    private Date    fecha;
    private Date    fechaVenta;
    private float   monto;
    private float   abonado;
    private boolean finalizado;
    private Integer idUsuario;
    private Integer idSucursal;
    private String  propietario;
}
