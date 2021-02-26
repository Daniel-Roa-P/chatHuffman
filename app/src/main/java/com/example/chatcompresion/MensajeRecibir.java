package com.example.chatcompresion;

import java.util.ArrayList;
import java.util.List;

import Huffman.Nodo;

public class MensajeRecibir extends Mensaje {

    private Long hora;
    private List<String> letras;

    public MensajeRecibir() {
    }

    public MensajeRecibir(Long hora) {
        this.hora = hora;
    }

    public MensajeRecibir(String mensaje, String urlFoto, String nombre, String fotoPerfil, String type_mensaje, Long hora , List<String> letras, List<Integer> frecuencias) {
        super(mensaje, urlFoto, nombre, fotoPerfil, type_mensaje, letras, frecuencias);
        this.hora = hora;
        this.letras = letras;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }

}
