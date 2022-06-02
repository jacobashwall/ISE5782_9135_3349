package geometries;


import primitives.Double3;
import primitives.Ray;
import scene.Scene;


import java.util.*;

/**
 * Class the represents a group of geometric entities
 */
public class Geometries extends Intersectable {
    //we will use linked list since adding a new object to the list is a lot faster than array list.
    //Also, we will never try to access an object in a specific index, but we will always scan all the array.
    //Meaning there are no drawbacks using linked list, but we would have the best running time.
    private final List<Intersectable> objects = new LinkedList<>();

    /**
     * Ctor that gets objects and adds them to the list of objects.
     *
     * @param geometries geometric entities.
     */
    public Geometries(Intersectable... geometries) {
        this.add(geometries);
        this.calcVolume();
    }

    /**
     * Adds several geometric entities to the list of objects.
     *
     * @param geometries geometric entities.
     */
    public void add(Intersectable... geometries) {
        if (geometries.length > 0) this.objects.addAll(List.of(geometries));
        for (var geometry:geometries) {
            this.volume+=geometry.getVolume();
        }
    }


    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (var geometry : objects) {
            List<GeoPoint> returnList = geometry.findGeoIntersections(ray, maxDistance);
            if (returnList != null) { //if it's not null (there are intersections)
                if (intersections == null)
                    intersections = new LinkedList<>(returnList);
                else
                    intersections.addAll(returnList);
            }
        }
        return intersections;
    }

    @Override
    public int[][] calcBoundary() {
        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        for (var geometry : objects) {
            if (geometry.boundary[0][0] < minX)
                minX = geometry.boundary[0][0];
            if (geometry.boundary[0][1] > maxX)
                maxX = geometry.boundary[0][1];
            if (geometry.boundary[1][0] < minY)
                minY = geometry.boundary[1][0];
            if (geometry.boundary[1][1] > maxY)
                maxY = geometry.boundary[1][1];
            if (geometry.boundary[2][0] < minZ)
                minZ = geometry.boundary[2][0];
            if (geometry.boundary[2][1] > maxZ)
                maxZ = geometry.boundary[2][1];
        }
        return new int[][]{{(int) minX, (int) Math.ceil(maxX)},
                {(int) minY, (int) Math.ceil(maxY)},
                {(int) minZ, (int) Math.ceil(maxZ)}};
    }

    public int getObjectsSize() {
        return this.objects.size();
    }

    @Override
    protected void calcVolume() {
        for (var geometry : objects) {
            this.volume += geometry.getVolume();
        }
    }

    /**
     * move over all geometric entities of a scene and return a hashmap of all the none empty voxels
     * @param scene the scene
     * @return the hash map of voxels
     */
    public HashMap<Double3, Geometries> attachVoxel(Scene scene) {
        HashMap<Double3,Geometries> voxels=new HashMap<>();
        List<Double3> voxelIndexes;
        for (var geometry : objects) {
            voxelIndexes = geometry.findVoxels(scene);
            for (var index : voxelIndexes) {
                if (!voxels.containsKey(index))//the voxel is already exists in thr map
                    voxels.put(index, new Geometries(geometry));
                else {
                    voxels.get(index).add(geometry);
                }
            }
        }
        return voxels;
    }

}

