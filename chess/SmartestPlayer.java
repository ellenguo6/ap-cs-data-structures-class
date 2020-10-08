import java.util.*;
import java.awt.Color;

/**
 * A smart player in a board game
 * decides its moves by using the MiniMax algorithm to look ahead a given
 * number of moves in the future
 * 
 * @author Ellen Guo
 * @version 4/28/19
 */
public class SmartestPlayer extends Player
{
    // how many moves it looks ahead
    int smartness;

    /**
     * Creates an instance of the SmartestPlayer class with the given attributes
     * 
     * @param b the board this player is playing in
     * @param s the name of this player
     * @param c the color of this player
     * @param smart the number of moves ahead this player can see
     */
    public SmartestPlayer(Board b, String s, Color c, int smart)
    {
        super(b, s, c);
        smartness = smart;
    }

    /**
     * Decides on a move depending on how many moves to look ahead
     * Evaluates how good a move is depending on how well the opponent can respond
     * Chooses the move with the weakest opponent response
     * 
     * @return the best move, assuming that the opponent is just as smart as this player
     */
    public Move nextMove()
    {
        Board board = getBoard();
        ArrayList<Move> moves = board.allMoves(getColor());
        int maxdex = 0;
        board.executeMove(moves.get(0));
        int maxscore = valueOfMeanestResponse(smartness);
        boolean flag = false;
        board.undoMove(moves.get(0));
        for (int i = 1; i < moves.size(); i++)
        {
            board.executeMove(moves.get(i));
            int response = valueOfMeanestResponse(smartness);
            if (response > maxscore)
            {
                maxdex = i;
                maxscore = response;
                flag = true;
            }
            board.undoMove(moves.get(i));
        }
        if (!flag)
        {
            maxdex = (int)(Math.random() * moves.size());
        }
        return moves.get(maxdex);
    }

    /**
     * Computes the value of the opponent's preferred move
     * Finds the opponent's preferred move by knowing that the opponent will want
     * this player to have a bad move
     * 
     * @param n the number of moves to look ahead
     * @return int the value of the opponent's meanest response
     */
    private int valueOfMeanestResponse(int n)
    {
        if (n == 0)
        {
            return score();
        }
        
        Board board = getBoard();
        Color oppoColor = Color.BLACK;
        if (getColor().equals(Color.BLACK))
        {
            oppoColor = Color.WHITE;
        }
        ArrayList<Move> oppoMoves = board.allMoves(oppoColor);
        board.executeMove(oppoMoves.get(0));
        int minscore = valueOfBestMove(n - 1);
        for (Move m : oppoMoves)
        {
            board.executeMove(m);
            int best = valueOfBestMove(n - 1);
            if (best < minscore)
            {
                minscore = best;
            }
            board.undoMove(m);
        }
        return minscore;
    }

    /**
     * Computes the value of best possible move that this player can make
     * The best possible move results in the weakest response from the opponent
     * 
     * @param n the number of moves to look ahead and decide the value
     * @return the value of the best move
     */
    private int valueOfBestMove(int n)
    {
        if (n == 0)
        {
            return score();
        }
        Board board = getBoard();
        ArrayList<Move> moves = board.allMoves(getColor());
        board.executeMove(moves.get(0));
        int maxscore = valueOfMeanestResponse(n - 1);
        board.undoMove(moves.get(0));
        for (Move m : moves)
        {
            board.executeMove(m);
            int score = valueOfMeanestResponse(n - 1);
            if (score > maxscore)
            {
                maxscore = score;
            }
            board.undoMove(m);
        }
        return maxscore;
    }

    /**
     * Scores the game board for this player
     * Adds the value of each piece if it belongs to this player
     * Subtracts the value of each piece that belongs to the opponent
     * 
     * @return the score
     */
    private int score()
    {
        Board board = getBoard();
        int score = 0;
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                Piece p = board.get(new Location(r, c));
                if (p != null)
                {
                    int val = p.getValue();
                    if (p.getColor().equals(getColor()))
                    {
                        score += val;
                    }
                    else

                    {
                        score -= val;
                    }
                }
            }
        }
        return score;
    }
}
