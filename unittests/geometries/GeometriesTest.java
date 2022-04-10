package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing geometries
 */
class GeometriesTest {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(Ray)}
     */
    @Test
    public void TestFindIntersections() {
        Sphere sph = new Sphere(new Point(1, 1, 1), 1);
        Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 2));
        Triangle tr = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        Geometries collection = new Geometries(sph, plane, tr);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Some Geometries are intersected (while others don't).
        Ray ray = new Ray(new Point(-1, 0, 0), new Vector(1, 1, 1));
        assertEquals(3, collection.findIntersections(ray).size()
                , "Wrong number of intersection points."); // Intersects only plane and sphere

        // =============== Boundary Values Tests ==================

        // TC11: All the Geometries are intersected.
        ray = new Ray(new Point(2, 2, 2.5), new Vector(-1, -1, -1));
        assertEquals(4, collection.findIntersections(ray).size()
                , "Wrong number of intersection points.");

        // TC12: No Geometries are intersected.
        ray = new Ray(new Point(-1, 0, 0), new Vector(-1, -1, -1));
        assertNull(collection.findIntersections(ray), "No intersection points.");

        // TC13: Only one Geometry shape is intersected.
        ray = new Ray(new Point(2, 0, 2), new Vector(-1, -1, -1));
        assertEquals(1, collection.findIntersections(ray).size()
                , "Wrong number of intersection points.");  // Intersects only plane

        // TC14: Empty Geometries collection.
        assertNull(new Geometries().findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0)))
                , "No geometry shapes in the collection.");
    }
}