package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * class that represents a tube and implements the interface Geometry
 */
public class Tube implements Geometry{
    protected Ray axisRay;
    protected double radius;

    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    public Ray getAxisRay() {
        return new Ray(axisRay.getP0(),axisRay.getDir());
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point pnt) {
        return null;
    }
}
