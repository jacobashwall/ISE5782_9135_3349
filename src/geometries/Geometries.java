package geometries;


import primitives.Ray;


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
}
