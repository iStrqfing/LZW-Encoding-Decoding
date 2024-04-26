import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;

public class LZWencode {

    static MultiwayTrie trie;

    public static void main(String[] args) {

        // String content = "aababacaacabaacada";
        trie = new MultiwayTrie();

        try {

            // Open the file specified by the argument and read all the content into the
            // variable "content"
            String content = "";
            Scanner fileReader = new Scanner(System.in);

            // Read all lines from the file
            while (fileReader.hasNextLine()) {
                content += fileReader.nextLine();
            }

            content = content.strip();
            // System.out.println(content);
            // content = "AABAABACAACABAACADAA"; // TESTING

            // Set the phrase to be the first character on the hexadecimal sequence. Declare
            // nextDigit as a string
            String phrase = Character.toString(content.charAt(0));
            String nextDigit;

            // While there is still content to encode
            while (content.length() > 0) {

                if (content.length() >= 2) {

                    // If content length is greater than or equal to 2, grab the next digit
                    nextDigit = Character.toString(content.charAt(1));

                } else {

                    // Otherwise, set the next digit to be null
                    nextDigit = null;
                }

                if (trie.find(phrase + nextDigit) != -1) {

                    // If the phrase already exists in the trie, concatenate the next digit to the
                    // phrase
                    phrase += nextDigit;
                } else {

                    // Else, add the phrase and print the sequence number. Set phrase to be the next
                    // digit
                    if (trie.phraseNumCounter < 2000) {
                        printPhraseSequence(phrase + nextDigit);
                        phrase = nextDigit;
                    } else {
                        System.out.println(trie.find(phrase));
                        phrase = nextDigit;
                    }
                    
                }

                // Remove the phrase value from the main string
                content = content.substring(1);
            }
            fileReader.close();
        } catch (Exception e) {

            // Display error messages if exception is thrown (likely illegal file pathname)
            System.out.println("LZWencode Error: " + e.getMessage());
        }
    }

    /*
     * Inserts a phrase into the trie and prints out the phrase sequence
     */
    static private void printPhraseSequence(String phrase) {
        int phraseNum = trie.insert(phrase);
        
        System.out.println(Integer.toString(phraseNum));
    }
}