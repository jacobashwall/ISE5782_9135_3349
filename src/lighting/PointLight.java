package lighting;

import primitives.*;

/**
 * Class that represents a light spot that has on origin in a specific point.
 * Affected by all three attenuation coefficients.
 * Extends the abstract class Light and implements the interface LightSource
 */
public class PointLight extends Light implements LightSource {
    //private fields

    private Point position;// position of the light source
    private Double3 kC = new Double3(1);//Attenuation coefficient
    private Double3 kL = Double3.ZERO;//Attenuation coefficient
    private Double3 kQ = Double3.ZERO;//Attenuation coefficient

    /**
     * Constructor with parameters
     *
     * @param p     point for the position field
     * @param color color for the intensity field in the base class
     */
    public PointLight(Color color, Point p) {
        super(color);
        this.position = p;

    }

    /**
     * Setter for the kC field
     *
     * @param kC parameter for the kC field
     * @return the object itself
     */
    public PointLight setKc(double kC) {
        this.kC = new Double3(kC);
        return this;
    }

    /**
     * Setter for the kL field
     *
     * @param kL parameter for the kL field
     * @return the object itself
     */
    public PointLight setKl(double kL) {
        this.kL = new Double3(kL);
        return this;
    }

    /**
     * Setter for the kQ field
     *
     * @param kQ parameter for the kQ field
     * @return the object itself
     */
    public PointLight setKq(double kQ) {
        this.kQ = new Double3(kQ);
        return this;
    }


    @Override
    public Color getIntensity(Point p) {
        double d = p.distance(this.position);
        return this.intensity.reduce(kC.add(kL.scale(d)).add(kQ.scale(d * d)));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return point.distance(position);
    }
}
