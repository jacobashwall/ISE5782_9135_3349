package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.alignZero;

/**
 * class that represents a sphere and implements the interface Geometry
 */
public class Sphere implements Geometry {
    //private fields
    private final Point center;
    private final double radius;
    private final double radiusSqr;

    /**
     * Ctor that get a point for the center of the sphere and double for the radius
     *
     * @param center center of the circle
     * @param radius radius of the circle
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
        this.radiusSqr = radius * radius;
    }

    /**
     * center getter
     *
     * @return center of the circle
     */
    public Point getCenter() {
        return center;
    }

    /**
     * radius getter
     *
     * @return radius of the circle
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point pnt) {
        //n = normalize(P - O)
        return (pnt.subtract(center)).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector vec;
        try {
            vec = this.center.subtract(ray.getP0());
        } catch (IllegalArgumentException ignore) {
            return List.of(ray.getPoint(this.radius));
        }

        // Here we calculate the projection of the vector formed by the center of the
        // circle and the head of the ray. Then we calculate the distance between then center
        // and the projection and the distance between the projection to the intersections points.
        // The idea is that the projection is the middle of the two intersection points
        // so all we have to do is to add and subtract the distance to the intersection points
        double tm = alignZero(vec.dotProduct(ray.getDir()));
        double dSqr = alignZero(vec.lengthSquared() - tm * tm);
        double thSqr = alignZero(this.radiusSqr - dSqr);
        // If the ray is tangent to the sphere or doesn't intersect the sphere at all return null
        if (alignZero(thSqr) <= 0) return null;

        double th = alignZero(sqrt(thSqr));
        double t2 = alignZero(tm + th);
        if (t2 <= 0) return null;
        double t1 = alignZero(tm - th);
        // If only one is greater than 0 then the ray intersects the sphere only once
        return t1 <= 0 ? List.of(ray.getPoint(t2)) : List.of(ray.getPoint(t1), ray.getPoint(t2));
    }
}