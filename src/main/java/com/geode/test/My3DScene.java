package com.geode.test;

import com.geode.core.*;
import com.geode.core.controller.Controller;
import com.geode.core.controller.Gamepad;
import com.geode.core.reflections.Inject;
import com.geode.core.reflections.SceneEntry;
import com.geode.entity.Transform;
import com.geode.exceptions.GeodeException;
import com.geode.graphics.camera.Camera3D;
import com.geode.graphics.meshing.Mesh;
import com.geode.graphics.meshing.MeshAttribute;
import com.geode.graphics.renderer.Renderer;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SceneEntry(value = "my_3d_scene", first = false)
public class My3DScene extends Scene {

    public @Inject Application app;
    public @Inject WindowManager windowManager;
    public @Inject SceneManager sceneManager;
    public @Inject MySingleton singleton;
    public @Inject WindowEventsManager eventsManager;
    public @Inject MouseManager mouseManager;
    public @Inject KeyManager keyManager;

    public @Inject MyResources resources;

    public @Inject ControllerManager controllerManager;

    private Renderer<Camera3D> renderer;

    private Transform transform = new Transform();
    private Mesh mesh = new Mesh();

    @Override
    public void init() {


    }

    @Override
    public void select() {
        windowManager.getWindow().setClearColor(new Vector4f(0f, 0, 0, 1));
        singleton.sayHello();



        try {
            resources.classic.init();

            mesh.init();

        } catch (GeodeException e) {
            throw new RuntimeException(e);
        }

        renderer = new Renderer<>();
        renderer.setCamera(new Camera3D(windowManager));
        renderer.setShader(resources.classic);

        renderer.getCamera().getProjectionSettings().setZfar(1000000);
        renderer.getCamera().enableDepthTest();

        mesh.setDynamic(false);
        mesh.asTriangle();
        mesh.setLineWeight(3);

        MeshAttribute posAttribute = new MeshAttribute(3, GL11.GL_FLOAT, Float.BYTES);
        MeshAttribute colorAttribute = new MeshAttribute(4, GL11.GL_FLOAT, Float.BYTES);
        List<MeshAttribute> attributes = new ArrayList<>();
        attributes.add(posAttribute);
        attributes.add(colorAttribute);
        // Vertex data
        mesh.fill(attributes, new float[]{
                // Face arrière (Rouge)
                0, 0, 0,   1, 0, 0, 1,  // Sommet 0 : arrière-bas-gauche, rouge
                0, 1, 0,   1, 0, 0, 1,  // Sommet 1 : arrière-haut-gauche, rouge
                1, 1, 0,   1, 0, 0, 1,  // Sommet 2 : arrière-haut-droit, rouge
                1, 0, 0,   1, 0, 0, 1,  // Sommet 3 : arrière-bas-droit, rouge

                // Face avant (Vert)
                0, 0, 1,   0, 1, 0, 1,  // Sommet 4 : avant-bas-gauche, vert
                0, 1, 1,   0, 1, 0, 1,  // Sommet 5 : avant-haut-gauche, vert
                1, 1, 1,   0, 1, 0, 1,  // Sommet 6 : avant-haut-droit, vert
                1, 0, 1,   0, 1, 0, 1,  // Sommet 7 : avant-bas-droit, vert

                // Face gauche (Bleu)
                0, 0, 0,   0, 0, 1, 1,  // Sommet 0 : arrière-bas-gauche, bleu
                0, 1, 0,   0, 0, 1, 1,  // Sommet 1 : arrière-haut-gauche, bleu
                0, 1, 1,   0, 0, 1, 1,  // Sommet 5 : avant-haut-gauche, bleu
                0, 0, 1,   0, 0, 1, 1,  // Sommet 4 : avant-bas-gauche, bleu

                // Face droite (Jaune)
                1, 0, 0,   1, 1, 0, 1,  // Sommet 3 : arrière-bas-droit, jaune
                1, 1, 0,   1, 1, 0, 1,  // Sommet 2 : arrière-haut-droit, jaune
                1, 1, 1,   1, 1, 0, 1,  // Sommet 6 : avant-haut-droit, jaune
                1, 0, 1,   1, 1, 0, 1,  // Sommet 7 : avant-bas-droit, jaune

                // Face haut (Magenta)
                0, 1, 0,   1, 0, 1, 1,  // Sommet 1 : arrière-haut-gauche, magenta
                1, 1, 0,   1, 0, 1, 1,  // Sommet 2 : arrière-haut-droit, magenta
                1, 1, 1,   1, 0, 1, 1,  // Sommet 6 : avant-haut-droit, magenta
                0, 1, 1,   1, 0, 1, 1,  // Sommet 5 : avant-haut-gauche, magenta

                // Face bas (Cyan)
                0, 0, 0,   0, 1, 1, 1,  // Sommet 0 : arrière-bas-gauche, cyan
                1, 0, 0,   0, 1, 1, 1,  // Sommet 3 : arrière-bas-droit, cyan
                1, 0, 1,   0, 1, 1, 1,  // Sommet 7 : avant-bas-droit, cyan
                0, 0, 1,   0, 1, 1, 1   // Sommet 4 : avant-bas-gauche, cyan
        }, new int[]{
                // Indices corrigés pour les faces
                // Face arrière
                0, 1, 2,
                0, 2, 3,

                // Face avant
                4, 5, 6,
                4, 6, 7,

                // Face gauche
                8, 9, 10,
                8, 10, 11,

                // Face droite
                12, 13, 14,
                12, 14, 15,

                // Face haut
                16, 17, 18,
                16, 18, 19,

                // Face bas
                20, 21, 22,
                20, 22, 23
        });




        transform.setPosition(new Vector3f(100, 100, 10));
        transform.setSize(new Vector3f(10, 10, 10));
        transform.setCenterOrigin();


        mouseManager.lock();





        //renderer.getCamera().focus(new Vector2f(transform.getPosition().x, transform.getPosition().y));


    }

    @Override
    public void update(double dt) {
        renderer.getCamera().update(new Vector2f(mouseManager.getPosition()));
        renderer.getCamera().activateKeys();
        transform.getRotation().add((float) (50*dt), (float) (50*dt), (float) (50*dt));
        //transform.getSize().add((float) (10f*dt), (float) (10.0*dt), (float) (10.0*dt));
        transform.setDirty(true);

        if(controllerManager.hasControllers()) {
            Controller controller = controllerManager.getAvailableController();
            if(controller.isPressedOnce(Gamepad.A))
                windowManager.getWindow().setPosition(new Vector2f(new Random().nextInt()%1000, new Random().nextInt()%800));
        }
    }

    @Override
    public void draw(double dt) {
        renderer.start();
        renderer.render(transform, mesh);
        renderer.stop();
    }

    @Override
    public void close() throws Exception {
        mesh.close();
    }
}
