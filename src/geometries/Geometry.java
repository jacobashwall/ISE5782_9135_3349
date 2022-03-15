package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * interface which all the geometries implement
 */
public interface Geometry extends Intersectable{
    /**
     * returns the normal to a point in the geometric object
     * @param pnt point in the geometric object
     * @return normal to the point
     */
    public Vector getNormal(Point pnt);
}
