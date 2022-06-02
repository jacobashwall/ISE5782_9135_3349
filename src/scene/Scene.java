package scene;

import lighting.*;
import geometries.Geometries;
import primitives.*;

import java.util.HashMap;
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
    public final String name;
    //Background color of the scene
    public Color background = Color.BLACK;
    //The ambient light in the scene
    public AmbientLight ambientLight = new AmbientLight();
    //The geometric bodies in the scene
    public Geometries geometries = new Geometries();
    //List of light sources
    public List<LightSource> lights = new LinkedList<>();

    //scene geometric attributes
    /**
     * the size of the edge of the scene boundary on the X axis
     */
    private int xEdgeScene;
    /**
     * the size of the edge of the scene boundary on the Y axis
     */
    private int yEdgeScene;
    /**
     * the size of the edge of the scene boundary on the Z axis
     */
    private int zEdgeScene;
    /**
     * the resolution of the voxels that divide the scene
     */
    private double[] resolutions;

    //voxel attributes
    /**
     * hash map of all voxels in the scene- their index as the key and the list of geometric entities that intersects
     * with the voxel as the value.
     */
    public HashMap<Double3, Geometries> voxels = new HashMap<>();
    /**
     * the size of the edge of the voxel on the X axis
     */
    private double xEdgeVoxel;
    /**
     * the size of the edge of the voxel on the Y axis
     */
    private double yEdgeVoxel;
    /**
     * the size of the edge of the voxel on the Z axis
     */
    private double zEdgeVoxel;

    /**
     * Constructor that sets the scene name and sets the other fields to their default values
     *
     * @param name The name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * xEdgeVoxel getter
     *
     * @return the size of the edge of the voxel on the X axis
     */
    public double getXEdgeVoxel() {
        return xEdgeVoxel;
    }

    /**
     * yEdgeVoxel getter
     *
     * @return the size of the edge of the voxel on the Y axis
     */
    public double getYEdgeVoxel() {
        return yEdgeVoxel;
    }

    /**
     * zEdgeVoxel getter
     *
     * @return the size of the edge of the voxel on the Z axis
     */
    public double getZEdgeVoxel() {
        return zEdgeVoxel;
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

    /**
     * calculates what voxels the scene has and the attributes of the voxels
     */
    public void calcVoxels() {
        this.setBoundary();
        this.setResolution();
        this.setVoxelsEdges();
        this.setVoxelsGeometries();
    }

    /**
     * sets the boundary of the geometries in the scene
     */
    private void setBoundary() {
        this.geometries.boundary = this.geometries.calcBoundary();
    }

    /**
     * sets the resolution of the scene to divide to voxels
     */
    private void setResolution() {
        double geometriesVolume = this.geometries.volume;
        double size = this.geometries.getObjectsSize();

        this.xEdgeScene = this.geometries.boundary[0][1] - this.geometries.boundary[0][0];
        this.yEdgeScene = this.geometries.boundary[1][1] - this.geometries.boundary[1][0];
        this.zEdgeScene = this.geometries.boundary[2][1] - this.geometries.boundary[2][0];

        double bigCbrVolume = xEdgeScene * yEdgeScene * zEdgeScene;
        if (bigCbrVolume == 0)
            bigCbrVolume = 1;//if the scene is not 3 dimensional
        double density = geometriesVolume / bigCbrVolume;
        double factor = Math.pow(density * size / bigCbrVolume, 1.0 / 3);

        int xResolution = (int) Math.ceil(xEdgeScene * factor);
        int yResolution = (int) Math.ceil(yEdgeScene * factor);
        int zResolution = (int) Math.ceil(zEdgeScene * factor);

        this.resolutions = new double[]{xResolution, yResolution, zResolution};
    }

    /**
     * sets the attributes of the voxels
     */
    private void setVoxelsEdges() {
        this.xEdgeVoxel = ((double) this.xEdgeScene) / resolutions[0];
        this.yEdgeVoxel = ((double) this.yEdgeScene) / resolutions[1];
        this.zEdgeVoxel = ((double) this.zEdgeScene) / resolutions[2];
    }

    /**
     * attaches the voxels to each geometric entity in the scene
     */
    private void setVoxelsGeometries() {
        this.voxels = this.geometries.attachVoxel(this);
    }
}
