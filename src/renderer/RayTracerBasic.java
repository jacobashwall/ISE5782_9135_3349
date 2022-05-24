package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

/**
 * the class
 */
public class RayTracerBasic extends RayTracerBase {


    /**
     * the max number of times that the recursion is going to happen.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * the min difference that the recursion creates and makes it useless.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * the initial value of k factor
     */
    private static final double INITIAL_K = 1.0;
    /**
     * resolution of the target area
     */
    private static final int TARGET_AREA_RESOLUTION = 9;
    /**
     * distance of the target area from the main ray head
     */
    private static final double TARGET_AREA_DISTANCE = 10;
    /**
     * length of the target area edge
     */
    private static final double TARGET_AREA_EDGE = 2;

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
        return closestIntersection == null ? scene.background : calcColor(closestIntersection, ray);
    }


    /**
     * calculate the color that needed to be returned from the pixel.
     *
     * @param gp  the point to calculate the color for.
     * @param ray the ray to pass to the function that summarise all the effects of the light sources.
     * @return the color to paint the pixel.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)));
    }


    /**
     * the entrance function to the recursive process of calculating the reflective effect and refractive effect.
     *
     * @param gp    the point of intersection that need the color calculation.
     * @param ray   the ray from the camera to that point.
     * @param level the remaining number of times to do the recursion.
     * @param k     the level of insignificance for the k.
     * @return the color of the pixel with all the refractions and reflections.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return level == 1 ? color : color.add(calcGlobalEffect(gp, ray, level, k));
    }

    /**
     * calculating a global effect color
     *
     * @param ray   the ray that intersects with the geometry.
     * @param level the remaining number of times to do the recursion.
     * @param k     the level of insignificance for the k.
     * @param kx    the attenuation factor of reflection or transparency
     * @return the calculated color.
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = kx.product(k);
        //calculate the reflected ray, and the color contribution to the point.
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null || kkx.lowerThan(MIN_CALC_COLOR_K)) ? Color.BLACK
                : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * calculating the color by checking for global effects,
     * finds the next intersection points to calculate for the  recursion.
     *
     * @param gp    the point of the intersection.
     * @param ray   the ray that intersects with the geometry.
     * @param level the remaining number of times to do the recursion.
     * @param k     the level of insignificance for the k.
     * @return the calculated color.
     */
    private Color calcGlobalEffect(GeoPoint gp, Ray ray, int level, Double3 k) {
        //Variables for the function
        Vector v = ray.getDir();
        Vector normal = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        Ray reflectedRay = constructReflectedRay(normal, gp.point, v);
        Ray refractedRay = constructRefractedRay(normal, gp.point, v);
        Color diffSamplingSum = Color.BLACK;
        Color glossSamplingSum = Color.BLACK;

        //If diffusive glass
        if (material.kDg != 0) {
            //super sample the refracted ray
            LinkedList<Ray> diffusedSampling = superSample(refractedRay, material.kDg, normal);
            //for each sampling ray calculate the global effect
            for (var secondaryRay : diffusedSampling) {
                diffSamplingSum = diffSamplingSum.add(calcGlobalEffect(secondaryRay, level, k, material.kT));
            }
            //take the average of the calculation for all sample rays
            diffSamplingSum = diffSamplingSum.reduce(diffusedSampling.size());
        }
        //If glossy surface
        if (material.kSg != 0) {
            //super sample the reflected ray
            LinkedList<Ray> glossySampling = superSample(reflectedRay, material.kSg, normal);
            //for each sampling ray calculate the global effect
            for (var secondaryRay : glossySampling) {
                glossSamplingSum = glossSamplingSum.add(calcGlobalEffect(secondaryRay, level, k, material.kR));
            }
            //take the average of the calculation for all sample rays
            glossSamplingSum = glossSamplingSum.reduce(glossySampling.size());
        }

        //If diffusive and glossy return both of the results above
        if (material.kDg != 0 && material.kSg != 0) {
            return glossSamplingSum
                    .add(diffSamplingSum);
        }
        //else return the matching result
        else if (material.kDg + material.kSg > 0) {
            return material.kDg != 0 ? calcGlobalEffect(reflectedRay, level, k, material.kR).add(diffSamplingSum) :
                    calcGlobalEffect(refractedRay, level, k, material.kT).add(glossSamplingSum);
        }

        return calcGlobalEffect(reflectedRay, level, k, material.kR)
                .add(calcGlobalEffect(refractedRay, level, k, material.kT));
    }

    /**
     * Calculating the local lighting effects of different light sources
     *
     * @param intersection the intersection point of the ray parameter with the geometric body.
     *                     This method calculate the light intensity at this point.
     * @param ray          the ray that intersects the geometric body
     * @param k            the level of insignificance for the k.
     * @return Color calculated by the light sources
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {
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
                Double3 ktr = transparency(intersection, l, n, lightSource);
                //Here we deal with the transparency of the objects
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add( //
                            iL.scale(calcDiffusive(material, nl)//diffusive effect
                                    .add(calcSpecular(material, n, l, nl, v))));//specular effect
                }
            }
        }
        return color;
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
        double minusVr = alignZero(-v.dotProduct(r));
        if (minusVr <= 0) return Double3.ZERO;

        //Calculation of the effect according to phong model
        return material.kS.scale(Math.pow(minusVr, material.nShininess));
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
     * Determining how much light reach a certain point, blocked by other objects
     *
     * @param gp          the point we want to calculate the transparency for
     * @param l           vector from the light source
     * @param n           normal to the point
     * @param lightSource the light source
     * @return the transparency of the point
     */
    private Double3 transparency(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {
        Vector lightDirection = l.scale(-1); // from point to light source
        //We make sure to move the object by DELTA
        //int the correct direction
        //Create a new ray to check shadiness
        Ray lightRay = new Ray(gp.point, lightDirection, n);

        Double3 ktr = Double3.ONE;
        //Find if any geometric object blocks the light
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(lightRay.getP0()));
        if (intersections == null)
            return ktr;
        //For every geometric object in the list, scale by its transparency coefficient
        for (var geoPoint : intersections) {
            ktr = ktr.product(geoPoint.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
        }
        return ktr;
    }


    /**
     * the function calculate the reflected ray.
     *
     * @param normal          the normal vector.
     * @param intersection    the in intersection point.
     * @param incomeRayVector the income vector ray,
     * @return the reflected ray.
     */
    private Ray constructReflectedRay(Vector normal, Point intersection, Vector incomeRayVector) {
        double scale = -2 * incomeRayVector.dotProduct(normal);
        return new Ray(intersection, incomeRayVector.add(normal.scale(scale)), normal);
    }

    /**
     * return ray in the same direction but starts in the intersection point.
     *
     * @param normal          the normal vector.
     * @param intersection    the intersection point.
     * @param incomeRayVector the income vector.
     * @return the fractured ray.
     */
    private Ray constructRefractedRay(Vector normal, Point intersection, Vector incomeRayVector) {
        return new Ray(intersection, incomeRayVector, normal);
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
     * A method that generates a ray, starting at the point and going through
     * specific square in the grid.
     *
     * @param j      The horizontal index of the square
     * @param i      The vertical index of the square
     * @param ray    The main ray which we built the grid around
     * @param vTo    The direction of the main ray
     * @param vUp    Orthogonal to vTo, decides the angle
     * @param vRight Orthogonal to vTo, decides the angle
     * @param k      glossy/diffusive attenuation coefficient
     * @param n      normal to the head of the main ray
     * @return Vector that goes through the requested square in the grid
     */
    private Vector createVectorBeam(int i, int j, Ray ray, Vector vTo, Vector vUp, Vector vRight, double k, Vector n) {
        Point p0 = ray.getP0();
        //Center of the grid
        Point pIj = p0.add(vTo.scale(TARGET_AREA_DISTANCE));
        //height and width of each square
        double rC = k * TARGET_AREA_EDGE / TARGET_AREA_RESOLUTION;
        //vertical distance of the required square from the center of the grid
        double yI = -(i - ((double) (TARGET_AREA_RESOLUTION - 1)) / 2) * rC;
        //horizontal distance of the required square from the center of the grid
        double xJ = -(j - ((double) (TARGET_AREA_RESOLUTION - 1)) / 2) * rC;
        //changing the position of the center point so that the ray will intersect the view plane in the right place
        if (xJ != 0) {
            pIj = pIj.add(vRight.scale(xJ));
        }
        if (yI != 0) {
            pIj = pIj.add(vUp.scale(yI));
        }
        //return the ray
        double sign = pIj.subtract(ray.getP0()).dotProduct(n);
        //Checking that the secondary ray doesn't go the other side of the normal plane
        if (vTo.dotProduct(n) * sign < 0) return null;

        return pIj.subtract(p0).normalize();

    }

    /**
     * Creates a sample ray for each square in the target area
     *
     * @param ray The main ray
     * @param k   glossy/diffusive attenuation coefficient
     * @param n   normal to the head of the main ray
     * @return List of sample rays
     */
    private LinkedList<Ray> superSample(Ray ray, double k, Vector n) {
        LinkedList<Ray> sampling = new LinkedList<>();
        Vector vUp;
        Vector vRight;
        Vector vTo = ray.getDir();
        vUp = Vector.createOrthogonal(vTo);
        vRight = vTo.crossProduct(vUp).normalize();
        Point p0 = ray.getP0();
        for (int i = 0; i < TARGET_AREA_RESOLUTION; i++) {
            for (int j = 0; j < TARGET_AREA_RESOLUTION; j++) {
                Vector sampleDir = createVectorBeam(i, j, ray, vTo, vUp, vRight, k, n);
                if (sampleDir != null) {
                    sampling.add(new Ray(p0, sampleDir, n));
                }
            }
        }
        return sampling;
    }

}