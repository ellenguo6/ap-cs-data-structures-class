import java.awt.*;
/**
 * This class acts as a bullet. The bullet's behavior is that it moves upward.
 * 
 * @author Emma Dionne
 * @version 5.27.19
 */
public class Bullet extends Shape
{
    
    /**
     * Constructs a bullet by only calling the move method.
     * 
     * @param x the starting x position
     * @param y the starting y position
     */
    public Bullet(double x, double y)
    {
        super(true, Color.WHITE, x, y, 5, 15);
    }
    
    /**
     * Sets the y value up calling the super class's method, ie moves the bullet up 
     */
    public void move()
    {
        super.setY(super.getY() - 50);
    }
}