package datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tree<E> {
    Node<E> root;
    List<Node<E>> allNodes = new ArrayList<>();

    public Tree(E rootValue){
        root = new Node<>(rootValue, this);
    }

    public List<Node<E>> getAllNodes(){
        return allNodes;
    }

    public List<Node<E>> getAllLeaves(){
        return getAllNodes().
                stream().
                filter(n->n.getChildren().isEmpty()).
                collect(Collectors.toList());
    }

    public void remove(Node<E> node){

    }

    public Node<E> getRoot() {
        return this.root;
    }
}
