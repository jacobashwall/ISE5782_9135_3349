package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface which all the geometries implement
 */
public interface Geometry extends Intersectable {
    /**
     * returns the normal to the geometric body surface at a point on the geometric object's surface
     *
     * @param pnt point on the geometric object surface
     * @return normal at the point
     */
    public Vector getNormal(Point pnt);
}
