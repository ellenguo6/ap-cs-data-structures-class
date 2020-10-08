import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * @author Anu Datar
 * 
 * Changed block size and added a split panel display for next block and Score
 * 
 * @author Ryan Adolf
 * @author Ellen Guo
 * @version 1.0
 * 
 * Fixed the lag issue with block rendering 
 * Removed the JPanel
 * 
 * Ellen:
 * added a JLabel to keep track of score and level
 * added a button to start a new game
 */
// Used to display the contents of a game board
public class BlockDisplay extends JComponent implements KeyListener
{
    private static final Color BACKGROUND = Color.BLACK;
    private static final Color BORDER = Color.BLACK;

    private static final int OUTLINE = 2;
    private static final int BLOCKSIZE = 20;

    private MyBoundedGrid<Block> board;
    private JFrame frame;
    private ArrowListener listener;
    private MouseListener buttonListener; //ELLEN CODE

    private JLabel label; //ELLEN CODE
    private JButton button; //ELLEN CODE

    // Constructs a new display for displaying the given board    
    public BlockDisplay(MyBoundedGrid<Block> board)
    {
        this.board = board;

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    createAndShowGUI();
                }
            });

        //Wait until display has been drawn
        try
        {
            while (frame == null || !frame.isVisible())
                Thread.sleep(1);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI()
    {
        //Create and set up the window.
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        
        frame.addKeyListener(this);

        //Display the window.
        this.setPreferredSize(new Dimension(
                BLOCKSIZE * board.getNumCols(),
                BLOCKSIZE * board.getNumRows()
            ));

        // Adds score and level box at the top of the display ELLEN CODE
        label = new JLabel("<html>Level: 1<br>Score: 0</html>");
        frame.add(label, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(BORDER);
        g.fillRect(0, 0, BLOCKSIZE * board.getNumCols() + OUTLINE, BLOCKSIZE * board.getNumRows());
        for (int row = 0; row < board.getNumRows(); row++)
            for (int col = 0; col < board.getNumCols(); col++)
            {
                Location loc = new Location(row, col);

                Block square = board.get(loc);

                if (square == null)
                    g.setColor(BACKGROUND);
                else
                    g.setColor(square.getColor());

                g.fillRect(col * BLOCKSIZE + OUTLINE/2, row * BLOCKSIZE + OUTLINE/2,
                    BLOCKSIZE - OUTLINE, BLOCKSIZE - OUTLINE);
            }

    }

    //Redraws the board to include the pieces and border colors.
    public void showBlocks()
    {
        repaint();
    }

    // Sets the title of the window.
    public void setTitle(String title)
    {
        frame.setTitle(title);
    }

    public void keyTyped(KeyEvent e)
    {
          
    }

    public void keyReleased(KeyEvent e)
    {
         
    }

    public void keyPressed(KeyEvent e)
    {
        if (listener == null)
            return;
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT)
            listener.leftPressed();
        else if (code == KeyEvent.VK_RIGHT)
            listener.rightPressed();
        else if (code == KeyEvent.VK_DOWN)
            listener.downPressed();
        else if (code == KeyEvent.VK_UP)
            listener.upPressed();
        else if (code == KeyEvent.VK_SPACE)
            listener.spacePressed();
    }

    public void setArrowListener(ArrowListener listener)
    {
        this.listener = listener;
    }

    /**
     * Method written by Ellen
     * 
     * Sets the button listener to a mouselistener
     * 
     * @param blistener the new button listener
     * @postcondition updates the MouseListener
     */
    public void setButtonListener(MouseListener blistener)
    {
        this.buttonListener = blistener;
    }

    
    /**
     * Method written by Ellen
     * 
     * Changes the Jlabel for the display given the new level and score
     * 
     * @param level the new level
     * @param score the new score
     * @postcondition the label has been changed 
     */
    public void setText(int level, int score)
    {
        frame.getContentPane().remove(label);
        label = new JLabel("<html>Level: " + level + "<br>Score: " + score + "</html>");
        frame.add(label, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    /**
     * Method written by Ellen
     * 
     * Updates the display to a situation when the user has lost 
     * Creates the new game button and updates the label with the final level and score and a 
     * game over message
     * 
     * @param level the new level
     * @param score the new score
     * 
     * @postcondition the display now has a new game button and a game over message
     */
    public void lose(int level, int score)
    {
        frame.getContentPane().remove(label);
        label = new JLabel("<html>Level: " + level + "<br>Score: " 
            + score + "<br>GAME OVER</html>");
        frame.add(label, BorderLayout.NORTH);
        
        button = new JButton("New Game");
        button.setFocusable(false);
        frame.add(button, BorderLayout.SOUTH);
        button.addMouseListener(buttonListener);
        
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * Method written by Ellen
     * 
     * Updates the display to get ready for a new game
     * 
     * @postcondition  label has been changed to Level 1 and Score 0, 
     * the new game button has been deleted
     */
    public void reset()
    {
        frame.getContentPane().remove(label);
        label = new JLabel("<html>Level: 1<br>Score: 0</html>");
        frame.add(label, BorderLayout.NORTH);
        frame.getContentPane().remove(button);
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public void setGrid(MyBoundedGrid<Block> grid)
    {
        board = grid;
    }
}
