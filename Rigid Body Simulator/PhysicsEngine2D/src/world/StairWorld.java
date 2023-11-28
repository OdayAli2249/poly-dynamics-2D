package world;

import bodies.Paddle;
import bodies.StaticBody;
import bodies.Wall;
import Tools.Edge;
import Tools.Matrix;
import Tools.Triple;
import Tools.Vector;
import java.awt.event.KeyEvent;

public class StairWorld extends World {

    private Vector BoardLocation;
    private Matrix Rotation;
    private final double Scaleh = 50 * 1.9;
    private final double Scalew = 50 * 1.92;
    public static Triple Color = new Triple(0.2, 0.8, 0.1);

    public StairWorld() {

    }

    public void UpdateDynamicData(boolean direction) {

        if (direction) {

            Rotation = Matrix.Rotate2D(Rotation, 0.5, 2);

        } else {

            Rotation = Matrix.Rotate2D(Rotation, 0.5, -2);

        }

        Triple Vertices[] = new Triple[4];

        for (int i = 5; i < 9; i++) {

            Vertices[i] = Triple.VectoToTriple(WorldVertices[i]);

        }

        Triple xt = Triple.VectoToTriple(BoardLocation);

        Triple[] TransferedTriples = new Triple[4];

        TransferedTriples[0] = Triple.Sum(Matrix.getMultMatrixTriple(Rotation, Vertices[0]), xt);
        TransferedTriples[1] = Triple.Sum(Matrix.getMultMatrixTriple(Rotation, Vertices[1]), xt);
        TransferedTriples[2] = Triple.Sum(Matrix.getMultMatrixTriple(Rotation, Vertices[2]), xt);
        TransferedTriples[3] = Triple.Sum(Matrix.getMultMatrixTriple(Rotation, Vertices[3]), xt);

        for (int i = 0; i < TransferedTriples.length; i++) {

            WorldVertices[i + 5] = Triple.TripleToVector(TransferedTriples[i]);

        }

        this.WorldEdges[14] = new Edge(this.WorldVertices[5], this.WorldVertices[6]);
        this.WorldEdges[15] = new Edge(this.WorldVertices[6], this.WorldVertices[7]);
        this.WorldEdges[16] = new Edge(this.WorldVertices[7], this.WorldVertices[8]);
        this.WorldEdges[17] = new Edge(this.WorldVertices[8], this.WorldVertices[5]);

    }

    @Override
    public World Build() {

        this.Rotation = new Matrix();
        this.BoardLocation = new Vector((275 / 400) * WIDTH, HIEGHT / 2);

        this.WorldEdges = new Edge[17];
        this.WorldVertices = new Vector[9];
        this.walls = new StaticBody[11];

        this.WorldVertices[0] = new Vector(1 * Scalew, 8.33333333 * Scaleh);
        this.WorldVertices[1] = new Vector(2 * Scalew, 6.66666666 * Scaleh);
        this.WorldVertices[2] = new Vector(3 * Scalew, 5 * Scaleh);
        this.WorldVertices[3] = new Vector(4 * Scalew, 3.33333333 * Scaleh);
        this.WorldVertices[4] = new Vector(5 * Scalew, 1.66666666 * Scaleh);

        this.WorldVertices[5] = new Vector(0.5 * WIDTH / 60, 0.5 * WIDTH / 15);
        this.WorldVertices[6] = new Vector(-0.5 * WIDTH / 60, 0.5 * WIDTH / 15);
        this.WorldVertices[7] = new Vector(-0.5 * WIDTH / 60, -0.5 * WIDTH / 15);
        this.WorldVertices[8] = new Vector(0.5 * WIDTH / 60, -0.5 * WIDTH / 15);

        this.WorldEdges[0] = new Edge(new Vector(0 * Scalew, 12 * Scaleh), new Vector(0 * Scalew, 8.3333333333 * Scaleh)).Inverse();
        this.WorldEdges[1] = new Edge(new Vector(0 * Scalew, 8.3333333333 * Scaleh), new Vector(1 * Scalew, 8.3333333333 * Scaleh)).Inverse();
        this.WorldEdges[2] = new Edge(new Vector(1 * Scalew, 8.3333333333 * Scaleh), new Vector(1 * Scalew, 6.6666666666 * Scaleh)).Inverse();
        this.WorldEdges[3] = new Edge(new Vector(1 * Scalew, 6.6666666666 * Scaleh), new Vector(2 * Scalew, 6.6666666666 * Scaleh)).Inverse();
        this.WorldEdges[4] = new Edge(new Vector(2 * Scalew, 6.6666666666 * Scaleh), new Vector(2 * Scalew, 5 * Scaleh)).Inverse();
        this.WorldEdges[5] = new Edge(new Vector(2 * Scalew, 5 * Scaleh), new Vector(3 * Scalew, 5 * Scaleh)).Inverse();
        this.WorldEdges[6] = new Edge(new Vector(3 * Scalew, 5 * Scaleh), new Vector(3 * Scalew, 3.3333333333 * Scaleh)).Inverse();
        this.WorldEdges[7] = new Edge(new Vector(3 * Scalew, 3.33333333333 * Scaleh), new Vector(4 * Scalew, 3.33333333333 * Scaleh)).Inverse();
        this.WorldEdges[8] = new Edge(new Vector(4 * Scalew, 3.33333333333 * Scaleh), new Vector(4 * Scalew, 0 * Scaleh)).Inverse();
        this.WorldEdges[9] = new Edge(new Vector(4 * Scalew, 0), new Vector(7.5 * Scalew, 0)).Inverse();
        this.WorldEdges[10] = new Edge(new Vector(7.5 * Scalew, 0), new Vector(10 * Scalew, 2.5 * Scaleh)).Inverse();
        this.WorldEdges[11] = new Edge(new Vector(10 * Scalew, 2.5 * Scaleh), new Vector(10 * Scalew, 12 * Scaleh)).Inverse();

        
        this.WorldEdges[12] = new Edge(new Vector(10 * Scalew, 12 * Scaleh), new Vector(12 * Scalew, 12 * Scaleh)).Inverse();
        this.WorldEdges[13] = new Edge(new Vector(12 * Scalew, 12 * Scaleh), new Vector(12 * Scalew, -5 * Scaleh)).Inverse();
        this.WorldEdges[14] = new Edge(new Vector(12 * Scalew, -5 * Scaleh),new Vector(-5 * Scalew, -5 * Scaleh)).Inverse();
        this.WorldEdges[15] = new Edge(new Vector(-5 * Scalew, -5 * Scaleh), new Vector(-5 * Scalew, 12 * Scaleh)).Inverse();
        this.WorldEdges[16] = new Edge(new Vector(-5 * Scalew, 12 * Scaleh), new Vector(0 * Scalew, 12 * Scaleh)).Inverse();

        this.walls[0] = new Wall(Vector.multConstantVector(0.66, 0.67, new Vector(this.WorldEdges[1].getMid().x - 200, this.WorldEdges[2].getMid().y + 42)), Scalew * 3.5, Scalew * 1.64);
        this.walls[1] = new Wall(Vector.multConstantVector(0.66, 0.67, new Vector(this.WorldEdges[3].getMid().x - 200, this.WorldEdges[4].getMid().y + 42)), Scalew * 3.5, Scalew * 1.7);
        this.walls[2] = new Wall(Vector.multConstantVector(0.66, 0.67, new Vector(this.WorldEdges[5].getMid().x - 200, this.WorldEdges[6].getMid().y + 42)), Scalew * 3.5, Scalew * 1.7);
        this.walls[3] = new Wall(Vector.multConstantVector(0.66, 0.67, new Vector(this.WorldEdges[7].getMid().x - 347, this.WorldEdges[8].getMid().y + -100)), Scalew * 5.5, Scalew * 5.8);

        this.walls[4] = new Wall(new Vector(-120, 912), Scalew * 2.5, Scalew * 2.5);
        this.walls[5] = new Wall(new Vector(1150, 912), Scalew * 3.92, Scalew * 3.92);
        this.walls[6] = new Wall(new Vector(950, 695), Scalew * 0.8, Scalew * 0.6);
        this.walls[7] = new Wall(new Vector(1176, 450), Scalew * 4.5, Scalew * 4.5);
        this.walls[8] = new Wall(new Vector(945, 0), Scalew * 3.9, Scalew * 3.3, Math.PI * 0.25);
        this.walls[8].UpdateVerticesLocation();
        this.walls[8].UpdateEdgesLocation();
        this.walls[9] = new Wall(new Vector(550, -110), Scalew * 3.5, Scalew * 2.3);
        this.walls[10] = (Paddle) new Paddle(new Vector(600, 500), 200, 20).Update();

        return this;

    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        Paddle p = (Paddle) this.walls[10];
        if (e.getKeyChar() == 'c') {
            p.rotate(0.2);
        } else if (e.getKeyChar() == 'v') {
            p.rotate(-0.2);
        }
         
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Paddle p = (Paddle) this.walls[10];
        p.stop();
    }

}
