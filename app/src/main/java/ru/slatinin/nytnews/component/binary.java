package ru.slatinin.nytnews.component;

public class binary {

    private Node root;

    public boolean add(int value) {
        if (root == null) {
            root = new Node();
            root.value = value;
            return true;
        } else {
            return createNode(root, value);
        }
    }

    private boolean createNode(Node node, int value) {
        if (node.value == value) {
            return false;
        }
        if (node.value > value) {
            if (node.leftChild != null) {
                return createNode(node.leftChild, value);
            } else {
                node.leftChild = new Node();
                node.leftChild.value = value;
                return true;
            }
        } else {
            if (node.rightChild != null) {
                return createNode(node.rightChild, value);
            } else {
                node.rightChild = new Node();
                node.rightChild.value = value;
                return true;
            }
        }
    }

    private class Node {
        public int value;
        private Node leftChild;
        private Node rightChild;
    }
}
