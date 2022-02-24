package primitives;

import static java.lang.Math.sqrt;

public class Vector extends Point{
    /**
     * Creates a new vector
     * @param x the x component of xyz
     * @param y the y component of xyz
     * @param z the z component of xyz
     */
    public Vector(double x, double y, double z) {
        super(x,y,z);
        if(this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Cannot implement zero vector");
    }
    /**
     * Create a new Vector
     * @param xyz the coordinate of the Vector
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if(this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Cannot implement zero vector");
    }

    /**
     * Adds two vectors
     * @param vec the vector that's been added
     * @return a new combined vector
     */
    public Vector add(Vector vec) {
     Double3 answer=this.xyz.add(vec.xyz);
     return new Vector(answer.d1, answer.d2, answer.d3);
    }

    /**
     * subtracts two vectors
     * @param vec vec the vector that's we subtract with
     * @return a new subtracted vector
     */
    public Vector subtract(Vector vec) {
        Double3 answer=this.xyz.subtract(vec.xyz);
        return new Vector(answer.d1, answer.d2, answer.d3);
    }

    /**
     * Multiply a vector by a number
     * @param factor the number that we multiply with
     * @return a new scaled vector
     */
    public Vector scale(double factor) {
        Double3 answer=this.xyz.scale(factor);
        return new Vector(answer.d1, answer.d2, answer.d3);
    }

    /**
     * Calculates the dot product of this vector and a given vector
     * @param other the other vector that we calculate the dot product with
     * @return the dot product
     */
    public double dotProduct(Vector other){
        Double3 answer= this.xyz.product(other.xyz);
        return answer.d1+answer.d2+answer.d3;
    }
    /**
     * Calculates the cross product of this vector and a given vector
     * @param other the other vector that we calculate the cross product with
     * @return the cross product
     */
    public Vector crossProduct (Vector other) {
        //AXB=C
        //Cx=AyBz-AzBx
        //Cy=AzBx-AxBz
        //Cz=AxBy-AyBx
        if(this.equals(other))
            throw new IllegalArgumentException("Cross of 2 equal vectors is zero vector");
        double x= (this.xyz.d2*other.xyz.d3)-(this.xyz.d3*other.xyz.d2);
        double y= (this.xyz.d3*other.xyz.d1)-(this.xyz.d1*other.xyz.d3);
        double z= (this.xyz.d1*other.xyz.d2)-(this.xyz.d2*other.xyz.d1);
        return new Vector(x,y,z);
    }

    /**
     * Calculate the length of this vector squared
     * @return the length of this vector squared
     */
    public double lengthSquared() {
        //the length squared is the distance between the point that is the head of the vector
        //to the point (0,0,0) that is the tail of the vector.
        return this.distanceSquared(new Point(0,0,0));
    }
    /**
     * Calculate the length of this vector
     * @return the length of this vector
     */
    public double length(){
        return sqrt(this.lengthSquared());
    }

    /**
     * Calculates a normalized vector of this vector
     * @return a new normalized vector
     */
    public Vector normalize(){
        //You can get a unit vector from any vector (except the zero vector) by dividing the original vector by its length.
        double factor= this.length();
        Vector ans=new Vector(this.xyz.d1,this.xyz.d2,this.xyz.d3);
        return ans.scale(1/factor);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector)obj;
        return super.equals(other);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
