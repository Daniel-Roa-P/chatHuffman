package Huffman;

class Node{

    String ch;
    Integer freq;
    Node left = null, right = null;

    Node(String ch, Integer freq){

        this.ch = ch;
        this.freq = freq;

    }

    public Node(String ch, Integer freq, Node left, Node right){

        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;

    }
}
