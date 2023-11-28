
package Tools;

import Tools.Matrix;


public class Quaternion {
    
    public double vx;
    public double vy;
    public double vz;
    public double s;
    
    public Quaternion(){
    
        this.vx = 0;
        this.vy = 0;
        this.vz = 0;
        this.s = 0;
        
    }
    
    public Quaternion(double vx, double vy, double vz, double s){
    
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.s = s;
        
    }
    
    public static Matrix ConvertQuaternionIntoMatrix(Quaternion q){
    
        Matrix R = new Matrix();
        
        R.Elem[0][0] = 1 - 2 * q.vy * q.vy - 2 * q.vz * q.vz;    R.Elem[0][1] = 2 * q.vx * q. vy - 2 * q.s * q.vz;     R.Elem[0][2] = 2 * q.vx * q.vz + 2 * q.s * q.vy;
        R.Elem[1][0] = 2 * q.vx * q.vy + 2 * q.s * q.vz;    R.Elem[1][1] = 1 - 2 * q.vx * q.vx - 2 * q.vz * q.vz;     R.Elem[1][2] = 2 * q.vy * q.vz - 2 * q.s * q.vx;
        R.Elem[2][0] = 2 * q.vx * q.vz - 2 * q.s * q.vy;    R.Elem[2][1] = 2 * q.vy * q.vz + 2 * q.s * q.vx;     R.Elem[2][2] = 1 - 2 * q.vx * q.vx - 2 * q.vy * q.vy;
        
        
        return R;
    
    }
    
    public static Quaternion ConvertMatrixIntoQuaternion(Matrix m){
    
        Quaternion R = new Quaternion();
        double tr,s;
        
        tr = m.Elem[0][0] +  m.Elem[1][1] +  m.Elem[2][2];
        System.out.println(" tr = " + tr);
        if(tr >= 0){
            
            s = Math.sqrt(tr + 1);
            System.out.println(" s = " + s);
            R.s = (float) ((float) 0.5 * s);
            s = 0.5 / s;
            R.vx = (float) ((float) (m.Elem[2][1] - m.Elem[1][2]) * s);
            R.vy = (float) ((float) (m.Elem[0][2] - m.Elem[2][0]) * s);
            R.vz = (float) ((float) (m.Elem[1][0] - m.Elem[0][1]) * s);
            
        }else {
        
        int i = 0;
        if(m.Elem[1][1] > m.Elem[0][0]){
            i = 1;
        }
        if(m.Elem[2][2] > m.Elem[i][i]){
            i = 2;
        }
        
            switch (i) {
                case 0:
                    s = Math.sqrt(m.Elem[0][0] - (m.Elem[1][1] + m.Elem[2][2]) + 1);
                    R.vx = (float) (0.5 * s);
                    s = 0.5 / s;
                    R.vy = (float) ((m.Elem[0][1] + m.Elem[1][0]) * s);
                    R.vz = (float) ((m.Elem[2][0] + m.Elem[0][2]) * s);
                    R.s = (float) ((m.Elem[2][1] - m.Elem[1][2]) * s);
                    break;
                case 1:
                    s = Math.sqrt(m.Elem[1][1] - (m.Elem[2][2] + m.Elem[0][0]) + 1);
                    R.vy = (float) (0.5 * s);
                    s = 0.5 / s;
                    R.vz = (float) ((m.Elem[1][2] + m.Elem[2][1]) * s);
                    R.vx = (float) ((m.Elem[0][1] + m.Elem[1][0]) * s);
                    R.s = (float) ((m.Elem[0][2] - m.Elem[2][0]) * s);
                    break;
                case 2:
                    s = Math.sqrt(m.Elem[2][2] - (m.Elem[0][0] + m.Elem[1][1]) + 1);
                    R.vz = (float) (0.5 * s);
                    s = 0.5 / s;
                    R.vx = (float) ((m.Elem[2][0] + m.Elem[0][2]) * s);
                    R.vy = (float) ((m.Elem[1][2] + m.Elem[2][1]) * s);
                    R.s = (float) ((m.Elem[1][0] - m.Elem[0][1]) * s);
                    break;
                default:
                    
                    break;
            }
        
        }
        
        
        return R;
    
    }
    
    
    
  
    
    
    public static Quaternion ComputeCurrentDirevation(Triple w, Quaternion q){
    
        Quaternion R = new Quaternion();
        Triple qt = new Triple(q.vx, q.vy, q.vz);
        Triple rt = new Triple();
        

        R.vx = (float) (0.5 * rt.x);
        R.vy = (float) (0.5 * rt.y);
        R.vz = (float) (0.5 * rt.z);
        return R;
    
    }
    
    public static Quaternion MultQuaternion(Quaternion q1, Quaternion q2){
    
        Quaternion R = new Quaternion();
        
        
        Triple t1 = new Triple(q1.vx, q1.vy, q1.vz) ;
        Triple t2 = new Triple(q2.vx, q2.vy, q2.vz) ;
        Triple tr = new Triple();
        System.out.println(R.s);
        R.vx = tr.x;
        R.vy = tr.y;
        R.vz = tr.z;
        return R;
    
    }
    
    public static Quaternion MakeMultConstantQuaternion(double C, Quaternion Q){
    
        return new Quaternion(C * Q.vx, C * Q.vy, C * Q.vz, C * Q.s);
    
    }
    
    public static Quaternion getUnitQuaternion(Quaternion q){
        
        float L = (float) Math.sqrt(q.vx * q.vx + q.vy * q.vy + q.vz * q.vz + q.s * q.s);
        return MakeMultConstantQuaternion(1 / L, q);
    
    }
    
    public static Quaternion Sum(Quaternion q1, Quaternion q2){
     
        return new Quaternion(q1.vx + q2.vx, q1.vy + q2.vy, q1.vz + q2.vz, q1.s + q2.s);
        
    }
    
}
