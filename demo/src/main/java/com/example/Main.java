package com.example;

import java.util.Scanner;

/**
 * Clase principal que contiene la interfaz de usuario del Diccionario Inglés-Español.
 * 
 * Proporciona un menú interactivo con las siguientes opciones:
 * 1. Traducir un archivo (.txt)
 * 2. Traducir palabras/frases escribiendo en la consola
 * 3. Ver el diccionario completo ordenado alfabéticamente
 * 4. Salir del programa
 * 
 * @author Alejandro Sagastume
 * @version 2.0
 * 
 * @see DictionaryManager
 * @see BinaryTree
 * @see Association
 */
public class Main {
    
    /** Instancia del gestor de diccionario */
    private static DictionaryManager manager;
    
    /** Objeto Scanner para leer entrada del usuario */
    private static Scanner scanner;

    /**
     * Método principal que inicia la aplicación del diccionario.
     * Carga el diccionario y muestra el menú interactivo al usuario.
     * 
     * @param args argumentos de línea de comandos (no utilizados)
     * 
     * @example
     * java -cp . com.example.Main
     */
    public static void main(String[] args) {
        manager = new DictionaryManager();
        scanner = new Scanner(System.in);

        System.out.println("=== DICCIONARIO INGLES-ESPANOL ===\n");
        
        // Cargar diccionario desde archivo
        manager.loadDictionary("diccionario.txt");
        System.out.println("[OK] Diccionario cargado exitosamente.\n");

        // Mostrar menú principal de forma repetida hasta que el usuario salga
        boolean running = true;
        while (running) {
            displayMenu();
            int option = getUserOption();

            switch (option) {
                case 1:
                    // Opción: Traducir archivo
                    translateFileOption();
                    break;
                case 2:
                    // Opción: Traducir palabras interactivamente
                    translateWordsInteractive();
                    break;
                case 3:
                    // Opción: Ver diccionario ordenado
                    displayDictionary();
                    break;
                case 4:
                    // Opción: Salir del programa
                    running = false;
                    System.out.println("[OK] Programa finalizado.");
                    break;
                default:
                    System.out.println("[ERROR] Opcion invalida. Intente de nuevo.\n");
            }
        }

        // Cerrar el Scanner
        scanner.close();
    }

    /**
     * Muestra el menú principal con las opciones disponibles.
     * 
     * @see #getUserOption()
     */
    private static void displayMenu() {
        System.out.println("\n========== MENU PRINCIPAL ==========");
        System.out.println("1. Traducir un archivo (.txt)");
        System.out.println("2. Traducir palabras (escribir en consola)");
        System.out.println("3. Ver diccionario ordenado");
        System.out.println("4. Salir");
        System.out.println("====================================");
        System.out.print("Seleccione una opcion (1-4): ");
    }

    /**
     * Lee una opción numérica del usuario desde la consola.
     * Valida que el entrada sea un número entero válido.
     * 
     * @return el número de opción seleccionado,
     *         o -1 si la entrada no es un número válido
     * 
     * @see #displayMenu()
     */
    private static int getUserOption() {
        try {
            int option = Integer.parseInt(scanner.nextLine().trim());
            return option;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Opción 1 del menú: Traduce un archivo de texto y muestra el resultado en la terminal.
     * 
     * Solicita al usuario el nombre del archivo a traducir,
     * lee su contenido, traduce todas las líneas y las muestra en la consola.
     * 
     * @see DictionaryManager#translateFileToConsole(String)
     */
    private static void translateFileOption() {
        System.out.print("\nIngrese el nombre del archivo a traducir (ej: texto.txt): ");
        String filename = scanner.nextLine().trim();

        System.out.println("\n========== TRADUCCION DEL ARCHIVO ==========\n");
        manager.translateFileToConsole(filename);
        System.out.println("\n==========================================\n");
    }

    /**
     * Opción 2 del menú: Traduce palabras/frases escritas en la consola.
     * 
     * Permite al usuario ingresar múltiples palabras o frases
     * para traducir de forma interactiva.
     * 
     * El usuario puede escribir "salir" para volver al menú principal.
     * 
     * @see DictionaryManager#translateText(String)
     */
    private static void translateWordsInteractive() {
        System.out.println("\n========== TRADUCTOR DE PALABRAS ==========");
        System.out.println("(Escriba 'salir' para volver al menu principal)\n");

        while (true) {
            System.out.print("Ingrese una palabra o frase a traducir: ");
            String input = scanner.nextLine().trim();

            // Si el usuario escribe "salir", volver al menú principal
            if (input.equalsIgnoreCase("salir")) {
                System.out.println("[OK] Volviendo al menu principal...");
                break;
            }

            // Validar que la entrada no esté vacía
            if (input.isEmpty()) {
                System.out.println("[ERROR] Por favor ingrese una palabra valida.\n");
                continue;
            }

            // Traducir y mostrar resultado
            String translated = manager.translateText(input);
            System.out.println("Traduccion: " + translated + "\n");
        }
    }

    /**
     * Opción 3 del menú: Muestra el diccionario completo ordenado alfabéticamente.
     * 
     * El diccionario se muestra en orden In-Order del árbol binario,
     * lo que significa que estará ordenado alfabéticamente por la palabra inglesa.
     * 
     * Formato de salida: (palabra_inglés: palabra_española) (palabra_inglés: palabra_española) ...
     * 
     * @see DictionaryManager#getDictionaryInOrder()
     */
    private static void displayDictionary() {
        System.out.println("\n========== DICCIONARIO (Ordenado In-Order) ==========");
        String dictionary = manager.getDictionaryInOrder();
        
        // Verificar si el diccionario está vacío
        if (dictionary.isEmpty()) {
            System.out.println("El diccionario esta vacio.");
        } else {
            System.out.println(dictionary);
        }
        System.out.println("====================================================\n");
    }
}