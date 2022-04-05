package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static primitives.Util.alignZero;

/**
 * Class that represents a triangle and extends the class polygon
 */
public class Triangle extends Polygon {
    public Triangle(Point... vertices) {
        super(vertices);
    }
    public static Triangle ReadXmlTriangle(Map<String, String> triangleAttributes) {

        String[] P0coordinates = triangleAttributes
                .get("p0").split("\\s+");

        Point p0 = new Point(Double.valueOf(P0coordinates[0]),
                Double.valueOf(P0coordinates[1]),
                Double.valueOf(P0coordinates[2]));

        String[] P1coordinates = triangleAttributes
                .get("p1").split("\\s+");

        Point p1 = new Point(Double.valueOf(P1coordinates[0]),
                Double.valueOf(P1coordinates[1]),
                Double.valueOf(P1coordinates[2]));

        String[] P2coordinates = triangleAttributes
                .get("p2").split("\\s+");

        Point p2 = new Point(Double.valueOf(P2coordinates[0]),
                Double.valueOf(P2coordinates[1]),
                Double.valueOf(P2coordinates[2]));
       return new Triangle(p0,p1,p2);
    }

    /*private Point readP0(Map<String, String> triangleAttributes) {

        String[] coordinates = triangleAttributes
                .get("P0").split("\\s+");

       return new Point(Double.valueOf(coordinates[0]),
                Double.valueOf(coordinates[1]),
                Double.valueOf(coordinates[2]));

    }
    private Point readP1(Map<String, String> triangleAttributes) {

        String[] coordinates = triangleAttributes
                .get("P1").split("\\s+");

        return new Point(Double.valueOf(coordinates[0]),
                Double.valueOf(coordinates[1]),
                Double.valueOf(coordinates[2]));

    }
    private Point readP2(Map<String, String> triangleAttributes) {

        String[] coordinates = triangleAttributes
                .get("P0").split("\\s+");

        return new Point(Double.valueOf(coordinates[0]),
                Double.valueOf(coordinates[1]),
                Double.valueOf(coordinates[2]));

    }*/

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }

   /* @Override
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
    }*/

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        var intersections = this.plane.findGeoIntersections(ray);
        if (intersections == null)//checks if there is an intersection with the plane of the triangle
            return null;
        List<GeoPoint> geoIntersection= new LinkedList<>();
        for (var geoPoint : intersections)
            geoIntersection.add(new GeoPoint(this,geoPoint.point));
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
        return geoIntersection;
    }

}
