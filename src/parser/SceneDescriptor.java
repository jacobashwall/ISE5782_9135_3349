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
    protected List<Map<String, String>> spheres = new ArrayList<>();
    protected List<Map<String, String>> triangles = new ArrayList<>();
    protected List<Map<String, String>> planes = new ArrayList<>();

    /**
     * Constructs scene description from given XML formatted text. Verifies
     * syntactic requirements that at least the scene element exist.
     *
     * @param text XML string
     */
    public void InitializeFromXMLString(String text) throws ParseException {

        SceneXMLParser parser = new SceneXMLParser();
        parser.parse(text, this);

        // Verify that scene structure is syntactically correct
        if (sceneAttributes == null) {
            throw new ParseException("No scene element found!", 0);
        }
    }

    /**
     * Scene attributes getter
     *
     * @return the scene attributes
     */
    public Map<String, String> getSceneAttributes() {
        return sceneAttributes;
    }

    /**
     * Ambient Light attributes getter
     *
     * @return the Ambient Light attributes
     */
    public Map<String, String> getAmbientLightAttributes() {
        return ambientLightAttributes;
    }

    /**
     * Sphere attributes getter
     *
     * @return the Sphere attributes
     */
    public List<Map<String, String>> getSpheres() {
        return spheres;
    }

    /**
     * Triangle attributes getter
     *
     * @return the Triangle attributes
     */
    public List<Map<String, String>> getTriangles() {
        return triangles;
    }

    /**
     * plane attributes getter
     *
     * @return the plane attributes
     */
    public List<Map<String, String>> getPlanes() {
        return planes;
    }
}