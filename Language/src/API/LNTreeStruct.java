package API;


import java.io.File;
import java.nio.file.Files;

interface LNTree<E> {
    Node addNode(Node current, int value);
    Node deleteNode(Node current, int value);
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

    @Override
    public Node addNode(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addNode(current.left, value);
        } else if (value > current.value) {
            current.right = addNode(current.right, value);
        } else {
            // value already exists
            return current;
        }

        return current;
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

    @Override
    public Node deleteNode(Node current, int value) {
        if (current == null) {
            return null;
        }

        if (value == current.value) {
            current = null;
        }
        if (value < current.value) {
            current.left = deleteNode(current.left, value);
            return current;
        }
        current.right = deleteNode(current.right, value);
        return current;
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