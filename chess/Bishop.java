import java.awt.Color;
import java.util.*;

/**
 * A bishop in the game of Chess
 * 
 * @author Ellen Guo
 * @version 4/28/19
 */
public class Bishop extends Piece
{
    /**
     * constructs a new Bishop with the given attributes.
     * 
     * @param col the color of the new bishop
     * @param fileName the name of the file used to display this bishop
     */
    public Bishop(Color col, String fileName)
    {
        super(col, fileName, 3);
    }

    /**
     * Gets a list of Locations that this Bishop can move to
     * Bishops can move as many square as they want, 
     * but only in diagonal directions (NE, NW, SE, SW)
     * they cannot jump over other pieces, 
     * but can capture pieces of the opposing color in the location
     * they land
     * 
     * @return the possible Locations
     */
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> dests = new ArrayList<Location>();
        sweep(dests, Location.NORTHEAST);
        sweep(dests, Location.NORTHWEST);
        sweep(dests, Location.SOUTHEAST);
        sweep(dests, Location.SOUTHWEST);
        return dests;
    }
}
