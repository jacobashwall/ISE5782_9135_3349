package parser;


import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Parses the scene XML description and initializes the scene description object
 */
public class SceneXmlParser extends DefaultHandler {
    /**
     * the entire string of the XML file
     */
    SceneDescriptor sceneDesc;

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        Map<String, String> myAttributes = new HashMap<>();

        for (int i = 0; i < attributes.getLength(); ++i) {
            String k = attributes.getQName(i);
            String v = attributes.getValue(i).trim();

            myAttributes.put(k, v);
        }

        if (qName.equals("scene")) {
            sceneDesc.sceneAttributes = myAttributes;
        } else {
            if (qName.equals("ambient-light"))
                sceneDesc.ambientLightAttributes = myAttributes;
            else {
                if (qName.equals("sphere"))
                    sceneDesc.spheres.add(myAttributes);
                if (qName.equals("triangle"))
                    sceneDesc.triangles.add(myAttributes);
                if (qName.equals("plane"))
                    sceneDesc.planes.add(myAttributes);
                if (qName.equals("tube"))
                    sceneDesc.tubes.add(myAttributes);
                if (qName.equals("cylinder"))
                    sceneDesc.cylinders.add(myAttributes);
                if (qName.equals("polygon"))
                    sceneDesc.polygons.add(myAttributes);
            }
        }

        super.startElement(uri, localName, qName, attributes);
    }

    /**
     * Start parsing scene description XML
     *
     * @param text      XML Scene description
     * @param sceneDesc Scene Description object to contain scene description
     */
    public void parse(String text, SceneDescriptor sceneDesc) {

        this.sceneDesc = sceneDesc;

        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            XMLReader xr = parser.getXMLReader();
            xr.setContentHandler(this);
            xr.setErrorHandler(this);
            xr.parse(new InputSource(new StringReader(text)));

        } catch (Exception e) {//if failed prints the exception description
            e.printStackTrace();
        }
    }

}
