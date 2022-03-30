package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

/**
 * A class that inherits from RayTracerBase to trace rays in a scene
 */
public class RayTracerBasic extends  RayTracerBase{
    /**
     * RayTracerBasic Constructor.
     * @param scene the scene we trace rays in.
     */
    public RayTracerBasic(Scene scene){
        super(scene);
    }

    /**
     * Trace the ray to find the color at the intersection point
     * @param ray the ray to find the color at the intersection point
     * @return the color at the intersection point
     */
    @Override
    public Color traceRay(Ray ray) {
        Point point= ray.findClosestPoint(scene.geometries.findIntersections(ray));//all intersection points wit the ray
        if (point==null)//if no intersection return background color
            return scene.background;
        return calcColor(point);
    }

    /**
     * calculates the color of given point
     * @param point the point to calculate the color of it
     * @return the color that was calculated
     */
    private Color calcColor(Point point)
    {
        return scene.ambientLight.getIntensity();
    }
}
