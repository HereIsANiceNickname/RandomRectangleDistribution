package utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Rectangle {
    java.awt.Rectangle rectangle;
    private final Point ZERO_POINT = new Point(0,0);
    public final Integer top;
    public final Integer bottom;
    public final Integer right;
    public final Integer left;

    public final Integer dimensionX;
    public final Integer dimensionY;

    public final Integer area;

    public final Point rightTop;
    public final Point leftTop;
    public final Point rightBottom;
    public final Point leftBottom;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.left = topLeft.x;
        this.right = bottomRight.x;
        this.top = topLeft.y;
        this.bottom = bottomRight.y;

        this.rightTop = new Point(right, top);
        this.leftTop = new Point(left, top);
        this.rightBottom = new Point(right, bottom);
        this.leftBottom = new Point(left, bottom);

        dimensionX = right - left;
        dimensionY = bottom - top;

        this.rectangle = new java.awt.Rectangle(topLeft.x, topLeft.y, dimensionX, dimensionY);
        area = dimensionX * dimensionY;
    }

    public boolean intersects(Rectangle other) {
        return this.rectangle.intersects(other.rectangle);
    }


    public boolean fitsIn(Rectangle other) {
        return this.rectangle.contains(other.rectangle);
    }

    public List<Point> possiblePositions(Rectangle toPlace) {
        List<Point> positions = new ArrayList<>();
        if(this.area < toPlace.area){
            return positions;
        }
        int xBorder = this.dimensionX - toPlace.dimensionX;
        int yBorder = this.dimensionY - toPlace.dimensionY;

        for (int x = 0; x <= xBorder; x++) {
            for (int y = 0; y <= yBorder; y++) {
                Integer xPos =  this.left + x;
                Integer yPos = this.top + y;
                if(xPos == 0.0 && yPos == 1.0){
                    System.out.println("QuadThat = "+ toPlace);
                    System.out.println("QuadThis = "+ this);
                    System.out.println("Left ="+ this.left+ " X = "+x);
                    System.out.println("Top ="+ this.top+ " Y = "+y);
                }
                positions.add(new Point(xPos, yPos));
            }
        }

        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Objects.equals(leftTop, rectangle.leftTop) &&
                Objects.equals(rightBottom, rectangle.rightBottom);
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

    public List<Rectangle> splitBy(Rectangle splitter) {
        List<Rectangle> splits = new ArrayList<>();
        //top
        Point p1s = this.leftTop;
        Point p1e = new Point(splitter.right, splitter.top);
        Rectangle top = new Rectangle(p1s, p1e);
        tryToAddQuad(splitter, top, splits);
        //Left
        Point p2s = this.leftTop;
        Point p2e = new Point(splitter.left, this.bottom);
        Rectangle left = new Rectangle(p2s, p2e);
        tryToAddQuad(splitter, left, splits);
        //Right
        Point p3s = new Point(splitter.right, this.top);
        Point p3e = this.rightBottom;
        Rectangle right = new Rectangle(p3s, p3e);
        tryToAddQuad(splitter, right, splits);
        //Bottom
        Point p4s = new Point(splitter.left, splitter.bottom);
        Point p4e = this.rightBottom;
        Rectangle bottom = new Rectangle(p4s, p4e);
        tryToAddQuad(splitter, bottom, splits);

        return splits;

    }
    private void tryToAddQuad(Rectangle splitter, Rectangle toAdd, List<Rectangle> result){
        if(toAdd.area > 0.0 && this.rectangle.contains(splitter.rectangle)){
            result.add(toAdd);
        }
    }


    public Rectangle onPosition(Point position) {
        return new Rectangle(position, new Point(position.x+dimensionX, position.y+dimensionY));
    }
}
