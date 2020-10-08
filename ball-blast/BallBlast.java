import java.util.*;
import java.util.stream.Collectors; 
import java.awt.*;
import java.awt.event.*;

/**
 * The game of BallBlast! Based on the mobile game
 * 
 * @author Ellen Guo
 * @author Emma Dionne
 * @version 5/29/19
 */
public class BallBlast extends KeyAdapter 
{
    private ShapeDisplay display;
    private int counter;
    private Cannon cannon;
    private int score;
    private static final int BULLET_VALUE = 1;
    private static final int CANNON_SPEED = 25;
    private static final int BALL_CANNON_DIFF = 10;

    /**
     * Constructor for objects of the BallBlast class
     * Instantiates a shape display 
     * Creates the walls in the game
     * Starts playing the game
     */
    public BallBlast() 
    {
        display = new ShapeDisplay();
        display.setTitle("Ball Blast");
        display.addKeyListener(this);

        cannon = new Cannon();
        display.add(cannon);

        Shape floor = new Wall(0, 970, 500, 30);
        display.add(floor);
        Shape left = new Wall(0, 0, 30, 1000);
        display.add(left);
        Shape right = new Wall(470, 0, 30, 1000);
        display.add(right);
        Shape top = new Wall(0, 0, 1000, 30);
        display.add(top);

        counter = 0;

        display.repaint();
        System.out.println("Welcome to BallBlast!");
        play();
    }

    /**
     * Plays the game of BallBlast!
     * Bullets are constantly spawned in groups of 3
     * Every 300 bullets a ball is spawned, random size and random value
     * Collisions between balls, bullets, walls, and cannon are checked
     */
    public void play()
    {
        boolean flag = true;
        while (flag)
        {
            double x = cannon.getX();
            double y = cannon.getY();

            Bullet b1 = new Bullet(x, y);
            Bullet b2 = new Bullet(x + cannon.getWidth()/2, y);
            Bullet b3 = new Bullet(x + cannon.getWidth(), y);

            display.add((Shape)b1);
            display.add((Shape)b2);
            display.add((Shape)b3);

            counter += 3;
            if (counter > 300)
            {
                Color color = new Color ((int)(Math.random()*256), 
                    (int)(Math.random()*256), (int)(Math.random()*256));
                int initial = (int)(Math.random() *200) + 50;
                int size = (int)(Math.random() * 3) + 1;
                int diameter;
                if (size == 1)
                {
                    diameter = 50;
                }
                else if (size == 2)
                {
                    diameter = 100;
                }
                else
                {
                    diameter = 200;
                }
                int side;
                int xVel;
                int randomSide = (int)(Math.random() * 2);
                if (randomSide == 0)
                {
                    side = 0;
                    xVel = CANNON_SPEED - BALL_CANNON_DIFF;
                }
                else
                {
                    side = 500;
                    xVel = -(CANNON_SPEED - BALL_CANNON_DIFF);
                }
                Ball ball = new Ball(color, side, (int) Math.random()*500, 
                    diameter, diameter, size, initial);
                ball.setXVel(xVel);
                display.add(ball);
                ball.move();
                counter = 0;
            }

            ArrayList<Shape> list = display.getArrayList();
            for (Shape s : list)
            {
                s.move();
            }

            flag = checkCollisions(list);

            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e) 
            {
                System.out.print("");
            }

            display.repaint();
        }
        
        Iterator<Shape> it = display.shapes();
        while (it.hasNext())
        {
            Shape s = it.next();
            if (s instanceof Bullet)
            {
                it.remove();
            }
        }
        System.out.println("GAME OVER! Your score was: " + score);
    }

    /**
     * Looks at every combination of shapes and sees if they collide
     * If some shapes need to be removed or added, they are done after checking each combination
     * 
     * @param list the list of all shapes -- checks these shapes for collisions
     * @return boolean - returns false if the game ends (hits the cannon), true otherwise
     */
    private boolean checkCollisions(ArrayList<Shape> list)
    {
        ArrayList<Integer> removed = new ArrayList<Integer>();
        ArrayList<Shape> adds = new ArrayList<Shape>();
        boolean end = true;
        for (int i = 0; i < list.size(); i++)
        {
            for (int j = 0; j < list.size(); j++)
            {
                if (i == j)
                {
                    continue;
                }
                if (!handleCollision(removed, adds, list, i, j))
                {
                    end = false;
                }
            }
        }

        Collections.sort(removed);
        java.util.List<Integer> noDupsRemoved = 
            removed.stream().distinct().collect(Collectors.toList());
        for (int i = noDupsRemoved.size() - 1; i >= 0; i--)
        {
            list.remove((int)noDupsRemoved.get(i));
        }

        for (Shape s : adds)
        {
            list.add(s);
        }
        return end;
    }

    /**
     * Sees if two given shapes are colliding
     * definition of a collision: if the top, bottom, left, and 
     * right points of Shape 2 are contained within Shape 1
     * 
     * @param one the first shape
     * @param two the second shape 
     * @return true if the two shapes collide, false otherwise
     */
    private boolean isCollision(Shape one, Shape two)
    {
        double x2 = two.getX();
        double y2 = two.getY();
        double h = two.getHeight();
        double w = two.getWidth();
        double topMost = y2;
        double bottomMost = y2 + h;
        double leftMost = x2;
        double rightMost = x2 + w;
        if (containsPoint(one, x2 + (w/2), topMost) || containsPoint(one, x2 + (w/2), bottomMost) ||
            containsPoint(one, leftMost, y2 + (h/2)) || containsPoint(one, rightMost, y2 + (h/2)))
        {
            return true;
        }
        return false;
    }

    /**
     * Sees if a given point is within a given shape
     * 
     * @param shape the given shape
     * @param x2 the x value of the point
     * @param y2 the y value of the point
     * @return true if the shape contains the point 
     */
    private boolean containsPoint(Shape shape, double x2, double y2) 
    {
        double leftBound = shape.getX();
        double rightBound = leftBound + shape.getWidth();
        double topBound = shape.getY();
        double bottomBound = topBound + shape.getHeight();
        if ((x2 >= leftBound && x2 <= rightBound) && (y2 >= topBound && y2 <= bottomBound))
        {
            return true;
        }
        return false;
    }

    /**
     * checks if a collision exists between two given shapes; if they collide, handles the collision
     * When wall collides with a bullet, the bullet is added to the "to be deleted" list
     *      ie. the bullet should disappear
     * When ball collides with cannon, return false
     * When ball collides with bullet, the bullet is added to the 
     *      "to be deleted" list (bullet should disappear)
     *      the ball's value decreases; if the ball's value gets to 
     *      0 and the ball is greater than size 1, 
     *      the ball splits into two balls, same color, half the value 
     *      and one size down. They are put in the 
     *      "to be added" list. If the size is 1 (smallest size) the ball disappears
     * If the ball hits the wall, it bounces
     * 
     * @param removed the "to be deleted" list
     * @param adds the "to be added" list
     * @param list the list of shapes
     * @param onedex the index in list of the first shape
     * @param twodex the index in the list of the second shape
     * 
     * @return false if the cannon is hit by the ball, true otherwise
     */
    private boolean handleCollision(ArrayList<Integer> removed, ArrayList<Shape> adds, 
    ArrayList<Shape>list, int onedex, int twodex)
    {
        Shape s1 = list.get(onedex);
        Shape s2 = list.get(twodex);
        if (s1 instanceof Wall && s2 instanceof Bullet && isCollision(s1, s2))
        {
            removed.add(twodex);
        }
        else if (s1 instanceof Ball)
        {
            if (s2 instanceof Cannon)
            {
                if (isCollision(s2, s1))
                {
                    return false;
                }
            }
            else if (s2 instanceof Bullet && isCollision(s1, s2))
            {
                removed.add(twodex);
                ((Ball)s1).setNum(((Ball)s1).getNum() - BULLET_VALUE);
                if (((Ball)s1).getNum() == 0)
                {
                    score += ((Ball) s1).getInitial();
                    System.out.println("Score: " + score);
                    removed.add(onedex);
                    if (((Ball)s1).size() > 1)
                    {
                        Color color = new Color ((int)(Math.random()*256), 
                            (int)(Math.random()*256), (int)(Math.random()*256));

                        Shape b1 = new Ball(color, s1.getX(), s1.getY(), s1.getWidth()/2, 
                                s1.getHeight()/2, ((Ball)s1).size() - 1, ((Ball)s1).getInitial()/2);
                        b1.setXVel(20);

                        Shape b2 = new Ball(color, s1.getX(), s1.getY(), s1.getWidth()/2, 
                                s1.getHeight()/2, ((Ball)s1).size() - 1, ((Ball)s1).getInitial()/2);
                        b2.setXVel(-20);

                        adds.add(b1);
                        adds.add(b2);
                    }
                }
            }
            else if (s2 instanceof Wall)
            {
                double topMost = s1.getY();
                double bottomMost = s1.getY() + s1.getHeight();
                double leftMost = s1.getX();
                double rightMost = s1.getX() + s1.getWidth();

                if (leftMost <= 30)
                {
                    s1.setXVel(Math.abs(s1.getXVel()));
                }
                if (bottomMost >= 970)
                {
                    s1.setYVel(-1 * Math.abs(s1.getYVel()));
                }
                if (topMost <= 30)
                {
                    s1.setYVel(Math.abs(s1.getYVel()));
                }
                if (rightMost >= 470)
                {
                    s1.setXVel(-1 * Math.abs(s1.getXVel()));
                }
            }
        }
        return true;
    }

    /**
     * When the left or right key is pressed, the cannon moves, 
     * but the cannon cannot move past the walls
     * 
     * @param e the key press event
     */
    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT)
        {
            cannon.setX(cannon.getX() - CANNON_SPEED);
        }
        if (keyCode == KeyEvent.VK_RIGHT)
        {
            cannon.setX(cannon.getX() + CANNON_SPEED);
        }
        if (cannon.getX() > 420)
        {
            cannon.setX(420);
        }
        if (cannon.getX() < 30)
        {
            cannon.setX(30);
        }
    }
}
