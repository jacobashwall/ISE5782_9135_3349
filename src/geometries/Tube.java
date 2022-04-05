package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * Class that represents a tube and implements the interface Geometry
 */
public class Tube extends Geometry {
    //Fields
    protected final Ray axisRay;
    protected final double radius;

    /**
     * Ctor that gets a vector for the axis ray of the tube and double for the radius
     *
     * @param axisRay Direction of the tube
     * @param radius  Radius of the tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * axisRay getter
     *
     * @return axisRay of the tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * radius getter
     *
     * @return radius of the tube
     */
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
        double t = axisRay.getDir().dotProduct(pnt.subtract(axisRay.getP0()));
        return pnt.subtract(axisRay.getPoint(t)).normalize();
    }

    /*@Override
    public List<Point> findIntersections(Ray ray) {

        return null;
    }*/
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {

        return null;
    }
}
