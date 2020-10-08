import java.awt.*;
/**
 * Acts as the boundaries of the game
 * 
 * @author Emma Dionne
 * @version 5.27.19
 */
public class Wall extends Shape
{
    /**
     * Constructor for objects of class Wall, is light gray and rectangular
     * 
     * @param xcoord the starting x position
     * @param ycoord the strating y position
     * @param w the width
     * @param h the height
     */
    public Wall(double xcoord, double ycoord, double w, double h)
    {
        super(false, Color.LIGHT_GRAY, xcoord, ycoord, w, h);
    }
    
    /**
     * Implements the Shape's abstract move() method, but since the wall
     * doesn't move, this move() does nothing
     */
    public void move() {}
}