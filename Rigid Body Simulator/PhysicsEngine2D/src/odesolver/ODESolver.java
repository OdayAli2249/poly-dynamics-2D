
package odesolver;

import bodies.RigidBody;

public class ODESolver {

    public static void UpdateState(double t1, double t2, int statelength, RigidBody[] rigidbody, boolean Stop) {

        if (Stop == false) {
            double[] CurrentState = new double[statelength];
            double[] CurrentDirevation = new double[statelength];
            double[] NextState = new double[statelength];

            for (int i = 0; i < rigidbody.length; i++) {
                
                
                
                rigidbody[i].TransferDataFromBodyToState(CurrentState);

                rigidbody[i].ComputeCurrentDerivations(t1, CurrentDirevation);
                double DeltaT = t2 - t1;

                // Euler's Method
                for (int j = 0; j < statelength; j++) {

                    NextState[j] = CurrentState[j] + DeltaT * CurrentDirevation[j];

                }

                rigidbody[i].TransferDataFromStateToBody(NextState);
            }
        }
    }

   

}
