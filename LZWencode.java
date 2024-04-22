public class LZWencode {
  
    public static void main(String[] args) {
        
        String trial = "ababbaababaaabababbbbbaaaaa";
        MultiwayTrie trie = new MultiwayTrie();

        while (trial.length() > 0) {

            String phrase = "";

            while(trial.length() > 0) {
                
                //Get the value at the start of the string and append it to the "to add to dictionary" phrase value
                String section = trial.substring(0, 1);
                phrase += section;

                //Remove the phrase value from the main string
                trial = trial.substring(1);

                if (!trie.find(phrase)) {

                    //Add phrase to dictionary then break out of loop
                    int phraseNum = trie.insert(phrase);
                    System.out.println(Integer.toString(phraseNum) + ", " + phrase.substring(phrase.length()-1));
                    break;
                }
            }
        }
    }
}
