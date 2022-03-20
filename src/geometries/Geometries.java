package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
    private List<Intersectable> objects;

    /**
     * Default ctor of Geometries.
     */
    public Geometries() {
        //we will use linked list since adding a new object to the list is a lot faster than array list.
        //Also, we will never try to access an object in a specific index, but we will always scan all the array.
        //Meaning there are no drawbacks using linked list, but we would have the best running time.
        this.objects= new LinkedList();
    }

    /**
     * Ctor that gets objects and adds them to the list of objects.
     * @param geometries geometric entities.
     */
    public Geometries(Intersectable... geometries){
        this.objects= new LinkedList();
        this.objects.addAll(List.of(geometries));
    }

    /**
     * Adds several geometric entities to the list of objects.
     * @param geometries geometric entities.
     */
    public void add(Intersectable... geometries){
        this.objects.addAll(List.of(geometries));
    }
    @Override
    public List<Point> findIntersections(Ray ray){return null;}
}
