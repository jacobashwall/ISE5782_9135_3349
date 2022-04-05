package parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Contains a scene description. Ensures syntactic correctness but not semantic.
 */
public class SceneDescriptor {

    protected Map<String, String> sceneAttributes;
    protected Map<String, String> ambientLightAttributes;
    protected List<Map<String, String>> spheres = new ArrayList<Map<String, String>>();
    protected List<Map<String, String>> triangles = new ArrayList<Map<String, String>>();
    protected List<Map<String, String>> planes = new ArrayList<Map<String, String>>();

    /**
     * Constructs scene description from given XML formatted text. Verifies
     * syntactic requirements that at least one scene element and one camera
     * element should exist.
     *
     * @param text XML string
     * @throws ParseException
     */
    public void InitializeFromXMLString(String text) throws ParseException {


        SceneXMLParser parser = new SceneXMLParser();
        parser.parse(text, this);

        // Verify that scene structure is syntactically correct
        if (sceneAttributes == null) {
            throw new ParseException("No scene element found!", 0);
        }
       /* if (ambientLightAttributes == null) {
            throw new ParseException("No ambientLightAttributes element found!", 0);
        }*/

    }

    public Map<String, String> getSceneAttributes() {
        return sceneAttributes;
    }

    public Map<String, String> getAmbientLightAttributes() {
        return ambientLightAttributes;
    }

    public List<Map<String, String>> getSpheres() {
        return spheres;
    }

    public List<Map<String, String>> getTriangles() {
        return triangles;
    }

    public List<Map<String, String>> get_planes() {
        return planes;
    }
}