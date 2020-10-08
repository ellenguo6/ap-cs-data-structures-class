import java.awt.Color;
import java.util.*;

/**
 * A King in the game of Chess
 * 
 * @author Ellen Guo
 * @version 4/28/19
 */
public class King extends Piece
{

    /**
     * constructs a new King with the given attributes.
     * 
     * @param col the color of the new king
     * @param fileName the name of the file used to display this king
     */
    public King(Color col, String fileName)
    {
        super(col, fileName, 1000);
    }

    /**
     * Gets a list of Locations that this King can move to
     * Kings can move one square in all 8 directions, provided it is within 
     * the bounds of the board and not occupied by a piece of the same color
     * 
     * @return the possible Locations
     */
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> list = new ArrayList<Location>();
        Location loc = getLocation();
        for (int r = loc.getRow() - 1; r <= loc.getRow() + 1; r++)
        {
            for (int c = loc.getCol() - 1; c <= loc.getCol() + 1; c++)
            {
                Location l = new Location(r, c);
                if (isValidDestination(l))
                {
                    list.add(l);
                }
            }
        }
        return list;
    }
}
