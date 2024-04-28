import java.io.*;
import java.util.*;

public class LZWpack {
  
    
    public static void main(String[] args) {
    
        try {
            
            List<Integer> phraseNums = new ArrayList<Integer>();
            Scanner fileReader = new Scanner(System.in);
            // File file = new File(args[0]);
            // Scanner fileReader = new Scanner(file);
            List<Boolean> bits = new ArrayList<Boolean>();
            int phraseCounter = 16;
            
            while (fileReader.hasNextLine()) {
                

                String line = fileReader.nextLine().strip();
                phraseNums.add(Integer.parseInt(line));
            }

            //For every phrase number in the list
            for (Integer phraseNum : phraseNums) {
                
                //Calculate the shift based on base log 2 of the phrase counter
                int shift = (int)(Math.log(phraseCounter) / Math.log(2)) + 1;
                phraseCounter++;

                //Convert the number to binary and calculate the number of zeros to append to the front
                String binary = Integer.toBinaryString(phraseNum);
                int numZeros = shift - binary.length();

                //For the number of zeros to append, append those zeros
                for (int i = 0; i < numZeros; i++) {
                    binary = "0" + binary;
                }

                //Convert the binary string to a char array
                char[] phraseBits = binary.toCharArray();
                System.out.println(binary);

                // //For each bit in the binary representation of the phrase number
                // for (char bit : phraseBits) {
                    
                //     //Convert to boolean and add it to the list
                //     boolean test = (bit == '1');
                //     bits.add(test);
                // }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        
    }
}
