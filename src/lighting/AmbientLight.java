package lighting;

import primitives.*;

import java.util.Map;

/**
 * Class that represents an ambient light, extends the abstract class Light.
 * Ambient light is an omnidirectional, fixed intensity and fixed color type of light.
 */
public class AmbientLight extends Light {

    /**
     * Constructor that takes a Color object and an attenuation coefficient (Double3) and calculates
     * the color object scaled by the attenuation coefficient, by calling
     * the base constructor.
     *
     * @param color Color of the ambient light
     * @param k     Attenuation coefficient
     */
    public AmbientLight(Color color, Double3 k) {
        super(color.scale(k));
    }

    /**
     * Default constructor, set the intensity field to Color. BLACK
     */
    public AmbientLight() {
        super(Color.BLACK);
    }
}

