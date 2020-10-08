import java.util.*;
/**
 * Document directs the scanning and parsing (recursive descent) of a document
 * by using a Scanner and storing Sentence objects from the document 
 * (see Sentence class) in an internal data structure that can be traversed and accessed. 
 * 
 * @author Ellen Guo
 * @version 3/26/19
 */
public class Document
{
    // instance variables - replace the example below with your own
    private Scanner scan;
    private Token current;
    private List<Sentence> sentences;

    /**
     * Constructor for objects of class Document
     * 
     * @param s the Scanner that tokenizes the document we are parsing
     */
    public Document(Scanner s)
    {
        scan = s;
        sentences = new ArrayList<Sentence>();
        current = scan.nextToken();
    }

    /**
     * updates the current Token instance variable with the next Token
     * from the Scanner instance variable
     * O(1)
     * 
     * @postcondition the current Token has been updated accordingly
     */
    private void getNextToken()
    {
        current = scan.nextToken();
    }

    /**
     * Advances the input stream if the given Token matches the current Token instance variable
     * otherwise, throws a RuntimeException that states the un-eat()-able Token 
     * O(1)
     * 
     * @param t the Token to eat
     */
    private void eat(Token t)
    {
        if (t.equals(current))
        {
            getNextToken();
        }
        else
        {
            throw new RuntimeException("Cannot eat " + t);
        }
    }

    /**
     * Adds the current Token to a Phrase object if it is of type WORD
     * continues eat()-ing (recursively) until the Token is of type 
     * END_OF_PHRASE, END_OF_FILE, or END_OF_SENTENCE
     * other Token types are not considered and thus skipped
     * O(n)
     * 
     * @param t the Token to start at 
     * 
     * @return a Phrase of WORD Tokens
     */
    private Phrase parsePhrase(Token t)
    {
        Phrase phrase = new Phrase();
        return parsePhraseHelper(t, phrase);
    }

    /**
     * recursive helper method for parsePhrase; adds all WORD Tokens following the given Token
     * to the given Phrase
     * 
     * @param t the Token to look at
     * @param p the Phrase to add the following Tokens to
     * 
     * @return a Phrase of WORD Tokens
     */
    private Phrase parsePhraseHelper(Token t, Phrase p)
    {
        Scanner.TOKEN_TYPE type = t.getType();
        if (type.equals(Scanner.TOKEN_TYPE.END_OF_PHRASE) 
            || type.equals(Scanner.TOKEN_TYPE.END_OF_FILE) 
            || type.equals(Scanner.TOKEN_TYPE.END_OF_SENTENCE))
        {
            return p;
        }
        if (type.equals(Scanner.TOKEN_TYPE.WORD))
        {
            p.add(t);
        }
        eat(t);
        return parsePhraseHelper(current, p);
    }

    /**
     * Calls parsePhrase() until the end of a sentence or end of the file is reached
     * adds the return of the parsePhrase() into a Sentence object
     * @return this Sentence of Phrase objects
     * O(n)
     */
    private Sentence parseSentence()
    {
        Sentence sent = new Sentence();
        while (!(current.getType().equals(Scanner.TOKEN_TYPE.END_OF_FILE) 
            || current.getType().equals(Scanner.TOKEN_TYPE.END_OF_SENTENCE)))
        {
            if (current.getType().equals(Scanner.TOKEN_TYPE.END_OF_PHRASE))
                eat(current);
            Phrase p = parsePhrase(current);
            sent.add(p);
        }
        return sent;
    }

    /**
     * Calls parseSentence() until the end of the file is reached
     * adds the return of the parseSentence() into the internal data structure
     * skips any leading Token objects that aren’t of type WORD
     * O(n)
     * 
     * @postcondition the instance field sentences is filled with Sentence objects 
     *      that have been parsed
     */
    private void parseDocument()
    {
        while (!current.getType().equals(Scanner.TOKEN_TYPE.END_OF_FILE))
        {
            if (!current.getType().equals(Scanner.TOKEN_TYPE.WORD))
            {
                eat(current);
            }
            else
                sentences.add(parseSentence());
        }
    }

    /**
     * Public getter for all the sentences in the document
     * @return a shallow pointer to the internal data structure, not a new copy
     * O(n)
     */
    public List<Sentence> getSentences()
    {
        parseDocument();
        return sentences;
    }
}