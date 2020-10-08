import java.awt.*;
import java.util.*;

/**
 * Represesents a rectangular game board, containing Piece objects.
 * 
 * @author Ellen Guo
 * 
 * @version 4/28
 */
public class Board extends BoundedGrid<Piece>
{
    /**
     * Constructs a new Board with the given dimensions
     */
    public Board()
    {
        super(8, 8);
    }

    /**
     * undos the given move (that has already occurred)
     * 
     * @param move the move to undo
     * 
     * @precondition move has already been made on the board
     * @postcondition piece has moved back to its source,
     *      and any captured piece is returned to its location
     */
    public void undoMove(Move move)
    {
        Piece piece = move.getPiece();
        Location source = move.getSource();
        Location dest = move.getDestination();
        Piece victim = move.getVictim();

        piece.moveTo(source);

        if (victim != null)
            victim.putSelfInGrid(piece.getBoard(), dest);
    }

    /**
     * Gets a list of all the possible moves that a given color can make
     * 
     * @param color the color whose moves you are finding
     * 
     * @return the list of the possible moves
     */
    public ArrayList<Move> allMoves(Color color)
    {
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                Piece p = get(new Location(r, c));
                if (p != null && p.getColor().equals(color))
                {
                    ArrayList<Location> locs = p.destinations();
                    for (Location l : locs)
                    {
                        moves.add(new Move(p, l));
                    }
                }
            }
        }
        return moves;
    }
    
    /**
     * Makes a given move happen!
     * 
     * @param move the given move to execute
     * @postcondition the board and pieces have been updated to reflect the move
     */
    public void executeMove(Move move)
    {
        move.getPiece().moveTo(move.getDestination());
    }
}