import java.awt.Color;
import java.util.*;

/**
 * A Queen in the game of chess
 * 
 * @author Ellen Guo
 * @version 4/28/19
 */
public class Queen extends Piece
{
    /**
     * constructs a new Queen with the given attributes.
     * 
     * @param col the color of the new queen
     * @param fileName the name of the file used to display this queen
     */
    public Queen(Color col, String fileName)
    {
        super(col, fileName, 9);
    }

    /**
     * Gets a list of Locations that this Queen can move to
     * Queens can move as many square as they want, 
     * in all 8 directions (N, S, E, W, NE, NW, SE, SW)
     * they cannot jump over other pieces, 
     * but can capture pieces of the opposing color in the location they land
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
        sweep(dests, Location.NORTHEAST);
        sweep(dests, Location.NORTHWEST);
        sweep(dests, Location.SOUTHEAST);
        sweep(dests, Location.SOUTHWEST);
        return dests;
    }
}
