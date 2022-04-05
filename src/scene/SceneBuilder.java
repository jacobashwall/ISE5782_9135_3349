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

public class SceneBuilder {

    private SceneDescriptor sceneDescriptor;
    private Scene scene;
    private final String filePath = System.getProperty("user.dir") + "/src/ScenesXML/";

    String sceneXMLDesc;

    public SceneBuilder(String sceneFileName, Scene givenScene) {
        scene = givenScene;
        File sceneFile = new File(filePath + sceneFileName);
        loadSceneFromFile(sceneFile);
        sceneDescriptor = new SceneDescriptor();
        try {
            sceneDescriptor.InitializeFromXMLString(sceneXMLDesc);
        } catch (ParseException e) {
            System.out.println("Syntactical error in scene description:");
            e.printStackTrace();
        }
        System.out.print(sceneXMLDesc);

        // Creating an AmbientLight object
        AmbientLight ambientLight = AmbientLight.ReadAmbientLight(sceneDescriptor.getAmbientLightAttributes());

        // creating a scene
        String[] backgroundColor = sceneDescriptor.getSceneAttributes()
                .get("background-color").split("\\s+");
        Color background = new Color(
                (int) (255 * Double.valueOf(backgroundColor[0])),
                (int) (255 * Double.valueOf(backgroundColor[1])),
                (int) (255 * Double.valueOf(backgroundColor[2])));

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
        for (Map<String, String> planeAttributes : sceneDescriptor.get_planes()) {
            Plane triangle = Plane.ReadXMLPlane(planeAttributes);
            scene.geometries.add(triangle);
        }

    }


    public Scene getScene() {
        return scene;
    }

    private boolean loadSceneFromFile(File file) {
        if (file == null) {
            return false;
        }
        try {
            byte[] buffer = new byte[(int) file.length()];
            FileInputStream fin = new FileInputStream(file);
            fin.read(buffer);
            sceneXMLDesc = (new String(buffer));
            fin.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}