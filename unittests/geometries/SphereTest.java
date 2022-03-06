package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /**
     * Test method for{@link geometries.Plane#getNormal(Point)}
     */
    @Test
    void getNormal() {
        //============ Equivalence Partitions Tests ==============//
        //TC: Testing that getNormal returns the right vector
        Sphere sphere = new Sphere(new Point(0,0,0),1);
        assertEquals(sphere.getNormal(new Point(0,0,1)),new Vector(0,0,1),"Wrong normal to the sphere");
    }
}