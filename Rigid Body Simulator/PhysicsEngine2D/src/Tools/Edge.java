package Tools;


public class Edge {

    public boolean isContact(Vector CurrentVertices, double THRESHOLD) {

        return this.Check(CurrentVertices, THRESHOLD);

    }

    public Vector s;
    public Vector e;
    public Vector normal;

    public Edge() {

    }

    public Edge(Vector s, Vector e) {

        this.s = s;
        this.e = e;
        this.normal = new Vector();

    }

    public void setEdge(Vector s, Vector e) {

        this.s = s;
        this.e = e;

    }

    public boolean Check(Vector v, double THRESHOLD) {

      
      return true;

    }
    
    public Edge Inverse(){
    
        return new Edge(this.e, this.s);
    
    }

   public Vector getMid(){
   return new Vector(s.x + 0.5 * e.x, s.y + 0.5 * e.y);   
   }

    public Vector ComputeNormal() {
        this.normal = Vector.MakeVector(e, s).getNormal().ToUnitVector();
        return normal;

    }
    
    @Override
    public String toString(){
    
        return this.s + ", " + this.e;
    
    }

}
