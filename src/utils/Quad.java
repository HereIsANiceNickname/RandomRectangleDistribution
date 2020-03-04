package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Quad {
    private final Pair<Double, Double> ZERO_PAIR = new Pair<>(0., 0.);
    public final Double top;
    public final Double bottom;
    public final Double right;
    public final Double left;

    public final Double dimensionX;
    public final Double dimensionY;

    public final Double area;

    public final Pair<Double, Double> rightTop;
    public final Pair<Double, Double> leftTop;
    public final Pair<Double, Double> rightBottom;
    public final Pair<Double, Double> leftBottom;

    public Quad(Pair<Double, Double> topLeft, Pair<Double, Double> bottomRight) {
        this.left = topLeft._1;
        this.right = bottomRight._1;
        this.top = topLeft._2;
        this.bottom = bottomRight._2;

        this.rightTop = new Pair<>(right, top);
        this.leftTop = new Pair<>(left, top);
        this.rightBottom = new Pair<>(right, bottom);
        this.leftBottom = new Pair<>(left, bottom);

        dimensionX = right - left;
        dimensionY = bottom - top;

        area = dimensionX * dimensionY;
    }

    public boolean intersects(Quad other) {


        return this.intersectsSpecialCase(other) ||
                other.intersectsSpecialCase(this) ||
                other.isAnyPointIn(this) ||
                this.isAnyPointIn(other) ;

    }

    private boolean intersectsSpecialCase(Quad other){
        return inBetween(other.left, other.right, this.left) &&
                inBetween(other.left, other.right, this.right) &&
                inBetween(this.top, this.bottom, other.top) &&
                inBetween(this.top, this.bottom, other.bottom);

    }

    public boolean isAnyPointIn(Quad other) {
        return other.isPointIn(this.leftBottom) ||
                other.isPointIn(this.leftTop) ||
                other.isPointIn(this.rightTop) ||
                other.isPointIn(this.rightTop);
    }

    private boolean isPointIn(Pair<Double, Double> point) {
        return this.inBetween(this.left, this.right, point._1) &&
                this.inBetween(this.top, this.bottom, point._2);
    }

    private boolean inBetween(Double left, Double right, Double x) {
        return left <= x && x <= right;
    }

    public boolean fitsIn(Quad other) {
        return this.dimensionX <= other.dimensionX && this.dimensionY <= other.dimensionY;
    }

    public List<Pair<Double, Double>> possiblePositions(Quad toPlace, Double delta) {
        List<Pair<Double, Double>> positions = new ArrayList<>();
        if(this.area < toPlace.area){
            return positions;
        }
        int xBorder = (int) ((this.dimensionX - toPlace.dimensionX) / delta);
        int yBorder = (int) ((this.dimensionY - toPlace.dimensionY) / delta);

        for (int x = 0; x <= xBorder; x++) {
            for (int y = 0; y <= yBorder; y++) {
                Double xPos =  this.left + x * delta;
                Double yPos = this.top + y * delta;
                if(xPos == 0.0 && yPos == 1.0){
                    System.out.println("QuadThat = "+ toPlace);
                    System.out.println("QuadThis = "+ this);
                    System.out.println("Delta = "+ delta);
                    System.out.println("Left ="+ this.left+ " X = "+x);
                    System.out.println("Top ="+ this.top+ " Y = "+y);
                }
                positions.add(new Pair<>(xPos, yPos));
            }
        }

        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quad quad = (Quad) o;
        return Objects.equals(leftTop, quad.leftTop) &&
                Objects.equals(rightBottom, quad.rightBottom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftTop, rightBottom);
    }

    @Override
    public String toString() {
        return "Quad{  p1="+ leftTop.toString()+", p2="
                + rightTop.toString()+ ", p3="
                + rightBottom.toString()+ ", p4="
                + leftBottom.toString()+ "}";
    }

    public List<Quad> splitBy(Quad splitter) {
        List<Quad> splits = new ArrayList<>();
        //top
        Pair<Double, Double> p1s = this.leftTop;
        Pair<Double, Double> p1e = new Pair<>(splitter.right, splitter.top);
        Quad top = new Quad(p1s, p1e);
        tryToAddQuad(splitter, top, splits);
        //Left
        Pair<Double, Double> p2s = this.leftTop;
        Pair<Double, Double> p2e = new Pair<>(splitter.left, this.bottom);
        Quad left = new Quad(p2s, p2e);
        tryToAddQuad(splitter, left, splits);
        //Right
        Pair<Double, Double> p3s = new Pair<>(splitter.right, this.top);
        Pair<Double, Double> p3e = this.rightBottom;
        Quad right = new Quad(p3s, p3e);
        tryToAddQuad(splitter, right, splits);
        //Bottom
        Pair<Double, Double> p4s = new Pair<>(splitter.left, splitter.bottom);
        Pair<Double, Double> p4e = this.rightBottom;
        Quad bottom = new Quad(p4s, p4e);
        tryToAddQuad(splitter, bottom, splits);

        return splits;

    }
    private void tryToAddQuad(Quad splitter, Quad toAdd, List<Quad> result){
        if(toAdd.area > 0.0 && toAdd.isAllPointsIn(this)){
            result.add(toAdd);
        }
    }

    private boolean isAllPointsIn(Quad other) {
        return other.isPointIn(this.leftBottom) &&
                other.isPointIn(this.leftTop) &&
                other.isPointIn(this.rightTop) &&
                other.isPointIn(this.rightTop);
    }

    public Quad onPosition(Pair<Double, Double> position) {
        return new Quad(position, new Pair<>(position._1+dimensionX, position._2+dimensionY));
    }
}
