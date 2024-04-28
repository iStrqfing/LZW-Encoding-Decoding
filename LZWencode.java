import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;

public class LZWencode {

    static MultiwayTrie trie;

    public static void main(String[] args) {

        trie = new MultiwayTrie();

        try {

            // Open the file specified by the argument and read all the content into the
            // variable "content"
            //String content = "";
            StringBuilder content = new StringBuilder();
            Scanner fileReader = new Scanner(System.in);

            // Read all lines from the file
            while (fileReader.hasNextLine()) {
                //content += fileReader.nextLine();
                content.append(fileReader.nextLine().strip());
            }

            //content = content.strip();
            // System.out.println(content);
            // content.append("abaaabababbbbaba"); // TESTING

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
                    nextDigit = "$";
                }

                //Attempt to insert the phrase into the trie
                int phraseNum = trie.insert(phrase + nextDigit);

                if (phraseNum == -1) {
                    
                    //If phrase already exists in trie, check length of remaining content
                    if (content.length() == 1) {

                        //If the content length equals 1, just find the phraseNum of phrase + next digit and print it
                        System.out.println(trie.find(phrase + nextDigit));

                    } else {

                        //Else, concatenate the next digit to the phrase
                        phrase += nextDigit;
                    }

                } else {

                    //Print out the phrase number and set the phrase to the next digit
                    System.out.println(phraseNum);
                    phrase = nextDigit;
                }
                // if (trie.find(phrase + nextDigit) != -1) {

                //     // If the phrase already exists in the trie, concatenate the next digit to the
                //     // phrase
                //     if (content.length() == 1) {
                //         System.out.println(trie.find(phrase + nextDigit));
                //     } else {
                //         phrase += nextDigit;
                //     }

                // } else {

                //     // Else, add the phrase and print the sequence number. Set phrase to be the next
                //     // digit
                //     if (trie.phraseNumCounter < 2000) {
                //         printPhraseSequence(phrase + nextDigit);
                //         phrase = nextDigit;
                //     } else {
                //         System.out.println(trie.find(phrase));
                //         phrase = nextDigit;
                //     }
                    
                // }

                // Remove the phrase value from the main string
                content.deleteCharAt(0);
            }
            // fileReader.close();

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