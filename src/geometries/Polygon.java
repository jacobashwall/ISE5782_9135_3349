package geometries;

import primitives.Point;
import primitives.Vector;

import java.util.List;

public class Polygon implements Geometry{
    protected List<Point> vertices;
    protected Plane plane;

    public Polygon(List<Point> vertices, Plane plane) {
        this.vertices = vertices;
        this.plane = plane;
    }



    public Plane getPlane() {
        return new Plane(plane.getQ0(),plane.getNormal());
    }

    @Override
    public Vector getNormal(Point pnt) {
        return null;
    }
}
