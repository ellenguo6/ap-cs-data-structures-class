import java.awt.Color;
import java.util.*;
import java.io.*;

/**
 * The game of chess!!!
 * 
 * @author Ellen Guo
 *
 * @version 4/28/19
 */
public class Game 
{

    /**
     * Play the game of chess!
     * Prompts the user to choose how many human players and the smartness of a computer
     * player (if any)
     *
     * @param args arguments from the command line
     */
    public static void main(String [ ] args)
    {
        Board board = new Board();

        Piece blackKing = new King(Color.BLACK, "chessfiles/black_king.gif");
        blackKing.putSelfInGrid(board, new Location(0, 4));

        Piece whiteKing = new King(Color.WHITE, "chessfiles/white_king.gif");
        whiteKing.putSelfInGrid(board, new Location(7, 4));

        Piece whiteRook1 = new Rook(Color.WHITE, "chessfiles/white_rook.gif");
        whiteRook1.putSelfInGrid(board, new Location(7, 0));
        Piece whiteRook2 = new Rook(Color.WHITE, "chessfiles/white_rook.gif");
        whiteRook2.putSelfInGrid(board, new Location(7, 7));

        Piece blackRook1 = new Rook(Color.BLACK, "chessfiles/black_rook.gif");
        blackRook1.putSelfInGrid(board, new Location(0, 0));
        Piece blackRook2 = new Rook(Color.BLACK, "chessfiles/black_rook.gif");
        blackRook2.putSelfInGrid(board, new Location(0, 7));

        for (int i = 0; i < 8; i++)
        {
            Piece whitePawn = new Pawn(Color.WHITE, "chessfiles/white_pawn.gif");
            whitePawn.putSelfInGrid(board, new Location(6, i));

            Piece blackPawn = new Pawn(Color.BLACK, "chessfiles/black_pawn.gif");
            blackPawn.putSelfInGrid(board, new Location(1, i));
        }

        Piece whiteBishop1 = new Bishop(Color.WHITE, "chessfiles/white_bishop.gif");
        whiteBishop1.putSelfInGrid(board, new Location(7, 2));
        Piece whiteBishop2 = new Bishop(Color.WHITE, "chessfiles/white_bishop.gif");
        whiteBishop2.putSelfInGrid(board, new Location(7, 5));

        Piece blackBishop1 = new Bishop(Color.BLACK, "chessfiles/black_bishop.gif");
        blackBishop1.putSelfInGrid(board, new Location(0, 2));
        Piece blackBishop2 = new Bishop(Color.BLACK, "chessfiles/black_bishop.gif");
        blackBishop2.putSelfInGrid(board, new Location(0, 5));

        Piece whiteQueen = new Queen(Color.WHITE, "chessfiles/white_queen.gif");
        whiteQueen.putSelfInGrid(board, new Location(7, 3));

        Piece blackQueen = new Queen(Color.BLACK, "chessfiles/black_queen.gif");
        blackQueen.putSelfInGrid(board, new Location(0, 3));

        Piece whiteKnight1 = new Knight(Color.WHITE, "chessfiles/white_knight.gif");
        whiteKnight1.putSelfInGrid(board, new Location(7, 1));
        Piece whiteKnight2 = new Knight(Color.WHITE, "chessfiles/white_knight.gif");
        whiteKnight2.putSelfInGrid(board, new Location(7, 6));

        Piece blackKnight1 = new Knight(Color.BLACK, "chessfiles/black_knight.gif");
        blackKnight1.putSelfInGrid(board, new Location(0, 1));
        Piece blackKnight2 = new Knight(Color.BLACK, "chessfiles/black_knight.gif");
        blackKnight2.putSelfInGrid(board, new Location(0, 6));

        Scanner in = new Scanner(System.in);
        Player white = null;
        Player black = null;

        BoardDisplay display = new BoardDisplay(board);

        System.out.println("How many human players are there? (0, 1, or 2)");
        int numPlays = in.nextInt();
        
        if (numPlays == 0)
        {
            System.out.println("What kind of player do you want the first computer to be?");
            white = getComputer(board, Color.WHITE, in);
            System.out.println("What kind of player do you want the second computer to be?");
            black = getComputer(board, Color.BLACK, in);
        }
        else if (numPlays == 1)
        {
            System.out.println("What's your name?");
            in.nextLine();
            String name = in.nextLine();
            white = new HumanPlayer(board, name, Color.WHITE, display);
            System.out.println("What kind of player do you want the computer to be?");
            black = getComputer(board, Color.BLACK, in);
        }
        else if (numPlays == 2)
        {
            System.out.println("What's the name of the Player 1?");
            in.nextLine();
            String name1 = in.nextLine();
            white = new HumanPlayer(board, name1, Color.WHITE, display);
            System.out.println("What's the name of the Player 2?");
            String name2 = in.nextLine();
            black = new HumanPlayer(board, name2, Color.BLACK, display);
        }
        
        System.out.println("\nLet's play!");
        play(board, display, white, black);

        // Testing destinations of kings
        //         ArrayList<Location> locs = blackKing.destinations();
        //         for (Location l : locs)
        //         {
        //             display.setColor(l, Color.YELLOW);
        //         }
        //         
        //         ArrayList<Location> locs2 = whiteKing.destinations();
        //         for (Location l : locs2)
        //         {
        //             display.setColor(l, Color.ORANGE);
        //         }

        // testing destinations of rook
        //         ArrayList<Location> locs = whiteRook1.destinations();
        //         for (Location l : locs)
        //         {
        //             display.setColor(l, Color.GREEN);
        //         }

        // testing getting all moves of one color
        //         ArrayList<Move> moves = board.allMoves(Color.WHITE);
        //         for (Move l : moves)
        //         {
        //             display.setColor(l.getDestination(), Color.GREEN);
        //         }

        // testing RandomPlayer's moves
        //         Player rando = new RandomPlayer(board, "Ellen", Color.WHITE);
        //         Move move = rando.nextMove();
        //         display.setColor(move.getSource(), Color.RED);
        //         display.setColor(move.getDestination(), Color.GREEN);

        // Two humans
        //         Player white = new HumanPlayer(board, "Ellen", Color.WHITE, display);
        //         Player black = new HumanPlayer(board, "Dad", Color.BLACK, display);
        //         play(board, display, white, black);

        // 1 random player
        //         Player white = new HumanPlayer(board, "Ellen", Color.WHITE, display);
        //         Player black = new RandomPlayer(board, "Rando", Color.BLACK);
        //         play(board, display, white, black);

        // 1 smart player
        //         Player white = new HumanPlayer(board, "Ellen", Color.WHITE, display);
        //         Player black = new SmartPlayer(board, "Smart", Color.BLACK);
        //         play(board, display, white, black);

        // 1 smarter player
        //         Player white = new HumanPlayer(board, "Ellen", Color.WHITE, display);
        //         Player black = new SmarterPlayer(board, "Smarter", Color.BLACK);
        //         play(board, display, white, black);

        // 1 smartest player, looks ahead 4 moves
        //         Player white = new HumanPlayer(board, "Ellen", Color.WHITE, display);
        //         Player black = new SmartestPlayer(board, "Smartest", Color.BLACK, 4);

    }

    /**
     * Executes the move of the given player, lighting the original square (source) red
     * and the square the piece moved to (the destination) green
     * If the piece moved is a pawn, this method checks if the pawn can be promoted
     * Sets the title of the display to the player's name
     * 
     * @param board the board in which the turn is happening
     * @param display the display of the turn
     * @param player the given player whose turn it is
     */
    private static void nextTurn(Board board, BoardDisplay display, Player player)
    {
        display.setTitle(player.getName());
        Move move = player.nextMove();
        board.executeMove(move);
        display.clearColors();
        Piece p = move.getPiece();
        Location dest = move.getDestination();

        if (p instanceof Pawn)
        {
            ((Pawn)p).setFirstMove(false);
            int r = dest.getRow();
            if ((p.getColor().equals(Color.BLACK) && r == 7) 
                || (p.getColor().equals(Color.WHITE) && r == 0))
            {
                p.removeSelfFromGrid();
                Piece promoted = promotePawn(player, p.getColor());
                promoted.putSelfInGrid(board, dest);
            }
        }
        
        display.setColor(move.getSource(), Color.RED);
        display.setColor(dest, Color.GREEN);
        try 
        {
            Thread.sleep(500);
        } 
        catch (InterruptedException e) 
        {
            return;
        }
    }

    /**
     * Alternates turns between two players, i.e. plays the game of chess
     * 
     * @param board the board in which we are playing
     * @param display the representation of the game
     * @param white the first Player (color is white)
     * @param black the second Player (color is black)
     */
    public static void play(Board board, BoardDisplay display, Player white, Player black)
    {
        while (true)
        {
            nextTurn(board, display, white);
            nextTurn(board, display, black);
        }
    }

    /**
     * Prompts the user to decide what kind of computer player it wants
     * either Random or Smart, and if it's smart then this method prompts the user to input
     * how many moves ahead the Smart Player should look
     * 
     * @param board the board in which the Player will be generated
     * @param c the color of the generated Player
     * @param in a scanner that gets the input of the user
     * 
     * @return a computer player, randomness/smartness depending on the user input
     */
    private static Player getComputer(Board board, Color c, Scanner in)
    {
        System.out.println("\n1: Random Player\n2: Smart Player");
        int player = in.nextInt();
        if (player == 1)
        {
            return new RandomPlayer(board, "RandomPlayer", c);
        }
        if (player == 2)
        { 
            System.out.println("How many moves ahead do you want the smart player to look?");
            int smart = in.nextInt();
            return new SmartestPlayer(board, "Smart Player: Lookahead: " + smart, c, smart);
        }
        return null;
    }

    /**
     * Returns a promoted-Pawn
     * if the player is a human, prompts the user for input about what piece to promote the pawn to
     * if the player is a Random Player, it randomly decides what piece to promote to
     * if the player is a Smart Player, it decides to get a Queen
     * 
     * @param p the Player who is promoting
     * @param c the color of the piece to create
     * @return the new Piece that the pawn will be replaced by
     */
    private static Piece promotePawn(Player p, Color c)
    {
        System.out.println("\nPromote your pawn!");
        int n = 0;

        if (p instanceof RandomPlayer)
        {
            n = (int)(Math.random() * 4) + 1;
        }
        if (p instanceof SmartestPlayer)
        {
            n = 1;
        }
        if (p instanceof HumanPlayer)
        {
            System.out.println("1: Queen \n2: Knight \n3: Rook \n4: Bishop");
            Scanner in = new Scanner(System.in);
            n = in.nextInt();
        }

        String color = "black";
        if (c.equals(Color.WHITE))
        {
            color = "white";
        }

        if (n == 1)
        {
            return new Queen(c, "chessfiles/" + color + "_queen.gif");
        }
        if (n == 2)
        {
            return new Knight(c, "chessfiles/" + color + "_knight.gif");
        }
        if (n == 3)
        {
            return new Rook(c, "chessfiles/" + color + "_rook.gif");
        }
        if (n == 4)
        {
            return new Bishop(c, "chessfiles/" + color + "_bishop.gif");
        }
        return null;
    }

}
