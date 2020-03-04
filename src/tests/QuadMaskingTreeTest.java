package tests;

import org.junit.jupiter.api.Test;
import utils.Node;
import utils.Pair;
import utils.Quad;
import utils.QuadMaskingTree;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuadMaskingTreeTest {

    @Test
    void getAllNodesOne() {
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(16., 16.));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        assertEquals(1, tree.getAllNodes().size());
    }
    @Test
    void getAllNodesTwo() {
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(16., 16.));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.getRoot().addChild(q1);

        assertEquals(2, tree.getAllNodes().size());
    }
    @Test
    void getAllNodesThreeInDepth() {
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(16., 16.));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.getRoot().addChild(q1);
        tree.getRoot().getChildren().get(0).addChild(q1);

        assertEquals(3, tree.getAllNodes().size());
    }
    @Test
    void getAllNodes1X4X4() {
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(16., 16.));
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
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(16., 16.));
        Quad q2 = new Quad(new Pair<>(0., 0.), new Pair<>(1., 1.));
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
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(8., 8.));
        Quad q2 = new Quad(new Pair<>(4., 4.), new Pair<>(6., 6.));
        Quad q3 = new Quad(new Pair<>(1., 1.), new Pair<>(3., 3.));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.splitNode(q2);
        tree.splitNode(q3);

        assertEquals(13, tree.getAllNodes().size());

    }
    @Test
    void splitTreeNotOverlapping(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(4., 4.));
        Quad q2 = new Quad(new Pair<>(4., 4.), new Pair<>(6., 6.));
        QuadMaskingTree tree = new QuadMaskingTree(q1);
        tree.splitNode(q2);
        System.out.println(tree.getAllNodes());

        assertEquals(2, tree.getAllNodes().size());

    }
    @Test
    void splitTreeExt(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(8., 8.));
        Quad q2 = new Quad(new Pair<>(4., 4.), new Pair<>(6., 6.));
        Quad q3 = new Quad(new Pair<>(0., 0.), new Pair<>(3., 3.));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.splitNode(q2);
        tree.splitNode(q3);

        assertEquals(9, tree.getAllNodes().size());

    }

    @Test
    void splitTreeImPossiblePos1(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(4., 4.));
        Quad q2 = new Quad(new Pair<>(0., 0.), new Pair<>(1., 1.));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.splitNode(q1);

        assertEquals(0, tree.getAllPossiblePositions(q2).size());

    }
    @Test
    void splitTreeImPossiblePos2(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(4., 4.));
        Quad q2 = new Quad(new Pair<>(0., 0.), new Pair<>(1., 1.));
        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.splitNode(q1);
        tree.splitNode(q2);
        assertEquals(0, tree.getAllPossiblePositions(q2).size());

    }

    @Test
    void splitTreePossiblePos2(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(4., 4.));
        Quad q2 = new Quad(new Pair<>(2., 2.), new Pair<>(4., 4.));

        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.splitNode(q2);
        System.out.println(tree.getAllPossiblePositions(q2));

        assertEquals(5, tree.getAllPossiblePositions(q2).size());

    }

    @Test
    void splitTreePossiblePos3(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(4., 4.));
        Quad q2 = new Quad(new Pair<>(2., 2.), new Pair<>(4., 4.));
        Quad q3 = new Quad(new Pair<>(2., 0.), new Pair<>(2., 2.));

        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.splitNode(q2);
        tree.splitNode(q3);
        for(Node n : tree.getAllLeaves()){
            System.out.println(n.getValue().area);

        }

        System.out.println(tree.getAllPossiblePositions(q2));
        assertEquals(3, tree.getAllPossiblePositions(q2).size());

    }

    @Test
    void splitTreePossiblePos3_1(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(2., 2.));
        Quad q2 = new Quad(new Pair<>(1., 1.), new Pair<>(2., 2.));
        Quad q3 = new Quad(new Pair<>(1., 0.), new Pair<>(1., 1.));

        QuadMaskingTree tree = new QuadMaskingTree(q1);

        tree.splitNode(q2);
        tree.splitNode(q3);

        assertEquals(3, tree.getAllPossiblePositions(q2).size());

    }


    //@Test
    void generateFittingQuadsSimple(){
        Quad root = new Quad(new Pair<>(0., 0.), new Pair<>(5., 3.));
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(2., 3.));
        Quad q2 = new Quad(new Pair<>(0., 0.), new Pair<>(1., 1.));

        List<Quad> quads = new ArrayList<>();

        quads.add(q1);
        quads.add(q1);
        quads.add(q2);

        QuadMaskingTree tree = new QuadMaskingTree(root);

        quads = tree.generateFittingQuads(quads);

        System.out.println(quads.get(0).leftTop + " " + quads.get(0).area);
        System.out.println(quads.get(1).leftTop + " " + quads.get(1).area);
        System.out.println(quads.get(2).leftTop + " " + quads.get(2).area);

        assertEquals(2.0, tree.getCurrentArea());


    }

    //@Test
    void generateFittingQuadsSimpleList(){
        Quad root = new Quad(new Pair<>(0., 0.), new Pair<>(5., 3.));
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(2., 3.));
        Quad q2 = new Quad(new Pair<>(0., 0.), new Pair<>(1., 1.));

        List<Quad> quads = new ArrayList<>();

        quads.add(q1);
        quads.add(q1);
        quads.add(q2);

        QuadMaskingTree tree = new QuadMaskingTree(root);

        quads = tree.generateFittingQuads(quads);

        System.out.println(quads.get(0).leftTop + " " + quads.get(0).area);
        System.out.println(quads.get(1).leftTop + " " + quads.get(1).area);
        System.out.println(quads.get(2).leftTop + " " + quads.get(2).area);

        assertEquals(2.0, tree.getCurrentArea());


    }
}