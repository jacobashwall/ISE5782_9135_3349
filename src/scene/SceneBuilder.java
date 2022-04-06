package scene;

import geometries.*;
import lighting.*;
import parser.SceneDescriptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import primitives.Color;

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
        AmbientLight ambientLight = AmbientLight.ReadAmbientLight(sceneDescriptor.getAmbientLightAttributes());

        // Creating background color
        String[] backgroundColor = sceneDescriptor.getSceneAttributes()
                .get("background-color").split("\\s+");
        Color background = new Color(
                (Double.valueOf(backgroundColor[0])),
                (Double.valueOf(backgroundColor[1])),
                (Double.valueOf(backgroundColor[2])));

        // adding the attributes to the scene
        scene.setBackground(background).setAmbientLight(ambientLight).setGeometries(new Geometries());

        // creating and adding spheres
        for (Map<String, String> sphereAttributes : sceneDescriptor.getSpheres()) {
            Sphere sphere = Sphere.ReadXMLSphere(sphereAttributes);
            scene.geometries.add(sphere);
        }

        // creating and adding triangles
        for (Map<String, String> triangleAttributes : sceneDescriptor.getTriangles()) {
            Triangle triangle = Triangle.ReadXmlTriangle(triangleAttributes);
            scene.geometries.add(triangle);
        }

        // creating and adding planes
        for (Map<String, String> planeAttributes : sceneDescriptor.getPlanes()) {
            Plane plane = Plane.ReadXMLPlane(planeAttributes);
            scene.geometries.add(plane);
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

}