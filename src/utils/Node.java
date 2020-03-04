package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private static Integer numberOfNodes = 0;
    private int id;
    private Quad value;
    private Node parent;
    private List<Node> children = new ArrayList<>();
    private boolean touched;

    public Node(Quad q){
        this.id = createId();
        this.value = q;
        this.parent = this;
        this.touched = false;
    }

    public int getId() {
        return id;
    }

    public Quad getValue() {
        return value;
    }

    public Node getParent() {
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
    public Node(Quad q, Node parent){
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
    public void addChild(Quad quad){
        this.children.add(new Node(quad, this));
    }
    public void addChildren(List<Quad> quads){
        for(Quad quad: quads){
            addChild(quad);
        }
    }
    public List<Node> getChildren(){
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
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
