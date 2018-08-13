package huffman;
import java.io.*;

import huffman.ThirdPartyClasses.BitOutputStream;

/**
 * COMPRESSOR
 * @author Andres Beltran
 * @version 1.0
 */

public class CompressedFileWriter extends BitOutputStream {
	
	private static int PROGRAM_NUMBER;      //Stores the program's ID
	private static int BITS_PER_INT = 32;   //Stores the number of bits used to write the program ID
	private String inputFile;               //Stores the name of the file to be compressed
	private String[] encodingArray;         //Stores the table mapping characters to their binary code
	private int originalFileSize;			//Stores the size of the original file
	private int compressedFileSize;			//Stores the size of the compressed file
	private int nonZeroCharacters;			//Stores the number of non-zero-frequency characters in the file
	
	
	/**
	 * Initializes the File Writer
	 * @param outputFile The name of the compressed file
	 * @param inputFile The name of the file to be compressed
	 * @param encodingArray The table containing each character's binary code
	 * @param programNumber The program's ID
	 * @throws IOException If there is an error with any file
	 */
	public CompressedFileWriter(String outputFile, String inputFile, String[] encodingArray, int programNumber, int nonZeroCharacters) throws IOException{
		
		super(new FileOutputStream(outputFile));
		this.inputFile = inputFile;
		this.encodingArray = encodingArray;
		this.nonZeroCharacters = nonZeroCharacters;
		PROGRAM_NUMBER = programNumber;
		
	}
	
	
	/**
	 * Writes the program ID at the top, useful when decompressing
	 */
	private void writeMagicNumber(){
		
		write(BITS_PER_INT,PROGRAM_NUMBER);		//Write the program's ID
		write(9, nonZeroCharacters);			//Write number of characters whose frequency is not 0 
		compressedFileSize += BITS_PER_INT + 9;
		
	}
	
	
	/**
	 * Stores the Huffman Tree in the compressed file using Preorder Traversal
	 * @param rootNode The root of the Huffman Tree
	 */
	private void writeHeaderTree(HuffmanNode rootNode){
		
		if (rootNode == null)
			return;
		
		//A leaf-node is represented as 1, followed by 9 bits that represent the character stored in the node
		if (rootNode.getLeft() == null && rootNode.getRight() == null 
				&& rootNode.getInChar() != null){
			
			write(1,1);
			write(9, (int)rootNode.getInChar());
			compressedFileSize += 10;
			return;
			
		}
		
		//A non-leaf node is represented as 0
		write(1,0);
		compressedFileSize ++;
		writeHeaderTree(rootNode.getLeft());
		writeHeaderTree(rootNode.getRight());
		
	}
	
	
	/**
	 * Compresses the actual file
	 * @param inputFile The name of the original file (to be compressed)
	 * @param encodingArray The table with each character's binary code
	 * @throws IOException If there is an error with the <code>inputFile</code>
	 */
	private void writeBody (String inputFile, String[] encodingArray) throws IOException{
		
		FileReader toRead = new FileReader(inputFile);
	   
		int i;
		while ((i = toRead.read()) != -1){ 						//for each character
			originalFileSize += 8;  							//each character is 8 bits for ASCII
			compressedFileSize += encodingArray[i].length();	//variable-length for Huffman coding
			writeCharacter(encodingArray[i]); 					//Writes the bits for this character
		}
		
		writeCharacter(encodingArray[256]); 					//Writes the Pseudo-EOF-character at the end
		toRead.close();
		
	}
	
	
	/**
	 * Helper method to write a character's binary code one bit at a time
	 * @param characterCode The binary code that represents a specific character
	 */
	private void writeCharacter(String characterCode){
		
		for (int i = 0; i < characterCode.length(); i++){
			write(1, Integer.parseInt(String.valueOf(characterCode.charAt(i))));
		}
	}
	
	
	/**
	 * Returns the size of the original file
	 * @return The size of the original file
	 */
	public int getOriginalFileSize(){
		return originalFileSize;
	}
	
	
	/**
	 * Returns the size of the compressed file
	 * @return The size of the compressed file
	 */
	public int getCompressedFileSize(){
		return compressedFileSize;
	}
	
	
	/**
	 * Puts everything together and generates the compressed file
	 * @param rootNode The root of the Huffman Tree
	 * @throws IOException If there is an error with the file to be compressed
	 */
	public void writeFile(HuffmanNode rootNode) throws IOException{
		
		writeMagicNumber();
		writeHeaderTree(rootNode);
		writeBody(inputFile, encodingArray);
	
	}
	
}
