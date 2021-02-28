package Huffman;

import android.os.Build;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import androidx.annotation.RequiresApi;

public class HuffmanImagenes{

    Node root;
    int [][] matriz;
    String cadenaNueva = "";
    String cadenaDeco = "";
    Map<String, Integer> freq;
    Map<String, String> huffmanCode;
    int alto, ancho;

    public void codificar(Node root, String str, Map<String, String> huffmanCode){

        if (root == null) {
            return;
        }

        if (isLeaf(root)) {
            huffmanCode.put(String.valueOf(root.ch), str.length() > 0 ? str : "1");
        }

        codificar(root.left, str + '0', huffmanCode);
        codificar(root.right, str + '1', huffmanCode);

    }

    public static boolean isLeaf(Node root) {

        return root.left == null && root.right == null;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void crearDiccionario(int [][] matriz, int alto, int ancho) {

        this.matriz = matriz;
        this.alto = alto;
        this.ancho = ancho;

        freq = new HashMap<>();

        for(int i = 0; i<alto; i++){

            for(int j = 0; j < ancho; j++){

                freq.put(String.valueOf(matriz[i][j]), freq.getOrDefault(String.valueOf(matriz[i][j]), 0) + 1);

            }

        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void contruirArbol(){

        PriorityQueue<Node> pq;
        pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));

        for ( Map.Entry<String, Integer> entry: freq.entrySet()) {

            pq.add(new Node(entry.getKey(), entry.getValue()));

        }

        while (pq.size() != 1){

            Node left = pq.poll();
            Node right = pq.poll();

            int sum = left.freq + right.freq;
            pq.add(new Node(null, sum, left, right));

        }

        root = pq.peek();

        huffmanCode = new HashMap<>();
        codificar(root, "", huffmanCode);

    }

    public void crearCodigo(){


        for(int i = 0; i<alto; i++){

            for(int j = 0; j < ancho; j++){

                cadenaNueva = cadenaNueva + huffmanCode.get(String.valueOf(matriz[i][j]));

            }

        }

    }

    public int [][] desencriptar(int alto, int ancho){

        int [][] color = new int[alto][ancho];

        int indice = 0;
        int x = 0, y = 0;

        Node aux = root;

        while(indice < cadenaNueva.length()){

            String temp = String.valueOf(cadenaNueva.charAt(indice));

            if(aux.right != null && temp.equals("1")){

                aux = aux.right;
                indice++;

            } else if (aux.left != null && temp.equals("0")) {

                aux = aux.left;
                indice++;

            } else {

                color[y][x] = Integer.parseInt(aux.ch);
                aux = root;

                x++;

                if(x == ancho){

                    x=0;
                    y++;

                }

                if(indice == cadenaNueva.length() - 1){

                    indice++;

                }

            }

        }

        return color;

    }

    public String getCadenaNueva() {
        return cadenaNueva;
    }

    public void setCadenaNueva(String cadenaNueva) {
        this.cadenaNueva = cadenaNueva;
    }

    public String getCadenaDeco() {
        return cadenaDeco;
    }

    public void setCadenaDeco(String cadenaDeco) {
        this.cadenaDeco = cadenaDeco;
    }

    public Map<String, Integer> getFreq() {
        return freq;
    }

    public void setFreq(Map<String, Integer> freq) {
        this.freq = freq;
    }
}

