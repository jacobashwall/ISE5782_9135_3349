package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

import static primitives.Util.*;

/**
 * Class that represents a plane and implements the interface Geometry
 */
public class Plane extends Geometry {
    //private fields
    private final Point q0;
    private final Vector normal;

    /**
     * ctor of parameters
     *
     * @param q0     representing point of the plane
     * @param normal normal to the plane
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
        this.boundary=calcBoundary();
    }

    /**
     * Constructor for creating a plane from 3 points: x,y and z
     *
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Plane(Point p1, Point p2, Point p3) {
        q0 = p1;
        //v1 = P2 - P1
        //v2 = P3 - P1
        //normal = normalize( v1 x v2 )
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        normal = (v1.crossProduct(v2)).normalize();
        this.boundary=calcBoundary();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return q0.equals(plane.q0) && normal.equals(plane.normal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(q0, normal);
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        double denominator = this.normal.dotProduct(ray.getDir());
        if (isZero(denominator))
            return null; // ray parallel to the plane- the ray direction orthogonal to the normal

        Vector u;
        try {
            u = q0.subtract(ray.getP0());
        } catch (IllegalArgumentException ignore) {
            // the ray starts at the plane's reference point
            return null;
        }

        double t = alignZero(this.normal.dotProduct(u) / denominator);
        return alignZero(t - maxDistance) > 0 || t <= 0 ? null
                : List.of(new GeoPoint(this, ray.getPoint(t)));
    }
    @Override
    public double[][] calcBoundary() {//there is no boundary to infinite geometric entity
        return null;
    }
}
