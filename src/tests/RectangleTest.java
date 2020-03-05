package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Pair;
import utils.Rectangle;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class RectangleTest {

    @Test
    void testDimX(){
        Rectangle q = new Rectangle(new Point(0,0), new Point(10,10));
        Assertions.assertEquals(10, q.dimensionX);
    }

    @Test
    void testDimY(){
        Rectangle q = new Rectangle(new Point(2,1), new Point(10,11));
        Assertions.assertEquals(10, q.dimensionY);
    }

    @Test
    void testDimXNegative(){
        Rectangle q = new Rectangle(new Point(-10,-10), new Point(10,10));
        Assertions.assertEquals(20, q.dimensionX);
    }

    @Test
    void testDimYNegative(){
        Rectangle q = new Rectangle(new Point(2,-1), new Point(10,11));
        Assertions.assertEquals(12, q.dimensionY);
    }

    @Test
    void testIntersectSelf(){
        Rectangle q1 = new Rectangle(new Point(5, 5), new Point(10, 10));
        Assertions.assertTrue(q1.intersects(q1));
    }

    @Test
    void testIntersectLeftTopCorner() {
        Rectangle q1 = new Rectangle(new Point(5, 5), new Point(10, 10));
        Rectangle q2 = new Rectangle(new Point(0, 0), new Point(7, 7));

        Assertions.assertTrue(q1.intersects(q2));
    }
    @Test
    void testIntersectLeftSide() {
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(4, 2));
        Rectangle q2 = new Rectangle(new Point(2, 0), new Point(4, 2));

        Assertions.assertTrue(q1.intersects(q2));
    }
    @Test
    void testIntersectOnlyTopCorner(){
        Rectangle q1 = new Rectangle(new Point(5, 5), new Point(10, 10));
        Rectangle q2 = new Rectangle(new Point(0, 0), new Point(12, 7));

        Assertions.assertTrue(q1.intersects(q2));
    }

    @Test
    void testIntersectRightTopCorner(){
        Rectangle q1 = new Rectangle(new Point(5, 5), new Point(10, 10));
        Rectangle q2 = new Rectangle(new Point(7, 0), new Point(12, 7));

        Assertions.assertTrue(q1.intersects(q2));
    }

    @Test
    void testIntersectInner(){
        Rectangle q1 = new Rectangle(new Point(5, 5), new Point(10, 10));
        Rectangle q2 = new Rectangle(new Point(6, 6), new Point(7, 7));

        Assertions.assertTrue(q1.intersects(q2));
    }

    @Test
    void testNotIntersectOuter(){
        Rectangle q1 = new Rectangle(new Point(5, 5), new Point(10, 10));
        Rectangle q2 = new Rectangle(new Point(0, 0), new Point(4, 4));

        Assertions.assertFalse(q1.intersects(q2));
    }

    @Test
    void testIntersectOuter(){
        Rectangle q1 = new Rectangle(new Point(5, 5), new Point(10, 10));
        Rectangle q2 = new Rectangle(new Point(4, 4), new Point(11, 11));

        Assertions.assertTrue(q1.intersects(q2));
    }

    @Test
    void testBarelyFittingQuad(){
        Rectangle q1 = new Rectangle(new Point(5, 5), new Point(10, 10));
        Rectangle q2 = new Rectangle(new Point(5, 5), new Point(10, 10));

        Assertions.assertTrue(q1.fitsIn(q2));
    }

    @Test
    void testSafelyFittingQuad(){
        Rectangle q1 = new Rectangle(new Point(8, 8), new Point(10, 10));
        Rectangle q2 = new Rectangle(new Point(5, 5), new Point(10, 10));

        Assertions.assertTrue(q1.fitsIn(q2));
    }

    @Test
    void testYSideFittingQuad(){
        Rectangle q1 = new Rectangle(new Point(5, 4), new Point(10, 10));
        Rectangle q2 = new Rectangle(new Point(5, 5), new Point(10, 10));

        Assertions.assertFalse(q1.fitsIn(q2));
    }

    @Test
    void testXSideFittingQuad(){
        Rectangle q1 = new Rectangle(new Point(4, 5), new Point(10, 10));
        Rectangle q2 = new Rectangle(new Point(5, 5), new Point(10, 10));

        Assertions.assertFalse(q1.fitsIn(q2));
    }
    @Test
    void testPossiblePositionSimple(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(2, 2));
        Rectangle q2 = new Rectangle(new Point(0, 0), new Point(1, 1));

        Assertions.assertEquals(4, q1.possiblePositions(q2).size());
    }


    @Test
    void testPossiblePositionDelta1AdvancedY2(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(2, 4));
        Rectangle q2 = new Rectangle(new Point(0, 0), new Point(2, 2));

        Assertions.assertEquals( 3, q1.possiblePositions(q2).size());
    }

    @Test
    void testSplitCenter(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(16, 16));
        Rectangle q2 = new Rectangle(new Point(4, 4), new Point(8, 8));
        List<Rectangle> result =  q1.splitBy(q2);
        Assertions.assertEquals(q1.leftTop, result.get(0).leftTop);
    }

    @Test
    void testSplitWithoutTop(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(16, 16));
        Rectangle q2 = new Rectangle(new Point(4, 0), new Point(8, 8));
        List<Rectangle> result =  q1.splitBy(q2);

        Assertions.assertEquals(3, result.size());
    }
    @Test
    void testSplitWithTwo(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(16, 16));
        Rectangle q2 = new Rectangle(new Point(0, 0), new Point(8, 8));
        List<Rectangle> result =  q1.splitBy(q2);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void testSplitOverlapping(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(8, 8));
        Rectangle q2 = new Rectangle(new Point(2, -2), new Point(4, 4));
        List<Rectangle> result =  q1.splitBy(q2);

        Assertions.assertEquals(3, result.size());
    }

    @Test
    void testSplitOverlapping1_1(){
        Rectangle q1 = new Rectangle(new Point(0, 1), new Point(3, 2));
        Rectangle q2 = new Rectangle(new Point(1, 0), new Point(2, 3));

        List<Rectangle> result =  q1.splitBy(q2);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void testFits(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(8, 8));
        Rectangle q2 = new Rectangle(new Point(0, 0), new Point(8, 8));


        Assertions.assertTrue(q1.fitsIn(q2));
    }
    @Test
    void testFits2(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(8, 8));
        Rectangle q2 = new Rectangle(new Point(1, 1), new Point(7, 7));


        Assertions.assertTrue(q2.fitsIn(q1));
    }
    @Test
    void testFits3(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(8, 8));
        Rectangle q2 = new Rectangle(new Point(0, 0), new Point(7, 7));


        Assertions.assertTrue(q2.fitsIn(q1));
    }

    @Test
    void fitsTo(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(8, 8));
        Rectangle q2 = new Rectangle(new Point(-2, -2), new Point(4, 4));
        Rectangle expected = new Rectangle(new Point(0, 0), new Point(4, 4));
        Assertions.assertEquals(expected, q2.fitTo(q1));
    }

    @Test
    void fitsTo2(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(8, 8));
        Rectangle q2 = new Rectangle(new Point(-4, -4), new Point(14, 14));

        Assertions.assertEquals(q1, q2.fitTo(q1));
    }
    @Test
    void testSplitOverlapping2(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(8, 8));
        Rectangle q2 = new Rectangle(new Point(0, -2), new Point(8, 4));
        List<Rectangle> result =  q1.splitBy(q2);

        Assertions.assertEquals(1, result.size());
    }

    @Test
    void testConcreteOverlap(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(3, 3));
        Rectangle q2 = new Rectangle(new Point(1, -2), new Point(2, 2));

        Rectangle r1 = new Rectangle(new Point(0, 0), new Point(1, 3));
        Rectangle r2 = new Rectangle(new Point(2, 0), new Point(3, 3));
        Rectangle r3 = new Rectangle(new Point(0, 2), new Point(3, 3));
        Set<Rectangle> result =  new HashSet<>(q1.splitBy(q2));
        Set<Rectangle> expected = new HashSet<>();
        expected.add(r1);
        expected.add(r2);
        expected.add(r3);


        Assertions.assertEquals(expected, result);
    }

    @Test
    void testSpecialCase(){
        Rectangle q1 = new Rectangle(new Point(1, 0), new Point(2, 3));
        Rectangle q2 = new Rectangle(new Point(0, 1), new Point(3, 2));

        Assertions.assertTrue(q1.intersects(q2));
    }
    @Test
    void testSpecialCaseReversed(){
        Rectangle q1 = new Rectangle(new Point(1, 0), new Point(2, 3));
        Rectangle q2 = new Rectangle(new Point(0, 1), new Point(3, 2));

        Assertions.assertTrue(q2.intersects(q1));
    }

    @Test
    void testRectangleSidebySide(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(2, 2));
        Rectangle q2 = new Rectangle(new Point(2, 0), new Point(4, 2));

        Assertions.assertFalse(q2.intersects(q1));
    }

    @Test
    void testRectangleDontFits(){
        Rectangle q1 = new Rectangle(new Point(0, 0), new Point(2, 2));
        Rectangle q2 = new Rectangle(new Point(-1, -1), new Point(3, 3));

        Assertions.assertFalse(q2.fitsIn(q1));
    }






}