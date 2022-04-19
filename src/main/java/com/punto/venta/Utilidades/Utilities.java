package com.punto.venta.Utilidades;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {
    static DateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
    static DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
    static DateFormat formatoTodo= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    static DateFormat formatoCompletoConGuion = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date getFecha(){
        Date date=new Date();
        String fecha=formatoFecha.format(date);
       try {
       date= formatoFecha.parse(fecha);
    } catch (ParseException e) {
        
    }
    return date;
    }   

    public static Date getFecha(Date date){
        String fecha=formatoFecha.format(date);
       try {
       date= formatoFecha.parse(fecha);
    } catch (ParseException e) {
        
    }
    return date;
    } 

    public static String getFechaString(){
        Date date=new Date();
        String fecha=formatoFecha.format(date);
       System.out.println(fecha);
    return fecha;
    }
    
    public static Date convertirFechaStrinADate(String fecha){
       try {
        return formatoTodo.parse(fecha);
    } catch (ParseException e) {
        
        return null;
    }
    }

    public static Date convertirFechaStringCompletoADate(String fecha){
        try {
         return formatoCompletoConGuion.parse(fecha);
     } catch (ParseException e) {
         
         return null;
     }
     }

    public static Date getHora(){
        Date date=new Date();
       String hora= formatoHora.format(date); 
       try {
        date= formatoFecha.parse(hora);
     } catch (ParseException e) {
         
     }
     return date;
    }
}
