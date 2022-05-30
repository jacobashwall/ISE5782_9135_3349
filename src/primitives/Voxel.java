package primitives;

import geometries.Geometries;

/**
 * cubes that divide the scene in order to implement regular grid
 */
public class Voxel {
    /**
     * the voxel index
     */
    public final Double3 index;
    /**
     * all geometric entities that intersects with the voxel
     */
    public Geometries geometries = new Geometries();
}
