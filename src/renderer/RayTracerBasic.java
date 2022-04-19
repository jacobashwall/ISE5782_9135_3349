package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

/**
 * A class that inherits from RayTracerBase to trace rays in a scene
 */
public class RayTracerBasic extends RayTracerBase {
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
        if (intersection == null)//if no intersection return background color
            return scene.background;
        return calcColor(intersection,ray);
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

    private Color calcLocalEffects(GeoPoint intersection, Ray ray){
        // Getting the emission of the geometric body
        Color color = intersection.geometry.getEmission();

        Vector v = ray.getDir ();//direction of the ray
        Vector n = intersection.geometry.getNormal(intersection.point);//normal to the geometric body in the intersection point

        //if the ray and the normal in the intersection point
        //orthogonal to each other, return just the emission.
        double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0) return color;


        Material material = intersection.geometry.getMaterial();//the material of the geometric body

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = Util.alignZero(n.dotProduct(l);
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(intersection.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v));
            }
        }
        return color;
    }

    private Double3 calcDiffusive(Material material,double nl){
        return material.kD.scale(nl);
    }

    private Double3 calcSpecular(Material material, Vector n, Vector l, Vector nl, Vector v);
}
