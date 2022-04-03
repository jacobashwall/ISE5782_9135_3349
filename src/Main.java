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
        Vector v1=new Vector(1,0,0);
        System.out.println(v1.rotateVector(new Vector(0,1,0),90));
    }
}

