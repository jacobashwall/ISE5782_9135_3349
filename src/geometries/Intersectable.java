package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Interface of all the intersectable by rays geometries apply.
 */
public interface Intersectable {
    /**
     * Find all intersection points of a ray and a geometric entity.
     * @param ray the ray that intersect with the geometric entity.
     * @return list of intersection points.
     */
    public List<Point> findIntersections(Ray ray);
}
