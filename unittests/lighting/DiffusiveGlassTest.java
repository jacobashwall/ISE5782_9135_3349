package lighting;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;

/**
 * Testing diffusive glass
 */
public class DiffusiveGlassTest {
    @Test
    public void DiffusiveGlass() {

        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

        scene.geometries.add( //
                new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setnShininess(100).setKt(0.3)),
                new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(100)));

        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point(0, 0, -100), new Vector(0, 0, 1)) //
                        .setKl(0.0004).setKq(0.0000006));

        //scene.ambientLight = new AmbientLight(new Color(125,125,1255), new Double3(0.5));
        scene.geometries.add(new Polygon(new Point(-1000,-1000,25),new Point(1000,-1000,25),new Point(1000,0,25),new Point(-1000,0,25)).setMaterial(new Material().setKt(1).setKdG(0.1)).setEmission(new Color(122,122,122)));
                ImageWriter imageWriter = new ImageWriter("DiffusiveGlass", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }
    @Test
    public void SimpleTest(){
        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2500, 2500).setVPDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

        Sphere sphere = new Sphere(new Point(0, 0, -50), 700);
        sphere.setEmission(Color.BLACK);
        sphere.setMaterial(new Material().setKs(1).setKr(0.4));
        scene.geometries.add(sphere);

        Sphere sphere1 = new Sphere(new Point(500, 0, 500), 400);
        sphere.setEmission(new Color(255,0,0));
        sphere.setMaterial(new Material().setKs(1).setKr(0.4));
        scene.geometries.add(sphere);

        //scene.ambientLight=new AmbientLight(new Color(122,122,122),new Double3(1));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(0, 0, 0), new Vector(0, 0, -1)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("SomethingCool", 100, 100);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();

    }
}
