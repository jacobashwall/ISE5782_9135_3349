package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class the represents a group of geometric entities
 */
public class Geometries extends Intersectable {
    //we will use linked list since adding a new object to the list is a lot faster than array list.
    //Also, we will never try to access an object in a specific index, but we will always scan all the array.
    //Meaning there are no drawbacks using linked list, but we would have the best running time.
    private List<Intersectable> objects = new LinkedList<>();

    /**
     * Default ctor of Geometries.
     */
    public Geometries() {
    }

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
        this.objects.addAll(List.of(geometries));
    }


    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = null;
        for (var geometry : objects) {
            List<Point> returnList = geometry.findIntersections(ray);
            if (returnList != null) { //if it's not null (there are intersections)
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(returnList);
            }
        }
        return intersections;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> intersections = null;
        for (var geometry : objects) {
            List<GeoPoint> returnList = geometry.findGeoIntersections(ray);
            if (returnList != null) { //if it's not null (there are intersections)
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(returnList);
            }
        }
        return intersections;
    }
}
