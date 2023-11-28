package Tools;

public class Triple {

    public double x;
    public double y;
    public double z;

    public Triple(double x, double y, double z) {

        this.x = x;
        this.y = y;
        this.z = z;

    }

    public Triple() {

        this.x = 0;
        this.y = 0;
        this.z = 0;

    }

    public static Triple Sum(Triple t1, Triple t2) {

        return new Triple(t1.x + t2.x, t1.y + t2.y, t1.z + t2.z);

    }

    public static Triple Sub(Triple t1, Triple t2) {

        return new Triple(t1.x - t2.x, t1.y - t2.y, t1.z - t2.z);

    }
    
    public static Triple VectoToTriple(Vector v){
    
        return new Triple(v.x, v.y, 0);
    
    }
    
    public static Vector TripleToVector(Triple t){
    
        return new Vector(t.x, t.y);
    
    }
    
    
    public static Triple VectoToTriple(double v){
    
        return new Triple(0, 0, v);
    
    }
    
    public static Triple MakeCrossProduct(Triple T1, Triple T2) {

        Triple R = new Triple();

        R.x = T1.y * T2.z - T2.y * T1.z;
        R.y = T1.z * T2.x - T2.z * T1.x;
        R.z = T1.x * T2.y - T2.x * T1.y;

        return R;

    }
    
    

}
