package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * an abstract class to be the base to RayTracerBasic
 */
public abstract class RayTracerBase {
    protected final Scene scene;

    /**
     * RayTracerBase constructor
     *
     * @param scene the given scene to trace the rays in
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Trace the ray to find the color at the intersection point
     *
     * @param ray the ray to find the color at the intersection point
     * @return the color at the intersection point
     */
    public abstract Color traceRay(Ray ray);
}
