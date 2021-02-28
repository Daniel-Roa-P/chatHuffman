package com.example.chatcompresion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Huffman.Nodo;

public class MensajeRecibir extends Mensaje {

    private Long hora;
    private Map<String, Integer> freq;

    public MensajeRecibir() {
    }

    public MensajeRecibir(Long hora) {
        this.hora = hora;
    }

    public MensajeRecibir(String mensaje, String urlFoto, String nombre, String fotoPerfil, String type_mensaje, Long hora , Map<String, Integer> freq) {
        super(mensaje, urlFoto, nombre, fotoPerfil, type_mensaje, freq);
        this.hora = hora;
        this.freq = freq;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }

}
