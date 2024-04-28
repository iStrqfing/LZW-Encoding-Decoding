
/***
 * This class converts hexdigits to bytes
 * HexToByte - Inspiration from https://www.geeksforgeeks.org/java-program-to-convert-hex-string-to-byte-array/
 */

import java.io.*;
import java.util.*;
import java.nio.file.Files;

public class HexToByte {
  public static void main(String[] args) {
    try {
      // Read from standard input
      Scanner fileReader = new Scanner(System.in);
      // While there is another line to read
      while (fileReader.hasNextLine()) {
        String line = fileReader.nextLine();
        // For the length of the line we read, convert each pair of characters
        for (int i = 0; i < line.length(); i += 2) {
          // Parse each pair of characters in the line as hexadecimal
          String hexByte = line.substring(i, i + 2);
          // Convert the pair of hex values to bytes
          int byteValue = Integer.parseInt(hexByte, 16);
          // Write the byte to system.out
          System.out.write(byteValue);
        }
      }
      System.out.flush();
      fileReader.close();
    } catch (Exception e) {
      System.err.println("HexToByte Error: " + e.toString());
    }
  }
}
