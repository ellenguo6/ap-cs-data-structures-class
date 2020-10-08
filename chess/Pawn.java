import java.awt.Color;
import java.util.*;

/**
 * A Pawn in the game of chess
 * 
 * @author Ellen Guo
 * @version 4/28/19
 */
public class Pawn extends Piece
{
    // if the Pawn has not moved yet, firstMove = true; false otherwise
    private boolean firstMove;
    
    /**
     * constructs a new Pawn with the given attributes.
     * 
     * @param col the color of the new pawn
     * @param fileName the name of the file used to display this pawn
     */
    public Pawn(Color col, String fileName)
    {
        super(col, fileName, 1);
        firstMove = true;
    }

    /**
     * Gets a list of Locations that this Pawn can move to
     * Pawns can only move 1 square forward, or capture pieces of the opposing color
     * through diagonals only
     * if it's the pawn's first move, it may also move 2 squares forward, provided
     * it doesn't jump over other pieces
     * 
     * @return the possible Locations
     */
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> dests = new ArrayList<Location>();
        int r = getLocation().getRow();
        int c = getLocation().getCol();
        boolean black = getColor().equals(Color.BLACK);

        Location forward = null;
        if (black)
            forward = new Location(r + 1, c);
        else
            forward = new Location(r - 1, c);
        if (isValidDestination(forward) && getBoard().get(forward) == null)
        {
            dests.add(forward);
            if (firstMove)
            {
                Location forward2 = null;
                if (black)
                    forward2 = new Location(r + 2, c);
                else
                    forward2 = new Location(r - 2, c);
                if (isValidDestination(forward2) && getBoard().get(forward2) == null)
                {
                    dests.add(forward2);
                }
            }

        }

        Location left = null;
        if (black)
            left = new Location(r + 1, c + 1);
        else
            left = new Location(r - 1, c - 1);
        if (isValidDestination(left) && getBoard().get(left) != null &&
            !getBoard().get(left).getColor().equals(getColor()))
        {
            dests.add(left);
        }

        Location right = null;
        if (black)
            right = new Location(r + 1, c - 1);
        else
            right = new Location(r - 1, c + 1);
        if (isValidDestination(right) && getBoard().get(right) != null && 
            !getBoard().get(right).getColor().equals(getColor()))
        {
            dests.add(right);
        }
        return dests;
    }

    /**
     * Sets the firstMove instance var to a given boolean
     * 
     * @param f the given value
     */
    public void setFirstMove(boolean f)
    {
        firstMove = f;
    }
}
