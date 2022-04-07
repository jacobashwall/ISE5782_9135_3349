package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Map;


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
     * Creates a tube using the list of attributes from the XML file
     *
     * @param tubeAttributes list of tube attributes fetched from the xml file
     * @return a tube with the values stated in the tube attributes
     */
    public static Tube ReadXMLTube(Map<String, String> tubeAttributes) {
        double radius = Double.parseDouble(tubeAttributes.get("radius"));

        String[] axisRayAttribute = tubeAttributes
                .get("p0").split("\\s+");
        Point p0 = new Point(Double.parseDouble(axisRayAttribute[0]),
                Double.parseDouble(axisRayAttribute[1]),
                Double.parseDouble(axisRayAttribute[2]));
        axisRayAttribute = tubeAttributes
                .get("dir").split("\\s+");
        Vector dir = new Vector(Double.parseDouble(axisRayAttribute[0]),
                Double.parseDouble(axisRayAttribute[1]),
                Double.parseDouble(axisRayAttribute[2]));
        Ray axisRay = new Ray(p0, dir);
        Tube tube = new Tube(axisRay, radius);
        if (tubeAttributes.get("emission") != null) {
            String[] emissionLightAttributes = tubeAttributes.get("emission").split("\\s+");
            Color emissionLight = new Color(
                    Double.parseDouble(emissionLightAttributes[0]),
                    Double.parseDouble(emissionLightAttributes[1]),
                    Double.parseDouble(emissionLightAttributes[2]));
            tube.setEmission(emissionLight);
        }
        return tube;
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
