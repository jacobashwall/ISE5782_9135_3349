package sampling;

import geometries.*;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

//ctrl+shift+numpad-/+ in order to collapse or expand
public class bathroom {
    /**
     * Produce an elaborate picture with several objects
     */
    @Test
    public void createPicture() {


        //region settings
        Sampling.setTargetAreaResolution(9);
        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point(0, 300, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2000, 2000).setVPDistance(1500);
        scene.background=new Color(122,122,122);

        //colored tiles
        Color[] colorPallet = {
                new Color(152, 251, 152),//green
                new Color(150, 150, 150),//grey
                new Color(240, 230, 140),//yellow
                new Color(205, 92, 92),//red
                new Color(72, 61, 139)//blue
        };

        //tiles
        int tileHeight;
        int tileWidth;
        int gap;
        //endregion

        //region front wall
        // back
        Polygon frontWall = new Polygon(new Point(-500, 0, 0), new Point(500, 0, 0), new Point(500, 500, 0), new Point(-500, 500, 0));
        frontWall.setMaterial(new Material());
        frontWall.setEmission(new Color(122, 122, 122));
        scene.geometries.add(frontWall);

        //front wall tiles
        tileHeight = 50;
        tileWidth = 100;
        gap = 2;
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
        scene.lights.add(new SpotLight(new Color(100, 100, 100), new Point(125, 450, 10), new Vector(0, -1, 1)).setNarrowBeam(3));
        Cylinder frontLight = new Cylinder(new Ray(new Point(75, 450, 0), new Vector(1, 0, 0)), 20, 100);
        frontLight.setMaterial(new Material().setKt(0.9));
        frontLight.setEmission(new Color(255, 255, 255));
        scene.geometries.add(frontLight);

        //extension light
        scene.lights.add(new SpotLight(new Color(100, 100, 100), new Point(-250, 490, 450), new Vector(0, -1, 0)).setNarrowBeam(3));
        Cylinder backLight = new Cylinder(new Ray(new Point(-250, 500, 450), new Vector(0, -1, 0)), 20, 15);
        backLight.setMaterial(new Material().setKt(0.9));
        backLight.setEmission(new Color(255, 255, 255));
        scene.geometries.add(backLight);

        //endregion

        //region mirror
        // back
        Polygon mirrorBack = new Polygon(new Point(-200, 275, 1.1), new Point(450, 275, 1.1), new Point(450, 425, 1.1), new Point(-200, 425, 1.1));
        mirrorBack.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(mirrorBack);

        //mirror
        gap = 10;
        Polygon mirror = new Polygon(new Point(-200 + gap, 275 + gap, 1.2), new Point(450 - gap, 275 + gap, 1.2), new Point(450 - gap, 425 - gap, 1.2), new Point(-200 + gap, 425 - gap, 1.2));
        mirror.setEmission(new Color(0, 0, 0)).setMaterial(new Material().setKs(0).setKd(0.5).setKr(1));
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

        Cylinder marbleFrontEdge = new Cylinder(new Ray(new Point(500, 187.5, 100), new Vector(-1, 0, 0)), 12.5, 725);
        marbleFrontEdge.setMaterial(new Material());
        marbleFrontEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(marbleFrontEdge);

        //endregion

        //region sink
        int depth = 60;

        Polygon sinkDistant = new Polygon(new Point(50, 190, 20), new Point(120, 190 - depth, 40), new Point(155, 190 - depth, 40), new Point(225, 190, 20));
        sinkDistant.setEmission(new Color(225, 225, 225)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(sinkDistant);

        Polygon sinkRight = new Polygon(new Point(225, 190, 20), new Point(155, 190 - depth, 40), new Point(155, 190 - depth, 60), new Point(225, 190, 80));
        sinkRight.setEmission(new Color(225, 225, 225)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(sinkRight);

        Polygon sinkLeft = new Polygon(new Point(50, 190, 20), new Point(120, 190 - depth, 40), new Point(120, 190 - depth, 60), new Point(50, 190, 80));
        sinkLeft.setEmission(new Color(225, 225, 225)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(sinkLeft);

        Polygon sinkClose = new Polygon(new Point(50, 190, 80), new Point(120, 190 - depth, 60), new Point(155, 190 - depth, 60), new Point(225, 190, 80));
        sinkClose.setEmission(new Color(225, 225, 225)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(sinkClose);

        Polygon sinkBottom = new Polygon(new Point(120, 190 - depth, 60), new Point(155, 190 - depth, 60), new Point(155, 190 - depth, 40), new Point(120, 190 - depth, 40));
        sinkBottom.setEmission(new Color(225, 225, 225)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(sinkBottom);

        Cylinder sinkHoleEdge = new Cylinder(new Ray(new Point(137.5, 190 - depth, 50), new Vector(0, 1, 0)), 3, 0.1);
        sinkHoleEdge.setMaterial(new Material());
        sinkHoleEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(sinkHoleEdge);

        Cylinder sinkHole = new Cylinder(new Ray(new Point(137.5, 190 - depth, 50), new Vector(0, 1, 0)), 2.5, 0.11);
        sinkHole.setMaterial(new Material());
        sinkHole.setEmission(new Color(255, 255, 255));
        scene.geometries.add(sinkHole);

        //sink tap
        Cylinder sinkTapBase = new Cylinder(new Ray(new Point(235, 200, 40), new Vector(0, 1, 0)), 5, 20);
        sinkTapBase.setMaterial(new Material());
        sinkTapBase.setEmission(new Color(122, 122, 122));
        scene.geometries.add(sinkTapBase);

        Cylinder sinkTapSpine = new Cylinder(new Ray(new Point(235, 220, 40), new Vector(-1, 1, 0.5)), 3, 60);
        sinkTapSpine.setMaterial(new Material());
        sinkTapSpine.setEmission(new Color(122, 122, 122));
        scene.geometries.add(sinkTapSpine);

        Cylinder sinkTapHead = new Cylinder(new Ray((new Point(235, 220, 40).add(new Vector(-1, 1, 0.5).scale(40))), new Vector(-1, 0, 0.5)), 3, 40);
        sinkTapHead.setMaterial(new Material());
        sinkTapHead.setEmission(new Color(122, 122, 122));
        scene.geometries.add(sinkTapHead);

        Cylinder sinkTapOpening = new Cylinder(new Ray(((new Point(235, 223, 40).add(new Vector(-1, 1, 0.5).scale(40))).add(new Vector(-1, 0, 0.5).scale(30))), new Vector(0, -1, 0)), 3, 8);
        sinkTapOpening.setMaterial(new Material());
        sinkTapOpening.setEmission(new Color(122, 122, 122));
        scene.geometries.add(sinkTapOpening);

        Cylinder sinkTapHandle = new Cylinder(new Ray(new Point(230, 215, 40), new Vector(-1, -0.5, 0)), 2, 15);
        sinkTapHandle.setMaterial(new Material());
        sinkTapHandle.setEmission(new Color(122, 122, 122));
        scene.geometries.add(sinkTapHandle);
        //endregion

        //region closets
        tileHeight = 50;
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
                gap = 2;
            }
            if (j == 2) {
                fixLeft = 80;
            }
            Polygon closetDoorFront = new Polygon(new Point(-225 + j * tileWidth, 0, 100), new Point(-225 + tileWidth + j * tileWidth - gap, 0, 100), new Point(-225 + +tileWidth + j * tileWidth - gap, tileHeight, 100), new Point(-225 + j * tileWidth, tileHeight, 100));
            closetDoorFront.setEmission(new Color(173,255,47)).setMaterial(new Material().setKs(0.5).setKd(0.5).setnShininess(300).setKr(0.01).setKsG(0));
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
                Polygon polygon = new Polygon(new Point(-500 + j * tileWidth, -1, i * tileHeight), new Point(-500 + tileWidth + j * tileWidth - gap, -1, i * tileHeight), new Point(-500 + tileWidth + j * tileWidth - gap, -1, tileHeight + i * tileHeight - gap), new Point(-500 + j * tileWidth, -1, tileHeight + i * tileHeight - gap));
                polygon.setEmission(new Color(143, 188, 143)).setMaterial(new Material().setKr(0.3).setKd(1).setKsG(0));
                scene.geometries.add(polygon);
                //new Color(143,188,143)
            }
        }

        // background extension
        Polygon floorBackgroundExtension = new Polygon(new Point(-500, -2, 300), new Point(-500, -2, 600), new Point(0, -2, 600), new Point(0, -2, 300));
        floorBackgroundExtension.setMaterial(new Material());
        floorBackgroundExtension.setEmission(new Color(122, 122, 122));
        scene.geometries.add(floorBackgroundExtension);


        //floor tiles extension
        tileHeight = 50;
        tileWidth = 50;
        gap = 2;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                Polygon polygon = new Polygon(new Point(-500 + j * tileWidth, -1, i * tileHeight + 300), new Point(-500 + tileWidth + j * tileWidth - gap, -1, i * tileHeight + 300), new Point(-500 + tileWidth + j * tileWidth - gap, -1, tileHeight + i * tileHeight - gap + 300), new Point(-500 + j * tileWidth, -1, tileHeight + i * tileHeight - gap + 300));
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

        //towels rack
        Cylinder rackSpine = new Cylinder(new Ray(new Point(500, 350, 100), new Vector(0, 0, 1)), 10, 100);
        rackSpine.setMaterial(new Material());
        rackSpine.setEmission(new Color(122, 122, 122));
        scene.geometries.add(rackSpine);


        for (int i = 1; i < 4; i++) {
            Cylinder hookBase = new Cylinder(new Ray(new Point(490, 350, 100 + i * 25), new Vector(-1, 0, 0)), 2, 25);
            hookBase.setMaterial(new Material());
            hookBase.setEmission(new Color(122, 122, 122));
            scene.geometries.add(hookBase);

            Cylinder hook = new Cylinder(new Ray(new Point(490 - 25, 350, 100 + i * 25), new Vector(-1, 0, 0)), 5, 3);
            hook.setMaterial(new Material());
            hook.setEmission(new Color(122, 122, 122));
            scene.geometries.add(hook);
        }
        //endregion

        //region right wall extension
        // back
        Polygon rightWallExtension = new Polygon(new Point(0.1, 0, 300), new Point(0.1, 0, 600), new Point(0.1, 500, 600), new Point(0.1, 500, 300));
        rightWallExtension.setMaterial(new Material());
        rightWallExtension.setEmission(new Color(122, 122, 122));
        scene.geometries.add(rightWallExtension);

        //right wall tiles extension
        tileHeight = 50;
        tileWidth = 100;
        gap = 2;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                Polygon polygon = new Polygon(new Point(0, i * tileHeight, 300 + j * tileWidth), new Point(0, i * tileHeight, 300 + tileWidth + j * tileWidth - gap), new Point(0, tileHeight + i * tileHeight - gap, 300 + tileWidth + j * tileWidth - gap), new Point(0, tileHeight + i * tileHeight - gap, 300 + j * tileWidth));
                polygon.setEmission(new Color(220, 220, 220)).setMaterial(new Material().setKs(1).setKd(0.5));
                scene.geometries.add(polygon);
            }
        }

        //right wall extension colored tile
        tileHeight = 50;
        tileWidth = 20;
        gap = 1;
        for (int j = 0; j < 15; j++) {
            Polygon polygon = new Polygon(new Point(-0.1, 225, 300 + j * tileWidth), new Point(-0.1, 225, 300 + tileWidth + j * tileWidth - gap), new Point(-0.1, 225 + tileHeight, 300 + tileWidth + j * tileWidth - gap), new Point(-0.1, 225 + tileHeight, 300 + j * tileWidth));
            polygon.setEmission(colorPallet[j % 5]).setMaterial(new Material().setKs(0).setKd(0.5));
            scene.geometries.add(polygon);
        }
        //endregion
/*
        //region back extension wall
        // back
        Polygon backWall = new Polygon(new Point(-500, 0, 600), new Point(-500, 500, 600), new Point(0, 500, 600), new Point(0, 0, 600));
        backWall.setMaterial(new Material());
        backWall.setEmission(new Color(122, 122, 122));
        scene.geometries.add(backWall);

        //back wall tiles
        tileHeight = 50;
        tileWidth = 100;
        gap = 2;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                Polygon polygon = new Polygon(new Point(-500 + j * tileWidth, i * tileHeight, 599.9), new Point(-500 + tileWidth + j * tileWidth - gap, i * tileHeight, 599.9), new Point(-500 + tileWidth + j * tileWidth - gap, tileHeight + i * tileHeight - gap, 599.9), new Point(-500 + j * tileWidth, tileHeight + i * tileHeight - gap, 599.9));
                polygon.setEmission(new Color(220, 220, 220)).setMaterial(new Material().setKs(1).setKd(0.5));
                scene.geometries.add(polygon);
            }
        }

        //colored tiles
        tileHeight = 50;
        tileWidth = 20;
        gap = 1;
        for (int j = 0; j < 25; j++) {
            Polygon polygon = new Polygon(new Point(-500 + j * tileWidth, 225, 599.8), new Point(-500 + tileWidth + j * tileWidth - gap, 225, 599.8), new Point(-500 + tileWidth + j * tileWidth - gap, 225 + tileHeight, 599.8), new Point(-500 + j * tileWidth, 225 + tileHeight, 599.8));
            polygon.setEmission(colorPallet[j % 5]).setMaterial(new Material().setKs(0).setKd(0.5));
            scene.geometries.add(polygon);
        }

        //endregion
 */

        //region door wall
        // back
        Polygon doorWall = new Polygon(new Point(0, 0, 300), new Point(0, 500, 300), new Point(500, 500, 300), new Point(500, 0, 300));
        doorWall.setMaterial(new Material());
        doorWall.setEmission(new Color(122, 122, 122));
        scene.geometries.add(doorWall);

        //back wall tiles
        tileHeight = 50;
        tileWidth = 100;
        gap = 2;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                Polygon polygon = new Polygon(new Point(j * tileWidth, i * tileHeight, 299.9), new Point(tileWidth + j * tileWidth - gap, i * tileHeight, 299.9), new Point(tileWidth + j * tileWidth - gap, tileHeight + i * tileHeight - gap, 299.9), new Point(j * tileWidth, tileHeight + i * tileHeight - gap, 299.9));
                polygon.setEmission(new Color(220, 220, 220)).setMaterial(new Material().setKs(1).setKd(0.5));
                scene.geometries.add(polygon);
            }
        }

        //colored tiles
        tileHeight = 50;
        tileWidth = 20;
        gap = 1;
        for (int j = 0; j < 25; j++) {
            Polygon polygon = new Polygon(new Point(j * tileWidth, 225, 299.8), new Point(tileWidth + j * tileWidth - gap, 225, 299.8), new Point(tileWidth + j * tileWidth - gap, 225 + tileHeight, 299.8), new Point(j * tileWidth, 225 + tileHeight, 299.8));
            polygon.setEmission(colorPallet[j % 5]).setMaterial(new Material().setKs(0).setKd(0.5));
            scene.geometries.add(polygon);
        }

        //endregion
/*
        //region left wall
        // back
        Polygon leftWall = new Polygon(new Point(-501, 0, 0), new Point(-501, 0, 600), new Point(-501, 500, 600), new Point(-501, 500, 0));
        leftWall.setMaterial(new Material());
        leftWall.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftWall);

        // wall tiles
        tileHeight = 50;
        tileWidth = 100;
        gap = 2;
        for (int i = 0; i < 10; i++) {//height
            for (int j = 0; j < 6; j++) {//width
                Polygon polygon = new Polygon(new Point(-500, i * tileHeight, j * tileWidth), new Point(-500, i * tileHeight, tileWidth + j * tileWidth - gap), new Point(-500, tileHeight + i * tileHeight - gap, tileWidth + j * tileWidth - gap), new Point(-500, tileHeight + i * tileHeight - gap, j * tileWidth));
                polygon.setEmission(new Color(220, 220, 220)).setMaterial(new Material().setKs(1).setKd(0.5));
                scene.geometries.add(polygon);
            }
        }

        // wall colored tile
        tileHeight = 50;
        tileWidth = 20;
        gap = 1;
        for (int j = 0; j < 30; j++) {
            Polygon polygon = new Polygon(new Point(-499.9, 225, j * tileWidth), new Point(-499.9, 225, tileWidth + j * tileWidth - gap), new Point(-499.9, 225 + tileHeight, tileWidth + j * tileWidth - gap), new Point(-499.9, 225 + tileHeight, j * tileWidth));
            polygon.setEmission(colorPallet[j % 5]).setMaterial(new Material().setKs(0).setKd(0.5));
            scene.geometries.add(polygon);
        }
        //endregion

 */

        //region ceiling

        // background
        Polygon ceilingBackground = new Polygon(new Point(-500, 501, 0), new Point(-500, 501, 300), new Point(500, 501, 300), new Point(500, 501, 0));
        ceilingBackground.setMaterial(new Material());
        ceilingBackground.setEmission(new Color(200, 200, 200));
        scene.geometries.add(ceilingBackground);

        // ceiling extension
        Polygon ceilingBackgroundExtension = new Polygon(new Point(-500, 501, 300), new Point(-500, 501, 600), new Point(0, 501, 600), new Point(0, 501, 300));
        ceilingBackgroundExtension.setMaterial(new Material());
        ceilingBackgroundExtension.setEmission(new Color(200, 200, 200));
        scene.geometries.add(ceilingBackgroundExtension);
        //endregion

        //region shower
        //right glass
        Polygon showerRight = new Polygon(new Point(-250, 0, 200), new Point(-250, 0, 0), new Point(-250, 450, 0), new Point(-250, 450, 200));
        showerRight.setEmission(new Color(87, 164, 133)).setMaterial(new Material().setKs(0).setKd(0.5).setKt(0.5));
        scene.geometries.add(showerRight);

        //front glass
        Polygon showerFront = new Polygon(new Point(-250, 0, 200), new Point(-250, 450, 200), new Point(-500, 450, 200), new Point(-500, 0, 200));
        showerFront.setEmission(new Color(87, 164, 133)).setMaterial(new Material().setKs(0).setKd(0.5).setKt(0.5));
        scene.geometries.add(showerFront);

        //surface
        Polygon showerSurface = new Polygon(new Point(-250, 0, 200), new Point(-500, 0, 200), new Point(-500, 0, 1.2), new Point(-250, 0, 1.2));
        showerSurface.setEmission(new Color(143, 188, 143)).setMaterial(new Material().setKs(0).setKd(0.5).setKt(0));
        scene.geometries.add(showerSurface);

        //edges
        Cylinder leftBottomShowerEdge = new Cylinder(new Ray(new Point(-250, 0, 100), new Vector(0, 0, -1)), 10, 100);
        leftBottomShowerEdge.setMaterial(new Material());
        leftBottomShowerEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftBottomShowerEdge);

        Cylinder leftTopShowerEdge = new Cylinder(new Ray(new Point(-250, 450, 100), new Vector(0, 0, -1)), 10, 100);
        leftTopShowerEdge.setMaterial(new Material());
        leftTopShowerEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftTopShowerEdge);

        Cylinder frontTopShowerEdge = new Cylinder(new Ray(new Point(-350, 450, 200), new Vector(-1, 0, 0)), 10, 150);
        frontTopShowerEdge.setMaterial(new Material());
        frontTopShowerEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(frontTopShowerEdge);

        Cylinder frontBottomShowerEdge = new Cylinder(new Ray(new Point(-350, 0, 200), new Vector(-1, 0, 0)), 10, 150);
        frontBottomShowerEdge.setMaterial(new Material());
        frontBottomShowerEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(frontBottomShowerEdge);

        Cylinder frontLeftShowerDoor = new Cylinder(new Ray(new Point(-250, 0, 97.5), new Vector(0, 1, 0)), 5, 450);
        frontLeftShowerDoor.setMaterial(new Material());
        frontLeftShowerDoor.setEmission(new Color(122, 122, 122));
        scene.geometries.add(frontLeftShowerDoor);

        Cylinder backLeftShowerDoor = new Cylinder(new Ray(new Point(-250, 0, 0), new Vector(0, 1, 0)), 5, 450);
        backLeftShowerDoor.setMaterial(new Material());
        backLeftShowerDoor.setEmission(new Color(122, 122, 122));
        scene.geometries.add(backLeftShowerDoor);

        Cylinder leftShowerDoorFront = new Cylinder(new Ray(new Point(-352.5, 0, 200), new Vector(0, 1, 0)), 5, 450);
        leftShowerDoorFront.setMaterial(new Material());
        leftShowerDoorFront.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftShowerDoorFront);

        Cylinder rightShowerDoorFront = new Cylinder(new Ray(new Point(-500, 0, 200), new Vector(0, 1, 0)), 5, 450);
        rightShowerDoorFront.setMaterial(new Material());
        rightShowerDoorFront.setEmission(new Color(122, 122, 122));
        scene.geometries.add(rightShowerDoorFront);

        Cylinder creek = new Cylinder(new Ray(new Point(-250, 0, 200), new Vector(0, 1, 0)), 2, 450);
        creek.setMaterial(new Material());
        creek.setEmission(new Color(122, 122, 122));
        scene.geometries.add(creek);

        //spheres
        Sphere frontTopShowerSphere = new Sphere(new Point(-350, 450, 200), 10);
        frontTopShowerSphere.setMaterial(new Material());
        frontTopShowerSphere.setEmission(new Color(122, 122, 122));
        scene.geometries.add(frontTopShowerSphere);

        Sphere frontBottomShowerSphere = new Sphere(new Point(-350, 0, 200), 10);
        frontBottomShowerSphere.setMaterial(new Material());
        frontBottomShowerSphere.setEmission(new Color(122, 122, 122));
        scene.geometries.add(frontBottomShowerSphere);

        Sphere leftBottomShowerSphere = new Sphere(new Point(-250, 0, 100), 10);
        leftBottomShowerSphere.setMaterial(new Material());
        leftBottomShowerSphere.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftBottomShowerSphere);

        Sphere leftTopShowerSphere = new Sphere(new Point(-250, 450, 100), 10);
        leftTopShowerSphere.setMaterial(new Material());
        leftTopShowerSphere.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftTopShowerSphere);

        //shower tap
        Cylinder showerTapBase = new Cylinder(new Ray(new Point(-400, 500, 100), new Vector(0, -1, 0)), 10, 20);
        showerTapBase.setMaterial(new Material());
        showerTapBase.setEmission(new Color(122, 122, 122));
        scene.geometries.add(showerTapBase);

        Sphere showerTapSpineSphere = new Sphere(new Point(-400, 480, 100), 10);
        showerTapSpineSphere.setMaterial(new Material());
        showerTapSpineSphere.setEmission(new Color(122, 122, 122));
        scene.geometries.add(showerTapSpineSphere);

        Cylinder showerTapSpine = new Cylinder(new Ray(new Point(-400, 480, 100), new Vector(0, -1, 0)), 5, 50);
        showerTapSpine.setMaterial(new Material());
        showerTapSpine.setEmission(new Color(122, 122, 122));
        scene.geometries.add(showerTapSpine);

        Sphere showerTapVerticalSphere = new Sphere(new Point(-400, 430, 100), 5);
        showerTapVerticalSphere.setMaterial(new Material());
        showerTapVerticalSphere.setEmission(new Color(122, 122, 122));
        scene.geometries.add(showerTapVerticalSphere);

        Cylinder showerTapVertical = new Cylinder(new Ray(new Point(-400, 430, 100), new Vector(0, -1, 0)), 3, 20);
        showerTapVertical.setMaterial(new Material());
        showerTapVertical.setEmission(new Color(122, 122, 122));
        scene.geometries.add(showerTapVertical);

        Cylinder showerTapHead = new Cylinder(new Ray(new Point(-400, 410, 100), new Vector(0, -1, 0)), 30, 4);
        showerTapHead.setMaterial(new Material());
        showerTapHead.setEmission(new Color(122, 122, 122));
        scene.geometries.add(showerTapHead);

        Cylinder showerTapHandleBase = new Cylinder(new Ray(new Point(-325, 250, 1.2), new Vector(0, 0, 1)), 15, 10);
        showerTapHandleBase.setMaterial(new Material());
        showerTapHandleBase.setEmission(new Color(122, 122, 122));
        scene.geometries.add(showerTapHandleBase);

        Cylinder showerTapHandle = new Cylinder(new Ray(new Point(-325, 240, 6.2), new Vector(0, -1, 0)), 2.5, 20);
        showerTapHandle.setMaterial(new Material());
        showerTapHandle.setEmission(new Color(122, 122, 122));
        scene.geometries.add(showerTapHandle);


        //endregion

        //region door
        //back
        Polygon backDoor = new Polygon(new Point(200, 0, 299.7), new Point(200, 400, 299.7), new Point(450, 400, 299.7), new Point(450, 0, 299.7));
        backDoor.setMaterial(new Material());
        backDoor.setEmission(new Color(0, 0, 0));
        scene.geometries.add(backDoor);

        int frameGap = 10;
        //right frame
        Polygon doorRightFrameRight = new Polygon(new Point(450, 400, 299.7), new Point(450, 0, 299.7), new Point(450, 0, 299.7 - frameGap), new Point(450, 400, 299.7 - frameGap));
        doorRightFrameRight.setMaterial(new Material());
        doorRightFrameRight.setEmission(new Color(25, 25, 112));
        scene.geometries.add(doorRightFrameRight);

        Polygon doorRightFrameLeft = new Polygon(new Point(450 - frameGap, 400, 299.7), new Point(450 - frameGap, 0, 299.7), new Point(450 - frameGap, 0, 299.7 - frameGap), new Point(450 - frameGap, 400, 299.7 - frameGap));
        doorRightFrameLeft.setMaterial(new Material());
        doorRightFrameLeft.setEmission(new Color(25, 25, 112));
        scene.geometries.add(doorRightFrameLeft);

        Polygon doorRightFrameFront = new Polygon(new Point(450 - frameGap, 400, 299.7 - frameGap), new Point(450 - frameGap, 0, 299.7 - frameGap), new Point(450, 0, 299.7 - frameGap), new Point(450, 400, 299.7 - frameGap));
        doorRightFrameFront.setMaterial(new Material());
        doorRightFrameFront.setEmission(new Color(25, 25, 112));
        scene.geometries.add(doorRightFrameFront);

        //left frame
        Polygon doorLeftFrameLeft = new Polygon(new Point(200, 400, 299.7), new Point(200, 0, 299.7), new Point(200, 0, 299.7 - frameGap), new Point(200, 400, 299.7 - frameGap));
        doorLeftFrameLeft.setMaterial(new Material());
        doorLeftFrameLeft.setEmission(new Color(25, 25, 112));
        scene.geometries.add(doorLeftFrameLeft);

        Polygon doorLeftFrameRight = new Polygon(new Point(200 + frameGap, 400, 299.7), new Point(200 + frameGap, 0, 299.7), new Point(200 + frameGap, 0, 299.7 - frameGap), new Point(200 + frameGap, 400, 299.7 - frameGap));
        doorLeftFrameRight.setMaterial(new Material());
        doorLeftFrameRight.setEmission(new Color(25, 25, 112));
        scene.geometries.add(doorLeftFrameRight);

        Polygon doorLeftFrameFront = new Polygon(new Point(200 + frameGap, 400, 299.7 - frameGap), new Point(200 + frameGap, 0, 299.7 - frameGap), new Point(200, 0, 299.7 - frameGap), new Point(200, 400, 299.7 - frameGap));
        doorLeftFrameFront.setMaterial(new Material());
        doorLeftFrameFront.setEmission(new Color(25, 25, 112));
        scene.geometries.add(doorLeftFrameFront);

        //top frame
        Polygon doorTopFrameBottom = new Polygon(new Point(200, 400, 299.7), new Point(200, 400, 299.7 - frameGap), new Point(450, 400, 299.7 - frameGap), new Point(450, 400, 299.7));
        doorTopFrameBottom.setMaterial(new Material());
        doorTopFrameBottom.setEmission(new Color(25, 25, 112));
        scene.geometries.add(doorTopFrameBottom);

        Polygon doorTopFrameTop = new Polygon(new Point(200, 400 + frameGap, 299.7), new Point(200, 400 + frameGap, 299.7 - frameGap), new Point(450, 400 + frameGap, 299.7 - frameGap), new Point(450, 400 + frameGap, 299.7));
        doorTopFrameTop.setMaterial(new Material());
        doorTopFrameTop.setEmission(new Color(25, 25, 112));
        scene.geometries.add(doorTopFrameTop);

        Polygon doorTopFrameFront = new Polygon(new Point(200, 400 + frameGap, 299.7 - frameGap), new Point(200, 400, 299.7 - frameGap), new Point(450, 400, 299.7 - frameGap), new Point(450, 400 + frameGap, 299.7 - frameGap));
        doorTopFrameFront.setMaterial(new Material());
        doorTopFrameFront.setEmission(new Color(25, 25, 112));
        scene.geometries.add(doorTopFrameFront);

        //door top and bottom
        Polygon doorTop = new Polygon(new Point(200 + 1.5 * frameGap, 400, 299.7), new Point(450 - 1.5 * frameGap, 400, 299.7), new Point(450 - 1.5 * frameGap, 400, 299.7 - 1.5 * frameGap), new Point(200 + 1.5 * frameGap, 400, 299.7 - 1.5 * frameGap));
        doorTop.setMaterial(new Material());
        doorTop.setEmission(new Color(95, 158, 160));
        scene.geometries.add(doorTop);

        Polygon doorBottom = new Polygon(new Point(200 + 1.5 * frameGap, 0, 299.7), new Point(450 - 1.5 * frameGap, 0, 299.7), new Point(450 - 1.5 * frameGap, 0, 299.7 - 1.5 * frameGap), new Point(200 + 1.5 * frameGap, 0, 299.7 - 1.5 * frameGap));
        doorBottom.setMaterial(new Material());
        doorBottom.setEmission(new Color(95, 158, 160));
        scene.geometries.add(doorBottom);

        //door right
        Polygon doorRightSideRight = new Polygon(new Point(450 - 1.5 * frameGap, 400, 299.7), new Point(450 - 1.5 * frameGap, 0, 299.7), new Point(450 - 1.5 * frameGap, 0, 299.7 - 1.5 * frameGap), new Point(450 - 1.5 * frameGap, 400, 299.7 - 1.5 * frameGap));
        doorRightSideRight.setMaterial(new Material());
        doorRightSideRight.setEmission(new Color(95, 158, 160));
        scene.geometries.add(doorRightSideRight);

        Polygon doorRightSideLeft = new Polygon(new Point(450 - 1.5 * frameGap - 50, 400, 299.7), new Point(450 - 1.5 * frameGap - 50, 0, 299.7), new Point(450 - 1.5 * frameGap - 50, 0, 299.7 - 1.5 * frameGap), new Point(450 - 1.5 * frameGap - 50, 400, 299.7 - 1.5 * frameGap));
        doorRightSideLeft.setMaterial(new Material());
        doorRightSideLeft.setEmission(new Color(95, 158, 160));
        scene.geometries.add(doorRightSideLeft);

        Polygon doorRightSideFront = new Polygon(new Point(450 - 1.5 * frameGap, 400, 299.7 - 1.5 * frameGap), new Point(450 - 1.5 * frameGap, 0, 299.7 - 1.5 * frameGap), new Point(450 - 1.5 * frameGap - 50, 0, 299.7 - 1.5 * frameGap), new Point(450 - 1.5 * frameGap - 50, 400, 299.7 - 1.5 * frameGap));
        doorRightSideFront.setMaterial(new Material());
        doorRightSideFront.setEmission(new Color(95, 158, 160));
        scene.geometries.add(doorRightSideFront);

        //door left
        Polygon doorLeftSideRight = new Polygon(new Point(200 + 1.5 * frameGap, 400, 299.7), new Point(200 + 1.5 * frameGap, 0, 299.7), new Point(200 + 1.5 * frameGap, 0, 299.7 - 1.5 * frameGap), new Point(200 + 1.5 * frameGap, 400, 299.7 - 1.5 * frameGap));
        doorLeftSideRight.setMaterial(new Material());
        doorLeftSideRight.setEmission(new Color(95, 158, 160));
        scene.geometries.add(doorLeftSideRight);

        Polygon doorLeftSideLeft = new Polygon(new Point(200 + 1.5 * frameGap + 100, 400, 299.7), new Point(200 + 1.5 * frameGap + 100, 0, 299.7), new Point(200 + 1.5 * frameGap + 100, 0, 299.7 - 1.5 * frameGap), new Point(200 + 1.5 * frameGap + 100, 400, 299.7 - 1.5 * frameGap));
        doorLeftSideLeft.setMaterial(new Material());
        doorLeftSideLeft.setEmission(new Color(95, 158, 160));
        scene.geometries.add(doorLeftSideLeft);

        Polygon doorLeftSideFront = new Polygon(new Point(200 + 1.5 * frameGap, 400, 299.7 - 1.5 * frameGap), new Point(200 + 1.5 * frameGap, 0, 299.7 - 1.5 * frameGap), new Point(200 + 1.5 * frameGap + 100, 0, 299.7 - 1.5 * frameGap), new Point(200 + 1.5 * frameGap + 100, 400, 299.7 - 1.5 * frameGap));
        doorLeftSideFront.setMaterial(new Material());
        doorLeftSideFront.setEmission(new Color(95, 158, 160));
        scene.geometries.add(doorLeftSideFront);

        //door bottom side
        Polygon doorBottomFront = new Polygon(new Point(450 - 1.5 * frameGap - 50, 300, 299.7 - 1.5 * frameGap), new Point(200 + 1.5 * frameGap + 100, 300, 299.7 - 1.5 * frameGap), new Point(200 + 1.5 * frameGap + 100, 0, 299.7 - 1.5 * frameGap), new Point(450 - 1.5 * frameGap - 50, 0, 299.7 - 1.5 * frameGap));
        doorBottomFront.setMaterial(new Material());
        doorBottomFront.setEmission(new Color(95, 158, 160));
        scene.geometries.add(doorBottomFront);

        Polygon doorBottomRight = new Polygon(new Point(450 - 1.5 * frameGap - 50, 300, 299.7), new Point(450 - 1.5 * frameGap - 50, 0, 299.7), new Point(450 - 1.5 * frameGap - 50, 0, 299.7 - 1.5 * frameGap), new Point(450 - 1.5 * frameGap - 50, 300, 299.7 - 1.5 * frameGap));
        doorBottomRight.setMaterial(new Material());
        doorBottomRight.setEmission(new Color(95, 158, 160));
        scene.geometries.add(doorBottomRight);

        Polygon doorBottomLeft = new Polygon(new Point(200 + 1.5 * frameGap + 100, 300, 299.7), new Point(200 + 1.5 * frameGap + 100, 0, 299.7), new Point(200 + 1.5 * frameGap + 100, 0, 299.7 - 1.5 * frameGap), new Point(200 + 1.5 * frameGap + 100, 300, 299.7 - 1.5 * frameGap));
        doorBottomLeft.setMaterial(new Material());
        doorBottomLeft.setEmission(new Color(95, 158, 160));
        scene.geometries.add(doorBottomLeft);

        //door top side
        Polygon doorTopFront = new Polygon(new Point(450 - 1.5 * frameGap - 50, 400, 299.7 - 1.5 * frameGap), new Point(200 + 1.5 * frameGap + 100, 400, 299.7 - 1.5 * frameGap), new Point(200 + 1.5 * frameGap + 100, 350, 299.7 - 1.5 * frameGap), new Point(450 - 1.5 * frameGap - 50, 350, 299.7 - 1.5 * frameGap));
        doorTopFront.setMaterial(new Material());
        doorTopFront.setEmission(new Color(95, 158, 160));
        scene.geometries.add(doorTopFront);

        Polygon doorTopRight = new Polygon(new Point(450 - 1.5 * frameGap - 50, 400, 299.7), new Point(450 - 1.5 * frameGap - 50, 350, 299.7), new Point(450 - 1.5 * frameGap - 50, 350, 299.7 - 1.5 * frameGap), new Point(450 - 1.5 * frameGap - 50, 400, 299.7 - 1.5 * frameGap));
        doorTopRight.setMaterial(new Material());
        doorTopRight.setEmission(new Color(95, 158, 160));
        scene.geometries.add(doorTopRight);

        Polygon doorTopLeft = new Polygon(new Point(200 + 1.5 * frameGap + 100, 400, 299.7), new Point(200 + 1.5 * frameGap + 100, 350, 299.7), new Point(200 + 1.5 * frameGap + 100, 350, 299.7 - 1.5 * frameGap), new Point(200 + 1.5 * frameGap + 100, 400, 299.7 - 1.5 * frameGap));
        doorTopLeft.setMaterial(new Material());
        doorTopLeft.setEmission(new Color(95, 158, 160));
        scene.geometries.add(doorTopLeft);

        //door window
        Polygon doorWindow = new Polygon(new Point(450 - 1.5 * frameGap - 50, 300, 299.7 - 0.75 * frameGap), new Point(200 + 1.5 * frameGap + 100, 300, 299.7 - 0.75 * frameGap), new Point(200 + 1.5 * frameGap + 100, 350, 299.7 - 0.75 * frameGap), new Point(450 - 1.5 * frameGap - 50, 350, 299.7 - 0.75 * frameGap));
        doorWindow.setMaterial(new Material().setKt(0.5));
        doorWindow.setEmission(new Color(173, 216, 230));
        scene.geometries.add(doorWindow);

        //door window frame
        Cylinder rightWindowFrame = new Cylinder(new Ray(new Point(450 - 1.5 * frameGap - 50, 300, 299.7 - 0.75 * frameGap), new Vector(0, 1, 0)), 4, 50);
        rightWindowFrame.setMaterial(new Material());
        rightWindowFrame.setEmission(new Color(222, 184, 135));
        scene.geometries.add(rightWindowFrame);

        Cylinder bottomWindowFrame = new Cylinder(new Ray(new Point(450 - 1.5 * frameGap - 50, 300, 299.7 - 0.75 * frameGap), new Vector(-1, 0, 0)), 4, 450 - (200 + 1.5 * frameGap + 100));
        bottomWindowFrame.setMaterial(new Material());
        bottomWindowFrame.setEmission(new Color(222, 184, 135));
        scene.geometries.add(bottomWindowFrame);

        Cylinder leftWindowFrame = new Cylinder(new Ray(new Point(200 + 1.5 * frameGap + 100, 300, 299.7 - 0.75 * frameGap), new Vector(0, 1, 0)), 4, 50);
        leftWindowFrame.setMaterial(new Material());
        leftWindowFrame.setEmission(new Color(222, 184, 135));
        scene.geometries.add(leftWindowFrame);

        Cylinder topWindowFrame = new Cylinder(new Ray(new Point(450 - 1.5 * frameGap - 50, 350, 299.7 - 0.75 * frameGap), new Vector(-1, 0, 0)), 4, 450 - (200 + 1.5 * frameGap + 100));
        topWindowFrame.setMaterial(new Material());
        topWindowFrame.setEmission(new Color(222, 184, 135));
        scene.geometries.add(topWindowFrame);


        //endregion

        //region edges
        //front edges
        Cylinder frontTopEdge = new Cylinder(new Ray(new Point(-500, 500, 0), new Vector(1, 0, 0)), 5, 1000);
        frontTopEdge.setMaterial(new Material());
        frontTopEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(frontTopEdge);

        Cylinder frontBotEdge = new Cylinder(new Ray(new Point(-500, 0, 0), new Vector(1, 0, 0)), 5, 1000);
        frontBotEdge.setMaterial(new Material());
        frontBotEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(frontBotEdge);

        //left edge
        Cylinder leftBotEdge = new Cylinder(new Ray(new Point(-500, 0, 0), new Vector(0, 0, 1)), 5, 600);
        leftBotEdge.setMaterial(new Material());
        leftBotEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftBotEdge);
/*
        Cylinder leftTopEdge = new Cylinder(new Ray(new Point(-500, 500, 0), new Vector(0, 0, 1)), 5, 600);
        leftTopEdge.setMaterial(new Material());
        leftTopEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftTopEdge);


 */

        //right edge
        Cylinder rightBotEdge = new Cylinder(new Ray(new Point(500, 0, 0), new Vector(0, 0, 1)), 5, 300);
        rightBotEdge.setMaterial(new Material());
        rightBotEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(rightBotEdge);

        Cylinder rightTopEdge = new Cylinder(new Ray(new Point(500, 500, 0), new Vector(0, 0, 1)), 5, 300);
        rightTopEdge.setMaterial(new Material());
        rightTopEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(rightTopEdge);

        //back right edge
        Cylinder rightBackBotEdge = new Cylinder(new Ray(new Point(500, 0, 300), new Vector(-1, 0, 0)), 5, 300);
        rightBackBotEdge.setMaterial(new Material());
        rightBackBotEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(rightBackBotEdge);

        Cylinder rightBackTopEdge = new Cylinder(new Ray(new Point(500, 500, 300), new Vector(-1, 0, 0)), 5, 300);
        rightBackTopEdge.setMaterial(new Material());
        rightBackTopEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(rightBackTopEdge);

        //back left edge
        Cylinder leftBackBotEdge = new Cylinder(new Ray(new Point(-500, 0, 600), new Vector(1, 0, 0)), 5, 600);
        leftBackBotEdge.setMaterial(new Material());
        leftBackBotEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftBackBotEdge);

        Cylinder leftBackTopEdge = new Cylinder(new Ray(new Point(-500, 500, 600), new Vector(1, 0, 0)), 5, 600);
        leftBackTopEdge.setMaterial(new Material());
        leftBackTopEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftBackTopEdge);

        //back sided edge
        Cylinder leftSideBotEdge = new Cylinder(new Ray(new Point(0, 0, 300), new Vector(0, 0, 1)), 5, 300);
        leftSideBotEdge.setMaterial(new Material());
        leftSideBotEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftSideBotEdge);

        Cylinder leftSideTopEdge = new Cylinder(new Ray(new Point(0, 500, 300), new Vector(0, 0, 1)), 5, 300);
        leftSideTopEdge.setMaterial(new Material());
        leftSideTopEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftSideTopEdge);

        //vertical edges
        Cylinder rightFrontEdge = new Cylinder(new Ray(new Point(500, 0, 0), new Vector(0, 1, 0)), 1, 500);
        rightFrontEdge.setMaterial(new Material());
        rightFrontEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(rightFrontEdge);

        Cylinder leftFrontEdge = new Cylinder(new Ray(new Point(-500, 0, 0), new Vector(0, 1, 0)), 1, 500);
        leftFrontEdge.setMaterial(new Material());
        leftFrontEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftFrontEdge);

        /*
        Cylinder leftBackEdge = new Cylinder(new Ray(new Point(-500, 0, 600), new Vector(0, 1, 0)), 1, 500);
        leftBackEdge.setMaterial(new Material());
        leftBackEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftBackEdge);

         */

        Cylinder middleBackBackEdge = new Cylinder(new Ray(new Point(0, 0, 600), new Vector(0, 1, 0)), 1, 500);
        middleBackBackEdge.setMaterial(new Material());
        middleBackBackEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(middleBackBackEdge);

        Cylinder middleFrontBackEdge = new Cylinder(new Ray(new Point(0, 0, 300), new Vector(0, 1, 0)), 1, 500);
        middleFrontBackEdge.setMaterial(new Material());
        middleFrontBackEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(middleFrontBackEdge);

        Cylinder rightBackEdge = new Cylinder(new Ray(new Point(500, 0, 300), new Vector(0, 1, 0)), 1, 500);
        rightBackEdge.setMaterial(new Material());
        rightBackEdge.setEmission(new Color(122, 122, 122));
        scene.geometries.add(rightBackEdge);
        //endregion

        //region taking picture
        //front always first!
        //renderFront(scene,camera);
        //From left
        //renderLeft(scene,camera);
        //From right
        //renderRight(scene,camera);
        //From top
        //renderTop(scene,camera);
        //From back
        //renderBack(scene,camera);
        //Final picture
        renderFinal(scene, camera);
        //endregion
    }

    //region rendering positions
    private void renderFront(Scene scene, Camera camera) {
        camera.moveCamera(new Point(0, 300, 1000), new Point(0, 250, 50));
        ImageWriter imageWriter = new ImageWriter("zFront", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }

    private void renderLeft(Scene scene, Camera camera) {
        camera.moveCamera(new Point(-1000, 250, 1000), new Point(0, 250, 50));
        ImageWriter imageWriter = new ImageWriter("zLeft", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }

    private void renderRight(Scene scene, Camera camera) {
        camera.moveCamera(new Point(1000, 400, 1000), new Point(0, 250, 50));
        ImageWriter imageWriter = new ImageWriter("zRight", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }

    private void renderTop(Scene scene, Camera camera) {
        camera.moveCamera(new Point(0, 1000, 500), new Point(0, 250, 50));
        ImageWriter imageWriter = new ImageWriter("zTop", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }

    private void renderBack(Scene scene, Camera camera) {
        camera.moveCamera(new Point(0, 300, -1000), new Point(0, 250, 50));
        ImageWriter imageWriter = new ImageWriter("zBack", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }

    private void renderFinal(Scene scene, Camera camera) {
        camera.moveCamera(new Point(-920, 420, 1100), new Point(0, 200, 0))
                .setVPDistance(4000);
        ImageWriter imageWriter = new ImageWriter("zFinal", 500, 500);
        scene.setResolution(5);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerRegular(scene)) //
                .renderImage(); //
        camera.writeToImage();
        // camera.moveCamera(new Point(-900, 400, 1100), new Point(0, 200, 0))
        //                .setVPDistance(4000);
    }
    //endregion

}
