package com.example.chatcompresion;

import java.util.Map;

public class MensajeRecibir extends Mensaje {

    private Long hora;

    public MensajeRecibir() {}

    public MensajeRecibir(Long hora) {
        this.hora = hora;
    }

    public MensajeRecibir(String cadenaRojo , String cadenaVerde, String cadenaAzul, String nombre, String fotoPerfil, String type_mensaje, Long hora , Map<String,Integer> freqRoja , Map<String, Integer> freqVerde, Map<String, Integer> freqAzul , int ancho, int alto) {
        super(cadenaRojo, cadenaVerde, cadenaAzul, nombre, fotoPerfil, type_mensaje, freqRoja, freqVerde, freqAzul, ancho, alto);
        this.hora = hora;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }
}
