/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bodies;

import Tools.Edge;
import Tools.Matrix;
import Tools.Triple;
import Tools.Vector;
import java.util.ArrayList;
import world.World;

public abstract class RigidBody {

    public Vector x;
    public Vector v;
    public Matrix R;

    public double Theta;
    public double Omega;
    public double Alpha;

    public double mass;
    public double inertia;

    public Vector force;
    public Vector forceGravity;
    public Vector forceWind;
    public double torque;

    public Vector[] VerticesInit;
    public Vector[] CurrentVertices;

    public Edge[] EdgesInit;
    public Edge[] CurrentEdges;

    public Edge[] debug;

    public Triple Color;

    public RigidBody(Vector x, Vector v, double Theta, double Omega, double mass, double inertia) {

        this.x = x;
        this.v = v;
        this.Theta = Theta;
        this.Omega = Omega;
        this.mass = mass;
        this.inertia = inertia;
        this.force = new Vector();
        this.forceGravity = new Vector();
        this.forceWind = new Vector();

    }

    public void TransferDataFromBodyToState(double[] state) {

        state[0] = x.x;
        state[1] = x.y;
        state[2] = v.x;
        state[3] = v.y;
        state[4] = Theta;
        state[5] = Omega;

    }

    public void TransferDataFromStateToBody(double[] state) {

        x.x = state[0];
        x.y = state[1];
        v.x = state[2];
        v.y = state[3];
        Theta = state[4];
        Omega = state[5];

        UpdateVerticesLocation();
        UpdateEdgesLocation();

    }

    public void UpdateForceAndTorque(double t) {

        World.updateForces(t);

        this.ApplyGravityEffect();
        this.ApplyWindEffect();

        this.force = Vector.sumV2d(this.forceGravity, this.forceWind);

    }

    public void ComputeCurrentDerivations(double t, double[] dS) {
        this.UpdateForceAndTorque(t);

        dS[0] = v.x;
        dS[1] = v.y;
        dS[2] = force.x / mass;
        dS[3] = force.y / mass;
        dS[4] = Omega;
        dS[5] = torque / inertia;
    }

    public Vector[] getCurrentVertices() {
        return CurrentVertices;
    }

    public abstract void UpdateVerticesLocation();

    public abstract void UpdateEdgesLocation();

    public Edge[] getCurrentEdges() {
        return null;
    }

    @Override
    public String toString() {

        return "location " + "( " + x.x + " ," + x.y + " )"
                + " velocity " + "( " + v.x + " ," + v.y + " )"
                + " angle " + Theta
                + " angular velocity " + Omega;

    }

    private void ApplyGravityEffect() {
        this.forceGravity.x = this.mass * World.gravity.x;
        this.forceGravity.y = this.mass * World.gravity.y;
    }
    // Wind Effect algorithm
    private void ApplyWindEffect() {
        ArrayList<Edge> EdgesFacesWind = new ArrayList<>();
        ArrayList<Vector> PointsFacesWind = new ArrayList<>();
        for (Edge edge : CurrentEdges) {
            Vector v = Vector.EdgeToVector(edge);
            Vector WindNormal = World.windForce.getNormal();
            if (WindNormal.Dot(v) < 0) {
                EdgesFacesWind.add(edge);
            }
        }
        double i;
        Vector Point_i;
        for (Edge edge : EdgesFacesWind) {
            Vector U = Vector.EdgeToVector(edge).ToUnitVector();
            i = 0.01;
            do {
                Point_i = Vector.sumV2d(edge.s, Vector.multConstantVector(i++ * 10, U));
                PointsFacesWind.add(Point_i);
            } while (Vector.MakeVector(Point_i, edge.s).Dot(Vector.MakeVector(Point_i, edge.e)) < 0);
        }

        this.forceWind.x = 0;
        this.forceWind.y = 0;
        this.torque = 0;

        for (Vector P : PointsFacesWind) {
            this.forceWind.x += World.windForce.x;
            this.forceWind.y += World.windForce.y;
            Vector ra = Vector.MakeVector(this.x, P);
            this.torque += Vector.MakeCrossProduct(ra, World.windForce);
        }

    }

}
