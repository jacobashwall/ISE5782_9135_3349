package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

import static primitives.Util.*;

/**
 * class that represents a plane and implements the interface Geometry
 */
public class Plane implements Geometry {
    //private fields
    private Point q0;
    private Vector normal;

    /**
     * ctor of parameters
     * @param q0 representing point of the plane
     * @param normal normal to the plane
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * ctor that build a plane out of three points
     * @param x point x
     * @param y point u
     * @param z point z
     */
    public Plane(Point x, Point y, Point z){
        q0 = x;
        //v1 = P2 - P1
        //v2 = P3 - P1
        //normal = normalize( v1 x v2 )
        Vector v1 =y.subtract(x);
        Vector v2 =z.subtract(x);
        normal = (v1.crossProduct(v2)).normalize();
    }


    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    /**
     * q0 getter
     * @return private field q0
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * normal field getter
     * @return private field normal
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point pnt) {
        return normal;
    }

    @Override
    public List<Point> findIntsersections(Ray ray){return null;}
}
