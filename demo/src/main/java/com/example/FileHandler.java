package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilitaria que maneja la lectura y escritura de archivos de texto.
 * Proporciona métodos estáticos para leer archivos desde resources o del sistema, escribir contenido y verificar la existencia de archivos.
 * 
 * Los archivos de entrada se buscan primero en la carpeta resources/, y luego en el directorio actual si no se encuentran.
 * 
 * @author Alejandro Sagastume
 * @version 2.0
 */
public class FileHandler {

    /**
     * Lee un archivo de texto desde la carpeta resources o del directorio actual y devuelve su contenido línea por línea.
     * 
     * Busca primero en resources/ (ideal para archivos empaquetados), luego en el directorio de ejecución.
     * 
     * @param filename el nombre del archivo a leer (ej: "diccionario.txt")
     * @return una Lista de String con cada línea del archivo.
     *         Retorna una lista vacía si hay error.
     * 
     * @example
     * List<String> lines = FileHandler.readFile("diccionario.txt");
     */
    public static List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = null;

        try {
            // Intentar leer desde resources/
            reader = getResourceReader(filename);

            // Si no está en resources, intentar leer del directorio actual
            if (reader == null) {
                reader = new BufferedReader(new FileReader(filename));
            }

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            System.out.println("[OK] Archivo '" + filename + "' leido exitosamente.");

        } catch (FileNotFoundException e) {
            System.out.println("[ERROR] Archivo '" + filename + "' no encontrado.");
            System.out.println("   Asegurate de que el archivo esta en src/main/resources/");
        } catch (IOException e) {
            System.out.println("[ERROR] al leer el archivo: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return lines;
    }

    /**
     * Obtiene un BufferedReader para un archivo ubicado en resources/.
     * 
     * @param filename el nombre del archivo en resources/
     * @return un BufferedReader si el archivo se encuentra, null si no
     */
    private static BufferedReader getResourceReader(String filename) {
        try {
            ClassLoader classLoader = FileHandler.class.getClassLoader();
            URL resource = classLoader.getResource(filename);

            if (resource != null) {
                Path path = Paths.get(resource.toURI());
                return new BufferedReader(new FileReader(path.toFile()));
            }
        } catch (URISyntaxException | FileNotFoundException e) {
            // El archivo no está en resources, continuaremos con el siguiente intento
        }
        return null;
    }

    /**
     * Escribe una lista de líneas de texto en un archivo del directorio actual.
     * Si el archivo ya existe, será sobrescrito.
     * 
     * Los archivos de salida se escriben en el directorio de ejecución.
     * 
     * @param filename el nombre o ruta del archivo de salida
     * @param lines la lista de String con las líneas a escribir
     * @return true si la escritura fue exitosa, false si hubo error
     * 
     * @example
     * List<String> lines = new ArrayList<>();
     * lines.add("Primera línea");
     * lines.add("Segunda línea");
     * FileHandler.writeFile("salida.txt", lines);
     */
    public static boolean writeFile(String filename, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("[OK] Archivo '" + filename + "' escrito exitosamente.");
            return true;
        } catch (IOException e) {
            System.out.println("[ERROR] al escribir el archivo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Escribe una cadena de texto completa en un archivo del directorio actual.
     * Si el archivo ya existe, será sobrescrito.
     * 
     * @param filename el nombre o ruta del archivo de salida
     * @param content el contenido de texto a escribir
     * @return true si la escritura fue exitosa, false si hubo error
     * 
     * @example
     * String contenido = "Este es el contenido completo del archivo";
     * FileHandler.writeContent("archivo.txt", contenido);
     */
    public static boolean writeContent(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
            System.out.println("[OK] Archivo '" + filename + "' escrito exitosamente.");
            return true;
        } catch (IOException e) {
            System.out.println("[ERROR] al escribir el archivo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si un archivo existe en resources/ o en el directorio actual.
     * 
     * @param filename el nombre del archivo a verificar
     * @return true si el archivo existe, false si no existe
     * 
     * @example
     * if (FileHandler.fileExists("diccionario.txt")) {
     *     // Procesar archivo
     * }
     */
    public static boolean fileExists(String filename) {
        // Verificar en resources/
        URL resource = FileHandler.class.getClassLoader().getResource(filename);
        if (resource != null) {
            return true;
        }

        // Verificar en directorio actual
        return new File(filename).exists();
    }
}