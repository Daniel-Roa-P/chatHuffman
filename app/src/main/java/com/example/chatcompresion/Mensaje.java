package com.example.chatcompresion;

import java.util.ArrayList;
import java.util.List;

import Huffman.Nodo;

public class Mensaje {

    private String mensaje;
    private String urlFoto;
    private String nombre;
    private String fotoPerfil;
    private String type_mensaje;
    private List<String> letras;
    private List<Integer> frecuencias;


    public Mensaje() {}

    public Mensaje(String mensaje, String nombre, String fotoPerfil, String type_mensaje , List<String> letras, List<Integer> frecuencias) {

        this.mensaje = mensaje;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.type_mensaje = type_mensaje;
        this.letras = letras;
        this.frecuencias = frecuencias;

    }

    public Mensaje(String mensaje, String urlFoto, String nombre, String fotoPerfil, String type_mensaje, List<String> letras , List<Integer> frecuencias) {

        this.mensaje = mensaje;
        this.urlFoto = urlFoto;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.type_mensaje = type_mensaje;
        this.letras = letras;
        this.frecuencias = frecuencias;

    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getType_mensaje() {
        return type_mensaje;
    }

    public void setType_mensaje(String type_mensaje) {
        this.type_mensaje = type_mensaje;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public List<String> getLetras() { return letras; }

    public void setLetras(List<String> letras) { this.letras = letras; }

    public List<Integer> getFrecuencias() { return frecuencias; }

    public void setFrecuencias(List<Integer> frecuencias) { this.frecuencias = frecuencias; }

}
