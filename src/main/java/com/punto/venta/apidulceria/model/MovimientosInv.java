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
@Table(name="movimientos_inv")
public class MovimientosInv implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idMovimientoInv;
    Integer idSucursal;
    String nombre;
    String descripcion;
    Date fecha;
    String tipoMovimiento;
    float habia;
    float cantidad;
    float hay;
    Integer idUsuario;
    String departamento;
    String propietario;
    
}
