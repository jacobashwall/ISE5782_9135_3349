package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    /**
     * Test method for{@link geometries.Plane#getNormal(Point)}
     */
    @Test
    void getNormal() {
        //============ Equivalence Partitions Tests ==============//
        //TC: Testing that getNormal returns the right vector
        Plane pln = new Plane(new Point(0,0,0),new Point(0,1,0),new Point(1,0,0));
        assertTrue(new Vector(0,0,1).equals(pln.getNormal()) || new Vector(0,0,-1).equals(pln.getNormal()),"Wrong normal to the plane");
    }
}