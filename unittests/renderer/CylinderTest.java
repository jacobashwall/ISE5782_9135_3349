package renderer;

import static org.junit.jupiter.api.Assertions.*;

import lighting.PointLight;
import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.*;
import scene.Scene;

public class CylinderTest {
    @Test
    public void cylinderTest(){
        Camera camera = new Camera(new Point(-2,0,2),new Vector(1,0,-1),new Vector(1,0,1)).setVPDistance(1).setVPSize(10,10);
        Scene scene = new Scene("Cylinder");
        Cylinder cylinder = new Cylinder(new Ray(new Point(-1,0,0),new Vector(1,0,0)),1,2);
        cylinder.setEmission(new Color(122,122,122));
        scene.lights.add(new PointLight(new Color(255,255,255),new Point(-2,0,1)));
        scene.geometries.add(cylinder);

        camera.setImageWriter(new ImageWriter("Cylinder", 500, 500))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage();
        camera.writeToImage();
    }
}
