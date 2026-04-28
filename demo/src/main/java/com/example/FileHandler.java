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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase utilitaria que maneja la lectura y escritura de archivos de texto con encoding UTF-8.
 * Proporciona métodos estáticos para leer archivos desde resources o del sistema, escribir contenido y verificar la existencia de archivos.
 * Los archivos de entrada se buscan primero en la carpeta resources/ con encoding UTF-8, y luego en el directorio actual si no se encuentran.
 * IMPORTANTE: Todos los archivos se manejan con encoding UTF-8 para asegurar que caracteres especiales como acentos (á, é, í, ó, ú) y otros diacríticos se lean correctamente.
 * 
 * @author Alejandro Sagastume
 * @version 3.0
 */
public class FileHandler {

    /**
     * Lee un archivo de texto desde la carpeta resources o del directorio actual con encoding UTF-8.
     * Devuelve el contenido línea por línea en una Lista de String.
     * 
     * Búsqueda:
     * 1. Primero intenta en resources/ (ideal para archivos empaquetados con UTF-8)
     * 2. Si no está, intenta en el directorio actual con UTF-8
     * 3. Si el archivo no existe, retorna lista vacía
     * 
     * UTF-8:
     * Este método utiliza StandardCharsets.UTF_8 para leer el archivo, asegurando que
     * caracteres especiales como "sí", "español", "teléfono" se lean correctamente.
     * 
     * @param filename el nombre del archivo a leer (ej: "diccionario.txt")
     * @return una Lista de String con cada línea del archivo.
     *         Retorna una lista vacía si hay error o archivo no existe.
     * 
     * @example
     * List<String> lines = FileHandler.readFile("diccionario.txt");
     * // Si contiene: (yes, sí)
     * // Se lee correctamente como: (yes, sí) ✓
     * // NO como: (yes, s??) ✗
     * 
     * @see #getResourceReader(String)
     */
    public static List<String> readFile(String filename) {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = null;

        try {
            // Intentar leer desde resources/ con UTF-8
            reader = getResourceReader(filename);

            // Si no está en resources, intentar leer del directorio actual con UTF-8
            if (reader == null) {
                reader = new BufferedReader(
                    new FileReader(filename, StandardCharsets.UTF_8)
                );
            }

            // Leer todas las líneas del archivo
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            System.out.println("[OK] Archivo '" + filename + "' leido exitosamente con UTF-8.");

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
     * Obtiene un BufferedReader para un archivo ubicado en resources/ con encoding UTF-8.
     * 
     * Este método busca el archivo en el classpath de resources/ y lo abre con UTF-8.
     * Es importante para archivos que se empacan dentro del JAR.
     * 
     * UTF-8 Encoding:
     * Usa StandardCharsets.UTF_8 y Files.newBufferedReader() para garantizar lectura correcta
     * de caracteres especiales en archivos de diccionario.
     * 
     * @param filename el nombre del archivo en resources/ (ej: "diccionario.txt")
     * @return un BufferedReader si el archivo se encuentra, null si no existe en resources/
     * 
     * @example
     * BufferedReader reader = getResourceReader("diccionario.txt");
     * if (reader != null) {
     *     String line = reader.readLine(); // Lee con UTF-8
     * }
     * 
     * @see StandardCharsets#UTF_8
     */
    private static BufferedReader getResourceReader(String filename) {
        try {
            // Obtener el ClassLoader para buscar en resources/
            ClassLoader classLoader = FileHandler.class.getClassLoader();
            URL resource = classLoader.getResource(filename);

            // Si el recurso existe en resources/
            if (resource != null) {
                // Convertir URL a Path
                Path path = Paths.get(resource.toURI());
                // Abrir con UTF-8 usando Files.newBufferedReader
                return Files.newBufferedReader(path, StandardCharsets.UTF_8);
            }
        } catch (URISyntaxException | IOException e) {
            // El archivo no está en resources, continuaremos con el siguiente intento
        }
        return null;
    }

    /**
     * Escribe una lista de líneas de texto en un archivo del directorio actual con encoding UTF-8.
     * Si el archivo ya existe, será sobrescrito.
     * 
     * UTF-8 Encoding:
     * Utiliza StandardCharsets.UTF_8 para garantizar que caracteres especiales se escriban correctamente.
     * Es especialmente importante para archivos que contienen palabras en español.
     * 
     * @param filename el nombre o ruta del archivo de salida (ej: "salida.txt")
     * @param lines la lista de String con las líneas a escribir
     * @return true si la escritura fue exitosa, false si hubo error
     * 
     * @example
     * List<String> lines = new ArrayList<>();
     * lines.add("(yes, sí)");     // Contiene acento
     * lines.add("(dog, perro)");
     * FileHandler.writeFile("diccionario.txt", lines);
     * // Se escribe correctamente con UTF-8 ✓
     * 
     * @see StandardCharsets#UTF_8
     */
    public static boolean writeFile(String filename, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(
            new FileWriter(filename, StandardCharsets.UTF_8))) {
            
            // Escribir cada línea
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("[OK] Archivo '" + filename + "' escrito exitosamente con UTF-8.");
            return true;
        } catch (IOException e) {
            System.out.println("[ERROR] al escribir el archivo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Escribe una cadena de texto completa en un archivo del directorio actual con encoding UTF-8.
     * Si el archivo ya existe, será sobrescrito.
     * 
     * UTF-8 Encoding:
     * Utiliza StandardCharsets.UTF_8 para asegurar que todo el contenido se escribe con encoding correcto.
     * Ideal para guardar resultados de traducción que contienen caracteres especiales.
     * 
     * @param filename el nombre o ruta del archivo de salida (ej: "traduccion.txt")
     * @param content el contenido de texto completo a escribir
     * @return true si la escritura fue exitosa, false si hubo error
     * 
     * @example
     * String contenido = "La traducción: *The* mujer *asked* *me*";
     * FileHandler.writeContent("salida.txt", contenido);
     * // Se escribe correctamente manteniendo caracteres especiales ✓
     * 
     * @see StandardCharsets#UTF_8
     */
    public static boolean writeContent(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter(
            new FileWriter(filename, StandardCharsets.UTF_8))) {
            
            // Escribir el contenido completo
            writer.write(content);
            System.out.println("[OK] Archivo '" + filename + "' escrito exitosamente con UTF-8.");
            return true;
        } catch (IOException e) {
            System.out.println("[ERROR] al escribir el archivo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si un archivo existe en resources/ o en el directorio actual.
     * 
     * Búsqueda:
     * 1. Primero verifica si existe en resources/ (usando ClassLoader)
     * 2. Si no, verifica si existe en el directorio actual del sistema de archivos
     * 
     * @param filename el nombre del archivo a verificar (ej: "diccionario.txt")
     * @return true si el archivo existe en resources/ o en el directorio actual,
     *         false si no existe en ninguna ubicación
     * 
     * @example
     * if (FileHandler.fileExists("diccionario.txt")) {
     *     List<String> lines = FileHandler.readFile("diccionario.txt");
     *     // Procesar archivo
     * }
     */
    public static boolean fileExists(String filename) {
        // Verificar en resources/
        URL resource = FileHandler.class.getClassLoader().getResource(filename);
        if (resource != null) {
            return true;
        }

        // Verificar en directorio actual del sistema de archivos
        return new File(filename).exists();
    }
}