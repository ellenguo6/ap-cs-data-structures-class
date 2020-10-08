import java.awt.*;
import java.util.*;
/**
 * Represents a piece in a game
 * 
 * @author Ellen Guo
 * 
 * @version 4/28/19
 */

public abstract class Piece
{
    //the board this piece is on
    private Board board;

    //the location of this piece on the board
    private Location location;

    //the color of the piece
    private Color color;

    //the file used to display this piece
    private String imageFileName;

    //the approximate value of this piece in a game of chess
    private int value;

    /**
     * constructs a new Piece with the given attributes.
     * 
     * @param col the color of the new piece
     * @param fileName the name of the file used to display this piece
     * @param val the relative value of the piece 
     */
    public Piece(Color col, String fileName, int val)
    {
        color = col;
        imageFileName = fileName;
        value = val;
    }

    /**
     * Getter for the board 
     * @return the board this piece is on
     */
    public Board getBoard()
    {
        return board;
    }

    /**
     * Getter for the location 
     * @return the location of this piece on the board
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Getter for the color 
     * @return the color of this piece
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Getter for the color 
     * @return the name of the file used to display this piece
     */
    public String getImageFileName()
    {
        return imageFileName;
    }

    /**
     * Getter for the value 
     * @return a number representing the relative value of this piece
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Puts this piece into a board. If there is another piece at the given
     * location, it is removed. <br />
     * Precondition: (1) This piece is not contained in a grid (2)
     * <code>loc</code> is valid in <code>gr</code>
     * @param brd the board into which this piece should be placed
     * @param loc the location into which the piece should be placed
     */
    public void putSelfInGrid(Board brd, Location loc)
    {
        if (board != null)
            throw new IllegalStateException(
                "This piece is already contained in a board.");

        Piece piece = brd.get(loc);
        if (piece != null)
            piece.removeSelfFromGrid();
        brd.put(loc, this);
        board = brd;
        location = loc;
    }

    /**
     * Removes this piece from its board.
     * Precondition: This piece is contained in a board
     */
    public void removeSelfFromGrid()
    {
        if (board == null)
            throw new IllegalStateException(
                "This piece is not contained in a board.");
        if (board.get(location) != this)
            throw new IllegalStateException(
                "The board contains a different piece at location "
                + location + ".");

        board.remove(location);
        board = null;
        location = null;
    }

    /**
     * Moves this piece to a new location. If there is another piece at the
     * given location, it is removed.
     * 
     * @precondition This piece is contained in a grid
     * @precondition newLocation is valid in the grid of this piece
     * @param newLocation the new location
     */
    public void moveTo(Location newLocation)
    {
        if (board == null)
            throw new IllegalStateException("This piece is not on a board.");
        if (board.get(location) != this)
            throw new IllegalStateException(
                "The board contains a different piece at location "
                + location + ".");
        if (!board.isValid(newLocation))
            throw new IllegalArgumentException("Location " + newLocation
                + " is not valid.");

        if (newLocation.equals(location))
            return;
        board.remove(location);
        Piece other = board.get(newLocation);
        if (other != null)
            other.removeSelfFromGrid();
        location = newLocation;
        board.put(location, this);
    }

    /**
     * Tells if a given location in the board is valid for this Piece to go to
     * it's valid if it's on the board (within its boundaries)
     * and the destination is either unoccupied or occupied by a Piece of a different color
     * 
     * @param dest the Location we're testing to see if it's valid
     * 
     * @return true if the destination is valid (see method description)
     *      false otherwise
     */
    public boolean isValidDestination(Location dest)
    {
        int r = dest.getRow();
        int c = dest.getCol();
        return dest != null && r >= 0 && r < 8 && c >= 0 && c < 8 &&
            (board.get(dest) == null 
            || !((Piece)board.get(dest)).getColor().equals(color));
    }

    /**
     * Gets a list of Locations that this Piece can move to, given the circumstances
     * of the board and the rules governing the movement of the Piece
     * 
     * @return the possible Locations
     */
    public abstract ArrayList<Location> destinations();

    /**
     * Adds to a given ArrayList all Locations in a given direction until another Piece
     * or the edge of the board is reached. If the Piece encountered is of an opposing 
     * color, its Location is also added to the list (i.e. it can be captured)
     * 
     * @param dests the list to which new locations will be added
     * @param direction the direction in which to sweep for locations
     */
    public void sweep(ArrayList<Location> dests, int direction)
    {
        Location next = location.getAdjacentLocation(direction);
        while (isValidDestination(next) && board.get(next) == null)
        {
            dests.add(next);
            next = next.getAdjacentLocation(direction);
        }
        if (isValidDestination(next) && 
            !((Piece)board.get(next)).getColor().equals(color))
        {
            dests.add(next);
        }
    }
}