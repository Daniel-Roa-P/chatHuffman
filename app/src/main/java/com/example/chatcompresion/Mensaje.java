package com.example.chatcompresion;

import android.graphics.Bitmap;

import java.util.Map;

public class Mensaje {

    private String mensaje1;
    private String mensaje2;
    private String mensaje3;
    private String nombre;
    private String fotoPerfil;
    private String type_mensaje;
    private Map<String, Integer> freq1;
    private Map<String, Integer> freq2;
    private Map<String, Integer> freq3;
    private int alto, ancho;
    private Bitmap bitmap;

    public Mensaje() {}

    public Mensaje(String mensaje1, String nombre, String fotoPerfil, String type_mensaje , Map<String, Integer> freq1) {

        this.mensaje1 = mensaje1;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.type_mensaje = type_mensaje;
        this.freq1 = freq1;

    }

    public Mensaje(String mensaje1 , String mensaje2, String mensaje3, String nombre, String fotoPerfil, String type_mensaje, Map<String, Integer> freq1 , Map<String, Integer> freq2,  Map<String, Integer> freq3 , int ancho, int alto ) {

        this.mensaje1 = mensaje1;
        this.mensaje2 = mensaje2;
        this.mensaje3 = mensaje3;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.type_mensaje = type_mensaje;
        this.freq1 = freq1;
        this.freq2 = freq2;
        this.freq3 = freq3;
        this.ancho = ancho;
        this.alto =alto;

    }

    public String getMensaje1() {
        return mensaje1;
    }

    public void setMensaje1(String mensaje1) {
        this.mensaje1 = mensaje1;
    }

    public String getMensaje2() {
        return mensaje2;
    }

    public void setMensaje2(String mensaje2) {
        this.mensaje2 = mensaje2;
    }

    public String getMensaje3() {
        return mensaje3;
    }

    public void setMensaje3(String mensaje3) {
        this.mensaje3 = mensaje3;
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

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public Map<String, Integer> getFreq1() {
        return freq1;
    }

    public void setFreq1(Map<String, Integer> freq1) {
        this.freq1 = freq1;
    }

    public Map<String, Integer> getFreq2() {
        return freq2;
    }

    public void setFreq2(Map<String, Integer> freq2) {
        this.freq2 = freq2;
    }

    public Map<String, Integer> getFreq3() {
        return freq3;
    }

    public void setFreq3(Map<String, Integer> freq3) {
        this.freq3 = freq3;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
