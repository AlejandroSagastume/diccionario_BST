package com.example;

/**
 * Clase genérica que implementa un Árbol Binario de Búsqueda (BST).
 * Permite insertar, buscar y recuperar elementos de forma eficiente.
 * Los elementos deben implementar la interfaz Comparable.
 * 
 * @param <E> el tipo de elemento a almacenar en el árbol
 *           debe implementar Comparable<E>
 * 
 * @author Alejandro Sagastume
 * @version 1.0
 */
public class BinaryTree<E extends Comparable<E>> {

    /**
     * Clase interna que representa un nodo del árbol binario.
     */
    private class Node {
        /** El dato almacenado en este nodo */
        E data;
        
        /** Referencia al nodo hijo izquierdo */
        Node left;
        
        /** Referencia al nodo hijo derecho */
        Node right;

        /**
         * Constructor que crea un nuevo nodo con el dato especificado.
         * 
         * @param data el dato a almacenar en el nodo
         */
        Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    /** La raíz del árbol binario de búsqueda */
    private Node root;

    /**
     * Constructor que crea un árbol binario vacío.
     */
    public BinaryTree() {
        this.root = null;
    }

    /**
     * Inserta un elemento en el árbol binario de búsqueda.
     * Si el elemento ya existe, no se inserta duplicado.
     * 
     * @param element el elemento a insertar
     */
    public void insert(E element) {
        root = insertRecursive(root, element);
    }

    /**
     * Método auxiliar recursivo para insertar un elemento en el árbol.
     * 
     * @param node el nodo actual siendo procesado
     * @param element el elemento a insertar
     * @return el nodo procesado (puede ser nuevo o existente)
     */
    private Node insertRecursive(Node node, E element) {
        // Si el nodo es nulo, crear uno nuevo
        if (node == null) {
            return new Node(element);
        }

        // Comparar el elemento con el dato del nodo actual
        int comparison = element.compareTo(node.data);
        
        // Si es menor, insertar en el subárbol izquierdo
        if (comparison < 0) {
            node.left = insertRecursive(node.left, element);
        } 
        // Si es mayor, insertar en el subárbol derecho
        else if (comparison > 0) {
            node.right = insertRecursive(node.right, element);
        }
        // Si es igual, no se inserta (evitar duplicados)

        return node;
    }

    /**
     * Busca un elemento en el árbol binario de búsqueda.
     * 
     * @param element el elemento a buscar
     * @return true si el elemento está en el árbol, false en caso contrario
     */
    public boolean search(E element) {
        return searchRecursive(root, element);
    }

    /**
     * Método recursivo para buscar un elemento en el árbol.
     * 
     * @param node el nodo actual siendo procesado
     * @param element el elemento a buscar
     * @return true si el elemento se encuentra, false en caso contrario
     */
    private boolean searchRecursive(Node node, E element) {
        // Si el nodo es nulo, el elemento no existe
        if (node == null) {
            return false;
        }

        // Comparar el elemento con el dato del nodo actual
        int comparison = element.compareTo(node.data);
        
        // Si son iguales, elemento encontrado
        if (comparison == 0) {
            return true;
        } 
        // Si el elemento es menor, buscar en el subárbol izquierdo
        else if (comparison < 0) {
            return searchRecursive(node.left, element);
        } 
        // Si es mayor, buscar en el subárbol derecho
        else {
            return searchRecursive(node.right, element);
        }
    }

    /**
     * Obtiene un elemento del árbol que sea igual al elemento especificado.
     * 
     * @param element el elemento a buscar
     * @return el elemento encontrado o null si no existe
     */
    public E get(E element) {
        return getRecursive(root, element);
    }

    /**
     * Método auxiliar recursivo para obtener un elemento del árbol.
     * 
     * @param node el nodo actual siendo procesado
     * @param element el elemento a buscar
     * @return el elemento encontrado o null si no existe
     */
    private E getRecursive(Node node, E element) {
        // Si el nodo es nulo, elemento no encontrado
        if (node == null) {
            return null;
        }

        // Comparar el elemento con el dato del nodo actual
        int comparison = element.compareTo(node.data);
        
        // Si son iguales, devolver el elemento
        if (comparison == 0) {
            return node.data;
        } 
        // Si el elemento es menor, buscar en el subárbol izquierdo
        else if (comparison < 0) {
            return getRecursive(node.left, element);
        } 
        // Si es mayor, buscar en el subárbol derecho
        else {
            return getRecursive(node.right, element);
        }
    }

    /**
     * Realiza un recorrido In-Order del árbol (izquierda-raíz-derecha).
     * Este recorrido devuelve los elementos en orden ascendente.
     * 
     * @return StringBuilder con los elementos ordenados separados por espacios
     */
    public StringBuilder getInOrderTraversal() {
        StringBuilder sb = new StringBuilder();
        inOrderRecursive(root, sb);
        return sb;
    }

    /**
     * Método auxiliar recursivo para realizar el recorrido In-Order.
     * Procesa: subárbol izquierdo -> nodo actual -> subárbol derecho
     * 
     * @param node el nodo actual siendo procesado
     * @param sb el StringBuilder donde acumular los resultados
     */
    private void inOrderRecursive(Node node, StringBuilder sb) {
        if (node != null) {
            // Procesar subárbol izquierdo
            inOrderRecursive(node.left, sb);
            
            // Procesar nodo actual
            sb.append(node.data).append(" ");
            
            // Procesar subárbol derecho
            inOrderRecursive(node.right, sb);
        }
    }

    /**
     * Verifica si el árbol está vacío.
     * 
     * @return true si el árbol no contiene elementos, false en caso contrario
     */
    public boolean isEmpty() {
        return root == null;
    }
}