import java.io.*;

/**
 * Tests the scanning, parsing, and statistics of a given Document
 * 
 * @author Ellen Guo
 *
 * @version 3/26/19
 */
public class DocumentTester 
{
    /**
     * Tests three classes:
     * Scanner
     * Document
     * DocumentStatistics
     *
     * @param args arguments from the command line
     * @throws FileNotFoundException
     */
    public static void main(String [ ] args) throws FileNotFoundException
    {
        // text.txt is a file I wrote which reads:
        
        // "Hello, my name is Ellen Guo, and I'm here to talk about my dog. 
        // His name is Popcorn. I like to name things, and I like having a dog as a pet."
        
        // Here are the statistics I computed by hand: [3.25, 0.6875, 0.40625, 10.66666, 2]
        
        FileReader reader = new FileReader(new File("./MysteryText/text.txt"));
        Scanner scanner = new Scanner(reader);
        while(scanner.hasNextToken())
        {
            System.out.println(scanner.nextToken());
        }
        
        FileReader reader2 = new FileReader(new File("./MysteryText/text.txt"));
        Scanner scanner2 = new Scanner(reader2);
        Document doc = new Document(scanner2);
        System.out.println(doc.getSentences());
        
        FileReader reader3 = new FileReader(new File("./MysteryText/text.txt"));
        Scanner scanner3 = new Scanner(reader3);
        DocumentStatistics stats = new DocumentStatistics(scanner3);
        System.out.println(stats.getStats());
    }

}
