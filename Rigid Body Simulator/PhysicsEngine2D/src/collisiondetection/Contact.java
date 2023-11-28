package collisiondetection;

import bodies.RigidBody;
import Tools.Edge;
import Tools.Triple;
import Tools.Vector;
import world.World;

public class Contact {

    private final RigidBody ra;
    private final RigidBody rb;
    private final Vector p;
    private final Vector normal;
    private final Edge s;
    private final int Type;
    private final World world;

    public Contact(RigidBody ra, RigidBody rb, Vector p, Edge s) {

        this.ra = ra;
        this.rb = rb;
        this.world = null;
        this.p = p;
        this.s = s;
        this.normal = s.ComputeNormal();
        this.Type = 0;

    }

    public Contact(RigidBody ra, World wb, Vector p, Edge s) {

        this.ra = ra;
        this.rb = null;
        this.world = wb;
        this.p = p;
        this.s = s;
        this.normal = s.ComputeNormal();
        this.Type = 1;

    }

    public Contact(World wa, RigidBody rb, Vector p, Edge s) {

        this.ra = null;
        this.rb = rb;
        this.world = wa;
        this.p = p;
        this.s = s;
        this.normal = s.ComputeNormal();
        this.Type = 2;

    }

    public RigidBody getRA() {
        return ra;
    }

    public RigidBody getRB() {
        return rb;
    }

    public Vector getNormal() {
        return normal;
    }

    public Vector getP() {
        return p;
    }

    public Edge getS() {
        return s;
    }

    public int getType() {
        return Type;
    }

    public static Contact CreatNewContact(RigidBody ra, RigidBody rb, Vector p, Edge s) {

        return new Contact(ra, rb, p, s);
    }

    public static Contact CreatNewContact(RigidBody ra, World wb, Vector p, Edge s) {

        return new Contact(ra, wb, p, s);
    }

    public static Contact CreatNewContact(World wa, RigidBody rb, Vector p, Edge s) {

        return new Contact(wa, rb, p, s);
    }

    public static boolean isRealCollion(Contact contact, double THRESHOLD) {

        Vector vac = new Vector();
        Vector vbc = new Vector();

        switch (contact.Type) {
            case 0:
                vac = ComputeAbsuloteVilocity(contact.ra, contact.p);
                vbc = ComputeAbsuloteVilocity(contact.rb, contact.p);
                break;
            case 1:
                vac = ComputeAbsuloteVilocity(contact.ra, contact.p);
                break;
            case 2:
                vbc = ComputeAbsuloteVilocity(contact.rb, contact.p);
                break;
            default:
                break;
        }
        double Vrel = contact.normal.Dot(Vector.subV2d(vac, vbc));

        if (Vrel > THRESHOLD) {
            return false;                       // moving away state
        } else {
            return Vrel <= -THRESHOLD; // resting contact state
        }        // collision state

    }

    public static Vector ComputeAbsuloteVilocity(RigidBody body, Vector p) {
        Triple T1 = Triple.VectoToTriple(Vector.MakeVector(body.x, p));
        Triple T2 = Triple.VectoToTriple(body.Omega);
        Vector vr = Triple.TripleToVector(Triple.MakeCrossProduct(T1, T2));
        return Vector.sumV2d(body.v, vr);

    }

    @Override
    public String toString() {

        return "noraml :" + this.normal
                + " Vertex : "
                + this.p + " Edge : " + this.s + " Type : " + this.Type;

    }

}
