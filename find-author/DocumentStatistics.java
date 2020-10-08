import java.util.*;
/**
 * Computes the following five statistics for a given scanner and its corresponding document
 * 1. Average word length: total number of characters in a word / total number of words
 * 2. Type-Token ratio: number of different words / total number of words
 * 3. Hapax Legomana ratio: number of words that only appear once / total number of words
 * 4. Average number of words in a sentence: total number of words / total number of sentences
 * 5. Sentence complexity: total number of phrases / total number of sentences
 * 
 * @author Ellen Guo
 * @version 3/27/29
 */
public class DocumentStatistics
{
    private Document doc;
    private List<Double> stats; // indices are as follows: 0: avg word length; 1: T-T ratio; 
    // 2: H-L Ratio; 3: avg # of words/sent; 4: sentence complexity
   
    /**
     * Constructor for objects of class DocumentStatistics
     * 
     * @param s the Scanner and its corresponding document for which to compute statistics
     */
    public DocumentStatistics(Scanner s)
    {
        doc = new Document(s);
        stats = new ArrayList<Double>();
    }
    
    /**
     * Uses the Document class to parse the document, then traverses through every Sentence,
     * Phrase, and Token to compute statistics
     * 
     * a Set was used to determine the T-T and H-L ratio as sets only allow unique elements;
     * if an element isn't unique, the add method's return would indicate as such, prompting
     * a decrease in the number of words that appear only once
     * 
     * @postcondition the List of stats has been updated according for the given document
     *      see instance field comment for index definitions
     */
    private void computeStats()
    {
        List<Sentence> sentences = doc.getSentences();
        int numWords = 0;
        int numChars = 0;
        int numPhrases = 0;
        Set<String> diffWords = new HashSet<String>();
        List<String> onlyOnce = new ArrayList<String>();
        for (Sentence s: sentences)
        {
            List<Phrase> phrases = s.copy();
            for (Phrase p: phrases)
            {
                List<Token> tokens = p.copy();
                numPhrases++;
                for (Token t: tokens)
                {
                    String content = t.getToken();
                    if (t.getType().equals(Scanner.TOKEN_TYPE.WORD))
                    {
                        onlyOnce.add(content);
                        numWords++;
                        numChars += content.length();
                        if (!diffWords.add(content))
                        {
                            while(onlyOnce.remove(content))
                            {
                                // do nothing
                            }
                        }
                    }
                }
            }
        }
        stats.add(((double)numChars) / numWords);
        stats.add(((double)diffWords.size()) / numWords);
        stats.add(((double)onlyOnce.size()) / numWords);
        stats.add(((double)numWords) / sentences.size());
        stats.add(((double)numPhrases) / sentences.size());
    }

    /**
     * public accessor to get the stats of the document; thusly this object computes the statistics
     * then returns a shallow copy of the internal data structure of statistics
     * 
     * @return the statistics of this document
     */
    public List<Double> getStats()
    {
        computeStats();
        return stats;
    }
}
