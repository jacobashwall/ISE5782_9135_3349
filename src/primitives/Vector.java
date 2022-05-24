package primitives;

import static java.lang.Math.*;
import static primitives.Util.alignZero;

/**
 * Vector in our cartesian coordinate system
 */
public class Vector extends Point {

    /**
     * y vector
     */
    public static final Vector Y=new Vector(0,1,0);
    /**
     * z vector
     */
    public static final Vector Z=new Vector(0,0,1);
    /**
     * Creates a new vector
     *
     * @param x coordinate value
     * @param y coordinate value
     * @param z coordinate value
     */
    public Vector(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    /**
     * Create a new Vector
     *
     * @param xyz the coordinate of the Vector
     */
    Vector(Double3 xyz) {
        super(xyz);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Cannot implement zero vector");
    }

    /**
     * Adds two vectors
     *
     * @param vec the vector that's been added
     * @return a new combined vector
     */
    public Vector add(Vector vec) {
        return new Vector(this.xyz.add(vec.xyz));
    }

    /**
     * subtracts two vectors
     *
     * @param vec vec the vector that's we subtract with
     * @return a new subtracted vector
     */
    public Vector subtract(Vector vec) {
        return new Vector(this.xyz.subtract(vec.xyz));
    }

    /**
     * Multiply a vector by a number
     *
     * @param factor the number that we multiply with
     * @return a new scaled vector
     */
    public Vector scale(double factor) {
        return new Vector(this.xyz.scale(factor));
    }


    /**
     * Given two vectors, this function returns the dot product of the two vectors
     *
     * @param other the vector to be added to this vector.
     * @return The dot product of the two vectors.
     */
    public double dotProduct(Vector other) {
        return (this.xyz.d1 * other.xyz.d1) + (this.xyz.d2 * other.xyz.d2) + (this.xyz.d3 * other.xyz.d3);
    }

    /**
     * Calculates the cross product of this vector and a given vector
     *
     * @param other the other vector that we calculate the cross product with
     * @return the cross product
     */
    public Vector crossProduct(Vector other) {
        //AXB=C
        //Cx=AyBz-AzBx
        //Cy=AzBx-AxBz
        //Cz=AxBy-AyBx
        double x = (this.xyz.d2 * other.xyz.d3) - (this.xyz.d3 * other.xyz.d2);
        double y = (this.xyz.d3 * other.xyz.d1) - (this.xyz.d1 * other.xyz.d3);
        double z = (this.xyz.d1 * other.xyz.d2) - (this.xyz.d2 * other.xyz.d1);
        return new Vector(x, y, z);
    }

    /**
     * Calculate the length of this vector squared
     *
     * @return the length of this vector squared
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * Calculate the length of this vector
     *
     * @return the length of this vector
     */
    public double length() {
        return sqrt(this.lengthSquared());
    }

    /**
     * Calculates a normalized vector of this vector
     *
     * @return a new normalized vector
     */
    public Vector normalize() {
        //You can get a unit vector from any vector (except the zero vector) by dividing the original vector by its length.
        return this.scale(1 / this.length());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector) obj;
        return super.equals(other);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Given a vector and an angle, rotate the vector about the given axis by the given angle
     *
     * @param axis  The axis of rotation.
     * @param theta the angle of rotation in degrees
     * @return A rotated new vector.
     */
    public Vector rotateVector(Vector axis, double theta) {
        double x = xyz.d1;
        double y = xyz.d2;
        double z = xyz.d3;
        double u = axis.getX();
        double v = axis.getY();
        double w = axis.getZ();
        double v1 = u * x + v * y + w * z;
        double thetaRad = toRadians(theta);
        double thetaCos = cos(thetaRad);
        double thetaSin = sin(thetaRad);
        double diff = 1d - thetaCos;
        double xPrime = u * v1 * diff + x * thetaCos + (-w * y + v * z) * thetaSin;
        double yPrime = v * v1 * diff + y * thetaCos + (w * x - u * z) * thetaSin;
        double zPrime = w * v1 * diff + z * thetaCos + (-v * x + u * y) * thetaSin;

        return new Vector(alignZero(xPrime), alignZero(yPrime), alignZero(zPrime));
    }

    /**
     * create orthogonal vector
     * @param vec the vector that we seek orthogonal vector to
     * @return the orthogonal vector
     */
    public static Vector createOrthogonal(Vector vec) {
        if (vec.getX() != 1) {//if y and z are not equal to zero
            return new Vector(0, -1 * vec.getZ(), vec.getY()).normalize();
        } else {
            return Vector.Y;
        }
    }
}
