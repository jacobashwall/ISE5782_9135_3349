package primitives;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 * @author Yonatan Dahary
 */
class PointTest {

    /**
     * Test method for{@link primitives.Point#subtract(Point)}
     */
    @Test
    void subtract() {
        //============ Equivalence Partitions Tests ==============//
        Point pnt1 = new Point(1,2,3);
        Point pnt2 = new Point(2,3,4);
        Vector vec = new Vector(-1,-1,-1);
        //TC: Test that subtraction is proper. We should get a vector from the two points.
        assertEquals(pnt1.subtract(pnt2),vec,"The subtraction is wrong");

        //============ Boundary Partitions Tests ==============//'
        assertThrows(IllegalArgumentException.class, () -> pnt1.subtract(pnt1),"Subtract fails to throw an exception for equal points");

    }
    /**
     * Test method for{@link primitives.Point#add(Vector)}
     */
    @Test
    void add() {
        //============ Equivalence Partitions Tests ==============//
        Point pnt1 = new Point(1,2,3);
        Point pnt2 = new Point(2,3,4);
        Vector vec = new Vector(-1,-1,-1);
        //TC: Test that addition is proper. We should get a point from a vector and a point.
        assertEquals(pnt2.add(vec),pnt1,"Addition is wrong");

    }
    /**
     * Test method for{@link primitives.Point#distanceSquared(Point)}
     */
    @Test
    void distanceSquared() {
        //============ Equivalence Partitions Tests ==============//
        Point pnt1 = new Point(1,2,3);
        Point pnt2 = new Point(2,3,4);
        //TC: Test that distanceSquared is proper. We should get the squared distance between the two points.
        assertEquals(pnt2.distanceSquared(pnt1),3,"distanceSquared is wrong");
    }
    /**
     * Test method for{@link primitives.Point#distance(Point)}
     */
    @Test
    void distance() {
        //============ Equivalence Partitions Tests ==============//
        Point pnt1 = new Point(1,2,3);
        Point pnt2 = new Point(2,3,4);
        //TC: Test that distance is proper. We should get the distance between the two points.
        assertTrue(Util.isZero(pnt2.distance(pnt1)-sqrt(3)),"distance is wrong");
    }
}