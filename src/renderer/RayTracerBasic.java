package renderer;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * A class that inherits from RayTracerBase to trace rays in a scene
 */
public class RayTracerBasic extends RayTracerBase {
    private static final double DELTA = 0.1;

    /**
     * RayTracerBasic Constructor.
     *
     * @param scene the scene we trace rays in.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Trace the ray to find the color at the intersection point
     *
     * @param ray the ray to find the color at the intersection point
     * @return the color at the intersection point
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint intersection = ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));//all intersection points wit the ray
        //if no intersection return background color
        return intersection == null ? scene.background : calcColor(intersection, ray);
    }

    /**
     * calculates the color of given point
     *
     * @param intersection the point to calculate the color of it
     * @return the color that was calculated
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(intersection, ray));
    }

    /**
     * Calculating the local lighting effects of different light sources
     *
     * @param intersection the intersection point of the ray parameter with the geometric body.
     *                     This method calculate the light intensity at this point.
     * @param ray          the ray that intersects the geometric body
     * @return
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        // Getting the emission of the geometric body
        Color color = intersection.geometry.getEmission();

        Vector v = ray.getDir();//direction of the ray
        Vector n = intersection.geometry.getNormal(intersection.point);//normal to the geometric body in the intersection point

        //if the ray and the normal in the intersection point
        //orthogonal to each other, return just the emission.
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;

        Material material = intersection.geometry.getMaterial();//the material of the geometric body

        //go through all the light sources and calculate their
        //intersection at the point
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            //Check the angle to decide whether
            //to add the effect of the other light sources
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if (unshaded(intersection, l, n, nv, lightSource)) {
                    Color iL = lightSource.getIntensity(intersection.point);
                    color = color.add( //
                            iL.scale(calcDiffusive(material, nl)//diffusive effect
                                    .add(calcSpecular(material, n, l, nl, v))));//specular effect
                }
            }
        }
        return color;
    }

    /**
     * Calculates the diffusive effect
     *
     * @param material material of the geometric object
     * @param nl       the dot product of the light source ray direction and the normal to the geometric object at the point
     * @return the diffusive effect expressed by Double3 object
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(nl > 0 ? nl : -nl);
    }

    /**
     * Calculates the specular effect
     *
     * @param material material of the geometric object
     * @param n        the normal to the geometric object at the point
     * @param l        the ray of the light source
     * @param nl       the dot product of the light source ray direction and the normal to the geometric object at the point
     * @param v        the direction of the camera ray
     * @return the specular effect expressed by Double3 object
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        //the reflection of the light source vector (l)
        Vector r = l.subtract(n.scale(2 * nl));
        double minusVR = alignZero(-v.dotProduct(r));
        if (minusVR <= 0) return Double3.ZERO;

        //Calculation of the effect according to phong model
        return material.kS.scale(Math.pow(minusVR, material.nShininess));
    }

    /**
     * Checks whether a given point is lighted by the light source
     * by tracing a ray back to the light source and check if it intersects
     * with a geometrical body
     *
     * @param gp          the given point to check
     * @param l           the ray light from the light source to the point
     * @param n           normal to the geometric body in this point
     * @param nv          dot product of the camera ray and the normal
     * @param lightSource the light source that we check shadiness for
     * @return
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nv, LightSource lightSource) {
        Vector lightDirection = l.scale(-1); // from point to light source
        //We make sure to move the object by DELTA
        //int the correct direction
        Vector epsVector = n.scale(nv < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        //Create a new ray to check shadiness
        Ray lightRay = new Ray(point, lightDirection);
        //Find if any geometric object blocks the light
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(gp.point));
        return intersections == null;
    }
}
