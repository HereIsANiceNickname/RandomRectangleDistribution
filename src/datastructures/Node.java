package datastructures;

import utils.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class Node<E>{
    private static Integer numberOfNodes = 0;
    private Tree<E> tree;
    private int id;
    private E value;
    private Node<E> parent;
    private List<Node<E>> children = new ArrayList<>();
    private boolean touched;

    public Node(E q, Tree<E> tree){
        this.id = createId();
        this.value = q;
        this.parent = this;
        this.touched = false;
        this.tree = tree;
        this.tree.allNodes.add(this);
    }

    public Node(E q, Node<E> parent){
        this.id = createId();
        this.value = q;
        this.parent = parent;
        this.touched = false;
        this.tree = this.parent.tree;
        this.tree.allNodes.add(this);
    }

    public int getId() {
        return id;
    }

    public E getValue() {
        return value;
    }

    public Node<E> getParent() {
        return parent;
    }

    public void touch(){
        this.touched = true;
    }

    public boolean isTouched(){
        return touched;
    }

    public void untouch(){
        this.touched = false;
    }

    public boolean isRoot(){
        return this.parent.equals(this);
    }

    private int createId(){
        synchronized (numberOfNodes){
            id = numberOfNodes;
            numberOfNodes++;
        }
        return id;
    }
    public void addChild(E e){
        Node<E> node = new Node<E>(e, this);
        this.children.add(node);
    }
    public void addChildren(List<E> es){
        for(E e : es){ addChild(e); }
    }
    public List<Node<E>> getChildren(){
        return children;
    }

    public void remove() {
        Stack<Node<E>> nodes = new Stack<>();
        nodes.addAll(this.children);
        for(Node<E> node: nodes){
            node.remove();
        }
        this.parent.getChildren().remove(this);
        this.children = new ArrayList<>();
        this.tree.allNodes.remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<E> node = (Node<E>) o;
        return id == node.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Node{" + "id=" + id + ", children=" + children + '}';
    }
}
