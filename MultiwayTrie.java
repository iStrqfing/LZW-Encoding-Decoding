import java.io.*;
import java.util.*;

public class MultiwayTrie {

    class Node {

        //Node stores the full value, last character in value (appended character) and the following nodes which build off of this node
        private String fullValue;
        private String lastValue;
        private int phraseNum;
        private List<Node> children;
    
        /*
         * Constructor - Initialises values
         * Takes full value as input and the phrase number
         */
        public Node(String fullValue, int phraseNum) {

            //Set full value and phraseNum to the two values passed in
            this.fullValue = fullValue;
            this.phraseNum = phraseNum;

            //Set last value to the last character in the full string and initialise the children list
            if (fullValue == null) {

                lastValue = null;
            } else {
            
                lastValue = fullValue.substring(fullValue.length() - 1);
            }
            
            children = new ArrayList<Node>();
        }

        /*
         * Returns the list of child nodes of the current node
         */
        public List<Node> getChildren() {
            return children;
        }

        /*
         * Returns the full string value of the current node
         */
        public String getFullValue() {
            return fullValue;
        }

        /*
         * Adds a node as a child to the childrens list
         */
        public void AddChild(Node node) {
            children.add(node);
        }
    }

    //Phrase Number Counter - counts which phrase number we are up to
    public int phraseNumCounter = -1;

    //Root node, the start of the trie
    Node root;

    /*
     * Constructor - Initialises the trie
     * Adds all hexadecimal digits to the trie
     */
    public MultiwayTrie() {

        //Create a list of hexadecimal digits
    String[] hexDigits = new String[] {/* "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", */"A", "B", "C", "D"/*, "E", "F"*/};

        //Initialise the trie by inserting all the hex digits already into the trie
        for (String digit : hexDigits) {
            insert(digit);
        }
    }

    /*
     * Insert a value into the multiway trie
     * Takes a phrase value as input
     * Returns phrase number of parent of our inserted node
     */
    public int insert(String value) {

        //If the root is null, initialise the root node
        if (root == null) { root = new Node(null, phraseNumCounter); }

        //Call the recursive insert function. Returns phrase number of parent of our inserted node
        return insertR(root, value);
    }

    /*
     * Insert a value into the multiway trie
     * Takes parent phrase number and a character as arguments
     * Returns String of full phrase value
     */
    public String insert(int parentPhraseNum, String character) {

        //If the root is null, initialise the root node
        if (root == null) { root = new Node(null, phraseNumCounter); }

        //Call the recursive insert function
        return insertR(root, parentPhraseNum, character, "");
    }

    /*
     * Inserts a value into the multiway trie by recursively going down node levels to each the place to insert
     * Takes parent node and string value as input
     * Returns phrase number of parent
     */
    private int insertR(Node parent, String value) {

        //Grabs the children list from the parent node
        List<Node> children = parent.getChildren();

        //If there is at least one child to the parent node
        if (children.size() >= 1) {
        
            //If the value length is greater than any of the children, then we likely need to move down to a child node
            if (value.length() > children.get(0).getFullValue().length()) {
                
                //For each of the nodes in the parent list
                for (Node node : children) {

                    //If the value we have starts with the entirety of the child node
                    if (value.startsWith(node.fullValue)) {

                        //Then we wish to build off of this node, so recursively call this method again but with the parent node being the current node  
                        return insertR(node, value);
                    }
                }

            } else {
                
                //Else, we will likely insert the phrase in this level as a new node
                insertNodeHere(parent, value);
            }

        } else {
            
            //Else, the parent node has no children (no more levels to move down) so add a node here
            insertNodeHere(parent, value);
        }

        //Returns the phrase number of the parent of our inserted node
        return parent.phraseNum;
    }

    /*
     * Inserts a value into the multiway trie by recursively going down node levels to each the place to insert
     * Takes a parent node, phrase number and character as input
     * Returns String of the whole new phrase. Is null if phrase is yet to be inserted
     */
    public String insertR(Node parent, int parentNum, String character, String prevChars) {

        //If the parent phrase number equals the desired phrase number we wish to expand on
        if (parent.phraseNum == parentNum) {
            
            //Insert the node here
            insertNodeHere(parent, prevChars + character);
            return prevChars + character;

        } else {

            //Grab the children of the node we currently have
            List<Node> children = parent.getChildren();
            String fullValue = null;

            //For each of the children
            for (Node node : children) {
                
                //Try to insert the node under one of their children
                fullValue = insertR(node, parentNum, character, prevChars + node.lastValue);

                //If the node has been inserted, return true
                if (fullValue != null) { return fullValue; }
            }
        }
        
        //If node has not been inserted, then we are at the wrong level so return null
        return null;
    }

    /*
     * Inserts a node in the children list of the specified parent
     */
    private void insertNodeHere(Node parent, String value) {

        //Increase the phrase num counter and add a child node to the parent
        phraseNumCounter++;
        parent.AddChild(new Node(value, phraseNumCounter));
    }

    /*
     * Finds whether there is a phrase already in the trie
     * Takes the specified phrase as input
     * Returns true if the phrase is in the trie
     */
    public boolean find(String value) {

        //If the root is null, initialise the root node
        if (root == null) { return false; }

        //Call the recursive insert function
        return findR(root, value);
    }

    /*
     * Finds whether there is a phrase already in the trie by recursively moving down node levels
     * Takes a parent node and the desired phrase as input
     * Returns true if the specified phrase is in the trie
     */
    private boolean findR(Node parent, String value) {

        //Grabs the children list from the parent node
        List<Node> children = parent.getChildren();

        //If there is at least one child to the parent node
        if (children.size() >= 1) {

            //If the value length is greater than any of the children, then we likely need to move down to a child node
            if (value.length() > children.get(0).getFullValue().length()) {
                
                //For each of the nodes in the parent list
                for (Node node : children) {

                    //If the value we have starts with the entirety of the child node
                    if (value.startsWith(node.fullValue)) {

                        //Then we wish to build off of this node, so recursively call this method again but with the parent node being the current node  
                        return findR(node, value);
                    }
                }

            } else {
                
                //Else, we will likely find the phrase in this level as a node
                //For each node in the children list
                for (Node node : children) {

                    //If we find a match, return true
                    if (value.equals(node.fullValue)) { return true; }
                }
            }
        }

        //If we have made it here, then there is no match so return false
        return false;
    }
}