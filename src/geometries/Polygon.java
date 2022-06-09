package geometries;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import primitives.*;

import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected final List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;
    private final int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        size = vertices.length;
        if (size < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);


        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) {
            this.boundary = calcBoundary();
            return; // no need for more tests for a Triangle
        }

        Vector n = plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
        this.boundary = calcBoundary();
    }


    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }


    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersection = this.plane.findGeoIntersections(ray, maxDistance);
        if (intersection == null)//checks if there is an intersection with the plane of the polygon
            return null;

        List<GeoPoint> geoIntersection = new LinkedList<>();
        for (var geoPoint : intersection)
            geoIntersection.add(new GeoPoint(this, geoPoint.point));
        //we will check if the point is inside or outside the polygon
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        //V(i)= vertices[i]-p0
        List<Vector> listVi = vertices.stream().map(p -> p.subtract(p0)).toList();
        //N(i)= Normalize(V(i)*V(i+1))
        List<Vector> listNi = new LinkedList<>();
        //can't use map since I need to reach the i and i+1 (don't know how to do it)
        //also can't use for each for the same reason
        int i = 0;
        for (; i < size - 1; i++) {
            listNi.add(listVi.get(i).crossProduct(listVi.get(i + 1).normalize()));
        }
        listNi.add((listVi.get(i).crossProduct(listVi.get(0))).normalize());//cross product the last and the first vectors
        //Sign(i)= v*N(i)
        double sign = v.dotProduct(listNi.get(0));
        int counter = 1;
        while (sign > 0)//all signs must be positive from now on in order the point to be inside the polygon
        {
            if (counter == size)//all signs are positive
                return geoIntersection;
            sign = v.dotProduct(listNi.get(counter));
            if (!(sign > 0))//if the sign is not positive
                return null;//not all signs are positive therefore no intersection
            counter++;
        }
        while (sign < 0)//all signs must be negative from now on in order the point to be inside the polygon
        {
            if (counter == size)//all signs are negative
                return geoIntersection;
            sign = v.dotProduct(listNi.get(counter));
            if (!(sign < 0))//if the sign is not negative
                return null;//not all signs are negative therefore no intersection
            counter++;
        }
        return null;//if the first sign is zero

    }

    @Override
    public int[][] calcBoundary() {
        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        double x;
        double y;
        double z;
        for (var point : vertices) {
            x = point.getX();
            y = point.getY();
            z = point.getZ();
            if (x < minX)
                minX = x;
            if (x > maxX)
                maxX = x;
            if (y < minY)
                minY = y;
            if (y > maxY)
                maxY = y;
            if (z < minZ)
                minZ = z;
            if (z > maxZ)
                maxZ = z;
        }

        return new int[][]{{(int) minX, (int) Math.ceil(maxX)},
                {(int) minY, (int) Math.ceil(maxY)},
                {(int) minZ, (int) Math.ceil(maxZ)}};
    }

}