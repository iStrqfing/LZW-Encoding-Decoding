public class LZWencode {
  

    public static void main(String[] args) {
        
        String trial = "3ab45cfea21";

        while (trial.length() > 0) {

            String phrase = "";

            while(true) {

                //Get the value at the start of the string and append it to the "to add to dictionary" phrase value
                String section = trial.substring(0, 1);
                phrase += section;

                //Remove the phrase value from the main string
                trial = trial.substring(1);

                // if (/* Phrase not in dictionary */) {

                //     //Add phrase to dictionary
                //     break;
                // }

                //Else, loop through again and build the phrase
            }
        }
    }
}
