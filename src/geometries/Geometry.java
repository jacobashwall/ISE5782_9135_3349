package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * abstract class which all the geometries implement
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;

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
     * returns the normal to the geometric body surface at a point on the geometric object's surface
     *
     * @param pnt point on the geometric object surface
     * @return normal at the point
     */
    public abstract Vector getNormal(Point pnt);

}
