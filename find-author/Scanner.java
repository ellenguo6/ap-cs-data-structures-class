import java.io.IOException;
import java.io.Reader;

/**
 * A Scanner is responsible for reading an input stream, one character at a
 * time, and separating the input into tokens.  A token is defined as:
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
 *  6. Any other character not defined above.
 * @author Mr. Page
 * @author Ellen Guo
 * 
 * @version 2/3/19
 * @version 2/26/19
 *
 */

public class Scanner
{
    private Reader in;
    private String currentChar;
    private boolean endOfFile;
    // define symbolic constants for each type of token
    /**
     * All 6 possible constants for each type of token
     */
    public static enum TOKEN_TYPE 
    {
        /**
         * See definition #1 of a token in Scanner class header
         */
        WORD, 

        /**
         * See definition #2 of a token in Scanner class header
         */
        END_OF_SENTENCE, 

        /**
         * See definition #3 of a token in Scanner class header
         */
        END_OF_FILE, 

        /**
         * See definition #4 of a token in Scanner class header
         */
        END_OF_PHRASE, 

        /**
         * See definition #5 of a token in Scanner class header
         */
        DIGIT, 

        /**
         * See definition #6 of a token in Scanner class header
         */
        UNKNOWN
    }

    /**
     * Constructor for Scanner objects.  The Reader object should be one of
     *  1. A StringReader
     *  2. A BufferedReader wrapped around an InputStream
     *  3. A BufferedReader wrapped around a FileReader
     *  The instance field for the Reader is initialized to the input parameter,
     *  and the endOfFile indicator is set to false.  The currentChar field is
     *  initialized by the getNextChar method.
     * @param in is the reader object supplied by the program constructing
     *        this Scanner object.
     */
    public Scanner(Reader in)
    {
        this.in = in;
        endOfFile = false;
        getNextChar();
    }

    /**
     * The getNextChar method attempts to get the next character from the input
     * stream.  It sets the endOfFile flag true if the end of file is reached on
     * the input stream.  Otherwise, it reads the next character from the stream
     * and converts it to a Java String object.
     * postcondition: The input stream is advanced one character if it is not at
     * end of file and the currentChar instance field is set to the String 
     * representation of the character read from the input stream.  The flag
     * endOfFile is set true if the input stream is exhausted.
     */
    private void getNextChar() 
    {
        try
        {
            int inp = in.read();
            if(inp == -1) 
            {
                endOfFile = true;
            }
            else 
                currentChar = "" + (char) inp;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Advances the input stream if the given string matches the current character
     * 
     * @param s the current string
     */
    private void eat(String s)
    {
        if (s.equals(currentChar))
        {
            getNextChar();
        }
        else 
        {
            throw new IllegalArgumentException("CANNOT EAT" + "\"" + s + "\"");
        }
    }

    /**
     * Tells if a given string is a single letter
     * 
     * @param s the given string
     * @return true if the given string is A-Z or a-z
     *          false otherwise
     */
    private boolean isLetter(String s)
    {
        return (s.compareTo("A") >= 0 && s.compareTo("Z") <= 0) || 
            (s.compareTo("a") >= 0 && s.compareTo("z") <= 0);
    }

    /**
     * Tells if a given string is a single digit number
     * 
     * @param s the given string
     * @return true if the given string is 0-9
     *          false otherwise
     */
    private boolean isDigit(String s)
    {
        return s.compareTo("0") >= 0 && s.compareTo("9") <= 0;
    }

    /**
     * Tells if a given string is a special character, ie single quotes "'" or a dash "-"
     * 
     * @param s the given string
     * @return true if the given string is a special character
     *          false otherwise
     */
    private boolean isSpecial(String s)
    {
        return s.equals("'") || s.equals("-");
    }

    /**
     * Tells if a given string is a phrase terminator, ie comma "," semicolon ";" or colon ":"
     * 
     * @param s the given string
     * @return true if the given string is a phrase terminator
     *          false otherwise
     */
    private boolean isPhraseTerm(String s)
    {
        return s.equals(",") || s.equals(";") || s.equals(":");
    }

    /**
     * Tells if a given string is a sentence terminator, ie period "." 
     * exclamation mark "!" or question mark "?"
     * 
     * @param s the given string
     * @return true if the given string is a phrase terminator
     *          false otherwise
     */
    private boolean isSentTerm(String s)
    {
        return s.equals(".") || s.equals("!") || s.equals("?");
    }

    /**
     * Tells if a given string is a whitespace, ie a single space, a new line, a tab, or 
     * carriage return
     * 
     * @param s the given string
     * @return true if the given string is a phrase terminator
     *          false otherwise
     */
    private boolean isWhitespace(String s)
    {
        return s.equals(" ") || s.equals("\n") || s.equals("\t") || s.equals("\r");
    }

    /**
     * Tells there are more tokens left in the file; that is, you haven't reached the end
     * 
     * @return true if there are more left
     *          false otherwise
     */
    public boolean hasNextToken()
    {
        return !endOfFile;
    }

    /**
     * Gets the next token in the input stream; see class header for token definitions
     * 
     * @return the token
     */
    public Token nextToken()
    {
        if (endOfFile)
        {
            return new Token(TOKEN_TYPE.END_OF_FILE, currentChar);
        }
        Token t = null;
        while (hasNextToken() && isWhitespace(currentChar))
        {
            eat(currentChar);
        }
        if (isSentTerm(currentChar))
        {
            t = new Token(TOKEN_TYPE.END_OF_SENTENCE, currentChar);
            eat(currentChar);
        }
        else if (isPhraseTerm(currentChar))
        {
            t = new Token(TOKEN_TYPE.END_OF_PHRASE, currentChar);
            eat(currentChar);
        }
        else if (isDigit(currentChar))
        {
            t = new Token(TOKEN_TYPE.DIGIT, currentChar);
            eat(currentChar);
        } 
        else if (isLetter(currentChar))
        {
            String s = "";
            while (hasNextToken() && 
                (isLetter(currentChar) || isSpecial(currentChar) || isDigit(currentChar)))
            {
                s += currentChar;
                eat(currentChar);
            }
            t = new Token(TOKEN_TYPE.WORD, s);
        }
        else 
        {
            t = new Token(TOKEN_TYPE.UNKNOWN, currentChar);
            eat(currentChar);
        }
        return t;
    }
}
