import java.awt.Color;
import java.util.*;

/**
 * A Rook in the game of Chess
 * 
 * @author Ellen Guo
 * @version 4/28/19
 */
public class Rook extends Piece
{
    /**
     * constructs a new Rook with the given attributes.
     * 
     * @param col the color of the new piece
     * @param fileName the name of the file used to display this rook
     */
    public Rook(Color col, String fileName)
    {
        super(col, fileName, 5);
    }

    /**
     * Gets a list of Locations that this Rook can move to
     * Rooks can move in four directions: north, south, east, and west
     * They can move as many squares as they'd like, without "jumping over" other pieces
     * 
     * @return the possible Locations
     */
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> dests = new ArrayList<Location>();
        sweep(dests, Location.NORTH);
        sweep(dests, Location.SOUTH);
        sweep(dests, Location.EAST);
        sweep(dests, Location.WEST);
        return dests;
    }
    
}
