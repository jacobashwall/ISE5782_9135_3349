package scene;

import lighting.AmbientLight;
import geometries.Geometries;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * The class Scene is a passive data structure (PDS)
 * its purpose is to bind the different components that
 * usually make up a scene. It has no functionality of itself.
 */

public class Scene {
    //public fields

    //Name of the scene
    public String name;
    //Background color of the scene
    public Color background;
    //The ambient light in the scene
    public AmbientLight ambientLight;
    //The geometric bodies in the scene
    public Geometries geometries;
    //List of light sources
    public List<LightSource> lights;

    /**
     * Constructor that sets the scene name and sets the other fields to their default values
     *
     * @param name The name of the scene
     */
    public Scene(String name) {
        this.name = name;
        //Default values
        this.background = Color.BLACK;
        this.ambientLight = new AmbientLight();
        this.geometries = new Geometries();
        this.lights = new LinkedList<>();
    }

    //Since this class is just a PDS, it is essential
    //that it will include setters. In each setter we return 'this'
    //so that we will be able to concatenate the setters.

    /**
     * Set the background of the scene
     *
     * @param background background of the scene
     * @return The object itself
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Set the ambientLight of the scene
     *
     * @param ambientLight ambientLight of the scene
     * @return The object itself
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Set the geometric bodies of the scene
     *
     * @param geometries geometric bodies of the scene
     * @return The object itself
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Set the list of light sources in the scene
     *
     * @param lights geometric bodies of the scene
     * @return The object itself
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
