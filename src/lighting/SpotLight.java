package lighting;

import primitives.*;


public class SpotLight extends PointLight{

    private Vector direction;

    /**
     * Constructor with parameters
     *
     * @param p     point for the position field
     * @param color color for the intensity field in the base class
     */
    public SpotLight(Color color, Point p,Vector direction) {
        super(color, p);
        this.direction = direction.normalize();
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(Math.max(0,this.direction.dotProduct(this.getL(p))));
    }

}
