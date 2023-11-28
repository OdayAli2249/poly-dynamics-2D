
package bodies;

import Tools.Vector;


public class DynamicBody extends RigidBody{

    public DynamicBody(Vector x, Vector v, double Theta, double Omega, double mass, double inertia) {
        super(x, v, Theta, Omega, mass, inertia);
    }

    @Override
    public void UpdateVerticesLocation() {
        
    }

    @Override
    public void UpdateEdgesLocation() {
       
    }
    
}
