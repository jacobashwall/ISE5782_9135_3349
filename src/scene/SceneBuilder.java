package scene;

import geometries.*;
import lighting.*;
import parser.SceneDescriptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import primitives.*;

/**
 * A class that holds the scene and modify it by the XML parser
 */
public class SceneBuilder {

    /**
     * the description of the scene divided by several maps
     */
    private SceneDescriptor sceneDescriptor;
    /**
     * the scene that we upload the xml file description to
     */
    private Scene scene;
    /**
     * the XML file path we read from
     */
    private final String filePath = System.getProperty("user.dir") + "/src/scenesXML/";
    /**
     * the entire XML file string
     */
    private String sceneXMLDesc;

    /**
     * Scene builder constructor that builds the scene using the parser
     *
     * @param sceneFileName the name of the XML file of the scene
     * @param givenScene    the scene that we load the attributes too
     */
    public SceneBuilder(String sceneFileName, Scene givenScene) {
        scene = givenScene;
        File sceneFile = new File(filePath + sceneFileName);
        loadSceneFromFile(sceneFile);
        sceneDescriptor = new SceneDescriptor();
        //checks if the file is in the right structure
        try {
            sceneDescriptor.InitializeFromXMLString(sceneXMLDesc);
        } catch (ParseException e) {
            System.out.println("Syntactical error in scene description:");
            e.printStackTrace();
        }
        //print the description on the terminal to be read easily
        System.out.print(sceneXMLDesc);

        // Creating an AmbientLight object
        AmbientLight ambientLight = readAmbientLight(sceneDescriptor.getAmbientLightAttributes());

        // Creating background color
        String[] backgroundColor = sceneDescriptor.getSceneAttributes()
                .get("background-color").split("\\s+");
        Color background = new Color(
                (Double.parseDouble(backgroundColor[0])),
                (Double.parseDouble(backgroundColor[1])),
                (Double.parseDouble(backgroundColor[2])));

        // adding the attributes to the scene
        scene.setBackground(background).setAmbientLight(ambientLight).setGeometries(new Geometries());

        // creating and adding spheres
        for (Map<String, String> sphereAttributes : sceneDescriptor.getSpheres()) {
            Sphere sphere = readXMLSphere(sphereAttributes);
            scene.geometries.add(sphere);
        }

        // creating and adding triangles
        for (Map<String, String> triangleAttributes : sceneDescriptor.getTriangles()) {
            Triangle triangle = readXmlTriangle(triangleAttributes);
            scene.geometries.add(triangle);
        }

        // creating and adding planes
        for (Map<String, String> planeAttributes : sceneDescriptor.getPlanes()) {
            Plane plane = readXMLPlane(planeAttributes);
            scene.geometries.add(plane);
        }

        // creating and adding tubes
        for (Map<String, String> tubeAttributes : sceneDescriptor.getTubes()) {
            Tube tube = readXMLTube(tubeAttributes);
            scene.geometries.add(tube);
        }

        // creating and adding cylinders
        for (Map<String, String> cylinderAttributes : sceneDescriptor.getCylinders()) {
            Cylinder cylinder = readXMLCylinder(cylinderAttributes);
            scene.geometries.add(cylinder);
        }

        // creating and adding polygons
        for (Map<String, String> polygonAttributes : sceneDescriptor.getPolygons()) {
            Polygon polygon = readXmlPolygon(polygonAttributes);
            scene.geometries.add(polygon);
        }

    }

    /**
     * returns the scene after it was created in the
     *
     * @return the complete scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Loads the entire scene string from the XML file, also checks if we did it successfully
     *
     * @param file the XML file we are reading from
     * @return whether we loaded the scene successfully
     */
    private boolean loadSceneFromFile(File file) {
        //if no file is given
        if (file == null) {
            return false;
        }
        try { //try to open and read the file
            byte[] buffer = new byte[(int) file.length()];
            FileInputStream fin = new FileInputStream(file);
            fin.read(buffer);
            sceneXMLDesc = (new String(buffer));
            fin.close();
            return true;

        } catch (IOException e) { //if there was an error trying to open and read the file
            e.printStackTrace();//prints the exception details
            return false;
        }
    }


    /**
     * Creates a cylinder using the list of attributes from the XML file
     *
     * @param cylinderAttributes list of cylinder attributes fetched from the xml file
     * @return a cylinder with the values stated in the tube attributes
     */
    private Cylinder readXMLCylinder(Map<String, String> cylinderAttributes) {
        double radius = Double.parseDouble(cylinderAttributes.get("radius"));

        String[] axisRayAttribute = cylinderAttributes
                .get("p0").split("\\s+");
        Point p0 = new Point(Double.parseDouble(axisRayAttribute[0]),
                Double.parseDouble(axisRayAttribute[1]),
                Double.parseDouble(axisRayAttribute[2]));
        axisRayAttribute = cylinderAttributes
                .get("dir").split("\\s+");
        Vector dir = new Vector(Double.parseDouble(axisRayAttribute[0]),
                Double.parseDouble(axisRayAttribute[1]),
                Double.parseDouble(axisRayAttribute[2]));
        Ray axisRay = new Ray(p0, dir);

        double height = Double.parseDouble(cylinderAttributes.get("height"));

        Cylinder cylinder = new Cylinder(axisRay, radius, height);
        if (cylinderAttributes.get("emission") != null) {
            String[] emissionLightAttributes = cylinderAttributes.get("emission").split("\\s+");
            Color emissionLight = new Color(
                    Double.parseDouble(emissionLightAttributes[0]),
                    Double.parseDouble(emissionLightAttributes[1]),
                    Double.parseDouble(emissionLightAttributes[2]));
            cylinder.setEmission(emissionLight);
        }
        return cylinder;
    }

    /**
     * Creates a plane using the list of attributes from the XML file
     *
     * @param planeAttributes list of plane attributes fetched from the xml file
     * @return a plane with the values stated in the plane attributes
     */
    private Plane readXMLPlane(Map<String, String> planeAttributes) {

        String[] p0Attributes = planeAttributes.get("p0").split("\\s+");
        Point p0 = new Point(Double.parseDouble(p0Attributes[0]),
                Double.parseDouble(p0Attributes[1]),
                Double.parseDouble(p0Attributes[2]));
        if (planeAttributes.get("normal") != null) {//using the constructor of normal+ q0
            String[] normalAttributes = planeAttributes.get("normal").split("\\s+");
            Vector normal = new Vector(Double.parseDouble(normalAttributes[0]),
                    Double.parseDouble(normalAttributes[1]),
                    Double.parseDouble(normalAttributes[2]));
            return new Plane(p0, normal);
        } else {//using 3 points constructor
            String[] p1Attributes = planeAttributes.get("p0").split("\\s+");
            Point p1 = new Point(Double.parseDouble(p1Attributes[0]),
                    Double.parseDouble(p1Attributes[1]),
                    Double.parseDouble(p1Attributes[2]));
            String[] p2Attributes = planeAttributes.get("p0").split("\\s+");
            Point p2 = new Point(Double.parseDouble(p2Attributes[0]),
                    Double.parseDouble(p2Attributes[1]),
                    Double.parseDouble(p2Attributes[2]));

            Plane plane = new Plane(p0, p1, p2);
            if (planeAttributes.get("emission") != null) {
                String[] emissionLightAttributes = planeAttributes.get("emission").split("\\s+");
                Color emissionLight = new Color(
                        Double.parseDouble(emissionLightAttributes[0]),
                        Double.parseDouble(emissionLightAttributes[1]),
                        Double.parseDouble(emissionLightAttributes[2]));
                plane.setEmission(emissionLight);
            }
            return plane;
        }
    }

    /**
     * Creates a polygon using the list of attributes from the XML file
     *
     * @param polygonAttributes list of polygon attributes fetched from the xml file
     * @return a polygon with the values stated in the polygon attributes
     */
    private Polygon readXmlPolygon(Map<String, String> polygonAttributes) {
        List<Point> copyVertices = new LinkedList<>();
        String[] coordinates;
        Point pI;
        String pointName;
        for (int i = 0; i < polygonAttributes.size(); i++) {
            pointName="p"+ i;
            if(polygonAttributes.get(pointName)!=null) {
                coordinates = polygonAttributes.get(pointName).split("\\s+");
                pI = new Point(Double.parseDouble(coordinates[0]),
                        Double.parseDouble(coordinates[1]),
                        Double.parseDouble(coordinates[2]));
                copyVertices.add(pI);
            }
        }
        Point[] vertices=copyVertices.toArray(new Point[0]);
        Polygon polygon = new Polygon(vertices);
        if (polygonAttributes.get("emission") != null) {
            String[] emissionLightAttributes = polygonAttributes.get("emission").split("\\s+");
            Color emissionLight = new Color(
                    Double.parseDouble(emissionLightAttributes[0]),
                    Double.parseDouble(emissionLightAttributes[1]),
                    Double.parseDouble(emissionLightAttributes[2]));
            polygon.setEmission(emissionLight);
        }
        return polygon;
    }
    /**
     * Creates a sphere using the list of attributes from the XML file
     *
     * @param sphereAttributes list of sphere attributes fetched from the xml file
     * @return a sphere with the values stated in the sphere attributes
     */
    private Sphere readXMLSphere(Map<String, String> sphereAttributes) {
        double radius = Double.parseDouble(sphereAttributes.get("radius"));

        String[] centerValues = sphereAttributes
                .get("center").split("\\s+");

        Point center = new Point(Double.parseDouble(centerValues[0]),
                Double.parseDouble(centerValues[1]),
                Double.parseDouble(centerValues[2]));
        Sphere sphere = new Sphere(center, radius);
        if (sphereAttributes.get("emission") != null) {
            String[] emissionLightAttributes = sphereAttributes.get("emission").split("\\s+");
            Color emissionLight = new Color(
                    Double.parseDouble(emissionLightAttributes[0]),
                    Double.parseDouble(emissionLightAttributes[1]),
                    Double.parseDouble(emissionLightAttributes[2]));
            sphere.setEmission(emissionLight);
        }
        return sphere;

    }
    /**
     * Creates a triangle using the list of attributes from the XML file
     *
     * @param triangleAttributes list of triangle attributes fetched from the xml file
     * @return a triangle with the values stated in the triangle attributes
     */
    private Triangle readXmlTriangle(Map<String, String> triangleAttributes) {

        String[] p0Coordinates = triangleAttributes
                .get("p0").split("\\s+");

        Point p0 = new Point(Double.parseDouble(p0Coordinates[0]),
                Double.parseDouble(p0Coordinates[1]),
                Double.parseDouble(p0Coordinates[2]));

        String[] p1Coordinates = triangleAttributes
                .get("p1").split("\\s+");

        Point p1 = new Point(Double.parseDouble(p1Coordinates[0]),
                Double.parseDouble(p1Coordinates[1]),
                Double.parseDouble(p1Coordinates[2]));

        String[] p2Coordinates = triangleAttributes
                .get("p2").split("\\s+");

        Point p2 = new Point(Double.parseDouble(p2Coordinates[0]),
                Double.parseDouble(p2Coordinates[1]),
                Double.parseDouble(p2Coordinates[2]));
        Triangle triangle = new Triangle(p0, p1, p2);
        if (triangleAttributes.get("emission") != null) {
            String[] emissionLightAttributes = triangleAttributes.get("emission").split("\\s+");
            Color emissionLight = new Color(
                    Double.parseDouble(emissionLightAttributes[0]),
                    Double.parseDouble(emissionLightAttributes[1]),
                    Double.parseDouble(emissionLightAttributes[2]));
            triangle.setEmission(emissionLight);
        }
        return triangle;
    }
    /**
     * Creates a tube using the list of attributes from the XML file
     *
     * @param tubeAttributes list of tube attributes fetched from the xml file
     * @return a tube with the values stated in the tube attributes
     */
    private Tube readXMLTube(Map<String, String> tubeAttributes) {
        double radius = Double.parseDouble(tubeAttributes.get("radius"));

        String[] axisRayAttribute = tubeAttributes
                .get("p0").split("\\s+");
        Point p0 = new Point(Double.parseDouble(axisRayAttribute[0]),
                Double.parseDouble(axisRayAttribute[1]),
                Double.parseDouble(axisRayAttribute[2]));
        axisRayAttribute = tubeAttributes
                .get("dir").split("\\s+");
        Vector dir = new Vector(Double.parseDouble(axisRayAttribute[0]),
                Double.parseDouble(axisRayAttribute[1]),
                Double.parseDouble(axisRayAttribute[2]));
        Ray axisRay = new Ray(p0, dir);
        Tube tube = new Tube(axisRay, radius);
        if (tubeAttributes.get("emission") != null) {
            String[] emissionLightAttributes = tubeAttributes.get("emission").split("\\s+");
            Color emissionLight = new Color(
                    Double.parseDouble(emissionLightAttributes[0]),
                    Double.parseDouble(emissionLightAttributes[1]),
                    Double.parseDouble(emissionLightAttributes[2]));
            tube.setEmission(emissionLight);
        }
        return tube;
    }

    /**
     * Creates a AmbientLight using the list of attributes from the XML file
     *
     * @param AmbientLightAttributes list of AmbientLights attributes fetched from the xml file
     * @return a AmbientLight with the values stated in the AmbientLights attributes
     */
    private AmbientLight readAmbientLight(Map<String, String> AmbientLightAttributes) {
        if (AmbientLightAttributes == null)
            return new AmbientLight();
        String[] colorAttributes = AmbientLightAttributes.get("color").split("\\s+");
        Color color = new Color(
                Double.parseDouble(colorAttributes[0]),
                Double.parseDouble(colorAttributes[1]),
                Double.parseDouble(colorAttributes[2]));
        String[] factorAttributes = AmbientLightAttributes.get("k").split("\\s+");
        Double3 k;
        if (factorAttributes.length == 1)//using the constructor that uses only one variable
            k = new Double3(Double.parseDouble(factorAttributes[0]));
        else//using 3 values constructor
            k = new Double3(Double.parseDouble(factorAttributes[0]),
                    Double.parseDouble(factorAttributes[1]),
                    Double.parseDouble(factorAttributes[2]));
        return new AmbientLight(color, k);
    }



}