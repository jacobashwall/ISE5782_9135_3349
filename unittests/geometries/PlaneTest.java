package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing planes
 */
class PlaneTest {
    /**
     * test for the constructor
     */
    @Test
    void Plane() {
        Point pnt1 = new Point(0, 0, 0);
        Point pnt2 = new Point(1, 0, 0);
        Point pnt3 = new Point(0, 1, 0);
        //============ Equivalence Partitions Tests ==============//
        //TC1: Testing that the three point ctor creates the correct plane

        assertEquals(new Plane(pnt1, pnt2, pnt3), new Plane(pnt1, new Vector(0, 0, 1)), "TC1:Error");

        //============ Boundary Tests ==============//
        //TC2: Testing that the three point ctor creates the correct plane - equal points
        assertThrows(IllegalArgumentException.class, () -> new Plane(pnt1, pnt1, pnt2), "TC2:Plane 3 points ctor fails to throw an exception for equal points");
        //TC3: Testing that the three point ctor creates the correct plane - collinear points
        assertThrows(IllegalArgumentException.class, () -> new Plane(pnt1, pnt1, new Point(2, 0, 0)), "TC3:Plane 3 points tor fails to throw an exception for collinear points");

    }


    /**
     * Test method for{@link geometries.Plane#getNormal(Point)}
     */
    @Test
    void getNormal() {
        //============ Equivalence Partitions Tests ==============//
        //TC1: Testing that getNormal returns the correct vector
        Plane pln = new Plane(new Point(-1, -1, 0), new Point(0, 1, 0), new Point(1, 0, 0));
        assertEquals(new Vector(0, 0, -1),pln.getNormal(new Point(0, 0, 0)), "Wrong normal to the plane");
    }

    /**
     * Test method for{@link geometries.Plane#findIntersections(Ray)}
     */
    @Test
    void findIntersections() {
        //============ Equivalence Partitions Tests ==============//

        //TC1:  Neither orthogonal nor parallel ray intersects the plane
        Plane pln1 = new Plane(new Point(-2, 0, -2), new Point(0, 0, 1), new Point(1, 0, 0));
        Ray ray1 = new Ray(new Point(1, 1, 2), new Vector(-1, -1, -1));
        assertEquals(List.of(new Point(0, 0, 1)), pln1.findIntersections(ray1), "Neither orthogonal nor parallel ray intersects the plane Failed to intersect");

        //TC2:  Neither orthogonal nor parallel ray does not intersect the plane
        Plane pln2 = new Plane(new Point(-2, 0, -2), new Point(0, 0, 1), new Point(1, 0, 0));
        Ray ray2 = new Ray(new Point(1, 1, 2), new Vector(1, 1, 1));
        assertNull(pln2.findIntersections(ray2), "Neither orthogonal nor parallel ray does not intersect the plane Failed to not intersect");

        //============ Boundary Tests ==============//
        //TC3: Ray is parallel to the plane and included in the plane
        Plane pln3 = new Plane(new Point(-2, 0, -2), new Point(0, 0, 1), new Point(1, 0, 0));
        Ray ray3 = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
        assertNull(pln3.findIntersections(ray3), "Ray is parallel to the plane and included in the plane Failed to not intersect");

        //TC4: Ray is parallel to the plane and not included in the plane
        Plane pln4 = new Plane(new Point(-2, 0, -2), new Point(0, 0, 1), new Point(1, 0, 0));
        Ray ray4 = new Ray(new Point(0, 1, 0), new Vector(1, 0, 0));
        assertNull(pln4.findIntersections(ray4), "Ray is parallel to the plane and not included in the plane Failed to not intersect");

        //TC5: Ray is orthogonal to the plane p0 before the plane
        Plane pln5 = new Plane(new Point(-2, 0, -2), new Point(0, 0, 1), new Point(1, 0, 0));
        Ray ray5 = new Ray(new Point(-1, -1, -1), new Vector(0, 1, 0));
        assertEquals(List.of(new Point(-1, 0, -1)), pln5.findIntersections(ray5), "Ray is orthogonal to the plane p0 before the plane Failed to intersect");

        //TC6: Ray is orthogonal to the plane p0 in the plane
        Plane pln6 = new Plane(new Point(-2, 0, -2), new Point(0, 0, 1), new Point(1, 0, 0));
        Ray ray6 = new Ray(new Point(-1, 0, -1), new Vector(0, -1, 0));
        assertNull(pln6.findIntersections(ray6), "Ray is orthogonal to the plane p0 in the plane Failed to not intersect");

        //TC7: Ray is orthogonal to the plane p0 after the plane
        Plane pln7 = new Plane(new Point(-2, 0, -2), new Point(0, 0, 1), new Point(1, 0, 0));
        Ray ray7 = new Ray(new Point(-1, 1, -1), new Vector(0, 1, 0));
        assertNull(pln7.findIntersections(ray7), "Ray is orthogonal to the plane p0 after the plane Failed to not intersect");

        //TC8: Ray is neither orthogonal nor parallel to and begins at the plane (𝑃0
        //is in the plane, but not the ray)
        Plane pln8 = new Plane(new Point(-2, 0, -2), new Point(0, 0, 1), new Point(1, 0, 0));
        Ray ray8 = new Ray(new Point(-1, 0, -1), new Vector(1, 1, 1));
        assertNull(pln8.findIntersections(ray8), " Ray is neither orthogonal nor parallel to and begins at the plane Failed to not intersect");

        //Ray is neither orthogonal nor parallel to the plane and begins in the
        //same point which appears as reference point in the plane (Q)
        Plane pln9 = new Plane(new Point(-2, 0, -2), new Point(0, 0, 1), new Point(1, 0, 0));
        Ray ray9 = new Ray(new Point(-2, 0, -2), new Vector(1, 1, 1));
        assertNull(pln9.findIntersections(ray9), "Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane Failed to not intersect");
    }

    /**
     * Test method for{@link geometries.Plane#findGeoIntersectionsHelper(Ray,double)}
     */
    @Test
    void findGeoIntersectionsHelper() {
        Plane pln1 = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(1, 1, 0));
        Ray ray = new Ray(new Point(1,1,1), new Vector(0,0,-1));
        Intersectable.GeoPoint p1 = new Intersectable.GeoPoint(pln1,new Point(1,1,0));
        //============ Equivalence Partitions Tests ==============//
        //EP1: Distance is enough
        assertEquals(List.of(p1),pln1.findGeoIntersectionsHelper(ray,2),"Bad intersection!");
        //EP2: Distance isn't enough
        assertNull(pln1.findGeoIntersectionsHelper(ray,0),"Bad intersection!");


        //============ Boundary Tests ==============//
        //BVA1: Distance is just enough
        assertEquals(List.of(p1),pln1.findGeoIntersectionsHelper(ray,1),"Bad intersection!");


    }
}