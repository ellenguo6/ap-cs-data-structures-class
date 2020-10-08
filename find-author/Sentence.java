import java.util.*;
/**
 * Sentence represents a sentence, i.e. a collection of phrases (see Phrase class) 
 * terminated by (but not including) “!” “?” “.” or the end of file, 
 * and without whitespace on either end. 
 * The Sentence class contains an internal data structure of Phrase objects of Token objects
 * that can be copied, printed, and changed (by adding new phrases).
 * 
 * @author Ellen Guo
 * @version 3/25/19
 */
public class Sentence
{
    // instance variables - replace the example below with your own
    private List<Phrase> phrases;

    /**
     * Constructor for objects of class Sentence
     * Initializes the List of Phrases
     */
    public Sentence()
    {
        phrases = new ArrayList<Phrase>();
    }

    /**
     * adds a given Phrase to the internal data structure
     * O(1)
     * 
     * @param p the Phrase to add
     * @postcondition the given Phrase has been appended to the end of the List
     */
    public void add(Phrase p)
    {
        phrases.add(p);
    }
    
    /**
     * Copies the current list; not a shallow copy, creates new instances of each element
     * (ie. new Phrase objects with new Token objects)
     * O(n)
     * 
     * @return a copy of the internal data structure of Phrase objects 
     * by traversing the structure and adding each element to a new List
     */
    public List copy()
    {
        List<Phrase> copy = new ArrayList<Phrase>();
        for (Phrase p: phrases)
        {
            Phrase newp = new Phrase();
            List<Token> copyp = p.copy();
            for (Token t: copyp)
                newp.add(t);
            copy.add(newp);
        }
        return copy;
    }
    
    /**
     * Gets a String representation of this Sentence, which is a bracketed list of 
     * Phrase objects separated by commas (so a bracketed list of bracketed lists of Token objects)
     * O(n)
     * 
     * @return the String version of this Phrase
     */
    public String toString()
    {
        return phrases.toString();
    }
}
