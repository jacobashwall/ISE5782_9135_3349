package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.isZero;

/**
 * A ray in the cartesian system
 */
public class Ray {
    private final Point p0;
    private final Vector dir;

    /**
     * Creates a new Ray
     *
     * @param p0  the starting point of the ray
     * @param dir the direction of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Return the starting point of the ray
     *
     * @return the starting point of the ray
     */
    public Point getP0() {
        return this.p0;
    }

    /**
     * Return the direction of the Ray
     *
     * @return the direction of the Ray
     */
    public Vector getDir() {
        return this.dir;
    }

    @Override
    public String toString() {
        return "starting point " + p0 +
                " with the direction of" + dir;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray other = (Ray) obj;
        return this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    /**
     * Returns the point on the end of th ray scaled by given parameter
     *
     * @param t Scale factor
     * @return Point at the end of the scaled ray
     */
    public Point getPoint(double t) {
        return isZero(t) ? p0 : p0.add(dir.scale(t));
    }

    /**
     * Method for finding the closest point to the head of the ray in a given list of points
     *
     * @param points List of points
     * @return Closest point to the head of the ray
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }


    /**
     * Method for finding the closest Geopoint to the head of the ray in a given list of points
     *
     * @param lst List of points
     * @return Closest Geopoint to the head of the ray
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> lst) {
        //In case of empty list return null
        if (lst == null || lst.isEmpty()) {
            return null;
        }

        GeoPoint p = null;
        double d = Double.POSITIVE_INFINITY;
        //Iterating through the list. Once we find smaller distance
        //than we have we replace the values.
        //This goes on until the end of the list.
        for (GeoPoint pnt : lst) {
            if (d > this.getP0().distance(pnt.point)) {
                d = this.getP0().distance(pnt.point);
                p = pnt;
            }
        }

        return p;
    }
}
