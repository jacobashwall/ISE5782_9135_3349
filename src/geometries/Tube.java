package geometries;

import primitives.*;

import java.util.List;
import java.util.Map;

import static primitives.Util.alignZero;


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


    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        //The overall idea is to form a quadratic equation that it's
        //solutions are the scale factor for the getPoint method.
        //We form this quadratic equation by setting two restriction on an arbitrary point:
        // 1. It is on the ray (i.e. of the form p+t*v)
        // 2. It is on the tube (i.e. it's distance from the tube axis ray is r)
        //Give those two restrictions we extract the requested quadratic equation.
        Vector tubeDir = this.axisRay.getDir();
        Vector rayDir = ray.getDir();

        // if the ray is parallel  to the tube axis ray return null
        if (tubeDir.equals(rayDir) || tubeDir.equals(rayDir.scale(-1))) {
            return null;
        }

        double dotP1 = Util.alignZero(rayDir.dotProduct(tubeDir));
        //if rayDir and tubeDir are orthogonal return just the rayDir,
        //else return their dot product.
        Vector vec1 = dotP1 == 0 ? rayDir : rayDir.subtract(tubeDir.scale(dotP1));
        double radiusSquared = this.radius * this.radius;

        //First coefficient of the quadratic equation.
        double a = Util.alignZero(vec1.lengthSquared());

        if (ray.getP0().equals(this.axisRay.getP0())) {
            return alignZero(radiusSquared / a - maxDistance) > 0 ? null: List.of(new GeoPoint(this, ray.getPoint(Math.sqrt(radiusSquared / a))));
        }

        //The vector between the ray heads.
        Vector deltaP = ray.getP0().subtract(this.axisRay.getP0());

        //If the ray starts at the tube axis ray
        if (tubeDir.equals(deltaP.normalize()) || tubeDir.equals(deltaP.normalize().scale(-1))) {
            return alignZero(radiusSquared / a - maxDistance) > 0 ? null: List.of(new GeoPoint(this, ray.getPoint(Math.sqrt(radiusSquared / a))));
        }

        double dotP2 = Util.alignZero(deltaP.dotProduct(tubeDir));
        var vec2 = dotP2 == 0 ? deltaP : deltaP.subtract(tubeDir.scale(dotP2));

        //Second coefficient for the quadratic equation
        double b = Util.alignZero(2 * (vec1.dotProduct(vec2)));
        //Third coefficient for the quadratic equation
        double c = Util.alignZero(vec2.lengthSquared() - radiusSquared);

        //Discriminant for the quadratic equation
        double det = Util.alignZero(b * b - 4 * a * c);

        //If the discriminant is smaller or equal to 0,
        // the ray is outside the tube.
        if (det <= 0) return null;

        //Solving the quadratic equation.
        det = Math.sqrt(det);
        double t1 = Util.alignZero((-b + det) / (2 * a));
        double t2 = Util.alignZero((-b - det) / (2 * a));

        //The intersection points are behind the head of the ray
        if (t1 <= 0 || alignZero(t2 - maxDistance) > 0) return null;
        if (t1 - maxDistance > 0) {
            return t2 <= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t2)));
        } else {
            //Check if there are one or two intersection points.
            return t2 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t1))) : List.of(new GeoPoint(this, ray.getPoint(t2)), new GeoPoint(this, ray.getPoint(t1)));
        }
    }
}
