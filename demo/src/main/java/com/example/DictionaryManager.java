package com.example;

import java.io.*;

/**
 * Clase que gestiona el diccionario y realiza la traducción de textos
 */
public class DictionaryManager {
    private BinaryTree<Association<String, String>> dictionary;

    /**
     * Constructor del gestor de diccionario
     */
    public DictionaryManager() {
        this.dictionary = new BinaryTree<>();
    }

    /**
     * Carga el diccionario desde un archivo
     * @param filename el nombre del archivo a cargar
     */
    public void loadDictionary(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("(") && line.endsWith(")")) {
                    line = line.substring(1, line.length() - 1);
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String english = parts[0].trim();
                        String spanish = parts[1].trim();
                        Association<String, String> association = 
                            new Association<>(english, spanish);
                        dictionary.insert(association);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo '" + filename + "' no encontrado.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Obtiene el recorrido in-order del diccionario
     * @return String con las asociaciones ordenadas
     */
    public String getDictionaryInOrder() {
        return dictionary.getInOrderTraversal().toString();
    }

    /**
     * Traduce un archivo de texto usando el diccionario
     * @param inputFile el archivo de entrada a traducir
     * @param outputFile el archivo de salida con la traducción
     */
    public void translateFile(String inputFile, String outputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String translatedLine = translateText(line);
                writer.write(translatedLine);
                writer.newLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo '" + inputFile + "' no encontrado.");
        } catch (IOException e) {
            System.out.println("Error al procesar el archivo: " + e.getMessage());
        }
    }

    /**
     * Traduce una línea de texto
     * @param line la línea a traducir
     * @return la línea traducida
     */
    private String translateText(String line) {
        StringBuilder result = new StringBuilder();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (Character.isLetter(c)) {
                word.append(c);
            } else {
                if (word.length() > 0) {
                    result.append(translateWord(word.toString()));
                    word = new StringBuilder();
                }
                result.append(c);
            }
        }
        if (word.length() > 0) {
            result.append(translateWord(word.toString()));
        }

        return result.toString();
    }

    /**
     * Traduce una palabra individual
     * @param word la palabra a traducir
     * @return la palabra traducida o la palabra original entre asteriscos
     */
    private String translateWord(String word) {
        Association<String, String> searchKey = 
            new Association<>(word.toLowerCase(), "");
        
        Association<String, String> found = dictionary.get(searchKey);
        
        if (found != null) {
            return found.getValue();
        } else {
            return "*" + word + "*";
        }
    }
}