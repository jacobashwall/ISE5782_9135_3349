package geometries;

import primitives.*;

import java.util.Map;

import static primitives.Util.isZero;

/**
 * Class that extends tube and represents a cylinder.
 */
public class Cylinder extends Tube {
    //height of cylinder
    private final double height;

    /**
     * ctor of parameters
     *
     * @param axisRay center ray of the cylinder
     * @param radius  radius of the cylinder
     * @param height  height of the cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * Creates a cylinder using the list of attributes from the XML file
     *
     * @param cylinderAttributes list of cylinder attributes fetched from the xml file
     * @return a cylinder with the values stated in the tube attributes
     */
    public static Cylinder ReadXMLCylinder(Map<String, String> cylinderAttributes) {
        double radius = Double.parseDouble(cylinderAttributes.get("radius"));

        String[] axisRayAttribute = cylinderAttributes
                .get("p0").split("\\s+");
        Point p0 = new Point(Double.parseDouble(axisRayAttribute[0]),
                Double.parseDouble(axisRayAttribute[1]),
                Double.parseDouble(axisRayAttribute[2]));
        axisRayAttribute = cylinderAttributes
                .get("dir").split("\\s+");
        Vector dir = new Vector(Double.parseDouble(axisRayAttribute[0]),
                Double.parseDouble(axisRayAttribute[1]),
                Double.parseDouble(axisRayAttribute[2]));
        Ray axisRay = new Ray(p0, dir);

        double height = Double.parseDouble(cylinderAttributes.get("height"));

        Cylinder cylinder = new Cylinder(axisRay, radius, height);
        if (cylinderAttributes.get("emission") != null) {
            String[] emissionLightAttributes = cylinderAttributes.get("emission").split("\\s+");
            Color emissionLight = new Color(
                    Double.parseDouble(emissionLightAttributes[0]),
                    Double.parseDouble(emissionLightAttributes[1]),
                    Double.parseDouble(emissionLightAttributes[2]));
            cylinder.setEmission(emissionLight);
        }
        return cylinder;
    }

    /**
     * height getter
     *
     * @return cylinder height
     */
    public double getHeight() {
        return height;
    }


    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point pnt) {
        //the points to represent each base center point
        Point base1Point = axisRay.getP0();
        Vector dir = axisRay.getDir();
        double t;
        try {
            t = pnt.subtract(base1Point).dotProduct(dir);
        } catch (IllegalArgumentException ignore) {
            t = 0;
        }
        if (isZero(t) || isZero(t - height)) return dir;

        return super.getNormal(pnt);
    }


}
