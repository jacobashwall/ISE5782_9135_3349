package renderer;

import primitives.Ray;

/**
 * an abstract class to be the base to RayTracerBasic
 */
public abstract class RayTracerBase {
    protected scene Scene;

    /**
     *  RayTracerBase constructor
     * @param Scene the given scene to trace the rays in
     */
    public RayTracerBase(scene Scene){
        this.Scene=Scene;
    }

    /**
     * Trace the ray to find the color at the intersection point
     * @param ray the ray to find the color at the intersection point
     * @return the color at the intersection point
     */
    public abstract Color traceRay(Ray ray);
}
