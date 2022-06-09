package geometries;

import primitives.*;

import java.util.List;
import java.util.Map;

import static java.lang.Math.*;
import static primitives.Util.alignZero;

/**
 * class that represents a sphere and implements the interface Geometry
 */
public class Sphere extends Geometry {
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
        this.boundary=calcBoundary();
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector vec;
        try {
            vec = this.center.subtract(ray.getP0());
        } catch (IllegalArgumentException ignore) {
            return alignZero(radius - maxDistance) > 0 ? null : List.of(new GeoPoint(this, ray.getPoint(this.radius)));
        }

        // Here we calculate the projection of the vector formed by the center of the
        // circle and the head of the ray. Then we calculate the distance between then center
        // and the projection and the distance between the projection to the intersections points.
        // The idea is that the projection is the middle of the two intersection points
        // so all we have to do is to add and subtract the distance to the intersection points
        double tm = alignZero(vec.dotProduct(ray.getDir()));
        double dSqr = vec.lengthSquared() - tm * tm;
        double thSqr = this.radiusSqr - dSqr;
        // If the ray is tangent to the sphere or doesn't intersect the sphere at all return null
        if (alignZero(thSqr) <= 0) return null;

        double th = sqrt(thSqr);
        double t2 = alignZero(tm + th);
        if (t2 <= 0) return null;
        double t1 = alignZero(tm - th);
        if (t1 - maxDistance > 0) return null;
        // If only one is greater than 0 then the ray intersects the sphere only once
        if (t2 - maxDistance > 0)
            return t1 <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t1)));
        return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2))) : List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
    }

    @Override
    public int[][] calcBoundary() {
        double x = center.getX();
        double y = center.getY();
        double z = center.getZ();

        return new int[][]{{(int) (x - radius), (int) Math.ceil(x + radius)},
                {(int) (y - radius), (int) Math.ceil(y + radius)},
                {(int) (z - radius), (int) Math.ceil(z + radius)}};
    }
}