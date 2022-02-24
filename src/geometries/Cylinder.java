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
        return null;
    }
}
