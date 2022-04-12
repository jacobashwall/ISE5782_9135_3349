package renderer;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
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
        return calcColor(intersection);
    }

    /**
     * calculates the color of given point
     *
     * @param intersection the point to calculate the color of it
     * @return the color that was calculated
     */
    private Color calcColor(GeoPoint intersection) {
        return scene.ambientLight.getIntensity()
                .add(intersection.geometry.getEmission());
    }
}
