package datastructures;

import utils.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node<E>{
    private static Integer numberOfNodes = 0;
    private int id;
    private E value;
    private Node<E> parent;
    private List<Node<E>> children = new ArrayList<>();
    private boolean touched;

    public Node(E q){
        this.id = createId();
        this.value = q;
        this.parent = this;
        this.touched = false;
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
    public Node(E q, Node<E> parent){
        this.id = createId();
        this.value = q;
        this.parent = parent;
        this.touched = false;
    }
    private int createId(){
        synchronized (numberOfNodes){
            id = numberOfNodes;
            numberOfNodes++;
        }
        return id;
    }
    public void addChild(E e){
        this.children.add(new Node<E>(e, this));
    }
    public void addChildren(List<E> es){
        for(E e : es){
            addChild(e);
        }
    }
    public List<Node<E>> getChildren(){
        return children;
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
        return "Node{" +
                "id=" + id +
                ", children=" + children +
                '}';
    }

    public void remove() {
        this.parent.getChildren().remove(this);
        this.children = new ArrayList<>();
    }

}
