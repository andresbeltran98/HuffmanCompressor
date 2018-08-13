package huffman;
import java.util.ArrayList;

/**
 * HUFFMAN PRIORITY QUEUE
 * @author Andres Beltran
 * @version 1.0
 *
 */

public class HuffmanTreeGenerator {
	
	/**
	 * Generates the Huffman Tree O(NlogN)
	 * @param list The list of HuffmanNodes
	 * @return The root of the generated tree
	 */
	public static HuffmanNode getRoot(ArrayList<HuffmanNode> list){
		
		//Builds the min-at-top heap -> O(N) algorithm
		for (int i = ((list.size() - 2) / 2); i >= 0; i--)
			siftDown(list, i);
		
		while (list.size() > 1){	//While there is more than 1 node in the list
			
			//Removes the two with the lowest frequencies and merges them
			HuffmanNode merged = mergeNodes(removeMin(list), removeMin(list));
			
			//Insert the merged node back into the heap
			insertToHeap(list, merged);	
		}
		
		//The remaining node is the root of the tree
		return list.get(0);
		
	}
	
	
	/**
	 * Merges two nodes into one node whose frequency is the sum of the left and right node
	 * @param leftNode The node with the lower frequency
	 * @param rightNode The node with the higher frequency
	 * @return The merged node
	 */
	private static HuffmanNode mergeNodes(HuffmanNode leftNode, HuffmanNode rightNode){

		//Creates a new node with frequency left + right
		HuffmanNode parentNode = new HuffmanNode(null, leftNode.getFrequency() + rightNode.getFrequency());
		parentNode.setLeft(leftNode);
		parentNode.setRight(rightNode);
		return parentNode;
	}
	
	/********************
	 * 
	 * MIN-AT-TOP HEAP METHODS
	 * 
	 *********************/
	
	/**
	 * Sift Down
	 * @param listNodes The list of Huffman nodes
	 * @param i The index of the node to be sifted down
	 */
	private static void siftDown(ArrayList<HuffmanNode> listNodes, int i) {
		
		HuffmanNode toSift = listNodes.get(i); //Stores the node to be sifted
		int parent = i;						   //Stores the index of the parent
		int child = 2 * parent + 1;            //Index of the left child 
		
		while (child < listNodes.size()) {

			if (child + 1 < listNodes.size() && // if the right child exists
					listNodes.get(child).compareTo(listNodes.get(child + 1)) > 0) //and is smaller than the left child 
				child = child + 1; // take the right child

			if (toSift.compareTo(listNodes.get(child)) <= 0) // we’re done
				break; 

			//Swaps the nodes (sifting down)
			listNodes.set(parent, listNodes.get(child));
			listNodes.set(child, toSift);
			
			//Update the indices
			parent = child;
			child = 2 * parent + 1; 
		}
		
		listNodes.set(parent, toSift);
	}
	
	
	/**
	 * Sift Up
	 * @param listNodes A list with all the nodes that will be in the tree
	 * @param i The index of the node to be sifted up
	 */
	private static void siftUp(ArrayList<HuffmanNode> listNodes, int i){
		
		HuffmanNode toSift = listNodes.get(i); //Stores the node to be sifted up
		int child = i;						   //Stores its index
		int parent = (i - 1) / 2; 			   //Stores the index of its parent
		
		while (parent > 0) {

			//If the parent has lower frequency we are done
			if (listNodes.get(parent).compareTo(listNodes.get(child)) <= 0)
				return;
			
			//Swaps the nodes 
			listNodes.set(child, listNodes.get(parent));
			listNodes.set(parent, toSift);
			
			//Updates the indices
			child = parent;
			parent = (child - 1) / 2;
		}
		
		//Checks if the root node has higher frequency, if so, we're done
		if (listNodes.get(parent).compareTo(listNodes.get(child)) <= 0)
			return;
		
		//If not, swap the root node
		listNodes.set(child, listNodes.get(parent));
		listNodes.set(parent, toSift);		
	}
	
	
	/**
	 * Inserts an element to the heap
	 * @param listNodes The list of Huffman Nodes
	 * @param node The node to be added
	 */
	private static void insertToHeap(ArrayList<HuffmanNode> listNodes, HuffmanNode node){
		
		//Adds the node at the end of the list
		listNodes.add(node);
		
		//Sift it up
		siftUp(listNodes, listNodes.size() - 1);
	}
	
	
	/**
	 * Removes the minimum element from the priority queue (heap)
	 * @param listNodes The list of Huffman nodes
	 * @return The node with the lowest frequency
	 */
	private static HuffmanNode removeMin (ArrayList<HuffmanNode> listNodes){
		
		HuffmanNode toRemove = listNodes.get(0); //Stores the node to be returned
		
		//Copy the last node in the root, and remove it
		listNodes.set(0, listNodes.get(listNodes.size() - 1)); 
		listNodes.remove(listNodes.size() - 1);
		
		//If there is at least 1 node, sift down the new root
		if (listNodes.size() > 0)
			siftDown(listNodes, 0);
		
		return toRemove;
	}

}
