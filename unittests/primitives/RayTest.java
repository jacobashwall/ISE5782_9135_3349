package primitives;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    /**
     * Test method for
     * {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void findClosestPoint() {
        Ray ray = new Ray (new Point(1,0,0),new Vector(1,1,1));
        Point pnt1 = new Point(2,1,1);
        Point pnt2 = new Point(3,2,2);
        Point pnt3 = new Point(4,3,3);
        //============ Equivalence Partitions Tests ==============//
        //EP1: Closest point is inside the list
        assertEquals(pnt1,ray.findClosestPoint(List.of(pnt3,pnt1,pnt2)),"EP1: Wrong point!");

        //============ Boundary Value Tests ==============//
        //BVA1: Closest point is the first in the list
        assertEquals(pnt1,ray.findClosestPoint(List.of(pnt1,pnt3,pnt2)),"BVA1: Wrong point!");
        //BVA2: Closest point is the last in the list
        assertEquals(pnt1,ray.findClosestPoint(List.of(pnt2,pnt3,pnt1)),"BVA2: Wrong point!");
        //BVA3: Empty list
        assertNull(ray.findClosestPoint(Collections.EMPTY_LIST),"BVA3: Fails to return null!");
        //BVA4: Null list
        assertNull(ray.findClosestPoint(null),"BVA4: Fails to return null!");

    }
}