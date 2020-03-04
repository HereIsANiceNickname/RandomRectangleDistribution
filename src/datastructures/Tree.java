package datastructures;

import java.util.ArrayList;
import java.util.List;

public class Tree<E> {
    Node<E> root;

    public Tree(E rootValue){
        root = new Node<>(rootValue);
    }

    public List<Node<E>> getAllLeaves(){
        List<Node<E>> result = new ArrayList<>();
        return result;
    }

    public void add(){

    }

    public void remove(Node<E> node){

    }
}
