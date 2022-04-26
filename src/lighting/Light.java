package lighting;

import primitives.Color;

/**
 * An abstract that includes common properties of light sources
 *
 * @author Yonatan Dahary
 */
abstract class Light {
    protected final Color intensity;

    /**
     * Constructor to initialize the intensity field
     *
     * @param color The color for the intensity field
     */
    protected Light(Color color) {
        this.intensity = color;
    }

    /**
     * Intensity field getter
     *
     * @return intensity (Color)
     */
    public Color getIntensity() {
        return this.intensity;
    }
}
