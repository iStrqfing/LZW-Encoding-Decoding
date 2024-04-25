import java.io.File;
import java.nio.file.Files;

public class LZWencode {
  
    static MultiwayTrie trie;

    public static void main(String[] args) {
        
        //String content = "aababacaacabaacada";
        trie = new MultiwayTrie();

        //If there is only one argument passed in
        if (args.length == 1 ) {
            
            try {
             
                //Open the file specified by the argument and read all the content into the variable "content"
                String filename = args[0];
                File file = new File(filename);
                String content = Files.readString(file.toPath());
                content = content.strip();

                content = "AABAABACAACABAACADA"; //TESTING

                //Set the phrase to be the first character on the hexadecimal sequence. Declare nextDigit as a string
                String phrase = Character.toString(content.charAt(0));
                String nextDigit;

                //While there is still content to encode
                while (content.length() > 0) {

                    if (content.length() >= 2) {

                        //If content length is greater than or equal to 2, grab the next digit
                        nextDigit = Character.toString(content.charAt(1));

                    } else {
                        
                        //Otherwise, set the next digit to be null
                        nextDigit = null;
                    }

                    if (trie.find(phrase + nextDigit)) {
                        
                        //If the phrase already exists in the trie, concatenate the next digit to the phrase
                        phrase += nextDigit;
                    }
                    else {

                        //Else, add the phrase and print the sequence number. Set phrase to be the next digit
                        printPhraseSequence(phrase + nextDigit);
                        phrase = nextDigit;
                    }

                    //Remove the phrase value from the main string
                    content = content.substring(1);
                }
        
                //Once the inputted text has been fully read, encode a "$" plus any remaining phrases to mark the end
                phrase += "$";
                printPhraseSequence(phrase);

            } catch (Exception e) {
                
                //Display error messages if exception is thrown (likely illegal file pathname)
                System.out.println(e.getMessage());
                System.out.println("Please enter a valid file path");
            }

        } else {
            
            //Display error message if there is not exactly one argument passed in
            System.out.println("Error, please input a valid file pathway");
        }
    }

    /*
     * Inserts a phrase into the trie and prints out the phrase sequence
     */
    static private void printPhraseSequence(String phrase) {

        //Add phrase to dictionary
        int phraseNum = trie.insert(phrase);
        System.out.println(Integer.toString(phraseNum));
    }
}