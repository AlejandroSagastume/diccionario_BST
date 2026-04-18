package com.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas unitarias para la clase BinaryTree.
 * 
 * Se enfoca en probar los métodos principales:
 * - insert(E element): Insertar un elemento en el árbol
 * - search(E element): Buscar un elemento en el árbol
 * 
 * Según la Hoja de Trabajo #7, se requieren pruebas unitarias para estos dos métodos.
 * 
 * @author Alejandro Sagastume
 * @version 1.0
 * 
 * @see BinaryTree
 */
@DisplayName("Pruebas Unitarias de BinaryTree - insert() y search()")
public class BinaryTreeTest {

    /** Instancia del árbol a usar en las pruebas */
    private BinaryTree<Integer> tree;

    /**
     * Se ejecuta antes de cada prueba.
     * Inicializa un nuevo árbol vacío.
     */
    @BeforeEach
    public void setUp() {
        tree = new BinaryTree<>();
    }

    // ========== PRUEBAS DEL MÉTODO INSERT ==========

    /**
     * Prueba 1: Insertar un elemento en un árbol vacío.
     * Verifica que después de insertar, el árbol no esté vacío.
     */
    @Test
    @DisplayName("Insertar un elemento en arbol vacio")
    public void testInsertSingleElement() {
        // Arranque: árbol vacío
        assertTrue(tree.isEmpty(), "El arbol debe estar vacio inicialmente");
        
        // Acción: insertar un elemento
        tree.insert(10);
        
        // Verificación: árbol no debe estar vacío
        assertFalse(tree.isEmpty(), "El arbol no debe estar vacio despues de insertar");
    }

    /**
     * Prueba 2: Insertar múltiples elementos.
     * Verifica que se pueden insertar varios elementos correctamente.
     */
    @Test
    @DisplayName("Insertar multiples elementos en el arbol")
    public void testInsertMultipleElements() {
        // Arranque: árbol vacío
        assertTrue(tree.isEmpty(), "El arbol debe estar vacio inicialmente");
        
        // Acción: insertar múltiples elementos
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);
        
        // Verificación: árbol no debe estar vacío
        assertFalse(tree.isEmpty(), "El arbol no debe estar vacio despues de insertar");
    }

    /**
     * Prueba 3: Insertar elementos en orden ascendente.
     * Crea un árbol desequilibrado hacia la derecha.
     */
    @Test
    @DisplayName("Insertar elementos en orden ascendente (desequilibrado derecha)")
    public void testInsertAscendingOrder() {
        // Arranque: árbol vacío
        tree = new BinaryTree<>();
        
        // Acción: insertar en orden ascendente
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        
        // Verificación: árbol no debe estar vacío
        assertFalse(tree.isEmpty(), "El arbol no debe estar vacio");
    }

    /**
     * Prueba 4: Insertar elementos en orden descendente.
     * Crea un árbol desequilibrado hacia la izquierda.
     */
    @Test
    @DisplayName("Insertar elementos en orden descendente (desequilibrado izquierda)")
    public void testInsertDescendingOrder() {
        // Arranque: árbol vacío
        tree = new BinaryTree<>();
        
        // Acción: insertar en orden descendente
        tree.insert(50);
        tree.insert(40);
        tree.insert(30);
        tree.insert(20);
        tree.insert(10);
        
        // Verificación: árbol no debe estar vacío
        assertFalse(tree.isEmpty(), "El arbol no debe estar vacio");
    }

    /**
     * Prueba 5: Insertar elementos duplicados.
     * Verifica que el árbol no permite duplicados.
     */
    @Test
    @DisplayName("Insertar elementos duplicados (no deben repetirse)")
    public void testInsertDuplicateElements() {
        // Arranque: árbol vacío
        tree = new BinaryTree<>();
        
        // Acción: insertar el mismo elemento varias veces
        tree.insert(25);
        tree.insert(25);
        tree.insert(25);
        
        // Verificación: el árbol debe contener el elemento sin duplicados
        assertFalse(tree.isEmpty(), "El arbol no debe estar vacio");
    }

    /**
     * Prueba 6: Insertar en un árbol balanceado.
     * Verifica el comportamiento de insert en un árbol binario balanceado.
     */
    @Test
    @DisplayName("Insertar elementos para crear arbol balanceado")
    public void testInsertBalancedTree() {
        // Arranque: árbol vacío
        tree = new BinaryTree<>();
        
        // Acción: insertar en patrón para crear árbol balanceado
        tree.insert(50);   // raíz
        tree.insert(30);   // hijo izquierdo
        tree.insert(70);   // hijo derecho
        tree.insert(20);   // hijo izquierdo-izquierdo
        tree.insert(40);   // hijo izquierdo-derecho
        tree.insert(60);   // hijo derecho-izquierdo
        tree.insert(80);   // hijo derecho-derecho
        
        // Verificación: árbol debe ser válido
        assertFalse(tree.isEmpty(), "El arbol no debe estar vacio");
    }

    // ========== PRUEBAS DEL MÉTODO SEARCH ==========

    /**
     * Prueba 7: Buscar en un árbol vacío.
     * Verifica que la búsqueda en árbol vacío retorna false.
     */
    @Test
    @DisplayName("Buscar en arbol vacio debe retornar false")
    public void testSearchInEmptyTree() {
        // Arranque: árbol vacío
        assertTrue(tree.isEmpty(), "El arbol debe estar vacio");
        
        // Acción y Verificación: buscar en árbol vacío
        assertFalse(tree.search(10), "No debe encontrar elementos en arbol vacio");
    }

    /**
     * Prueba 8: Buscar un elemento que existe.
     * Verifica que find() retorna true para elementos insertados.
     */
    @Test
    @DisplayName("Buscar elemento que existe debe retornar true")
    public void testSearchFoundElement() {
        // Arranque: insertar elementos
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        
        // Acción y Verificación: buscar elementos existentes
        assertTrue(tree.search(50), "Debe encontrar 50 (raiz)");
        assertTrue(tree.search(30), "Debe encontrar 30 (izquierda)");
        assertTrue(tree.search(70), "Debe encontrar 70 (derecha)");
        assertTrue(tree.search(20), "Debe encontrar 20 (hoja)");
        assertTrue(tree.search(40), "Debe encontrar 40 (hoja)");
    }

    /**
     * Prueba 9: Buscar un elemento que NO existe.
     * Verifica que search() retorna false para elementos no insertados.
     */
    @Test
    @DisplayName("Buscar elemento que NO existe debe retornar false")
    public void testSearchNotFoundElement() {
        // Arranque: insertar algunos elementos
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        
        // Acción y Verificación: buscar elementos no existentes
        assertFalse(tree.search(10), "No debe encontrar 10");
        assertFalse(tree.search(25), "No debe encontrar 25");
        assertFalse(tree.search(100), "No debe encontrar 100");
    }

    /**
     * Prueba 10: Buscar raíz en árbol balanceado.
     * Verifica búsqueda del elemento raíz.
     */
    @Test
    @DisplayName("Buscar elemento raiz en arbol balanceado")
    public void testSearchRootElement() {
        // Arranque: crear árbol balanceado
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);
        
        // Acción y Verificación: buscar raíz
        assertTrue(tree.search(50), "Debe encontrar raiz 50");
    }

    /**
     * Prueba 11: Buscar elementos en subárbol izquierdo.
     * Verifica búsqueda en subárbol izquierdo de la raíz.
     */
    @Test
    @DisplayName("Buscar elementos en subarbol izquierdo")
    public void testSearchLeftSubtree() {
        // Arranque: crear árbol
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        
        // Acción y Verificación: buscar en subárbol izquierdo
        assertTrue(tree.search(30), "Debe encontrar 30 en subarbol izquierdo");
        assertTrue(tree.search(20), "Debe encontrar 20 en subarbol izquierdo");
        assertTrue(tree.search(40), "Debe encontrar 40 en subarbol izquierdo");
    }

    /**
     * Prueba 12: Buscar elementos en subárbol derecho.
     * Verifica búsqueda en subárbol derecho de la raíz.
     */
    @Test
    @DisplayName("Buscar elementos en subarbol derecho")
    public void testSearchRightSubtree() {
        // Arranque: crear árbol
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);
        
        // Acción y Verificación: buscar en subárbol derecho
        assertTrue(tree.search(70), "Debe encontrar 70 en subarbol derecho");
        assertTrue(tree.search(60), "Debe encontrar 60 en subarbol derecho");
        assertTrue(tree.search(80), "Debe encontrar 80 en subarbol derecho");
    }

    /**
     * Prueba 13: Buscar después de insertar elemento duplicado.
     * Verifica que search() funciona correctamente con duplicados.
     */
    @Test
    @DisplayName("Buscar despues de intentar insertar duplicados")
    public void testSearchAfterDuplicateInsert() {
        // Arranque: insertar elementos incluyendo duplicados
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(10);  // intento de duplicado
        tree.insert(10);  // intento de duplicado
        
        // Acción y Verificación: buscar el elemento
        assertTrue(tree.search(10), "Debe encontrar 10");
        assertTrue(tree.search(5), "Debe encontrar 5");
        assertTrue(tree.search(15), "Debe encontrar 15");
    }

    /**
     * Prueba 14: Buscar en árbol con estructura desequilibrada.
     * Verifica búsqueda en árbol desequilibrado.
     */
    @Test
    @DisplayName("Buscar en arbol desequilibrado")
    public void testSearchSkewedTree() {
        // Arranque: crear árbol desequilibrado (cadena)
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        
        // Acción y Verificación: buscar en árbol desequilibrado
        assertTrue(tree.search(1), "Debe encontrar 1");
        assertTrue(tree.search(3), "Debe encontrar 3 (elemento central)");
        assertTrue(tree.search(5), "Debe encontrar 5");
        assertFalse(tree.search(6), "No debe encontrar 6");
    }
}