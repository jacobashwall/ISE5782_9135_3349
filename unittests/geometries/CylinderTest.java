package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing cylinders
 */
class CylinderTest {

    /**
     * Test method for{@link geometries.Cylinder#getNormal(Point)}
     */
    @Test
    void getNormal() {
        Vector vec = new Vector(1, 0, 0);
        Point pnt = new Point(0, 0, 1);
        Ray ray = new Ray(pnt, vec);
        Cylinder cylinder = new Cylinder(ray, 1, 10);
        //============ Equivalence Partitions Tests ==============//
        //Testing that getNormal returns the correct vector in different equivalence partitions

        //EP1: casing
        assertEquals(new Vector(0, 0, -1), cylinder.getNormal(new Point(1, 0, 0)), "getNormal is not working - EP1:casing");

        //EP2: First base
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(0, 0.5, 1)), "getNormal is not working - EP2: First base");

        //EP3: Second base
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(10, 0.5, 1)), "getNormal is not working - EP3: Second base");

        //============ Boundary Tests ==============//
        //Testing the boundary case where the point is on the perimeter or the center of the bases. The normal should be calculated as the other base points.

        //BVA1: First base - center
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(0, 0, 1)),"getNormal is not working - BVA1: First base");

        //BVA2: Second base - center
        assertEquals(new Vector(1, 0, 0),cylinder.getNormal(new Point(10, 0, 1)), "getNormal is not working - BVA2: Second base");

        //BVA3: First base - perimeter
        assertEquals(new Vector(1, 0, 0),cylinder.getNormal(new Point(0, 0, 2)), "getNormal is not working - BVA3: First base");

        //BVA4: Second base - perimeter
        assertEquals(new Vector(1, 0, 0),cylinder.getNormal(new Point(10, 0, 2)), "getNormal is not working - BVA4: Second base");


    }

}