package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas unitarias para la clase Association.
 * 
 * Prueba los métodos principales:
 * - Crear una asociación
 * - Obtener la clave
 * - Obtener el valor
 * - Comparar asociaciones (para búsqueda y ordenamiento en BST)
 * 
 * Según la Hoja de Trabajo #7, se requieren pruebas para insertar y buscar
 * una ASOCIACIÓN en el árbol.
 * 
 * @author Alejandro Sagastume
 * @version 1.0
 * 
 * @see Association
 */
@DisplayName("Pruebas Unitarias de Association")
public class AssociationTest {

    /** Asociación para usar en las pruebas */
    private Association<String, String> association;

    /**
     * Se ejecuta antes de cada prueba.
     * Inicializa una nueva asociación.
     */
    @BeforeEach
    public void setUp() {
        association = new Association<>("house", "casa");
    }

    // ========== PRUEBAS DE CREACIÓN Y GETTERS ==========

    /**
     * Prueba 1: Crear una asociación con clave y valor.
     * Verifica que se almacenan correctamente.
     */
    @Test
    @DisplayName("Crear una asociacion con clave y valor")
    public void testCreateAssociation() {
        // Acción: crear una asociación
        Association<String, String> assoc = new Association<>("dog", "perro");
        
        // Verificación: verificar clave y valor
        assertEquals("dog", assoc.getKey(), "La clave debe ser 'dog'");
        assertEquals("perro", assoc.getValue(), "El valor debe ser 'perro'");
    }

    /**
     * Prueba 2: Obtener la clave de una asociación.
     */
    @Test
    @DisplayName("Obtener la clave de una asociacion")
    public void testGetKey() {
        // Verificación: getKey debe retornar la clave correcta
        assertEquals("house", association.getKey(), "Debe retornar 'house'");
    }

    /**
     * Prueba 3: Obtener el valor de una asociación.
     */
    @Test
    @DisplayName("Obtener el valor de una asociacion")
    public void testGetValue() {
        // Verificación: getValue debe retornar el valor correcto
        assertEquals("casa", association.getValue(), "Debe retornar 'casa'");
    }

    /**
     * Prueba 4: Crear múltiples asociaciones diferentes.
     */
    @Test
    @DisplayName("Crear multiples asociaciones")
    public void testCreateMultipleAssociations() {
        // Acción: crear varias asociaciones
        Association<String, String> assoc1 = new Association<>("dog", "perro");
        Association<String, String> assoc2 = new Association<>("cat", "gato");
        Association<String, String> assoc3 = new Association<>("bird", "pajaro");
        
        // Verificación: todas deben tener sus valores correctos
        assertEquals("dog", assoc1.getKey());
        assertEquals("perro", assoc1.getValue());
        
        assertEquals("cat", assoc2.getKey());
        assertEquals("gato", assoc2.getValue());
        
        assertEquals("bird", assoc3.getKey());
        assertEquals("pajaro", assoc3.getValue());
    }

    // ========== PRUEBAS DE COMPARACIÓN PARA BÚSQUEDA ==========

    /**
     * Prueba 5: Comparar dos asociaciones con la misma clave.
     * Para búsqueda en BST: claves iguales deben comparar como 0.
     */
    @Test
    @DisplayName("Comparar asociaciones con misma clave (para busqueda)")
    public void testCompareEqualKeys() {
        // Arranque: crear dos asociaciones con la misma clave
        Association<String, String> assoc1 = new Association<>("house", "casa");
        Association<String, String> assoc2 = new Association<>("house", "hogar");
        
        // Verificación: deben comparar como iguales (para búsqueda en BST)
        assertEquals(0, assoc1.compareTo(assoc2), 
            "Claves iguales deben comparar como 0 para busqueda correcta");
    }

    /**
     * Prueba 6: Comparar asociaciones donde la primera clave es menor.
     * Para inserción en BST: menor debe retornar valor negativo.
     */
    @Test
    @DisplayName("Comparar donde primera clave es menor (para insercion)")
    public void testCompareLessThan() {
        // Arranque: crear dos asociaciones con claves diferentes
        Association<String, String> assoc1 = new Association<>("apple", "manzana");
        Association<String, String> assoc2 = new Association<>("dog", "perro");
        
        // Verificación: apple < dog, debe retornar negativo
        assertTrue(assoc1.compareTo(assoc2) < 0, 
            "apple debe ser menor que dog para insercion correcta en BST");
    }

    /**
     * Prueba 7: Comparar asociaciones donde la primera clave es mayor.
     * Para inserción en BST: mayor debe retornar valor positivo.
     */
    @Test
    @DisplayName("Comparar donde primera clave es mayor (para insercion)")
    public void testCompareGreaterThan() {
        // Arranque: crear dos asociaciones con claves diferentes
        Association<String, String> assoc1 = new Association<>("zebra", "cebra");
        Association<String, String> assoc2 = new Association<>("apple", "manzana");
        
        // Verificación: zebra > apple, debe retornar positivo
        assertTrue(assoc1.compareTo(assoc2) > 0, 
            "zebra debe ser mayor que apple para insercion correcta en BST");
    }

    /**
     * Prueba 8: Comparación insensible a mayúsculas (requisito del documento).
     * El documento indica: "no debe importar si la palabra está en mayúscula o minúscula".
     */
    @Test
    @DisplayName("Comparacion insensible a mayusculas (requisito del documento)")
    public void testCompareIgnoreCase() {
        // Arranque: crear asociaciones con mayúsculas/minúsculas diferentes
        Association<String, String> assoc1 = new Association<>("HOUSE", "casa");
        Association<String, String> assoc2 = new Association<>("house", "hogar");
        
        // Verificación: deben comparar como iguales
        assertEquals(0, assoc1.compareTo(assoc2), 
            "HOUSE y house deben ser iguales en busqueda (requisito del documento)");
    }

    /**
     * Prueba 9: Comparación insensible a acentos.
     * Verificar que "hábía" = "habia" para búsqueda correcta.
     */
    @Test
    @DisplayName("Comparacion insensible a acentos")
    public void testCompareIgnoreAccents() {
        // Arranque: crear asociaciones con y sin acentos
        Association<String, String> assoc1 = new Association<>("hábía", "sin acento");
        Association<String, String> assoc2 = new Association<>("habia", "sin acento");
        
        // Verificación: deben comparar como iguales
        assertEquals(0, assoc1.compareTo(assoc2), 
            "hábía y habia deben ser iguales en busqueda");
    }

    /**
     * Prueba 10: Comparación combinada: mayúsculas y acentos.
     * Ejemplo: ESPAÑA = españa = españa (para búsqueda en diccionario).
     */
    @Test
    @DisplayName("Comparacion combinada: mayusculas y acentos")
    public void testCompareCaseAndAccents() {
        // Arranque: crear asociaciones con diferentes mayúsculas y acentos
        Association<String, String> assoc1 = new Association<>("ESPANA", "pais");
        Association<String, String> assoc2 = new Association<>("espana", "pais");
        Association<String, String> assoc3 = new Association<>("EsPana", "pais");
        
        // Verificación: todas deben comparar como iguales
        assertEquals(0, assoc1.compareTo(assoc2), 
            "ESPANA y espana deben ser iguales");
        assertEquals(0, assoc2.compareTo(assoc3), 
            "espana y EsPana deben ser iguales");
        assertEquals(0, assoc1.compareTo(assoc3), 
            "ESPANA y EsPana deben ser iguales");
    }

    /**
     * Prueba 11: Comparación alfabética para ordenamiento.
     * Verificar que el ordenamiento In-Order funciona correctamente.
     */
    @Test
    @DisplayName("Comparacion alfabetica para ordenamiento In-Order")
    public void testCompareAlphabeticalOrder() {
        // Arranque: crear asociaciones del diccionario del documento
        Association<String, String> dog = new Association<>("dog", "perro");
        Association<String, String> homework = new Association<>("homework", "tarea");
        Association<String, String> house = new Association<>("house", "casa");
        Association<String, String> town = new Association<>("town", "pueblo");
        Association<String, String> woman = new Association<>("woman", "mujer");
        Association<String, String> yes = new Association<>("yes", "si");
        
        // Verificación: comprobar orden alfabético
        assertTrue(dog.compareTo(homework) < 0, "dog < homework");
        assertTrue(homework.compareTo(house) < 0, "homework < house");
        assertTrue(house.compareTo(town) < 0, "house < town");
        assertTrue(town.compareTo(woman) < 0, "town < woman");
        assertTrue(woman.compareTo(yes) < 0, "woman < yes");
    }

    /**
     * Prueba 12: Comparación con palabras del ejemplo del documento.
     * Usando las palabras del ejemplo: house, dog, homework, woman, town, yes.
     */
    @Test
    @DisplayName("Comparacion con palabras del ejemplo del documento")
    public void testCompareWithDocumentExample() {
        // Arranque: crear asociaciones del ejemplo del documento
        Association<String, String> house = new Association<>("house", "casa");
        Association<String, String> homework = new Association<>("homework", "tarea");
        
        // Verificación: homework < house (para búsqueda correcta)
        assertTrue(homework.compareTo(house) < 0, 
            "homework debe ser menor que house para BST correcto");
    }

    // ========== PRUEBAS DE REPRESENTACIÓN ==========

    /**
     * Prueba 13: toString debe retornar formato (clave: valor).
     * Útil para verificar que la asociación se representa correctamente.
     */
    @Test
    @DisplayName("toString retorna formato (clave: valor)")
    public void testToString() {
        // Verificación: toString debe tener formato correcto
        String result = association.toString();
        assertEquals("(house: casa)", result, 
            "Debe retornar formato (clave: valor)");
    }

    /**
     * Prueba 14: toString con ejemplo del documento.
     */
    @Test
    @DisplayName("toString con ejemplo del documento")
    public void testToStringDocumentExample() {
        // Arranque: crear asociación del documento
        Association<String, String> dog = new Association<>("dog", "perro");
        
        // Verificación: formato correcto
        assertEquals("(dog: perro)", dog.toString(), 
            "Debe mostrar (dog: perro)");
    }
}