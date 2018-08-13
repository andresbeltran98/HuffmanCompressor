package huffman;
import java.io.*;

import huffman.ThirdPartyClasses.BitInputStream;

/**
 * DECOMPRESSOR
 * @author Andres Beltran
 * @version 1.0
 * 
 */

public class HuffmanDecoder {
	
	private static int PROGRAM_NUMBER;		//Program's ID
	private BitInputStream reader;    		//Bit reader
	private int nonZeroCharacters;			//Number of distinct characters in the file
	private int treeCounter;				//Counts the number of distinct characters when reading the tree
	private HuffmanNode root;				//Huffman tree's root
	
	
	/**
	 * Initializes the Decoder
	 * @param inputFile The name of the compressed file
	 * @param magicNumber The program's ID
	 * @throws IOException If there is an error with the file
	 */
	public HuffmanDecoder(String inputFile, int magicNumber) throws IOException {
	
		PROGRAM_NUMBER = magicNumber;
		reader = new BitInputStream(new FileInputStream(inputFile));
		root = new HuffmanNode(null);
		
	}
	
	
	/**
	 * Generates the Huffman tree stored in this file (using Preorder traversal)
	 * @param node The tree's nodes
	 * @throws IOException If there is an error reading the file
	 */
	private void recreateTree(HuffmanNode node) throws IOException{
		
		//Check if all the characters have been accounted for
		if (treeCounter == nonZeroCharacters)
			return;
		
		int bit = reader.read(1);
		
		//1 represents a leaf-node (where characters are stored)
		if (bit == 0){
			node.setLeft(new HuffmanNode(null));
			node.setRight(new HuffmanNode(null));
			
			recreateTree(node.getLeft());
			recreateTree(node.getRight());
			
		}else{
			node.setInChar((char)reader.read(9));
			treeCounter++;
			return;
		}
	}
	
	
	/**
	 * Traverses the Huffman Tree stored in the file to decode each character
	 * @param outputFile The name of the decoded file
	 * @throws IOException If there is an error with <code>outputFile</code>
	 */
	private void readFile(String outputFile) throws IOException{
		
		int readBit;
		HuffmanNode pointer = root; //A pointer that helps traversing the tree
		FileWriter writer = new FileWriter(outputFile);
		
		while(true){
			
			//Check if a leaf-node (a character) has been reached
			if (pointer.getInChar() != null){
				
				//Check if the Pseudo-EOF-character has been reached 
				if ((int)pointer.getInChar() == 256)
					break;
				
				writer.write(pointer.getInChar()); //Write the corresponding character
				pointer = root;					   //Go back to the root
			}
			
			readBit = reader.read(1);
			
			//0 goes left, 1 right
			if (readBit == 0)
				pointer = pointer.getLeft();
			else
				pointer = pointer.getRight();
			
		}
		
		writer.close();
	}
	
	
	/**
	 * Puts everything together and generates the original (decoded) file
	 * @param outputFile The name of the decoded file
	 * @return 0 if the file was successfully decoded, or -1 if the program's ID does not match
	 * @throws IOException If there is an error with the <code>outputFile</code>
	 */
	public int decode(String outputFile) throws IOException{
		
		if (reader.read(32) != PROGRAM_NUMBER) 
			return -1;
		
		nonZeroCharacters = reader.read(9);
		recreateTree(root);
		readFile(outputFile);
		reader.close();
		return 0;
		
	}
	
}
