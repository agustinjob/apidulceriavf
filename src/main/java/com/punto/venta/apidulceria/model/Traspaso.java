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
@Table(name="traspaso")
public class Traspaso {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idTraspaso;// agregar autoincrementable
    private int idSucursalEnvia;
    private int idSucursaRecibe;
    private int idProducto;
    private float precioCompra;
    private float cantidad;
    private float monto;
    private Date fecha;

    private String realizadaEn;
    private String estatus;
    private String operacion;
    private String propietario;


    
}
