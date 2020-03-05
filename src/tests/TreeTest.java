package tests;

import datastructures.Tree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {
    static Tree<Integer> createTree(int i){
        switch (i) {
            case 1: Tree<Integer> t1 = new Tree<>(1);
                    t1.getRoot().addChild(2);
                    t1.getRoot().addChild(3);
                    t1.getRoot().addChild(4);
                    return t1;
            case 2: Tree<Integer> t2 = new Tree<>(2);
                    t2.getRoot().addChild(2);
                    t2.getRoot().getChildren().get(0).addChild(3);
                    t2.getRoot().getChildren().get(0).getChildren().get(0).addChild(4);
                    return t2;
            case 3: Tree<Integer> t3 = new Tree<>(3);
            case 4: Tree<Integer> t4 = new Tree<>(4);
        }
        return null;
    }
    @Test
    void getAllNodesT1All() {
        Tree<Integer> t = TreeTest.createTree(1);
        assertEquals(4 , t.getAllNodes().size());
    }

    @Test
    void getAllNodesT1Leaves() {
        Tree<Integer> t = TreeTest.createTree(1);
        assertEquals(3 , t.getAllLeaves().size());
    }

    @Test
    void removeT1() {
        Tree<Integer> t = TreeTest.createTree(1);
        t.getRoot().getChildren().get(0).remove();
        assertEquals(3 , t.getAllNodes().size());

    }
    @Test
    void getAllNodesT2All() {
        Tree<Integer> t = TreeTest.createTree(2);
        assertEquals(4 , t.getAllNodes().size());
    }

    @Test
    void getAllNodesT2Leaves() {
        Tree<Integer> t = TreeTest.createTree(2);
        assertEquals(1 , t.getAllLeaves().size());
    }

    @Test
    void removeT2() {
        Tree<Integer> t = TreeTest.createTree(2);
        t.getRoot().getChildren().get(0).remove();
        assertEquals(1 , t.getAllNodes().size());

    }

    //@Test
    void remove() {
    }

    //@Test
    void getRoot() {
    }
}