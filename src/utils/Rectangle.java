package utils;

import java.awt.*;
import java.nio.charset.Charset;
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
        return rectangle.width <= other.rectangle.width &&
                rectangle.height <= other.rectangle.height;
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

    public boolean isIn(Rectangle that){
        return this.rectangle.x <=that.rectangle.x &&
                this.rectangle.y <=that.rectangle.y &&
                this.rectangle.width <= that.rectangle.width&&
                this.rectangle.height <= that.rectangle.height;
    }

    private java.awt.Rectangle shrinked(java.awt.Rectangle rectangle) {
        Integer x = positionDecrease(rectangle.x);
        Integer y = positionDecrease(rectangle.y);
        return new java.awt.Rectangle(x, y, rectangle.width-2, rectangle.height-2);

    }

    private Integer positionDecrease(int x) {
        return x+1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftTop, rightBottom);
    }

    @Override
    public String toString() {
        return "Quad{  pos="+ leftTop.toString() + ", " +
                "DimX=" + dimensionX + ", " +
                "DimY=" + dimensionY + " }";
    }

    public List<Rectangle> splitBy(Rectangle splitter) {
        splitter = splitter.fitTo(this);
        List<Rectangle> splits = new ArrayList<>();
        //top
        Point p1s = this.leftTop;
        Point p1e = new Point(this.right, splitter.top);
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
        Point p4s = new Point(this.left, splitter.bottom);
        Point p4e = this.rightBottom;
        Rectangle bottom = new Rectangle(p4s, p4e);
        tryToAddQuad(splitter, bottom, splits);

        return splits;

    }

    public Rectangle fitTo(Rectangle that) {
        Point start = new Point(Math.max(this.left, that.left ),
                Math.max(this.top, that.top));
        Point end = new Point(Math.min(this.right, that.right ),
                Math.min(this.bottom, that.bottom));
        return new Rectangle(start, end);
    }

    private void tryToAddQuad(Rectangle splitter, Rectangle toAdd, List<Rectangle> result){
        if(toAdd.area > 0.0){
            result.add(toAdd);
        }
    }





    public Rectangle onPosition(Point position) {
        return new Rectangle(position, new Point(position.x+dimensionX, position.y+dimensionY));
    }

}
