package Tools;

import java.util.ArrayList;

public class ClosestPointHundler {

    public Vector point;
    public Edge edge;
    public double distance;

    public ClosestPointHundler setData(Vector point, Edge edge, double distance) {

        this.point = point;
        this.edge = edge;
        this.distance = distance;
        return this;
    }

    public static ClosestPointHundler getMinDistance(ArrayList<ClosestPointHundler> CPHArr) {
        double MinDistance = -Double.MAX_VALUE;
        for (ClosestPointHundler cph : CPHArr) {
            if (cph.distance > MinDistance) {
                MinDistance = cph.distance;
            }
        }
        return Search(CPHArr, MinDistance);
    }

    public static ClosestPointHundler Search(ArrayList<ClosestPointHundler> CPHArr, double MinDistance) {
        for (ClosestPointHundler cph : CPHArr) {
            if (cph.distance == MinDistance) {
                return cph;
            }
        }
        return null;
    }

}
