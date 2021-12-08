package API;


import java.io.File;
import java.nio.file.Files;

interface LNTree<E> {
    boolean containsNode(Node current, int value);

}
class Node {
    int value;
    Node left;
    Node right;

    Node(int value) {
        this.value = value;
        right = null;
        left = null;
    }
}


public class LNTreeStruct implements LNTree {
    private Node root;

    public void BinaryTree(Node root) {
        this.setRoot(root);
    }


    public void BinaryTree() {
        this.setRoot(null);
    }


    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }


    private Node addRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value);
        } else {
            // value already exists
            return current;
        }

        return current;
    }

    public void add(int value) {
        root = addRecursive(root, value);
    }

    @Override
    public boolean containsNode(Node current, int value) {
        if (current == null) {
            return false;
        }
        if (value == current.value) {
            return true;
        }
        return value < current.value
                ? containsNode(current.left, value)
                : containsNode(current.right, value);
    }


    @SuppressWarnings("unused")
    private Node deleteRecursive(Node current, int value) {
        if (current == null) {
            return null;
        }

        if (value == current.value) {
            // Node to delete found
            // ... code to delete the node will go here
        }
        if (value < current.value) {
            current.left = deleteRecursive(current.left, value);
            return current;
        }
        current.right = deleteRecursive(current.right, value);
        return current;
    }

    public void delete(int value) {
        root = deleteRecursive(root, value);
    }

    public String toString(Node root){
        String result = "";
        if (root == null)
            return "";
        result += toString(root.left);
        result += toString(root.right);
        result += Integer.toString(root.value);
        return result;
    }




}