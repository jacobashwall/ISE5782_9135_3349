package lighting;

import primitives.*;

/**
 * An interface for different kinds of lighting. All of them should implement getIntensity and getL.
 *
 * @author Yonatan Dahary
 */
public interface LightSource {
    /**
     * Returns the intensity of the light (color) of a light source in a specific point
     *
     * @param p The point for the intensity calculation
     * @return Color object that represent the color (intensity) of the point p
     * @author Yonatan Dahary
     */
    public Color getIntensity(Point p);

    /**
     * Returns the vector from the light source to the point
     *
     * @param p The point which we want the vector to
     * @return Vector from the light source to the point
     */
    public Vector getL(Point p);


    /**
     * Returns the distance between the given point and the light source
     * @param point point to calculate the distance to
     * @return distance of the light source from the point
     */
    public double getDistance(Point point);
}
