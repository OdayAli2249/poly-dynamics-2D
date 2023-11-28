package bodies;

import Tools.Triple;
import Tools.Vector;

public class Wall extends StaticBody {

    public Wall(Vector x, double Dimension1, double Dimension2) {
        super(x, Dimension1, Dimension2);
        Color = new Triple(0.349, 0.1098, 0.0);
    }

    public Wall(Vector x, double Dimension1, double Dimension2, double Theta) {
        super(x, Dimension1, Dimension2, Theta);
        Color = new Triple(0.349, 0.1098, 0.0);
    }

}
