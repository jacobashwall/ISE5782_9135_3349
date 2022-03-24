package primitives;

import static java.lang.Math.sqrt;

/**
 * Point in our cartesian coordinate system
 */
public class Point {
    protected final Double3 xyz;

    /**
     * Constant object of Point at the center of our cartesian coordinate system
     */
    public static final Point ZERO = new Point(Double3.ZERO);

    /**
     * Create a new Point with 3 coordinates values of x, y and z
     *
     * @param x coordinate value
     * @param y coordinate value
     * @param z coordinate value
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Create a new point
     *
     * @param xyz the coordinates set of the point
     */
    protected Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Subtracts this point with another point
     *
     * @param other the point to subtract with
     * @return the vector that starts at the given point and ends at this point
     */
    public Vector subtract(Point other) {
        return new Vector(this.xyz.subtract(other.xyz));
    }

    /**
     * Adds a vector to this point
     *
     * @param vec the vector that's been added
     * @return the point after the addition
     */
    public Point add(Vector vec) {
        return new Point(this.xyz.add(vec.xyz));
    }


    /**
     * Calculates the distance between another point and this point squared
     *
     * @param other the other point to calculate the distance with
     * @return the distance between the points squared
     */
    public double distanceSquared(Point other) {
        //distance squared between p1 and p2=(p1x-p2x)^2+(p1y-p2y)^2+(p1z-p2z)^2
        double dx = this.xyz.d1 - other.xyz.d1;
        double dy = this.xyz.d2 - other.xyz.d2;
        double dz = this.xyz.d3 - other.xyz.d3;
        return dx * dx + dy * dy + dz * dz;
    }


    /**
     * Calculates the distance between another point and this point
     *
     * @param other the other point to calculate the distance with
     * @return the distance between the points
     */
    public double distance(Point other) {
        return sqrt(distanceSquared(other));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Point other = (Point) obj;
        return this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return xyz.toString();
    }

    /**
     * X getter.
     *
     * @return the x cardinal of the given point .
     */
    public double getX() {
        return this.xyz.d1;
    }

    /**
     * Y getter.
     *
     * @return the y cardinal of the given point .
     */
    public double getY() {
        return this.xyz.d2;
    }

    /**
     * Z getter.
     *
     * @return the z cardinal of the given point .
     */
    public double getZ() {
        return this.xyz.d3;
    }
}





