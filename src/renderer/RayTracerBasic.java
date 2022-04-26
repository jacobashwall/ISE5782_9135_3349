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
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

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
        GeoPoint intersection = findClosestIntersection(ray);
        //if no intersection return background color
        return intersection == null ? scene.background : calcColor(intersection, ray);
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
        if(intersections == null)
            return true;
        for (var geoPoint:intersections){
            if(lightSource.getDistance(geoPoint.point)>geoPoint.point.distance(gp.point))
                if(geoPoint.geometry.getMaterial().kT.equals(Double3.ZERO))
                    return false;
        }
        return true;
    }

    /**
     * constructs the reflected Ray from a geometric entity
     *
     * @param normal       the normal in the intersection point
     * @param intersection the intersection point that the ray starts in
     * @param inRay        the ray the intersects with the geometric entity
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Vector normal, Point intersection, Ray inRay) {
        double nv = alignZero(normal.dotProduct(inRay.getDir()));
        Vector epsVector = normal.scale(nv < 0 ? DELTA : -DELTA);
        Point rayBase = intersection.add(epsVector);
        Vector reflection = inRay.getDir().subtract(normal.scale(2 * normal.dotProduct(inRay.getDir())));
        return new Ray(rayBase, reflection);
    }

    /**
     * constructs the refracted Ray from a geometric entity
     *
     * @param normal       the normal in the intersection point
     * @param intersection the intersection point that the ray starts in
     * @param inRay        the ray the intersects with the geometric entity
     * @return the refracted ray
     */
    private Ray constructRefractedRay(Vector normal, Point intersection, Ray inRay) {
        double nv = alignZero(normal.dotProduct(inRay.getDir()));
        Vector epsVector = normal.scale(nv < 0 ? -DELTA : DELTA);
        Point rayBase = intersection.add(epsVector);
        return new Ray(rayBase, inRay.getDir());
    }

    /**
     * finds the closest intersection GeoPoint to the base of the ray
     *
     * @param ray the ray that we find intersection from
     * @return the closest intersection GeoPoint
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     * a helper function that calls the actual calc color
     *
     * @param gp  the intersection point
     * @param ray the ray that intersects the geometric body
     * @return the color at the intersection point
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, MIN_CALC_COLOR_K).add(scene.ambientLight.getIntensity());
    }

    /**
     * the recursive calc color function
     * @param intersection the intersection point with the geometric entity
     * @param ray the intersected ray
     * @param level how many levels left to the recursion
     * @param k reduction factor
     * @return the color at the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission().add(calcLocalEffects(intersection, ray));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * the recursive calcGlobalEffects function
     * @param intersection the intersection point with the geometric entity
     * @param ray the intersected ray
     * @param level how many levels left to the recursion
     * @param k reduction factor
     * @return the color at the point
     */
    private Color calcGlobalEffects(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = Color.BLACK;
        Vector normal = intersection.geometry.getNormal(intersection.point);
        Double3 kR = intersection.geometry.getMaterial().kR;
        Double3 kKr = kR.scale(k);
        if (!kKr.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcColor(intersection, constructReflectedRay(normal, intersection.point, ray), level - 1, kKr).scale(kR));
        }
        Double3 kT = intersection.geometry.getMaterial().kT;
        Double3 kKt = kT.scale(k);
        if (!kKr.lowerThan(MIN_CALC_COLOR_K)) {
            color = color.add(calcColor(intersection, constructRefractedRay(normal, intersection.point, ray), level - 1, kKt).scale(kT));
        }
        return color;
    }


}
