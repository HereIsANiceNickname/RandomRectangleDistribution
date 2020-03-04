package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Pair;
import utils.Quad;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuadTest {

    @Test
    void testDimX(){
        Quad q = new Quad(new Pair<>(0.0,0.0), new Pair<>(10.,10.));
        Assertions.assertEquals(10.0, q.dimensionX);
    }

    @Test
    void testDimY(){
        Quad q = new Quad(new Pair<>(2.0,1.0), new Pair<>(10.,11.));
        Assertions.assertEquals(10.0, q.dimensionY);
    }

    @Test
    void testDimXNegative(){
        Quad q = new Quad(new Pair<>(-10.0,-10.0), new Pair<>(10.,10.));
        Assertions.assertEquals(20.0, q.dimensionX);
    }

    @Test
    void testDimYNegative(){
        Quad q = new Quad(new Pair<>(2.0,-1.0), new Pair<>(10.,11.));
        Assertions.assertEquals(12.0, q.dimensionY);
    }

    @Test
    void testIntersectLeftTopCorner(){
        Quad q1 = new Quad(new Pair<>(5., 5.), new Pair<>(10., 10.));
        Quad q2 = new Quad(new Pair<>(0.0, 0.0), new Pair<>(7., 7.));

        Assertions.assertTrue(q1.intersects(q2));
    }
    @Test
    void testIntersectOnlyTopCorner(){
        Quad q1 = new Quad(new Pair<>(5., 5.), new Pair<>(10., 10.));
        Quad q2 = new Quad(new Pair<>(0.0, 0.0), new Pair<>(12., 7.));

        Assertions.assertTrue(q1.intersects(q2));
    }

    @Test
    void testIntersectRightTopCorner(){
        Quad q1 = new Quad(new Pair<>(5., 5.), new Pair<>(10., 10.));
        Quad q2 = new Quad(new Pair<>(7.0, 0.0), new Pair<>(12., 7.));

        Assertions.assertTrue(q1.intersects(q2));
    }

    @Test
    void testIntersectInner(){
        Quad q1 = new Quad(new Pair<>(5., 5.), new Pair<>(10., 10.));
        Quad q2 = new Quad(new Pair<>(6.0, 6.0), new Pair<>(7., 7.));

        Assertions.assertTrue(q1.intersects(q2));
    }

    @Test
    void testNotIntersectOuter(){
        Quad q1 = new Quad(new Pair<>(5., 5.), new Pair<>(10., 10.));
        Quad q2 = new Quad(new Pair<>(0.0, 0.0), new Pair<>(4., 4.));

        Assertions.assertFalse(q1.intersects(q2));
    }

    @Test
    void testIntersectOuter(){
        Quad q1 = new Quad(new Pair<>(5., 5.), new Pair<>(10., 10.));
        Quad q2 = new Quad(new Pair<>(4.0, 4.0), new Pair<>(11., 11.));

        Assertions.assertTrue(q1.intersects(q2));
    }

    @Test
    void testBarelyFittingQuad(){
        Quad q1 = new Quad(new Pair<>(5., 5.), new Pair<>(10., 10.));
        Quad q2 = new Quad(new Pair<>(5.0, 5.0), new Pair<>(10., 10.));

        Assertions.assertTrue(q1.fitsIn(q2));
    }

    @Test
    void testSafelyFittingQuad(){
        Quad q1 = new Quad(new Pair<>(8.0, 8.0), new Pair<>(10., 10.));
        Quad q2 = new Quad(new Pair<>(5., 5.), new Pair<>(10., 10.));

        Assertions.assertTrue(q1.fitsIn(q2));
    }

    @Test
    void testYSideFittingQuad(){
        Quad q1 = new Quad(new Pair<>(5., 4.), new Pair<>(10., 10.));
        Quad q2 = new Quad(new Pair<>(5.0, 5.0), new Pair<>(10., 10.));

        Assertions.assertFalse(q1.fitsIn(q2));
    }

    @Test
    void testXSideFittingQuad(){
        Quad q1 = new Quad(new Pair<>(4., 5.), new Pair<>(10., 10.));
        Quad q2 = new Quad(new Pair<>(5.0, 5.0), new Pair<>(10., 10.));

        Assertions.assertFalse(q1.fitsIn(q2));
    }
    @Test
    void testPossiblePositionSimple(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(2., 2.));
        Quad q2 = new Quad(new Pair<>(0.0, 0.0), new Pair<>(1.0, 1.0));

        Assertions.assertEquals(4, q1.possiblePositions(q2, 1.0).size());
    }
    @Test
    void testPossiblePositionDelta2(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(2., 2.));
        Quad q2 = new Quad(new Pair<>(0.0, 0.0), new Pair<>(1.0, 1.0));

        Assertions.assertEquals(1, q1.possiblePositions(q2, 2.0).size());
    }
    @Test
    void testPossiblePositionDeltaSmaller2(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(2., 2.));
        Quad q2 = new Quad(new Pair<>(0.0, 0.0), new Pair<>(1.0, 1.0));

        Assertions.assertEquals(1, q1.possiblePositions(q2, 1.3).size());
    }

    @Test
    void testPossiblePositionDelta2Advanced(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(16., 16.));
        Quad q2 = new Quad(new Pair<>(0.0, 0.0), new Pair<>(1.0, 1.0));

        Assertions.assertEquals(64, q1.possiblePositions(q2, 2.0).size());
    }

    @Test
    void testPossiblePositionDelta1AdvancedY2(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(2., 4.));
        Quad q2 = new Quad(new Pair<>(0.0, 0.0), new Pair<>(2.0, 2.0));

        Assertions.assertEquals( 3, q1.possiblePositions(q2, 1.0).size());
    }

    @Test
    void testSplitCenter(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(16., 16.));
        Quad q2 = new Quad(new Pair<>(4.0, 4.0), new Pair<>(8.0, 8.0));
        List<Quad> result =  q1.splitBy(q2);
        Assertions.assertEquals(q1.leftTop, result.get(0).leftTop);
    }

    @Test
    void testSplitWithoutTop(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(16., 16.));
        Quad q2 = new Quad(new Pair<>(4.0, 0.), new Pair<>(8.0, 8.0));
        List<Quad> result =  q1.splitBy(q2);

        Assertions.assertEquals(3, result.size());
    }
    @Test
    void testSplitWithTwo(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(16., 16.));
        Quad q2 = new Quad(new Pair<>(0.0, 0.0), new Pair<>(8.0, 8.0));
        List<Quad> result =  q1.splitBy(q2);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void testSplitOverlapping(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(8., 8.));
        Quad q2 = new Quad(new Pair<>(2.0, -2.0), new Pair<>(4.0, 4.0));
        List<Quad> result =  q1.splitBy(q2);

        Assertions.assertEquals(3, result.size());
    }

    @Test
    void testSplitOverlapping2(){
        Quad q1 = new Quad(new Pair<>(0., 0.), new Pair<>(8., 8.));
        Quad q2 = new Quad(new Pair<>(0.0, -2.0), new Pair<>(8.0, 4.0));
        List<Quad> result =  q1.splitBy(q2);

        Assertions.assertEquals(1, result.size());
    }

    @Test
    void testSpecialCase(){
        Quad q1 = new Quad(new Pair<>(1.0, 0.0), new Pair<>(2.0, 3.0));
        Quad q2 = new Quad(new Pair<>(0.0, 1.0), new Pair<>(3.0, 2.0));

        Assertions.assertTrue(q1.intersects(q2));
    }
    @Test
    void testSpecialCaseReversed(){
        Quad q1 = new Quad(new Pair<>(1.0, 0.0), new Pair<>(2.0, 3.0));
        Quad q2 = new Quad(new Pair<>(0.0, 1.0), new Pair<>(3.0, 2.0));

        Assertions.assertTrue(q2.intersects(q1));
    }





}