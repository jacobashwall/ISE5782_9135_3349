package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        if (this.plane.findIntersections(ray) == null)//checks if there is an intersection with the plane of the triangle
            return null;
        //we will check if the point is inside or outside the triangle
        Vector v1 = this.vertices.get(0).subtract(ray.getP0());
        Vector v2 = this.vertices.get(1).subtract(ray.getP0());
        Vector v3 = this.vertices.get(2).subtract(ray.getP0());
        Vector n1 = (v1.crossProduct(v2)).normalize();
        Vector n2 = (v2.crossProduct(v3)).normalize();
        Vector n3 = (v3.crossProduct(v1)).normalize();
        double sign1 = ray.getDir().dotProduct(n1);
        double sign2 = ray.getDir().dotProduct(n2);
        double sign3 = ray.getDir().dotProduct(n3);
        //if all signs are equal (+/-) the point is inside the triangle
        if (((sign1 > 0) && (sign2 > 0) && (sign3 > 0)) || ((sign1 < 0) && (sign2 < 0) && (sign3 < 0)))//if the point is inside the triangle
            return this.plane.findIntersections(ray);
        else //if the point is not inside the triangle (1 sign or more is zero or all signs are not equal)
            return null;
    }

}
