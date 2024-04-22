import java.io.*;
import java.util.*;

public class MultiwayTrie {

    public int phraseNumCounter = 0;

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
            lastValue = fullValue.substring(fullValue.length() - 1);
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

    //Root node, the start of the trie
    Node root;

    //Insert a value into the multiway trie
    public void insert(String value) {

        //If the root is null, initialise the root node
        if (root == null) { root = new Node(null, phraseNumCounter); }

        //Call the recursive insert function
        insertR(root, value);
    }

    /*
     * Inserts a value into the multiway trie by recursively going down node levels to each the place to insert
     */
    private void insertR(Node parent, String value) {

        //Grabs the children list from the parent node
        List<Node> children = parent.getChildren();

        //If the value length is greater than any of the children, then we likely need to move down to a child node
        if (value.length() > children.get(0).getFullValue().length()) {
            
            //For each of the nodes in the parent list
            for (Node node : children) {

                //If the value we have starts with the entirety of the child node
                if (value.startsWith(node.fullValue)) {

                    //Then we wish to build off of this node, so recursively call this method again but with the parent node being the current node  
                    insertR(node, value);
                    return;
                }
            }

        } else {
            
            //Else, we will likely insert the phrase in this level as a new node
            phraseNumCounter++;
            parent.AddChild(new Node(value, phraseNumCounter));
        }
    }

    public boolean find(String value) {

        //If the root is null, initialise the root node
        if (root == null) { return false; }

        //Call the recursive insert function
        return findR(root, value);
    }

    private boolean findR(Node parent, String value) {

        //Grabs the children list from the parent node
        List<Node> children = parent.getChildren();

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

        //If we have made it here, then there is no match so return false
        return false;
    }
}