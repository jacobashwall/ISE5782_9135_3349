package primitives;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing rays
 */
class RayTest {

    /**
     * Test method for
     * {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void findClosestPoint() {
        Ray ray = new Ray(new Point(1, 0, 0), new Vector(1, 1, 1));
        Point pnt1 = new Point(2, 1, 1);
        Point pnt2 = new Point(3, 2, 2);
        Point pnt3 = new Point(4, 3, 3);
        //============ Equivalence Partitions Tests ==============//
        //EP1: Closest point is inside the list
        assertEquals(pnt1, ray.findClosestPoint(List.of(pnt3, pnt1, pnt2)), "EP1: Wrong point!");

        //============ Boundary Value Tests ==============//
        //BVA1: Closest point is the first in the list
        assertEquals(pnt1, ray.findClosestPoint(List.of(pnt1, pnt3, pnt2)), "BVA1: Wrong point!");

        //BVA2: Closest point is the last in the list
        assertEquals(pnt1, ray.findClosestPoint(List.of(pnt2, pnt3, pnt1)), "BVA2: Wrong point!");

        //BVA3: Empty list
        assertNull(ray.findClosestPoint(Collections.EMPTY_LIST), "BVA3: Fails to return null!");

        //BVA4: Null list
        assertNull(ray.findClosestPoint(null), "BVA4: Fails to return null!");

    }

    /**
     * Test method for
     * {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void getPoint() {
        Ray ray = new Ray(Point.ZERO, new Vector(0, 0, 1));
        //============ Equivalence Partitions Tests ==============//
        //EP 1: the scale is positive (the point is on the ray)
        double t1 = 2;
        assertEquals(new Point(0, 0, 2), ray.getPoint(t1), "EP1: Failed to get point with positive scale");

        //EP 2: the scale is negative (the point is on the other way of the ray)
        double t2 = -2;
        assertEquals(new Point(0, 0, -2), ray.getPoint(t2), "EP2: Failed to get point with negative scale");

        //============ Boundary Value Tests ==============//
        //BVA1: the scale is zero (the point is on the start of the ray)
        double t3 = 0;
        assertEquals(ray.getP0(), ray.getPoint(t3), "BVA1: Failed to get point with zero scale");


    }
}