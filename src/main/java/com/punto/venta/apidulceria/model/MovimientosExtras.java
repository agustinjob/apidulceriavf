package com.punto.venta.apidulceria.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="movimientos_extras")
public class MovimientosExtras implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMovimiento;
    private String tipo;
    private String descripcion;
    private float monto;
    private Date fecha;
    private boolean revisado;
    private Integer idUsuario;
    private Integer idSucursal;
    private String propietario;  
}
