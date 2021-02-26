package Huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Encriptado {

    private ArbolHuffman arbol = new ArbolHuffman();

    private List<Nodo> listaArbol = new ArrayList();

    private Nodo raiz;

    private String [][] matriz;

    private List <String>letras = new ArrayList();
    private List <Integer> frecuencia = new ArrayList();
    private List <Integer> copiaFrecuencias = new ArrayList();
    private List <Nodo> padres = new ArrayList();

    private Map<String, String> codigos = new HashMap<>();

    private String cadenaNueva = "";
    private String cadenaEspaciada = "";
    private String cadenaDeco = "";

    public void crearMatriz (String cadena) {

        for(int i = 0; i<cadena.length(); i++){

            String temp = String.valueOf(cadena.charAt(i));

            if(!letras.contains(temp)){

                letras.add(temp);
                frecuencia.add(0);
                copiaFrecuencias.add(0);

            }

            int index = letras.indexOf(temp);
            int nuevaFrecuencia = 1 + frecuencia.get(index);

            frecuencia.set(index, nuevaFrecuencia);
            copiaFrecuencias.set(index, nuevaFrecuencia);

        }

        for(int i=0;i < frecuencia.size() + 1; i++) {

            for(int j=i+1; j < frecuencia.size(); j++) {

                if(frecuencia.get(i) > frecuencia.get(j)) {

                    int temp = frecuencia.get(i);
                    frecuencia.set(i, frecuencia.get(j));
                    frecuencia.set(j, temp);

                    copiaFrecuencias.set(i, copiaFrecuencias.get(j));
                    copiaFrecuencias.set(j, temp);

                    String tempB = String.valueOf(letras.get(i));
                    letras.set(i, String.valueOf(letras.get(j)));
                    letras.set(j, tempB);

                }

            }

        }

        System.out.println(letras);
        System.out.println(frecuencia);

        crearArbol(frecuencia, letras);

        for(int k = 0; k<cadena.length(); k++){

            char temp = cadena.charAt(k);

            cadenaNueva = cadenaNueva + codigos.get(String.valueOf(temp));

        }

        System.out.println(cadenaNueva);

    }

    public void crearArbol(List<Integer> frecuencia, List letras){

        int tempColum = letras.size();

        int menor1 = 999, menor2 = 999;
        int indice1 = 0, indice2 = 0;

        Nodo padre, hijoDer, hijoIzq;

        matriz = new String [6][(letras.size()*2) - 1];

        for(int i = 0; i < 6; i++){

            for(int j = 0; j < ((letras.size()*2) - 1); j++){

                if(i < 2 && j < letras.size()){

                    matriz[0][j] = String.valueOf(letras.get(j));
                    matriz[1][j] = String.valueOf(frecuencia.get(j));

                } else {

                    matriz[i][j] = "0";

                }
            }

        }

        while(tempColum < ((letras.size()*2) - 1)) {

            for (int i = frecuencia.size() - 1; i >= 0; i--) {

                int temp = frecuencia.get(i);

                if (menor1 >= temp) {

                    menor2 = menor1;
                    menor1 = temp;

                    indice2 = indice1;
                    indice1 = i;

                } else if (temp < menor2 && temp != menor1) {

                    menor2 = temp;
                    indice2 = i;

                }

            }

            matriz[2][indice1] = Integer.toString(tempColum);
            matriz[2][indice2] = Integer.toString(tempColum);

            frecuencia.add(menor1 + menor2);
            padre = new Nodo(String.valueOf(menor1 + menor2));
            matriz[1][tempColum] = Integer.toString(menor1 + menor2);

            frecuencia.set(indice1, 999);
            frecuencia.set(indice2, 999);

            if (menor1 > menor2) {

                if (indice1 < letras.size()) {

                    hijoDer = new Nodo(matriz[0][indice1]);

                } else {

                    hijoDer = new Nodo(String.valueOf(menor1));

                }

                if (indice2 < letras.size()) {

                    hijoIzq = new Nodo(matriz[0][indice2]);

                } else {

                    hijoIzq = new Nodo(String.valueOf(menor2));

                }

                matriz[4][tempColum] = Integer.toString(indice1);
                matriz[5][tempColum] = Integer.toString(indice2);

                matriz[3][indice1] = Integer.toString(2);
                matriz[3][indice2] = Integer.toString(1);

            } else {

                if (indice1 < letras.size()) {

                    hijoIzq = new Nodo(matriz[0][indice1]);

                } else {

                    hijoIzq = new Nodo(String.valueOf(menor1));

                }

                if (indice2 < letras.size()) {

                    hijoDer = new Nodo(matriz[0][indice2]);

                } else {

                    hijoDer = new Nodo(String.valueOf(menor2));

                }

                matriz[4][tempColum] = Integer.toString(indice1);
                matriz[5][tempColum] = Integer.toString(indice2);

                matriz[3][indice1] = Integer.toString(1);
                matriz[3][indice2] = Integer.toString(2);

            }

            padre.setDerecho(hijoDer);
            padre.setIzquierdo(hijoIzq);

            padres.add(padre);

            tempColum++;
            menor1 = 999;
            menor2 = 999;

        }

        raiz = arbol.crearArbol(padres);

        List <Nodo> lista = new ArrayList();
        lista.add(raiz);
        listaArbol = new ArrayList();
        listaArbol.add(raiz);
        listarArbol(lista, 0, arbol.getMax());

        int i=0;

        while(i<listaArbol.size()){

            if(listaArbol.get(i) != null){

                if(listaArbol.get(i).getIzquierdo() == null && listaArbol.get(i).getDerecho() == null){

                    codigos.put(listaArbol.get(i).getValor() , listaArbol.get(i).getCodigo());

                }

            }

            i++;

        }

    }

    private boolean todoNull(List list) {

        for(Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

    public void listarArbol(List<Nodo> nodos, int nivel, int profundidad){

        if (nodos.isEmpty() || todoNull(nodos)){

            return;

        }

        List<Nodo> newNodes = new ArrayList<>();

        for (Nodo node : nodos) {
            if (node != null) {

                newNodes.add(node.getIzquierdo());
                newNodes.add(node.getDerecho());
                listaArbol.add(node.getIzquierdo());
                listaArbol.add(node.getDerecho());


            } else {

                newNodes.add(null);
                newNodes.add(null);
                listaArbol.add(null);
                listaArbol.add(null);

            }
        }

        listarArbol(newNodes, nivel + 1, profundidad);

    }

    public void desencriptar(){

        int indice = 0;

        Nodo aux = raiz;

        System.out.print(cadenaNueva);

        while(indice < cadenaNueva.length()){

            String temp = String.valueOf(cadenaNueva.charAt(indice));

            System.out.println("indice: " + indice + "temp " + temp);

            if(aux.getDerecho() != null && temp.equals("1")){

                aux = aux.getDerecho();
                indice++;

            } else if (aux.getIzquierdo() != null && temp.equals("0")) {

                aux = aux.getIzquierdo();
                indice++;

            } else {

                cadenaDeco = cadenaDeco + aux.getValor();
                aux = raiz;

                if(indice == cadenaNueva.length() - 1){

                    System.out.println("entre");
                    indice++;

                }

            }

        }

        System.out.println(cadenaDeco);

    };

    public String getCadenaNueva() { return cadenaNueva; }

    public void setCadenaNueva(String cadenaNueva) { this.cadenaNueva = cadenaNueva; }

    public List<Nodo> getPadres() {
        return padres;
    }

    public void setPadres(ArrayList<Nodo> padres) {
        this.padres = padres;
    }

    public List<String> getLetras() {
        return letras;
    }

    public void setLetras(List<String> letras) {
        this.letras = letras;
    }

    public List<Integer> getCopiaFrecuencias() { return copiaFrecuencias; }

    public void setCopiaFrecuencias(List<Integer> copiaFrecuencias) { this.copiaFrecuencias = copiaFrecuencias; }

    public String getCadenaDeco() { return cadenaDeco; }

    public void setCadenaDeco(String cadenaDeco) { this.cadenaDeco = cadenaDeco; }

}
