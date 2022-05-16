package renderer;


import geometries.*;
import lighting.DirectionalLight;
import lighting.LightSource;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class pictureLastPart {
    @Test
    void myPictureLastPart() {
        Scene scene = new Scene("Targil 7.3");
        Camera camera = new Camera(new Point(500, -400, 10), new Vector(-0.5, 1, 0), new Vector(0, 0, 1));
        camera.setVPSize(150, 150).setVPDistance(100);

        Material material = new Material().setKd(0.4).setKs(1).setnShininess(50).setKt(0).setKr(0.5).setKsG(0.5);
        Material material1 = new Material().setKd(0.4).setKs(0.3).setnShininess(50).setKt(0.7).setKr(0);
        SpotLight light = new SpotLight(new Color(255, 255, 255), new Point(0, -50, 25), new Vector(0, 2, -1));
        light.setKc(0).setKl(0.01).setKq(0.05);
        light.setNarrowBeam(5);

        DirectionalLight directionalLight1 = new DirectionalLight(new Color(100, 100, 100), new Vector(0, 1, 0));
        DirectionalLight directionalLight2 = new DirectionalLight(new Color(100, 100, 100), new Vector(1, 0, 0));
        DirectionalLight directionalLight3 = new DirectionalLight(new Color(100, 100, 100), new Vector(-1, 0, 0));
        PointLight pointLight = new PointLight(new Color(255,255,255),new Point(0,0,-100));


        //scene.lights.add(light);
        //scene.lights.add(directionalLight1);
        //scene.lights.add(directionalLight2);
        //scene.lights.add(directionalLight3);
        scene.lights.add(pointLight);

        Sphere sphere = new Sphere(new Point(0, 0, 0), 50);
        sphere.setEmission(new Color(255, 0, 0)).setMaterial(material1);

        Sphere sphere2 = new Sphere(new Point(0, 0, 0), 50);
        sphere2.setEmission(new Color(100, 69, 0)).setMaterial(material);

        Polygon sqr1 = new Polygon(new Point(-100, 150, -100), new Point(-100, 150, 100), new Point(100, 150, 100), new Point(100, 150, -100));
        sqr1.setMaterial(material);//.setEmission(new Color(255, 215, 0));

        Polygon sqrt2 = new Polygon(new Point(-150, 100, -100), new Point(-150, 100, 100), new Point(-150, -100, 100), new Point(-150, -100, -100));
        sqrt2.setMaterial(material);//.setEmission(new Color(0, 0, 0));

        Polygon sqrt3 = new Polygon(new Point(150, -100, -100), new Point(150, -100, 100), new Point(150, 100, 100), new Point(150, 100, -100));
        sqrt3.setMaterial(material);//.setEmission(new Color(0, 0, 0));

        Polygon sqrt4 = new Polygon(new Point(150, -100, 150), new Point(-100, -100, 150), new Point(-100, 100, 150), new Point(100, 100, 150));
        sqrt4.setMaterial(material);//.setEmission(new Color(0, 0, 0));

        Polygon sqrt5 = new Polygon(new Point(100, -100, -150), new Point(-100, -100, -150), new Point(-100, 100, -150), new Point(100, 100, -150));
        sqrt5.setMaterial(material);//.setEmission(new Color(0, 0, 0));

        Plane pln = new Plane(new Point(100,-100,-150),new Vector(0,0,1));
        pln.setMaterial(material);//.setEmission(new Color(0, 0, 0));

        scene.geometries.add(/*sqr1, sqrt2, sqrt3, sqrt4, sqrt5,*/pln, sphere/*,sphere2*/);

        camera.setImageWriter(new ImageWriter("Targil 7.3", 500, 500))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage();
        camera.writeToImage();
    }
}
