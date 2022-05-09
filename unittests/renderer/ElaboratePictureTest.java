package renderer;

import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.BLUE;

/**
 * testing the renderer using all the elements we have
 */
public class ElaboratePictureTest {
    /**
     * Produce an elaborate picture with several objects
     */
    @Test
    public void createElaboratePicture() {
        //settings
        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point(0, 100, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2500, 2500).setVPDistance(8500);

        Color[] rainbow = {new Color(3, 0, 152),
                new Color(1, 10, 152),
                new Color(0, 60, 152),
                new Color(0, 74, 152),
                new Color(0, 152, 152),
                new Color(0, 152, 127),
                new Color(0, 152, 88),
                new Color(0, 152, 71),
                new Color(0, 152, 37),
                new Color(0, 152, 13),
                new Color(45, 152, 0),
                new Color(74, 152, 0),
                new Color(102, 152, 0),
                new Color(132, 152, 0),
                new Color(152, 150, 0),
                new Color(152, 137, 0),
                new Color(152, 122, 0),
                new Color(152, 110, 0),
                new Color(152, 85, 0),
                new Color(152, 72, 0),
                new Color(152, 63, 0),
                new Color(152, 47, 0),
                new Color(152, 0, 16),
                new Color(152, 0, 27),
                new Color(152, 0, 41),
                new Color(152, 0, 55),
                new Color(152, 0, 67),
                new Color(152, 0, 90),
                new Color(152, 0, 100),
                new Color(152, 0, 128),
                new Color(129, 0, 152),
                new Color(113, 0, 152),
                new Color(83, 0, 152),
                new Color(56, 0, 152)};

        //spheres helix
        int index = 0;
        for (double t = 0; t < 25; t += 0.2) {
            scene.geometries.add(new Sphere(new Point(Math.cos(t) * 1000, t * 100, Math.sin(t) * 1000 - 6000), 100)//using helix coordinates function
                    .setEmission(rainbow[(index++) % rainbow.length]) //
                    .setMaterial(new Material().setKd(0.4).setKs(0.3).setnShininess(100).setKt(0.3).setKr(0)));
        }

        //lights
        scene.lights.add(new SpotLight(new Color(191, 191, 191), new Point(0, 3500, -4500), new Vector(-2, -2, -3)).setKc(0).setKl(0.000001).setKq(0.0000005));
        scene.lights.add(new SpotLight(new Color(191, 191, 191), new Point(0, 3500, -4500), new Vector(-2, -2, -3)).setKc(0).setKl(0.000001).setKq(0.0000005));

        /*
        Triangle t = new Triangle(new Point(-3000, -400, 0), new Point(3000, -400, 0), new Point(0, -400, -8000));
        t.setEmission(new Color(0, 0, 0));
        t.setMaterial(new Material().setKs(1).setKr(0.4));
        scene.geometries.add(t);
        */

        //mirror surface
        Plane pl = new Plane(new Point(-3000, -400, 0), new Point(3000, -400, 0), new Point(0, -400, -8000));
        pl.setEmission(new Color(0, 0, 0));
        pl.setMaterial(new Material().setKs(1).setKr(0.4));
        scene.geometries.add(pl);

        //mirror balls
        Sphere sphere = new Sphere(new Point(-6500, 1000, -7000), 5000);
        sphere.setEmission(new Color(0, 0, 0));
        sphere.setMaterial(new Material().setKs(1).setKr(0.4));
        scene.geometries.add(sphere);

        sphere = new Sphere(new Point(6500, 1000, -7000), 5000);
        sphere.setEmission(new Color(0, 0, 0));
        sphere.setMaterial(new Material().setKs(1).setKr(0.4));
        scene.geometries.add(sphere);


        //choose one of those:

/*
        //taking picture for images
        camera.moveCamera(new Point(0, 0, 0),new Point(0, 1000,-6000)).setVPDistance(2500);
        ImageWriter imageWriter = new ImageWriter("elaboratePicture", 10000, 10000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
*/

        //taking pictures for the gif using move camera and rotate camera
        double movingFactor = 10;
        double rotatingFactor = 4.5;
        for (int i = 0; i <= 40; i++) {
            camera.moveCamera(new Point(camera.getP0().getX(), camera.getP0().getY(), camera.getP0().getZ() - (i * movingFactor)),
                            new Point(0, 1000, -6000))
                    .turnCamera(i * rotatingFactor)
                    .setImageWriter(new ImageWriter("gif\\image" + i, 500, 500))
                    .setRayTracer(new RayTracerBasic(scene))
                    .renderImage();
            camera.writeToImage();
        }

    }
}