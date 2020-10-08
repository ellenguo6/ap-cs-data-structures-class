import java.util.*;

/**
 * MyBoundedGrid uses a 2D array to represent a finite grid of objects
 * Can:
 *      tell if a given location is a valid location in the gird
 *      put an object in a given location
 *      removes the object at a given location
 *      get the object at a given location
 *      return a list of all occupied locations
 * 
 * @author Ellen Guo
 * @version 3/6/19
 * 
 * @param <E> the type of object in the grid
 */
public class MyBoundedGrid<E>
{
    private Object[][] grid;

    /**
     * Constructor for objects of class MyBoundedGrid
     * 
     * @param rows the number of rows in the bounded grid
     * @param cols the number of columns in the bounded grid
     */
    public MyBoundedGrid(int rows, int cols)
    {
        grid = new Object[rows][cols];
    }

    /**
     * Getter for the number of rows
     * 
     * @return the number of rows
     */
    public int getNumRows() 
    {
        return grid.length;
    }
    
    /**
     * Getter for the number of columns
     * 
     * @return the number of columns
     */
    public int getNumCols()
    {
        return grid[0].length;
    }
    
    /**
     * Tells if a given location is valid within the boundaries of the grid
     * 
     * @param loc the given lcoation
     * @return true if the location is valid, false otherwise
     */
    public boolean isValid(Location loc)
    {
        int row = loc.getRow();
        int col = loc.getCol();
        return row >= 0 && row < getNumRows() && col >= 0 && col < getNumCols();
    }
    
    /**
     * Puts a given object in the given location (if the location is valid) in the grid
     * 
     * @param loc the given location to put the object
     * @param obj the object to put
     * @postcondition the given location in the grid now holds the given object
     * @return the object that was originally in the given location; null if nothing was there 
     *      or location was invalid
     */
    public E put (Location loc, E obj)
    {
        if (isValid(loc))
        {
            int row = loc.getRow();
            int col = loc.getCol();
            E temp = (E) grid[row][col];
            grid[row][col] = obj;
            return temp;
        }
        return null;
    }
    
    /**
     * Removes the object at a given location in the grid
     * 
     * @param loc the given location
     * @postcondition the given location in the grid now holds a null
     * @return the object that was at that location; null if nothing was there 
     *      or the location was invalid
     */
    public E remove(Location loc)
    {
        if (isValid(loc))
        {
            int row = loc.getRow();
            int col = loc.getCol();
            E temp = (E) grid[row][col];
            grid[row][col] = null;
            return temp;
        }
        return null;
    }
    
    /**
     * Gets the object at a given location in the grid (if it's valid)
     * 
     * @param loc the given location from which to get the object
     * @return the object in that location; 
     *      null if the location is invalid or there is nothing there
     */
    public E get(Location loc)
    {
        if (isValid(loc))
        {
            int row = loc.getRow();
            int col = loc.getCol();
            return (E) grid[row][col];
        }
        return null;
    }
    
    /**
     * Gets a list of all the locations in the grid that have objects
     * 
     * @return a list of the locations in the grid that contain nulls
     */
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> list = new ArrayList<Location>();
        for (int r = 0; r < grid.length; r++)
        {
            for (int c = 0; c < grid[0].length; c++)
            {
                if (grid[r][c] != null)
                {
                    list.add(new Location(r, c));
                }
            }
        }
        return list;
    }
}
