package com.example.chatcompresion;

import java.util.ArrayList;
import java.util.List;
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

    public MensajeEnviar(String mensaje, String urlFoto, String nombre, String fotoPerfil, String type_mensaje, Map hora, Map<String, Integer> freq) {
        super(mensaje, urlFoto, nombre, fotoPerfil, type_mensaje, freq);
        this.hora = hora;
    }

    public Map getHora() {
        return hora;
    }

    public void setHora(Map hora) {
        this.hora = hora;
    }

}
