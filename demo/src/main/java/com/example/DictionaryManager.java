package com.example;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Clase que gestiona la operación del diccionario inglés-español.
 * Proporciona funcionalidad para cargar un diccionario desde un archivo,
 * realizar traducciones de texto y mostrar el diccionario ordenado.
 * 
 * Utiliza un Árbol Binario de Búsqueda (BST) para almacenar
 * las asociaciones palabra-traducción.
 * 
 * El sistema es insensible a:
 * - Mayúsculas y minúsculas
 * - Acentos y tildes
 * 
 * @author Alejandro Sagastume
 * @version 3.0
 * 
 * @see BinaryTree
 * @see Association
 * @see FileHandler
 * @see StringUtils
 */
public class DictionaryManager {
    
    /** Árbol binario que almacena las asociaciones del diccionario */
    private BinaryTree<Association<String, String>> dictionary;

    /**
     * Constructor que crea un nuevo gestor de diccionario
     * con un árbol binario de búsqueda vacío.
     */
    public DictionaryManager() {
        this.dictionary = new BinaryTree<>();
    }

    /**
     * Carga el diccionario desde un archivo de texto.
     * El archivo debe contener asociaciones en el formato: (palabra_inglés, palabra_española)
     * 
     * Ejemplo de contenido válido:
     * <pre>
     * (house, casa)
     * (dog, perro)
     * (homework, tarea)
     * (hábía, había)
     * </pre>
     * 
     * Las palabras se normalizan al cargar (se remueven acentos para búsqueda).
     * 
     * @param filename el nombre o ruta del archivo que contiene el diccionario
     * 
     * @throws FileNotFoundException si el archivo no existe
     * 
     * @example
     * DictionaryManager manager = new DictionaryManager();
     * manager.loadDictionary("diccionario.txt");
     */
    public void loadDictionary(String filename) {
        // Verificar si el archivo existe antes de intentar leerlo
        if (!FileHandler.fileExists(filename)) {
            System.out.println("[ERROR] El archivo '" + filename + "' no existe.");
            return;
        }

        // Leer el archivo línea por línea
        List<String> lines = FileHandler.readFile(filename);

        // Procesar cada línea del archivo
        for (String line : lines) {
            line = line.trim();
            
            // Verificar que la línea tenga el formato correcto
            if (line.startsWith("(") && line.endsWith(")")) {
                // Remover los paréntesis
                line = line.substring(1, line.length() - 1);
                
                // Dividir por coma para obtener palabra inglesa y española
                String[] parts = line.split(",");
                
                if (parts.length == 2) {
                    String english = parts[0].trim();
                    String spanish = parts[1].trim();
                    
                    // Crear la asociación e insertarla en el árbol
                    // Se almacena la palabra original, pero la búsqueda usa normalización
                    Association<String, String> association = 
                        new Association<>(english, spanish);
                    dictionary.insert(association);
                }
            }
        }
    }

    /**
     * Obtiene el diccionario completo en orden alfabético (In-Order traversal).
     * Las palabras se muestran ordenadas alfabéticamente por la palabra inglesa,
     * en formato: (palabra_inglés: palabra_española)
     * 
     * El ordenamiento considera la normalización (sin acentos).
     * 
     * @return String con todas las asociaciones del diccionario separadas por espacios
     * 
     * @example
     * String diccionarioOrdenado = manager.getDictionaryInOrder();
     * System.out.println(diccionarioOrdenado);
     * // Output: (dog: perro) (homework: tarea) (house: casa)
     */
    public String getDictionaryInOrder() {
        return dictionary.getInOrderTraversal().toString();
    }

    /**
     * Lee un archivo de texto y muestra su traducción en la consola.
     * Traduce cada línea del archivo y la imprime en la terminal.
     * 
     * Las palabras que no se encuentren en el diccionario se escriben
     * encerradas entre asteriscos: *palabra*
     * 
     * El sistema es insensible a mayúsculas, minúsculas y acentos.
     * 
     * @param filename el nombre o ruta del archivo a traducir
     * 
     * @throws FileNotFoundException si el archivo no existe
     * 
     * @example
     * manager.translateFileToConsole("texto.txt");
     * // Output:
     * // *The* mujer *asked* *me* *to* *do* *my* tarea *about* *my* pueblo.
     * 
     * @see #translateText(String)
     */
    public void translateFileToConsole(String filename) {
        // Verificar si el archivo existe
        if (!FileHandler.fileExists(filename)) {
            System.out.println("[ERROR] El archivo '" + filename + "' no existe.");
            return;
        }

        // Leer el archivo
        List<String> lines = FileHandler.readFile(filename);

        // Traducir y mostrar cada línea en la consola
        for (String line : lines) {
            String translatedLine = translateText(line);
            System.out.println(translatedLine);
        }
    }

    /**
     * Traduce una línea de texto del inglés al español.
     * Procesa cada palabra de la línea y mantiene la puntuación y espacios intactos.
     * 
     * Características:
     * - Insensible a mayúsculas/minúsculas
     * - Insensible a acentos y tildes
     * - Preserva la puntuación y espacios originales
     * 
     * Palabras no encontradas se rodean de asteriscos: *palabra*
     * 
     * @param line la línea de texto a traducir
     * @return la línea traducida
     * 
     * @example
     * String original = "The HOUSE is BIG";
     * String traducida = manager.translateText(original);
     * System.out.println(traducida);
     * // Output: *The* casa *is* *big*
     * 
     * String original2 = "haBía UnA VEZ Un caRRO";
     * String traducida2 = manager.translateText(original2);
     * System.out.println(traducida2);
     * // Output: había una vez un carro (si existen en diccionario)
     * 
     * @see #translateWord(String)
     */
    public String translateText(String line) {
        StringBuilder result = new StringBuilder();
        StringBuilder word = new StringBuilder();

        // Procesar cada carácter de la línea
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            // Si es una letra, agregarla a la palabra actual
            if (Character.isLetter(c)) {
                word.append(c);
            } 
            // Si es un carácter no letra (espacio, puntuación, etc.)
            else {
                // Si hay una palabra acumulada, traducirla
                if (word.length() > 0) {
                    result.append(translateWord(word.toString()));
                    word = new StringBuilder();
                }
                // Agregar el carácter no letra tal cual
                result.append(c);
            }
        }
        
        // Traducir la última palabra si existe
        if (word.length() > 0) {
            result.append(translateWord(word.toString()));
        }

        return result.toString();
    }

    /**
     * Traduce una palabra individual buscándola en el diccionario.
     * 
     * La búsqueda es insensible a:
     * - Mayúsculas/minúsculas
     * - Acentos y tildes
     * 
     * Devuelve la traducción tal cual está almacenada en el diccionario.
     * 
     * @param word la palabra a traducir (puede tener cualquier combinación de mayúsculas,
     *             minúsculas o acentos)
     * @return la traducción de la palabra si se encuentra,
     *         o la palabra original encerrada entre asteriscos si no
     * 
     * @example
     * String traducida = translateWord("house");
     * System.out.println(traducida); // Output: casa
     * 
     * String traducida2 = translateWord("HOUSE");
     * System.out.println(traducida2); // Output: casa
     * 
     * String noEncontrada = translateWord("xyz");
     * System.out.println(noEncontrada); // Output: *xyz*
     * 
     * String traducida3 = translateWord("haBía");
     * System.out.println(traducida3); // Output: había (si existe en diccionario)
     * 
     * @see StringUtils#normalize(String)
     */
    private String translateWord(String word) {
        // Crear una clave de búsqueda normalizada (sin acentos, minúsculas)
        Association<String, String> searchKey = 
            new Association<>(word.toLowerCase(), "");
        
        // Buscar la asociación en el diccionario
        Association<String, String> found = dictionary.get(searchKey);
        
        // Si se encuentra, devolver la traducción
        if (found != null) {
            return found.getValue();
        } 
        // Si no se encuentra, devolver la palabra entre asteriscos
        else {
            return "*" + word + "*";
        }
    }
}