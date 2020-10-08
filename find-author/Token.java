
/**
 * A class that represents a section of text in a textfile
 * There are 6 types of tokens
 *  1. A 'word' which is defined as a non-empty sequence of characters that 
 *     begins with an alpha character and then consists of alpha characters, 
 *     numbers, the single quote character "'", or the hyphen character "-". 
 *  2. An 'end-of-sentence' delimiter defined as any one of the characters 
 *     ".", "?", "!".
 *  3. An end-of-file token which is returned when the scanner is asked for a
 *     token and the input is at the end-of-file.
 *  4. A phrase separator which consists of one of the characters ",",":" or
 *     ";".
 *  5. A digit.
 *  6. Any other character not defined above, ie, unknown
 * 
 * @author Ellen Guo
 * @version 2/26/19
 */
public final class Token
{
    private Scanner.TOKEN_TYPE type;
    private String token;
    
    /**
     * Constructs a token with ta given type and string
     * 
     * @param type the type of token, one of six types
     * @param s the token content
     */
    public Token(Scanner.TOKEN_TYPE type, String s)
    {
        this.type = type;
        token = s;
    }

    /**
     * Getter for the token type
     * 
     * @return the type (1 of 6 types)
     */
    public Scanner.TOKEN_TYPE getType()
    {
        return type;
    }

    /**
     * Getter for the token content
     * 
     * @return the token content as a String
     */
    public String getToken()
    {
        return token;
    }

    
    @Override
    /**
     * Expresses the token as a String, with type and token content
     * 
     * @return the String representation of the token
     */
    public String toString()
    {
        String s = "" + type + ": " + token;
        return s;
    }

    @Override
    /**
     * Overrides the Object equals method an tells if this token equals a given object
     * 
     * @param t the given object
     * @return true if the objects are equal; that is, if they have the same string representation
     *          false otherwise
     */
    public boolean equals(Object t)
    {
        return toString().equals(((Token)t).toString());
    }

}
