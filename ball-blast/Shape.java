import java.awt.*;
/**
 * A Shape in the game of Ball Blast
 * 
 * @author Ellen Guo
 * @version 5/24/19
 */
public abstract class Shape
{
    private boolean isRound;
    private Color color;
    private double x;
    private double y;
    private double width;
    private double height;
    private double xVel;
    private double yVel;

    /**
     * Constructor for objects of class Shape
     * 
     * @param round if the shape is roiund
     * @param col the color
     * @param xcoord the x coordinate
     * @param ycoord the y coordinate
     * @param w the width
     * @param h the height
     */
    public Shape(boolean round, Color col, double xcoord, double ycoord, double w, double h)
    {
        isRound = round;
        color = col;
        x = xcoord;
        y = ycoord;
        width = w;
        height = h;
    }
    
    /**
     * Getter for roundness
     * @return true if round, false otherwise
     */
    public boolean isRound()
    {
        return isRound;
    }
    
    /**
     * Getter for color
     * @return the color
     */
    public Color getColor() 
    {
        return color;
    }
    
    /**
     * Getter for x coordinate
     * @return x
     */
    public double getX()
    {
        return x;
    }
    
    /**
     * Getter for y coordinate
     * @return y
     */
    public double getY()
    {
        return y;
    }
    
    /**
     * Getter for x velocity
     * @return xVel
     */
    public double getXVel()
    {
        return xVel;
    }
   
    /**
     * Getter for y velocity
     * @return yVel
     */
    public double getYVel()
    {
        return yVel;
    }
    
    /**
     * Getter for width
     * @return yVel
     */
    public double getWidth()
    {
        return width;
    }
    
    /**
     * Getter for y velocity
     * @return yVel
     */
    public double getHeight()
    {
        return height;
    }
    
    /**
     * Setter for the color
     * @param col the new color
     */
    public void setColor(Color col)
    {
        color = col;
    }
    
    /**
     * Setter for the width
     * @param w the new width
     */
    public void setWidth(double w)
    {
        width = w;
    }
    
    /**
     * Setter for the height
     * @param h the new height
     */
    public void setHeight(double h)
    {
        height = h;
    }
    
    /**
     * Setter for the x coordinate
     * @param xcoord the new x coord
     */
    public void setX(double xcoord)
    {
        x = xcoord;
    }
    
    /**
     * Setter for the y coordinate
     * @param ycoord the new y coord
     */
    public void setY(double ycoord)
    {
        y = ycoord;
    }
    
    /**
     * Setter for the x velocity
     * @param xVelocity the new x vel
     */
    public void setXVel(double xVelocity)
    {
        xVel = xVelocity;
    }
    
    /**
     * Setter for the y velocity
     * @param yVelocity the new y vel
     */
    public void setYVel(double yVelocity)
    {
        yVel = yVelocity;
    }
    
    /**
     * how the Shape moves; different for each shape, so is an abstract method
     */
    public abstract void move();
    
}
