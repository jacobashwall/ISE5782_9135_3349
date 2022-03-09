package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    /**
     * Test method for{@link geometries.Cylinder#getNormal(Point)}
     */
    @Test
    void getNormal() {
        Vector vec = new Vector(1,0,0);
        Point pnt  = new Point(0,0,1);
        Ray ray = new Ray(pnt,vec);
        Tube tube = new Tube(ray,1);
        //============ Equivalence Partitions Tests ==============//
        //TC: Testing that getNormal returns the correct vector in the single equivalence partition

        //EP: casing
        assertEquals(new Vector(0,0,-1),tube.getNormal(new Point(1,0,0)),"getNormal is not working - EP1:casing");


        //============ Boundary Tests ==============//
        //TC: Testing the boundary case where the point creates an orthogonal vector to the ray direction vector.

        //BVA: First base
        assertEquals(new Vector(-1,0,0),tube.getNormal(new Point(0,0,0)),"getNormal is not working - BVA");

    }
}