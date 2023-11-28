package bodies;

import collisiondetection.Collision;
import collisiondetection.Contact;
import Tools.Edge;
import Tools.Matrix;
import Tools.Triple;
import Tools.Vector;
import java.util.ArrayList;


public class Triangle extends DynamicBody {

    public double Dimension;
    public final int VerticesNumber;

    public final int EdgesNumber;

    public Triangle(Vector x, Vector v, double Theta, double w, double mass, double Dimension, double inertia) {
        super(x, v, Theta, w, mass, inertia);
        this.Dimension = Dimension;
        VerticesInit = new Vector[3];
        CurrentVertices = new Vector[3];
        VerticesNumber = 3;

        EdgesInit = new Edge[3];
        CurrentEdges = new Edge[3];

        EdgesNumber = 3;

        // Initialize Vertices
        VerticesInit[0] = new Vector();

        VerticesInit[0].x = Math.cos(Math.toRadians(0)) * 0.5f * this.Dimension;
        VerticesInit[0].y = Math.sin(Math.toRadians(0)) * 0.5f * this.Dimension;

        VerticesInit[1] = new Vector();

        VerticesInit[1].x = Math.cos(Math.toRadians(120)) * 0.5f * this.Dimension;
        VerticesInit[1].y = Math.sin(Math.toRadians(120)) * 0.5f * this.Dimension;

        VerticesInit[2] = new Vector();

        VerticesInit[2].x = Math.cos(Math.toRadians(240)) * 0.5f * this.Dimension;
        VerticesInit[2].y = Math.sin(Math.toRadians(240)) * 0.5f * this.Dimension;

        // Initialize Edges
        EdgesInit[0] = new Edge(VerticesInit[0], VerticesInit[1]);
        EdgesInit[1] = new Edge(VerticesInit[1], VerticesInit[2]);
        EdgesInit[2] = new Edge(VerticesInit[2], VerticesInit[0]);

        CurrentEdges[0] = EdgesInit[0];
        CurrentEdges[1] = EdgesInit[1];
        CurrentEdges[2] = EdgesInit[2];
        
        debug = new Edge[7];
        
        Color = new Triple(0.0, 0.0, 1.0);

    }

    @Override
    public void UpdateVerticesLocation() {

        Triple Vertices[] = new Triple[3];

        for (int i = 0; i < VerticesInit.length; i++) {

            Vertices[i] = Triple.VectoToTriple(VerticesInit[i]);

        }

        Triple xt = Triple.VectoToTriple(x);

        Triple[] TransferedTriples = new Triple[3];
        this.R = Matrix.ComputeCurrentRotation(Theta);
        
        TransferedTriples[0] = Triple.Sum(Matrix.getMultMatrixTriple(R, Vertices[0]), xt);
        TransferedTriples[1] = Triple.Sum(Matrix.getMultMatrixTriple(R, Vertices[1]), xt);
        TransferedTriples[2] = Triple.Sum(Matrix.getMultMatrixTriple(R, Vertices[2]), xt);

        for (int i = 0; i < TransferedTriples.length; i++) {

            CurrentVertices[i] = Triple.TripleToVector(TransferedTriples[i]);
           
        }

    }

    @Override
    public void UpdateEdgesLocation() {
        CurrentEdges[0].setEdge(CurrentVertices[0], CurrentVertices[1]);
        CurrentEdges[1].setEdge(CurrentVertices[1], CurrentVertices[2]);
        CurrentEdges[2].setEdge(CurrentVertices[2], CurrentVertices[0]);
        
        CurrentEdges[0].ComputeNormal();
        CurrentEdges[1].ComputeNormal();
        CurrentEdges[2].ComputeNormal();
        
        debug[0] = new Edge(CurrentEdges[0].s, Vector.sumV2d(CurrentEdges[0].s, Vector.multConstantVector(5, CurrentEdges[0].ComputeNormal())));
        debug[1] = new Edge(CurrentEdges[1].s, Vector.sumV2d(CurrentEdges[1].s, Vector.multConstantVector(5, CurrentEdges[1].ComputeNormal())));
        debug[2] = new Edge(CurrentEdges[2].s, Vector.sumV2d(CurrentEdges[2].s, Vector.multConstantVector(5, CurrentEdges[2].ComputeNormal())));
       
        debug[3] = new Edge(CurrentVertices[0], Vector.sumV2d(CurrentVertices[0], Collision.ComputeAbsuloteVelocity(this, CurrentVertices[0])));
        debug[4] = new Edge(CurrentVertices[1], Vector.sumV2d(CurrentVertices[1], Collision.ComputeAbsuloteVelocity(this, CurrentVertices[1])));
        debug[5] = new Edge(CurrentVertices[2], Vector.sumV2d(CurrentVertices[2], Collision.ComputeAbsuloteVelocity(this, CurrentVertices[2])));
       
        debug[6] = new Edge(x, Vector.sumV2d(x, v));
    }

   
    public void checkCollision(RigidBody body, ArrayList<Contact> contactslist) {

        for (int i = 0; i < CurrentVertices.length; i++) {

            for (int j = 0; j < body.CurrentEdges.length; j++) {

                if (body.CurrentEdges[j].Check(CurrentVertices[i], 0.01)) {

                    Contact contact = Contact.CreatNewContact(this, body, CurrentVertices[i], body.CurrentEdges[j]);
                    if (Contact.isRealCollion(contact, 0.01)) {
                        contactslist.add(contact);
                    }
                }

            }

        }

    }

   

}
