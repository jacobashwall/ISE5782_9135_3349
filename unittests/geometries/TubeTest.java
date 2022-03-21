package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

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
        assertEquals(new Vector(0,0,-1),tube.getNormal(new Point(0,0,0)),"getNormal is not working - BVA");

    }
    /**
     * Test method for{@link geometries.Tube#findIntersections(Ray)}
     */
    @Test
    void findIntersections(){
        Ray ray = new Ray(new Point(0,0,2),new Vector(1,0,0));
        Tube tube = new Tube(ray,1);

        Point pnt1 = new Point(1.5,-0.6,2.8);
        Point pnt2 = new Point(2.5,-1,2);
        //============ Equivalence Partitions Tests ==============//
        //EP1: Ray starts outside the tube and intersects the tube twice
        assertEquals(2, tube.findIntersections(new Ray(new Point(0, 0, 4), new Vector(5, -2, -4))).size(), "EP1: Wrong number of points - 2");
        assertEquals(List.of(pnt1,pnt2), tube.findIntersections(new Ray(new Point(0, 0, 4), new Vector(5, -2, -4))), "EP1: Wrong Intersections");

        //EP2: Ray starts inside the sphere and intersects it in one point
        assertEquals(1, tube.findIntersections(new Ray(new Point(2, -0.8, 2.4), new Vector(5, -2, -4))).size(),"EP2: Wrong number of intersections");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(new Point(0, 0, 4), new Vector(5, -2, -4))), "EP2: Wrong Intersection");

        //EP3: Ray starts before the tube and doesn't intersect it
        assertNull(tube.findIntersections(new Ray(new Point(0,0,4),new Vector(0,-5,-4))),"EP3: Fails to return null");

        //EP4: Ray starts after the tube and doesn't intersect it
        assertNull(tube.findIntersections(new Ray(new Point(5, -2, -4), new Vector(5, -2, -4))),"EP4: Fails to return null");

        //============ Boundary Tests ==============//

        // **** Group: Ray's line goes through the axis ray
        Point pnt3 = new Point(1.5,0,3);
        Point pnt4 = new Point(4.5,0,1);
        //BVA1: Ray simply intersect the axis ray
        assertEquals(2, tube.findIntersections(new Ray(new Point(0, 0, 4), new Vector(5, -2, -4))).size(), "BVA1: Wrong number of points - 2");
        assertEquals(List.of(pnt3,pnt4), tube.findIntersections(new Ray(new Point(0, 0, 4), new Vector(6, 0, -4))), "BVA1: Wrong Intersections");

        //BVA2: Ray starts at the tube and untersects







    }
}