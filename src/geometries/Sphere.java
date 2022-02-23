package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * class that represents a sphere and implements the interface Geometry
 */
public class Sphere implements Geometry{
    private Point center;
    private double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

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
        return null;
    }
}
