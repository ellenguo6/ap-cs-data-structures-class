import java.awt.*;
/**
 * A Cannon that shoots bullets in the Ball Blast game
 * 
 * @author Ellen Guo
 * @version 5/27/19
 */
public class Cannon extends Shape
{
    
    /**
     * Constructor for objects of class Cannon
     * Is a 50 by 75 blue rectangle that starts at the bottom of the screen, in the middle
     */
    public Cannon()
    {
        super(false, Color.BLUE, 275, 895, 50, 75);
    }

    /**
     * Implements the Shape's abstract move() method, but since the cannon
     * is moved by the arrows, this move() does nothing
     */
    public void move() {}
}
