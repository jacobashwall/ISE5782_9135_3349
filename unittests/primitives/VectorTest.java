package primitives;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Vector class
 */
class VectorTest {
    /**
     * Test method for{@link primitives.Vector#add(Vector)}
     */
    @Test
    void add() {
        //============ Equivalence Partitions Tests ==============//
        Vector vec1 = new Vector(1, 2, 3);
        Vector vec2 = new Vector(2, 3, 4);
        //TC: Test that vector addition is proper. We should get a new vector from the two vectors.
        assertEquals(vec1.add(vec2), new Vector(3, 5, 7), "There is a problem with vector addition");

        //============ Boundary Partitions Tests ==============//
        //TC: Test that addition of a vector by its opposite throws an error
        assertThrows(IllegalArgumentException.class, () -> vec1.add(vec1.scale(-1)), "Add fails to throw an exception for opposite vectors");
    }

    /**
     * Test method for{@link primitives.Vector#subtract(Vector)}
     */
    @Test
    void subtract() {
        //============ Equivalence Partitions Tests ==============//
        Vector vec1 = new Vector(1, 2, 3);
        Vector vec2 = new Vector(2, 3, 4);
        //TC: Test that vector subtraction is proper. We should get a new vector from the two vectors.
        assertEquals(vec2.subtract(vec1), new Vector(1, 1, 1), "There is a problem with vector subtraction");

        //============ Boundary Partitions Tests ==============//
        //TC: Test that subtraction of a vector by itself throws an error
        assertThrows(IllegalArgumentException.class, () -> vec1.subtract(vec1), "Subtract fails to throw an exception for equal vectors");

    }

    /**
     * Test method for{@link primitives.Vector#scale(double)}
     */
    @Test
    void scale() {
        //============ Equivalence Partitions Tests ==============//
        Vector vec = new Vector(1, 2, 3);
        //TC: Test that vector scalar multiplication is proper. We should get a new vector multiplied by the double parameter.
        assertEquals(vec.scale(2.5), new Vector(2.5, 5, 7.5), "There is a problem with scale method");

        //============ Boundary Tests ==============//
        //TC: Test that checks that scalar multiplication by zero throws an error
        assertThrows(IllegalArgumentException.class, () -> vec.scale(0), "Scale fails to throw an exception for multiplying by 0");

    }

    /**
     * Test method for{@link primitives.Vector#dotProduct(Vector)}
     */
    @Test
    void dotProduct() {
        //============ Equivalence Partitions Tests ==============//
        //acute angle
        Vector vec1 = new Vector(1, 2, 3);
        Vector vec2 = new Vector(2, 3, 4);
        //TC1: Test that vector dotProduct is proper. We should get a positive number.
        assertEquals(vec1.dotProduct(vec2), 20, "TC1: There is a problem with dotProduct method - acute angle");
        //obtuse angle
        Vector vec3 = new Vector(-1, -2, -5);
        //TC: Test that vector dotProduct is proper. We should get a positive number.
        assertEquals(vec1.dotProduct(vec3), -20, "TC2: There is a problem with dotProduct method - obtuse angle");

    }

    /**
     * Test method for{@link primitives.Vector#crossProduct(Vector)}
     */
    @Test
    void crossProduct() {
        //============ Equivalence Partitions Tests ==============//
        Vector vec1 = new Vector(1, 2, 3);
        Vector vec2 = new Vector(2, 3, 4);
        Vector vec3 = new Vector(-1, 2, -1);
        //TC1: Test that vector crossProduct is proper. We should get a new vector multiplied by the double parameter.
        assertEquals(vec1.crossProduct(vec2), vec3, "TC1: There is a problem with crossProduct method");
        //TC2:Test that the crossProduct produces an orthogonal vector.
        assertTrue(vec1.dotProduct(vec3) == 0 && vec2.dotProduct(vec3) == 0, "TC2: Cross product failed to give orthogonal vector.");

        //============ Boundary Tests ==============//
        //TC: Test that exception is thrown for cross product of parallel vectors.
        Vector vec4 = new Vector(2, 4, 6);
        assertThrows(IllegalArgumentException.class, () -> vec1.crossProduct(vec4), "TC4: Cross Product for parallel vectors does not throw an exception");

        //TC: Test that exception is thrown for cross product of opposite vectors.
        Vector vec5 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> vec1.crossProduct(vec4), "TC5: Cross Product for opposite vectors does not throw an exception");

    }

    /**
     * Test method for{@link Vector#lengthSquared()}
     */
    @Test
    void lengthSquared() {
        //============ Equivalence Partitions Tests ==============//
        Vector vec1 = new Vector(-1, 2, -3);
        //TC1: Test that checks the calculation
        assertEquals(vec1.lengthSquared(), 14, 0.000001, "TC1: lengthSquared doesn't work properly");
    }

    /**
     * Test method for{@link Vector#length()}
     */
    @Test
    void length() {
        //============ Equivalence Partitions Tests ==============//
        Vector vec1 = new Vector(-1, 2, -3);
        //TC1: Test that checks the calculation
        assertEquals(vec1.length(), sqrt(14), 0.000001, "TC1: length doesn't work properly");
    }

    /**
     * Test method for{@link Vector#normalize()}
     */
    @Test
    void normalize() {
        //============ Equivalence Partitions Tests ==============//
        Vector vec = new Vector(1, 1, 1);
        //TC1: Test that checks the calculation
        assertEquals(vec.normalize(), vec.scale(1 / sqrt(3)), "TC1: normalize doesn't work properly");
    }
}