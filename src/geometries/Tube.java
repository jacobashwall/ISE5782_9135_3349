package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

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
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point pnt) {
        //t=v*(p-p0)
        //O=p0+t*v
        //n = normalize(P - O)
        Point p0 = axisRay.getP0();
        Vector dir = axisRay.getDir();

        double t= dir.dotProduct(pnt.subtract(p0));
        Point o = isZero(t) ? p0 : p0.add(dir.scale(t));
        return (pnt.subtract(o)).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray){return null;}
}
