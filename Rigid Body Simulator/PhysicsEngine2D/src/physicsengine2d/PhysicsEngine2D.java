package physicsengine2d;

import UserInterfaces.LinearValuesUI;
import bodies.Cube;
import bodies.RigidBody;
import bodies.Rock1;
import bodies.Rock2;
import bodies.Rock3;
import collisiondetection.Collision;
import com.jogamp.opengl.util.FPSAnimator;
import Tools.Vector;
import odesolver.ODESolver;
import graphics.SceneDisplayer;
import world.StairWorld;
import world.World;

public class PhysicsEngine2D {

    public static FPSAnimator animator;
    public static World stairworld;
    public static RigidBody[] bodies;
    public static double epselon = 0.5;

  
    public static Vector x1;
    public static Vector v1;
    public static double ma1;
    public static double w1;
    public static double m1;
    public static double d1;
    public static double in1;


    public static Vector x2;
    public static Vector v2;
    public static double ma2;
    public static double w2;
    public static double m2;
    public static double d2;
    public static double in2;

    
    public static Vector x3;
    public static Vector v3;
    public static double ma3;
    public static double w3;
    public static double m3;
    public static double d3;
    public static double in3;


    public static Vector x4, x5, x6;
    public static Vector v4;
    public static double ma4;
    public static double w4;
    public static double m4;
    public static double d4;
    public static double in4;

    public static double t;

    static {

        t = 0;
        bodies = new RigidBody[4];

        x1 = new Vector(600.0, 300.0);
        v1 = new Vector(-10, 25);
        ma1 = Math.toRadians(120.297183463 + 4.2971834635);
        w1 = Math.toRadians(4.2971834635);
        m1 = 20;
        d1 = 100;
        in1 = 75000;

        x2 = new Vector(500, 600);
        v2 = new Vector(-10, 10);
        ma2 = Math.toRadians(30.297183463 + 4.2971834635);
        w2 = Math.toRadians(10.2971834635);
        m2 = 20;
        d2 = 70;
        in2 = 75000;

        x3 = new Vector(700, 1000);
        v3 = new Vector(-10, 4);
        ma3 = Math.toRadians(190.297183463 + 4.2971834635);
        w3 = Math.toRadians(5.2971834635);
        m3 = 20;
        d3 = 50;
        in3 = 35000;

        x4 = new Vector(900, 500);
        v4 = new Vector(-20, 30);
        ma4 = 0;
        w4 = Math.toRadians(2.2971834635);
        m4 = 20;
        d4 = 95;
        in4 = 70000;

        // Default Initialization for body 2
        bodies[0] = new Rock1(x1, v1, ma1, w1, m1, d1, in1);
        bodies[1] = new Rock2(x2, v2, ma2, w2, m2, d2, in2);
        bodies[2] = new Rock3(x3, v3, ma3, w3, m3, d3, in3);
        bodies[3] = new Cube(x4, v4, ma4, w4, m4, d4, in4);

        stairworld = new StairWorld();
        World.setWorldTo("Stair world");

        for (RigidBody body : PhysicsEngine2D.bodies) {
            body.UpdateVerticesLocation();
            body.UpdateEdgesLocation();

        }

    }

    public static void setNewInitialization(Vector x1, Vector v1, double ma1, double w1, double m1, double d1, double in1,
            Vector x2, Vector v2, double ma2, double w2, double m2, double d2, double in2,
            Vector x3, Vector v3, double ma3, double w3, double m3, double d3, double in3,
            Vector x4, Vector v4, double ma4, double w4, double m4, double d4, double in4) {

        bodies[0] = new Rock1(x1, v1, ma1, w1, m1, d1, in1);
        bodies[1] = new Rock2(x2, v2, ma2, w2, m2, d2, in2);
        bodies[2] = new Rock3(x3, v3, ma3, w3, m3, d3, in3);
        bodies[3] = new Cube(x4, v4, ma4, w4, m4, d4, in4);

    }

    public static void Update() {

        ODESolver.UpdateState(t, t + 0.3, 6, bodies, false);
        Collision.FindAllCurrentContacts(bodies, World.getWorld());
        Collision.HundleAllCollion(epselon);

        t += 0.03;

    }

    public static void runAnimator() {

        PhysicsEngine2D.animator = new FPSAnimator(new SceneDisplayer(stairworld, bodies).InitializeSreen(), 900, true);
        PhysicsEngine2D.animator.start();

    }

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LinearValuesUI().setVisible(true);
            }
        });

    }
}
