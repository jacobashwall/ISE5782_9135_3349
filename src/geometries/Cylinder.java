package geometries;

import primitives.*;

/**
 * a class that extends tube and represents a cylinder
 */
public class Cylinder extends Tube{
    //height of cylinder
   private double height;

    /**
     * ctor of parameters
     * @param axisRay center ray of the cylinder
     * @param radius radius of the cylinder
     * @param height height of the cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * height getter
     * @return cylinder height
     */
    public double getHeight() {
        return height;
    }


    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point pnt) {
        //the points to represent each base center point
        Point base1Point=axisRay.getP0();
        Point base2Point=axisRay.getP0().add(axisRay.getDir().scale(height));
        //the vectors from each base points to the given point
        Vector base1Vector=pnt.subtract(base1Point);
        Vector base2Vector=pnt.subtract(base2Point);
        //the dot product of each vector and the normal, if zero means that they are orthogonal
        Double dotPro1=axisRay.getDir().dotProduct(base1Vector);
        Double dotPro2=axisRay.getDir().dotProduct(base2Vector);
        //the distances from the point to each of the base center point
        Double distance1=pnt.distance(base1Point);
        Double distance2=pnt.distance(base2Point);
        //check if the point is on the plane of base,
        //as the vector to it is orthogonal to the direction of the center points (the dot product equals zero).
        //and even though it's on the same plane, maybe it's not on the circle,
        //therefore we will check if the distance to each base's center point is less or equals to the radius.
        if((dotPro1==0 || dotPro2==0) && (distance1<=radius||distance2<=radius) ) {
            //if so, the normal is the normal of the base, ore just the axis direction
            return axisRay.getDir();
        }
        else{ //if not, then the point is on the sides of the cylinder, and has the same calculations as a Tube
            return super.getNormal(pnt);
        }
    }
}
