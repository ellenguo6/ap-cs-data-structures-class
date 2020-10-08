import java.util.*;
import java.io.*;
/**
 * Authorship detection: Finds the author of mystery files given signatures of 
 * various authors by comparing the statistics of each mystery file with the stats 
 * of the given authors and seeing which one matches the best
 * 
 * @author Ellen Guo
 *
 * @version 3/28/19
 */
public class FindAuthor 
{
    /**
     * a Map was chosen for the the author signatures as they are inherently key-value 
     * (author-stats) pairs; additionally, the order of elements does not matter
     */
    private static Map<String, double[]> authors;
    private static double[] weights = {11, 33, 50, 0.4, 4};

    /**
     * Scans the author signature files in a given directory, and compares those
     * stats to the 5 mystery files in a different direction, and
     * outputs the predicted author of each file
     *
     * @param args arguments from the command line
     */
    public static void main(String [ ] args) throws FileNotFoundException
    {
        authors = new HashMap<String, double[]>();

        File[] files = new File("./SignatureFiles").listFiles();

        for (File file: files)
        {
            java.util.Scanner s = new java.util.Scanner(file);
            String key = s.nextLine();
            double[] vals = new double[5];
            for (int i = 0; i < 5; i++)
            {
                vals[i] = new Double(s.nextLine());
            }
            authors.put(key, vals);
        }

        for (int i = 0; i < 5; i++)
        {
            String filename = "./MysteryText/mystery" + (i + 1) + ".txt";
            FileReader reader = new FileReader(new File(filename));
            Scanner scanner = new Scanner(reader);
            DocumentStatistics doc = new DocumentStatistics(scanner);
            List<Double> mystStats = doc.getStats();

            String author = findAuthor(mystStats);
            System.out.println("The author of the mystery document " + (i + 1) + " is " + author);
        }

    }

    /**
     * Helper method for the main method to compute the best author fit for a given
     * set of stats
     * 
     * @param stats the given stats (of the mystery doc) to compare to
     * @return the author (from the filled-out instance field) that matches best to the given stats
     */
    private static String findAuthor(List<Double> stats)
    {
        double lowest = Double.MAX_VALUE; 
        String author = "";
        Iterator it = authors.entrySet().iterator();
        while (it.hasNext()) 
        {
            Map.Entry<String, double[]> pair = (Map.Entry<String, double[]>)it.next();
            double sum = 0;
            for (int i = 0; i < 5; i++)
            {
                double diff = Math.abs(pair.getValue()[i] - stats.get(i));
                sum += diff * weights[i];
            }
            if (sum < lowest)
            {
                lowest = sum;
                author = pair.getKey();
            }
        }
        return author;
    }
}

