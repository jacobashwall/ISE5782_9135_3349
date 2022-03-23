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
    private Point center;
    private double radius;

    /**
     * parameter ctor
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

        //Head of the ray and center of the circle coincide
        if (ray.getP0().equals(this.getCenter())) {
            return List.of(ray.getPoint(this.getRadius()));
        }

        //Head of the ray is inside or on the sphere and goes through the center
        boolean b1 = ray.getP0().subtract(this.getCenter()).length() < this.getRadius() && ray.getDir().equals(this.getCenter().subtract(ray.getP0()).normalize());
        boolean b2 = ray.getP0().subtract(this.getCenter()).length() < this.getRadius() && ray.getDir().equals(this.getCenter().subtract(ray.getP0()).normalize().scale(-1));
        if (b1 || b2) {
            double t = getCenter().subtract(ray.getP0()).length();
            if (b1) {
                return List.of(ray.getPoint(t + this.getRadius()));
            } else {
                return List.of(ray.getPoint(-t + this.getRadius()));
            }

        }


        //Head of the ray is outside the sphere and goes through the center
        boolean b3 = ray.getP0().subtract(this.getCenter()).length() > this.getRadius() && ray.getDir().equals(this.getCenter().subtract(ray.getP0()).normalize());
        boolean b4 = ray.getP0().subtract(this.getCenter()).length() > this.getRadius() && ray.getDir().equals(this.getCenter().subtract(ray.getP0()).normalize().scale(-1));
        double t = getCenter().subtract(ray.getP0()).length();
        if (b3 || b4) {
            if (b3) {
                return List.of(ray.getPoint(t - this.getRadius()), ray.getPoint(t + this.getRadius()));
            } else {
                return null;
            }

        }

        //Head of the ray is outside the sphere and goes through the center
        boolean b5 = Util.isZero(ray.getP0().subtract(this.getCenter()).length() - this.getRadius()) && ray.getDir().equals(this.getCenter().subtract(ray.getP0()).normalize());
        if (b5) {
            return List.of(ray.getPoint(2 * this.getRadius()));
        }

        //Head of the ray is outside the sphere and the ray doesn't intersect the sphere
        if (ray.getP0().subtract(this.getCenter()).length() > this.getRadius()) {
            double projection = ray.getDir().dotProduct(this.getCenter().subtract(ray.getP0()));
            Point shortest = ray.getPoint(abs(projection));
            //The ray is tangent to the sphere
            if (Util.isZero(shortest.subtract(this.getCenter()).length() - this.getRadius())) {
                return null;
            }
            //All the ray is outside the sphere
            else if (shortest.subtract(this.getCenter()).length() > this.getRadius()) {
                return null;
            }
        }

        //Head of the ray is on the sphere
        if (Util.isZero(ray.getP0().subtract(this.getCenter()).length() - this.getRadius())) {
            Vector onSphereVec = this.getCenter().subtract(ray.getP0());
            //Ray is tangent to the sphere
            if (Util.isZero(ray.getDir().dotProduct(onSphereVec))) {
                return null;
            }
            //Ray goes outside the sphere
            else if (ray.getDir().dotProduct(onSphereVec) < 0) {
                return null;
            }
            //Ray goes inside the sphere
            else if (ray.getDir().dotProduct(onSphereVec) > 0) {
                return List.of(ray.getP0().add(ray.getDir().scale(2 * onSphereVec.dotProduct(ray.getDir()))));
            }
        }


        //Head of the ray is inside the sphere and doesn't go through the center
        if (ray.getP0().subtract(this.getCenter()).length() < this.getRadius()) {
            Vector inSphereVec = this.getCenter().subtract(ray.getP0());
            double x = inSphereVec.dotProduct(ray.getDir());
            double y = inSphereVec.length();
            double z = sqrt(y * y - x * x);
            double d = sqrt(this.getRadius() * this.getRadius() - z * z);
            if (Util.isZero(ray.getDir().dotProduct(inSphereVec))) {
                return List.of(ray.getPoint(d));
            } else if (ray.getDir().dotProduct(inSphereVec) < 0) {
                return List.of(ray.getPoint(d - abs(x)));
            } else if (ray.getDir().dotProduct(inSphereVec) > 0) {
                return List.of(ray.getPoint(d + abs(x)));
            }

        }

        //Head of the ray is outside the sphere and the ray goes inside the sphere
        if (ray.getP0().subtract(this.getCenter()).length() > this.getRadius()) {
            Vector outTheSphere = this.getCenter().subtract(ray.getP0());
            double projection = ray.getDir().dotProduct(outTheSphere);
            double dtemp = sqrt(outTheSphere.lengthSquared() - (projection * projection));
            double d2 = sqrt((this.getRadius() * this.getRadius()) - (dtemp * dtemp));
            if (Util.isZero(dtemp - this.getRadius())) {
                return null;
            } else {
                return List.of(ray.getPoint(projection - d2), ray.getPoint(projection + d2));
            }
        }
        return null;
    }
}