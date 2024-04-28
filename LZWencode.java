import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Scanner;

/***
 * LZWencode takes hex strings from system output and encodes it using the LZW compression method. The encoded data is outputed to system.out.
 */
public class LZWencode {

    static MultiwayTrie trie;

    public static void main(String[] args) {

        trie = new MultiwayTrie();

        try {
            // Create buffered reader for system input
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(System.in));
            String content;

            // Read all lines from the file
            while ((content = fileReader.readLine()) != null) {
                // Set the phrase to be the first character of the hexadecimal sequence. Declare nextChar as a string
                String phrase = Character.toString(content.charAt(0));
                String nextChar;

                // The character pointer for the string
                int contentPointer = 0;

                // While there is still content to encode
                while (content.length() > contentPointer) {
                    // If we are not at the last character in the string
                    if (content.length() - contentPointer > 1) {
                        // Grab the next char in the string
                        nextChar = Character.toString(content.charAt(contentPointer + 1));
                    } else {
                        // Else set the next char to nothing
                        nextChar = "";
                    }

                    // Attempt to insert the phrase into the trie
                    int phraseNum = trie.insert(phrase + nextChar);

                    // If phrase was already in the trie
                    if (phraseNum == -1) {
                        // If we are at the end of the string
                        if (content.length() == contentPointer + 1) {
                            // Print the phrase number
                            System.out.println(trie.find(phrase + nextChar));
                        } else { // Else, concatenate the next character to the phrase
                            phrase += nextChar;
                        }
                    } else { // Else print out the phrase number and set the phrase to the next character     
                        System.out.println(phraseNum);
                        phrase = nextChar;
                    }
                    // Increase content pointer
                    contentPointer++;
                }
            }
            fileReader.close();

        } catch (Exception e) {
            // Display error messages if exception is thrown (likely illegal file pathname)
            System.out.println("LZWencode Error: " + e.getMessage());
        }
    }
}