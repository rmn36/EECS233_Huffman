import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author ryannowacoski
 * This class represents a Huffman Tree used to encode a text file
 */
public class HuffmanLib {
	/**
	 * The root node of the Huffman Tree
	 */
	private HuffmanTreeNode root = null;
	
	/**
	 * @return the root
	 */
	public HuffmanTreeNode getRoot() {
		return root;
	}
	
	/**
	 * @param root- the root to set
	 */
	public void setRoot(HuffmanTreeNode root) {
		this.root = root;
	}
	
	/**
	 * Merges a node with a given node
	 * @param t2- node being merged
	 */
	public void mergeWithLeftSibling(HuffmanTreeNode t2){
		HuffmanTreeNode temp = new HuffmanTreeNode(root.getFreq() + t2.getFreq());  //Makes a new node with the combined frequencies
		temp.setR(root);															//Sets the current root as the right child of the new node
		temp.setL(t2);																//Sets the other node as the Left child
		root = temp;
	}
	
	/**
	 * Reads a text file to a string
	 * @param fileName- the file which will be read
	 * @return a string representation of the file
	 * @throws IOException
	 */
	public static String readFileToString(String fileName) throws IOException{
		StringBuilder str = new StringBuilder();				//will build the new string
		FileReader reader = new FileReader(fileName);			//opens the file
		BufferedReader bReader = new BufferedReader(reader);  	//will read the file
		String nextLine = bReader.readLine();					//reads the file line by line and appends it to the stringbuilder
		while (nextLine != null){
			str.append(nextLine);
			str.append("\n");
			nextLine = bReader.readLine();
		}
		reader.close();
		String fileString = str.toString();
		return fileString;
	}
	
	/**
	 * Creates a linked list of characters and their corresponding frequencies
	 * @param fileString
	 * @return
	 */
	public static LinkedList<HuffmanTreeNode> createLinkedList(String fileString){
		LinkedList<HuffmanTreeNode> charList = new LinkedList<HuffmanTreeNode>();
		for(int i = 0; i < fileString.length(); i++){
			boolean found = false;  
			for(int j = 0; j < charList.size(); j++){
				if(Character.valueOf(fileString.charAt(i)) == charList.get(j).getC()){				//checks if char is already in the list
					charList.get(j).setFreq(charList.get(j).getFreq() + 1);							//increments the frequency is char is found
					found = true;
				}
			}
			if(!found){																				//if char is not found adds a new node
				charList.add(new HuffmanTreeNode(Character.valueOf(fileString.charAt(i)), 1));
			}
		}
		return charList;
	}

	/**
	 * Sorts the linked list by increasing frequency
	 * @param charList
	 * @return
	 */
	public static LinkedList<HuffmanTreeNode> sortLinkedList(LinkedList<HuffmanTreeNode> charList){ 
		LinkedList<HuffmanTreeNode> temp = charList;	
		Collections.sort(temp);		//Sorts the LinkedList using the compareTo method of the HuffmanTreeNode
		return temp;
	}
	
	/**
	 * Converts a list to a tree by merging the first two nodes then re-sorting
	 * @param charList
	 */
	public void convertToTree(LinkedList<HuffmanTreeNode> charList){ 
		while(charList.size() > 1){
			root = charList.remove(1);						//Removes second node of the list
			this.mergeWithLeftSibling(charList.remove(0));	//Merges the second node with the first
			charList.addLast(root);							//Adds the new node to the end of the list
			charList = HuffmanLib.sortLinkedList(charList);	//Sorts the list
		}
	}
	
	/**
	 * Assigns a binary code to all characters in the tree
	 * @param root
	 * @param list
	 */
	public static void assignBinary(HuffmanTreeNode root, LinkedList<HuffmanTreeNode> list){
		if(root.getL() != null){
			root.getL().setBinary(root.getBinary() + "0");	//Recursively assigns 0 to all left branches
			assignBinary(root.getL(), list);
			
			root.getR().setBinary(root.getBinary() + "1");	//Recursively assigns 1 to all right branches
			assignBinary(root.getR(), list);
		}
		else{
			list.get(HuffmanLib.find(root.getC(), list)).setBinary(root.getBinary());	//If the node is a leaf then it sets the leaf node binary to the full code
		}
	}
	
	/**
	 * Finds a node in a linked list by a given character
	 * @param c
	 * @param list
	 * @return
	 */
	public static int find(Character c, LinkedList<HuffmanTreeNode> list){
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getC().compareTo(c) == 0){
				return i;
			}
			else;
		}
		return 0;
	}
	
	/**
	 * Encodes a text file by implementing all of the methods above. Outputs the encoded text, a table of characters to binary and a calculation of space savings to a text file.
	 * @param input
	 * @param output
	 * @throws IOException
	 */
	public static void huffmanCoder(String input, String output) throws IOException{
		String file;
		try {
			file = HuffmanLib.readFileToString(input);								//reads the file to a string
			LinkedList<HuffmanTreeNode> list = HuffmanLib.createLinkedList(file);	//creates a linked list of the characters and frequencies
			LinkedList<HuffmanTreeNode> list1 = HuffmanLib.createLinkedList(file);	//makes another independent copy of the linked list to be used later
			list = HuffmanLib.sortLinkedList(list);									//sort both lists
			list1 = HuffmanLib.sortLinkedList(list1);
			HuffmanLib lib = new HuffmanLib();
			lib.convertToTree(list);												//convert the sorted linked list into a tree
			HuffmanLib.assignBinary(lib.getRoot(), list1);							//assign binary to the characters and store the binary in the nodes of list1
			BufferedWriter writer = null;
		    writer = new BufferedWriter( new FileWriter(output));
		    for(int i = 0; i < file.length(); i++){									//reads through the original file and inputs the binary digit instead of the character
		    	writer.write(list1.get(HuffmanLib.find(file.charAt(i), list1)).getBinary());
		    }
			for(HuffmanTreeNode node: list1){										//creates a table of characters and their corresponding binary code
				writer.write("Character: " + node.getC() + " Code: " + node.getBinary());
				writer.write("\n");
			}
			int original = 0;
			int encoded = 0;
			for(HuffmanTreeNode node: list1){
				original = (original + node.getFreq())*8;							//calculates the original bit size knowing that all characters are 8 bits
				encoded = encoded + (node.getBinary().length()*node.getFreq());		//calculates the encoded bit size using the length of the binary codes
			}
			writer.write("Original= " + original + " Encoded= " + encoded + " Savings= " + (original-encoded));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[] args) {
		try {
			HuffmanLib.huffmanCoder("input.txt", "output.txt");						//runs the program on input.txt and outputs the results to output.txt
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
