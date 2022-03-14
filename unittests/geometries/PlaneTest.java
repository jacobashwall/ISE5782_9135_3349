package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    void Plane() {
        Point pnt1 = new Point(0,0,0);
        Point pnt2 = new Point(1,0,0);
        Point pnt3 = new Point(0,1,0);
        //============ Equivalence Partitions Tests ==============//
        //TC: Testing that the three point ctor creates the correct plane

        assertEquals(new Plane(pnt1,pnt2,pnt3), new Plane(pnt1, new Vector(0,0,-1)),"Error");

        //============ Boundary Tests ==============//
        //TC: Testing that the three point ctor creates the correct plane
        assertThrows(IllegalArgumentException.class, () -> new Plane(pnt1,pnt1,pnt2),"Plane 3 points ctor fails to throw an exception for equal points");
        assertThrows(IllegalArgumentException.class, () -> new Plane(pnt1,pnt1,new Point(2,0,0)),"Plane 3 points ctor fails to throw an exception for colinear points");

    }



    /**
     * Test method for{@link geometries.Plane#getNormal(Point)}
     */
    @Test
    void getNormal() {
        //============ Equivalence Partitions Tests ==============//
        //TC: Testing that getNormal returns the correct vector
        Plane pln = new Plane(new Point(0,0,0),new Point(0,1,0),new Point(1,0,0));
        assertTrue(new Vector(0,0,1).equals(pln.getNormal(new Point(0,0,0))) || new Vector(0,0,-1).equals(pln.getNormal(new Point(0,0,0))),"Wrong normal to the plane");
    }
}