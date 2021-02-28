package Huffman;

import android.os.Build;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import androidx.annotation.RequiresApi;

public class Huffman{

    Node root;
    String texto;
    String cadenaNueva = "";
    String cadenaDeco = "";
    Map<String, Integer> freq;
    Map<String, String> huffmanCode;

    public void codificar(Node root, String str, Map<String, String> huffmanCode){

        if (root == null) {
            return;
        }

        // Found a leaf node
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
    public void crearDiccionario(String texto) {

        this.texto = texto;

        if (texto == null || texto.length() == 0) {
            return;
        }

        freq = new HashMap<>();
        for (char c: texto.toCharArray()) {

            freq.put(String.valueOf(c), freq.getOrDefault(String.valueOf(c), 0) + 1);

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
        System.out.println("Huffman Codes are: " + huffmanCode);

    }

    public void crearCodigo(){

        System.out.println("Huffman Codes are: " + huffmanCode);

        StringBuilder sb = new StringBuilder();
        for (char c: texto.toCharArray()) {
            cadenaNueva = cadenaNueva + huffmanCode.get(String.valueOf(c));
        }

        System.out.println("The coded: " + cadenaNueva);

    }

    public void decodificar(){

        int indice = 0;

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

                cadenaDeco = cadenaDeco + aux.ch;
                aux = root;

                if(indice == cadenaNueva.length() - 1){

                    indice++;

                }

            }

        }

        System.out.println("The decoded: " + cadenaDeco);

    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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
