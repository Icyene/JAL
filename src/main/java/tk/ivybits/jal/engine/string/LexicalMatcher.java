package tk.ivybits.jal.engine.string;

import java.util.LinkedList;

public class LexicalMatcher {

    public static double getSimilarity(String a, String b) {
        LinkedList<String> pairs1 = wordLetterPairs(a.toUpperCase());
        LinkedList<String> pairs2 = wordLetterPairs(b.toUpperCase());

        int intersection = 0;
        int union = pairs1.size() + pairs2.size();
        for (int i = 0; i < pairs1.size(); i++) {
            Object pair1 = pairs1.get(i);
            for (int j = 0; j < pairs2.size(); j++) {
                Object pair2 = pairs2.get(j);
                if (pair1.equals(pair2)) {
                    intersection++;
                    pairs2.remove(j);
                    break;
                }
            }
        }
        return (2.0 * intersection) / union;
    }

    private static LinkedList<String> wordLetterPairs(String str) {
        LinkedList<String> allPairs = new LinkedList<String>();

        String[] words;
        for (int w = 0; w < (words = str.split("\\s")).length; w++) {
            String[] pairsInWord;
            for (int p = 0; p < (pairsInWord = letterPairs(words[w])).length; p++) {
                allPairs.add(pairsInWord[p]);
            }
        }
        return allPairs;
    }

    private static String[] letterPairs(String str) {
        int numPairs;
        if ((numPairs = str.length()) != 0) {
            numPairs = numPairs - 1;
        }

        String[] pairs = new String[numPairs];

        for (int i = 0; i < numPairs; i++) {
            pairs[i] = str.substring(i, i + 2);
        }

        return pairs;
    }
}