package lighting;

import primitives.*;

import static primitives.Util.alignZero;

/**
 * Class that extends the class PointLight and represent spot light
 */
public class SpotLight extends PointLight {
    private final Vector direction;
    private int narrow;

    /**
     * Constructor with parameters
     *
     * @param p     point for the position field
     * @param color color for the intensity field in the base class
     */
    public SpotLight(Color color, Point p, Vector direction) {
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
        double lDir = alignZero(this.direction.dotProduct(super.getL(p)));
        if (lDir <= 0) return Color.BLACK;

        return super.getIntensity(p).scale(Math.pow(lDir, narrow));
    }

    /**
     * Determines the width of the spotlight
     *
     * @param narrow narrowness of spot beam (power of cosine)
     * @return the object itself
     */
    public PointLight setNarrowBeam(int narrow) {
        this.narrow = narrow;
        return this;
    }
}
