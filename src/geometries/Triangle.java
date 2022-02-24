package geometries;

import primitives.Point;

import java.util.List;

public class Triangle extends Polygon{
    public Triangle(Point... vertices) {
        super(vertices);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}
