package lighting;

import primitives.*;

/**
 * Class that extends the class PointLight and represent spot light
 */
public class SpotLight extends PointLight{

    private Vector direction;
    private double narrow;

    /**
     * Constructor with parameters
     *
     * @param p     point for the position field
     * @param color color for the intensity field in the base class
     */
    public SpotLight(Color color, Point p,Vector direction) {
        super(color, p);
        this.direction = direction.normalize();
        this.narrow = 1;
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(Math.pow(Math.max(0,this.direction.dotProduct(super.getL(p))),narrow));
    }

    /**
     * Determines the width of the spotlight
     * @param narrow
     * @return the object itself
     */
    public PointLight setNarrowBeam(double narrow) {
        this.narrow = narrow;
        return this;
    }
}
