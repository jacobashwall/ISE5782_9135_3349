package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * class that represents a plane and implements the interface Geometry
 */
public class Plane implements Geometry {
    //private fields
    private Point q0;
    private Vector normal;

    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    public Point getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point pnt) {
        return normal;
    }
}
