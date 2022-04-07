package lighting;

import primitives.*;

import java.util.Map;

/**
 * Class that represents an ambient light.
 * Ambient light is an omnidirectional, fixed intensity and fixed color type of light.
 */
public class AmbientLight {

    //private field
    private final Color intensity;

    /**
     * Constructor that takes a Color object and an attenuation coefficient (Double3) and return
     * the color object scaled by the attenuation coefficient.
     *
     * @param color Color of the ambient light
     * @param k     Attenuation coefficient
     */
    public AmbientLight(Color color, Double3 k) {
        this.intensity = color.scale(k);
    }

    public static AmbientLight ReadAmbientLight(Map<String, String> AmbientLightAttributes) {
        if (AmbientLightAttributes == null)
            return new AmbientLight();
        String[] colorAttributes = AmbientLightAttributes.get("color").split("\\s+");
        Color color = new Color(
                Double.parseDouble(colorAttributes[0]),
                Double.parseDouble(colorAttributes[1]),
                Double.parseDouble(colorAttributes[2]));
        String[] factorAttributes = AmbientLightAttributes.get("k").split("\\s+");
        Double3 k;
        if (factorAttributes.length == 1)//using the constructor that uses only one variable
            k = new Double3(Double.parseDouble(factorAttributes[0]));
        else//using 3 values constructor
            k = new Double3(Double.parseDouble(factorAttributes[0]),
                    Double.parseDouble(factorAttributes[1]),
                    Double.parseDouble(factorAttributes[2]));
        return new AmbientLight(color, k);
    }

    /**
     * Default constructor, set the intensity field to Color. BLACK
     */
    public AmbientLight() {
        this.intensity = Color.BLACK;
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
