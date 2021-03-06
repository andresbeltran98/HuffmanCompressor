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
	

	/**
	 * Returns this node's character
	 * @return This node's character
	 */
	public Character getInChar() {
		return inChar;
	}
	
	
	/**
	 * Changes this node's character
	 * @param inChar This node's character
	 */
	public void setInChar(Character inChar) {
		this.inChar = inChar;
	}
	
	
	/**
	 * Returns this node's frequency
	 * @return This node's frequency
	 */
	public int getFrequency() {
		return frequency;
	}
	
	
	/**
	 * Changes this node's frequency
	 * @param frequency This node's frequency
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	
	/**
	 * Returns this node's left child
	 * @return This node's left child
	 */
	public HuffmanNode getLeft() {
		return left;
	}
	
	
	/**
	 * Changes this node's left child
	 * @param left This node's left child
	 */
	public void setLeft(HuffmanNode left) {
		this.left = left;
	}
	
	
	/**
	 * Returns this node's right child
	 * @return This node's right child
	 */
	public HuffmanNode getRight() {
		return right;
	}
	
	
	/**
	 * Changes this node's right child
	 * @param right This node's right child
	 */
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