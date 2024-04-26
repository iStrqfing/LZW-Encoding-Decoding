/**
 * ByteToHex - Sourced from https://stackoverflow.com/questions/2817752/how-can-i-convert-a-byte-array-to-hexadecimal-in-java
 */
import java.io.File;
import java.nio.file.Files;
public class ByteToHex {
  public static void main(String[] args) {
    if (args.length == 1) {
      try {
        // Store the first argument as a string of the filename
        String filename = args[0];

        // Retrieve the text file specified by the user
        File txtFile = new File(filename);

        byte[] fileContent = Files.readAllBytes(txtFile.toPath());

         // Convert bytes to hexadecimal string
         StringBuilder hexString = new StringBuilder();
         for (byte b : fileContent) {
             String hex = Integer.toHexString(b & 0xFF);
             if (hex.length() == 1) {
                 hexString.append('0'); // Append 0 at front so hex is always two chars
             }
             hexString.append(hex);
         }

         // Print the hexadecimal string
         System.out.println(hexString.toString());
      } catch (Exception e) {
        System.out.println("ByteToHex Error: " + e.toString());
      }
    } else {
      System.out.println("Error, must include one argument: File name");
    }
  }
}