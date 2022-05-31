package geometries;


import primitives.Ray;


import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

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
    }

    /**
     * Adds several geometric entities to the list of objects.
     *
     * @param geometries geometric entities.
     */
    public void add(Intersectable... geometries) {
        if (geometries.length > 0) this.objects.addAll(List.of(geometries));
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

    /*@Override
    public double[][] calcBoundary() {
        double minX = 0;
        double maxX = 0;
        double minY = 0;
        double maxY = 0;
        double minZ = 0;
        double maxZ = 0;
        for (var geometry : objects) {
            if (objects.get(0).equals(geometry)) {//if it's the first geometry in order to compare
                minX = geometry.boundary[0][0];
                maxX = geometry.boundary[0][1];
                minY = geometry.boundary[1][0];
                maxY = geometry.boundary[1][1];
                minZ = geometry.boundary[2][0];
                maxZ = geometry.boundary[2][1];
            } else {
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
        }
        return new double[][]{{minX, maxX}, {minY, maxY}, {minZ, maxZ}};
    }*/

    @Override
    public double[][] calcBoundary() {
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
        return new double[][]{{minX, maxX}, {minY, maxY}, {minZ, maxZ}};


    }

    public int getObjectsSize(){
        return this.objects.size();
    }

    @Override
    protected void calcVolume() {
        for(var geometry:objects){
            this.volume+=geometry.getVolume();
        }
    }
}
