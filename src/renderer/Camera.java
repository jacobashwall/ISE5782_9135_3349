package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

/**
 * Camera to take the picture
 */
public class Camera {
    //position
    Point p0;
    //rotation
    private Vector vUp, vRight, vTo;
    //view plane attributes
    double width, height, distance;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;


    /**
     * Constructor to camera
     *
     * @param p0  camera position
     * @param vUp camera upward vector
     * @param vTo camera front vector
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        //check if vUp and Vto are orthogonal.
        if (vUp.dotProduct(vTo) != 0) //if not, throw.
            throw new IllegalArgumentException("vUp and vTo are not orthogonal");
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        this.vRight = (vUp.crossProduct(vTo)).normalize();
    }

    /**
     * imageWriter setter
     *
     * @param imageWriter an image Writer
     * @return the camera with modified image Writer
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * RayTracerBase setter
     *
     * @param RayTracerBase a Ray Tracer Base
     * @return the camera with modified Ray Tracer Base
     */
    public Camera setRayTracerBase(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    /**
     * V up getter
     *
     * @return V up vector
     */
    public Vector getVup() {
        return vUp;
    }

    /**
     * V to getter
     *
     * @return V to vector
     */
    public Vector getVto() {
        return vTo;
    }

    /**
     * V right getter
     *
     * @return V right vector
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * P0 getter
     *
     * @return camera's position
     */
    public Point getP0() {
        return p0;
    }

    /**
     * height getter
     *
     * @return view plane's height
     */
    public double getHeight() {
        return height;
    }

    /**
     * width getter
     *
     * @return view plane's width
     */
    public double getWidth() {
        return width;
    }

    /**
     * distance getter
     *
     * @return view plane's distance from the camera
     */
    public double getDistance() {
        return distance;
    }

    /**
     * View plane size setter
     *
     * @param width  view plane's width
     * @param height view plane's height
     * @return the camera with changed view plane size
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * View plane's distance setter
     *
     * @param distance distance from camera
     * @return the camera with changed view plane distance
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * A method that generates a ray, starting at the point and going through
     * specific pixel in the view plane. Gets the resolution of the view plane and
     * the coordinates of the requested pixel as parameters.
     *
     * @param nX Horizontal component of the resolution. Number of partitions for the horizontal axis in the view plane.
     * @param nY Vertical component of the resolution. Number of partitions for the vertical axis in the view plane.
     * @param j  The horizontal index of the pixel
     * @param i  The vertical index of the pixel
     * @return Ray that goes through the requested pixel in the view plane
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //Center of the view plane
        Point pIJ = p0.add(vTo.scale(distance));
        //height of each pixel
        double Ry = height / nY;
        //width of each pixel
        double Rx = width / nX;
        //vertical distance of the required pixel from the center of the view plane
        double yI = -(i - ((double) (nY - 1)) / 2) * Ry;
        //horizontal distance of the required pixel from the center of the view plane
        double xJ = -(j - ((double) (nX - 1)) / 2) * Rx;

        //changing the position of the center point so that the ray will intersect the view plane in the right place
        if (xJ != 0) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (yI != 0) {
            pIJ = pIJ.add(vUp.scale(yI));
        }

        //return the ray
        return new Ray(p0, pIJ.subtract(p0).normalize());

    }


    /**
     * Given a vector axis and a double theta, rotate the camera's up, right, and to vectors by theta radians about axis
     *
     * @param axis  the axis about which the camera will be rotated
     * @param theta the angle of rotation in radians
     * @return the rotated camera.
     */
    public Camera turnCamera(Vector axis, double theta) {
        if (theta == 0) return this; //there is nothing to turn
        this.vUp = this.vUp.rotateVector(axis, theta);
        this.vRight = this.vRight.rotateVector(axis, theta);
        this.vTo = this.vTo.rotateVector(axis, theta);
        return this;
    }


    /**
     * Move the camera by the given amounts
     *
     * @param up    the distance to move the camera up
     * @param right the distance to move right
     * @param to    the distance to move the camera in the direction of the to vector
     * @return the moved camera.
     */
    public Camera moveCamera(double up, double right, double to) {
        if (up == 0 && right == 0 && to == 0) return this; //there is nothing to move
        if (up != 0) this.p0.add(this.vUp.scale(up));
        if (right != 0) this.p0.add(this.vRight.scale(right));
        if (to != 0) this.p0.add(this.vTo.scale(to));
        return this;
    }

    /**
     * Checks if there are any empty camera fields
     */
    public void renderImage() {
        if (p0 == null)
            throw new MissingResourceException("One of the camera's attributes are missing", "p0", "0");
        if (vUp == null)
            throw new MissingResourceException("One of the camera's attributes are missing", "vUp", "1");
        if (vRight == null)
            throw new MissingResourceException("One of the camera's attributes are missing", "vRight", "2");
        if (vTo == null)
            throw new MissingResourceException("One of the camera's attributes are missing", "vTo", "3");
        if (width == 0)
            throw new MissingResourceException("One of the camera's attributes are missing", "width", "4");
        if (height == 0)
            throw new MissingResourceException("One of the camera's attributes are missing", "height", "5");
        if (distance == 0)
            throw new MissingResourceException("One of the camera's attributes are missing", "distance", "6");
        if (imageWriter == null)
            throw new MissingResourceException("One of the camera's attributes are missing", "imageWriter", "7");
        if (rayTracerBase == null)
            throw new MissingResourceException("One of the camera's attributes are missing", "rayTracerBase", "8");
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param interval
     * @param color
     */
    public void printGrid(int interval, Color color){
        if (imageWriter == null)
            throw new MissingResourceException("One of the camera's attributes are missing", "imageWriter", "7");
        //wait for yonatan
    }

    /**
     *
     */
    public void writeToImage(){
        if (imageWriter == null)
            throw new MissingResourceException("One of the camera's attributes are missing", "imageWriter", "7");
        //wait for yonatan
    }

}
