import java.util.*;
import java.awt.Color;
import java.util.concurrent.Semaphore;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * The classic tile-matching puzzle Soviet video game of Tetris!
 * Create an instance of this class to start playing.
 * 
 * @author Ellen Guo
 * @version 3/26/19
 */
public class Tetris implements ArrowListener, MouseListener
{
    private MyBoundedGrid<Block> grid;
    private BlockDisplay display;
    private Tetrad active;
    private Semaphore lock;
    private boolean gameOver; 
    private int count;
    private int score;
    private int level;
    private int speed;
    private int rowsCleared;

    /**
     * Constructor for objects of class Tetris
     * Instantiates all instance variables
     * calls the method play to begin the game
     */
    public Tetris()
    {
        grid = new MyBoundedGrid(20, 10);
        count = 0;
        score = 0;
        level = 1;
        speed = 1000;
        rowsCleared = 0;
        display = new BlockDisplay(grid);
        lock = new Semaphore(1, true);
        gameOver = false;
        display.setTitle("Tetris");
        display.setButtonListener(this);
        display.setArrowListener(this);
        display.showBlocks();
        active = new Tetrad(grid);
        play();
    }

    /**
     * One of the 7 different 4-block shapes that can be generated in a tetris game
     * 
     * @author Ellen Guo
     * @version 3/26/19
     */
    public class Tetrad
    {
        private Block[] blocks;
        private MyBoundedGrid<Block> gr;

        /**
         * Constructor for objects of class Tetrad
         * 
         * @param grid the grid that the Tetrad will be placed in
         * 
         * randomly chooses one of 7 shapes: I, T, O, L, J, S, Z and adds blocks to the 
         * appropriate locations
         * 
         * attempts to move downward one block; if this cannot be accomplished, the game is over
         */
        public Tetrad(MyBoundedGrid<Block> grid)
        {
            gr = grid;
            count++;
            int random = (int)(Math.random() * 7);
            blocks = new Block[4];
            for (int i = 0; i < blocks.length; i++)
            {
                blocks[i] = new Block();
            }
            Location[] locs = null;
            if (random == 0) // I shape
            {
                locs = new Location[]{new Location(1, 4), new Location(0, 4), 
                    new Location(2, 4), new Location(3, 4)};
                for (int i = 0; i < blocks.length; i++)
                {
                    blocks[i].setColor(Color.RED);
                }
            } 
            else if (random == 1) // T shape
            {
                locs = new Location[]{new Location(0, 5), new Location(0, 4), 
                    new Location(0, 6), new Location(1, 5)};
                for (Block b: blocks)
                {
                    b.setColor(Color.GRAY);
                }
            } 
            else if (random == 2) // O shape
            {
                locs = new Location[]{new Location(0, 4), new Location(0, 5), 
                    new Location(1, 4), new Location(1, 5)};
                for (Block b: blocks)
                {
                    b.setColor(Color.CYAN);
                }
            } 
            else if (random == 3) // L shape
            {
                locs = new Location[]{new Location(1, 4), new Location(0, 4), 
                    new Location(2, 4), new Location(2, 5)};
                for (Block b: blocks)
                {
                    b.setColor(Color.YELLOW);
                }
            } 
            else if (random == 4) // J shape
            {
                locs = new Location[]{new Location(1, 5), new Location(0, 5), 
                    new Location(2, 4), new Location(2, 5)};
                for (Block b: blocks)
                {
                    b.setColor(Color.MAGENTA);
                }
            } 
            else if (random == 5) // S shape
            { 
                locs = new Location[]{new Location(0, 4), new Location(0, 5), 
                    new Location(1, 3), new Location(1, 4)};
                for (Block b: blocks)
                {
                    b.setColor(Color.BLUE);
                }
            } 
            else // Z shape
            {
                locs = new Location[]{new Location(0, 5), new Location(0, 4), 
                    new Location(1, 5), new Location(1, 6)};
                for (Block b: blocks)
                {
                    b.setColor(Color.GREEN);
                }
            } 
            addToLocations(gr, locs);
            gameOver = !translate(1, 0);
        }

        /**
         * Adds the blocks of this Tetrad to the given locations in a given grid
         * 
         * @precondition blocks are not in any grid; locs.length = 4
         * @postcondition the locations of blocks match locs, and blocks have been
         *      put in the grid
         * 
         * @param g the grid you're adding the blocks to
         * @param locs the locations wthin the grid you're adding the blocks to
         */
        private void addToLocations(MyBoundedGrid<Block> g, Location[] locs)
        {
            for (int i = 0; i < blocks.length; i++)
            {
                blocks[i].putSelfInGrid(g, locs[i]);
            }
        }

        /**
         * Removes the blocks of this Tetrad from the grid
         * 
         * @precondition Blocks are in the grid.
         * @return the old locations of blocks; blocks have been removed from grid.
         */
        private Location[] removeBlocks()
        {
            Location[] locs = new Location[4];
            for (int i = 0; i < 4; i++)
            {
                locs[i] = blocks[i].getLocation();
                blocks[i].removeSelfFromGrid();
            }
            return locs;
        }

        /**
         * Checks if the given locations in a given grid are empty
         * 
         * @param g the grid in which we are checking 
         * @param locs the locations we are checking
         * @return true if each of locs is valid and empty in grid;
         *      false otherwise.
         */
        private boolean areEmpty(MyBoundedGrid<Block> g, Location[] locs)
        {
            for (Location l:locs)
            {
                if (!(g.isValid(l) && g.get(l) == null))
                    return false;
            }
            return true;
        }

        /**
         * Attempts to move this tetrad deltaRow
         * rows down and deltaCol columns to the right, if those positions are valid
         * and empty
         * 
         * @param deltaRow the number of rows you're moving to the down (negative 
         *      numbers move up)
         * @param deltaCol the number of columns you're moving to the right (negative 
         *      numbers move to the left)
         * @return true if successful and false otherwise.
         */
        public boolean translate(int deltaRow, int deltaCol)
        {
            try
            {
                lock.acquire();
                Location[] locs = new Location[4];
                for (int i = 0; i < 4; i++)
                {
                    Location orig = blocks[i].getLocation();
                    locs[i] = new Location(orig.getRow() + deltaRow, //***nullpointer
                        orig.getCol() + deltaCol);
                }
                Location[] old = removeBlocks();
                if (areEmpty(gr, locs))
                {
                    addToLocations(gr, locs);
                    return true;
                }
                addToLocations(gr, old);
                return false;
            }
            catch (InterruptedException e)
            {
                // did not modify the tetrad
                return false;
            }
            finally
            {
                lock.release();
            }

        }

        /**
         * Attempts to rotate this tetrad clockwise by 90 degrees
         *      about its center, if the necessary positions are empty; 
         * @return true if successful and false otherwise
         */
        public boolean rotate()
        {
            try
            {
                lock.acquire();
                Location[] locs = new Location[4];
                for (int i = 0; i < locs.length; i++)
                {
                    Location l = blocks[i].getLocation();
                    Location first = blocks[0].getLocation();
                    locs[i] = new Location(first.getRow() - first.getCol() + l.getCol(),
                        first.getRow() + first.getCol() - l.getRow());
                }
                Location[] old = removeBlocks();
                if (areEmpty(gr, locs))
                {
                    addToLocations(gr, locs);
                    return true;
                }
                addToLocations(gr, old);
                return false;
            }
            catch (InterruptedException e)
            {
                //did not modify the tetrad
                return false;
            }
            finally
            {
                lock.release();
            }

        }

        /**
         * Creates a string representation of the Tetrad by getting the string
         * representations of each block
         * 
         * @return the string version of the Tetrad
         */
        public String toString()
        {
            String s = "";
            for (Block b : blocks)
            {
                String add = b.toString() + " ";
                s += add;
            }
            return s;
        }
    }

    /**
     * Response of this ArrowListener to when the up arrow is pressed
     * Rotates the active Tetrad 
     * 
     * @postcondition the current Tetrad has been rotated and the display updated
     */
    public void upPressed()
    {
        if (active != null)
        {
            active.rotate();
            display.showBlocks();
        }
    }

    /**
     * Response of this ArrowListener to when the down arrow is pressed
     * Translates the active Tetrad down 1 block (soft drop)
     * 
     * @postcondition the current Tetrad has been translated down and the display updated
     */
    public void downPressed()
    {
        if (active != null)
        {
            active.translate(1, 0);
            display.showBlocks();
        }
    }

    /**
     * Response of this ArrowListener to when the left arrow is pressed
     * Translates the active Tetrad left 1 block
     * 
     * @postcondition the current Tetrad has been translated left and the display updated
     */
    public void leftPressed()
    {
        if (active != null)
        {
            active.translate(0, -1);
            display.showBlocks();
        }
    }

    /**
     * Response of this ArrowListener to when the right arrow is pressed
     * Translates the active Tetrad right 1 block
     * 
     * @postcondition the current Tetrad has been translated right and the display updated
     */
    public void rightPressed()
    {
        if (active != null)
        {
            active.translate(0, 1);
            display.showBlocks();
        }
    }

    /**
     * Response of this ArrowListener to when the spacebar is pressed
     * Hard drops the current Tetrad all the way down (hard drop)
     * 
     * @postcondition the current Tetrad has been dropped as far down as it can go 
     *      and the display updated
     */
    public void spacePressed()
    {
        if (active != null)
        {
            while (active.translate(1, 0)){}
        }
        display.showBlocks();
    }

    /**
     * Response of this MouseListener to when the corresponding button (see MyBoundedGrid)
     * has been clicked
     * Starts a new game
     * 
     * @param e the mouseclick from the button
     * @postcondition a new display has been created, the entire game reset
     */
    public void mouseClicked(MouseEvent e)
    {
        if (gameOver)
        {
            System.out.println("newgame");
            gameOver = false;
            for (int r = 0; r < grid.getNumRows(); r++)
            {
                for (int c = 0; c < grid.getNumCols(); c++)
                {
                    Block b = grid.get(new Location(r, c));
                    if (b != null)
                        b.removeSelfFromGrid();
                }
            }
            count = 0;
            score = 0;
            level = 1;
            speed = 1000;
            rowsCleared = 0;
            updateText();
            active = new Tetrad(grid);
            display.setGrid(grid);
            //display.reset();
            display.showBlocks();
            //play();
        }
    }

    /**
     * Does nothing
     * 
     * @param e when the mouse enters the button 
     */
    public void mouseEntered(MouseEvent e)
    {
        return;
    }

    /**
     * Does nothing
     * 
     * @param e when the mouse exits the button
     */
    public void mouseExited(MouseEvent e)
    {
        return;
    }

    /**
     * Does nothing
     * 
     * @param e when the mouse is pressed on the button
     */
    public void mousePressed(MouseEvent e)
    {
        return;
    }

    /**
     * Does nothing
     * 
     * @param e when the mouse is released from the button
     */
    public void mouseReleased(MouseEvent e)
    {
        return;
    }

    /**
     * Checks if the given row has blocks in every cell
     * 
     * @param row the row to check
     * @precondition 0 <= row < number of rows
     * @return true if every cell in the given row is occupied; 
     *      false otherwise
     */
    private boolean isCompletedRow(int row)
    {
        for (int i = 0; i < grid.getNumCols(); i++)
        {
            Location loc = new Location(row, i);
            if (grid.get(loc) == null)
                return false;
        }
        return true;
    }

    /**
     * Clears the given row by moving all rows above it down one row, 
     * and setting the top row all to null
     * 
     * @param row the row to clear
     * @precondition 0 <= row < number of rows; given row is full of blocks
     * @postcondition every block in the given row has been removed, and every block above row
     *          has been moved down one row
     */
    private void clearRow(int row)
    {
        for (int r = row - 1; r >= 0; r--) 
        {
            for (int c = 0; c < grid.getNumCols(); c++)
            {
                Location down = new Location(r + 1, c);
                grid.put(down, grid.get(new Location(r, c)));
            }
        }

        for (int c = 0; c < grid.getNumCols(); c++)
        {
            grid.put(new Location(0, c), null);
        }
    }

    /**
     * Traverses the entire grid and clears all the occupied rows
     * The score is updated depending on how many rows were cleared at once
     * The level is updated depending on the total number of rows cleared
     * The speed is updated depending on the level
     * 
     * @postcondition all complete rows have been cleared and the display updated
     */
    private void clearCompletedRows()
    {
        int rowsCompleted = 0;
        for (int r = grid.getNumRows() - 1; r >= 0; r--)
        {
            if (isCompletedRow(r))
            {
                clearRow(r);
                r++;
                rowsCompleted++;
                rowsCleared++;
                if (rowsCleared % 5 == 0)
                {
                    level++;
                    updateText();
                    speed *= 0.5;
                }
            }
        }
        if (rowsCompleted == 1)
        {
            score += 40 * level;
            updateText();
        } 
        else if (rowsCompleted == 2)
        {
            score += 100 * level;
            updateText();
        }
        else if (rowsCompleted == 3)
        {
            score += 300 * level;
            updateText();
        }
        else if (rowsCompleted >= 4)
        {
            score += 1200 * level;
            updateText();
        }
    }

    /**
     * Allows the game to proceed by translating the active Tetrad down 1 block per unit
     * of time. The higher the level, the faster the drop
     * Once the active Tetrad has fallen as far as it can go, this method checks to see if 
     * any rows can be cleared (and clears them). Then a new Tetrad is spawned and becomes
     * the current one
     * This method continues until the active Tetrad can't move when it is spawned; ie. 
     * game over. When the game is over, the display updates accordingly.
     */
    public void play()
    {
        while(!gameOver)
        {
            try
            {
                display.showBlocks();
                if (!active.translate(1, 0))
                {
                    clearCompletedRows();
                    active = new Tetrad(grid);
                }
                Thread.sleep(speed);
            }
            catch (InterruptedException e)
            {
                System.out.println("Interrupted Exception");
            }
        }
        active = null;
        display.lose(level, score);
    }

    /**
     * Updates the level and score JLabel in the display
     */
    private void updateText()
    {
        display.setText(level, score);
    }
}
