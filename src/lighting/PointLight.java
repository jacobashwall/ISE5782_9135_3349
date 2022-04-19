package lighting;

import primitives.*;
/**
 * Class that represents a light spot that has on origin in a specific point.
 * Affected by all three attenuation coefficients.
 * Extends the abstract class Light and implements the interface LightSource
 */
public class PointLight extends Light implements LightSource{
    //private fields

    private Point position;// position of the light source
    private double kC;//Attenuation coefficient
    private double kL;//Attenuation coefficient
    private double kQ;//Attenuation coefficient

    /**
     * Constructor with parameters
     * @param p point for the position field
     * @param color color for the intensity field in the base class
     */
    public PointLight(Color color, Point p){
        super(color);
        this.position = p;
        //Default values
        this.kC = 1;
        this.kL = 0;
        this.kQ = 0;
    }

    /**
     * Setter for the kC field
     * @param kC parameter for the kC field
     * @return the object itself
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Setter for the kL field
     * @param kL parameter for the kL field
     * @return the object itself
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Setter for the kQ field
     * @param kQ parameter for the kQ field
     * @return the object itself
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }


    @Override
    public Color getIntensity(Point p) {
        double d = p.distance(this.position);
        return this.getIntensity().reduce(kC +kL*d +kQ*d*d);
    }


    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();
    }
}
