package primitives;

/**
 * A ray in the cartesian system
 */
public class Ray {
   private final Point p0;
   private final Vector dir;

    /**
     * Creates a new Ray
     * @param p0 the starting point of the ray
     * @param dir the direction of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Return the starting point of the ray
     * @return the starting point of the ray
     */
    public Point getP0() {
        return this.p0;
    }

    /**
     * Return the direction of the Ray
     * @return the direction of the Ray
     */
    public Vector getDir() {
        return this.dir;
    }

    @Override
    public String toString() {
        return "starting point " + p0 +
                " with the direction of" + dir;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray other = (Ray)obj;
        return this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    public Point getPoint(double t){
        return p0.add(dir.scale(t));
    }
}
