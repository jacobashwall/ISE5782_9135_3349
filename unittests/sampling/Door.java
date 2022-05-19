package sampling;

import geometries.Sphere;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;

public class Door {

    @Test
    public void Door() {
        Scene scene=new Scene("test scene");
        Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);




        camera.setImageWriter(new ImageWriter("Door", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }
}
