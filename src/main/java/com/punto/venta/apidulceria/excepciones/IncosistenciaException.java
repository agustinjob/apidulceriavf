package com.punto.venta.apidulceria.excepciones;

public class IncosistenciaException extends Exception {
    public static final long serialVersionUID = 1L;

    public IncosistenciaException(String cause){
        super(cause);
    }
}
