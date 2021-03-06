# Huffman Compressor
The program applies the Huffman Coding algorithm to compress text files encoded with ASCII. It obtains the frequency of each character found in the input file, and generates a priority queue in a way that the most frequent characters are closer to the root node than the characters that appear less frequently in the file. As a result, a table that maps characters to their respective binary codes (0 for left nodes, and 1 for right nodes) is generated.

Huffman coding reduces the original file size by writing bits based on the relative character frequency, so that the most frequent characters will be written with fewer bits (as opposed to ASCII encoding where any character represents 1 byte of memory). The program also stores the Huffman tree generated when compressing a file as a header in the output (compressed) file, using pre-order traversal for maximum space-savings. This facilitates the process of unzipping any file that was first compressed with this program, since the Huffman tree can be recreated, and the sequence of bits can be turned into characters by constantly traversing the tree until a leaf node is reached (the characters are stored in leaf-nodes). 

## Getting Started
### Prerequisites
Java 7 or above.

### Installing and running the program
The program compresses .txt files, and the output files should also have a .txt extension.

To compress a file:
```bash
java -jar Huffman.jar compress [inputfile.txt] [compressedfile.txt]
```
* Example:
```bash
java -jar Huffman.jar compress PrideAndPrejudice.txt CompressedFile.txt
```

To decompress a file generated by this program:
```bash
java -jar Huffman.jar decompress [compressedfile.txt] [originalfile.txt]
```
* Example:
```bash
java -jar Huffman.jar decompress CompressedFile.txt OriginalFile.txt
```

## Documentation
Documentation is offered under docs/index.html <br>
Or you can see it [here](http://htmlpreview.github.io/?https://github.com/andresbeltran98/HuffmanCompressor/blob/master/doc/index.html)

## Author
* Andres Beltran - B.S. Computer Science candidate. CWRU 2021

## License
This project is licensed under the MIT License - see the [LICENSE](https://github.com/andresbeltran98/HuffmanCompressor/blob/master/LICENSE)  file for details
