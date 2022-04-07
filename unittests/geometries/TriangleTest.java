package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing triangles
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC: There is a simple single test here
        Triangle trn = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), trn.getNormal(new Point(0, 0, 1)), "Bad normal to triangle");
    }

    /**
     * Test method for{@link geometries.Triangle#findIntersections(Ray)}
     */
    @Test
    void findIntersections() {

        Triangle trn = new Triangle(new Point(-1, 1, 0), new Point(1, 0, 0), new Point(-1, -1, 0));
        //============ Equivalence Partitions Tests ==============//
        //TC: The ray intersect with the triangle's plane inside the triangle
        Ray ray1 = new Ray(new Point(0, 0, 1), new Vector(-0.5, 0, -1));
        assertEquals(List.of(new Point(-0.5, 0, 0)), trn.findIntersections(ray1), "The ray failed to intersect with the triangle's plane inside the triangle ");

        //TC: The ray intersect with the triangle's plane outside the triangle in front of the triangle's rib
        Ray ray2 = new Ray(new Point(0, 0, 1), new Vector(0, 1, -1));
        assertNull(trn.findIntersections(ray2), "The ray failed to intersect with the triangle's plane outside the triangle in front of the triangle's rib");

        //TC: The ray intersect with the triangle's plane outside the triangle in front of the triangle's vertex
        Ray ray3 = new Ray(new Point(0, 0, 1), new Vector(-2, 2, -1));
        assertNull(trn.findIntersections(ray3), "The ray failed to intersect with the triangle's plane outside the triangle in front of the triangle's vertex");

        //============ Boundary Tests ==============//
        //TC: The ray intersect with the triangle's plane on the triangle's rib
        Ray ray4 = new Ray(new Point(0, 0, 1), new Vector(-1, 0, -1));
        assertNull(trn.findIntersections(ray4), "The ray failed to intersect with the triangle's plane on the triangle's rib");

        //TC: The ray intersect with the triangle's plane on the triangle's vertex
        Ray ray5 = new Ray(new Point(0, 0, 1), new Vector(1, 0, -1));
        assertNull(trn.findIntersections(ray5), "The ray failed to intersect with the triangle's plane on the triangle's vertex");

        //TC: The ray intersect with the triangle's plane outside the triangle on one of the triangle's rib's vector
        Ray ray6 = new Ray(new Point(0, 0, 1), new Vector(-1, -2, -1));
        assertNull(trn.findIntersections(ray6), "The ray failed to intersect with the triangle's plane on one of the triangle's rib's vector");
    }

}