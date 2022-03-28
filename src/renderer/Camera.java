package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
     * java doc here
     *
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return Ray that goes through the requested pixel in the view plane
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pIJ = p0.add(vTo.scale(distance));
        double Ry = height/nY;
        double Rx = width/nX;
        double yI = -(i-((double)(nY-1))/2)*Ry;
        double xJ = -(j-((double)(nX-1))/2)*Rx;

        if(xJ!=0) {pIJ = pIJ.add(vRight.scale(xJ));}
        if(yI!=0) {pIJ = pIJ.add(vUp.scale(yI));}

        return new Ray(p0,pIJ.subtract(p0).normalize());

    }

    ;
}
