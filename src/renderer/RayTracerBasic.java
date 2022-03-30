package renderer;

import primitives.Ray;

/**
 * A class that inherits from RayTracerBase to trace rays in a scene
 */
public class RayTracerBasic extends  RayTracerBase{
    /**
     * RayTracerBasic Constructor.
     * @param Scene the scene we trace rays in.
     */
    public RayTracerBasic(scene Scene){
        super(Scene);
    }

    /**
     * Trace the ray to find the color at the intersection point
     * @param ray the ray to find the color at the intersection point
     * @return the color at the intersection point
     */
    @Override
    public Color traceRay(Ray ray) {
        return null;
    }
}
