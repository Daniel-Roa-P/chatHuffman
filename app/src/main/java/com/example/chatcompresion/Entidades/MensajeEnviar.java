package com.example.chatcompresion.Entidades;

import com.example.chatcompresion.Entidades.Mensaje;

import java.util.Map;

public class MensajeEnviar extends Mensaje {

    private Map hora;

    public MensajeEnviar() {
    }

    public MensajeEnviar(Map hora) {
        this.hora = hora;
    }

    public MensajeEnviar(String mensaje, String nombre, String fotoPerfil, String type_mensaje, Map hora, Map<String, Integer> freq) {
        super(mensaje, nombre, fotoPerfil, type_mensaje, freq);
        this.hora = hora;
    }

    public MensajeEnviar(String cadenaRojo , String cadenaVerde, String cadenaAzul, String nombre, String fotoPerfil, String type_mensaje, Map hora , Map<String,Integer> freqRoja , Map<String, Integer> freqVerde,  Map<String, Integer> freqAzul , int ancho, int alto) {
        super(cadenaRojo, cadenaVerde, cadenaAzul, nombre, fotoPerfil, type_mensaje, freqRoja, freqVerde, freqAzul, ancho, alto);
        this.hora = hora;
    }

    public Map getHora() {
        return hora;
    }

    public void setHora(Map hora) {
        this.hora = hora;
    }

}
