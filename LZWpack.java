import java.io.*;
import java.util.*;

/***
 *  Takes the output of LZWencode and outputs a stream of bytes encoding the input in as few bits as are needed (i.e. log p bits per phrase number when there are p patterns in the dictionary).
 */
public class LZWpack {
  
    
    public static void main(String[] args) {
    
        try {
            
            List<Integer> phraseNums = new ArrayList<Integer>();

            // Create buffered reader for system input
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(System.in));

            int phraseCounter = 16;
            String line;

            while ((line = fileReader.readLine()) != null) {
                phraseNums.add(Integer.parseInt(line));
            }
            StringBuilder binaryStringBuilder = new StringBuilder();
            //For every phrase number in the list
            for (Integer phraseNum : phraseNums) {
                // Calculate the shift based on base log 2 of the phrase counter, adding one to account for the initialised 16 hex phrases
                int shift = (int)(Math.log(phraseCounter) / Math.log(2) + 1);
                phraseCounter++;

                //Convert the number to binary and calculate the number of zeros to append to the front
                String binary = Integer.toBinaryString(phraseNum);
                int numZeros = shift - binary.length();

                //For the number of zeros to prepend, preppend those zeros
                for (int i = 0; i < numZeros; i++) {
                    binary = "0" + binary;
                }
                binaryStringBuilder.append(binary);
            }

            String binary = binaryStringBuilder.toString();

            // The length of the binary string
            int binaryLength = binary.length();
            // Byte array to hold binary string in byte form
            byte[] byteArray = new byte[(int) Math.ceil((double) binaryLength / 8)];
            // For each byte of data in the binary string
            for (int i = 0; i < binaryLength; i += 8) {
                // Get a byte of data from the string
                String binaryString = binary.substring(i, Math.min(i + 8, binaryLength));

                // Set the current index of the array to that byte
                if (i == 0) {
                    byteArray[0] = (byte) Integer.parseInt(binaryString, 2);
                } else {
                    byteArray[i / 8] = (byte) Integer.parseInt(binaryString, 2);
                }         
            }
            fileReader.close();

            //Write the binary to system.out
            System.out.write(byteArray);
            System.out.flush();

        } catch (Exception e) {
            // Display error messages if exception is thrown
            System.out.println("LZWpack Error: " + e.getMessage());
        }       
    }
}
