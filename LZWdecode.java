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

                // content.clear();
                // content.add("0");
                // content.add("0");
                // content.add("1");
                // content.add("4");
                // content.add("6");
                // content.add("2");
                // content.add("4");
                // content.add("9");
                // content.add("6");
                // content.add("0");
                // content.add("9");
                // content.add("3");
                // content.add("4");

                //Grab the first number in the sequence and declare nextItem as int and phrase as string
                int prevItem = Integer.parseInt(content.get(0));
                int nextItem;
                String phrase;
                String nextChar = "";

                //Print out the first phrase
                System.out.print(trie.retrieve(prevItem));

                //For each of the items in the content list
                for (int i = 1; i < content.size(); i++) {

                    //Grab the next item in the sequence
                    nextItem = Integer.parseInt(content.get(i));

                    if (trie.find(nextItem)) {
                        
                        //If the next phrase is in the trie, retrieve it and store the full value in phrase
                        phrase = trie.retrieve(nextItem); 

                    } else {
                        
                        //Otherwise, retrieve the full value of the previous phrase number and concatenate the next character
                        phrase = trie.retrieve(prevItem);
                        phrase += nextChar;
                    }

                    //Set the next character to the start of the phrase just seen, insert a new phrase into the trie, and set prevItem to nextItem
                    nextChar = Character.toString(phrase.charAt(0));
                    trie.insert(prevItem, nextChar);
                    prevItem = nextItem;

                    //Print the phrase
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