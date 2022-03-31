package geometries;

import primitives.*;

import static primitives.Util.isZero;

/**
 * Class that extends tube and represents a cylinder
 */
public class Cylinder extends Tube {
    //height of cylinder
    private final double height;

    /**
     * ctor of parameters
     *
     * @param axisRay center ray of the cylinder
     * @param radius  radius of the cylinder
     * @param height  height of the cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * height getter
     *
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
        Point base1Point = axisRay.getP0();
        Vector dir = axisRay.getDir();
        double t;
        try {
            t = pnt.subtract(base1Point).dotProduct(dir);
        } catch (IllegalArgumentException ignore) {
            t = 0;
        }
        if (isZero(t) || isZero(t - height)) return dir;

        return super.getNormal(pnt);
    }


}
