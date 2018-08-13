package huffman;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * 
 * HUFFMAN COMPRESSOR
 * @author Andres Beltran
 * @version 1.0
 *
 */

public class HuffmanCompressor {
	
	private static int ASCII = 256;
	private static int PROGRAM_NUMBER = 1998;
	private static String[] encodingArray = new String[ASCII + 1]; //array of codes
	private static int[] frequencyArray = new int[ASCII + 1];      // array of frequencies
	private static int nonZeroCharacters;
	
	
	/**
	 * Huffman Encoder
	 * @param inputFileName The name of the file to be compressed
	 * @param outputFileName The name of the compressed file
	 * @return A String stating the result of the encoding process
	 */
	public static String compress(String inputFileName, String outputFileName){
		
		//Stores each character's frequency 
		try {
			
			getFrequencies(inputFileName);
			
		} catch (IOException e) {
			return "File error";
		}
		
		//Creates the Huffman Tree
		HuffmanNode root = HuffmanTreeGenerator.getRoot(generateList());

		//Traverse the tree to store the code for each character
		traverse(root, "");
		
		//Prints out the frequency table with the code for each character
		System.out.println("ENCODING TABLE");
		for (int i = 0; i < frequencyArray.length; i++){
			
			if (frequencyArray[i] > 0)
				System.out.println(((char)(i)) + " : " + frequencyArray[i] + " : " + encodingArray[i]);
			
		}
		
		//Generates the encoded file and calculates the space savings		
		try {
			
			CompressedFileWriter writer = new CompressedFileWriter(outputFileName, inputFileName, encodingArray, PROGRAM_NUMBER, nonZeroCharacters);
			writer.writeFile(root);
			writer.close();
			
			// % Space savings = 100 * (1 - compressed / uncompressed)
			double percentSavings = 100 * (1 - ((double) writer.getCompressedFileSize() / (double) writer.getOriginalFileSize()));

			// Prints out compression information
			System.out.println();
			System.out.println("Savings:");
			System.out.println("Size of original file: " + writer.getOriginalFileSize() + " bits");
			System.out.println("Size of compressed file: " + writer.getCompressedFileSize() + " bits");
			System.out.println("Space saving: " + (int) percentSavings + "%");
			
				
		} catch (IOException e) {
			return "Encoding error";
		}
		
		return "File successfully encoded!";
	}
	
	
	/**
	 * Huffman Decoder
	 * @param inputFileName The name of the file to be decoded
	 * @param outputFileName The name of the decoded file
	 * @return A String stating the result of the decoding process
	 */
	public static String decompress(String inputFileName, String outputFileName){
		
		try {
			
			HuffmanDecoder decoder = new HuffmanDecoder(inputFileName, PROGRAM_NUMBER);
			if (decoder.decode(outputFileName) == -1)
				return "The file was not compressed by this program. Impossible to decode";
				

		} catch (IOException e) {
			return "File error";
		}
		
		return "File successfully decoded!";
		
	}

	
	/**
	 * Computes each character's frequency and stores it in <code>frequencyArray</code>
	 * @param file The file to be scanned
	 * @throws IOException If the file was not found
	 */
	private static void getFrequencies(String file) throws IOException{
		
		FileReader toRead = new FileReader(file);

		int i;

		while ((i = toRead.read()) != -1){  //While the end of the file has not been reached
			if (i < 256)
				frequencyArray[i]++; 		  	//Increment the count for the character of value i
		}

		frequencyArray[256] = 1; 			//Pseudo-EOF-character
		toRead.close();
		
	}
	
	
	/**
	 * Generates an array list of Huffman nodes
	 * @return The ArrayList containing all HuffmanNodes
	 */
	private static ArrayList<HuffmanNode> generateList(){

		ArrayList<HuffmanNode> nodeList = new ArrayList<>();
		
		//Checks if the encoding file is not empty
		boolean isEmpty = true;

		for (int i = 0; i < frequencyArray.length; i++){
			
			//Creates a node for each character whose frequency is > 0, and adds them to the list
			if (frequencyArray[i] > 0){
				nodeList.add(new HuffmanNode((char)(i), frequencyArray[i]));
				nonZeroCharacters++;
				isEmpty = false;
			}
		}

		//If the encoding file is empty (all characters have frequency of 0), throw an exception
		if (isEmpty)
			throw new NoSuchElementException("Encoding file empty");
		
		return nodeList;
	}

	
	/**
	 * Traverses the tree to generate the codes for each character
	 * @param rootNode The root of the tree
	 * @param code The code for each character
	 */
	private static void traverse(HuffmanNode rootNode, String code){
		
		//Stopping condition
		if (rootNode.getLeft() == null && rootNode.getRight() == null 
				&& rootNode.getInChar() != null){
			
			//Stores the code of each character in the corresponding character index
			encodingArray[(int)(rootNode.getInChar())] = code;
			return;
		}
		
		//Traverses left appending 0s
		traverse(rootNode.getLeft(), code + "0");
		
		//Traverses right appending 1s
		traverse(rootNode.getRight(), code + "1");
		  
	}
	
	
	/**
	 * Runs the Huffman Encoder
	 * @param args index 0: compress/decompress; index 1: inputFile; index 2: outputFile
	 */
	public static void main (String[] args){

		//System.out.println(compress(args[0], args[1]));
		//System.out.println(decompress(args[1], "Again.txt"));
		
		if (args.length != 3){
			System.out.println("Format: [compress/decompress] inputFile outputFile");
			return;
		}
		
		if (args[0].equals("compress"))
			System.out.println(compress(args[1], args[2]));
		else if (args[0].equals("decompress"))
			System.out.println(decompress(args[1], args[2]));
		else
			System.out.println("Format: [compress/decompress] inputFile outputFile");
		
	}
	
}