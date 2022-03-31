package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class that represents a triangle and extends the class polygon
 */
public class Triangle extends Polygon {
    public Triangle(Point... vertices) {
        super(vertices);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        var result = this.plane.findIntersections(ray);
        if (result == null)//checks if there is an intersection with the plane of the triangle
            return null;

        Point p0 = ray.getP0();
        Vector dir = ray.getDir();
        //we will check if the point is inside or outside the triangle
        Vector v1 = this.vertices.get(0).subtract(p0);
        Vector v2 = this.vertices.get(1).subtract(p0);
        Vector n1 = (v1.crossProduct(v2)).normalize();
        double sign1 = alignZero(dir.dotProduct(n1));
        if (sign1 == 0) return null;

        Vector v3 = this.vertices.get(2).subtract(p0);
        Vector n2 = (v2.crossProduct(v3)).normalize();
        double sign2 = alignZero(dir.dotProduct(n2));
        if (sign1 * sign2 <= 0) return null;

        Vector n3 = (v3.crossProduct(v1)).normalize();
        double sign3 = alignZero(dir.dotProduct(n3));
        if (sign1 * sign3 <= 0) return null;

        //if all signs are equal (+/-) the point is inside the triangle
        return result;
    }

}
