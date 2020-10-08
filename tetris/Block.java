import java.awt.Color;
/**
* class BLock encapsulates a Block abstraction which can be placed into a Gridworld style grid
* 
* @author Ellen Guo
* @version 3/6/19
*/
public class Block
{
    private MyBoundedGrid<Block> grid;
    private Location location;
    private Color color;
    /**
    * constructs a blue block, because blue is the greatest color ever!
    */
    public Block()
    {
        color = Color.BLUE;
        grid = null;
        location = null;
    }
    
    /**
    * Getter for the color of the block
    * 
    * @return the color of the block
    */
    public Color getColor()
    {
        return color;
    }
    
    /**
    * Setter to change the color of the block
    * 
    * @param newColor the new color for the block to be
    */
    public void setColor(Color newColor)
    {
        color = newColor;
    }
    
    /**
    * Getter for the grid that the block is encapsulated in
    * 
    * @return the grid
    */
    public MyBoundedGrid<Block> getGrid()
    {
        return grid;
    }
    
    /**
    * Getter for the location of the block in the grid
    * 
    * @return the location
    */
    public Location getLocation()
    {
        return location;
    }
    
    /**
    * Removes this block from the grid by setting its location and grid to null
    * 
    * @postcondition this block's location and grid are null and the grid has removed the 
    *       block from its array
    */
    public void removeSelfFromGrid()
    {
        grid.remove(location);
        grid = null;
        location = null;
    }
    
    /**
    * Puts this block into a given location in a given grid;
    * if another block is already in that location, it will remove itself
    * 
    * @postcondition this block and the grid have updated their variables 
    *           to account for the placement
    * @param gr the grid to place this block in
    * @param loc the location in the grid to put this block in
    */
    public void putSelfInGrid(MyBoundedGrid<Block> gr, Location loc)
    {
        if (gr != null && gr.isValid(loc))
        {
            Block temp = gr.get(loc);
            if (temp != null)
            {
                temp.removeSelfFromGrid();
            } 
            grid = gr;
            location = loc;
            grid.put(location, this);
        }
    }

    /**
    * Moves this block to a given location in its current gird 
    * 
    * @postcondition this block and its grid are updated to account for the movement
    * @param newLocation the new given location
    */
    public void moveTo(Location newLocation)
    {
        if (newLocation != location && grid != null && grid.isValid(newLocation))
        {
            MyBoundedGrid<Block> gridTemp = grid;
            removeSelfFromGrid();
            Block temp = gridTemp.get(newLocation);
            if (temp != null)
            {
                temp.removeSelfFromGrid();
            } 
            putSelfInGrid(gridTemp, newLocation);
        }
    }

    /**
    * returns a string with the location and color of this block
    * 
    * @return the string representation of this block
    */
    public String toString()
    {
        return "Block[location=" + location + ",color=" + color + "]";
    }
}