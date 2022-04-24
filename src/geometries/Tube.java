package geometries;

import primitives.*;

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

    @Override
    public List<Point> findIntersections(Ray ray) {

        Vector tubeDir = this.axisRay.getDir();
        Vector rayDir = ray.getDir();

        if(tubeDir.equals(rayDir) || tubeDir.equals(rayDir.scale(-1))){
            return null;
        }

        double dotP1 = Util.alignZero(rayDir.dotProduct(tubeDir));
        Vector vec1 = dotP1 == 0? rayDir : rayDir.subtract(tubeDir.scale(dotP1));
        double radiusSquared = this.radius*this.radius;

        double A = Util.alignZero(vec1.lengthSquared());

        if(ray.getP0().equals(this.axisRay.getP0())){
            return List.of(ray.getPoint(Math.sqrt(radiusSquared/A)));
        }

        Vector deltaP = ray.getP0().subtract(this.axisRay.getP0());

        if(tubeDir.equals(deltaP.normalize()) || tubeDir.equals(deltaP.normalize().scale(-1))){
            return List.of(ray.getPoint(Math.sqrt(radiusSquared/A)));
        }

        double dotP2 = Util.alignZero(deltaP.dotProduct(tubeDir));
        var vec2 = dotP2 == 0 ? deltaP : deltaP.subtract(tubeDir.scale(dotP2));

        double B = Util.alignZero(2*(vec1.dotProduct(vec2)));
        double C = Util.alignZero(vec2.lengthSquared()-radiusSquared);

        double det = Util.alignZero(B*B - 4*A*C);

        if (det <= 0) return null;

        det  = Math.sqrt(det);
        double t1 = Util.alignZero((-B + det)/(2*A));
        double t2 = Util.alignZero((-B - det)/(2*A));

        if(t1 <= 0) return null;

        return t2 <= 0 ? List.of(ray.getPoint(t1)) : List.of(ray.getPoint(t2), ray.getPoint(t1));
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        Vector tubeDir = this.axisRay.getDir();
        Vector rayDir = ray.getDir();

        // if the ray is parallel  to the tube axis ray return null
        if(tubeDir.equals(rayDir) || tubeDir.equals(rayDir.scale(-1))){
            return null;
        }

        double dotP1 = Util.alignZero(rayDir.dotProduct(tubeDir));
        //if rayDir and tubeDir are orthogonal return just the rayDir,
        //else return their dot product.
        Vector vec1 = dotP1 == 0? rayDir : rayDir.subtract(tubeDir.scale(dotP1));
        double radiusSquared = this.radius*this.radius;

        //First coefficient of the quadratic equation.
        double A = Util.alignZero(vec1.lengthSquared());

        try{ray.getP0().equals(this.axisRay.getP0());}
        catch (IllegalArgumentException exc){
            return List.of(new GeoPoint(this,ray.getPoint(Math.sqrt(radiusSquared/A))));
        }

        //The vector between the ray heads.
        Vector deltaP = ray.getP0().subtract(this.axisRay.getP0());

        //If the ray starts at the tube axis ray
        if(tubeDir.equals(deltaP.normalize()) || tubeDir.equals(deltaP.normalize().scale(-1))){
            return List.of(new GeoPoint(this,(ray.getPoint(Math.sqrt(radiusSquared/A)))));
        }

        double dotP2 = Util.alignZero(deltaP.dotProduct(tubeDir));
        var vec2 = dotP2 == 0 ? deltaP : deltaP.subtract(tubeDir.scale(dotP2));

        //Second coefficient for the quadratic equation
        double B = Util.alignZero(2*(vec1.dotProduct(vec2)));
        //Third coefficient for the quadratic equation
        double C = Util.alignZero(vec2.lengthSquared()-radiusSquared);

        //Discriminant for the quadratic equation
        double det = Util.alignZero(B*B - 4*A*C);

        //If the discriminant is smaller or equal to 0,
        // the ray is outside the tube.
        if (det <= 0) return null;

        //Solving the quadratic equation.
        det  = Math.sqrt(det);
        double t1 = Util.alignZero((-B + det)/(2*A));
        double t2 = Util.alignZero((-B - det)/(2*A));

        //The intersection points are behind the head of the ray
        if(t1 <= 0) return null;

        //Check if there are one or two intersection points.
        return t2 <= 0 ? List.of(new GeoPoint(this,ray.getPoint(t1))) : List.of(new GeoPoint(this,ray.getPoint(t2)), new GeoPoint(this,ray.getPoint(t1)));
    }
}
