import java.util.*;
import java.awt.Color;

/**
 * A random player in a board game
 * decides its moves randomly
 * 
 * @author Ellen Guo
 * @version 4/28/19
 */
public class RandomPlayer extends Player
{
    
    /**
     * Creates an instance of the RandomPlayer class with the given attributes
     * 
     * @param b the board this player is playing in
     * @param s the name of this player
     * @param c the color of this player
     */
    public RandomPlayer(Board b, String s, Color c)
    {
        super(b, s, c);
    }

    /**
     * Out of all its possible moves, the RandomPlayer chooses one randomly
     * 
     * @return move a randomly selected move
     */
    public Move nextMove()
    {
        ArrayList<Move> moves = getBoard().allMoves(getColor());
        int random = (int)(Math.random() * moves.size());
        return moves.get(random);
    }
}
