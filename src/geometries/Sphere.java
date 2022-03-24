package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.*;

/**
 * class that represents a sphere and implements the interface Geometry
 */
public class Sphere implements Geometry {
    //private fields
    private final Point center;
    private final double radius;

    /**
     * Ctor that get a point for the center of the sphere and double for the radius
     *
     * @param center center of the circle
     * @param radius radius of the circle
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
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

        // In case the center coincide with the head of the ray
        if(ray.getP0().equals(this.center)){
            return List.of(ray.getPoint(this.radius));
        }

        // Here we calculate the projection of the vector formed by the center of the
        // circle and the head of the ray. Then we calculate the distance between then center
        // and the projection and the distance between the projection to the intersections points.
        //The idea is that the projection is the middle of the two intersection points
        // so all we have to do is to add and subtract the distance to the intersection points
        Vector vec = this.getCenter().subtract(ray.getP0());
        double tm = Util.alignZero(vec.dotProduct(ray.getDir()));
        double d = sqrt(vec.lengthSquared()-tm*tm);
        double th = sqrt(this.radius*this.radius-d*d);
        double t1 = Util.alignZero(tm-th);
        double t2 = Util.alignZero(tm+th);


        // If the ray is tangent to the sphere return null
        if(Util.isZero(d-this.radius)){
            return null;
        }
        // The ray doesn't intersect the sphere at all
        else{
            if(d>this.radius){
                return null;
            }
        }


        // If both t1 and t2 are bigger than 0 then there are two intersections
        if(t1 > 0 && t2 >0){
            return List.of(ray.getPoint(t1),ray.getPoint(t2));
        }

        // If only one is greater than 0 then the ray intersects the sphere only once

        if(t1 <= 0 && t2 >0){
            return List.of(ray.getPoint(t2));
        }

        if(t1 > 0 && t2 <= 0){
            return List.of(ray.getPoint(t1));
        }

        //return null for all the other cases
        return null;

    }
}