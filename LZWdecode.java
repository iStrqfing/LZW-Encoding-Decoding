import java.nio.file.Files;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.*;

public class LZWdecode {

    static MultiwayTrie trie;

    public static void main(String[] args) {
        
        //Initialise the multiway trie
        trie = new MultiwayTrie();

        //If there is only one argument passed in
        if (args.length == 1 ) {

            try {
                
                //Open the file passed in and read all the content into the list "content"
                String filename = args[0];
                File file = new File(filename);
                List<String> content = Files.readAllLines(file.toPath());

                //For each of the items in the content list
                for (int i = 0; i < content.size(); i++) {

                    //Split the item to get the parent phrase number and the next character in the sequence
                    String[] items = content.get(i).split(",");
                    int parentNum = Integer.parseInt(items[0]);
                    String character = items[1];

                    //Insert the phrase into the trie
                    String phrase = trie.insert(parentNum, character);

                    //If phrase contains dollar sign (marks end of string)
                    if (phrase.contains("$")) {
                        
                        //Calculate the end index for the substring to get rid of the dollar sign
                        int endIndex = phrase.length() - 2;
                        if (endIndex < 0) { endIndex = 0; }

                        //Substring the phrase so the dollar sign is not included
                        phrase = phrase.substring(0, endIndex);
                    }

                    //Print out the phrase
                    System.out.print(phrase);
                }

            } catch (Exception e) {
                
                //Display error message if exception is thrown
                System.out.println(e.getMessage());
            }
        }
        else {

            //Display error message if there is not exactly one argument passed in
            System.out.println("Error, please input a valid file pathway");
        }
    }
}