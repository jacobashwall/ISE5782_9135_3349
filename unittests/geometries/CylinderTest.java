/*
package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

*/
/**
 * Testing cylinders
 *//*

class CylinderTest {

    */
/**
     * Test method for{@link geometries.Cylinder#getNormal(Point)}
     *//*

    @Test
    void getNormal() {
        Vector vec = new Vector(1, 0, 0);
        Point pnt = new Point(0, 0, 1);
        Ray ray = new Ray(pnt, vec);
        Cylinder cylinder = new Cylinder(ray, 1, 10);
        //============ Equivalence Partitions Tests ==============//
        //Testing that getNormal returns the correct vector in different equivalence partitions

        //EP1: casing
        assertEquals(new Vector(0, 0, -1), cylinder.getNormal(new Point(1, 0, 0)), "getNormal is not working - EP1:casing");

        //EP2: First base
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(0, 0.5, 1)), "getNormal is not working - EP2: First base");

        //EP3: Second base
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(10, 0.5, 1)), "getNormal is not working - EP3: Second base");

        //============ Boundary Tests ==============//
        //Testing the boundary case where the point is on the perimeter or the center of the bases. The normal should be calculated as the other base points.

        //BVA1: First base - center
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(0, 0, 1)), "getNormal is not working - BVA1: First base");

        //BVA2: Second base - center
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(10, 0, 1)), "getNormal is not working - BVA2: Second base");

        //BVA3: First base - perimeter
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(0, 0, 2)), "getNormal is not working - BVA3: First base");

        //BVA4: Second base - perimeter
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(10, 0, 2)), "getNormal is not working - BVA4: Second base");


    }

    */
/**
     * Test method for{@link Cylinder#findIntersections(Ray)}
     *//*

    @Test
    void findIntersections() {
        Point p1 = new Point(-1, 0, 0);
        Vector vec1 = new Vector(1, 0, 0);
        Ray axisRay = new Ray(p1, vec1);
        Cylinder cylinder = new Cylinder(axisRay, 1, 2);

        //============ Equivalence Partitions Tests ==============//
        //EP1: ray intersects with both bases
        assertEquals(List.of(new Point(-1, 0.5, 0.5), new Point(1, 0.5, -0.5)), cylinder.findIntersections(new Ray(new Point(-3, 0.5, 2.5), new Vector(2, 0, -2))), "EP1: Bad intersection");

        //EP2: ray intersects with first base
        assertEquals(List.of(new Point(0, 0, 1), new Point(-1, -0.5, -0.5)), cylinder.findIntersections(new Ray(new Point(1, 0.5, 2.5), new Vector(-1, -0.5, -1.5))), "EP2: Bad intersection");

        //EP3: ray intersects with the second base
        assertEquals(List.of(new Point(0, 0, 1), new Point(1, 0.5, -0.5)), cylinder.findIntersections(new Ray(new Point(-1, -0.5, 2.5), new Vector(1, 0.5, -1.5))), "EP3: Bad intersection");

        //EP4: ray starts inside the cylinder
        assertEquals(List.of(new Point(-1, -0.5, -0.5)), cylinder.findIntersections(new Ray(new Point(-0.5, -0.25, 0.25), new Vector(-1, -0.5, -1.5))), "EP4: Bad intersection");

        //EP5: no intersections
        assertNull(cylinder.findIntersections(new Ray(new Point(0, 0, 100), new Vector(1, 0.5, -1.5))), "EP5: No intersections!");

        //============ Boundary Tests ==============//
        //Group 1 - axisRay parallel to the ray (base with the head of the axis ray)

        //BVA1.1: ray coincides with the axis ray
        assertEquals(List.of(new Point(1, 0, 0)), cylinder.findIntersections(axisRay), "BVA1.1: Bad intersections");

        //BVA1.2: rays starts before the axis ray and coincide with it (same vectors)
        assertEquals(List.of(new Point(-1, 0, 0), new Point(1, 0, 0)), cylinder.findIntersections(new Ray(new Point(-1.5, 0, 0), vec1)), "BVA1.2: Bad intersection");

        //BVA1.3: rays starts after the axis ray and coincide with it (same vector)
        assertEquals(List.of(new Point(1, 0, 0)), cylinder.findIntersections(new Ray(new Point(-0.5, 0, 0), vec1)), "BVA1.3: Bad intersection");

        //BVA1.4: ray starts inside the tube
        assertEquals(List.of(new Point(1, 0, 0.5)), cylinder.findIntersections(new Ray(new Point(-0.5, 0, 0.5), vec1)), "BVA1.4: Bad intersection");

        //BVA1.5: ray starts in the first base
        assertEquals(List.of(new Point(1, 0, 0.5)), cylinder.findIntersections(new Ray(new Point(-1, 0, 0.5), vec1)), "BVA1.5: Bad intersection");

        //BVA1.6: ray starts in the second base
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 0, 0.5), vec1)), "BVA1.6: Bad intersection");

        //BVA1.7: ray starts in the perimeter of the first base
        assertNull(cylinder.findIntersections(new Ray(new Point(-1, 0, 1), vec1)), "BVA1.7: Bad intersection");

        //BVA1.8: ray starts in the perimeter of the second base
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 0, 1), vec1)), "BVA1.8: Bad intersection");

        //BVA1.9: ray starts in the center of the second base
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 0, 0), vec1)), "BVA1.9: Bad intersection");

        //BVA.10: ray starts after the cylinder and coincide with it
        assertNull(cylinder.findIntersections(new Ray(new Point(2, 0, 0), vec1)), "BVA1.9: Bad intersection");

        //BVA1.11: rays starts before the axis ray head
        assertNull(cylinder.findIntersections(new Ray(new Point(2, 0, 0), vec1)), "BVA1.9: Bad intersection");

        //BVA1.12: rays goes through the casing
        assertNull(cylinder.findIntersections(new Ray(new Point(-2, 0, 1), vec1)), "BVA1.8: Bad intersection");

        //BVA1.13: ray starts at the casing
        assertNull(cylinder.findIntersections(new Ray(new Point(0, 0, 1), vec1)), "BVA1.8: Bad intersection");

        //BVA1.14: ray is outside the cylinder
        assertNull(cylinder.findIntersections(new Ray(new Point(0, 0, 2), vec1)), "BVA1.8: Bad intersection");

        //BVA1.15: ray is outside cylinder but the vector between the heads is orthogonal to the axis ray
        assertNull(cylinder.findIntersections(new Ray(new Point(-1, 0, 2), vec1)), "BVA1.8: Bad intersection");

        // Group 1* reverse - ray goes the other direction (second base)

        //BVA1.1: ray starts at the center of the other base
        vec1 = vec1.scale(-1);
        assertEquals(List.of(new Point(1, 0, 0)), cylinder.findIntersections(new Ray(new Point(1,0,0),vec1)), "BVA1.1: Bad intersections");

        //BVA1.2: rays starts before the axis ray and coincide with it (same vectors)
        assertEquals(List.of(new Point(1, 0, 0), new Point(-1, 0, 0)), cylinder.findIntersections(new Ray(new Point(1.5,0,0),vec1)), "BVA1.2: Bad intersection");

        //BVA1.3: rays starts after the axis ray and coincide with it (same vector)
        assertEquals(List.of(new Point(-1, 0, 0)), cylinder.findIntersections(new Ray(new Point(0.5, 0, 0), vec1)), "BVA1.3: Bad intersection");

        //BVA1.4: ray starts inside the tube
        assertEquals(List.of(new Point(-1, 0, 0.5)), cylinder.findIntersections(new Ray(new Point(0.5, 0, 0.5), vec1)), "BVA1.4: Bad intersection");

        //BVA1.5: ray starts in the first base
        assertEquals(List.of(new Point(-1, 0, 0.5)), cylinder.findIntersections(new Ray(new Point(1, 0, 0.5), vec1)), "BVA1.5: Bad intersection");

        //BVA1.6: ray starts in the second base
        assertNull(cylinder.findIntersections(new Ray(new Point(-1, 0, 0.5), vec1)), "BVA1.6: Bad intersection");

        //BVA1.7: ray starts in the perimeter of the first base
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 0, 1), vec1)), "BVA1.7: Bad intersection");

        //BVA1.8: ray starts in the perimeter of the second base
        assertNull(cylinder.findIntersections(new Ray(new Point(-1, 0, 1), vec1)), "BVA1.8: Bad intersection");

        //BVA1.9: ray starts in the center of the second base
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 0, 0), vec1)), "BVA1.9: Bad intersection");

        //BVA.10: ray starts after the cylinder and coincide with it
        assertNull(cylinder.findIntersections(new Ray(new Point(-2, 0, 0), vec1)), "BVA1.9: Bad intersection");

        //BVA1.11: rays starts before the axis ray head
        assertNull(cylinder.findIntersections(new Ray(new Point(-2, 0, 0), vec1)), "BVA1.9: Bad intersection");

        //BVA1.12: rays goes through the casing
        assertNull(cylinder.findIntersections(new Ray(new Point(-2, 0, 1), vec1)), "BVA1.8: Bad intersection");

        //BVA1.13: ray starts at the casing
        assertNull(cylinder.findIntersections(new Ray(new Point(0, 0, 1), vec1)), "BVA1.8: Bad intersection");

        //BVA1.14: ray is outside the cylinder
        assertNull(cylinder.findIntersections(new Ray(new Point(0, 0, 2), vec1)), "BVA1.8: Bad intersection");

        //BVA1.15: ray is outside cylinder but the vector between the heads is orthogonal to the axis ray
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 0, 2), vec1)), "BVA1.8: Bad intersection");


        //Group 2 - ray starts at the base with the head of the axis ray (not parallel - see group 1)

        //BVA2.1: ray starts at the head of the axis ray and goes outside
        assertNull(cylinder.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(-1, 0, 1))), "BVA1.8: Bad intersection");

        //BVA2.2: ray starts at the head of the axis ray and orthogonal
        assertNull(cylinder.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(0, 0, 1))), "BVA1.8: Bad intersection");

        //BVA2.3: ray starts at the head of the axis ray and intersects with the perimeter of the other base
        assertEquals(List.of(new Point(1, 0, 1)), cylinder.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(2, 0, 1))), "BVA1.5: Bad intersection");

        //BVA2.4: ray starts at the head of the axis ray and intersects with the other base
        assertEquals(List.of(new Point(1, 0, 0.5)), cylinder.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(2, 0, 0.5))), "BVA1.5: Bad intersection");

        //BVA2.5: ray starts at the head of the axis ray and intersects with the casing
        assertEquals(List.of(new Point(0, 0, 1)), cylinder.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 0, 1))), "BVA1.5: Bad intersection");


        //BVA2.6: ray goes outside
        assertNull(cylinder.findIntersections(new Ray(new Point(-1, 0, 0.5), new Vector(-1, 0, 1))), "BVA1.8: Bad intersection");

        //BVA2.7: ray orthogonal to the axis ray
        assertNull(cylinder.findIntersections(new Ray(new Point(-1, 0, 0.5), new Vector(0, 0, 1))), "BVA1.8: Bad intersection");

        //BVA2.8: ray intersects with the perimeter of the other base
        assertEquals(List.of(new Point(1, 0, 1)), cylinder.findIntersections(new Ray(new Point(-1, 0, 0.5), new Vector(2, 0, 0.5))), "BVA1.5: Bad intersection");

        //BVA2.9: ray intersects with the other base
        assertEquals(List.of(new Point(1, 0, 0.5)), cylinder.findIntersections(new Ray(new Point(-1, 0, -0.5), new Vector(2, 0, 1))), "BVA1.5: Bad intersection");

        //BVA2.10: ray intersects with the casing
        assertEquals(List.of(new Point(0, 0, 1)), cylinder.findIntersections(new Ray(new Point(-1, 0, 0.5), new Vector(2, 0, 1))), "BVA1.5: Bad intersection");

        //BVA2.11: ray starts at the perimeter and goes outside
        assertNull(cylinder.findIntersections(new Ray(new Point(-1, 0, 1), new Vector(-1, 0, 1))), "BVA1.8: Bad intersection");

        //BVA2.12: ray starts at the perimeter and orthogonal to the axis ray
        assertNull(cylinder.findIntersections(new Ray(new Point(-1, 0, 1), new Vector(0, 0, 1))), "BVA1.8: Bad intersection");

        //BVA2.13: ray starts at the perimeter and intersects with the perimeter of the other base
        assertEquals(List.of(new Point(1, 0, 1)), cylinder.findIntersections(new Ray(new Point(-1, 0, -1), new Vector(1, 0, 1))), "BVA1.5: Bad intersection");

        //BVA2.14: ray starts at the perimeter and intersects with the other base
        assertEquals(List.of(new Point(1, 0, 0.5)), cylinder.findIntersections(new Ray(new Point(-1, 0, -1), new Vector(2, 0, 1.5))), "BVA1.5: Bad intersection");

        //BVA2.15: ray starts at the perimeter and intersects with the casing
        assertEquals(List.of(new Point(0, 0, 1)), cylinder.findIntersections(new Ray(new Point(-1, 0, -1), new Vector(1, 0, 2))), "BVA1.5: Bad intersection");

        //BVA2.16: ray starts at the perimeter and goes through the head of the axis ray
        assertNull(cylinder.findIntersections(new Ray(new Point(-1, 0, 1), new Vector(0, 0, -1))), "BVA1.8: Bad intersection");

        //BVA2.17: ray starts at the base and goes through the head of the axis ray
        assertNull(cylinder.findIntersections(new Ray(new Point(-1, 0, 0.5), new Vector(0, 0, -1))), "BVA1.8: Bad intersection");


        //Group 2* - ray starts at the other base (the one that doesn't contain the head of the axis ray) (not parallel - see group 1)

        //BVA2.1: ray starts at center and goes outside
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 1))), "BVA1.8: Bad intersection");

        //BVA2.2: ray starts at the center and orthogonal
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 0, 1))), "BVA1.8: Bad intersection");

        //BVA2.3: ray starts at the center and intersects with the perimeter of the other base
        assertEquals(List.of(new Point(-1, 0, 1)), cylinder.findIntersections(new Ray(new Point(1, 0, 0), new Vector(-2, 0, 1))), "BVA1.5: Bad intersection");

        //BVA2.4: ray starts at the center and intersects with the other base
        assertEquals(List.of(new Point(-1, 0, 0.5)), cylinder.findIntersections(new Ray(new Point(1, 0, 0), new Vector(-2, 0, 0.5))), "BVA1.5: Bad intersection");

        //BVA2.5: ray starts at the center and intersects with the casing
        assertEquals(List.of(new Point(0, 0, 1)), cylinder.findIntersections(new Ray(new Point(1, 0, 0), new Vector(-1, 0, 1))), "BVA1.5: Bad intersection");


        //BVA2.6: ray goes outside
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 0, 0.5), new Vector(1, 0, 1))), "BVA1.8: Bad intersection");

        //BVA2.7: ray orthogonal to the axis ray
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 0, 0.5), new Vector(0, 0, 1))), "BVA1.8: Bad intersection");

        //BVA2.8: ray intersects with the perimeter of the other base
        assertEquals(List.of(new Point(-1, 0, 1)), cylinder.findIntersections(new Ray(new Point(1, 0, 0.5), new Vector(-2, 0, 0.5))), "BVA1.5: Bad intersection");

        //BVA2.9: ray intersects with the other base
        assertEquals(List.of(new Point(-1, 0, 0.5)), cylinder.findIntersections(new Ray(new Point(1, 0, -0.5), new Vector(-2, 0, 1))), "BVA1.5: Bad intersection");

        //BVA2.10: ray intersects with the casing
        assertEquals(List.of(new Point(0, 0, 1)), cylinder.findIntersections(new Ray(new Point(1, 0, 0.5), new Vector(-2, 0, 1))), "BVA1.5: Bad intersection");


        //BVA2.11: ray starts at the perimeter and goes outside
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 0, 1), new Vector(1, 0, 1))), "BVA1.8: Bad intersection");

        //BVA2.12: ray starts at the perimeter and orthogonal to the axis ray
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, 1, 1))), "BVA1.8: Bad intersection");

        //BVA2.13: ray starts at the perimeter and intersects with the perimeter of the other base
        assertEquals(List.of(new Point(-1, 0, -1)), cylinder.findIntersections(new Ray(new Point(1, 0, 1), new Vector(-1, 0, -1))), "BVA1.5: Bad intersection");

        //BVA2.14: ray starts at the perimeter and intersects with the other base
        assertEquals(List.of(new Point(-1, 0, -0.5)), cylinder.findIntersections(new Ray(new Point(1, 0, 1), new Vector(-2, 0, -1.5))), "BVA1.5: Bad intersection");

        //BVA2.15: ray starts at the perimeter and intersects with the casing
        assertEquals(List.of(new Point(0, 1, 0)), cylinder.findIntersections(new Ray(new Point(-1, 0, -1), new Vector(-1, 1, 1))), "BVA1.5: Bad intersection");

        //BVA2.16: ray starts at the perimeter and goes through center
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, 0, -1))), "BVA1.8: Bad intersection");

        //BVA2.17: ray starts at the base and goes through the head of the axis ray
        assertNull(cylinder.findIntersections(new Ray(new Point(1, 0, 0.5), new Vector(0, 0, -1))), "BVA1.8: Bad intersection");


        //Group 3 - ray intersect with the axis ray

        vec1 = new Vector(2,0,-1);
        //BVA3.1: rays starts outside the cylinder and goes through the bases
        assertEquals(List.of(new Point(-1, 0, 0.5), new Point(1,0,-0.5)), cylinder.findIntersections(new Ray(new Point(-3, 0, 1), vec1)), "BVA1.5: Bad intersection");

        //BVA3.2: ray starts after the first base before the axis ray
        assertEquals(List.of(new Point(1,0,-0.5)), cylinder.findIntersections(new Ray(new Point(-0.5, 0, 0.25), vec1)), "BVA1.5: Bad intersection");

        //BV3.3: ray starts after the first base after the axis ray
        assertEquals(List.of(new Point(1,0,-0.5)), cylinder.findIntersections(new Ray(new Point(0.5, 0, -0.25), vec1)), "BVA1.5: Bad intersection");

        //BV3.4: ray starts after the second base
        assertNull(cylinder.findIntersections(new Ray(new Point(20, 0, -0.25), vec1)), "BVA1.5: Bad intersection");

        vec1 = new Vector(2,0,-2);
        //BVA3.5: rays starts outside the cylinder and goes through the perimeters
        assertEquals(List.of(new Point(-1, 0, 1), new Point(1,0,-1)), cylinder.findIntersections(new Ray(new Point(-2, 0, 2), vec1)), "BVA1.5: Bad intersection");

        //BVA3.6: ray starts after the first base before the axis ray and goes through the second base perimetre
        assertEquals(List.of(new Point(1,0,-1)), cylinder.findIntersections(new Ray(new Point(-0.5, 0, 0.5), vec1)), "BVA1.5: Bad intersection");

        //BVA3.7: ray starts after the first base before the axis ray and goes through the second base perimeter
        assertEquals(List.of(new Point(1,0,1)), cylinder.findIntersections(new Ray(new Point(0.5, 0, -0.25), vec1)), "BVA1.5: Bad intersection");

        //BVA3.8: ray starts after the second base
        assertNull(cylinder.findIntersections(new Ray(new Point(20, 0, -0.25), vec1)), "BVA1.5: Bad intersection");

        //Group 4 - ray starts inside the cylinder

        //BVA4.1: ray starts at the axis ray and goes through the perimeter of the base with the head of the axis ray
        assertEquals(List.of(new Point(-1, 0, 1), cylinder.findIntersections(new Ray(new Point(-0.5, 0, 0), new Vector(-0.5,0,2))), "BVA1.5: Bad intersection"));

        //BVA4.2: ray starts at the axis ray and goes through the perimeter of the base without the head of the axis ray
        assertEquals(List.of(new Point(1, 0, 1), cylinder.findIntersections(new Ray(new Point(1.5, 0, 0), new Vector(0.5,0,2))), "BVA1.5: Bad intersection");

        //BVA4.3: ray starts inside the cylinder and intersects with the head of the axis ray
        assertEquals(List.of(new Point(-1,0,0)),cylinder.findIntersections(new Ray(new Point(-0.5,0,0.5),new Vector(-1,0,-1))),"BVA1.5: Bad intersection");

        //BVA4.4: ray starts inside the cylinder and intersects the perimeter of the base with the head of the axis ray
        assertEquals(List.of(new Point(-1,0,1)),cylinder.findIntersections(new Ray(new Point(-0.5,0.1,0.5),new Vector(-1,-0.2,1))),"BVA1.5: Bad intersection");

        //BVA4.5: ray starts inside the cylinder and intersects the perimeter of the other base
        assertEquals(List.of(new Point(1,0,1)),cylinder.findIntersections(new Ray(new Point(0.5,0.1,0.5),new Vector(1,-0.2,1))),"BVA1.5: Bad intersection");

        //BVA4.6: ray starts inside the cylinder and intersects the center of the other base
        assertEquals(List.of(new Point(1,0,0)),cylinder.findIntersections(new Ray(new Point(0.5,0.1,0.5),new Vector(1,-0.2,-1))),"BVA1.5: Bad intersection");

        //Group 5 - ray is tangent to the perimeters - base with the head of the axis ray
        vec1 = new Vector(1,0,1);
        //BVA5.1: ray starts before the axis ray
        assertNull(cylinder.findIntersections(new Ray(new Point(-2, 0, 0), vec1)), "BVA1.5: Bad intersection");

        //BVA5.2: backs of the rays intersects
        assertNull(cylinder.findIntersections(new Ray(new Point(-1.5, 0, 0.5), vec1)), "BVA1.5: Bad intersection");

        //BVA5.3: ray intersects with the back of the axis ray
        assertNull(cylinder.findIntersections(new Ray(new Point(-3, 0, -1), vec1)), "BVA1.5: Bad intersection");

        //BVA5.4:simple tangent ray
        assertNull(cylinder.findIntersections(new Ray(new Point(-2,-2,-2), new Vector(1,2,3))), "BVA1.5: Bad intersection");

        //Group 5* - ray is tangent to the perimeters - base without the head of the axis ray
        vec1 = new Vector(-1,0,1);
        //BVA5.1: ray starts before the axis ray
        assertNull(cylinder.findIntersections(new Ray(new Point(2, 0, 0), vec1)), "BVA1.5: Bad intersection");

        //BVA5.2: backs of the rays intersects
        assertNull(cylinder.findIntersections(new Ray(new Point(1.5, 0, 0.5), vec1)), "BVA1.5: Bad intersection");

        //BVA5.3: ray intersects with the back of the axis ray
        assertNull(cylinder.findIntersections(new Ray(new Point(3, 0, -1), vec1)), "BVA1.5: Bad intersection");

        //BVA5.4:simple tangent ray
        assertNull(cylinder.findIntersections(new Ray(new Point(2,2,-2), new Vector(-1,-2,3))), "BVA1.5: Bad intersection");


    }

}*/
