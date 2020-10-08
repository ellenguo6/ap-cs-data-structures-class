import java.util.*;
import java.awt.Color;

/**
 * A human player in a board game
 * gets a move from user input
 * 
 * @author Ellen Guo
 * @version 4/28/19
 */
public class HumanPlayer extends Player
{
    private BoardDisplay display;

    /**
     * Creates an instance of the HumanPlayer class with the given attributes
     * 
     * @param b the board this player is playing in
     * @param s the name of this player
     * @param c the color of this player
     * @param bd the display this player uses/clicks
     */
    public HumanPlayer(Board b, String s, Color c, BoardDisplay bd)
    {
        super(b, s, c);
        display = bd;
    }

    /**
     * Prompts the user for a move, and decides if it's valid
     * If the move is valid, returns it
     * otherwise continues to prompt the user for a move until a valid one is chosen
     * 
     * @return the move chosen
     */
    public Move nextMove()
    {
        boolean flag = false;
        Move move = null;
        while (!flag)
        {
            move = display.selectMove();
            ArrayList<Move> moves = getBoard().allMoves(getColor());
            for (Move m : moves)
            {
                if (move.equals(m))
                {
                    flag = true;
                }
            }
        }
        return move;
    }
}
