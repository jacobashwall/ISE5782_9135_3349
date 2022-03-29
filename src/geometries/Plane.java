package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * Class that represents a plane and implements the interface Geometry
 */
public class Plane implements Geometry {
    //private fields
    private final Point q0;
    private final Vector normal;

    /**
     * ctor of parameters
     *
     * @param q0 representing point of the plane
     * @param normal normal to the plane
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * Constructor for creating a plane from 3 points: x,y and z
     *
     * @param x first point
     * @param y second point
     * @param z third point
     */
    public Plane(Point x, Point y, Point z) {
        q0 = x;
        //v1 = P2 - P1
        //v2 = P3 - P1
        //normal = normalize( v1 x v2 )
        Vector v1 = y.subtract(x);
        Vector v2 = z.subtract(x);
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
     *
     * @return private field q0
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * normal field getter
     *
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
    public List<Point> findIntersections(Ray ray) {
        if (ray.getP0().equals(this.q0)) { // the ray starts at the plane's reference point
            return null;
        }
        double numerator = this.normal.dotProduct(q0.subtract(ray.getP0()));
        if (isZero(numerator)) {// p0 is on the plane
            return null;
        }
        double denominator = this.normal.dotProduct(ray.getDir());
        if (isZero(denominator)) { // ray parallel to the plane- the ray direction orthogonal to the normal
            return null;
        }
        double t = alignZero(numerator / denominator);
        if (t > 0) {
            Point p = ray.getPoint(t);
            return List.of(p);
        } else {// if the Ray is after the plane (do not intersect)
            return null;
        }
    }
}
