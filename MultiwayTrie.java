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
         * Adds a node as a child to the childrens list
         */
        public void AddChild(Node node) {
            children.add(node);
        }
    }

    private int MAX_TRIE_SIZE = 10000000;

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
    String[] hexDigits = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

        //Initialise the trie by inserting all the hex digits already into the trie
        for (String digit : hexDigits) {
            insert(digit);
        }
    }

    /*
     * Insert a value into the multiway trie
     * Takes a phrase value as input
     * Returns phrase number of parent of our inserted node
     * Returns -1 if phrase already exists
     */
    public int insert(String value) {

        //If the root is null, initialise the root node
        if (root == null) { root = new Node(null, phraseNumCounter); }

        // //Call the recursive insert function. Returns phrase number of parent of our inserted node
        // return insertR(root, value);

        //Boolean states whether node has been inserted or not
        boolean finished = false;
        int parentPhraseNum = -1;

        Node parent = root;


        //While the node is yet to be inserted
        while(!finished) {

            //Grabs the children list from the parent node
            List<Node> children = parent.children;

            if (children.size() < 1) {
                
                //If there are no children, check whether we have reached max trie size
                if (phraseNumCounter < MAX_TRIE_SIZE) {
                    
                    //If we have not reached max trie size, insert node here
                    insertNodeHere(parent, value);

                }
                
                //Set return value to parent phrase number and finished to true
                parentPhraseNum = parent.phraseNum;                
                finished = true;
                

            } else {
                
                if (value.length() <= children.get(0).fullValue.length()) {

                    //If value is of same length as children, try to see whether value is already at this level
                    boolean found = searchChildren(value, children);
                        
                    if (!found) {

                        //If phrase not found, check whether we have reached max trie size before inserting
                        if (phraseNumCounter < MAX_TRIE_SIZE) {

                            //Else, if value of same length as children, insert node here. inserted = true
                            insertNodeHere(parent, value);
                        }

                        //Set parent phrase num variable to actual parent phrase num
                        parentPhraseNum = parent.phraseNum;
                    }

                    //Mark that the job is finished
                    finished = true;

                } else {
                    
                    //Else, for each of the parent's children
                    for (Node node : children) {
                        
                        if (value.startsWith(node.fullValue)) {

                            //If the value starts with the full value of the current node, set the new parent to be the current node
                            parent = node;
                            break;
                        }
                    }
                }
            }
        }

        //Returns the phrase number of the parent of our inserted node
        return parentPhraseNum;

     

        
    }

    /*
     * Finds whether a value is amongst a list of children
     * Returns true if a child matches the desired value
     * Returns false if no children match desired value
     */
    private boolean searchChildren(String value, List<Node> children) {

        //For each of the children
        for (Node node : children) {

            //If the child matches the desired string value, return true
            if (value.equals(node.fullValue)) {
                return true;    
            }
        }

        //Else, return false
        return false;
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
    // private int insertR(Node parent, String value) {

    //     // //Grabs the children list from the parent node
    //     // List<Node> children = parent.children;

    //     // //If there is at least one child to the parent node
    //     // if (children.size() >= 1) {
        
    //     //     //If the value length is greater than any of the children, then we likely need to move down to a child node
    //     //     if (value.length() > children.get(0).fullValue.length()) {
                
    //     //         //For each of the nodes in the parent list
    //     //         for (Node node : children) {

    //     //             //If the value we have starts with the entirety of the child node
    //     //             if (value.startsWith(node.fullValue)) {

    //     //                 //Then we wish to build off of this node, so recursively call this method again but with the parent node being the current node  
    //     //                 return insertR(node, value);
    //     //             }
    //     //         }

    //     //     } else {
                
    //     //         //Else, we will likely insert the phrase in this level as a new node
    //     //         insertNodeHere(parent, value);
    //     //     }

    //     // } else {
            
    //     //     //Else, the parent node has no children (no more levels to move down) so add a node here
    //     //     insertNodeHere(parent, value);
    //     // }

    //     /////////////////////////////////////////////////////////////////

    //     //Boolean states whether node has been inserted or not
    //     boolean inserted = false;

    //     //While the node is yet to be inserted
    //     while(!inserted) {

    //         //Grabs the children list from the parent node
    //         List<Node> children = parent.children;

    //         if (children.size() < 1) {
                
    //             //If the children list size is less than one, insert node here. Mark inserted as true
    //             insertNodeHere(parent, value);
    //             inserted = true;

    //         } else {
                
    //             if (value.length() <= children.get(0).fullValue.length()) {

    //                 //Else, if value of same length as children, insert node here. inserted = true
    //                 insertNodeHere(parent, value);
    //                 inserted = true;

    //             } else {
                    
    //                 //Else, for each of the parent's children
    //                 for (Node node : children) {
                        
    //                     if (value.startsWith(node.fullValue)) {

    //                         //If the value starts with the full value of the current node, set the new parent to be the current node
    //                         parent = node;
    //                         break;
    //                     }
    //                 }
    //             }
    //         }
    //     }
    //     //////////////////////////////////////////////////////////////////

    //     //Returns the phrase number of the parent of our inserted node
    //     return parent.phraseNum;
    // }

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
            List<Node> children = parent.children;
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
     * Returns phraseNum if the phrase is in the trie, -1 otherwise
     */
    public int find(String value) {

        //If the root is null, initialise the root node
        if (root == null) { return -1; }

        // //Call the recursive insert function
        // return findR(root, value);

        Node parent = root;
        boolean searching = true;

        //While the node is yet to be inserted
        while(searching) {

            //Grabs the children list from the parent node
            List<Node> children = parent.children;

            if (children.size() < 1) {
                
                searching = false;

            } else {
                
                if (value.length() <= children.get(0).fullValue.length()) {

                    //Else, we will likely find the phrase in this level as a node
                    //For each node in the children list
                    for (Node node : children) {

                        //If we find a match, return true
                        if (value.equals(node.fullValue)) { return node.phraseNum; }
                    }

                    searching = false;

                } else {
                    
                    //For each of the nodes in the parent list
                    for (Node node : children) {

                        //If the value we have starts with the entirety of the child node
                        if (value.startsWith(node.fullValue)) {

                            //Then we wish to build off of this node, so recursively call this method again but with the parent node being the current node  
                            parent = node;
                        }
                    }
                }
            }
        }

        return -1;
    }

    /*
     * Finds whether there is a phrase already in the trie
     * Takes phrase number as input
     * Returns true if the phrase is in the trie, false otherwise
     */
    public boolean find(int phraseNum) {

        //If phrase number is less than or equal to the phrase number counter, return true. Otherwise false
        if (phraseNum <= phraseNumCounter) { return true; }
        else { return false; }
    }

    /*
     * Finds whether there is a phrase already in the trie by recursively moving down node levels
     * Takes a parent node and the desired phrase as input
     * Returns true if the specified phrase is in the trie
     */
    private int findR(Node parent, String value) {

        //Grabs the children list from the parent node
        List<Node> children = parent.children;

        //If there is at least one child to the parent node
        if (children.size() >= 1) {

            //If the value length is greater than any of the children, then we likely need to move down to a child node
            if (value.length() > children.get(0).fullValue.length()) {
                
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
                    if (value.equals(node.fullValue)) { return node.phraseNum; }
                }
            }
        }

        //If we have made it here, then there is no match so return false
        return -1;
    }

    public String retrieve(int phraseNum) {

        if (root == null) { return null; }
        
        return retrieveR(phraseNum, root);
    }


    public String retrieveR(int phraseNum, Node parent) {

        //If the parent phrase number equals the desired phrase number we wish to expand on
        if (parent.phraseNum == phraseNum) {
            
            //Return the parent's full value
            return parent.fullValue;

        } else {

            //Grab the children of the node we currently have
            List<Node> children = parent.children;
            String fullValue = null;

            //For each of the children
            for (Node node : children) {
                
                //Try to retrieve the node under one of their children
                fullValue = retrieveR(phraseNum, node);

                //If the node has been retrieved, return true
                if (fullValue != null) { return fullValue; }
            }
        }
        
        //If node has not been retrieved, then we are at the wrong level so return null
        return null;
    }
}