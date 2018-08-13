package huffman;
/**
 * HUFFMAN NODE
 * @author Andres Beltran
 * @version 1.0
 */

public class HuffmanNode implements Comparable<HuffmanNode>{
	
	private Character inChar; 			//Stores the character
	private int frequency;    			//Stores the frequency 
	private HuffmanNode left = null;	//Stores its left child
	private HuffmanNode right = null;	//Stores its right child
	
	/**
	 * Initializes a new Huffman Node with frequency
	 * @param inChar The character the node stores
	 * @param frequency The character's frequency
	 */
	public HuffmanNode(Character inChar, int frequency){
		this.inChar = inChar;
		this.frequency = frequency;
	}
	
	/**
	 * Initializes a new Huffman Node without frequency
	 * @param inChar The character the node stores
	 */
	public HuffmanNode(Character inChar){
		this.inChar = inChar;
	}
	
	/**********************
	 * GETTERS AND SETTERS
	 *********************/
	
	public Character getInChar() {
		return inChar;
	}
	public void setInChar(Character inChar) {
		this.inChar = inChar;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public HuffmanNode getLeft() {
		return left;
	}
	public void setLeft(HuffmanNode left) {
		this.left = left;
	}
	public HuffmanNode getRight() {
		return right;
	}
	public void setRight(HuffmanNode right) {
		this.right = right;
	}

	/**
	 * Compares the frequency of two nodes
	 * @return Greater than zero if this node's frequency is greater than node <code>o</code>
	 */
	@Override
	public int compareTo(HuffmanNode o) {
		return getFrequency() - o.getFrequency();
	}
}