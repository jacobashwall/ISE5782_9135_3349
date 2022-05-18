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

        //region settings
        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point(0, 300, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2000, 2000).setVPDistance(1500);

        //colored tiles
        Color[] colorPallet = {
                new Color(152, 251, 152),//green
                new Color(150, 150, 150),//grey
                new Color(240, 230, 140),//yellow
                new Color(205, 92, 92),//red
                new Color(72, 61, 139)//blue
        };
        //endregion

        //region front wall
        // back
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

        //colored tiles
        tileHeight = 50;
        tileWidth = 20;
        gap = 1;
        for (int j = 0; j < 50; j++) {
            Polygon polygon = new Polygon(new Point(-500 + j * tileWidth, 225, 1.1), new Point(-500 + tileWidth + j * tileWidth - gap, 225, 1.1), new Point(-500 + tileWidth + j * tileWidth - gap, 225 + tileHeight, 1.1), new Point(-500 + j * tileWidth, 225 + tileHeight, 1.1));
            polygon.setEmission(colorPallet[j % 5]).setMaterial(new Material().setKs(0).setKd(0.5));
            scene.geometries.add(polygon);
        }
        //endregion

        //region lighting
        //front wall light
        scene.lights.add(new SpotLight(new Color(200,200,200), new Point(125,450,10),new Vector(0,-1,1)).setNarrowBeam(3));
        //scene.lights.add(new PointLight(new Color(200,200,200),new Point(125,450,15)));
        //endregion

        //region mirror
        // back
        Polygon mirrorBack = new Polygon(new Point(-200, 275, 1.1), new Point(450, 275, 1.1), new Point(450, 425, 1.1), new Point(-200, 425, 1.1));
        mirrorBack.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(mirrorBack);

        //mirror
        gap = 10;
        Polygon mirror = new Polygon(new Point(-200 + gap, 275 + gap, 1.2), new Point(450 - gap, 275 + gap, 1.2), new Point(450 - gap, 425 - gap, 1.2), new Point(-200 + gap, 425 - gap, 1.2));
        mirror.setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(mirror);
        //endregion

        //region marble
        Polygon marbleTopLeft = new Polygon(new Point(-225, 200, 3), new Point(50, 200, 3), new Point(50, 200, 100), new Point(-225, 200, 100));
        marbleTopLeft.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleTopLeft);

        Polygon marbleTopRight = new Polygon(new Point(225, 200, 3), new Point(500, 200, 3), new Point(500, 200, 100), new Point(225, 200, 100));
        marbleTopRight.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleTopRight);

        Polygon marbleFront = new Polygon(new Point(500, 200, 100), new Point(-225, 200, 100), new Point(-225, 175, 100), new Point(500, 175, 100));
        marbleFront.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleFront);

        Polygon marbleBottomLeft = new Polygon(new Point(-225, 175, 3), new Point(50, 175, 3), new Point(50, 175, 100), new Point(-225, 175, 100));
        marbleBottomLeft.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleBottomLeft);

        Polygon marbleBottomRight = new Polygon(new Point(225, 175, 3), new Point(500, 175, 3), new Point(500, 175, 100), new Point(225, 175, 100));
        marbleBottomRight.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleBottomRight);

        Polygon marbleTopClose = new Polygon(new Point(50, 200, 100), new Point(225, 200, 100), new Point(225, 200, 80), new Point(50, 200, 80));
        marbleTopClose.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleTopClose);

        Polygon marbleTopDistant = new Polygon(new Point(50, 200, 20), new Point(225, 200, 20), new Point(225, 200, 0), new Point(50, 200, 0));
        marbleTopDistant.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleTopDistant);

        Polygon marbleBottomClose = new Polygon(new Point(50, 175, 100), new Point(225, 175, 100), new Point(225, 175, 80), new Point(50, 175, 80));
        marbleBottomClose.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleBottomClose);

        Polygon marbleBottomDistant = new Polygon(new Point(50, 175, 20), new Point(225, 175, 20), new Point(225, 175, 0), new Point(50, 175, 0));
        marbleBottomDistant.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleBottomDistant);

        Polygon marbleLeft = new Polygon(new Point(-225, 200, 0), new Point(-225, 175, 0), new Point(-225, 175, 100), new Point(-225, 200, 100));
        marbleLeft.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleLeft);

        Polygon marbleHeightDistant = new Polygon(new Point(50, 175, 20), new Point(50, 200, 20), new Point(225, 200, 20), new Point(225, 175, 20));
        marbleHeightDistant.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleHeightDistant);

        Polygon marbleHeightRight = new Polygon(new Point(225, 175, 20), new Point(225, 200, 20), new Point(225, 200, 80), new Point(225, 175, 80));
        marbleHeightRight.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleHeightRight);

        Polygon marbleHeightLeft = new Polygon(new Point(50, 175, 20), new Point(50, 200, 20), new Point(50, 200, 80), new Point(50, 175, 80));
        marbleHeightLeft.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleHeightLeft);

        Polygon marbleHeightClose = new Polygon(new Point(50, 175, 80), new Point(50, 200, 80), new Point(225, 200, 80), new Point(225, 175, 80));
        marbleHeightClose.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(marbleHeightClose);
        //endregion

        //region sink
        int depth = 60;

        Polygon sinkDistant = new Polygon(new Point(50, 175, 20), new Point(120, 175 - depth, 40), new Point(155, 175 - depth, 40), new Point(225, 175, 20));
        sinkDistant.setEmission(new Color(225, 225, 225)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(sinkDistant);

        Polygon sinkRight = new Polygon(new Point(225, 175, 20), new Point(155, 175 - depth, 40), new Point(155, 175 - depth, 60), new Point(225, 175, 80));
        sinkRight.setEmission(new Color(225, 225, 225)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(sinkRight);

        Polygon sinkLeft = new Polygon(new Point(50, 175, 20), new Point(120, 175 - depth, 40), new Point(120, 175 - depth, 60), new Point(50, 175, 80));
        sinkLeft.setEmission(new Color(225, 225, 225)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(sinkLeft);

        Polygon sinkClose = new Polygon(new Point(50, 175, 80), new Point(120, 175 - depth, 60), new Point(155, 175 - depth, 60), new Point(225, 175, 80));
        sinkClose.setEmission(new Color(225, 225, 225)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(sinkClose);

        Polygon sinkBottom = new Polygon(new Point(120, 175 - depth, 60), new Point(155, 175 - depth, 60), new Point(155, 175 - depth, 40), new Point(120, 175 - depth, 40));
        sinkBottom.setEmission(new Color(225, 225, 225)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(sinkBottom);
        //endregion

        //region closets
        Polygon closetLeft = new Polygon(new Point(-225, 0, 100), new Point(-225, 0, 0), new Point(-225, tileHeight, 0), new Point(-225, tileHeight, 100));
        closetLeft.setEmission(new Color(100, 100, 100)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(closetLeft);
        tileHeight = 175;
        tileWidth = 180;
        int fixLeft;
        int fixRight;
        for (int j = 0; j < 4; j++) {
            fixLeft = 0;
            fixRight = 0;
            gap = 5;
            if (j == 1) {
                fixRight = 80;
                gap=2;
            }
            if (j == 2) {
                fixLeft = 80;
            }
            Polygon closetDoorFront = new Polygon(new Point(-225 + j * tileWidth, 0, 100), new Point(-225 + tileWidth + j * tileWidth - gap, 0, 100), new Point(-225 + +tileWidth + j * tileWidth - gap, tileHeight, 100), new Point(-225 + j * tileWidth, tileHeight, 100));
            closetDoorFront.setEmission(new Color(127, 255, 0)).setMaterial(new Material().setKs(0).setKd(0.5));
            scene.geometries.add(closetDoorFront);
            Polygon closetDoorLeft = new Polygon(new Point(-225 + j * tileWidth, 0, 100), new Point(-225 + j * tileWidth, 0, fixLeft), new Point(-225 + j * tileWidth, tileHeight, fixLeft), new Point(-225 + j * tileWidth, tileHeight, 100));
            closetDoorLeft.setEmission(new Color(100, 100, 100)).setMaterial(new Material().setKs(0).setKd(0.5));
            scene.geometries.add(closetDoorLeft);

            Polygon closetDoorRight = new Polygon(new Point(-225 + tileWidth + j * tileWidth - gap, 0, 100), new Point(-225 + tileWidth + j * tileWidth - gap, 0, fixRight), new Point(-225 + tileWidth + j * tileWidth - gap, tileHeight, fixRight), new Point(-225 + tileWidth + j * tileWidth - gap, tileHeight, 100));
            closetDoorRight.setEmission(new Color(100, 100, 100)).setMaterial(new Material().setKs(0).setKd(0.5));
            scene.geometries.add(closetDoorRight);

        }
        //endregion

        //region floor
        // background
        Polygon floorBackground = new Polygon(new Point(-500, -2, 0), new Point(-500, -2, 300), new Point(500, -2, 300), new Point(500, -2, 0));
        floorBackground.setMaterial(new Material());
        floorBackground.setEmission(new Color(122, 122, 122));
        scene.geometries.add(floorBackground);

        //floor tiles
        tileHeight = 50;
        tileWidth = 50;
        gap = 2;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 20; j++) {
                Polygon polygon = new Polygon(new Point(-500 + j * tileWidth, 0, i * tileHeight), new Point(-500 + tileWidth + j * tileWidth - gap, 0, i * tileHeight), new Point(-500 + tileWidth + j * tileWidth - gap, 0, tileHeight + i * tileHeight - gap), new Point(-500 + j * tileWidth, 0, tileHeight + i * tileHeight - gap));
                polygon.setEmission(new Color(143, 188, 143)).setMaterial(new Material().setKs(0).setKd(1));
                scene.geometries.add(polygon);
                //new Color(143,188,143)
            }
        }
        //endregion

        //region right wall
        // back
        Polygon rightWall = new Polygon(new Point(501, 0, 0), new Point(501, 0, 300), new Point(501, 500, 300), new Point(501, 500, 0));
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
        for (int j = 0; j < 15; j++) {
            Polygon polygon = new Polygon(new Point(499.9, 225, j * tileWidth), new Point(499.9, 225, tileWidth + j * tileWidth - gap), new Point(499.9, 225 + tileHeight, tileWidth + j * tileWidth - gap), new Point(499.9, 225 + tileHeight, j * tileWidth));
            polygon.setEmission(colorPallet[j % 5]).setMaterial(new Material().setKs(0).setKd(0.5));
            scene.geometries.add(polygon);
        }
        //endregion

        //region taking picture
        //front always first!
        renderFront(scene,camera);
        //From left
        renderSide(scene,camera);
        //From top
        renderTop(scene,camera);
        //endregion
    }

    //region rendering positions
    private void renderFront(Scene scene,Camera camera){
        camera.moveCamera(new Point(0, 300, 1000),new Point(0,250,50));
        ImageWriter imageWriter = new ImageWriter("zFront", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }

    private void renderSide(Scene scene,Camera camera){
        camera.moveCamera(new Point(-1000, 250,1000 ),new Point(0, 250,50 ));
        ImageWriter imageWriter = new ImageWriter("zSide", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }

    private void renderTop(Scene scene,Camera camera){
        camera.moveCamera(new Point(0, 1000, 500), new Point(0, 250, 50));
        ImageWriter imageWriter = new ImageWriter("zTop", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }
    //endregion

}
