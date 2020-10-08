import java.io.*;
/**
 * Tests the Scanner class to see if it can read in the tokens correctly 
 *
 * @author Anu Datar
 * @author Ellen Guo
 * @version 05/17/2018
 * @version 02/26/2019
 */
public class ScannerTester
{
    /**
     * Main tester method 
     *
     * @param  str array of String objects 
     */
    public static void main(String[] str) throws FileNotFoundException
    {
        FileReader reader = new FileReader(new File("./MysteryText/mystery1.txt"));
        Scanner scanner = new Scanner(reader);
        
        while(scanner.hasNextToken())
        {
            System.out.println(scanner.nextToken());
        }
    }
}
