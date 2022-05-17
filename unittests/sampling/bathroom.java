package sampling;

import geometries.Geometries;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import lighting.PointLight;
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

public class bathroom {
    /**
     * Produce an elaborate picture with several objects
     */
    @Test
    public void createPicture() {
        //settings
        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point(0, 300, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2000, 2000).setVPDistance(1500);


        //front wall back
        Polygon frontWall = new Polygon(new Point(-500, 0, 0), new Point(500, 0, 0), new Point(500, 500, 0), new Point(-500, 500, 0));
        frontWall.setMaterial(new Material());
        frontWall.setEmission(new Color(122, 122, 122));
        scene.geometries.add(frontWall);

        //front wall tiles
        int tileHeight = 50;
        int tileWidth = 100;
        int gap = 2;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Polygon polygon = new Polygon(new Point(-500 + j * tileWidth, i * tileHeight, 1), new Point(-500 + tileWidth + j * tileWidth - gap, i * tileHeight, 1), new Point(-500 + tileWidth + j * tileWidth - gap, tileHeight + i * tileHeight - gap, 1), new Point(-500 + j * tileWidth, tileHeight + i * tileHeight - gap, 1));
                polygon.setEmission(new Color(220, 220, 220)).setMaterial(new Material().setKs(1).setKd(0.5));
                scene.geometries.add(polygon);
            }
        }

        //front wall colored tiles
        Color[] colorPallete = {
                new Color(152, 251, 152),//green
                new Color(150, 150, 150),//grey
                new Color(240, 230, 140),//yellow
                new Color(205, 92, 92),//red
                new Color(72, 61, 139)//blue
        };

        tileHeight = 50;
        tileWidth = 20;
        gap = 1;
        for (int j = 0; j < 50; j++) {
            Polygon polygon = new Polygon(new Point(-500 + j * tileWidth, 225, 2), new Point(-500 + tileWidth + j * tileWidth - gap, 225, 2), new Point(-500 + tileWidth + j * tileWidth - gap, 225 + tileHeight, 2), new Point(-500 + j * tileWidth, 225 + tileHeight, 2));
            polygon.setEmission(colorPallete[j % 5]).setMaterial(new Material().setKs(0).setKd(0.5));
            scene.geometries.add(polygon);
        }

        //front wall light
        //scene.lights.add(new PointLight(new Color(255,255,255),new Point(125,450,5)));

        //mirror back
        Polygon mirrorBack = new Polygon(new Point(-200, 275, 2), new Point(450, 275, 2), new Point(450, 425, 2), new Point(-200, 425, 2));
        mirrorBack.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(mirrorBack);

        //mirror
        gap = 10;
        Polygon mirror = new Polygon(new Point(-200 + gap, 275 + gap, 3), new Point(450 - gap, 275 + gap, 3), new Point(450 - gap, 425 - gap, 3), new Point(-200 + gap, 425 - gap, 3));
        mirror.setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(mirror);

        //marble //note- need to add sink!
        Polygon marbleTopLeft = new Polygon(new Point(-225, 200, 3), new Point(500, 200, 3), new Point(500, 200, 100), new Point(-225, 200, 100));
        marbleTopLeft.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleTopLeft);

        Polygon marbleFront = new Polygon(new Point(500, 200, 100), new Point(-225, 200, 100), new Point(-225, 175, 100), new Point(500, 175, 100));
        marbleFront.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleFront);

        Polygon marbleBottomLeft = new Polygon(new Point(-225, 175, 3), new Point(500, 175, 3), new Point(500, 175, 100), new Point(-225, 175, 100));
        marbleBottomLeft.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleBottomLeft);

        Polygon marbleLeft = new Polygon(new Point(-225, 200, 0), new Point(-225, 200, 100), new Point(-225, 175, 100), new Point(-225, 175, 0));
        marbleLeft.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleLeft);

        //closets
        Polygon closetLeft = new Polygon(new Point(-225, 0, 100), new Point(-225, 0, 0), new Point(-225, tileHeight, 0), new Point(-225, tileHeight, 100));
        closetLeft.setEmission(new Color(100, 100, 100)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(closetLeft);
        tileHeight = 175;
        tileWidth = 180;
        gap = 5;
        for (int j = 0; j < 4; j++) {
            Polygon closetDoorFront = new Polygon(new Point(-225 + j * tileWidth, 0, 100), new Point(-225 + tileWidth + j * tileWidth - gap, 0, 100), new Point(-225 + +tileWidth + j * tileWidth - gap, tileHeight, 100), new Point(-225 + j * tileWidth, tileHeight, 100));
            closetDoorFront.setEmission(new Color(127, 255, 0)).setMaterial(new Material().setKs(0).setKd(0.5));
            scene.geometries.add(closetDoorFront);
            Polygon closetDoorLeft = new Polygon(new Point(-225 + j * tileWidth, 0, 100), new Point(-225 + j * tileWidth, 0, 0), new Point(-225 + j * tileWidth, tileHeight, 0), new Point(-225 + j * tileWidth, tileHeight, 100));
            closetDoorLeft.setEmission(new Color(100, 100, 100)).setMaterial(new Material().setKs(0).setKd(0.5));
            scene.geometries.add(closetDoorLeft);
            Polygon closetDoorRight = new Polygon(new Point(-225 + tileWidth + j * tileWidth - gap, 0, 100), new Point(-225 + tileWidth + j * tileWidth - gap, 0, 0), new Point(-225 + tileWidth + j * tileWidth - gap, tileHeight, 0), new Point(-225 + tileWidth + j * tileWidth - gap, tileHeight, 100));
            closetDoorRight.setEmission(new Color(100, 100, 100)).setMaterial(new Material().setKs(0).setKd(0.5));
            scene.geometries.add(closetDoorRight);
        }

       /* Polygon closetBackground = new Polygon(new Point(500, 200, 50), new Point(-225, 200, 50), new Point(-225, 0, 50), new Point(500, 0, 50));
        closetBackground.setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(closetBackground);*/

        //floor background
        Polygon floorBackground = new Polygon(new Point(-500, -2, 0), new Point(-500, -2, 250), new Point(500, -2, 250), new Point(500, -2, 0));
        floorBackground.setMaterial(new Material());
        floorBackground.setEmission(new Color(122, 122, 122));
        scene.geometries.add(floorBackground);

        //floor tiles
        tileHeight = 50;
        tileWidth = 50;
        gap = 2;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 20; j++) {
                Polygon polygon = new Polygon(new Point(-500 + j * tileWidth, 0, i * tileHeight), new Point(-500 + tileWidth + j * tileWidth - gap, 0, i * tileHeight), new Point(-500 + tileWidth + j * tileWidth - gap, 0, tileHeight + i * tileHeight - gap), new Point(-500 + j * tileWidth, 0, tileHeight + i * tileHeight - gap));
                polygon.setEmission(new Color(143, 188, 143)).setMaterial(new Material().setKs(0).setKd(1));
                scene.geometries.add(polygon);
                //new Color(143,188,143)
            }
        }

        //right wall back
        Polygon rightWall = new Polygon(new Point(501, 0, 0), new Point(501, 0, 250), new Point(501, 500, 250), new Point(501, 500, 0));
        rightWall.setMaterial(new Material());
        rightWall.setEmission(new Color(122, 122, 122));
        scene.geometries.add(rightWall);

        //right wall tiles
        tileHeight = 50;
        tileWidth = 100;
        gap = 2;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                Polygon polygon = new Polygon(new Point(500, i * tileHeight, j * tileWidth), new Point(500, i * tileHeight, tileWidth + j * tileWidth - gap), new Point(500, tileHeight + i * tileHeight - gap, tileWidth + j * tileWidth - gap), new Point(500, tileHeight + i * tileHeight - gap, j * tileWidth));
                polygon.setEmission(new Color(220, 220, 220)).setMaterial(new Material().setKs(1).setKd(0.5));
                scene.geometries.add(polygon);
            }
        }

        //right wall colored tile
        tileHeight = 50;
        tileWidth = 20;
        gap = 1;
        for (int j = 0; j < 13; j++) {
            Polygon polygon = new Polygon(new Point(499, 225, j * tileWidth), new Point(499, 225, tileWidth + j * tileWidth - gap), new Point(499, 225 + tileHeight, tileWidth + j * tileWidth - gap), new Point(499, 225 + tileHeight, j * tileWidth));
            polygon.setEmission(colorPallete[j % 5]).setMaterial(new Material().setKs(0).setKd(0.5));
            scene.geometries.add(polygon);
        }

        //camera.moveCamera(new Point(-1000, 250,500 ),new Point(0, 250,50 ));
        //taking picture
        ImageWriter imageWriter = new ImageWriter("zzzzz", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();


    }

}
