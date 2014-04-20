import java.lang.Comparable;

/**
 * @author ryannowacoski
 * This class represents a node within the HuffmanTree. 
 * This node can be an inner node or a leaf.
 */
public class HuffmanTreeNode implements Comparable<HuffmanTreeNode>{
	/**
	 * The character stored in the node of the tree
	 */
	private Character c = null;
	
	/**
	 * The frequency of the character
	 */
	private int freq = 0;
	
	/**
	 * The Binary code assigned to the character
	 */
	private String binary = "";
	
	/**
	 * The nodes left and right children
	 */
	private HuffmanTreeNode L = null;
	private HuffmanTreeNode R = null;
	
	/**
	 * A constructor used for non-leaf nodes
	 * @param freq- the sum of the frequency of all characters below this node
	 */
	public HuffmanTreeNode(int freq){
		this.freq = freq;
	}
	
	/**
	 * A constructor used for leaf nodes
	 * @param c- The character to be stored at the leaf
	 * @param freq- the frequency of the character in this node
	 */
	public HuffmanTreeNode(Character c, int freq){
		this.c = c;
		this.freq = freq;
	}
	
	/**
	 * Accesses and returns the binary field
	 * @return the binary code of the node
	 */
	public String getBinary() {
		return binary;
	}

	/**
	 * Accesses and sets the binary field
	 * @param binary- the binary to set
	 */
	public void setBinary(String binary) {
		this.binary = binary;
	}
	
	/**
	 * Accesses and returns the character field
	 * @return the c
	 */
	public Character getC() {
		return c;
	}
	
	/**
	 * Accesses and sets the character field
	 * @param c the c to set
	 */
	public void setC(Character c) {
		this.c = c;
	}
	
	/**
	 * Accesses and returns the frequency field
	 * @return the freq
	 */
	public int getFreq() {
		return freq;
	}
	
	/**
	 * Accesses and sets the frequency field
	 * @param freq the freq to set
	 */
	public void setFreq(int freq) {
		this.freq = freq;
	}
	
	/**
	 * Accesses and returns the left child field
	 * @return the l
	 */
	public HuffmanTreeNode getL() {
		return L;
	}
	
	/**
	 * Accesses and sets the left child field
	 * @param l the l to set
	 */
	public void setL(HuffmanTreeNode l) {
		L = l;
	}
	
	/**
	 * Accesses and returns the right child field
	 * @return the r
	 */
	public HuffmanTreeNode getR() {
		return R;
	}
	
	/**
	 * Accesses and sets the right child field
	 * @param r the r to set
	 */
	public void setR(HuffmanTreeNode r) {
		R = r;
	}
	
	/**
	 * Allows two nodes to be compared by their respective frequencies 
	 * @param arg0- the node to which it will be compared 
	 */
	@Override
	public int compareTo(HuffmanTreeNode arg0) {
		if(this.getFreq() < arg0.getFreq()){
			return -1;
		}
		else if(this.getFreq() > arg0.getFreq()){
			return 1;
		}
		else{
			return 0;
		}
	}
}
