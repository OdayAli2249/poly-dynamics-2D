package collisiondetection;

import bodies.RigidBody;
import Tools.ClosestPointHundler;
import Tools.Edge;
import Tools.Vector;
import java.util.ArrayList;
import world.StairWorld;
import world.World;

public class Collision {

    public static ArrayList<Contact> contactslist;

    static {

        contactslist = new ArrayList<>();

    }

    public static void FindAllCurrentContacts(RigidBody[] bodies, World world) {
        contactslist.clear();
        findAllCollisionsBetweenBodyes(bodies, world);
    }

    public static void findAllCollisionsBetweenBodyes(RigidBody[] bodiesp, World world) {

        RigidBody bodies[] = new RigidBody[bodiesp.length + world.walls.length];

        int n;
        for (n = 0; n < bodiesp.length; n++) {
            bodies[n] = bodiesp[n];
        }
        for (int k = n; k < world.walls.length + n; k++) {

            bodies[k] = world.walls[k - n];
        }

        for (RigidBody body : bodies) {
            body.UpdateVerticesLocation();
            body.UpdateEdgesLocation();
        }

        for (int i = 0; i < bodies.length; i++) {

            for (int j = i; j < bodies.length; j++) {
                if (bodies[i] != bodies[j]) {
                    RigidBody body1 = bodies[i];
                    RigidBody body2 = bodies[j];
                    ClosestPointHundler CPHtemp1 = checkCollisionBetweenTwoBodies(body1, body2);
                    ClosestPointHundler CPHtemp2 = checkCollisionBetweenTwoBodies(body2, body1);
                    if (CPHtemp1 == null || CPHtemp2 == null) {
                        continue;
                    }
                    Vector contactPoint = null;
                    Edge edge = new Edge();
                    double penetration = -Integer.MAX_VALUE;
                    if (CPHtemp1.point != null) {
                        contactPoint = CPHtemp1.point;
                        penetration = CPHtemp1.distance;
                        edge = CPHtemp1.edge;
                    }
                    if (CPHtemp2.point != null && CPHtemp2.distance > penetration) {

                        contactPoint = CPHtemp2.point;
                        edge = CPHtemp2.edge;
                        RigidBody Temp = body1;
                        body1 = body2;
                        body2 = Temp;
                    }

                    Contact contact = Contact.CreatNewContact(body1, body2, contactPoint, edge);
                    if (!contactslist.contains(contact)) {
                        contactslist.add(contact);

                    }
                }
            }

        }

    }

    public static void findAllCollisionsOfBodiesWithWorld(RigidBody[] bodies, World world) {

        for (RigidBody rb : bodies) {
            checkCollisionOfBodyWithWorld(rb, world);
        }

    }

    public static ClosestPointHundler checkCollisionBetweenTwoBodies(RigidBody A, RigidBody B) {

        boolean penetration;
        ClosestPointHundler CPHresult = new ClosestPointHundler();
        ClosestPointHundler CPHtemp = new ClosestPointHundler();

        CPHresult.setData(null, null, -Integer.MAX_VALUE);

        for (Edge e : B.CurrentEdges) {
            CPHtemp.setData(null, null, 0);
            Vector ntemp = e.ComputeNormal();
            penetration = false;
            for (Vector v : A.CurrentVertices) {
                Vector vtemp = Vector.MakeVector(e.s, v);
                double distance = ntemp.Dot(vtemp);
                if (distance < CPHtemp.distance) {
                    penetration = true;
                    CPHtemp.setData(v, e, distance);
                }
            }
            if (!penetration) {
                return null;
            } else if (CPHtemp.distance > CPHresult.distance) {
                CPHresult.setData(CPHtemp.point, CPHtemp.edge, CPHtemp.distance);
            }
        }

        return CPHresult;

    }

    public static void checkCollisionOfBodyWithWorld(RigidBody A, World world) {
        ArrayList<ClosestPointHundler> CPHtempArr = new ArrayList<>();

        for (Vector CurrentVertex : A.CurrentVertices) {
            CPHtempArr.clear();
            for (Edge WorldEdge : world.WorldEdges) {
                if (CheckCollisionVertexWithEdge(CurrentVertex, WorldEdge, contactslist, true)) {
                    double Distance = Vector.MakeVector(WorldEdge.s, CurrentVertex).Dot(WorldEdge.ComputeNormal());
                    CPHtempArr.add(new ClosestPointHundler().setData(CurrentVertex, WorldEdge, Distance));
                }
            }
            if (!CPHtempArr.isEmpty()) {
                ClosestPointHundler CPHResult = ClosestPointHundler.getMinDistance(CPHtempArr);
                Contact contact = Contact.CreatNewContact(A, world, CPHResult.point, CPHResult.edge);
                if (!contactslist.contains(contact)) {
                    contactslist.add(contact);
                }
            }

        }

        if (world instanceof StairWorld) {
            for (Edge be : A.CurrentEdges) {
                int i = 0;
                for (Vector wv : world.WorldVertices) {

                    if (i > 13) {
                        continue;
                    }

                    int left = ++i;
                    int right = ++i;

                    if (CheckCollisionVertexWithEdge(be.s, world.WorldEdges[left], contactslist, false)
                            && CheckCollisionVertexWithEdge(be.e, world.WorldEdges[right], contactslist, false)) {
                        Contact contact = Contact.CreatNewContact(world, A, wv, be);
                        if (!contactslist.contains(contact)) {
                            contactslist.add(contact);
                            System.out.println("Collision v world with e body " + contact);
                        }
                    } else if (CheckCollisionVertexWithEdge(be.e, world.WorldEdges[left], contactslist, false)
                            && CheckCollisionVertexWithEdge(be.s, world.WorldEdges[right], contactslist, false)) {
                        Contact contact = Contact.CreatNewContact(world, A, wv, be);
                        if (!contactslist.contains(contact)) {
                            contactslist.add(contact);
                            System.out.println("Collision v world with e body " + contact);
                        }
                    }

                }
            }
        }

    }

    public static boolean CheckCollisionVertexWithEdge(Vector vertex, Edge edge, ArrayList<Contact> contactslist, boolean Type) {

        Vector v = Vector.MakeVector(edge.s, edge.e);
        double trend1 = Vector.MakeVector(edge.s, vertex).Dot(v);
        double trend2 = Vector.MakeVector(edge.e, vertex).Dot(v);

        double scop = trend1 * trend2;
        Vector normal;
        if (Type) {
            normal = edge.ComputeNormal();
        } else {
            normal = Vector.inverse(edge.ComputeNormal());
        }
        Vector vtemp = Vector.MakeVector(edge.s, vertex);
        double penetration = normal.Dot(vtemp);

        return scop < 0 && penetration < 0;

    }

    public static void HundleAllCollion(double epselon) {

        contactslist.stream().forEach((contact) -> {
            switch (contact.getType()) {
                case 0:
                    HundleCollisionDynamicVertexDynamicEdge(contact, epselon);
                    break;
                case 1:
                    HundleCollisionDynamicVertexStaticEdge(contact, epselon);
                    break;
                default:
                    HundleCollisionStaticVertexDynamicEdge(contact, epselon);
                    break;
            }
        });

        contactslist.clear();

    }

    public static void HundleCollisionDynamicVertexDynamicEdge(Contact contact, double epselon) {
        Vector VApr = ComputeAbsuloteVelocity(contact.getRA(), contact.getP());
        Vector VBpr = ComputeAbsuloteVelocity(contact.getRB(), contact.getP());
        Vector n = contact.getNormal();
        Vector VOutCome = Vector.subV2d(VApr, VBpr);
        double VRelative = n.Dot(VOutCome);
        if (VRelative > 0) {
            return;
        }
        Vector ra = Vector.MakeVector(contact.getRA().x, contact.getP());
        Vector rb = Vector.MakeVector(contact.getRB().x, contact.getP());

        double term1 = 1 / contact.getRA().mass;
        double term2 = 1 / contact.getRB().mass;
        double term3 = (Vector.MakeCrossProduct(ra, n) * Vector.MakeCrossProduct(ra, n)) / contact.getRA().inertia;
        double term4 = (Vector.MakeCrossProduct(rb, n) * Vector.MakeCrossProduct(rb, n)) / contact.getRB().inertia;
        double J = (-(epselon + 1) * VRelative) / (term1 + term2 + term3 + term4);
        Vector ImpluseA = Vector.multConstantVector(J, n);
        Vector ImpluseB = Vector.multConstantVector(J, Vector.inverse(n));

        contact.getRA().v.x += ImpluseA.x / contact.getRA().mass;
        contact.getRA().v.y += ImpluseA.y / contact.getRA().mass;
        contact.getRB().v.x += ImpluseB.x / contact.getRB().mass;
        contact.getRB().v.y += ImpluseB.y / contact.getRB().mass;
        contact.getRA().Omega += Vector.MakeCrossProduct(ra, ImpluseA) / contact.getRA().inertia;
        contact.getRB().Omega += Vector.MakeCrossProduct(rb, ImpluseB) / contact.getRB().inertia;

    }

    public static void HundleCollisionDynamicVertexStaticEdge(Contact contact, double epselon) {
        Vector VApr = ComputeAbsuloteVelocity(contact.getRA(), contact.getP());
        Vector n = contact.getNormal();
        Vector VOutCome = VApr;

        double VRelative = n.Dot(VOutCome);
        if (VRelative > 0) {
            return;

        }

        contact.getRA().UpdateVerticesLocation();
        contact.getRA().UpdateEdgesLocation();

        Vector ra = Vector.MakeVector(contact.getRA().x, contact.getP());

        double term1 = 1 / contact.getRA().mass;

        double term2 = (Vector.MakeCrossProduct(ra, n) * Vector.MakeCrossProduct(ra, n)) / contact.getRA().inertia;

        double J = (-(epselon + 1) * VRelative) / (term1 + term2);

        Vector ImpluseA = Vector.multConstantVector(J, n);

        contact.getRA().v.x += ImpluseA.x / contact.getRA().mass;
        contact.getRA().v.y += ImpluseA.y / contact.getRA().mass;

        contact.getRA().Omega += Vector.MakeCrossProduct(ra, ImpluseA) / contact.getRA().inertia;
    }

    public static void HundleCollisionStaticVertexDynamicEdge(Contact contact, double epselon) {
        Vector VBpr = ComputeAbsuloteVelocity(contact.getRB(), contact.getP());
        Vector n = contact.getNormal();
        Vector VOutCome = VBpr;
        double VRelative = n.Dot(VOutCome);
        if (VRelative > 0) {
            return;
        }
        Vector rb = Vector.MakeVector(contact.getRB().x, contact.getP());

        double term1 = 1 / contact.getRB().mass;
        double term2 = (Vector.MakeCrossProduct(rb, n) * Vector.MakeCrossProduct(rb, n)) / contact.getRB().inertia;

        double J = (-(epselon + 1) * VRelative) / (term1 + term2);
        Vector ImpluseB = Vector.multConstantVector(J, Vector.inverse(n));

        contact.getRB().v.x += ImpluseB.x / (contact.getRB().mass);
        contact.getRB().v.y += ImpluseB.y / (contact.getRB().mass);
        contact.getRB().Omega += Vector.MakeCrossProduct(rb, ImpluseB) / contact.getRB().inertia;
    }

    public static Vector ComputeAbsuloteVelocity(RigidBody body, Vector p) {
        Vector rad = Vector.MakeVector(body.x, p);
        return Vector.sumV2d(body.v, Vector.MakeCrossProduct(body.Omega, rad));
    }

    public static boolean AlreadyFound(Contact CurrentContact) {

        for (Contact c : contactslist) {

            if (c.getRA().equals(CurrentContact.getRA()) && c.getRB().equals(CurrentContact.getRB()) && c.getP().equals(CurrentContact.getP()) && c.getS().equals(CurrentContact.getS())) {
                return true;
            }

        }

        return false;

    }
}
