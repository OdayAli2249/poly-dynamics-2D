package graphics;

import bodies.RigidBody;
import bodies.StaticBody;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import Tools.Vector;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import physicsengine2d.PhysicsEngine2D;
import world.World;

public class SceneDisplayer implements GLEventListener {

    private final RigidBody[] bodies;

    private final boolean DEBUG = false;
    protected GLWindow window;

    public SceneDisplayer(World world, RigidBody[] bodies) {
        this.bodies = bodies;
        //this.world = world;
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        try {
            Thread.sleep(40);
        } catch (InterruptedException ex) {
            Logger.getLogger(SceneDisplayer.class.getName()).log(Level.SEVERE, null, ex);
        }

        gl.glLoadIdentity();

        PhysicsEngine2D.Update();
        DrawWorld(gl);
        DrawBodyes(gl);

        gl.glFlush();

    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        //method body 
    }

    @Override
    public void init(GLAutoDrawable arg0) {
        // method body 
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }

    public GLCanvas InitializeSreen() {

        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        final GLCanvas glcanvas = new GLCanvas(capabilities);
        glcanvas.addGLEventListener((GLEventListener) this);
        glcanvas.setSize(720, 720);
        glcanvas.addMouseListener(World.getWorld());
        glcanvas.addKeyListener(World.getWorld());
        glcanvas.addMouseMotionListener(World.getWorld());
        final JFrame frame = new JFrame("Rotating Triangle");

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });
        frame.getContentPane().add(glcanvas);
        frame.addKeyListener(World.getWorld());
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);

        return glcanvas;

    }

    public void DrawWorld(GL2 gl) {
        gl.glColor3d(0, 1, 0);
        gl.glColor3d(0.070588, 0.019607, 0.23137255);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3d(10000, 10000, 0);
        gl.glVertex3d(10000, -10000, 0);
        gl.glVertex3d(-10000, -10000, 0);
        gl.glVertex3d(-10000, 10000, 0);
        gl.glEnd();

        for (StaticBody body : World.getWorld().walls) {
            gl.glColor3d(body.Color.x, body.Color.y, body.Color.z);
            gl.glBegin(GL2.GL_POLYGON);
            for (Vector vertex : body.CurrentVertices) {
                gl.glVertex3d(vertex.x / 500 - 0.95, vertex.y / 500 - 0.95, 0);
            }
            gl.glEnd();
        }

    }

    public void DrawBodyes(GL2 gl) {

        for (RigidBody body : bodies) {
            body.UpdateVerticesLocation();
            body.UpdateEdgesLocation();

        }
        gl.glColor3d(0.1, 0.3, 1.0);
        for (RigidBody body : bodies) {
            gl.glColor3d(body.Color.x, body.Color.y, body.Color.z);
            gl.glBegin(GL2.GL_POLYGON);
            for (Vector vertex : body.CurrentVertices) {
                gl.glVertex3d(vertex.x / 500 - 0.95, vertex.y / 500 - 0.95, 0);
            }
            gl.glEnd();
        }

    }

}
