package geometries;

import primitives.Point;

import java.util.List;

public class Triangle extends Polygon{
    public Triangle(List<Point> vertices, Plane plane) {
        super(vertices, plane);
    }
}
