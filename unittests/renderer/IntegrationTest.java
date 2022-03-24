package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test the integration between the constructRay and geometric entities.
 */
class IntegrationTest {


    /**
     * Determine how many intersection points suppose to have with a geometric entity.
     *
     * @param cam      the camera that we construct ray from.
     * @param geo      the geometric entity that we are testing intersection with.
     * @param expected the expected amount of intersection points.
     * @param test     the test that called this function.
     */
    private void assertCountIntersections(Camera cam, Intersectable geo, int expected, String test) {
        //counts how many intersection points.
        int count = 0;
        //the view plane is the size of 3x3 and a distance of 1 from the camera.
        cam.setVPSize(3, 3);
        cam.setVPDistance(1);
        //the resolution is 3x3.
        int nX = 3;
        int nY = 3;
        //the view plane is the size of 3x3 and a resolution of 3x3, therefore Rx = Ry = 1.
        //the values are taken from the slides.

        //A loop that iterates on each pixel.
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                //create a list of all the intersection points with the geometric entity through the pixel.
                List<Point> intersections = geo.findIntersections(cam.constructRay(nX, nY, j, i));
                //adds the length of the list, the amount of intersection points.
                count += ((intersections == null) ? 0 : intersections.size());
            }
        }
        assertEquals(expected, count, "Wrong amount of intersections with "+geo.getClass()+": "+ test);
    }

    @Test
    public void cameraRayTriangleIntegration() {
        // most of the values are taken from the slides
        Camera cam = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0));

        // TC01: Small triangle in front of the view plane, 1 point of intersection.
        assertCountIntersections(cam, new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -1, -2)), 1, "TC01");

        // TC02: Large triangle in front of the view plane, 2 points of intersection.
        assertCountIntersections(cam, new Triangle(new Point(1, 1, -2), new Point(-1, 1, -2), new Point(0, -20, -2)), 2, "TC02");
    }

    @Test
    public void cameraRayPlaneIntegration() {
        // most of the values are taken from the slides
        Camera cam = new Camera(Point.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0));

        // TC01: Plane against the camera, parallel to the view plane, 9 points of intersection.
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 0, 1)), 9, "TC01");

        // TC02: Plane has acute angle to the view plane, all rays intersect, 9 points of intersection.
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 2)), 9, "TC02");

        // TC03: Plane has obtuse angle to the view plane, parallel to lower rays , 6 points of intersection.
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 1)), 6, "TC03");

        // TC04: Plane beyond the view plane, 0 points of intersection.
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 1)), 6, "TC04");
    }

}