package primitives;

import static java.lang.Math.sqrt;

public class Point {
    protected Double3 xyz;

    /**
     * Create a new Point
     * @param x the x component of xyz
     * @param y the y component of xyz
     * @param z the z component of xyz
     */
    public Point(double x, double y, double z) {
        this.xyz=new Double3(x,y,z);
    }

    /**
     * Create a new point
     * @param xyz the coordinate of the point
     */
    public Point(Double3 xyz) {
        this.xyz=xyz;
    }

    /**
     * Subtracts this point with another point
     * @param other the point to subtract with
     * @return the vector that starts at the given point and ends at this point
     */
    public Vector subtract(Point other){
        Double3 answer= this.xyz.subtract(other.xyz);
        return new Vector(answer.d1,answer.d2, answer.d3);
    }

    /**
     * Adds a vector to this point
     * @param vec the vector that's been added
     * @return the point after the addition
     */
    public Point add(Vector vec){
        Double3 answer= this.xyz.add(vec.xyz);
        return new Point(answer.d1,answer.d2, answer.d3);
    }

    /**
     * Calculates the distance between another point and this point squared
     * @param other the other point to calculate the distance with
     * @return the distance between the points squared
     */
    public double distanceSquared(Point other) {
        //distance squared between p1 and p2=(p1x-p2x)^2+(p1y-p2y)^2+(p1z-p2z)^2
        double squaredD1=(this.xyz.d1-other.xyz.d1)*(this.xyz.d1-other.xyz.d1);
        double squaredD2=(this.xyz.d2-other.xyz.d2)*(this.xyz.d2-other.xyz.d2);
        double squaredD3=(this.xyz.d3-other.xyz.d3)*(this.xyz.d3-other.xyz.d3);
        return squaredD1+squaredD2+squaredD3;
    }

    /**
     * Calculates the distance between another point and this point
     * @param other the other point to calculate the distance with
     * @return the distance between the points
     */
    public double distance(Point other){
        return sqrt(distanceSquared(other));
    }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof Point)) return false;
            Point other = (Point)obj;
            return this.xyz.equals(other.xyz);
        }

    @Override
    public String toString() {
        return xyz.toString();
    }
}





