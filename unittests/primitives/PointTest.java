package primitives;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 */
class PointTest {

    /**
     * Test method for{@link primitives.Point#subtract(Point)}
     */
    @Test
    void subtract() {
        //============ Equivalence Partitions Tests ==============//
        Point pnt1 = new Point(1, 2, 3);
        Point pnt2 = new Point(2, 3, 4);
        Vector vec = new Vector(-1, -1, -1);
        //TC1: Test that subtraction is proper. We should get a vector from the two points.
        assertEquals(pnt1.subtract(pnt2), vec, "TC1: The subtraction is wrong");

        //============ Boundary Partitions Tests ==============//
        //TC2: Subtracting a point with itself
        assertThrows(IllegalArgumentException.class, () -> pnt1.subtract(pnt1), "TC2: Subtract fails to throw an exception for equal points");

    }

    /**
     * Test method for{@link primitives.Point#add(Vector)}
     */
    @Test
    void add() {
        //============ Equivalence Partitions Tests ==============//
        Point pnt1 = new Point(1, 2, 3);
        Point pnt2 = new Point(2, 3, 4);
        Vector vec = new Vector(-1, -1, -1);
        //TC1: Test that addition is proper. We should get a point from a vector and a point.
        assertEquals(pnt2.add(vec), pnt1, "TC1: Addition is wrong");

        //============ Boundary Partitions Tests ==============//
        //TC2: Adding a point the opposite vector
        assertEquals(pnt1.add(new Vector(pnt1.xyz).scale(-1)), new Point(0, 0, 0), "TC2: addition with opposite vector gives 0");


    }

    /**
     * Test method for{@link primitives.Point#distanceSquared(Point)}
     */
    @Test
    void distanceSquared() {
        //============ Equivalence Partitions Tests ==============//
        Point pnt1 = new Point(1, 2, 3);
        Point pnt2 = new Point(2, 3, 4);
        //TC1: Test that distanceSquared is proper. We should get the squared distance between the two points.
        assertEquals(pnt2.distanceSquared(pnt1), 3, "TC1: distanceSquared is wrong");

        //============ Boundary Partitions Tests ==============//
        //TC2: distance of point from itself
        assertEquals(pnt1.distanceSquared(pnt1), 0, "TC2: distance with itself return 0");

    }

    /**
     * Test method for{@link primitives.Point#distance(Point)}
     */
    @Test
    void distance() {
        //============ Equivalence Partitions Tests ==============//
        Point pnt1 = new Point(1, 2, 3);
        Point pnt2 = new Point(2, 3, 4);
        //TC: Test that distance is proper. We should get the distance between the two points.
        assertEquals(pnt2.distance(pnt1), sqrt(3), 0.000001, "TC1: distanceSquared is wrong");

        //============ Boundary Partitions Tests ==============//
        //TC2: distance of point from itself
        assertEquals(pnt1.distance(pnt1), 0, "TC2: distance with itself return 0");

    }
}