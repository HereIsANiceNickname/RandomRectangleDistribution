package tests;

import org.junit.jupiter.api.Test;
import datastructures.Node;
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

        tree.splitNode(q2);
        tree.splitNode(q3);

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

        rectangles = tree.generateFittingPositions(rectangles);
        assertEquals(2.0, tree.getCurrentArea());


    }

    @Test
    void generateFittingQuadsSimpleUpscale(){
        Rectangle root = new Rectangle(new Point(0, 0), new Point(5000, 3000));
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(2000, 3000));
        Rectangle q2 = new Rectangle(new Point(0, 0), new Point(1000, 1000));

        List<Rectangle> rectangles = new ArrayList<>();

        rectangles.add(q1);
        rectangles.add(q1);
        rectangles.add(q2);

        QuadMaskingTree tree = new QuadMaskingTree(root);

        rectangles = tree.generateFittingPositions(rectangles);
        assertEquals(16000000, tree.getCurrentArea());


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

        rectangles = tree.generateFittingPositions(rectangles);
        System.out.println(rectangles);
        assertEquals(2, tree.getCurrentArea());


    }

    @Test
    void generateFittingRealData(){
        int border = 50;
        int offset = 25;
        Rectangle root = new Rectangle(new Point(155+offset, 5+offset),990-offset, 740-offset);
        Rectangle machine = new Rectangle(new Point(0, 0), 200+border, 150+border);
        Rectangle regal = new Rectangle(new Point(0, 0),200+border, 140+border);
        Rectangle ladebucht = new Rectangle(new Point(0, 0), 300+border, 80+border);
        Rectangle ablade = new Rectangle(new Point(155, 350), 50, 150);
        List<Rectangle> rectangles = new ArrayList<>();


        for(int x = 0; x< 4; x++){
            rectangles.add(machine);
        }
        for(int x = 0; x< 2; x++){
            rectangles.add(regal);
        }
        rectangles.add(ladebucht);

        QuadMaskingTree tree = new QuadMaskingTree(root);
        tree.splitNode(ablade);
        rectangles = tree.generateFittingPositions(rectangles);
        for(Rectangle r :rectangles){
            System.out.println("name="+RectangleMaskingTreeTest.getName(r));
            System.out.println("x="+(r.leftTop.x+offset)+", y="+(r.leftTop.y+offset));
        }
        assertEquals(2, tree.getCurrentArea());


    }

    private static String getName(Rectangle r) {

        int border = 50;
        int offset = 25;
        Rectangle root = new Rectangle(new Point(155+offset, 5+offset),990-offset, 740-offset);
        Rectangle machine = new Rectangle(new Point(0, 0), 200+border, 150+border);
        Rectangle regal = new Rectangle(new Point(0, 0),200+border, 140+border);
        Rectangle ladebucht = new Rectangle(new Point(0, 0), 300+border, 80+border);
        Rectangle ablade = new Rectangle(new Point(155, 350), 50, 150);

        if(r.dimensionY.equals(machine.dimensionY) &&
                r.dimensionX.equals(machine.dimensionX)){
            return "Machine";
        }
        else if(r.dimensionY.equals(regal.dimensionY) && r.dimensionX.equals(regal.dimensionX)){
            return "Regal";
        }
        else if(r.dimensionY.equals(ladebucht.dimensionY) && r.dimensionX.equals(ladebucht.dimensionX)){
            return "Ladebucht";
        }
        else{
            return "NoNameFound";
        }
    }
}