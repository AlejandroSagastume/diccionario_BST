package com.example;
public class BinaryTree<E extends Comparable<E>> {

    private class Node {
        E data;
        Node left;
        Node right;

        Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public BinaryTree() {
        this.root = null;
    }

    public void insert(E element) {
        root = insertRecursive(root, element);
    }

    private Node insertRecursive(Node node, E element) {
        if (node == null) {
            return new Node(element);
        }

        int comparison = element.compareTo(node.data);
        if (comparison < 0) {
            node.left = insertRecursive(node.left, element);
        } else if (comparison > 0) {
            node.right = insertRecursive(node.right, element);
        }

        return node;
    }

    public boolean search(E element) {
        return searchRecursive(root, element);
    }

    private boolean searchRecursive(Node node, E element) {
        if (node == null) {
            return false;
        }

        int comparison = element.compareTo(node.data);
        if (comparison == 0) {
            return true;
        } else if (comparison < 0) {
            return searchRecursive(node.left, element);
        } else {
            return searchRecursive(node.right, element);
        }
    }

    public E get(E element) {
        return getRecursive(root, element);
    }

    private E getRecursive(Node node, E element) {
        if (node == null) {
            return null;
        }

        int comparison = element.compareTo(node.data);
        if (comparison == 0) {
            return node.data;
        } else if (comparison < 0) {
            return getRecursive(node.left, element);
        } else {
            return getRecursive(node.right, element);
        }
    }

    public StringBuilder getInOrderTraversal() {
        StringBuilder sb = new StringBuilder();
        inOrderRecursive(root, sb);
        return sb;
    }

    private void inOrderRecursive(Node node, StringBuilder sb) {
        if (node != null) {
            inOrderRecursive(node.left, sb);
            sb.append(node.data).append(" ");
            inOrderRecursive(node.right, sb);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }
}