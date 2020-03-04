package utils;

import datastructures.Node;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class QuadMaskingTree {
    private Node<Rectangle> root = null;
    private Integer area = 0;
    private final Random r = new Random();


    public QuadMaskingTree(Rectangle rootRectangle) {
        root = new Node<Rectangle>(rootRectangle);
        area = rootRectangle.area;
    }

    public List<Rectangle> generateFittingQuads(List<Rectangle> rectangles) {
        List<Rectangle> results = new ArrayList<>();
        rectangles.sort((Rectangle o1, Rectangle o2) -> r.nextInt(3) - 1);

        Stack<Snapshot> backtracks = new Stack<>();
        backtracks.push(createSnapshot(rectangles.get(0), backtracks, -1));

        for (int i = 0; i < rectangles.size(); ) {
            Rectangle rectangle = rectangles.get(i);
            Snapshot s = backtracks.peek();

            if (fits(rectangle, s)) {
                processQuad(s);
                backtracks.push(s);
                s = createSnapshot(rectangles.get(i + 1), backtracks, i);
                area -= rectangle.area;
            } else {
                fallback(s);
                if (s.getPositions().isEmpty()) {
                    backtracks.pop();
                }
                area += rectangle.area;
            }
            i = backtracks.size();
        }

        for (Snapshot s : backtracks) {
            results.add(s.rectangle);
        }

        return results;
    }


    private void fallback(Snapshot snap) {
        for (Node<Rectangle> node : snap.getNodes()) {
            node.remove();
        }

    }


    private Rectangle processQuad(Snapshot snap) {
        List<Point> currentPositions = snap.getPositions();
        Point position = currentPositions.get(r.nextInt(currentPositions.size()));
        currentPositions.remove(position);

        Rectangle movedRectangle = snap.rectangle.onPosition(position);
        List<Node<Rectangle>> alteredNodes = splitNode(movedRectangle);
        snap.getNodes().addAll(alteredNodes);
        snap.active = false;

        return movedRectangle;
    }

    private Snapshot createSnapshot(Rectangle rectangle, Stack<Snapshot> backtracks, int i) {

        if (i + 1 == backtracks.size() && !backtracks.empty()) {
            return backtracks.pop();
        } else {
            Snapshot snap = new Snapshot(rectangle);
            snap.getPositions().addAll(getAllPossiblePositions(rectangle));

            return snap;
        }
    }

    private boolean fits(Rectangle rectangle, Snapshot snap) {
        return rectangle.area <= area && snap.getPositions().size() > 0;
    }

    public List<Node<Rectangle>> splitNode(Point pos, Rectangle q) {
        Rectangle rectangle = new Rectangle(pos, new Point(pos.x + q.dimensionX, pos.y + q.dimensionY));
        return splitNode(rectangle);
    }

    public List<Node<Rectangle>> splitNode(Rectangle rectangle) {
        List<Node<Rectangle>> nodes = getIntersectingNodes(rectangle);
        List<Node<Rectangle>> alteredNodes = new ArrayList<>();
        for (Node<Rectangle> node : nodes) {
            alteredNodes.add(node);
            node.touch();
            node.addChildren(node.getValue().splitBy(rectangle));
        }

        return alteredNodes;
    }

    private List<Node<Rectangle>> getIntersectingNodes(Rectangle rectangle) {
        List<Node<Rectangle>> result = new ArrayList<>();

        for (Node<Rectangle> node : getUntouchedLeaves()) {
            if (rectangle.intersects(node.getValue())) {
                result.add(node);
            }
        }

        return result;
    }

    public List<Node<Rectangle>> getAllNodes() {
        Set<Node<Rectangle>> nodes = new HashSet<>();
        Set<Node<Rectangle>> changes = new HashSet<>();
        changes.add(root);

        while (!changes.equals(nodes)) {
            nodes.addAll(changes);
            for (Node<Rectangle> n : nodes) {
                changes.addAll(n.getChildren());
            }
        }

        return new ArrayList<>(nodes);
    }

    public List<Node<Rectangle>> getAllLeaves() {
        return gatherNodes((n)->n.getChildren().isEmpty());
    }

    public List<Node<Rectangle>> getUntouchedLeaves(){
        return gatherNodes((n)->n.getChildren().isEmpty() && !n.isTouched());
    }

    private List<Node<Rectangle>> gatherNodes(Function<Node<Rectangle>, Boolean> isSearched){
        List<Node<Rectangle>> nodes = this.getAllNodes();
        List<Node<Rectangle>> result = new ArrayList<>();

        for (Node<Rectangle> node : nodes) {
            if (isSearched.apply(node)) {
                result.add(node);
            }
        }

        return result;
    }

    public List<Point> getAllPossiblePositions(Rectangle q) {
        List<Node<Rectangle>> nodes = getUntouchedLeaves();
        Set<Point> result = new HashSet<>();

        for (Node<Rectangle> node : nodes) {
            result.addAll(node.getValue().possiblePositions(q));
        }

        return new ArrayList<>(result);

    }

    public Node<Rectangle> getRoot() {
        return root;
    }

    public double getCurrentArea() {
        return this.area;
    }

    private static class Snapshot {
        private final List<Node<Rectangle>> nodes = new ArrayList<>();
        private final List<Point> positions = new ArrayList<>();
        private final Rectangle rectangle;
        public boolean active = false;

        public Snapshot(Rectangle rectangle) {
            this.rectangle = rectangle;
        }

        List<Node<Rectangle>> getNodes() {
            return nodes;
        }

        List<Point> getPositions() {
            return positions;
        }

        public Rectangle getRectangle() {
            return rectangle;
        }
    }

}
