package Tools;

public class Matrix {

   

    public double[][] Elem;

    public Matrix() {

        Elem = new double[3][3];

        Elem[0][0] = 1;
        Elem[0][1] = 0;
        Elem[0][2] = 0;
        Elem[1][0] = 0;
        Elem[1][1] = 1;
        Elem[1][2] = 0;
        Elem[2][0] = 0;
        Elem[2][1] = 0;
        Elem[2][2] = 1;

    }

    public static Matrix ComputeCurrentRotation(double Theta) {
        Triple Pivot = new Triple(0,0,1);
        Matrix R = new Matrix();
        //Theta = Math.toRadians(Theta);
        double k = 1 - Math.cos(Theta);

        R.Elem[0][0] = Pivot.x * Pivot.x * k + Math.cos(Theta);
        R.Elem[0][1] = Pivot.x * Pivot.y * k - Pivot.z * Math.sin(Theta);
        R.Elem[0][2] = Pivot.x * Pivot.z * k + Pivot.y * Math.sin(Theta);
        R.Elem[1][0] = Pivot.x * Pivot.y * k + Pivot.z * Math.sin(Theta);
        R.Elem[1][1] = Pivot.y * Pivot.y * k + Math.cos(Theta);
        R.Elem[1][2] = Pivot.y * Pivot.z * k - Pivot.x * Math.sin(Theta);
        R.Elem[2][0] = Pivot.x * Pivot.z * k - Pivot.y * Math.sin(Theta);
        R.Elem[2][1] = Pivot.y * Pivot.z * k + Pivot.x * Math.sin(Theta);
        R.Elem[2][2] = Pivot.z * Pivot.z * k + Math.cos(Theta);

        return R;

    }

    public static Matrix getMultMatrix(Matrix M1, Matrix M2) {

        /* we will de this function later .. */
        Matrix R = new Matrix();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                R.Elem[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    R.Elem[i][j] = R.Elem[i][j] + M1.Elem[i][k] * M2.Elem[k][j];
                }
            }
        }

        return R;

    }

    public static Matrix Rotate2D(Matrix m, double degree, double Theta) {

        Matrix R = getMultMatrix(m, ComputeCurrentRotation(degree * Theta));

        return R;

    }
    
    public static Triple getMultMatrixTriple(Matrix M, Triple T) {

        /* we will de this function later .. */
        Triple R = new Triple();
        double[] tr = new double[3];
        tr[0] = T.x;
        tr[1] = T.y;
        tr[2] = T.z;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 0) {
                    R.x += tr[j] * M.Elem[i][j];
                }
                if (i == 1) {
                    R.y += tr[j] * M.Elem[i][j];
                }
                if (i == 2) {
                    R.z += tr[j] * M.Elem[i][j];
                }
            }

        }

        return R;

    }


}
