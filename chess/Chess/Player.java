import java.awt.Color;
import java.util.*;

/**
 * Abstract class Player
 * A player in any board game
 * has a name and a color
 * 
 * @author Ellen Guo
 * @version 4/28/19
 */
public abstract class Player
{
    private Board board;
    private String name;
    private Color color;

    /**
     * Creates an instance of the Player class with the given attributes
     * 
     * @param b the board this player is playing in
     * @param s the name of this player
     * @param c the color of this player
     */
    public Player(Board b, String s, Color c)
    {
        board = b;
        name = s;
        color = c;
    }
    
    /**
     * Getter for the board this player is on
     * 
     * @return the board
     */
    public Board getBoard()
    {
        return board;
    }
    
    /**
     * Getter for the name of this Player
     * 
     * @return the name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Getter for the color of this color
     * 
     * @return the color
     */
    public Color getColor()
    {
        return color;
    }
    
    /**
     * Gets the move this player, depending on what kind of player it is
     * 
     * @return the next move this player chooses to take
     */
    public abstract Move nextMove();
}   
