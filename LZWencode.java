import java.io.File;
import java.nio.file.Files;

public class LZWencode {
  
    static MultiwayTrie trie;

    public static void main(String[] args) {
        
        //String content = "ababbaababaaabababbbbbaaaaab";
        trie = new MultiwayTrie();
        String phrase = "";

        //If there is only one argument passed in
        if (args.length == 1 ) {
            
            try {
             
                //Open the file specified by the argument and read all the content into the variable "content"
                String filename = args[0];
                File file = new File(filename);
                String content = Files.readString(file.toPath());
                content = content.strip();

                while (content.length() > 0) {

                    phrase = "";
        
                    while(content.length() > 0) {
                        
                        //Get the value at the start of the string and append it to the "to add to dictionary" phrase value
                        String section = content.substring(0, 1);
                        phrase += section;
        
                        //Remove the phrase value from the main string
                        content = content.substring(1);
        
                        //If the phrase is not already in the dictionary
                        if (!trie.find(phrase)) {
        
                            //Add phrase to dictionary then break out of loop
                            printPhraseSequence(phrase);
                            break;
                        }
                    }
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
        System.out.println(Integer.toString(phraseNum) + ", " + phrase.substring(phrase.length()-1));
    }
}
