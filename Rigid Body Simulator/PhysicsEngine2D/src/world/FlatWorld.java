
package world;

import Tools.Edge;
import Tools.Vector;
import java.awt.event.KeyEvent;


public class FlatWorld extends World{
   
    private final double Scaleh = 50*1.9;
    private final double Scalew = 50*1.92;
    
    public FlatWorld(){
        WorldEdges = new Edge[4];
        this.WorldEdges[0] = new Edge(new Vector(0 * Scalew, 0 * Scaleh), new Vector(10 * Scalew, 0 * Scaleh)).Inverse();
        this.WorldEdges[1] = new Edge(new Vector(10 * Scalew, 0 * Scaleh), new Vector(10 * Scalew, 10 * Scaleh)).Inverse();
        this.WorldEdges[2] = new Edge(new Vector(10 * Scalew, 10 * Scaleh), new Vector(0 * Scalew, 10 * Scaleh)).Inverse();
        this.WorldEdges[3] = new Edge(new Vector(0 * Scalew, 10 * Scaleh), new Vector(0 * Scalew, 0 * Scaleh)).Inverse();
        
    }

    @Override
    public World Build() {
      return this; 
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
      
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
      
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
      
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
       
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
       
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

   
}
