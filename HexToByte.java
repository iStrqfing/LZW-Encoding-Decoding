
/***
 * https://www.geeksforgeeks.org/java-program-to-convert-hex-string-to-byte-array/
 */

import java.io.*;
import java.util.*;
import java.nio.file.Files;

public class HexToByte {
  public static void main(String[] args) {
    try {
      // Read from standard input
      Scanner fileReader = new Scanner(System.in);

      while (fileReader.hasNextLine()) {
        String line = fileReader.nextLine();

        // System.out.println(line.length());
        // Parse each pair of characters in the line as hexadecimal
        for (int i = 0; i < line.length(); i += 2) {
          String hexByte = line.substring(i, i + 2);
          int byteValue = Integer.parseInt(hexByte, 16);
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
