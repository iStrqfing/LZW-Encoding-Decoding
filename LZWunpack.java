import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/***
 * Takes as input the output of LZWpack and produces as output the exact input that went into LZWpack
 */
public class LZWunpack {

  public static void main(String[] args) {
    try {
      // Read all the bytes from the input stream
      byte[] byteArray = System.in.readAllBytes();
      StringBuilder binaryStringBuilder = new StringBuilder();

      // For every byte in the array
      for (byte b : byteArray) {
        // Convert byte to an integer to get the binary string representation
        String binaryString = Integer.toBinaryString(b & 0xFF); // Use & 0xFF to handle signed bytes
        // Pad left with 0's so the string is always 8 bits
        binaryString = String.format("%8s", binaryString).replace(' ', '0');

        // Append the binary string to string builder
        binaryStringBuilder.append(binaryString);
      }

      // The phrase counter and binary pointer for the string
      int phraseCounter = 16, binaryPointer = 0;

      // While there is still binary strings to unnpack
      while (binaryStringBuilder.length() > binaryPointer) {
        // Calculate what the shifted value was in the packer
        int shift = (int)(Math.log(phraseCounter) / Math.log(2) + 1);
        phraseCounter++;

        // If the binary pointer and shift exceeds the length of the string
        if (binaryPointer + shift > binaryStringBuilder.length()) {
          // Set the shift to go to the end of the string
          shift = binaryStringBuilder.length() - binaryPointer; ///////////////////////////////////// Could check for 000000 here?
          //break; // We can break and treat the rest as padded 0's???
        }
        // Get the current binary string we are unpacking
        String currentString = binaryStringBuilder.substring(binaryPointer, binaryPointer + shift);
        //System.out.println("Length: " + binaryStringBuilder.length() + " \t Shifted: " + (binaryPointer + shift));
        binaryPointer += shift;
        
        // Convert the binary string to an integer for the phrase number and print it
        String phraseNum = Integer.toString(Integer.parseInt(currentString, 2));
        System.out.println(phraseNum);
      }
      //System.out.println(binaryStringBuilder.toString());
    } catch (Exception e) {
      // Display error messages if exception is thrown
      System.out.println("LZWunpack Error: " + e.getMessage());
    }
  }
}
