package bodies;


import Tools.Edge;
import Tools.Matrix;
import Tools.Triple;
import Tools.Vector;


public class Rock3 extends DynamicBody {

    public double Dimension;
    public final int VerticesNumber;
    public final int EdgesNumber;

    public Rock3(Vector x, Vector v, double Theta, double w, double mass, double Dimension, double inertia) {
        super(x, v, Theta, w, mass, inertia);
        this.Dimension = Dimension;
        VerticesInit = new Vector[6];
        CurrentVertices = new Vector[6];
        EdgesInit = new Edge[6];
        CurrentEdges = new Edge[6];
        VerticesNumber = 6;
        EdgesNumber = 6;

        // Initialize Vertices
        VerticesInit[0] = new Vector();

        VerticesInit[0].x = Math.cos(Math.toRadians(15)) * this.Dimension * 2 * 0.3;
        VerticesInit[0].y = Math.sin(Math.toRadians(15)) * this.Dimension * 2 * 0.3;
        
        VerticesInit[1] = new Vector();

        VerticesInit[1].x = Math.cos(Math.toRadians(80)) * this.Dimension * 2 * 0.3;
        VerticesInit[1].y = Math.sin(Math.toRadians(80)) * this.Dimension * 2 * 0.3;
        
        VerticesInit[2] = new Vector();

        VerticesInit[2].x = Math.cos(Math.toRadians(165)) * this.Dimension * 4 * 0.3;
        VerticesInit[2].y = Math.sin(Math.toRadians(165)) * this.Dimension * 4 * 0.3;
        
        VerticesInit[3] = new Vector();

        VerticesInit[3].x = Math.cos(Math.toRadians(195)) * this.Dimension * 4 * 0.3;
        VerticesInit[3].y = Math.sin(Math.toRadians(195)) * this.Dimension * 4 * 0.3;
        
        VerticesInit[4] = new Vector();

        VerticesInit[4].x = Math.cos(Math.toRadians(-80)) * this.Dimension * 2 * 0.3;
        VerticesInit[4].y = Math.sin(Math.toRadians(-80)) * this.Dimension * 2 * 0.3;
        
        VerticesInit[5] = new Vector();

        VerticesInit[5].x = Math.cos(Math.toRadians(-15)) * this.Dimension * 2 * 0.3;
        VerticesInit[5].y = Math.sin(Math.toRadians(-15)) * this.Dimension * 2 * 0.3;
       

        // Initialize Edges
        EdgesInit[0] = new Edge(VerticesInit[0], VerticesInit[1]);
        EdgesInit[1] = new Edge(VerticesInit[1], VerticesInit[2]);
        EdgesInit[2] = new Edge(VerticesInit[2], VerticesInit[3]);
        EdgesInit[3] = new Edge(VerticesInit[3], VerticesInit[4]);
        EdgesInit[4] = new Edge(VerticesInit[4], VerticesInit[5]);
        EdgesInit[5] = new Edge(VerticesInit[5], VerticesInit[0]);

        CurrentEdges[0] = EdgesInit[0];
        CurrentEdges[1] = EdgesInit[1];
        CurrentEdges[2] = EdgesInit[2];
        CurrentEdges[3] = EdgesInit[3];
        CurrentEdges[4] = EdgesInit[4];
        CurrentEdges[5] = EdgesInit[5];

        debug = new Edge[9];
        
        Color = new Triple(0.2, 0.2, 0.7);

    }

    @Override
    public void UpdateVerticesLocation() {

        Triple Vertices[] = new Triple[6];

        for (int i = 0; i < VerticesInit.length; i++) {

            Vertices[i] = Triple.VectoToTriple(VerticesInit[i]);

        }

        Triple xt = Triple.VectoToTriple(x);
        this.R = Matrix.ComputeCurrentRotation(Theta);  // make sure the rotation direction caused by matrix accossiate with with graphics (pivot direction basiclly) 

        Triple[] TransferedTriples = new Triple[6];

        TransferedTriples[0] = Triple.Sum(Matrix.getMultMatrixTriple(R, Vertices[0]), xt);
        TransferedTriples[1] = Triple.Sum(Matrix.getMultMatrixTriple(R, Vertices[1]), xt);
        TransferedTriples[2] = Triple.Sum(Matrix.getMultMatrixTriple(R, Vertices[2]), xt);
        TransferedTriples[3] = Triple.Sum(Matrix.getMultMatrixTriple(R, Vertices[3]), xt);
        TransferedTriples[4] = Triple.Sum(Matrix.getMultMatrixTriple(R, Vertices[4]), xt);
        TransferedTriples[5] = Triple.Sum(Matrix.getMultMatrixTriple(R, Vertices[5]), xt);

        for (int i = 0; i < TransferedTriples.length; i++) {

            CurrentVertices[i] = Triple.TripleToVector(TransferedTriples[i]);
            //System.out.println("Vertex" + "[" + i + "] " + CurrentVertices[i]);

        }

    }

    @Override
    public void UpdateEdgesLocation() {

        CurrentEdges[0].setEdge(CurrentVertices[0], CurrentVertices[1]);
        CurrentEdges[1].setEdge(CurrentVertices[1], CurrentVertices[2]);
        CurrentEdges[2].setEdge(CurrentVertices[2], CurrentVertices[3]);
        CurrentEdges[3].setEdge(CurrentVertices[3], CurrentVertices[4]);
        CurrentEdges[4].setEdge(CurrentVertices[4], CurrentVertices[5]);
        CurrentEdges[5].setEdge(CurrentVertices[5], CurrentVertices[0]);

        CurrentEdges[0].ComputeNormal();
        CurrentEdges[1].ComputeNormal();
        CurrentEdges[2].ComputeNormal();
        CurrentEdges[3].ComputeNormal();
        CurrentEdges[4].ComputeNormal();
        CurrentEdges[5].ComputeNormal();

      

    }

}
