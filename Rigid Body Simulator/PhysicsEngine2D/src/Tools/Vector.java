/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Tools.Edge;

/**
 *
 * @author Emperor
 */
public class Vector {

    public double x;
    public double y;

    public Vector(double x, double y) {

        this.x = x;
        this.y = y;

    }

    public Vector() {

        this.x = 0;
        this.y = 0;

    }

    public Vector getNormal() {

        return new Vector(-this.y, this.x);

    }

    public double Dot(Vector v) {

        return this.x * v.x + this.y * v.y;

    }

    public static Vector inverse(Vector v) {

        return new Vector(-v.x, -v.y);

    }

    public static Vector sumV2d(Vector v1, Vector v2) {

        return new Vector(v1.x + v2.x, v1.y + v2.y);

    }

    public static Vector subV2d(Vector v1, Vector v2) {

        return new Vector(v1.x - v2.x, v1.y - v2.y);

    }

    public Vector ToUnitVector() {

        double vl = Math.sqrt(x * x + y * y);
        return new Vector(x / vl, y / vl);

    }

    public static Vector MakeVector(Vector v1, Vector v2) {

        return new Vector(v2.x - v1.x, v2.y - v1.y);

    }

    public static Vector MakeCrossProduct(double va, Vector vb) {
        Vector R = new Vector();
        R.x = -va * vb.y;
        R.y = va * vb.x;
        return R;
    }

    public static double MakeCrossProduct(Vector va, Vector vb) {
        return va.x * vb.y - va.y * vb.x;
    }

    public static Vector multConstantVector(double Constant, Vector vector) {
        return new Vector(Constant * vector.x, Constant * vector.y);
    }
    
    public static Vector multConstantVector(double Constantx, double Constanty, Vector vector) {
        return new Vector(Constantx * vector.x, Constanty * vector.y);
    }

    @Override
    public String toString() {
        return "(" + this.x + " ," + this.y + ")";
    }
    
    public static Vector EdgeToVector(Edge edge){
        return Vector.MakeVector(edge.s, edge.e);
    }

}
