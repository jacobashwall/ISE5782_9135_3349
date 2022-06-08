package lighting;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;
import static java.awt.Color.*;

/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class LightsTests {
	private Scene scene1 = new Scene("Test scene");
	private Scene scene2 = new Scene("Test scene") //
			.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
	private Scene scene3 = new Scene("Test scene");
	private Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(150, 150) //
			.setVPDistance(1000);
	private Camera camera2 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(200, 200) //
			.setVPDistance(1000);
	 private Camera camera3 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVPSize(200, 200) //
			.setVPDistance(1000);

	private Point[] p = { // The Triangles' vertices:
			new Point(-110, -110, -150), // the shared left-bottom
			new Point(95, 100, -150), // the shared right-top
			new Point(110, -110, -150), // the right-bottom
			new Point(-75, 78, 100) }; // the left-top
	private Point trPL = new Point(30, 10, -100); // Triangles test Position of Light
	private Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
	private Color trCL = new Color(800, 500, 250); // Triangles test Color of Light
	private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light
	private Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light
	private Material material = new Material().setKd(0.5).setKs(0.5).setnShininess(300);
	private Geometry triangle1 = new Triangle(p[0], p[1], p[2]).setMaterial(material);
	private Geometry triangle2 = new Triangle(p[0], p[1], p[3]).setMaterial(material);
	private Geometry tube1 = new Tube(new Ray(new Point (1,2,3), new Vector(1,1,0)), 50)
			.setEmission(new Color(GRAY).reduce(1.5))//
			.setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(300));
	private Geometry sphere = new Sphere(new Point(0, 0, -50), 50d) //
			.setEmission(new Color(BLUE).reduce(2)) //
			.setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(300));

	/**
	 * Produce a picture of a sphere lighted by a directional light
	 */
	@Test
	public void sphereDirectional() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new DirectionalLight(spCL, new Vector(1, 1, -0.5)));

		ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage(); //
		camera1.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by a point light
	 */
	@Test
	public void spherePoint() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new PointLight(spCL, spPL).setKl(0.001).setKq(0.0002));

		ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage(); //
		camera1.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by a spotLight
	 */
	@Test
	public void sphereSpot() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setKl(0.001).setKq(0.0001));

		scene1.setResolution(5);
		ImageWriter imageWriter = new ImageWriter("RegularlightSphereSpot", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.setBaseOrRegular(true)
				.renderImage(); //
		camera1.writeToImage(); //
	}

	@Test
	public void normalSphereSpot() {
		scene1.geometries.add(sphere);
		scene1.lights.add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setKl(0.001).setKq(0.0001));

		scene1.setResolution(5);
		ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 50, 50);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				//.setBaseOrRegular(true)
				.renderImage(); //
		camera1.writeToImage(); //
	}
	/**
	 * Produce a picture of two triangles lighted by a directional light
	 */
	@Test
	public void trianglesDirectional() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new DirectionalLight(trCL, trDL));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage(); //
		camera2.writeToImage(); //
	}

	/**
	 * Produce a picture of two triangles lighted by a point light
	 */
	@Test
	public void trianglesPoint() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new PointLight(trCL, trPL).setKl(0.001).setKq(0.0002));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage(); //
		camera2.writeToImage(); //
	}

/**
	 * Produce a picture of two triangles lighted by a spot light
	 */

	@Test
	public void trianglesSpot() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new SpotLight(trCL, trPL, trDL).setKl(0.001).setKq(0.0001));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage(); //
		camera2.writeToImage(); //
	}


/**
	 * Produce a picture of a sphere lighted by a narrow spot light
	 */

	@Test
	public void sphereSpotSharp() {
		scene1.geometries.add(sphere);
		scene1.lights
				.add(new SpotLight(spCL, spPL, new Vector(1, 1, -0.5)).setNarrowBeam(10).setKl(0.001).setKq(0.00004));

		ImageWriter imageWriter = new ImageWriter("lightSphereSpotSharp", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage(); //
		camera1.writeToImage(); //
	}

/**
	 * Produce a picture of two triangles lighted by a narrow spot light
	 */

	@Test
	public void trianglesSpotSharp() {
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new SpotLight(trCL, trPL, trDL).setNarrowBeam(10).setKl(0.001).setKq(0.00004));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesSpotSharp", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage(); //
		camera2.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by all three light sources
	 */
	@Test
	public void sphereCombined() {

		Point spPL1 = new Point(50,0 , 0);
		Point spPL2 = new Point(-50, 50, 15);
		scene1.geometries.add(sphere);
		scene1.lights.add(new DirectionalLight(new Color(255,0,0), new Vector(0.5, 0, -1)));
		scene1.lights.add(new PointLight(new Color(192,192,192), spPL1).setKl(0.000001).setKq(0.0001));
		scene1.lights.add(new SpotLight(new Color(205,127,50), spPL2, new Vector(1, 1, -0.5)).setKl(0.000001).setKq(0.000001));

		ImageWriter imageWriter = new ImageWriter("lightSphereCombined", 500, 500);
		camera1.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene1)) //
				.renderImage(); //
		camera1.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by all three light sources
	 */
	@Test
	public void trianglesCombined() {

		Point trPL1 = new Point(50, -80, -80);
		Point trPL2 = new Point(20, -20, 15);
		scene2.geometries.add(triangle1, triangle2);
		scene2.lights.add(new DirectionalLight(trCL, trDL));
		scene2.lights.add(new PointLight(trCL, trPL1).setKl(0.001).setKq(0.0002));
		scene2.lights.add(new SpotLight(trCL, trPL2, trDL).setKl(0.001).setKq(0.0001));

		ImageWriter imageWriter = new ImageWriter("lightTrianglesCombined", 500, 500);
		camera2.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene2)) //
				.renderImage(); //
		camera2.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by all three light sources
	 */
	@Test
	public void TubeTest() {

		Point trPL1 = new Point(50, -80, -80);
		Point trPL2 = new Point(20, -20, 15);
		scene3.geometries.add(tube1);
		scene3.lights.add(new DirectionalLight(trCL, trDL));
		scene3.lights.add(new PointLight(trCL, trPL1).setKl(0.001).setKq(0.0002));
		scene3.lights.add(new SpotLight(trCL, trPL2, trDL).setKl(0.001).setKq(0.0001));

		ImageWriter imageWriter = new ImageWriter("TubeTest", 500, 500);
		camera3.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene3)) //
				.renderImage(); //
		camera3.writeToImage(); //
	}

	/**
	 * Produce a picture of a sphere lighted by all three light sources
	 */
	@Test
	public void CylinderTest5() {
		Scene scene4 = new Scene("CylinderScene");
		scene4.ambientLight = new AmbientLight(new Color(120,120,120),Double3.ONE);

		Cylinder cylinder = new Cylinder(new Ray(new Point (0,0,0), new Vector(0,1,-0.5)), 50,100);
		Material material = new Material();
		material.setKd(0.5).setKs(0.5).setnShininess(300);
		cylinder.setMaterial(material);
		cylinder.setEmission(new Color(255,0,0));
		Point trPL1 = new Point(50, -80, -80);
		Point trPL2 = new Point(20, -20, 15);
		scene4.geometries.add(cylinder);
		//scene4.lights.add(new DirectionalLight(trCL, new Vector(0,0,-1)));
		scene4.lights.add(new PointLight(trCL,new Point(0,0,75)).setKl(0.01).setKq(0.002));
	//	scene4.lights.add(new SpotLight(trCL, new Point(0,100,50), new Vector(0,-1,-1)).setKl(0.001).setKq(0.0001));

		scene4.setResolution(1);
		ImageWriter imageWriter = new ImageWriter("CylinderTest5", 500, 500);
		camera3.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene4)) //
				.renderImage(); //
		camera3.writeToImage(); //
	}
}
