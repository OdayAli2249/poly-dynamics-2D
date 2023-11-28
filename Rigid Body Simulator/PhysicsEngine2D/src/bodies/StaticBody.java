package bodies;


import Tools.Edge;
import Tools.Matrix;
import Tools.Triple;
import Tools.Vector;

public class StaticBody extends DynamicBody {

    public double Dimension1, Dimension2;
    public final int VerticesNumber;
    public final int EdgesNumber;

    public StaticBody(Vector x, double Dimension1, double Dimension2) {
        super(x, new Vector(0,0), 0, 0, Double.MAX_VALUE, Double.MAX_VALUE);
        this.Dimension1 = Dimension1;
        this.Dimension2 = Dimension2;
        VerticesInit = new Vector[4];
        CurrentVertices = new Vector[4];
        EdgesInit = new Edge[4];
        CurrentEdges = new Edge[4];
        VerticesNumber = 4;
        EdgesNumber = 4;

        // Initialize Vertices
        VerticesInit[0] = new Vector();

        VerticesInit[0].x = 0.5f * this.Dimension1;
        VerticesInit[0].y = 0.5f * this.Dimension2;

        VerticesInit[1] = new Vector();

        VerticesInit[1].x = -0.5f * this.Dimension1;
        VerticesInit[1].y = 0.5f * this.Dimension2;

        VerticesInit[2] = new Vector();

        VerticesInit[2].x = -0.5f * this.Dimension1;
        VerticesInit[2].y = -0.5f * this.Dimension2;

        VerticesInit[3] = new Vector();

        VerticesInit[3].x = 0.5f * this.Dimension1;
        VerticesInit[3].y = -0.5f * this.Dimension2;

        // Initialize Edges
        EdgesInit[0] = new Edge(VerticesInit[0], VerticesInit[1]);
        EdgesInit[1] = new Edge(VerticesInit[1], VerticesInit[2]);
        EdgesInit[2] = new Edge(VerticesInit[2], VerticesInit[3]);
        EdgesInit[3] = new Edge(VerticesInit[3], VerticesInit[0]);

        CurrentEdges[0] = EdgesInit[0];
        CurrentEdges[1] = EdgesInit[1];
        CurrentEdges[2] = EdgesInit[2];
        CurrentEdges[3] = EdgesInit[3];

        debug = new Edge[9];
        UpdateVerticesLocation();

    }

    
     public StaticBody(Vector x, double Dimension1, double Dimension2, double Theta) {
        super(x, new Vector(0,0), Theta, 0, Double.MAX_VALUE, Double.MAX_VALUE);
        this.Dimension1 = Dimension1;
        this.Dimension2 = Dimension2;
        VerticesInit = new Vector[4];
        CurrentVertices = new Vector[4];
        EdgesInit = new Edge[4];
        CurrentEdges = new Edge[4];
        VerticesNumber = 4;
        EdgesNumber = 4;

        // Initialize Vertices
        VerticesInit[0] = new Vector();

        VerticesInit[0].x = 0.5f * this.Dimension1;
        VerticesInit[0].y = 0.5f * this.Dimension2;

        VerticesInit[1] = new Vector();

        VerticesInit[1].x = -0.5f * this.Dimension1;
        VerticesInit[1].y = 0.5f * this.Dimension2;

        VerticesInit[2] = new Vector();

        VerticesInit[2].x = -0.5f * this.Dimension1;
        VerticesInit[2].y = -0.5f * this.Dimension2;

        VerticesInit[3] = new Vector();

        VerticesInit[3].x = 0.5f * this.Dimension1;
        VerticesInit[3].y = -0.5f * this.Dimension2;

        // Initialize Edges
        EdgesInit[0] = new Edge(VerticesInit[0], VerticesInit[1]);
        EdgesInit[1] = new Edge(VerticesInit[1], VerticesInit[2]);
        EdgesInit[2] = new Edge(VerticesInit[2], VerticesInit[3]);
        EdgesInit[3] = new Edge(VerticesInit[3], VerticesInit[0]);

        CurrentEdges[0] = EdgesInit[0];
        CurrentEdges[1] = EdgesInit[1];
        CurrentEdges[2] = EdgesInit[2];
        CurrentEdges[3] = EdgesInit[3];

        debug = new Edge[9];
        UpdateVerticesLocation();

    }

   

    @Override
    public void UpdateVerticesLocation() {

        Triple Vertices[] = new Triple[4];

        for (int i = 0; i < VerticesInit.length; i++) {

            Vertices[i] = Triple.VectoToTriple(VerticesInit[i]);

        }

        Triple xt = Triple.VectoToTriple(x);
        this.R = Matrix.ComputeCurrentRotation(Theta);  

        Triple[] TransferedTriples = new Triple[4];

        TransferedTriples[0] = Triple.Sum(Matrix.getMultMatrixTriple(R, Vertices[0]), xt);
        TransferedTriples[1] = Triple.Sum(Matrix.getMultMatrixTriple(R, Vertices[1]), xt);
        TransferedTriples[2] = Triple.Sum(Matrix.getMultMatrixTriple(R, Vertices[2]), xt);
        TransferedTriples[3] = Triple.Sum(Matrix.getMultMatrixTriple(R, Vertices[3]), xt);

        for (int i = 0; i < TransferedTriples.length; i++) {

            CurrentVertices[i] = Triple.TripleToVector(TransferedTriples[i]);

        }

    }

    @Override
    public void UpdateEdgesLocation() {

        CurrentEdges[0].setEdge(CurrentVertices[0], CurrentVertices[1]);
        CurrentEdges[1].setEdge(CurrentVertices[1], CurrentVertices[2]);
        CurrentEdges[2].setEdge(CurrentVertices[2], CurrentVertices[3]);
        CurrentEdges[3].setEdge(CurrentVertices[3], CurrentVertices[0]);

        CurrentEdges[0].ComputeNormal();
        CurrentEdges[1].ComputeNormal();
        CurrentEdges[2].ComputeNormal();
        CurrentEdges[3].ComputeNormal();


    }

}

