package Huffman;

import java.util.ArrayList;
import java.util.List;

public class ArbolHuffman {

    private Nodo raiz = null;
    private int nivel = 0, max = 0;
    private String cadena = "";

    public Nodo crearArbol(List<Nodo> padres){

        ArrayList <Nodo> hijos;
        hijos = new ArrayList<>();

        for(int i = 0; i<padres.size()-1;i++){

            hijos.add(padres.get(i));

        }

        raiz = padres.get(padres.size()-1);

        Nodo tempRaiz, tempHijo;
        int j = hijos.size()-1;
        boolean vacio1 = true, vacio2 = true;

        while(!hijos.isEmpty()){

            tempRaiz = padres.get(padres.size()-1);
            tempHijo = hijos.get(j);

            if( tempHijo.getValor().equals(tempRaiz.getDerecho().getValor()) && vacio1 ){

                tempRaiz.setDerecho(tempHijo);
                j--;
                vacio1=false;
                tempRaiz.getDerecho().setCodigo(tempRaiz.getCodigo() + "1");
                tempHijo.getDerecho().setCodigo(tempRaiz.getCodigo() + "11");
                tempHijo.getIzquierdo().setCodigo(tempRaiz.getCodigo() + "10");
                hijos.remove(tempHijo);

            }else if( tempHijo.getValor().equals(tempRaiz.getIzquierdo().getValor()) && vacio2 ){

                tempRaiz.setIzquierdo(tempHijo);
                j--;
                vacio2=false;
                tempRaiz.getIzquierdo().setCodigo(tempRaiz.getCodigo() + "0");
                tempHijo.getIzquierdo().setCodigo(tempRaiz.getCodigo() + "00");
                tempHijo.getDerecho().setCodigo(tempRaiz.getCodigo() + "01");
                hijos.remove(tempHijo);

            } else if ((j-1) == -1) {

                padres.remove(tempRaiz);
                j = hijos.size()-1;
                vacio1 = true;
                vacio2 = true;

            } else {

                j--;

            }

        }

        return raiz;

    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    String printInorder(Nodo nodo){

        if (nodo == null){
            return "";
        }

        nivel++;
        printInorder(nodo.getIzquierdo());
        nivel--;

        cadena = cadena + nodo.getValor() +", ";

        if(nivel > max){

            max = nivel;

        }

        nivel++;
        printInorder(nodo.getDerecho());
        nivel--;

        return cadena;

    }

    public int getMax() {
        return max;
    }


}
