package utils;

import java.util.*;
import java.util.function.Function;

public class QuadMaskingTree {
    private Node root = null;
    private Double area = 0.0;
    private final Random r = new Random();


    public QuadMaskingTree(Quad rootQuad) {
        root = new Node(rootQuad);
        area = rootQuad.area;
    }

    public List<Quad> generateFittingQuads(List<Quad> quads) {
        List<Quad> results = new ArrayList<>();
        quads.sort((Quad o1, Quad o2) -> r.nextInt(3) - 1);

        Stack<Snapshot> backtracks = new Stack<>();
        backtracks.push(createSnapshot(quads.get(0), backtracks, -1));

        for (int i = 0; i < quads.size(); ) {
            Quad quad = quads.get(i);
            Snapshot s = backtracks.peek();

            if (fits(quad, s)) {
                processQuad(s);
                backtracks.push(s);
                s = createSnapshot(quads.get(i + 1), backtracks, i);
                area -= quad.area;
            } else {
                fallback(s);
                if (s.getPositions().isEmpty()) {
                    backtracks.pop();
                }
                area += quad.area;
            }
            i = backtracks.size();
        }

        for (Snapshot s : backtracks) {
            results.add(s.quad);
        }

        return results;
    }


    private void fallback(Snapshot snap) {
        for (Node node : snap.getNodes()) {
            node.remove();
        }

    }


    private Quad processQuad(Snapshot snap) {
        List<Pair<Double, Double>> currentPositions = snap.getPositions();
        Pair<Double, Double> position = currentPositions.get(r.nextInt(currentPositions.size()));
        currentPositions.remove(position);

        Quad movedQuad = snap.quad.onPosition(position);
        List<Node> alteredNodes = splitNode(movedQuad);
        snap.getNodes().addAll(alteredNodes);
        snap.active = false;

        return movedQuad;
    }

    private Snapshot createSnapshot(Quad quad, Stack<Snapshot> backtracks, int i) {

        if (i + 1 == backtracks.size() && !backtracks.empty()) {
            return backtracks.pop();
        } else {
            Snapshot snap = new Snapshot(quad);
            snap.getPositions().addAll(getAllPossiblePositions(quad));

            return snap;
        }
    }

    private boolean fits(Quad quad, Snapshot snap) {
        return quad.area <= area && snap.getPositions().size() > 0;
    }

    public List<Node> splitNode(Pair<Double, Double> pos, Quad q) {
        Quad quad = new Quad(pos, new Pair<>(pos._1 + q.dimensionX, pos._2 + q.dimensionY));
        return splitNode(quad);
    }

    public List<Node> splitNode(Quad quad) {
        List<Node> nodes = getIntersectingNodes(quad);
        List<Node> alteredNodes = new ArrayList<>();
        for (Node node : nodes) {
            alteredNodes.add(node);
            node.touch();
            node.addChildren(node.getValue().splitBy(quad));
        }

        return alteredNodes;
    }

    private List<Node> getIntersectingNodes(Quad quad) {
        List<Node> result = new ArrayList<>();

        for (Node node : getUntouchedLeaves()) {
            if (quad.intersects(node.getValue())) {
                result.add(node);
            }
        }

        return result;
    }

    public List<Node> getAllNodes() {
        Set<Node> nodes = new HashSet<>();
        Set<Node> changes = new HashSet<>();
        changes.add(root);

        while (!changes.equals(nodes)) {
            nodes.addAll(changes);
            for (Node n : nodes) {
                changes.addAll(n.getChildren());
            }
        }

        return new ArrayList<>(nodes);
    }

    public List<Node> getAllLeaves() {
        return gatherNodes((n)->n.getChildren().isEmpty());
    }

    public List<Node> getUntouchedLeaves(){
        return gatherNodes((n)->n.getChildren().isEmpty() && !n.isTouched());
    }

    private List<Node> gatherNodes(Function<Node, Boolean> isSearched){
        List<Node> nodes = this.getAllNodes();
        List<Node> result = new ArrayList<>();

        for (Node node : nodes) {
            if (isSearched.apply(node)) {
                result.add(node);
            }
        }

        return result;
    }

    public List<Pair<Double, Double>> getAllPossiblePositions(Quad q) {
        List<Node> nodes = getUntouchedLeaves();
        Set<Pair<Double, Double>> result = new HashSet<>();

        for (Node node : nodes) {
            result.addAll(node.getValue().possiblePositions(q, 1.0));
        }

        return new ArrayList<>(result);

    }

    public Node getRoot() {
        return root;
    }

    public double getCurrentArea() {
        return this.area;
    }

    private static class Snapshot {
        private final List<Node> nodes = new ArrayList<>();
        private final List<Pair<Double, Double>> positions = new ArrayList<>();
        private final Quad quad;
        public boolean active = false;

        public Snapshot(Quad quad) {
            this.quad = quad;
        }

        List<Node> getNodes() {
            return nodes;
        }

        List<Pair<Double, Double>> getPositions() {
            return positions;
        }

        public Quad getQuad() {
            return quad;
        }
    }

}
