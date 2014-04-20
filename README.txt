Ryan Nowacoski
EECS 233 Programming HW #2 
Huffman Encoding Tree

To run my file you simple input the names of the input and output text files that you want to use into huffmanCoder method in my main method 
and the program will read the input file and output the encoded text, a table of characters to binary and calculated savings to your specified
out put file. 

Question 1: There is no next field because this field is handled by Java's LinkedList implementation. 

Question 2: I chose linked list so that their would be no size restrictions that would need to be handled. I saw a size restriction as an issue
because we do not know what character set is used in any given text file. My intention was that this would be able to encode ASCII and non-ASCII
characters. With an array you would need to set the array to hold all possible characters which could result in a large amount of wasted space.

