import java.nio.file.Files;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.*;

/***
 * LZWdecode takes LZW encoded strings from system output and decodes it using
 * the LZW decompression method. The decoded data is outputed to system.out.
 */
public class LZWdecode {

    static MultiwayTrie trie;

    public static void main(String[] args) {

        // Initialise the HashMap with hex digits
        HashMap<Integer, String> hashMap = new HashMap<>();
        String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
        for (int i = 0; i < hexDigits.length; i++) {
            hashMap.put(i, hexDigits[i]);
        }

        try {
            // Create buffered reader for system input
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(System.in));

            // String array for phrase numbers
            List<String> phraseArray = new ArrayList<String>();
            String line;

            // Read all lines from the reader and add the line to the phrase array
            while ((line = fileReader.readLine()) != null) {
                phraseArray.add(line);
            }

            // Grab the first number in the sequence and declare nextItem as int and phrase as string
            int prevItem = Integer.parseInt(phraseArray.get(0));
            int nextItem;
            String phrase;
            String nextChar = "";

            // Print out the first phrase
            System.out.print(hashMap.get(prevItem));

            // For each of the phrases in the phraseArray
            for (int i = 1; i < phraseArray.size(); i++) {
                // Grab the next item in the array
                nextItem = Integer.parseInt(phraseArray.get(i));

                // If the hashmap contains the next item
                if (hashMap.containsKey(nextItem)) {
                    // If the next item is in the hashmap, retrieve it and store the full value in phrase
                    phrase = hashMap.get(nextItem);
                } else { // Else retrieve the full value of the previous phrase number and concatenate the next character
                    phrase = hashMap.get(prevItem);
                    phrase += nextChar;
                }

                // Set the next character to the start of the phrase just seen, insert the new phrase into the hashmap, and set prevItem to nextItem
                nextChar = Character.toString(phrase.charAt(0));
                hashMap.put(hashMap.size(), hashMap.get(prevItem) + nextChar);
                prevItem = nextItem;

                // Print the phrase
                System.out.print(phrase);
            }
            fileReader.close();
        } catch (Exception e) {
            // Display error message if exception is thrown
            System.out.println("LZWdecode Error: " + e.getMessage());
        }
    }
}