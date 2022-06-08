package renderer;

import geometries.*;
import geometries.Polygon;
import lighting.LightSource;
import primitives.*;
import primitives.Vector;
import scene.Scene;


import java.util.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Ray tracer that using the regular grid
 */
public class RayTracerRegular extends RayTracerBase {
    /**
     * the max number of times that the recursion is going to happen.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * the min difference that the recursion creates and makes it useless.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * the initial value of k factor
     */
    private static final double INITIAL_K = 1.0;

    /**
     * small number in order to move the ray
     */
    private final double EPSILON = 0.01;

    /**
     * construction the class with the given scene.
     *
     * @param scene the scene to pass on to the superclass.
     */
    public RayTracerRegular(Scene scene) {
        super(scene);
        scene.calcVoxels();
    }

    /**
     * tracing the ray and returns the color that the pixel needs to be painted with.
     *
     * @param ray the ray we need to get the color for.
     * @return the color of te pixel.
     */
    @Override
    public Color traceRay(Ray ray) {

        Intersectable.GeoPoint closestIntersection = traversalAlgorithm(ray);

        return closestIntersection == null ? scene.background : calcColor(closestIntersection, ray);
    }


    /**
     * calculate the color that needed to be returned from the pixel.
     *
     * @param gp  the point to calculate the color for.
     * @param ray the ray to pass to the function that summarise all the effects of the light sources.
     * @return the color to paint the pixel.
     */
    private Color calcColor(Intersectable.GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(INITIAL_K)));
    }


    /**
     * the entrance function to the recursive process of calculating the reflective effect and refractive effect.
     *
     * @param gp    the point of intersection that need the color calculation.
     * @param ray   the ray from the camera to that point.
     * @param level the remaining number of times to do the recursion.
     * @param k     the level of insignificance for the k.
     * @return the color of the pixel with all the refractions and reflections.
     */
    private Color calcColor(Intersectable.GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return level == 1 ? color : color.add(calcGlobalEffect(gp, ray, level, k));
    }

    /**
     * calculating a global effect color
     *
     * @param ray   the ray that intersects with the geometry.
     * @param level the remaining number of times to do the recursion.
     * @param k     the level of insignificance for the k.
     * @param kx    the attenuation factor of reflection or transparency
     * @return the calculated color.
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = kx.product(k);
        //calculate the reflected ray, and the color contribution to the point.
        Intersectable.GeoPoint gp = traversalAlgorithm(ray);
        return (gp == null || kkx.lowerThan(MIN_CALC_COLOR_K)) ? Color.BLACK
                : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * calculating the color by checking for global effects,
     * finds the next intersection points to calculate for the  recursion.
     *
     * @param gp    the point of the intersection.
     * @param ray   the ray that intersects with the geometry.
     * @param level the remaining number of times to do the recursion.
     * @param k     the level of insignificance for the k.
     * @return the calculated color.
     */
    private Color calcGlobalEffect(Intersectable.GeoPoint gp, Ray ray, int level, Double3 k) {
        //Variables for the function
        Vector v = ray.getDir();
        Vector normal = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        Ray reflectedRay = constructReflectedRay(normal, gp.point, v);
        Ray refractedRay = constructRefractedRay(normal, gp.point, v);
        Color diffSamplingSum = Color.BLACK;
        Color glossSamplingSum = Color.BLACK;

        //If diffusive glass
        if (material.kDg != 0) {
            //super sample the refracted ray
            LinkedList<Ray> diffusedSampling = Sampling.superSample(refractedRay, material.kDg, normal);
            //for each sampling ray calculate the global effect
            for (var secondaryRay : diffusedSampling) {
                diffSamplingSum = diffSamplingSum.add(calcGlobalEffect(secondaryRay, level, k, material.kT));
            }
            //take the average of the calculation for all sample rays
            diffSamplingSum = diffSamplingSum.reduce(diffusedSampling.size());
        }
        //If glossy surface
        if (material.kSg != 0) {
            //super sample the reflected ray
            LinkedList<Ray> glossySampling = Sampling.superSample(reflectedRay, material.kSg, normal);
            //for each sampling ray calculate the global effect
            for (var secondaryRay : glossySampling) {
                glossSamplingSum = glossSamplingSum.add(calcGlobalEffect(secondaryRay, level, k, material.kR));
            }
            //take the average of the calculation for all sample rays
            glossSamplingSum = glossSamplingSum.reduce(glossySampling.size());
        }

        //If diffusive and glossy return both of the results above
        if (material.kDg != 0 && material.kSg != 0) {
            return glossSamplingSum
                    .add(diffSamplingSum);
        }
        //else return the matching result
        else if (material.kDg + material.kSg > 0) {
            return material.kDg != 0 ? calcGlobalEffect(reflectedRay, level, k, material.kR).add(diffSamplingSum) :
                    calcGlobalEffect(refractedRay, level, k, material.kT).add(glossSamplingSum);
        }

        return calcGlobalEffect(reflectedRay, level, k, material.kR)
                .add(calcGlobalEffect(refractedRay, level, k, material.kT));
    }

    /**
     * Calculating the local lighting effects of different light sources
     *
     * @param intersection the intersection point of the ray parameter with the geometric body.
     *                     This method calculate the light intensity at this point.
     * @param ray          the ray that intersects the geometric body
     * @param k            the level of insignificance for the k.
     * @return Color calculated by the light sources
     */
    private Color calcLocalEffects(Intersectable.GeoPoint intersection, Ray ray, Double3 k) {
        // Getting the emission of the geometric body
        Color color = intersection.geometry.getEmission();

        Vector v = ray.getDir();//direction of the ray
        Vector n = intersection.geometry.getNormal(intersection.point);//normal to the geometric body in the intersection point

        //if the ray and the normal in the intersection point
        //orthogonal to each other, return just the emission.
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;

        Material material = intersection.geometry.getMaterial();//the material of the geometric body

        //go through all the light sources and calculate their
        //intersection at the point
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            //Check the angle to decide whether
            //to add the effect of the other light sources
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Double3 ktr = transparency(intersection, l, n, lightSource);
                //Here we deal with the transparency of the objects
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add( //
                            iL.scale(calcDiffusive(material, nl)//diffusive effect
                                    .add(calcSpecular(material, n, l, nl, v))));//specular effect
                }
            }
        }
        return color;
    }

    /**
     * Calculates the specular effect
     *
     * @param material material of the geometric object
     * @param n        the normal to the geometric object at the point
     * @param l        the ray of the light source
     * @param nl       the dot product of the light source ray direction and the normal to the geometric object at the point
     * @param v        the direction of the camera ray
     * @return the specular effect expressed by Double3 object
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        //the reflection of the light source vector (l)
        Vector r = l.subtract(n.scale(2 * nl));
        double minusVr = alignZero(-v.dotProduct(r));
        if (minusVr <= 0) return Double3.ZERO;

        //Calculation of the effect according to phong model
        return material.kS.scale(Math.pow(minusVr, material.nShininess));
    }

    /**
     * calculate the diffusive part of the light source.
     *
     * @param material the material.
     * @param nl       the direction degree between the direction vector of the light to the direction between the light and geometry.
     * @return the defensive coefficient.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(nl < 0 ? -nl : nl);
    }

    /**
     * Determining how much light reach a certain point, blocked by other objects
     *
     * @param gp          the point we want to calculate the transparency for
     * @param l           vector from the light source
     * @param n           normal to the point
     * @param lightSource the light source
     * @return the transparency of the point
     */
    private Double3 transparency(Intersectable.GeoPoint gp, Vector l, Vector n, LightSource lightSource) {
        Vector lightDirection = l.scale(-1); // from point to light source
        //We make sure to move the object by DELTA
        //int the correct direction
        //Create a new ray to check shadiness
        Ray lightRay = new Ray(gp.point, lightDirection, n);

        Double3 ktr = Double3.ONE;
        //Find if any geometric object blocks the light
        List<Intersectable.GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(lightRay.getP0()));
        if (intersections == null)
            return ktr;
        //For every geometric object in the list, scale by its transparency coefficient
        for (var geoPoint : intersections) {
            ktr = ktr.product(geoPoint.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
        }
        return ktr;
    }


    /**
     * the function calculate the reflected ray.
     *
     * @param normal          the normal vector.
     * @param intersection    the in intersection point.
     * @param incomeRayVector the income vector ray,
     * @return the reflected ray.
     */
    private Ray constructReflectedRay(Vector normal, Point intersection, Vector incomeRayVector) {
        double scale = -2 * incomeRayVector.dotProduct(normal);
        return new Ray(intersection, incomeRayVector.add(normal.scale(scale)), normal);
    }

    /**
     * return ray in the same direction but starts in the intersection point.
     *
     * @param normal          the normal vector.
     * @param intersection    the intersection point.
     * @param incomeRayVector the income vector.
     * @return the fractured ray.
     */
    private Ray constructRefractedRay(Vector normal, Point intersection, Vector incomeRayVector) {
        return new Ray(intersection, incomeRayVector, normal);
    }


    /**
     * finds the closest intersection GeoPoint to the base of the ray
     *
     * @param ray the ray that we find intersection from
     * @return the closest intersection GeoPoint
     */
    private Intersectable.GeoPoint findClosestIntersection(Ray ray, Geometries geometries) {
        return ray.findClosestGeoPoint(geometries.findGeoIntersections(ray));
    }

    /**
     * this function implements the 3dda algorithm. It determines through which voxels the ray goes.
     *
     * @param ray the ray through the scene voxels grid
     * @return the first intersection GeoPoint
     */
    private Intersectable.GeoPoint traversalAlgorithm(Ray ray) {
        Point firstIntersection = firstIntersection(ray);
        if (firstIntersection == null) return null;
        Vector dir = ray.getDir();
        int[][] boundary = scene.geometries.boundary;
        Point newFirstIntersection = fixPoint(firstIntersection, boundary);
        Point index = VoxelByPoint(newFirstIntersection, boundary);

        int indexX = (int) index.getX();
        int indexY = (int) index.getY();
        int indexZ = (int) index.getZ();

        double dirX = dir.getX();
        double dirY = dir.getY();
        double dirZ = dir.getZ();

        //the direction of the ray on each axis
        int stepX = determineDirection(dirX);
        int stepY = determineDirection(dirY);
        int stepZ = determineDirection(dirZ);

        //find the closet exit from the voxel in order to find the next voxel in teh ray's way
        double tMaxX = 0;
        double tMaxY = 0;
        double tMaxZ = 0;
        if (stepX == 1) {
            tMaxX = Math.abs((boundary[0][0] + (indexX + 1) * scene.getXEdgeVoxel() - firstIntersection.getX()) / dirX);
        } else if (stepX == -1) {
            tMaxX = Math.abs((firstIntersection.getX() - (boundary[0][0] + indexX * scene.getXEdgeVoxel())) / dirX);
        }
        if (stepY == 1) {
            tMaxY = Math.abs((boundary[1][0] + (indexY + 1) * scene.getYEdgeVoxel() - firstIntersection.getY()) / dirY);
        } else if (stepY == -1) {
            tMaxY = Math.abs((firstIntersection.getY() - (boundary[1][0] + indexY * scene.getYEdgeVoxel())) / dirY);
        }
        if (stepZ == 1) {
            tMaxZ = Math.abs((boundary[2][0] + (indexZ + 1) * scene.getZEdgeVoxel() - firstIntersection.getZ()) / dirZ);
        } else if (stepZ == -1) {
            tMaxZ = Math.abs((firstIntersection.getZ() - (boundary[2][0] + indexZ * scene.getZEdgeVoxel())) / dirZ);
        }

        //move over all the geometries of the first voxel and find the closest intersection (if there is any)
        Geometries list = scene.voxels.get(new Double3(indexX, indexY, indexZ));
        Intersectable.GeoPoint closestIntersection;
        if (list != null) {
            closestIntersection = findClosestIntersection(ray, list);
            //check if the intersection point exists, and it's inside the voxel
            if (closestIntersection != null && isInsideVoxel(index, closestIntersection.point, boundary))
                return closestIntersection;
        }
        //if there is no intersection points in the first voxel search in the rest of the ray's way
        //since the ray starts in the middle of a voxel (since we moved it on the intersection with the  scene CBR,
        //or the head is already inside the scene SCR), we had to calculate the remaining distance to the fist voxel's edge.
        //But from now on, we can use the constant voxel size, since it would always intersect with the edge of the voxel.
        double tDeltaX = Math.abs(scene.getXEdgeVoxel() / dirX);
        double tDeltaY = Math.abs(scene.getYEdgeVoxel() / dirY);
        double tDeltaZ = Math.abs(scene.getZEdgeVoxel() / dirZ);
        do {

            if (tMaxX < tMaxY) {
                if (tMaxX < tMaxZ) {
                    indexX = indexX + stepX;
                    if ((indexX > 0 && indexX == scene.resolution + 1) || (indexX < 0))
                        return null; /* outside grid */
                    tMaxX = tMaxX + tDeltaX;
                } else {
                    indexZ = indexZ + stepZ;
                    if ((indexZ > 0 && indexZ == scene.resolution + 1) || (indexZ < 0))
                        return null;
                    tMaxZ = tMaxZ + tDeltaZ;
                }
            } else {
                if (tMaxY < tMaxZ) {
                    indexY = indexY + stepY;
                    if ((indexY > 0 && indexY == scene.resolution + 1) || (indexY < 0))
                        return null;
                    tMaxY = tMaxY + tDeltaY;
                } else {
                    indexZ = indexZ + stepZ;
                    if ((indexZ > 0 && indexZ == scene.resolution + 1) || (indexZ < 0))
                        return null;
                    tMaxZ = tMaxZ + tDeltaZ;
                }
            }
            list = scene.voxels.get(new Double3(indexX, indexY, indexZ));
            if (list == null) {
                closestIntersection = null;
            } else {
                closestIntersection = findClosestIntersection(ray, list);
            }

        } while (closestIntersection == null || !isInsideVoxel(new Point(indexX, indexY, indexZ), closestIntersection.point, boundary));


        return closestIntersection;
        //return closestIntersection == null ? scene.background : calcColor(closestIntersection, ray);
    }

    /**
     * the intersection of the ray with CBR of the scene
     *
     * @param ray the entering ray
     * @return the intersection point
     */
    private Point firstIntersection(Ray ray) {
        double x = ray.getP0().getX();
        double y = ray.getP0().getY();
        double z = ray.getP0().getZ();
        Point head = ray.getP0();

        int[][] boundary = scene.geometries.boundary;

        if (x >= boundary[0][0] && x <= boundary[0][1] &&
                y >= boundary[1][0] && y <= boundary[1][1] &&
                z >= boundary[2][0] && z <= boundary[2][1]) {
            return head;
        }
        //creates an actual object of the scene edges

        Point p1 = new Point(boundary[0][0], boundary[1][0], boundary[2][0]);//(0,0,0)
        Point p2 = new Point(boundary[0][1], boundary[1][0], boundary[2][0]);//(1,0,0)
        Point p3 = new Point(boundary[0][0], boundary[1][1], boundary[2][0]);//(0,1,0)
        Point p4 = new Point(boundary[0][0], boundary[1][0], boundary[2][1]);//(0,0,1)
        Point p5 = new Point(boundary[0][1], boundary[1][1], boundary[2][0]);//(1,1,0)
        Point p6 = new Point(boundary[0][1], boundary[1][0], boundary[2][1]);//(1,0,1)
        Point p7 = new Point(boundary[0][0], boundary[1][1], boundary[2][1]);//(0,1,1)
        Point p8 = new Point(boundary[0][1], boundary[1][1], boundary[2][1]);//(1,1,1)

        Polygon bottom = new Polygon(p1, p2, p5, p3);//bottom
        Polygon front = new Polygon(p1, p2, p6, p4);//front
        Polygon left = new Polygon(p1, p3, p7, p4);//left
        Polygon up = new Polygon(p4, p6, p8, p7);//up
        Polygon behind = new Polygon(p3, p5, p8, p7);//behind
        Polygon right = new Polygon(p2, p5, p8, p6);//right

        //Memory wise, we won't use the ray function of findClosestPoint, since we will be in need to create a list of points
        Polygon[] faces = new Polygon[]{bottom, front, left, up, behind, right};
        List<Point> intersections;
        Point closest = null;

        double distance = Double.POSITIVE_INFINITY;
        for (Polygon p : faces) {
            intersections = p.findIntersections(ray);
            if (intersections != null) {
                if (intersections.get(0).distance(head) < distance) {
                    distance = intersections.get(0).distance(head);
                    closest = intersections.get(0);
                }
            }
        }

        return closest;

    }

    /**
     * this function matches a voxel to a point.
     *
     * @param p        the point in the voxel
     * @param boundary the scene CBR
     * @return the voxel of the specified point
     */
    private Point VoxelByPoint(Point p, int[][] boundary) {

        int xCoordinate = (int) ((p.getX() - boundary[0][0]) / scene.getXEdgeVoxel());
        int yCoordinate = (int) ((p.getY() - boundary[1][0]) / scene.getYEdgeVoxel());
        int zCoordinate = (int) ((p.getZ() - boundary[2][0]) / scene.getZEdgeVoxel());

        return new Point(xCoordinate, yCoordinate, zCoordinate);
    }

    /**
     * decides sign of a number
     *
     * @param component direction of the ray inside teh voxel
     * @return positivity or negativity of the direction
     */
    private int determineDirection(double component) {
        if (component > 0)
            return 1;
        else if (component < 0)
            return -1;
        else
            return 0;
    }

    /**
     * checks if the intersection point ith the geometry is inside the voxel
     * @param index the voxel's index
     * @param intersection the intersection point of teh ray with the geometry
     * @param boundary the boundary of the scene
     * @return if the intersection point ith the geometry is inside the voxel
     */
    private boolean isInsideVoxel(Point index, Point intersection, int[][] boundary) {
        double xMax = boundary[0][0] + (index.getX() + 1) * scene.getXEdgeVoxel();
        double yMax = boundary[1][0] + (index.getY() + 1) * scene.getYEdgeVoxel();
        double zMax = boundary[2][0] + (index.getZ() + 1) * scene.getZEdgeVoxel();

        double xMin = boundary[0][0] + (index.getX()) * scene.getXEdgeVoxel();
        double yMin = boundary[1][0] + (index.getY()) * scene.getYEdgeVoxel();
        double zMin = boundary[2][0] + (index.getZ()) * scene.getZEdgeVoxel();

        return intersection.getX() >= xMin && intersection.getX() <= xMax
                && intersection.getY() >= yMin && intersection.getY() <= yMax
                && intersection.getZ() >= zMin && intersection.getZ() <= zMax;
    }

    /**
     * fixes the intersection of the ray with teh scene CBR on exact intersection with voxel edges
     *
     * @param p        intersection point with the scene CBR
     * @param boundary the CBR
     * @return the fixed point
     */
    private Point fixPoint(Point p, int[][] boundary) {
        if (isZero((p.getX() - boundary[0][0]))) {
            p = p.add(new Vector(1, 0, 0).scale(EPSILON));
        }
        if (isZero((p.getX() - boundary[0][1]))) {
            p = p.add(new Vector(1, 0, 0).scale(-EPSILON));
        }
        if (isZero((p.getY() - boundary[1][0]))) {
            p = p.add(new Vector(0, 1, 0).scale(EPSILON));
        }
        if (isZero((p.getY() - boundary[1][1]))) {
            p = p.add(new Vector(0, 1, 0).scale(-EPSILON));
        }
        if (isZero((p.getZ() - boundary[2][0]))) {
            p = p.add(new Vector(0, 0, 1).scale(EPSILON));
        }
        if (isZero((p.getZ() - boundary[2][1]))) {
            p = p.add(new Vector(0, 0, 1).scale(-EPSILON));
        }
        return p;
    }


}
