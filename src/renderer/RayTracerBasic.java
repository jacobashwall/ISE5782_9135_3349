package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * the class
 */
public class RayTracerBasic extends RayTracerBase {


    /**
     * the max number of times that the recursion is going to happen.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * the nim difference that the recursion creates and makes it useless.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    private static final double INITIAL_K = 1.0;

    /**
     * construction the class with the given scene.
     *
     * @param scene the scene to pass on to the superclass.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * tracing the ray and returns the color that the pixel needs to be painted with.
     *
     * @param ray the ray we need to get the color for.
     * @return the color of te pixel.
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestIntersection = findClosestIntersection(ray);
        if (closestIntersection != null)
            return calcColor(closestIntersection, ray);
        return scene.background;
    }


    /**
     * calculate the color that needed to be returned from the pixel.
     *
     * @param gp  the point to calculate the color for.
     * @param ray the ray to pass to the function that summarise all the effects of the light sources.
     * @return the color to paint the pixel.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K));
    }


    /**
     * the entrance function to the recursive process of calculating the reflective effect and refractive effect.
     *
     * @param gp  the point of intersection that need the color calculation.
     * @param ray the ray from the camera to that point.
     * @return the color of the pixel with all the refractions and reflections.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = scene.ambientLight.getIntensity()
                .add(calcLocalEffects(gp, ray))
                .add(gp.geometry.getEmission());
        if (level > 1) {
            color = color.add(calcGlobalEffects(gp, ray, level, k));
        }
        return color;
    }

    /**
     * calculating the color in the global scene, tells what more points we need to
     * check for the color calculations.
     *
     * @param gp    the point of the intersection.
     * @param ray   the ray that intersects with the geometry.
     * @param level the remaining number of times to do the recursion.
     * @param k     the level of insignificance for the k.
     * @return the calculated color.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {

        Color color = Color.BLACK;
        Vector normal = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        //calculate the new kr value by multiplying it by k (-> kkr).
        Double3 kr = material.kR, kkr = kr.product(k);
        //calculate the reflected ray, and the color contribution to the point.
        Ray reflectedRay = constructReflectedRay(normal, gp.point, ray.getDir());
        GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K) && reflectedPoint != null)
            color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr)
                    .scale(material.kR));


        //calculate the new kr value by multiplying it by k (-> kkr).
        Double3 kt = material.kT, kkt = kt.product(k);
        //calculate the refracted ray, and the color contribution to the point.
        Ray refractedRay = constructRefractedRay(normal, gp.point, ray.getDir());
        GeoPoint refractedPoint = findClosestIntersection(refractedRay);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K) && refractedPoint != null)
            color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt)
                    .scale(material.kT));

        return color;
    }


    /**
     * the sum of all the impacts of the light sources on the point.
     *
     * @param gp  the point with the geometry properties including the material properties.
     * @param ray the ray to check the light from.
     * @return the sum of all the light sources on the point.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = Color.BLACK;
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(l.dotProduct(n));
            Color iL = lightSource.getIntensity(gp.point);

            if (unshaded(gp, l, n, lightSource)) {
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }

    /**
     * calculating the specular part of the light.
     *
     * @param material the material containing the properties of
     * @param n        the normal vector of the point in the intersection.
     * @param l        the direction point of the camera.
     * @param nl       the dot product between them.
     * @param v        the to vector of the camera.
     * @return the specular coefficient.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {

        Vector r;

        //the specular vector, to check the match between it and the camera to vector.
        if(isZero(2*nl)){
            r = l;
        }else {
            r = l.subtract(n.scale(2 * nl)).normalize();
        }
        double vr = -v.dotProduct(r);

        //if the object is behind the light source in relation to the camera the specular impact is zero.
        if (vr <= 0) return Double3.ZERO;

        //duplicating it by itself for the nShininess factor.
        double vrMult = 1;
        for (int i = 0; i < material.nShininess; i++) {
            vrMult *= vr;
        }
        return material.kS.scale(vrMult);
    }

    /**
     * calculate the diffusive part of the light source.
     *
     * @param material the material.
     * @param nl       the direction degree between the direction vector of the light to the direction between the light and geometry.
     * @return the defensive coefficient.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(nl < 0 ? -nl : nl);
    }


    /**
     * calculate if the point is shaded, then the phong model is useless.
     *
     * @param gp the intersection point.
     * @param l  the vector from the light source to the point.
     * @param n  the normal vector.
     * @return true if the point is unshaded, false if it is shaded.
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {

        Vector lightDirection = l.scale(-1);

        //the ray from the modified point to the light source.
        Ray lightRay = new Ray(gp.point, lightDirection, n);

        List<GeoPoint> shadePoints = scene.geometries.findGeoIntersections(lightRay);

        //check if there are any intersections and return the result.
        if (shadePoints == null)
            return true;

        //iterate over all the points and check if there is a point that is closer to the head, with on transparency
        // of the ray more than to the light source return false.
        for (GeoPoint shadeGp : shadePoints) {
            if (lightSource.getDistance(gp.point) > gp.point.distance(shadeGp.point) &&
                    shadeGp.geometry.getMaterial().kT.equals(new Double3(0)))
                return false;
        }

        return true;
    }


    /**
     * the function calculate the reflected ray.
     *
     * @param n            the normal vector.
     * @param intersection the in intersection point.
     * @param rayVector    the income vector ray,
     * @return the reflected ray.
     */
    private Ray constructReflectedRay(Vector n, Point intersection, Vector rayVector) {
        double scale = -2 * rayVector.dotProduct(n);
        return new Ray(intersection, rayVector.add(n.scale(scale)), n);
    }

    /**
     * return ray in the same direction but starts in the intersection point.
     *
     * @param intersection    the intersection point.
     * @param incomeRayVector the income vector.
     * @return the fractured ray.
     */
    private Ray constructRefractedRay(Vector normal, Point intersection, Vector incomeRayVector) {
        return new Ray(intersection, incomeRayVector, normal);
    }

    /**
     * Finds the closest intersection
     * @param ray the ray that we look for the closest intersection point
     * @return the closest intersection point
     */
    private GeoPoint findClosestIntersection(Ray ray) {

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);

        if (intersections == null || intersections.size() == 0) {
            return null;
        }

        //initializing the first values for comperation.
        GeoPoint closestGeoPoint = intersections.get(0);
        double distance = intersections.get(0).point.distance(ray.getP0());

        //iterating over the list.
        for (GeoPoint p : intersections) {
            if (p.point.distance(ray.getP0()) < distance) {
                distance = p.point.distance(ray.getP0());
                closestGeoPoint = p;
            }
        }

        //returning the result
        return closestGeoPoint;
    }

}