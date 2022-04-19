package com.punto.venta.apidulceria.model.keys;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class UsuarioLlave implements Serializable {
    
    
    private int idUsuario;
    private int idSucursal;


}
