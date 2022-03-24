package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    /**
     * Test method for{@link geometries.Cylinder#getNormal(Point)}
     */
    @Test
    void getNormal() {
        Vector vec = new Vector(1,0,0);
        Point pnt  = new Point(0,0,1);
        Ray ray = new Ray(pnt,vec);
        Cylinder cyln = new Cylinder(ray,1, 10);
        //============ Equivalence Partitions Tests ==============//
        //TC: Testing that getNormal returns the correct vector in different equivalence partitions

        //EP1: casing
        assertEquals(new Vector(0,0,-1),cyln.getNormal(new Point(1,0,0)),"getNormal is not working - EP1:casing");

        //EP2: First base
        assertEquals(new Vector(1,0,0),cyln.getNormal(new Point(0,0.5,1)),"getNormal is not working - EP2: First base");

        //EP3: Second base
        assertEquals(new Vector(1,0,0),cyln.getNormal(new Point(10,0.5,1)),"getNormal is not working - EP3: Second base");

        //============ Boundary Tests ==============//
        //TC: Testing the boundary case where the point is on the perimeter of on of the bases. The normal should be calculated as the other base points.

        //BVA1: First base
        assertTrue((new Vector(1,0,0).equals(cyln.getNormal(new Point(0,0,1)))||new Vector(-1,0,0).equals(cyln.getNormal(new Point(0,0,1)))),"getNormal is not working - BVA1: First base");

        //BVA2: Second base
        assertTrue((new Vector(1,0,0).equals(cyln.getNormal(new Point(10,0,1)))||new Vector(-1,0,0).equals(cyln.getNormal(new Point(10,0,1)))),"getNormal is not working - BVA2: Second base");

        //BVA3: First base
        assertTrue((new Vector(1,0,0).equals(cyln.getNormal(new Point(0,0,2)))||new Vector(-1,0,0).equals(cyln.getNormal(new Point(0,0,2)))),"getNormal is not working - BVA3: First base");

        //BVA4: Second base
        assertTrue((new Vector(1,0,0).equals(cyln.getNormal(new Point(10,0,2)))||new Vector(-1,0,0).equals(cyln.getNormal(new Point(10,0,2)))),"getNormal is not working - BVA4: Second base");


    }

}