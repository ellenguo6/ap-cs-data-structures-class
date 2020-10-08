import java.awt.*;

/**
 * A bouncing ball in the game of Ball Blast
 * 
 * @author Ellen Guo
 * @version 5/27/19
 */
public class Ball extends Shape
{
    private int size;
    private int num;
    private final int initial;
    private static final double GRAVITY = 0.2;

    /**
     * Constructor for objects of class Ball
     * @param col the color
     * @param xcoord the x coordinate
     * @param ycoord the y coordinate
     * @param w the width
     * @param h the height
     * @param sz the size of the ball, smallest = 1
     * @param n the value of the ball
     */
    public Ball(Color col, double xcoord, double ycoord, double w, double h, int sz, int n)
    {
        super(true, col, xcoord, ycoord, w, h);
        size = sz;
        num = n;
        initial = n;
        setYVel(0.5);
        move();
    }
    
    /**
     * Getter for the size
     * @return size
     */
    public int size()
    {
        return size;
    }
    
    /**
     * Getter for the value
     * @return num
     */
    public int getNum()
    {
        return num;
    }
    
    /**
     * Getter for the initial value
     * @return initial
     */
    public int getInitial()
    {
        return initial;
    }
    
    /**
     * Setter for the size of the ball (either 1, 2 or 3)
     * @param sz the new size
     */
    public void setSize(int sz)
    {
        size = sz;
    }
    
    /**
     * Setter for the value of the ball
     * @param n the new num
     */
    public void setNum(int n)
    {
        num = n;
    }
    
    /**
     * Moves the Ball, allows acceleration by gravity
     */
    public void move()
    {
        setX(getX() + getXVel());
        setY(getY() + getYVel());
        setYVel(getYVel() + Math.abs(getYVel() * GRAVITY));
    }
}
