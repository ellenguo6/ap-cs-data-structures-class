import java.util.*;
/**
 * Phrase represents a sentence phrase, i.e. a collection of words separated within a Sentence 
 * (see Sentence class) by colons, commas, or semicolons. 
 * The Phrase class contains an internal data structure of Token objects (words)
 * that can be copied, printed, and changed (by adding new words).
 * 
 * @author Ellen Guo
 * @version 3/25/29
 */
public class Phrase
{
    // instance variables - replace the example below with your own
    private List<Token> tokens;

    /**
     * Constructor for objects of class Phrase
     * Initializes the List of tokens
     */
    public Phrase()
    {
        tokens = new ArrayList<Token>();
    }

    /**
     * adds a given Token to the internal data structure
     * O(1)
     * 
     * @param t the Token to be added
     * @postcondition the given token has been appended to the end of the List
     */
    public void add(Token t)
    {
        tokens.add(t);
    }

    /**
     * Copies the current list; not a shallow copy, creates new instances of each element
     * O(n)
     * 
     * @return a copy of the internal data structure of Token objects 
     * by traversing the structure and adding each element to a new List
     */
    public List<Token> copy()
    {
        List<Token> copy = new ArrayList<Token>();
        for (Token t: tokens)
        {
            copy.add(new Token(t.getType(), t.getToken()));
        }
        return copy;
    }

    /**
     * Gets a String representation of this Phrase, 
     * which is a bracketed list of the Token objects separated by commas
     * O(n)
     * 
     * @return the String version of this Phrase
     */
    public String toString()
    {
        return tokens.toString();
    }
}
