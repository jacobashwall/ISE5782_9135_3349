package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing spheres
 */
class SphereTest {

    /**
     * Test method for{@link geometries.Sphere#getNormal(Point)}
     */
    @Test
    void getNormal() {
        //============ Equivalence Partitions Tests ==============//
        //TC: Testing that getNormal returns the right vector
        Sphere sphere = new Sphere(new Point(0, 0, 0), 1);
        assertEquals(sphere.getNormal(new Point(0, 0, 1)), new Vector(0, 0, 1), "Wrong normal to the sphere");
    }

    /**
     * Test method for{@link Sphere#findIntersections(Ray)}
     */
    @Test
    void findIntersections() {
        Sphere sphere = new Sphere(new Point(2, 0, 0), 1);
        Point pnt1 = new Point(1.1339745962156, 0.5, 0);
        Point pnt2 = new Point(2.8660254037844, 0.5, 0);


        //============ Equivalence Partitions Tests ==============//

        //EP1: Head of the ray is outside the sphere and intersects the sphere twice
        assertEquals(2, sphere.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(1, 0, 0))).size(), "EP1: Wrong number of points - 2");
        assertEquals(List.of(pnt1, pnt2), sphere.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(1, 0, 0))), "EP1: Wrong intersections");

        //EP2: Ray doesn't intersect the sphere
        assertNull(sphere.findIntersections(new Ray(new Point(0, 2, 0), new Vector(1, 0, 0))), "EP2: Ray's line out of sphere");

        //EP3: Ray starts inside the sphere
        assertEquals(1, sphere.findIntersections(new Ray(new Point(1.5, 0.5, 0), new Vector(1, 0, 0))).size(), "EP3: Wrong number of points - 1");
        assertEquals(List.of(pnt1), sphere.findIntersections(new Ray(new Point(1.5, 0.5, 0), new Vector(-1, 0, 0))), "EP3: Wrong intersection");

        //EP4: Ray starts after the sphere
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0.5, 0), new Vector(1, 0, 0))), "EP4: Ray starts after the sphere");

        //============ Boundary Tests ==============//

        // **** Group: Ray's line crosses the sphere (but not the center)


        //BVA1: Ray starts at the sphere and goes inside
        assertEquals(1, sphere.findIntersections(new Ray(pnt1, new Vector(1, 0, 0))).size(), "BVA1: Wrong number of points - 1");
        assertEquals(List.of(pnt2), sphere.findIntersections(new Ray(pnt1, new Vector(1, 0, 0))), "BVA1: Wrong intersection");

        //BVA2: Ray starts at the sphere and goes outside
        assertNull(sphere.findIntersections(new Ray(pnt1, new Vector(-1, 0, 0))), "BVA2: Ray's line out of sphere");

        // **** Group: Ray's line goes through the center


        //BVA3: Ray starts before the sphere
        assertEquals(2, sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0))).size(), "BVA3: Wrong number of points - 2");
        assertEquals(List.of(new Point(1, 0, 0), new Point(3, 0, 0)), sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0))), "BVA3: Wrong intersections");

        //BVA4: Ray starts at sphere and goes inside
        assertEquals(1, sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0))).size(), "BVA4: Wrong number of points - 1");
        assertEquals(List.of(new Point(3, 0, 0)), sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0))), "BVA4: Wrong intersection");

        //BVA5: Ray starts at the sphere and goes outside
        assertNull(sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(-1, 0, 0))), "BVA5: Ray's line out of sphere");

        //BVA6: Ray starts inside the sphere before the center
        assertEquals(1, sphere.findIntersections(new Ray(new Point(1.5, 0, 0), new Vector(1, 0, 0))).size(), "BVA6: Wrong number of points - 1");
        assertEquals(List.of(new Point(3, 0, 0)), sphere.findIntersections(new Ray(new Point(1.5, 0, 0), new Vector(1, 0, 0))), "BVA6: Wrong intersection");

        //BVA7: Ray starts inside the sphere after the center
        assertEquals(1, sphere.findIntersections(new Ray(new Point(2.5, 0, 0), new Vector(1, 0, 0))).size(), "BVA7: Wrong number of points - 1");
        assertEquals(List.of(new Point(3, 0, 0)), sphere.findIntersections(new Ray(new Point(2.5, 0, 0), new Vector(1, 0, 0))), "BVA7: Wrong intersection");

        //BVA8: Ray starts at the center
        assertEquals(1, sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))).size(), "BVA8: Wrong number of points - 1");
        assertEquals(List.of(new Point(3, 0, 0)), sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))), "BVA8: Wrong intersection");

        //BVA9: Ray starts after the sphere
        assertNull(sphere.findIntersections(new Ray(new Point(4, 0, 0), new Vector(1, 0, 0))), "BVA9: Ray's line inside of sphere");

        // **** Group: Ray's line is tangent to the sphere

        //BVA10: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0, 1, 0), new Vector(1, 0, 0))), "BVA10: Ray starts before the tangent point");

        //BVA11: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, 1, 0), new Vector(1, 0, 0))), "BVA11: Ray starts at the tangent point");

        //BVA12: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(3, 1, 0), new Vector(1, 0, 0))), "BVA12: Ray starts after the tangent point");

        // **** Group: Special cases

        //BVA13: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(0, 1, 0), new Vector(0, 1, 0))), "BVA13: Ray's line is outside, ray is orthogonal to ray start to sphere's center line");


    }

    /**
     * Test method for{@link Sphere#findGeoIntersectionsHelper(Ray)}
     */
    @Test
    void findGeoIntersectionsHelper() {
        Sphere sphere = new Sphere(new Point(2, 1, 0), 1);
        Vector vec = new Vector(1,0,0);
        Ray ray = new Ray(new Point(0,1,0),vec);
        Intersectable.GeoPoint p1 = new Intersectable.GeoPoint(sphere,new Point(1,1,0));
        Intersectable.GeoPoint p2 = new Intersectable.GeoPoint(sphere,new Point(3,1,0));

        //============ Equivalence Partitions Tests ==============//
        //Group: Head of the ray is outside the sphere and intersects the sphere twice
        //EP1: maxDistance<t1
        assertNull(sphere.findGeoIntersectionsHelper(ray,0.5),"EP1: Bad intersection!");
        //EP2: Head of the ray is inside the sphere and intersects the sphere once t1<maxDistance<t2
        assertEquals(List.of(p1),sphere.findGeoIntersectionsHelper(ray,2),"EP2: Bad intersection");
        //EP3: t2<maxDistance
        assertEquals(List.of(p1,p2),sphere.findGeoIntersectionsHelper(ray,4),"EP3: Bad intersection");

        //============ Boundary Tests ==============//
        //EP1: Ray maxDistance=t1
        assertEquals(List.of(p1),sphere.findGeoIntersectionsHelper(ray,1),"EP1: Bad intersection!");
        //EP2:maxDistance=t2
        assertEquals(List.of(p1,p2),sphere.findGeoIntersectionsHelper(ray,3),"EP2: Bad intersection");





    }
}