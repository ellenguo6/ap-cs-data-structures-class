import java.awt.Color;
import java.util.*;

/**
 * A Knight in the game of chess
 * 
 * @author Ellen Guo
 * @version 4/28/19
 */
public class Knight extends Piece
{
    /**
     * constructs a new Knight with the given attributes.
     * 
     * @param col the color of the new knight
     * @param fileName the name of the file used to display this knight
     */
    public Knight(Color col, String fileName)
    {
        super(col, fileName, 3);
    }

    /**
     * Gets a list of Locations that this Knight can move to
     * Knights move in the shape of an L (so max of 8 possible destinations)
     * they can jump over other pieces
     * 
     * @return the possible Locations
     */
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> dests = new ArrayList<Location>();
        int r = getLocation().getRow();
        int c = getLocation().getCol();
        int[] dx = {1, 1, -1, -1, -2, -2, 2, 2};
        int[] dy = {2, -2, 2, -2, 1, -1, 1, -1};
        for (int i = 0; i < 8; i++)
        {
            Location l = new Location(r + dx[i], c + dy[i]);
            if (isValidDestination(l))
                dests.add(l);
        }
        return dests;
    }
}
