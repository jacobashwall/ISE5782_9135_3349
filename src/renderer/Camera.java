package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.io.Console;
import java.util.MissingResourceException;

/**
 * Camera to take the picture
 */
public class Camera {
    //position
    private Point p0;
    //rotation
    private Vector vUp;
    private Vector vRight;
    private Vector vTo;
    //view plane attributes
    double width;
    double height;
    double distance;

    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;

    private boolean baseOrRegular;

    public Camera setBaseOrRegular(boolean baseOrRegular) {
        this.baseOrRegular = baseOrRegular;
        if(baseOrRegular){
            this.rayTracerBase = new RayTracerRegular(this.rayTracerBase.scene);
        }
        return this;
    }

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
     * @param rayTracerBase a Ray Tracer Base
     * @return the camera with modified Ray Tracer Base
     */
    public Camera setRayTracer(RayTracerBasic rayTracerBase) {
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
        Point pIj = p0.add(vTo.scale(distance));
        //height of each pixel
        double rY = height / nY;
        //width of each pixel
        double rX = width / nX;
        //vertical distance of the required pixel from the center of the view plane
        double yI = -(i - ((double) (nY - 1)) / 2) * rY;
        //horizontal distance of the required pixel from the center of the view plane
        double xJ = -(j - ((double) (nX - 1)) / 2) * rX;

        //changing the position of the center point so that the ray will intersect the view plane in the right place
        if (xJ != 0) {
            pIj = pIj.add(vRight.scale(xJ));
        }
        if (yI != 0) {
            pIj = pIj.add(vUp.scale(yI));
        }

        //return the ray
        return new Ray(p0, pIj.subtract(p0).normalize());
    }


    /**
     * Given double theta, rotate the camera's up and right vectors by theta degrees
     *
     * @param theta the angle of rotation in degrees clockwise
     * @return the rotated camera.
     */
    public Camera turnCamera(double theta) {
        if (theta == 0) return this; //there is nothing to turn
        this.vUp = this.vUp.rotateVector(this.vTo, theta);
        this.vRight = this.vRight.rotateVector(this.vTo, theta);
        return this;
    }

    /**
     * Moves the camera to a certain location to point to a single point
     *
     * @param from the camera's new location
     * @param to   the point the camera points to
     * @return the moved camera
     */
    public Camera moveCamera(Point from, Point to) {
        Vector vec;
        try {
            vec = to.subtract(from);
        } catch (IllegalArgumentException ignore) {
            throw new IllegalArgumentException("The camera cannot point at its starting location");
        }
        this.p0 = from;
        this.vTo = vec.normalize();
        //in order to determine Vup, we will find the intersection vector of two planes, the plane that Vto is represented
        //as its normal, and the plane that includes the Y axis and the Vto vector (as demanded in the instructions).

        //if the Vto is already on the Y axis, we will use the Z axis instead
        if (this.vTo.equals(Vector.Y) || this.vTo.equals(Vector.Y.scale(-1))) {
            this.vUp = (vTo.crossProduct(Vector.Z)).crossProduct(vTo).normalize();
        } else {
            this.vUp = (vTo.crossProduct(Vector.Y)).crossProduct(vTo).normalize();
        }
        this.vRight = vTo.crossProduct(vUp).normalize();
        return this;
    }

    /**
     * Checks if there are any empty camera fields
     */
    public void renderImage() {
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
        //move over the coordinates of the grid
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nX; i++) {
            System.out.println(i+"/"+nX);
            for (int j = 0; j < nY; j++) {
                //get the ray through the pixel

                if(i==12&&j==23){
                    int u = 5;
                    //color=new Color(255,0,0);
                }

                Ray ray = this.constructRay(nX, nY, j, i);
                Color color = rayTracerBase.traceRay(ray);





                imageWriter.writePixel(j, i, color);
            }
        }
    }


    /**
     * Prints a grid
     *
     * @param interval the interval of the distance between ech grid line
     * @param color    the color of the grid
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("One of the camera's attributes are missing", "imageWriter", "7");
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        //move over the coordinates of the grid
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                //Coordinates of the net

                if (i % interval == 0 || j % interval == 0 || i == nX - 1 || j == nY - 1) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * uses the writeToImage function by delegation of imageWriter
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("One of the camera's attributes are missing", "imageWriter", "7");
        imageWriter.writeToImage();
    }

}
