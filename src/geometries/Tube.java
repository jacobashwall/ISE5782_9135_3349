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
        double t= axisRay.getDir().dotProduct(pnt.subtract(axisRay.getP0()));
        Point o = axisRay.getP0();
        if (t!=0){
            o=o.add(axisRay.getDir().scale(t));
        }
        return (pnt.subtract(o)).normalize();
    }
}
