package world;

import bodies.StaticBody;
import java.awt.event.MouseListener;
import Tools.Edge;
import Tools.Vector;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;

public abstract class World implements MouseListener,MouseMotionListener,KeyListener{

    public static final int WIDTH = 5;
    public static final int HIEGHT = 5;

    public static Vector gravity;

    public static Vector windForce;
    public Vector[] WorldVertices;
    public Edge[] WorldEdges;
    public Vector[] WorldNormals;
    public StaticBody[] walls;

    private static World CurrentWorld = null;
    private static final StairWorld stairworld = (StairWorld) new StairWorld().Build();
    private static final FlatWorld flatworld = (FlatWorld) new FlatWorld().Build();

    static {

        // set Forces to default values
        gravity = new Vector();
        windForce = new Vector();

        gravity.y = -5.0;
        gravity.x = 0.0;

        windForce.x = 0.0;
        windForce.y = 0.0;

    }

    public static void updateForces(double t) {
        // our forces initially constant at any time t ..
    }

    public static void setWorldTo(String worldtype) {

        if (worldtype.equals("Stair world")) {
            CurrentWorld = stairworld;
        } else {
            CurrentWorld = flatworld;
        }

    }
    
    public static World getWorld(){
    return CurrentWorld;
    }
    
    public abstract World Build();

}
