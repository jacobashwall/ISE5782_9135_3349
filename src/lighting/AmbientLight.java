package lighting;

import primitives.*;

/**
 * Class that represents an ambient light.
 * Ambient light is an omni-directional, fixed intensity and fixed color type of light.
 */
public class AmbientLight {

    //private field
    private Color intensity;

    /**
     * Constructor that takes a Color object and an attenuation coefficient (Double3) and return
     * the color object scaled by the attenuation coefficient.
     * @param color Color of the ambient light
     * @param double3 Attenuation coefficient
     */
    public AmbientLight(Color color, Double3 double3){
        this.intensity = color;
        this.intensity.scale(double3);
    }

    /**
     * Default constructor, set the intensity field to Color.BLACK
     */
    public AmbientLight(){
        this.intensity = Color.BLACK;
    }

    /**
     * Intensity field getter
     * @return intensity (Color)
     */
    public Color getIntensity(){
        return this.intensity;
    }
}
