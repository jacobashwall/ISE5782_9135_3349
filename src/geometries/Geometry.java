package geometries;

import primitives.*;

/**
 * abstract class which all the geometries implement
 */
public abstract class Geometry extends Intersectable {
    private Color emission = Color.BLACK;
    private Material material = new Material();
    /**
     * boundary of the geometric entity represented by the array [x[min,max],y[min,max],z[min,max]]
     */
    public final int[][] boundary;
    /**
     * finds the boundary values of the geometric entity
     * @return the geometry boundary
     */
    protected abstract int[][] calcBoundary();

    /**
     * Emission getter
     *
     * @return the emission
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Emission setter
     *
     * @param emission the new emission
     * @return the modified geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * material field getter
     *
     * @return the material field
     */
    public Material getMaterial() {
        return this.material;
    }

    /**
     * material field setter
     *
     * @param material parameter for the material
     * @return The object itself
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * returns the normal to the geometric body surface at a point on the geometric object's surface
     *
     * @param pnt point on the geometric object surface
     * @return normal at the point
     */
    public abstract Vector getNormal(Point pnt);

}
