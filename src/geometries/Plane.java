package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Map;

import static primitives.Util.*;

/**
 * Class that represents a plane and implements the interface Geometry
 */
public class Plane extends Geometry {
    //private fields
    private final Point q0;
    private final Vector normal;

    /**
     * ctor of parameters
     *
     * @param q0     representing point of the plane
     * @param normal normal to the plane
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * Constructor for creating a plane from 3 points: x,y and z
     *
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Plane(Point p1, Point p2, Point p3) {
        q0 = p1;
        //v1 = P2 - P1
        //v2 = P3 - P1
        //normal = normalize( v1 x v2 )
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        normal = (v1.crossProduct(v2)).normalize();
    }

    /**
     * Creates a plane using the list of attributes from the XML file
     *
     * @param planeAttributes list of plane attributes fetched from the xml file
     * @return a plane with the values stated in the plane attributes
     */
    public static Plane ReadXMLPlane(Map<String, String> planeAttributes) {

        String[] p0Attributes = planeAttributes.get("p0").split("\\s+");
        Point p0 = new Point(Double.parseDouble(p0Attributes[0]),
                Double.parseDouble(p0Attributes[1]),
                Double.parseDouble(p0Attributes[2]));
        if (planeAttributes.get("normal") != null) {//using the constructor of normal+ q0
            String[] normalAttributes = planeAttributes.get("normal").split("\\s+");
            Vector normal = new Vector(Double.parseDouble(normalAttributes[0]),
                    Double.parseDouble(normalAttributes[1]),
                    Double.parseDouble(normalAttributes[2]));
            return new Plane(p0, normal);
        } else {//using 3 points constructor
            String[] p1Attributes = planeAttributes.get("p0").split("\\s+");
            Point p1 = new Point(Double.parseDouble(p1Attributes[0]),
                    Double.parseDouble(p1Attributes[1]),
                    Double.parseDouble(p1Attributes[2]));
            String[] p2Attributes = planeAttributes.get("p0").split("\\s+");
            Point p2 = new Point(Double.parseDouble(p2Attributes[0]),
                    Double.parseDouble(p2Attributes[1]),
                    Double.parseDouble(p2Attributes[2]));

            Plane plane = new Plane(p0, p1, p2);
            if (planeAttributes.get("emission") != null) {
                String[] emissionLightAttributes = planeAttributes.get("emission").split("\\s+");
                Color emissionLight = new Color(
                        Double.parseDouble(emissionLightAttributes[0]),
                        Double.parseDouble(emissionLightAttributes[1]),
                        Double.parseDouble(emissionLightAttributes[2]));
                plane.setEmission(emissionLight);
            }
            return plane;
        }


    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    /**
     * q0 getter
     *
     * @return private field q0
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * normal field getter
     *
     * @return private field normal
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point pnt) {
        return normal;
    }

    /* @Override
    public List<Point> findIntersections(Ray ray) {
        double denominator = this.normal.dotProduct(ray.getDir());
        if (isZero(denominator))
            return null; // ray parallel to the plane- the ray direction orthogonal to the normal

        Vector u;
        try {
            u = q0.subtract(ray.getP0());
        } catch (IllegalArgumentException ignore) {
            // the ray starts at the plane's reference point
            return null;
        }

        double t = alignZero(this.normal.dotProduct(u) / denominator);
        return t <= 0 ? null : List.of(ray.getPoint(t));
    }*/

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        double denominator = this.normal.dotProduct(ray.getDir());
        if (isZero(denominator))
            return null; // ray parallel to the plane- the ray direction orthogonal to the normal

        Vector u;
        try {
            u = q0.subtract(ray.getP0());
        } catch (IllegalArgumentException ignore) {
            // the ray starts at the plane's reference point
            return null;
        }

        double t = alignZero(this.normal.dotProduct(u) / denominator);
        return t <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
    }
}
