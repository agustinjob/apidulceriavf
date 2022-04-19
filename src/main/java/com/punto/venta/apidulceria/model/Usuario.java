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
@Table(name="usuario")
public class Usuario implements Serializable{

  //  private static final long serialVersionUID = -2164553723990982332L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    private Integer idSucursal;
    private String nombre;
    private String direccion;
    private String username;
    private String password;
    private String tipoUsuario;
    private String telefono;
    private Date fecha;
    private boolean enSesion;
    private boolean eliminado;
    private String foto;
    private String propietario;
    

    
}
