package tests;

import org.junit.jupiter.api.Test;
import datastructures.Node;
import utils.Pair;
import utils.Rectangle;
import utils.QuadMaskingTree;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RectangleMaskingTreeTest {

    @Test
    void getAllNodesOne() {
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(16, 16));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        assertEquals(1, tree.getAllNodes().size());
    }
    @Test
    void getAllNodesTwo() {
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(16, 16));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.getRoot().addChild(q1);

        assertEquals(2, tree.getAllNodes().size());
    }
    @Test
    void getAllNodesThreeInDepth() {
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(16, 16));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.getRoot().addChild(q1);
        tree.getRoot().getChildren().get(0).addChild(q1);

        assertEquals(3, tree.getAllNodes().size());
    }
    @Test
    void getAllNodes1X4X4() {
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(16, 16));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.getRoot().addChild(q1);
        tree.getRoot().addChild(q1);
        tree.getRoot().addChild(q1);
        tree.getRoot().addChild(q1);

        for(Node n : tree.getRoot().getChildren()){
            n.addChild(q1);
            n.addChild(q1);
            n.addChild(q1);
            n.addChild(q1);
        }

        assertEquals(21, tree.getAllNodes().size());
    }
    @Test
    void possiblePositions(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(16, 16));
        Rectangle q2 = new Rectangle(new Point(0, 0), new Point(1, 1));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.getRoot().addChild(q1);
        tree.getRoot().addChild(q1);
        tree.getRoot().addChild(q1);
        tree.getRoot().addChild(q1);

        for(Node n : tree.getRoot().getChildren()){
            n.addChild(q1);
            n.addChild(q1);
            n.addChild(q1);
            n.addChild(q1);
        }

        assertEquals(256, tree.getAllPossiblePositions(q2).size());
    }

    @Test
    void splitTree(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(8, 8));
        
        Rectangle q2 = new Rectangle(new Point(4, 4), new Point(6, 6));
        Rectangle q3 = new Rectangle(new Point(1, 1), new Point(3, 3));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.splitNode(q2);
        tree.splitNode(q3);

        assertEquals(13, tree.getAllNodes().size());

    }
    @Test
    void splitTreeNotOverlapping(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(4, 4));
        Rectangle q2 = new Rectangle(new Point(4, 4), new Point(6, 6));
        QuadMaskingTree tree = new QuadMaskingTree(q1);
        tree.splitNode(q2);

        assertEquals(1, tree.getAllNodes().size());

    }
    @Test
    void splitTreeExt(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(8, 8));
        Rectangle q2 = new Rectangle(new Point(4, 4), new Point(6, 6));
        Rectangle q3 = new Rectangle(new Point(0, 0), new Point(3, 3));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.splitNode(q2);
        tree.splitNode(q3);

        assertEquals(9, tree.getAllNodes().size());

    }

    @Test
    void splitTreeImPossiblePos1(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(4, 4));
        Rectangle q2 = new Rectangle(new Point(0, 0), new Point(1, 1));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.splitNode(q1);

        assertEquals(0, tree.getAllPossiblePositions(q2).size());

    }
    @Test
    void splitTreeImPossiblePos2(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(4, 4));
        Rectangle q2 = new Rectangle(new Point(0, 0), new Point(1, 1));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.splitNode(q1);
        tree.splitNode(q2);
        assertEquals(0, tree.getAllPossiblePositions(q2).size());

    }

    @Test
    void splitTreePossiblePos2(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(4, 4));
        Rectangle q2 = new Rectangle(new Point(2, 2), new Point(4, 4));

        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.splitNode(q2);

        assertEquals(5, tree.getAllPossiblePositions(q2).size());

    }

    @Test
    void splitTreePossiblePos3(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(4, 4));
        Rectangle q2 = new Rectangle(new Point(2, 2), new Point(4, 4));
        Rectangle q3 = new Rectangle(new Point(2, 0), new Point(4, 2));

        QuadMaskingTree tree = new QuadMaskingTree(q1);

        System.out.println(tree.splitNode(q2));
        System.out.println(
                tree.splitNode(q3));

        assertEquals(3, tree.getAllPossiblePositions(q2).size());

    }

    @Test
    void splitTreePossiblePos3_1(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(2, 2));
        Rectangle q2 = new Rectangle(new Point(1, 1), new Point(2, 2));
        Rectangle q3 = new Rectangle(new Point(1, 0), new Point(1, 1));

        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.splitNode(q2);
        tree.splitNode(q3);

        assertEquals(3, tree.getAllPossiblePositions(q2).size());

    }


    @Test
    void generateFittingQuadsSimple(){
        Rectangle root = new Rectangle(new Point(0, 0), new Point(5, 3));
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(2, 3));
        Rectangle q2 = new Rectangle(new Point(0, 0), new Point(1, 1));

        List<Rectangle> rectangles = new ArrayList<>();

        rectangles.add(q1);
        rectangles.add(q1);
        rectangles.add(q2);

        QuadMaskingTree tree = new QuadMaskingTree(root);

        rectangles = tree.generateFittingQuads(rectangles);
        assertEquals(2.0, tree.getCurrentArea());


    }

    @Test
    void generateFittingQuadsSimpleList(){
        Rectangle root = new Rectangle(new Point(0, 0), new Point(5, 3));
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(2, 3));
        Rectangle q2 = new Rectangle(new Point(0, 0), new Point(1, 1));

        List<Rectangle> rectangles = new ArrayList<>();

        rectangles.add(q1);
        rectangles.add(q1);
        rectangles.add(q2);

        QuadMaskingTree tree = new QuadMaskingTree(root);

        rectangles = tree.generateFittingQuads(rectangles);

        assertEquals(2.0, tree.getCurrentArea());


    }
}