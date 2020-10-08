import java.awt.*;
import java.util.*;

// Represesents a rectangular game board, containing Piece objects.
public class Board extends BoundedGrid<Piece>
{
	// Constructs a new Board with the given dimensions
	public Board()
	{
		super(8, 8);
	}

	// Precondition:  move has already been made on the board
	// Postcondition: piece has moved back to its source,
	//                and any captured piece is returned to its location
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
}