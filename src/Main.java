import geometries.Polygon;
import primitives.*;

import java.util.List;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static primitives.Util.*;
/**
 * Students:
 * Jacob Shalev 209783349 yakovash70@gmail.com
 * Jonathan Dahary 324249135 yonatan.dahary@gmail.com
 */

/**
 * Test program for the 1st stage
 *
 * @author Dan Zilberstein
 */
public final class Main {

    /**
     * Main program to tests initial functionality of the 1st stage
     *
     * @param args irrelevant here
     */
    public static void main(String[] args) {
        Polygon trn = new Polygon(new Point(-1, 1, 0), new Point(1, 0, 0), new Point(-1, -1, 0));
        //============ Equivalence Partitions Tests ==============//
        //TC: The ray intersect with the Polygon's plane inside the Polygon
        Ray ray1 = new Ray(new Point(0, 0, 1), new Vector(-0.5, 0, -1));
         trn.findIntersections(ray1);
    }
}

