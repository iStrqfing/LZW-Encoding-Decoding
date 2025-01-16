#LZW Encoder and Decoder for text

Use Cases

Tests ByteToHex conversion
  java ByteToHex inputs/test.txt > outputs/byteToHexOutput.txt

Tests HexToByte conversion
  java HexToByte < inputs/test.txt > outputs/hexToByteOutput.txt

Tests ByteToHex and HexToByte conversion returns same input and output
  java ByteToHex inputs/test.txt | java HexToByte > outputs/hexToByteOutput.txt

Tests LZWencode
  java LZWencode < inputs/LZWencodeInput.txt > outputs/LZWencodeOutput.txt

Tests LZWdecode
  java LZWdecode < inputs/LZWdecodeInput.txt > outputs/LZWdecodeOutput.txt

Tests LZWencode and LZWdecode conversion returns same input and output
  java LZWencode < inputs/LZWencodeInput.txt | java LZWdecode > outputs/LZWdecodeOutput.txt

Tests for ByteToHex to LZWencode to LZWdecode to HexToByte
  java ByteToHex inputs/test.txt | java LZWencode | java LZWdecode | java HexToByte > outputs/hexToByteOutput.txt
  java ByteToHex inputs/MobyDick.txt | java LZWencode | java LZWdecode | java HexToByte > outputs/MobyDick.txt
  java ByteToHex inputs/BrownCorpus.txt | java LZWencode | java LZWdecode | java HexToByte > outputs/BrownCorpusOutput.txt
___________________________________________________________________________________________________________________________________________________

Tests for ByteToHex to LZWencode to LZWpack to LZWunpack to LZWdecode to HexToByte
  java ByteToHex inputs/test.txt | java LZWencode | java LZWpack | java LZWunpack | java LZWdecode | java HexToByte > outputs/hexToByteOutput.txt
  java ByteToHex inputs/MobyDick.txt | java LZWencode | java LZWpack | java LZWunpack | java LZWdecode | java HexToByte > outputs/MobyDick.txt
  java ByteToHex inputs/BrownCorpus.txt | java LZWencode | java LZWpack | java LZWunpack | java LZWdecode | java HexToByte > outputs/BrownCorpusOutput.txt


| directs output from system.out.print/write to the next command
> directs output from system.out.print/write to file name
< use the contents of the file instead of system input

To use GitHub - In console of directory to use
Initial setup: 
git clone https://github.com/iStrqfing/COMPX301Assn2.git
git checkout main
git pull origin main

Uploading
git add .
git commit -m "your commit message"
git push -u origin main        - For first upload, subsequent uploads doesnt need -u origin main


Moby Dick encodes to roughly 500,000 lines

md5sum inputs/test.txt outputs/hexToByteOutput.txt
md5sum inputs/MobyDick.txt outputs/MobyDick.txt

Don't think we need .strip()

Almost done, need to fix unpack breaking last value up into two values e.g. last value 50 is 48 and 2 for test.txt
