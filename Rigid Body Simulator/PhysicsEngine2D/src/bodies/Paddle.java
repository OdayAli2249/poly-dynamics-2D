package bodies;

import Tools.Triple;
import Tools.Vector;

public class Paddle extends StaticBody {

    public Paddle(Vector x, double Dimension1, double Dimension2) {
        super(x, Dimension1, Dimension2);
        Color = new Triple(1,0,0);

    }

    public void rotate(double degree) {
        this.Omega = degree;
        this.Theta += degree;
    }

    public void stop() {
        this.Omega = 0;
    }

    public Paddle Update() {
        this.UpdateVerticesLocation();
        this.UpdateEdgesLocation();
        return this;
    }

}
