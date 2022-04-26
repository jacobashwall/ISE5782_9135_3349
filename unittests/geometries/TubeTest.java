package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing tubes
 */
class TubeTest {

    /**
     * Test method for{@link geometries.Cylinder#getNormal(Point)}
     */
    @Test
    void getNormal() {
        Vector vec = new Vector(1, 0, 0);
        Point pnt = new Point(0, 0, 1);
        Ray ray = new Ray(pnt, vec);
        Tube tube = new Tube(ray, 1);
        //============ Equivalence Partitions Tests ==============//
        //EP1:Testing that getNormal returns the correct vector on the casing of the

        assertEquals(new Vector(0, 0, -1), tube.getNormal(new Point(1, 0, 0)), "EP1: getNormal is not working on casing");


        //============ Boundary Tests ==============//
        //BVA1: Testing the boundary case where the point creates an orthogonal vector to the ray direction vector on the base.

        assertEquals(new Vector(0, 0, -1), tube.getNormal(new Point(0, 0, 0)), "BVA1: getNormal is not working");

    }

   /* *//**
     * Test method for{@link geometries.Tube#findGeoIntersections(Ray)}
     *//*
    @Test
    void findIntersections() {
        Ray ray = new Ray(new Point(0, 0, 2), new Vector(1, 0, 0));
        Tube tube = new Tube(ray, 1);

        Point pnt1 = new Point(1.5, -0.6, 2.8);
        Point pnt2 = new Point(2.5, -1, 2);
        //============ Equivalence Partitions Tests ==============//
        //EP1: Ray starts outside the tube and intersects the tube twice
        assertEquals(2, tube.findGeoIntersections(new Ray(new Point(0, 0, 4), new Vector(5, -2, -4))).size(), "EP1: Wrong number of points - 2");
        assertEquals(List.of(pnt1, pnt2), tube.findIntersections(new Ray(new Point(0, 0, 4), new Vector(5, -2, -4))), "EP1: Wrong Intersections");

        //EP2: Ray starts inside the sphere and intersects it in one point
        assertEquals(1, tube.findIntersections(new Ray(new Point(2, -0.8, 2.4), new Vector(5, -2, -4))).size(), "EP2: Wrong number of intersections");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(new Point(0, 0, 4), new Vector(5, -2, -4))), "EP2: Wrong Intersection");

        //EP3: Ray starts before the tube and doesn't intersect it
        assertNull(tube.findIntersections(new Ray(new Point(0, 0, 4), new Vector(0, -5, -4))), "EP3: Fails to return null");

        //EP4: Ray starts after the tube and doesn't intersect it
        assertNull(tube.findIntersections(new Ray(new Point(5, -2, -4), new Vector(5, -2, -4))), "EP4: Fails to return null");

        //============ Boundary Tests ==============//

        // **** Group: Ray's line goes through the axis ray
        Point pnt3 = new Point(1.5, 0, 3);
        Point pnt4 = new Point(4.5, 0, 1);
        Point pnt5 = new Point(-3, 0, 4);
        //Point pnt6 = new Point(3, 0, 0);
        Point pnt7 = new Point(-1.5, 0, 3);
        Point pnt8 = new Point(1.5, 0, 1);
        Vector vec1 = new Vector(6, 0, -4);
        //BVA1: Ray simply intersect the axis ray
        assertEquals(2, tube.findIntersections(new Ray(new Point(0, 0, 4), vec1)).size(), "BVA1: Wrong number of points - 2");
        assertEquals(List.of(pnt3, pnt4), tube.findIntersections(new Ray(new Point(0, 0, 4), vec1)), "BVA1: Wrong Intersections");

        //BVA2: Ray starts at the tube and intersects it once
        assertEquals(1, tube.findIntersections(new Ray(pnt3, vec1)).size(), "BVA2: Wrong number of points - 1");
        assertEquals(List.of(pnt4), tube.findIntersections(new Ray(pnt3, vec1)), "BVA2: Wrong Intersection1");

        //BVA3: Ray starts inside the tube and intersects it once
        assertEquals(1, tube.findIntersections(new Ray(new Point(3, 0, 2), vec1)).size(), "BVA3: Wrong number of points - 1");
        assertEquals(List.of(pnt4), tube.findIntersections(new Ray(new Point(3, 0, 2), vec1)), "BVA3: Wrong Intersection");

        //BVA4: Ray starts at the tube and goes outside
        assertNull(tube.findIntersections(new Ray(pnt4, vec1)), "BVA4: Fails to return null");

        //BVA5: Ray starts after the tube
        assertNull(tube.findIntersections(new Ray(new Point(6, 0, -4), vec1)), "BVA5: Fails to return null");


        // **** Group: Ray's line goes through the head of the axis ray

        //BVA6: Ray starts outside the tube and intersect the tube twice
        assertEquals(2, tube.findIntersections(new Ray(pnt5, vec1)).size(), "BVA6: Wrong number of points - 2");
        assertEquals(List.of(pnt7, pnt8), tube.findIntersections(new Ray(pnt5, vec1)), "BVA6: Wrong Intersections");

        //BVA7:  Ray starts at the tube
        assertEquals(1, tube.findIntersections(new Ray(pnt7, vec1)).size(), "BVA7: Wrong number of points - 1");
        assertEquals(List.of(pnt8), tube.findIntersections(new Ray(pnt7, vec1)), "BVA7: Wrong Intersection");

        //BVA8: Ray starts inside the tube before the head of the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(-0.75, 0, 2.5), vec1)).size(), "BVA8: Wrong number of points - 1");
        assertEquals(List.of(pnt8), tube.findIntersections(new Ray(new Point(-0.75, 0, 2.5), vec1)), "BVA8: Wrong Intersection");

        //BVA9: Ray starts inside the tube after the head of the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(0.75, 0, 1.5), vec1)).size(), "BVA9: Wrong number of points - 1");
        assertEquals(List.of(pnt8), tube.findIntersections(new Ray(new Point(0.75, 0, 1.5), vec1)), "BVA9: Wrong Intersection");

        //BVA10: Ray starts inside the tube after the head of the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(0.75, 0, 1.5), vec1)).size(), "BVA10: Wrong number of points - 1");
        assertEquals(List.of(pnt8), tube.findIntersections(new Ray(new Point(0.75, 0, 1.5), vec1)), "BVA10: Wrong Intersection");

        //BVA11: Ray starts at the tube and the ray goes outside the sphere
        assertNull(tube.findIntersections(new Ray(pnt8, vec1)), "BVA11: Fails to return null");

        //BVA12: Ray starts after the tube and the ray goes outside the sphere
        assertNull(tube.findIntersections(new Ray(new Point(6, 0, -4), vec1)), "BVA12: Fails to return null");

        // **** Group: Ray's line orthogonal to the axis ray
        Vector vec2 = new Vector(0, 1, 0);
        pnt1 = new Point(1, -1, 2);
        pnt2 = new Point(1, 1, 2);

        //BVA13: Ray orthogonal to the axis ray and goes through it
        assertEquals(2, tube.findIntersections(new Ray(new Point(1, -2, 2), vec2)).size(), "BVA13: Wrong number of points - 2");
        assertEquals(List.of(pnt1, pnt2), tube.findIntersections(new Ray(new Point(1, -2, 2), vec1)), "BVA13: Wrong Intersection");

        //BVA14:  Ray starts on the tube
        assertEquals(1, tube.findIntersections(new Ray(pnt1, vec1)).size(), "BVA14: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(pnt1, vec1)), "BVA14: Wrong Intersection");

        //BVA15: Ray starts inside the tube before the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(1, -0.5, 2), vec1)).size(), "BVA15: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(new Point(1, -0.5, 2), vec1)), "BVA15: Wrong Intersection");

        //BVA16: Ray starts inside the tube on the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(1, 0, 2), vec1)).size(), "BVA16: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(new Point(1, 0, 2), vec1)), "BVA16: Wrong Intersection");

        //BVA17: Ray starts inside the tube after the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(1, 0.5, 2), vec1)).size(), "BVA17: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(new Point(1, 0.5, 2), vec1)), "BVA17: Wrong Intersection");

        //BVA18: Ray starts at the tube and the ray goes outside
        assertNull(tube.findIntersections(new Ray(pnt2, vec1)), "BVA18: Fails to return null");

        //BVA19: Ray starts at the tube and the ray goes outside
        assertNull(tube.findIntersections(new Ray(new Point(1, 2, 2), vec1)), "BVA19: Fails to return null");

        // **** Group: Ray's line orthogonal to the axis ray and goes through the center
        pnt1 = new Point(0, -1, 2);
        pnt2 = new Point(0, 1, 2);

        //BVA20: Ray orthogonal to the axis ray and goes through it
        assertEquals(2, tube.findIntersections(new Ray(new Point(0, -2, 2), vec2)).size(), "BVA20: Wrong number of points - 2");
        assertEquals(List.of(pnt1, pnt2), tube.findIntersections(new Ray(new Point(0, -2, 2), vec1)), "BVA20: Wrong Intersection");

        //BVA21:  Ray starts on the tube
        assertEquals(1, tube.findIntersections(new Ray(pnt1, vec1)).size(), "BVA21: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(pnt1, vec1)), "BVA21: Wrong Intersection");

        //BVA22: Ray starts inside the tube before the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(0, -0.5, 2), vec1)).size(), "BVA22: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(new Point(0, -0.5, 2), vec1)), "BVA22: Wrong Intersection");

        //BVA23: Ray starts inside the tube on the head of the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(0, 0, 2), vec1)).size(), "BVA23: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(new Point(0, 0, 2), vec1)), "BVA23: Wrong Intersection");

        //BVA24: Ray starts inside the tube after the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(0, 0.5, 2), vec1)).size(), "BVA24: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(new Point(0, 0.5, 2), vec1)), "BVA24: Wrong Intersection");

        //BVA25: Ray starts at the tube and the ray goes outside
        assertNull(tube.findIntersections(new Ray(pnt2, vec1)), "BVA25: Fails to return null");

        //BVA26: Ray starts at the tube and the ray goes outside
        assertNull(tube.findIntersections(new Ray(new Point(0, 2, 2), vec1)), "BVA26: Fails to return null");

        // **** Group: Ray is parallel to the axis ray and in the same direction

        //BVA27: Ray is parallel to the axis ray and has the same direction
        assertNull(tube.findIntersections(new Ray(new Point(-1, 0, 3), new Vector(1, 0, 0))), "BVA27: Fails to return null");

        //BVA28: Ray is parallel to the axis ray and the vector between the heads of the rays is orthogonal to the axis ray
        assertNull(tube.findIntersections(new Ray(new Point(0, 0, 4), new Vector(1, 0, 0))), "BVA28: Fails to return null");

        //BVA29: Ray coincides with the axis ray
        assertNull(tube.findIntersections(new Ray(new Point(-1, 0, 2), new Vector(1, 0, 0))), "BVA29: Fails to return null");

        //BVA30: Ray coincides with the axis ray and starts at the same point
        assertNull(tube.findIntersections(new Ray(new Point(0, 0, 2), new Vector(1, 0, 0))), "BVA30: Fails to return null");

        //BVA31: Ray is parallel to the axis have the same direction and inside the tube
        assertNull(tube.findIntersections(new Ray(new Point(0, 0, 2.5), new Vector(1, 0, 0))), "BVA31: Fails to return null");

        //BVA32: Ray is on the tube
        assertNull(tube.findIntersections(new Ray(new Point(0, 0.8, 2.6), new Vector(1, 0, 0))), "BVA31: Fails to return null");


        // **** Group: Ray is orthogonal to the axis ray and doesn't intersect the axis ray but intersects the tube

        pnt1 = new Point(0, -0.8, 2.6);
        pnt2 = new Point(0, 0.8, 2.6);
        vec2 = new Vector(0, 1, 0);

        //BVA33: Ray parallel to the axis ray
        assertEquals(2, tube.findIntersections(new Ray(new Point(0, -2, 2.6), vec2)).size(), "BVA33: Wrong number of points - 2");
        assertEquals(List.of(pnt1, pnt2), tube.findIntersections(new Ray(new Point(0, -2, 2.6), vec1)), "BVA33: Wrong Intersection");

        //BVA34:  Ray starts on the tube
        assertEquals(1, tube.findIntersections(new Ray(pnt1, vec1)).size(), "BVA34: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(pnt1, vec1)), "BVA34: Wrong Intersection");

        //BVA35: Ray starts inside the tube before the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(0, -0.4, 2.6), vec1)).size(), "BVA35: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(new Point(0, -0.4, 2.6), vec1)), "BVA35: Wrong Intersection");

        //BVA36: Ray starts inside the tube above the head of the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(0, 0, 2.6), vec1)).size(), "BVA36: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(new Point(0, 0, 2.6), vec1)), "BVA36: Wrong Intersection");

        //BVA37: Ray starts inside the tube after the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(0, 0.4, 2.6), vec1)).size(), "BVA37: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(new Point(0, 0.4, 2.6), vec1)), "BVA37: Wrong Intersection");

        //BVA38: Ray starts at the tube and the ray goes outside
        assertNull(tube.findIntersections(new Ray(pnt2, vec1)), "BVA38: Fails to return null");

        //BVA39: Ray starts at the tube and the ray goes outside
        assertNull(tube.findIntersections(new Ray(new Point(0, 2, 2.6), vec1)), "BVA39: Fails to return null");

        // **** Group: Ray is orthogonal to the axis ray and doesn't intersect the axis and the tube

        pnt1 = new Point(0, -0.8, 4);
        pnt2 = new Point(0, 0.8, 4);
        vec2 = new Vector(0, 1, 0);

        //BVA40: Ray parallel to the axis ray
        assertEquals(2, tube.findIntersections(new Ray(new Point(0, -2, 4), vec2)).size(), "BVA40: Wrong number of points - 2");
        assertEquals(List.of(pnt1, pnt2), tube.findIntersections(new Ray(new Point(0, -2, 4), vec1)), "BVA40: Wrong Intersection");

        //BVA41:  Ray starts on the tube
        assertEquals(1, tube.findIntersections(new Ray(pnt1, vec1)).size(), "BVA41: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(pnt1, vec1)), "BVA41: Wrong Intersection");

        //BVA42: Ray starts inside the tube before the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(0, -0.4, 4), vec1)).size(), "BVA42: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(new Point(0, -0.4, 4), vec1)), "BVA42: Wrong Intersection");

        //BVA43: Ray starts inside the tube above the head of the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(0, 0, 4), vec1)).size(), "BVA43: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(new Point(0, 0, 4), vec1)), "BVA43: Wrong Intersection");

        //BVA44: Ray starts inside the tube after the axis ray
        assertEquals(1, tube.findIntersections(new Ray(new Point(0, 0.4, 4), vec1)).size(), "BVA44: Wrong number of points - 1");
        assertEquals(List.of(pnt2), tube.findIntersections(new Ray(new Point(0, 0.4, 4), vec1)), "BVA44: Wrong Intersection");

        //BVA45: Ray starts at the tube and the ray goes outside
        assertNull(tube.findIntersections(new Ray(pnt2, vec1)), "BVA45: Fails to return null");

        //BVA46: Ray starts at the tube and the ray goes outside
        assertNull(tube.findIntersections(new Ray(new Point(0, 2, 4), vec1)), "BVA46: Fails to return null");


    }
*/
    /**
     * Test method for {@link geometries.Tube#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersectionsRay() {
        Tube tube1 = new Tube(new Ray(new Point(1, 0, 0), new Vector(0, 1, 0)), 1d);
        Vector vAxis = new Vector(0, 0, 1);
        Tube tube2 = new Tube(new Ray(new Point(1, 1, 1), vAxis), 1d);
        Ray ray;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the tube (0 points)
       ray = new Ray(new Point(1, 1, 2), new Vector(1, 1, 0));
        assertNull(tube1.findIntersections(ray),"Must not be intersections");

        // TC02: Ray's crosses the tube (2 points)
        ray = new Ray(new Point(0, 0, 0), new Vector(2, 1, 1));
        List<Point> result = tube2.findIntersections(ray);
        assertNotNull(result,"must be intersections");
        assertEquals(2, result.size(),"must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(0.4, 0.2, 0.2), new Point(2, 1, 1)), result, "Bad intersections");

        // TC03: Ray's starts within tube and crosses the tube (1 point)
        ray = new Ray(new Point(1, 0.5, 0.5), new Vector(2, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result,"must be intersections");
        assertEquals(1, result.size(),"must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 1)), result,"Bad intersections");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line is parallel to the axis (0 points)
        // TC11: Ray is inside the tube (0 points)
        ray = new Ray(new Point(0.5, 0.5, 0.5), vAxis);
        assertNull(tube2.findIntersections(ray),"must not be intersections");
        // TC12: Ray is outside the tube
        ray = new Ray(new Point(0.5, -0.5, 0.5), vAxis);
        assertNull(tube2.findIntersections(ray),"must not be intersections");
        // TC13: Ray is at the tube surface
        ray = new Ray(new Point(2, 1, 0.5), vAxis);
        assertNull(tube2.findIntersections(ray),"must not be intersections");
        // TC14: Ray is inside the tube and starts against axis head
        ray = new Ray(new Point(0.5, 0.5, 1), vAxis);
        assertNull(tube2.findIntersections(ray),"must not be intersections");
        // TC15: Ray is outside the tube and starts against axis head
        ray = new Ray(new Point(0.5, -0.5, 1), vAxis);
        assertNull( tube2.findIntersections(ray),"must not be intersections");
        // TC16: Ray is at the tube surface and starts against axis head
        ray = new Ray(new Point(2, 1, 1), vAxis);
        assertNull(tube2.findIntersections(ray),"must not be intersections");
        // TC17: Ray is inside the tube and starts at axis head
        ray = new Ray(new Point(1, 1, 1), vAxis);
        assertNull(tube2.findIntersections(ray),"must not be intersections");

        // **** Group: Ray is orthogonal but does not begin against the axis head
        // TC21: Ray starts outside and the line is outside (0 points)
        ray = new Ray(new Point(0, 2, 2), new Vector(1, 1, 0));
        assertNull(tube2.findIntersections(ray),"must not be intersections");
        // TC22: The line is tangent and the ray starts before the tube (0 points)
        ray = new Ray(new Point(0, 2, 2), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray),"must not be intersections");
        // TC23: The line is tangent and the ray starts at the tube (0 points)
        ray = new Ray(new Point(1, 2, 2), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray),"must not be intersections");
        // TC24: The line is tangent and the ray starts after the tube (0 points)
        ray = new Ray(new Point(2, 2, 2), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        // TC25: Ray starts before (2 points)
        ray = new Ray(new Point(0, 0, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(0.4, 0.2, 2), new Point(2, 1, 2)), result, "Bad intersections");
        // TC26: Ray starts at the surface and goes inside (1 point)
        ray = new Ray(new Point(0.4, 0.2, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result,"must be intersections");
        assertEquals(1, result.size(),"must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 2)), result, "Bad intersections");
        // TC27: Ray starts inside (1 point)
        ray = new Ray(new Point(1, 0.5, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 2)), result , "Bad intersections");
        // TC28: Ray starts at the surface and goes outside (0 points)
        ray = new Ray(new Point(2, 1, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result , "Bad intersections");
        // TC29: Ray starts after
        ray = new Ray(new Point(4, 2, 2), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        // TC30: Ray starts before and crosses the axis (2 points)
        ray = new Ray(new Point(1, -1, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(1, 0, 2), new Point(1, 2, 2)), result, "Bad intersections");
        // TC31: Ray starts at the surface and goes inside and crosses the axis
        ray = new Ray(new Point(1, 0, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 2)), result, "Bad intersections");
        // TC32: Ray starts inside and the line crosses the axis (1 point)
        ray = new Ray(new Point(1, 0.5, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals( 1, result.size() , "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 2)), result, "Bad intersections");
        // TC33: Ray starts at the surface and goes outside and the line crosses the
        // axis (0 points)
        ray = new Ray(new Point(1, 2, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        // TC34: Ray starts after and crosses the axis (0 points)
        ray = new Ray(new Point(1, 3, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        // TC35: Ray starts at the axis
        ray = new Ray(new Point(1, 1, 2), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 2)), result, "Bad intersections");

        // **** Group: Ray is orthogonal to axis and begins against the axis head
        // TC41: Ray starts outside and the line is outside (
        ray = new Ray(new Point(0, 2, 1), new Vector(1, 1, 0));
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        // TC42: The line is tangent and the ray starts before the tube
        ray = new Ray(new Point(0, 2, 1), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        // TC43: The line is tangent and the ray starts at the tube
        ray = new Ray(new Point(1, 2, 1), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        // TC44: The line is tangent and the ray starts after the tube
        ray = new Ray(new Point(2, 2, 2), new Vector(1, 0, 0));
        assertNull(tube2.findIntersections(ray), "must not be intersections");
        // TC45: Ray starts before
        ray = new Ray(new Point(0, 0, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(0.4, 0.2, 1), new Point(2, 1, 1)), result, "Bad intersections");
        // TC46: Ray starts at the surface and goes inside
        ray = new Ray(new Point(0.4, 0.2, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 1)), result, "Bad intersections");
        // TC47: Ray starts inside
        ray = new Ray(new Point(1, 0.5, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 1)), result, "Bad intersections");
        // TC48: Ray starts at the surface and goes outside
        ray = new Ray(new Point(2, 1, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        // TC49: Ray starts after
        ray = new Ray(new Point(4, 2, 1), new Vector(2, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        // TC50: Ray starts before and goes through the axis head
        ray = new Ray(new Point(1, -1, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(1, 0, 1), new Point(1, 2, 1)), result, "Bad intersections");
        // TC51: Ray starts at the surface and goes inside and goes through the axis
        // head
        ray = new Ray(new Point(1, 0, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 1)), result, "Bad intersections");
        // TC52: Ray starts inside and the line goes through the axis head
        ray = new Ray(new Point(1, 0.5, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 1)), result, "Bad intersections");
        // TC53: Ray starts at the surface and the line goes outside and goes through
        // the axis head
        ray = new Ray(new Point(1, 2, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        // TC54: Ray starts after and the line goes through the axis head
        ray = new Ray(new Point(1, 3, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        // TC55: Ray start at the axis head
        ray = new Ray(new Point(1, 1, 1), new Vector(0, 1, 0));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 1)), result, "Bad intersections");

        // **** Group: Ray's line is neither parallel nor orthogonal to the axis and
        // begins against axis head
        Point p0 = new Point(0, 2, 1);
        // TC61: Ray's line is outside the tube
        ray = new Ray(p0, new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        // TC62: Ray's line crosses the tube and begins before
        ray = new Ray(p0, new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(2, 1, 2), new Point(0.4, 1.8, 1.2)), result, "Bad intersections");
        // TC63: Ray's line crosses the tube and begins at surface and goes inside
        ray = new Ray(new Point(0.4, 1.8, 1), new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 1.8)), result, "Bad intersections");
        // TC64: Ray's line crosses the tube and begins inside
        ray = new Ray(new Point(1, 1.5, 1), new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(2, 1, 1.5)), result, "Bad intersections");
        // TC65: Ray's line crosses the tube and begins at the axis head
        ray = new Ray(new Point(1, 1, 1), new Vector(0, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(1, 2, 2)), result , "Bad intersections");
        // TC66: Ray's line crosses the tube and begins at surface and goes outside
        ray = new Ray(new Point(2, 1, 1), new Vector(2, -1, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        // TC67: Ray's line is tangent and begins before
        ray = new Ray(p0, new Vector(0, 2, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        // TC68: Ray's line is tangent and begins at the tube surface
        ray = new Ray(new Point(1, 2, 1), new Vector(1, 0, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        // TC69: Ray's line is tangent and begins after
        ray = new Ray(new Point(2, 2, 1), new Vector(1, 0, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");

        // **** Group: Ray's line is neither parallel nor orthogonal to the axis and
        // does not begin against axis head
        double sqrt2 = Math.sqrt(2);
        double denomSqrt2 = 1 / sqrt2;
        double value1 = 1 - denomSqrt2;
        double value2 = 1 + denomSqrt2;
        // TC71: Ray's crosses the tube and the axis
        ray = new Ray(new Point(0, 0, 2), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(value1, value1, 2 + value1), new Point(value2, value2, 2 + value2)), result, "Bad intersections");
        // TC72: Ray's crosses the tube and the axis head
        ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(2, result.size(), "must be 2 intersections");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(value1, value1, value1), new Point(value2, value2, value2)), result, "Bad intersections");
        // TC73: Ray's begins at the surface and goes inside
        // TC74: Ray's begins at the surface and goes inside crossing the axis
        ray = new Ray(new Point(value1, value1, 2 + value1), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(value2, value2, 2 + value2)), result, "Bad intersections");
        // TC75: Ray's begins at the surface and goes inside crossing the axis head
        ray = new Ray(new Point(value1, value1, value1), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(value2, value2, value2)), result, "Bad intersections");
        // TC76: Ray's begins inside and the line crosses the axis
        ray = new Ray(new Point(0.5, 0.5, 2.5), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(value2, value2, 2 + value2)), result, "Bad intersections");
        // TC77: Ray's begins inside and the line crosses the axis head
        ray = new Ray(new Point(0.5, 0.5, 0.5), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals(1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(value2, value2, value2)), result, "Bad intersections");
        // TC78: Ray's begins at the axis
        ray = new Ray(new Point(1, 1, 3), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNotNull(result, "must be intersections");
        assertEquals( 1, result.size(), "must be 1 intersections");
        assertEquals(List.of(new Point(value2, value2, 2 + value2)), result, "Bad intersections");
        // TC79: Ray's begins at the surface and goes outside
        ray = new Ray(new Point(2, 1, 2), new Vector(2, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        // TC80: Ray's begins at the surface and goes outside and the line crosses the
        // axis
        ray = new Ray(new Point(value2, value2, 2 + value2), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");
        // TC81: Ray's begins at the surface and goes outside and the line crosses the
        // axis head
        ray = new Ray(new Point(value2, value2, value2), new Vector(1, 1, 1));
        result = tube2.findIntersections(ray);
        assertNull(result, "Bad intersections");

    }

}