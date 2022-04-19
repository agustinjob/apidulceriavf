package com.punto.venta.apidulceria.model.respuesta;

import java.util.List;

import com.punto.venta.apidulceria.model.Usuario;

import lombok.Data;

@Data
public class UsuarioResponse {
    private List<Usuario> usuarios;
    private String mensaje;
    private boolean realizado;

}
